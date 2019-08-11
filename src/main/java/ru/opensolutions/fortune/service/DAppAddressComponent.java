package ru.opensolutions.fortune.service;

import ru.opensolutions.fortune.util.enums.FunctionType;

/**
 * Комопнент для работы с dApp адресами. */
public interface DAppAddressComponent {

    /**
     * Получение dApp адреса в зависимости от типа требуемой функции.
     * @param functionType тип функции.
     * @return dApp адрес. */
    String getDAppValueByFunctionType(FunctionType functionType);
}
