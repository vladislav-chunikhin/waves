package ru.opensolutions.fortune.api;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;

import static ru.opensolutions.fortune.util.JsonUtils.getJsonFromObject;

/**
 * Класс для формирования ответа клиенту. */
@Slf4j
public final class WavesAPI {

    /**
     * Запрещаем создавать эксземпляр данного класса. */
    private WavesAPI() {
        throw new RuntimeException();
    }

    /**
     * Положительный ответ.
     * @param data данные.
     * @return pojo общего ответа. */
    public static <T> WavesResponse positiveResponse(final T data) {
        log.debug("\nRESPONSE = {}", getJsonFromObject(data, true));
        return new WavesResponse(new StatusResponse(HttpStatus.OK.value(), Strings.EMPTY), new ResultResponse<>(data));
    }

    /**
     * Ответ при ошибке.
     * @param code код ошибки.
     * @param message сообщение ошибки.
     * @return pojo общего ответа. */
    public static WavesResponse negativeResponse(final int code, final String message){
        log.error("\nCODE = {}\nMESSAGE = {}", code, message);
        return new WavesResponse(new StatusResponse(code, message),new ResultResponse<>(Strings.EMPTY));
    }
}
