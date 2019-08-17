package ru.opensolutions.fortune.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * pojo для результата. */
@AllArgsConstructor
class ResultResponse<T> {

    /**
     * Данные.
     * @param <T> параметризированный тип данных. */
    @Getter
    private final T data;

}
