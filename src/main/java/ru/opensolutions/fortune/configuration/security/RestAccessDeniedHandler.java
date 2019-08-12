package ru.opensolutions.fortune.configuration.security;

import lombok.NonNull;
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
    public void handle(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final AccessDeniedException e)
    {
        final ServletResponseWrapperUtils servletResponseWrapperUtils = new ServletResponseWrapperUtils();
        servletResponseWrapperUtils.setServletResponseWrapper(
                response,
                "Доступ запрещен.",
                HttpStatus.FORBIDDEN.value()
        );
    }
}
