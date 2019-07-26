package ru.opensolutions.fortune.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.opensolutions.fortune.api.WavesResponse;
import ru.opensolutions.fortune.common.AbstractLogger;
import ru.opensolutions.fortune.json.request.SendDataToNodeRequest;
import ru.opensolutions.fortune.json.request.TransactionIdRequest;
import ru.opensolutions.fortune.service.FortuneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.opensolutions.fortune.util.marker.SwaggerMarker;
import ru.opensolutions.fortune.util.regex.UriMapperRegex;

/**
 * Ресурс для waves rsa подписи. */
@RestController
@RequestMapping("/rsa")
@Api(tags = "Ресурс для работы с идентификатором транзакции")
@SwaggerMarker
public class FortuneController extends AbstractLogger {

    @Autowired
    private FortuneService fortuneService;

    @PostMapping("/{function:" + UriMapperRegex.FUNCTION + "}/send")
    @ApiOperation(value = "Отправка транзакции в тестовую ноду waves")
    @PreAuthorize("hasRole('ADMIN')")
    public WavesResponse sendData(
            @RequestBody @ApiParam(value = "Тело запроса") final SendDataToNodeRequest req,
            @PathVariable(value = "function")
            @ApiParam(value = "Тип функции.\nСписок используемых функций: " + UriMapperRegex.FUNCTION) final String function
    ) {
        log("SEND DATA REQUEST = {}", req, String.format("\nТип функции: %s", function));
        return fortuneService.sendData(req, function);
    }

    @PostMapping("/sign")
    @ApiOperation(value = "Тест RSA. Подпись данных")
    @PreAuthorize("hasRole('ADMIN')")
    public WavesResponse signData(@RequestBody @ApiParam(value = "Тело запроса") final TransactionIdRequest req) {
        log("METHOD SIGN DATA REQUEST = {}", req);
        return fortuneService.signData(req);
    }
}
