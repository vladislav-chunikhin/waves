package ru.opensolutions.fortune.json.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

/**
 * created by vlad on 07.07.19
 */
@Data
@ToString(callSuper = true)
public class SendDataToNodeRequest extends TransactionIdRequest {

    @JsonProperty("test_number")
    private String testNumber;
}
