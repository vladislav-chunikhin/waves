package ru.opensolutions.fortune.controller;

import org.springframework.web.bind.annotation.*;
import ru.opensolutions.fortune.api.WavesResponse;
import ru.opensolutions.fortune.common.AbstractLogger;
import ru.opensolutions.fortune.json.request.AuthenticationRequest;
import ru.opensolutions.fortune.service.spring.security.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

/**
 * Ресурс для авторизации в модуль.
 */
@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
@Api(tags = "Ресурс для авторизации")
@CrossOrigin(origins = "http://localhost:50")
public class AuthController extends AbstractLogger {

    private final AuthenticationService authenticationService;

    @PostMapping("/auth")
    @ApiOperation(value = "Авторизация. Получение accessToken'a")
    public WavesResponse authenticationRequest(@RequestBody AuthenticationRequest authenticationRequest) {
        return authenticationService.authenticationRequest(authenticationRequest);
    }
}
