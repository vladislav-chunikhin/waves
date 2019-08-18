package ru.opensolutions.fortune.util.log;

import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Абстрактный класс для логирования. */
public abstract class AbstractLogger {

    /**
     * Основной логгер. */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @param format формат сообщения.
     * @param arguments параметры.
     */
    protected void log(@NonNull final String format, @NonNull final Object... arguments) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("\n".concat(format), arguments);
        }
    }

    /**
     * @param arguments параметры.
     */
    protected void logStartMethod(@NonNull final Object... arguments) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("\n************************ METHOD {} START ************************", arguments);
        }
    }

    /**
     * @param arguments параметры.
     */
    protected void logEndMethod(@NonNull final Object... arguments) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("\n************************ METHOD {} END **************************", arguments);
        }
    }

    /**
     * @param format формат сообщения.
     * @param arguments параметры.
     */
    protected void error(@NonNull final String format, @NonNull final Object... arguments) {
        this.logger.error("\n".concat(format), arguments);
    }


}
