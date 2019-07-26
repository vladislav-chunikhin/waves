package ru.opensolutions.fortune.json.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * created by vlad on 21.07.19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    @JsonProperty("access_token")
    private String accessToken;
}
