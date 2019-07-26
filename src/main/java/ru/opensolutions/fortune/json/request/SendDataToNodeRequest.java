package ru.opensolutions.fortune.json.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * created by vlad on 07.07.19
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class SendDataToNodeRequest extends TransactionIdRequest {

    @JsonProperty("test_number")
    private String testNumber;

    @JsonProperty("bet_value")
    private String betValue;
}
