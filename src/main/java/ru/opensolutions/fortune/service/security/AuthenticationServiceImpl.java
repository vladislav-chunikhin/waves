package ru.opensolutions.fortune.service.security;

import com.nimbusds.jose.JOSEException;
import lombok.NonNull;
import ru.opensolutions.fortune.api.WavesAPI;
import ru.opensolutions.fortune.api.WavesResponse;
import ru.opensolutions.fortune.util.log.AbstractLogger;
import ru.opensolutions.fortune.configuration.security.SecurityParamsConfig;
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
 * Имплементация {@link AuthenticationService}. */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl extends AbstractLogger implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    @SneakyThrows({IOException.class, JOSEException.class})
    public WavesResponse authenticationRequest(@NonNull final AuthenticationRequest authenticationRequest) {
        final String methodName = "authenticationRequest";
        this.logStartMethod(methodName);
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
        final String accessToken = generateHMACToken(
                login,
                authentication.getAuthorities(),
                secret,
                SecurityParamsConfig.EXPIRATION_IN_MINUTES_FOR_ACCESS_TOKEN);

        return WavesAPI.positiveResponse(new AuthenticationResponse(SecurityParamsConfig.PREFIX_AUTH_HEADER.concat(" ").concat(accessToken)));
    }
}
