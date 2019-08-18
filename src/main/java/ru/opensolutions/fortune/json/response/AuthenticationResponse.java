package ru.opensolutions.fortune.json.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * pojo для ответа авторизации.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    /**
     * Токен доступа в строком представлении. */
    @JsonProperty("access_token")
    private String accessToken;
}
