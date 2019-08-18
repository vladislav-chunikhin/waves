package ru.opensolutions.fortune.json.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Расширенный pojo для запроса на отправку txId. */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class SendDataToNodeRequest extends TransactionIdRequest {

    /**
     * Номер теста. */
    @JsonProperty("test_number")
    private String testNumber;

    /**
     * Значение ставки. */
    @JsonProperty("bet_value")
    private String betValue;
}
