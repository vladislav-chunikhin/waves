package ru.opensolutions.fortune.configuration.auth;

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
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        ServletResponseWrapperUtils servletResponseWrapperUtils = new ServletResponseWrapperUtils();
        if (authException instanceof InsufficientAuthenticationException){
            servletResponseWrapperUtils.setServletResponseWrapper(
                    response,
                    "Для доступа к этому ресурсу требуется аутентификация.",
                    HttpStatus.UNAUTHORIZED.value()
            );
            return;
        }
        servletResponseWrapperUtils.setServletResponseWrapper(
                response,
                authException.getMessage(),
                HttpStatus.UNAUTHORIZED.value()
        );
    }
}
