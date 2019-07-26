package ru.opensolutions.fortune.api;

/**
 * pojo для результата.
 */
public class ResultResponse<T> {
    private final T data;

    ResultResponse(final T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
