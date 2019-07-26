package ru.opensolutions.fortune.service;

import com.wavesplatform.wavesj.PrivateKeyAccount;
import com.wavesplatform.wavesj.transactions.InvokeScriptTransaction;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import ru.opensolutions.fortune.model.TransactionParamsDto;

import static com.wavesplatform.wavesj.Asset.toWavelets;

/**
 * Сервис для заполнения транзакции параметрами. */
@Service
class ParamFillingTransactionService {

    private static final Long PAYMENT_AMT = toWavelets(0.001);

    void fillTxForCheckSign(
            @NonNull final InvokeScriptTransaction tx,
            @NonNull final TransactionParamsDto dto,
            @NonNull final PrivateKeyAccount account) {
        tx
                .withArg(dto.getTestNumber())
                .withArg(dto.getTxId())
                .withArg(dto.getSignatureAsByteString())
                .withArg(dto.getPublicKeyAsByteString())
                .withPayment(PAYMENT_AMT, null)
                .sign(account);
    }

    void fillTxForWithdraw(
           @NonNull final InvokeScriptTransaction tx,
           @NonNull final TransactionParamsDto dto,
           @NonNull final PrivateKeyAccount account
    ) {
        tx
                .withArg(dto.getTxId())
                .withArg(dto.getSignatureAsByteString())
                .sign(account);
    }

    void fillTxForBet(
            @NonNull final InvokeScriptTransaction tx,
            @NonNull final TransactionParamsDto dto,
            @NonNull final PrivateKeyAccount account
    ) {
        tx
                .withArg(dto.getBetValue())
                .withPayment(PAYMENT_AMT, null)
                .sign(account);
    }
}
