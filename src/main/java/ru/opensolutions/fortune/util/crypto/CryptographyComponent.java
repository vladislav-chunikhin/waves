package ru.opensolutions.fortune.util.crypto;

import lombok.NonNull;
import org.bouncycastle.util.io.pem.PemObject;
import ru.opensolutions.fortune.util.enums.SignatureType;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Интерфейс, необходимый для формирования подписи
 * и работы с приватными и публичными ключами. */
public interface CryptographyComponent {

    /**
     * Подписание данных приватным ключом.
     * @param data Данные, которые необходимо подписать приватным ключом.
     * @return Сформированная подпись в виде массива байтов. */
    byte[] signDataByPrivateKey(@NonNull final String data);

    /**
     * Верификация подписи.
     * @param data Данные, которые необходимо проверить.
     * @param signature Подпись для верификации. */
    void verifySignature(@NonNull final String data, @NonNull byte[] signature);

    /**
     * Получение публичного ключа.
     * @return Объект публичного ключа. */
    PublicKey getPublicKey();

    /**
     * Получение приватного ключа.
     * @return Объект приватного ключа. */
    PrivateKey getPrivateKey();

    /**
     * Получение PEM объекта из файла.
     * @param path Путь ресурса, где находится ключ.
     * @return Объект в формате PEM, который используется в спеке
     * и преобразуется в необходимый объект. */
    PemObject getPemObjectFromFile(@NonNull final String path);

    /**
     * Получение подписи в виде строки.
     * @param data Данные для подписи.
     * @param signatureType Тип подписи.
     * @return Подпись. */
    String getSignatureAsString(@NonNull final String data, @NonNull final SignatureType signatureType);
}
