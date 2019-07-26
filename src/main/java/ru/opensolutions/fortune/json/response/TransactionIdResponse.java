package ru.opensolutions.fortune.json.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * created by vlad on 18.07.19
 */
public class TransactionIdResponse implements Serializable {

    @JsonProperty("tx_id")
    @Getter
    @Setter
    private String txId;

    public TransactionIdResponse(String txId) {
        this.txId = txId;
    }
}
