package ru.opensolutions.fortune.service.security;

import ru.opensolutions.fortune.api.WavesResponse;
import ru.opensolutions.fortune.json.request.AuthenticationRequest;

/**
 * Сервис для авторизации пользователя в системе. */
public interface AuthenticationService {

    /**
     * Запрос на авторизацию.
     * @param authenticationRequest dto с логином и паролем пользователя.
     * @return dto с токеном доступа. */
    WavesResponse authenticationRequest(AuthenticationRequest authenticationRequest);
}
