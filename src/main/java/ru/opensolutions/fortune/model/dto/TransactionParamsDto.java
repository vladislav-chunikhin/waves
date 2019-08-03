package ru.opensolutions.fortune.model.dto;

import com.wavesplatform.wavesj.ByteString;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;

/**
 * Dto для параметров необходимых для транзакции. */
@Builder
public class TransactionParamsDto {
    /**
     * Идентификатор транзакции. */
    @Nullable
    @Getter
    private String txId;
    /**
     * Номер теста. */
    @Nullable
    @Getter
    private String testNumber;
    /**
     * Подпись в виде строки. */
    @Nullable
    @Getter
    private ByteString signatureAsByteString;
    /**
     * Публичный ключ в виде строки. */
    @Nullable
    @Getter
    private ByteString publicKeyAsByteString;
    /**
     * Значение ставки. */
    @Nullable
    @Getter
    private String betValue;
}
