package ru.opensolutions.fortune.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import ru.opensolutions.fortune.api.WavesAPI;
import ru.opensolutions.fortune.api.WavesResponse;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.util.Collections;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Обёртка для http ответа. */
public final class ServletResponseWrapperUtils {
    @SneakyThrows(IOException.class)
    public void setServletResponseWrapper(
            @NonNull final HttpServletResponse response,
            @NonNull final String errorMsg,
            @NonNull final int status) {
        final WavesResponse wavesResponse = WavesAPI.negativeResponse(status, errorMsg);
        final ObjectMapper objMapper = new ObjectMapper();
        final HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper(response);
        wrapper.setStatus(status);
        wrapper.setContentType(APPLICATION_JSON_VALUE);
        wrapper.getWriter().println(objMapper.writeValueAsString(wavesResponse));
        wrapper.getWriter().flush();
    }
}
