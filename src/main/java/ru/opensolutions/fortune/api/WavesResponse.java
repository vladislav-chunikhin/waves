package ru.opensolutions.fortune.api;

/**
 * Общий ответ для клиента. */
public class WavesResponse {

    private final StatusResponse status;

    private final ResultResponse result;

    public WavesResponse(final StatusResponse statusResponse, final ResultResponse resultResponse) {
        this.status = statusResponse;
        this.result = resultResponse;
    }

    public ResultResponse getResult() {
        return result;
    }

    public StatusResponse getStatus() {
        return status;
    }
}
