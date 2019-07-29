package ru.opensolutions.fortune.model.constants;

import static com.wavesplatform.wavesj.Asset.toWavelets;

/**
 * Константы для сервиса Fortune. */
public class FortuneConstants {
    /**
     * Запрещаем создавать эксземпляр данного класса. */
    private FortuneConstants() {
        throw new RuntimeException();
    }
    /**
     * Налог за транзакцию. */
    public static final long FEE = toWavelets(0.01);
    /**
     * Текущее время на сервере в формате timestamp.*/
    public static final long TIMESTAMP = System.currentTimeMillis();
    /**
     * Общий код ошибок. */
    public static final int SERVER_ERROR_CODE = 500;
}
