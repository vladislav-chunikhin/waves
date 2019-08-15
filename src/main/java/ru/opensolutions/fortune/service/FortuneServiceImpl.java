package ru.opensolutions.fortune.service;

import lombok.NonNull;
import ru.opensolutions.fortune.api.WavesAPI;
import ru.opensolutions.fortune.api.WavesResponse;
import ru.opensolutions.fortune.json.request.SendDataToNodeRequest;
import ru.opensolutions.fortune.json.request.TransactionIdRequest;
import ru.opensolutions.fortune.json.response.SignDataResponse;
import ru.opensolutions.fortune.json.response.TransactionIdResponse;
import ru.opensolutions.fortune.model.SecurityAndWavesParams;
import ru.opensolutions.fortune.model.constants.FortuneConstants;
import ru.opensolutions.fortune.model.dto.TransactionParamsDto;
import ru.opensolutions.fortune.service.crypto.CryptographyComponent;
import ru.opensolutions.fortune.util.enums.FunctionType;
import com.wavesplatform.wavesj.*;
import com.wavesplatform.wavesj.transactions.InvokeScriptTransaction;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.opensolutions.fortune.util.log.AbstractTransactionLogger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.PublicKey;

import static ru.opensolutions.fortune.model.constants.FortuneConstants.FEE;
import static ru.opensolutions.fortune.model.constants.FortuneConstants.TIMESTAMP;
import static ru.opensolutions.fortune.model.constants.FortuneConstants.SERVER_ERROR_CODE;
import static ru.opensolutions.fortune.util.enums.SignatureType.BASE58;
import static ru.opensolutions.fortune.util.enums.SignatureType.BASE64;

/**
 * Имплементация {@link FortuneService}. */
@Service
@RequiredArgsConstructor
public class FortuneServiceImpl extends AbstractTransactionLogger implements FortuneService {

    /**
     * Компонент настроек для waves аккаунтов и java security пакета. */
    private final SecurityAndWavesParams securityAndWavesParams;
    /**
     * Интерфейс, необходимый для формирования подписи
     * и работы с приватными и публичными ключами. */
    private final CryptographyComponent cryptographyComponent;
    /**
     * Компонент для заполнения транзакции параметрами. */
    private final ParamFillingTransactionComponent paramFillingTransactionComponent;
    /**
     * Комопнент для работы с dApp адресами. */
    private final DAppAddressComponent dAppAddressComponent;

    @SneakyThrows(URISyntaxException.class)
    @Override
    public WavesResponse sendData(
            @NonNull final SendDataToNodeRequest request,
            @NonNull final String function) {
        final String methodName = "sendData";
        this.logStartMethod(methodName);

        final String nodeUri = this.securityAndWavesParams.getWalletNodeUri();
        final String seed = this.securityAndWavesParams.getSeed();
        final FunctionType functionType = FunctionType.getEnum(function);
        final String dApp = this.dAppAddressComponent.getDAppValueByFunctionType(functionType);

        this.log(
                "NODE URI = {}" +
                        "\nDAPP ADDRESS = {}" +
                        "\nSEED = {}"
                , nodeUri, dApp, seed);

        final String testNumber = request.getTestNumber();
        final String txId = request.getTxId();
        final String betValue = request.getBetValue();
        final String signatureBase58 = this.cryptographyComponent.getSignatureAsString(txId, BASE58);
        final PublicKey publicKey = this.cryptographyComponent.getPublicKey();

        final Node node = new Node(nodeUri, FortuneConstants.TEST_CHAIN_ID);
        final PrivateKeyAccount account = PrivateKeyAccount.fromSeed(seed, 0, FortuneConstants.TEST_CHAIN_ID);
        final ByteString signatureAsByteString = new ByteString(signatureBase58);
        final ByteString publicKeyAsByteString = new ByteString(publicKey.getEncoded());

        final InvokeScriptTransaction tx = new InvokeScriptTransaction
                (
                        FortuneConstants.TEST_CHAIN_ID,
                        account,
                        dApp,
                        function,
                        FEE,
                        null,
                        TIMESTAMP
                );

        switch (functionType) {
            case WITHDRAW:
                this.paramFillingTransactionComponent.fillTxForWithdraw(
                        tx,
                        TransactionParamsDto
                                .builder()
                                .txId(txId)
                                .signatureAsByteString(signatureAsByteString)
                                .build(),
                        account);
                break;
            case CHECK_SIGN:
                this.paramFillingTransactionComponent.fillTxForCheckSign(
                        tx,
                        TransactionParamsDto
                                .builder()
                                .testNumber(testNumber)
                                .txId(txId)
                                .signatureAsByteString(signatureAsByteString)
                                .publicKeyAsByteString(publicKeyAsByteString)
                                .build(),
                        account);
                break;
            case BET:
                this.paramFillingTransactionComponent.fillTxForBet(
                        tx,
                        TransactionParamsDto
                                .builder()
                                .betValue(betValue)
                                .build(),
                        account);
                break;
            default:
                throw new IllegalArgumentException("Unexpected function type: "
                        .concat(functionType.getName()));
        }

        this.logTxAsPrettyJson(tx);
        final String txIdResponse;

        try {
            txIdResponse = node.send(tx);
        } catch (IOException ex) {
            this.error(ex.toString());
            return WavesAPI.negativeResponse(SERVER_ERROR_CODE, ex.toString());
        }

        this.log("txId = {}", txIdResponse);
        this.logEndMethod(methodName);
        return WavesAPI.positiveResponse(new TransactionIdResponse(txIdResponse));
    }

    @Override
    public WavesResponse signData(@NonNull final TransactionIdRequest request) {
        final String methodName = "signData";
        this.logStartMethod(methodName);
        final String txId = request.getTxId();

        final PublicKey publicKey = this.cryptographyComponent.getPublicKey();
        final String publicKeyStr = Base64.encode(
                publicKey.getEncoded()
        );
        final SignDataResponse response = new SignDataResponse(
                txId,
                this.cryptographyComponent.getSignatureAsString(request.getTxId(), BASE64),
                publicKeyStr
        );
        this.logEndMethod(methodName);
        return WavesAPI.positiveResponse(response);
    }
}
