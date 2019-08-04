package ru.opensolutions.fortune.controller;

import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.opensolutions.fortune.api.WavesResponse;
import ru.opensolutions.fortune.util.log.AbstractLogger;
import ru.opensolutions.fortune.json.request.AuthenticationRequest;
import ru.opensolutions.fortune.service.interfaces.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Ресурс для авторизации в модуль. */
@RestController
@RequestMapping("/security")
@Api(tags = "Ресурс для авторизации")
public class AuthController extends AbstractLogger {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/auth")
    @ApiOperation(value = "Авторизация. Получение accessToken'a")
    public WavesResponse authenticationRequest(
            @RequestBody
            @ApiParam("Тело запроса")
            @NonNull final AuthenticationRequest authenticationRequest) {
        return authenticationService.authenticationRequest(authenticationRequest);
    }
}
