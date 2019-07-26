package ru.opensolutions.fortune.controller;

import ru.opensolutions.fortune.api.WavesAPI;
import ru.opensolutions.fortune.api.WavesResponse;
import ru.opensolutions.fortune.common.AbstractLogger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.opensolutions.fortune.util.marker.SwaggerMarker;

import java.time.LocalDateTime;
import java.util.Collections;

/**
 * created by vlad on 18.07.19
 */
@RestController
@RequestMapping("/system")
@Api(tags ="Ресурс для проверки сервера")
@SwaggerMarker
public class SystemController extends AbstractLogger {

    @GetMapping("/time")
    @ApiOperation("Получение текущего времени сервера")
    @PreAuthorize("hasRole('ADMIN')")
    public WavesResponse getCurrentSystemTime() {
        return WavesAPI.positiveResponse(
                Collections.singletonMap("current_time", LocalDateTime.now()));
    }
}
