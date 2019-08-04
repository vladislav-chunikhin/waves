package ru.opensolutions.fortune.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.opensolutions.fortune.model.SecurityAndWavesParams;
import ru.opensolutions.fortune.service.interfaces.DAppAddressService;
import ru.opensolutions.fortune.util.enums.FunctionType;

/**
 * Имплементация {@link DAppAddressService}. */
@Service
@RequiredArgsConstructor
class DAppAddressServiceImpl implements DAppAddressService {

    private final SecurityAndWavesParams securityAndWavesParams;

    public String getDAppValueByFunctionType(@NonNull final FunctionType functionType) {
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
