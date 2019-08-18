package ru.opensolutions.fortune.json.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * pojo для ответа идентификатора транзакции. */
@AllArgsConstructor
public class TransactionIdResponse implements Serializable {

    /**
     * Идентификатор транзакции. */
    @JsonProperty("tx_id")
    @Getter
    @Setter
    private String txId;
}
