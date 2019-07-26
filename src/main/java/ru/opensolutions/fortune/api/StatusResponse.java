package ru.opensolutions.fortune.api;

import java.util.List;

/**
 *  pojo для http статуса.
 */
public class StatusResponse {
    private final int code;
    private final List<String> message;

    StatusResponse(final int code, final List<String> message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public List<String> getMessage() {
        return message;
    }
}
