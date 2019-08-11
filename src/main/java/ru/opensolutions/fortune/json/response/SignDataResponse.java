package ru.opensolutions.fortune.json.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * created by vlad on 07.07.19
 */
public class SignDataResponse extends TransactionIdResponse {

    @JsonProperty("signature_base_64")
    @Getter
    @Setter
    private String signatureBase64;

    @JsonProperty("public_key")
    @Getter
    @Setter
    private String publicKey;

    public SignDataResponse(String txId,String signatureBase64, String publicKey) {
        super(txId);
        this.signatureBase64 = signatureBase64;
        this.publicKey = publicKey;
    }
}
