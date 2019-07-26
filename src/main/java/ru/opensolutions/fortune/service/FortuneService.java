package ru.opensolutions.fortune.service;

import lombok.NonNull;
import ru.opensolutions.fortune.api.WavesAPI;
import ru.opensolutions.fortune.api.WavesResponse;
import ru.opensolutions.fortune.common.AbstractWavesService;
import ru.opensolutions.fortune.json.request.SendDataToNodeRequest;
import ru.opensolutions.fortune.json.request.TransactionIdRequest;
import ru.opensolutions.fortune.json.response.SignDataResponse;
import ru.opensolutions.fortune.json.response.TransactionIdResponse;
import ru.opensolutions.fortune.model.SecurityAndWavesParams;
import ru.opensolutions.fortune.util.crypto.CryptographyComponent;
import ru.opensolutions.fortune.util.enums.FunctionType;
import com.wavesplatform.wavesj.*;
import com.wavesplatform.wavesj.transactions.InvokeScriptTransaction;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.PublicKey;
import java.util.Collections;

import static ru.opensolutions.fortune.util.enums.SignatureType.BASE58;
import static ru.opensolutions.fortune.util.enums.SignatureType.BASE64;

/**
 * Сервис для работы с RSA подписью.
 */
@Service
@RequiredArgsConstructor
public class FortuneService extends AbstractWavesService {

    private final SecurityAndWavesParams securityAndWavesParams;
    private final CryptographyComponent cryptographyComponent;
    private final ParamFillingTransactionService paramFillingTransactionService;
    private final DAppAddressService dAppAddressService;

    /**
     * Отправка подписи в теле транзакции в тестовую ноду.
     * @param request Тело запроса с номером теста и txId.
     * @return Объект, содержащий информацию об отправке в ноду подписи.
     */
    @SneakyThrows(URISyntaxException.class)
    public WavesResponse sendData(
            @NonNull final SendDataToNodeRequest request,
            @NonNull final String function) {
        final String methodName = "sendData";
        logStartMethod(methodName);

        final String nodeUri = this.securityAndWavesParams.getWalletNodeUri();
        final String seed = this.securityAndWavesParams.getSeed();
        final FunctionType functionType = FunctionType.getEnum(function);
        String dApp = this.dAppAddressService.setDAppValueByFunctionType(functionType);

        log(
                "NODE URI = {}" +
                        "\nDAPP ADDRESS = {}" +
                        "\nSEED = {}"
                , nodeUri, dApp, seed);

        final String testNumber = request.getTestNumber();
        final String txId = request.getTxId();
        final String signatureBase58 = this.cryptographyComponent.getSignatureAsString(txId, BASE58);
        final PublicKey publicKey = this.cryptographyComponent.getPublicKey();

        final Node node = new Node(nodeUri, TEST_CHAIN_ID);
        final PrivateKeyAccount account = PrivateKeyAccount.fromSeed(seed, 0, TEST_CHAIN_ID);
        final ByteString signatureAsByteString = new ByteString(signatureBase58);
        final ByteString publicKeyAsByteString = new ByteString(publicKey.getEncoded());

        final InvokeScriptTransaction tx = new InvokeScriptTransaction
                (
                        TEST_CHAIN_ID,
                        account,
                        dApp,
                        function,
                        FEE,
                        null,
                        TIMESTAMP
                );

        switch (functionType) {
            case WITHDRAW:
                this.paramFillingTransactionService.fillTxForWithdraw(
                        tx,
                        txId,
                        signatureAsByteString,
                        account);
                break;
            case CHECK_SIGN:
                this.paramFillingTransactionService.fillTxForCheckSign(
                        tx,
                        testNumber,
                        txId,
                        signatureAsByteString,
                        publicKeyAsByteString,
                        account);
                break;
            default:
                throw new IllegalArgumentException("Unexpected function type: "
                        .concat(functionType.getName()));
        }

        logTxAsPrettyJson(tx);
        final String txIdResponse;

        try {
            txIdResponse = node.send(tx);
        } catch (IOException ex) {
            error(ex.toString());
            return WavesAPI.negativeResponse(SERVER_ERROR_CODE, Collections.singletonList(ex.toString()));
        }

        log("txId = {}", txIdResponse);
        logEndMethod(methodName);
        return WavesAPI.positiveResponse(new TransactionIdResponse(txIdResponse));
    }

    /**
     * Метод для подписи данных.
     * @param request Тело запроса, которое содержит txId.
     * @return Объект, содержащий в себе подпись в формате base64.
     */
    public WavesResponse signData(@NonNull final TransactionIdRequest request) {
        final String methodName = "signData";
        logStartMethod(methodName);
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
        logEndMethod(methodName);
        return WavesAPI.positiveResponse(response);
    }
}
