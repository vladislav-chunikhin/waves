package ru.opensolutions.fortune.model.constants;

import com.wavesplatform.wavesj.Account;

import static com.wavesplatform.wavesj.Asset.toWavelets;

/**
 * Константы для сервиса Fortune. */
public class FortuneConstants {
    /**
     * Запрещаем создавать эксземпляр данного класса. */
    private FortuneConstants() {
        throw new RuntimeException();
    }

    //todo скорее всего нужно переделать, уточнить у Виталия про коммисию. Она должна рассчитываться
    /**
     * Коммисия за транзакцию. */
    public static final long FEE = toWavelets(0.01);
    /**
     * Текущее время на сервере в формате timestamp.*/
    public static final long TIMESTAMP = System.currentTimeMillis();
    /**
     * Общий код ошибок. */
    public static final int SERVER_ERROR_CODE = 500;

    /**
     * Тестовая сеть блокчейна. */
    public static final byte TEST_CHAIN_ID = Account.TESTNET;
    /**
     * Главная сеть блокчейна. */
    public static final byte MAIN_CHAIN_ID = Account.MAINNET;
}
