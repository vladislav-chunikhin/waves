package ru.opensolutions.fortune.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.opensolutions.fortune.model.SecurityAndWavesParams;
import ru.opensolutions.fortune.util.enums.FunctionType;

/**
 * Сервис для работы с dApp адресами. */
@Service
class DAppAddressService {

    @Autowired
    private SecurityAndWavesParams securityAndWavesParams;

    /**
     * Получение dApp адреса в зависимости от типа требуемой функции.
     * @param functionType тип функции.
     * @return dApp адрес. */
    String getDAppValueByFunctionType(@NonNull final FunctionType functionType) {
        switch (functionType) {
            case CHECK_SIGN:
                return this.securityAndWavesParams.getDAppCheckSign();
            case WITHDRAW:
                return this.securityAndWavesParams.getDAppWithdraw();
            case BET:
                return this.securityAndWavesParams.getDAppBet();
            default:
                throw new IllegalArgumentException("Unexpected function type: "
                        .concat(functionType.getName()));
        }
    }
}
