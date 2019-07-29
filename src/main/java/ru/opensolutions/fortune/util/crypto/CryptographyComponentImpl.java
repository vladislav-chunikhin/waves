package ru.opensolutions.fortune.util.crypto;

import com.wavesplatform.wavesj.Base58;
import com.wavesplatform.wavesj.Base64;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import ru.opensolutions.fortune.model.SecurityAndWavesParams;
import ru.opensolutions.fortune.util.log.AbstractLogger;
import lombok.SneakyThrows;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.stereotype.Component;
import ru.opensolutions.fortune.util.enums.SignatureType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.security.PublicKey;
import java.security.KeyFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Реализация интерфейса {@link CryptographyComponent} */
@Component
public class CryptographyComponentImpl extends AbstractLogger implements CryptographyComponent {

    @Autowired
    private SecurityAndWavesParams securityAndWavesParams;

    @Override
    public byte[] signDataByPrivateKey(final @NonNull String data) {
        final PrivateKey privateKey = getPrivateKey();
        byte[] result = new byte[0];

        try {
            final Signature sign = Signature.getInstance(
                    this.securityAndWavesParams.getSignatureAlgorithm());
            sign.initSign(privateKey);
            byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
            sign.update(bytes);
            result = sign.sign();
        } catch (NoSuchAlgorithmException ex) {
            error("Ошибка в выборе алгоритма подписи: {}", ex.toString());
        } catch (InvalidKeyException ex) {
            error("Ошибка в инициализации подписи приватным ключом: {}", ex.toString());
        } catch (SignatureException ex) {
            error("Ошибка в формировании подписи: {}", ex.toString());
        }
        return result;
    }

    @Override
    public void verifySignature(final @NonNull String data, @NonNull final byte[] signature) {
        try {
            final Signature sign = Signature.getInstance(
                    this.securityAndWavesParams.getSignatureAlgorithm());
            sign.initVerify(getPublicKey());
            sign.update(data.getBytes(StandardCharsets.UTF_8));

            if (sign.verify(signature)) {
                log("Подпись для данных = {} прошла верификацию.", data);
            } else {
                error("Подпись для данных = {} не прошла верификацию.", data);
            }
        } catch (NoSuchAlgorithmException ex) {
            error("Ошибка в выборе алгоритма подписи: {}", ex.toString());
        } catch (InvalidKeyException ex) {
            error("Ошибка в инициализации публичного ключа: {}", ex.toString());
        } catch (SignatureException ex) {
            error("Ошибка в формировании подписи: {}", ex.toString());
        }
    }

    @SneakyThrows({
            NoSuchAlgorithmException.class,
            InvalidKeySpecException.class}
    )
    @Override
    public PublicKey getPublicKey() {
        final PublicKey publicKey;
        final KeyFactory keyFactory = KeyFactory.getInstance(
                this.securityAndWavesParams
                        .getKeyFactoryAlgorithm()
                );
        publicKey = keyFactory.generatePublic(
                new X509EncodedKeySpec(getPemObjectFromFile(
                        this.securityAndWavesParams
                                        .getPublicKeyPath()
                                ).getContent()
                )
        );
        return publicKey;
    }

    @SneakyThrows({
            NoSuchAlgorithmException.class,
            InvalidKeySpecException.class}
    )
    @Override
    public PrivateKey getPrivateKey() {
        final PrivateKey privateKey;
        final KeyFactory keyFactory = KeyFactory.getInstance(
                this.securityAndWavesParams.
                                getKeyFactoryAlgorithm()
                );
        privateKey = keyFactory.generatePrivate(
                new PKCS8EncodedKeySpec(getPemObjectFromFile(
                          this.securityAndWavesParams
                                        .getPrivateKeyPath()
                                ).getContent()
                )
        );
        return privateKey;
    }

    @Override
    public PemObject getPemObjectFromFile(final @NonNull String path) {
        final PemReader pemReader;
        PemObject pemObject = null;
        try {
            final InputStream resourceAsStream = this.getClass().getResourceAsStream(path);
            try (final BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(resourceAsStream))) {
                pemReader = new PemReader(bufferedReader);
                pemObject = pemReader.readPemObject();
                pemReader.close();
            }
        } catch (IOException ex) {
            error("Ошибка при чтении файла {}", ex.toString());
        }
        return pemObject;
    }

    @Override
    public String getSignatureAsString(final @NonNull String data, final @NonNull SignatureType signatureType) {
        final byte[] signatureBytes = this.signDataByPrivateKey(data);
        this.verifySignature(data, signatureBytes);
        switch (signatureType) {
            case BASE58:
                final String signatureBase58 = Base58.encode(
                        signatureBytes
                );
                log("SIGNATURE58 = {}", signatureBase58);
                return signatureBase58;
            case BASE64:
                final String signatureBase64 = Base64.encode(
                        signatureBytes
                );
                log("SIGNATURE64 = {}", signatureBase64);
                return signatureBase64;
            default:
                throw new IllegalArgumentException(
                        "Unexpected signature type: ".concat(signatureType.name())
                );
        }
    }
}
