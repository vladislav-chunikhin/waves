package ru.opensolutions.fortune.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * pojo для результата. */
@AllArgsConstructor
class ResultResponse<T> {

    /**
     * Данные. */
    @Getter
    private final T data;

}
