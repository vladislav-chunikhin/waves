package ru.opensolutions.fortune.controller;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ru.opensolutions.fortune.api.WavesResponse;
import ru.opensolutions.fortune.service.FortuneService;
import ru.opensolutions.fortune.util.log.AbstractLogger;
import ru.opensolutions.fortune.json.request.SendDataToNodeRequest;
import ru.opensolutions.fortune.json.request.TransactionIdRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.opensolutions.fortune.util.marker.SwaggerMarker;
import ru.opensolutions.fortune.util.regex.UriMapperRegex;

import static ru.opensolutions.fortune.util.JsonUtils.getJsonFromObject;

/**
 * Ресурс для waves rsa подписи. */
@RestController
@RequestMapping("/rsa")
@Api(tags = "Ресурс для работы с идентификатором транзакции")
@SwaggerMarker
public class FortuneController extends AbstractLogger {

    /**
     * Сервис для работы с RSA подписью и функциями игры Fortune. */
    @Autowired
    private FortuneService fortuneService;

    /**
     * Ручка для отправки идентификатора транзакции в waves node.
     * @param req тело запрсоа с txId (идентификатор транзакции).
     * @param function наименование вызываемой функции.
     * @return txId в json формате. */
    @PostMapping("/{function:" + UriMapperRegex.FUNCTION + "}/send")
    @ApiOperation(value = "Отправка транзакции в тестовую ноду waves")
    @PreAuthorize("hasRole('ADMIN')")
    public WavesResponse sendData(
            @RequestBody @ApiParam(value = "Тело запроса")
            @NonNull final SendDataToNodeRequest req,
            @PathVariable(value = "function")
            @ApiParam(value = "Тип функции.\nСписок используемых функций: " + UriMapperRegex.FUNCTION)
            @NonNull final String function
    )
    {
        this.log("SEND DATA REQUEST = {}",
                getJsonFromObject(req, true),
                String.format("\nТип функции: %s", function));
        return this.fortuneService.sendData(req, function);
    }

    /**
     * Тестовая ручка для проверка подписи данных.
     * @param req тело запрсоа с txId (идентификатор транзакции).
     * @return сигнатура подписанная парой ключей. */
    @PostMapping("/sign")
    @ApiOperation(value = "Тест RSA. Подпись данных")
    @PreAuthorize("hasRole('ADMIN')")
    public WavesResponse signData(
            @RequestBody @ApiParam(value = "Тело запроса")
            @NonNull final TransactionIdRequest req
    )
    {
        this.log("METHOD SIGN DATA REQUEST = {}", getJsonFromObject(req, true));
        return this.fortuneService.signData(req);
    }
}
