package ru.opensolutions.fortune.json.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * pojo для запроса на отправку txId. */
@Data
public class TransactionIdRequest implements Serializable {

    /**
     * Идентификатор транзакции. */
    @JsonProperty("tx_id")
    private String txId;
}
