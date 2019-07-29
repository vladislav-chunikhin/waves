package ru.opensolutions.fortune.service.spring.security;

import com.nimbusds.jose.JOSEException;
import ru.opensolutions.fortune.api.WavesAPI;
import ru.opensolutions.fortune.api.WavesResponse;
import ru.opensolutions.fortune.util.log.AbstractLogger;
import ru.opensolutions.fortune.configuration.SecurityParamsConfig;
import ru.opensolutions.fortune.json.request.AuthenticationRequest;
import ru.opensolutions.fortune.json.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static ru.opensolutions.fortune.util.JwtUtils.generateHMACToken;

/**
 * Сервис по авторизации пользователя. */
@Service
@RequiredArgsConstructor
public class AuthenticationService extends AbstractLogger {

    private final AuthenticationManager authenticationManager;

    @SneakyThrows({IOException.class, JOSEException.class})
    public WavesResponse authenticationRequest(AuthenticationRequest authenticationRequest) {
        final String methodName = "authenticationRequest";
        logStartMethod(methodName);
        final String login = authenticationRequest.getLogin();
        final String password = authenticationRequest.getPassword();

        final Authentication authentication;
        try{
            authentication = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login, password));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Неверный логин или пароль");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String secret = IOUtils.toString(this.getClass().getResourceAsStream(SecurityParamsConfig.SECRET_FILE));
        final String accessToken = generateHMACToken(login, authentication.getAuthorities(), secret, SecurityParamsConfig.EXPIRATION_IN_MINUTES_FOR_ACCESS_TOKEN);

        return WavesAPI.positiveResponse(new AuthenticationResponse(SecurityParamsConfig.PREFIX_AUTH_HEADER.concat(" ").concat(accessToken)));
    }
}
