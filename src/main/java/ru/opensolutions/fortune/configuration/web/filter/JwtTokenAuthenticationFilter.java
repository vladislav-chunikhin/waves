package ru.opensolutions.fortune.configuration.web.filter;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.core.authority.AuthorityUtils;
import ru.opensolutions.fortune.exception.JwtBadSignatureException;
import ru.opensolutions.fortune.exception.JwtExpirationException;
import ru.opensolutions.fortune.exception.MalformedJwtException;
import ru.opensolutions.fortune.configuration.SecurityParamsConfig;
import ru.opensolutions.fortune.model.JwtUser;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static ru.opensolutions.fortune.util.JwtUtils.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * Фильтр для всех http запросов. */
public class JwtTokenAuthenticationFilter extends GenericFilterBean implements SecurityParamsConfig {

    private String secretKey;
    private String authSwitch;

    public JwtTokenAuthenticationFilter(String secretKey, String authSwitch) {
        this.secretKey = secretKey;
        this.authSwitch = authSwitch;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (authSwitch.equals("off")) {
            Authentication auth = buildDefaultAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(auth);
            chain.doFilter(request, response);
            return;
        }

        List<String> urlPatterns = Collections.singletonList(AUTH_URL);

        if (urlPatterns.contains(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader(AUTHORIZATION);
        if (Objects.isNull(header) || !header.startsWith(PREFIX_AUTH_HEADER)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            SignedJWT jwt = extractAndDecodeJwt(request);
            checkAuthenticationAndValidity(jwt);
            Authentication auth = buildAuthentication(jwt, request);
            SecurityContextHolder.getContext().setAuthentication(auth);
            chain.doFilter(request, response);
        } catch (JwtBadSignatureException | JOSEException | JwtExpirationException ex ) {
            throw new MalformedJwtException(ex.toString());
        } catch (ParseException ex) {
            throw new MalformedJwtException(String.format("Неверно составлен токен. Ошибка при парсинге:%s", ex.toString()));
        }
    }

    private SignedJWT extractAndDecodeJwt(HttpServletRequest request) throws ParseException {
        String authHeader = request.getHeader(AUTHORIZATION);
        String token = authHeader.substring(PREFIX_AUTH_HEADER.length());
        return parse(token);
    }

    private void checkAuthenticationAndValidity(SignedJWT jwt) throws ParseException, JOSEException {
        assertNotExpired(jwt);
        assertValidSignature(jwt, secretKey);
    }

    @SneakyThrows
    private Authentication buildAuthentication(SignedJWT jwt, HttpServletRequest request) {

        String username = getUsername(jwt);
        Collection<? extends GrantedAuthority> authorities = getRoles(jwt);
        Date creationDate = getIssueTime(jwt);
        JwtUser userDetails = new JwtUser(username, creationDate, authorities);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                authorities);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authentication;
    }

    private Authentication buildDefaultAuthentication(HttpServletRequest request) {
        List<GrantedAuthority> authorities =
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN");
        JwtUser defaultUserDetails = new JwtUser(
                "open-solutions",
                Date.from(
                        LocalDateTime
                                .now()
                                .minusHours(1L)
                                .atZone(ZoneId.systemDefault())
                                .toInstant()),
                authorities
                );
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                defaultUserDetails,
                null,
                authorities);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authentication;
    }
}
