package ru.opensolutions.fortune.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Общий ответ для клиента. */
@AllArgsConstructor
public class WavesResponse {

    /**
     * Статус ответа. */
    @Getter
    private final StatusResponse status;

    /**
     * Результат. */
    @Getter
    private final ResultResponse result;
}
