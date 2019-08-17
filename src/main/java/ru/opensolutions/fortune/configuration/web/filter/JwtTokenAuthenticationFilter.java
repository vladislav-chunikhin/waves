package ru.opensolutions.fortune.configuration.web.filter;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import lombok.NonNull;
import org.springframework.security.core.authority.AuthorityUtils;
import ru.opensolutions.fortune.exception.JwtBadSignatureException;
import ru.opensolutions.fortune.exception.JwtExpirationException;
import ru.opensolutions.fortune.exception.MalformedJwtException;
import ru.opensolutions.fortune.model.constants.SecurityConstants;
import ru.opensolutions.fortune.model.security.JwtUser;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;
import ru.opensolutions.fortune.util.MessageUtils;
import ru.opensolutions.fortune.util.enums.AuthOptionType;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

import java.util.List;
import java.util.Collections;
import java.util.Collection;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static ru.opensolutions.fortune.util.JwtUtils.assertNotExpired;
import static ru.opensolutions.fortune.util.JwtUtils.assertValidSignature;
import static ru.opensolutions.fortune.util.JwtUtils.parse;
import static ru.opensolutions.fortune.util.JwtUtils.getUsername;
import static ru.opensolutions.fortune.util.JwtUtils.getRoles;

/**
 * Фильтр для всех http запросов. */
public class JwtTokenAuthenticationFilter extends GenericFilterBean {

    /**
     * Секретный ключ. */
    private String secretKey;
    /**
     * Настройка по включению | выключению авторизации в проекте. */
    private String authSwitch;

    /**
     * @param secretKey секретный ключ.
     * @param authSwitch опция для авторизации. */
    public JwtTokenAuthenticationFilter(@NonNull final String secretKey,
                                        @NonNull final String authSwitch)
    {
        this.secretKey = secretKey;
        this.authSwitch = authSwitch;
    }

    /**
     * @param req http запрос.
     * @param res http ответ.
     * @param chain цепочка настроенных фильтров. */
    @Override
    public void doFilter(@NonNull final ServletRequest req,
                         @NonNull final ServletResponse res,
                         @NonNull final FilterChain chain)
            throws IOException, ServletException
    {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;

        @NonNull final AuthOptionType authOptionType =
                AuthOptionType.getEnum(authSwitch);

        if (authOptionType == AuthOptionType.OFF) {
            final Authentication auth = this.buildDefaultAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(auth);
            chain.doFilter(request, response);
            return;
        }

        final List<String> urlPatterns = Collections.singletonList(SecurityConstants.AUTH_URL);

        if (urlPatterns.contains(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        final String header = request.getHeader(AUTHORIZATION);
        if (Objects.isNull(header) || !header.startsWith(SecurityConstants.PREFIX_AUTH_HEADER)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            final SignedJWT jwt = extractAndDecodeJwt(request);
            this.checkAuthenticationAndValidity(jwt);
            final Authentication auth = this.buildAuthentication(jwt, request);
            SecurityContextHolder.getContext().setAuthentication(auth);
            chain.doFilter(request, response);
        } catch (JwtBadSignatureException | JOSEException | JwtExpirationException ex ) {
            throw new MalformedJwtException(ex.toString());
        } catch (ParseException ex) {
            throw new MalformedJwtException(
                    MessageUtils.getMessage("malformed.jwt.exception", ex.toString()));
        }
    }

    /**
     * @param request http запрос.
     * @return объект токена после парсинга.
     * @throws ParseException какое-либо исключение во время парсинга. */
    private SignedJWT extractAndDecodeJwt(@NonNull final HttpServletRequest request) throws ParseException {
        final String authHeader = request.getHeader(AUTHORIZATION);
        final String token = authHeader.substring(SecurityConstants.PREFIX_AUTH_HEADER.length());
        return parse(token);
    }

    /**
     * @param jwt {@link SignedJWT} объект токена после парсинга.
     * @throws ParseException какое-либо исключение во время парсинга.
     * @throws JOSEException какое-либо исключение во время валидации. */
    private void checkAuthenticationAndValidity(@NonNull final SignedJWT jwt) throws ParseException, JOSEException {
        assertNotExpired(jwt);
        assertValidSignature(jwt, secretKey);
    }

    /**
     * @param jwt {@link SignedJWT} объект токена после парсинга.
     * @param request http запрос.
     * @return {@link Authentication} объект авторизации пользователя который устанавливается в контекст приложения. */
    @SneakyThrows(ParseException.class)
    private Authentication buildAuthentication(@NonNull final SignedJWT jwt,
                                               @NonNull final HttpServletRequest request)
    {

        final String username = getUsername(jwt);
        final Collection<? extends GrantedAuthority> authorities = getRoles(jwt);
        final JwtUser userDetails = new JwtUser(username, authorities);

        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                authorities);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authentication;
    }

    /**
     * @param request http запрос.
     * @return {@link Authentication} объект авторизации пользователя который устанавливается в контекст приложения. */
    private Authentication buildDefaultAuthentication(@NonNull final HttpServletRequest request)
    {
        final List<GrantedAuthority> authorities =
                AuthorityUtils.commaSeparatedStringToAuthorityList(SecurityConstants.DEFAULT_ROLE);
        final JwtUser defaultUserDetails = new JwtUser(
                SecurityConstants.DEFAULT_LOGIN,
                authorities
                );
        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                defaultUserDetails,
                null,
                authorities);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authentication;
    }
}
