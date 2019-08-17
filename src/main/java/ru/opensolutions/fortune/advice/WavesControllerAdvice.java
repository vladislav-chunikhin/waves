package ru.opensolutions.fortune.advice;

import lombok.NonNull;
import ru.opensolutions.fortune.api.WavesAPI;
import ru.opensolutions.fortune.api.WavesResponse;
import ru.opensolutions.fortune.util.log.AbstractLogger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Класс-совет для обработки ошибок. */
@RestControllerAdvice
public class WavesControllerAdvice extends AbstractLogger {

    /**
     * Перехватывает все ошибки связанные с валидацией
     * @param ex исключения по валидации параметров.
     * @return {@link WavesResponse} общий ответ API. */
    @ExceptionHandler({
            IllegalArgumentException.class,
            ValidationException.class
    })
    @ResponseStatus(BAD_REQUEST)
    public WavesResponse handleValidationException (@NonNull final RuntimeException ex) {
        this.error("handleValidationException = {}", ex.toString());
        return WavesAPI.negativeResponse(BAD_REQUEST.value(), ex.toString());
    }

    /**
     * Общий перехватчик ошибок.
     * @param t любые исключения.
     * @return {@link WavesResponse} общий ответ API. */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public WavesResponse handleException(@NonNull final Throwable t) {
        this.error("handleException = {}", t.toString());
        return WavesAPI.negativeResponse(INTERNAL_SERVER_ERROR.value(), t.toString());
    }
}
