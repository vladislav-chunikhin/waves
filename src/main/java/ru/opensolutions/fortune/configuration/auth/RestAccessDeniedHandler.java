package ru.opensolutions.fortune.configuration.auth;

import ru.opensolutions.fortune.util.ServletResponseWrapperUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Перехватчик ошибок прав доступа. */
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) {
        ServletResponseWrapperUtils servletResponseWrapperUtils = new ServletResponseWrapperUtils();
        servletResponseWrapperUtils.setServletResponseWrapper(
                response,
                "Доступ запрещен.",
                HttpStatus.FORBIDDEN.value()
        );
    }
}
