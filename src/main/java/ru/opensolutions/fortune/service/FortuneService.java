package ru.opensolutions.fortune.service;

import ru.opensolutions.fortune.api.WavesResponse;
import ru.opensolutions.fortune.json.request.SendDataToNodeRequest;
import ru.opensolutions.fortune.json.request.TransactionIdRequest;

/**
 * Сервис для работы с RSA подписью и функциями игры Fortune. */
public interface FortuneService {

    /**
     * Отправка подписи в теле транзакции в тестовую ноду.
     * @param request Тело запроса с номером теста и txId.
     * @param function Наименование функции.
     * @return Объект, содержащий информацию об отправке в ноду подписи. */
    WavesResponse sendData(SendDataToNodeRequest request, String function);

    /**
     * Метод для подписи данных.
     * @param request Тело запроса, которое содержит txId.
     * @return Объект, содержащий в себе подпись в формате base64. */
    WavesResponse signData(TransactionIdRequest request);
}
