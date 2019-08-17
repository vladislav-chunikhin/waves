package ru.opensolutions.fortune.controller;

import io.swagger.annotations.ApiParam;
import lombok.NonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import ru.opensolutions.fortune.api.WavesResponse;
import ru.opensolutions.fortune.util.log.AbstractLogger;
import ru.opensolutions.fortune.json.request.AuthenticationRequest;
import ru.opensolutions.fortune.service.security.AuthenticationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Ресурс для авторизации в модуль. */
@RestController
@RequestMapping("/security")
@Api(tags = "Ресурс для авторизации")
public class AuthController extends AbstractLogger {

    /**
     * Сервис для авторизации пользователя в системе. */
    @Autowired
    private AuthenticationService authenticationService;

    /**
     * @param authenticationRequest запрос на авторизацию.
     * @return pojo общего ответа с токеном доступа. */
    @PostMapping("/auth")
    @ApiOperation(value = "Авторизация. Получение accessToken'a")
    public WavesResponse authenticationRequest(
            @RequestBody
            @ApiParam("Тело запроса")
            @NonNull final AuthenticationRequest authenticationRequest)
    {
        return authenticationService.authenticationRequest(authenticationRequest);
    }
}
