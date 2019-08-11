package ru.opensolutions.fortune.util.log;

import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Абстрактный класс для логирования. */
public abstract class AbstractLogger {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected void log(@NonNull final String format, @NonNull final Object... arguments) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("\n".concat(format), arguments);
        }
    }

    protected void logStartMethod(@NonNull final Object... arguments) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("\n************************ METHOD {} START ************************", arguments);
        }
    }

    protected void logEndMethod(@NonNull final Object... arguments) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("\n************************ METHOD {} END **************************", arguments);
        }
    }

    protected void error(@NonNull final String format, @NonNull final Object... arguments) {
        this.logger.error("\n".concat(format), arguments);
    }


}
