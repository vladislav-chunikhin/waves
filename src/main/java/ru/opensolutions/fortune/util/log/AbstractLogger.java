package ru.opensolutions.fortune.util.log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wavesplatform.wavesj.Account;
import com.wavesplatform.wavesj.Transaction;
import com.wavesplatform.wavesj.json.WavesJsonMapper;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.opensolutions.fortune.util.JsonUtils;

/**
 * Абстрактный класс для логирования. */
public abstract class AbstractLogger {

    /**
     * Тестовая сеть блокчейна. */
    protected static final byte TEST_CHAIN_ID = Account.TESTNET;
    /**
     * Главная сеть блокчейна. */
    protected static final byte MAIN_CHAIN_ID = Account.MAINNET;

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

    /**
     * Логирование транзакции в красивом json формате.
     * @param tx Транзакция для waves. */
    @SneakyThrows(JsonProcessingException.class)
    protected void logTxAsPrettyJson(@NonNull final Transaction tx) {
        final WavesJsonMapper mapper = new WavesJsonMapper(TEST_CHAIN_ID);
        final String txJson = mapper.writeValueAsString(tx);
        log("Transaction json = {}", JsonUtils.getPrettyJson(txJson));
    }
}
