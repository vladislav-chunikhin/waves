package ru.opensolutions.fortune.configuration.security;

import lombok.NonNull;
import ru.opensolutions.fortune.util.MessageUtils;
import ru.opensolutions.fortune.util.ServletResponseWrapperUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Перехватчик ошибок аутентификации. */
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final AuthenticationException authException)
    {
        if (authException instanceof InsufficientAuthenticationException) {
            ServletResponseWrapperUtils.setServletResponseWrapper(
                    response,
                    MessageUtils.getMessage("authentication.entry.point.msg"),
                    HttpStatus.UNAUTHORIZED.value()
            );
            return;
        }
        ServletResponseWrapperUtils.setServletResponseWrapper(
                response,
                authException.getMessage(),
                HttpStatus.UNAUTHORIZED.value()
        );
    }
}
