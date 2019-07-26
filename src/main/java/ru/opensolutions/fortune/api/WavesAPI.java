package ru.opensolutions.fortune.api;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

/**
 * Класс для формирования ответа клиенту. */
@Slf4j
public final class WavesAPI {

    private WavesAPI() {
        throw new RuntimeException();
    }

    public static <T> WavesResponse positiveResponse(final T data) {
        log.debug("\nRESPONSE = {}",data);
        return new WavesResponse(new StatusResponse(HttpStatus.OK.value(), Collections.emptyList()), new ResultResponse<>(data));
    }

    public static WavesResponse negativeResponse(final int code, final List<String> message){
        log.error("\nCODE = {}\nMESSAGE = {}", code, message.get(0));
        return new WavesResponse(new StatusResponse(code,message),new ResultResponse<>(Strings.EMPTY));
    }
}
