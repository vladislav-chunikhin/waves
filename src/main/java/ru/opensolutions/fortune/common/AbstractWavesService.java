package ru.opensolutions.fortune.common;

import static com.wavesplatform.wavesj.Asset.toWavelets;

/**
 * Абстрактный класс для waves сервиса. */
public class AbstractWavesService extends AbstractLogger {
    protected static final long FEE = toWavelets(0.01);
    protected static final long TIMESTAMP = System.currentTimeMillis();
    protected static final int SERVER_ERROR_CODE = 500;
}
