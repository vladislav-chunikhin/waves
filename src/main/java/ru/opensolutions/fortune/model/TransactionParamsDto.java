package ru.opensolutions.fortune.model;

import com.wavesplatform.wavesj.ByteString;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

/**
 * Dto для параметров необходимых для транзакции.
 */
@Data
@Builder
public class TransactionParamsDto {
    @Nullable private String txId;
    @Nullable private String testNumber;
    @Nullable private ByteString signatureAsByteString;
    @Nullable private ByteString publicKeyAsByteString;
    @Nullable private String betValue;
}
