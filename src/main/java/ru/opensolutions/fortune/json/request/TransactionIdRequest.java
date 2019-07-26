package ru.opensolutions.fortune.json.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * created by vlad on 30.06.19
 */
@Data
public class TransactionIdRequest implements Serializable {

    @JsonProperty("tx_id")
    private String txId;
}
