package ru.opensolutions.fortune.advice;

import ru.opensolutions.fortune.api.WavesAPI;
import ru.opensolutions.fortune.api.WavesResponse;
import ru.opensolutions.fortune.util.log.AbstractLogger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.util.Collections;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Класс-совет для обработки ошибок. */
@RestControllerAdvice
public class WavesControllerAdvice extends AbstractLogger {

    @ExceptionHandler({
            IllegalArgumentException.class,
            ValidationException.class
    })
    @ResponseStatus(BAD_REQUEST)
    public WavesResponse handleValidationException (final RuntimeException ex) {
        error("handleValidationException = {}", ex.toString());
        return WavesAPI.negativeResponse(BAD_REQUEST.value(),
                Collections.singletonList(ex.toString()));
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public WavesResponse handleException(final Throwable t) {
        error("handleException = {}", t.toString());
        return WavesAPI.negativeResponse(INTERNAL_SERVER_ERROR.value(),
                Collections.singletonList(t.toString()));
    }
}
