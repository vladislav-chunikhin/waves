package ru.opensolutions.fortune.service;

import com.wavesplatform.wavesj.ByteString;
import com.wavesplatform.wavesj.PrivateKeyAccount;
import com.wavesplatform.wavesj.transactions.InvokeScriptTransaction;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import static com.wavesplatform.wavesj.Asset.toWavelets;

/**
 * Сервис для заполнения транзакции параметрами. */
@Service
class ParamFillingTransactionService {

    private static final Long PAYMENT_AMT = toWavelets(0.01);

    void fillTxForCheckSign(
            @NonNull final InvokeScriptTransaction tx,
            @NonNull final String testNumber,
            @NonNull final String txId,
            @NonNull final ByteString signatureAsByteString,
            @NonNull final ByteString publicKeyAsByteString,
            @NonNull final PrivateKeyAccount account) {
        tx
                .withArg(testNumber)
                .withArg(txId)
                .withArg(signatureAsByteString)
                .withArg(publicKeyAsByteString)
                .withPayment(PAYMENT_AMT, null)
                .sign(account);
    }

    void fillTxForWithdraw(
           @NonNull final InvokeScriptTransaction tx,
           @NonNull final String txId,
           @NonNull final ByteString signatureAsByteString,
           @NonNull final PrivateKeyAccount account
    ) {
        tx
                .withArg(txId)
                .withArg(signatureAsByteString)
                .sign(account);
    }
}
