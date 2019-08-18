package ru.opensolutions.fortune.json.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * pojo для ответа теста подписи. */
public class SignDataResponse extends TransactionIdResponse {

    /**
     * Подпись в формате base64. */
    @JsonProperty("signature_base_64")
    @Getter
    @Setter
    private String signatureBase64;

    /**
     * Публичный ключ в строковом представлении. */
    @JsonProperty("public_key")
    @Getter
    @Setter
    private String publicKey;

    /**
     * @param txId идентификатор транзакции.
     * @param signatureBase64 {@link SignDataResponse#signatureBase64}.
     * @param publicKey {@link SignDataResponse#publicKey}.
     */
    public SignDataResponse(final String txId, final String signatureBase64, final String publicKey) {
        super(txId);
        this.signatureBase64 = signatureBase64;
        this.publicKey = publicKey;
    }
}
