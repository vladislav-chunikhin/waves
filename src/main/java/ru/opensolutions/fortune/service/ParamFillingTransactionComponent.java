package ru.opensolutions.fortune.service;

import com.wavesplatform.wavesj.PrivateKeyAccount;
import com.wavesplatform.wavesj.transactions.InvokeScriptTransaction;
import ru.opensolutions.fortune.model.dto.TransactionParamsDto;

/**
 * Компонент для заполнения транзакции параметрами. */
public interface ParamFillingTransactionComponent {

    /**
     * @param tx объект транзакции.
     * @param dto dto параметров транзакции.
     * @param account аккаунт которым будем подписывать транзакцию. */
    void fillTxForCheckSign(
           InvokeScriptTransaction tx,
           TransactionParamsDto dto,
           PrivateKeyAccount account);

    /**
     * @param tx объект транзакции.
     * @param dto dto параметров транзакции.
     * @param account аккаунт которым будем подписывать транзакцию. */
    void fillTxForWithdraw(
           InvokeScriptTransaction tx,
           TransactionParamsDto dto,
           PrivateKeyAccount account
    );

    /**
     * @param tx объект транзакции.
     * @param dto dto параметров транзакции.
     * @param account аккаунт которым будем подписывать транзакцию. */
    void fillTxForBet(
           InvokeScriptTransaction tx,
           TransactionParamsDto dto,
           PrivateKeyAccount account
    );
}
