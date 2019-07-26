package ru.opensolutions.fortune.json.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * created by vlad on 21.07.19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    private String login;
    private String password;
}
