package ru.opensolutions.fortune.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *  pojo для http статуса и сообщения. */
@AllArgsConstructor
class StatusResponse {

    /**
     * Код ответа. */
    @Getter
    private final int code;

    /**
     * Сообщение. */
    @Getter
    private final String message;

}
