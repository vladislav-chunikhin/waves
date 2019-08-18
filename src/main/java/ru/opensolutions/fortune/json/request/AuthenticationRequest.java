package ru.opensolutions.fortune.json.request;

import lombok.Data;

/**
 * pojo для запроса на авторизацию. */
@Data
public class AuthenticationRequest {
    /**
     * Логин пользователя. */
    private String login;
    /**
     * Пароль пользователя. */
    private String password;
}
