package ru.opensolutions.fortune.service.interfaces;

import ru.opensolutions.fortune.util.enums.FunctionType;

/**
 * Сервис для работы с dApp адресами. */
public interface DAppAddressService {

    /**
     * Получение dApp адреса в зависимости от типа требуемой функции.
     * @param functionType тип функции.
     * @return dApp адрес. */
    String getDAppValueByFunctionType(FunctionType functionType);
}
