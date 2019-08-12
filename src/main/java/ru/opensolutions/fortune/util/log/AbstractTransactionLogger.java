package ru.opensolutions.fortune.util.log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wavesplatform.wavesj.Transaction;
import com.wavesplatform.wavesj.json.WavesJsonMapper;
import lombok.NonNull;
import lombok.SneakyThrows;
import ru.opensolutions.fortune.model.constants.FortuneConstants;
import ru.opensolutions.fortune.util.JsonUtils;

/**
 * Абстрактный класс для логирования транзакций для waves, расширяет основной {@link AbstractLogger}. */
public abstract class AbstractTransactionLogger extends AbstractLogger {
    /**
     * Логирование транзакции в красивом json формате.
     * @param tx Транзакция для waves. */
    @SneakyThrows(JsonProcessingException.class)
    protected void logTxAsPrettyJson(@NonNull final Transaction tx) {
        final WavesJsonMapper mapper = new WavesJsonMapper(FortuneConstants.TEST_CHAIN_ID);
        final String txJson = mapper.writeValueAsString(tx);
        this.log("Transaction json = {}", JsonUtils.getPrettyJson(txJson));
    }
}
