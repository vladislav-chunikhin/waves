package ru.opensolutions.fortune.model;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Компонент настроек для waves аккаунтов и java security пакета. */
@Component
public class SecurityAndWavesParams {

    /**
     * Адрес тестовой ноды. */
    @Value("${waves.node.uri}")
    @Getter
    private String walletNodeUri;

    /**
     * dApp адресс аккаунта для функции checkSign. */
    @Value("${waves.dApp.checkSign}")
    @Getter
    private String dAppCheckSign;

    /**
     * dApp адресс аккаунта для функции withdraw. */
    @Value("${waves.dApp.withdraw}")
    @Getter
    private String dAppWithdraw;

    /**
     * dApp адресс аккаунта для функции bet. */
    @Value("${waves.dApp.bet}")
    @Getter
    private String dAppBet;

    /**
     * Seed аккаунта. */
    @Value("${waves.seed}")
    @Getter
    private String seed;

    /**
     * Алгоритм подписи. */
    @Value("${security.signature.algorithm}")
    @Getter
    private String signatureAlgorithm;

    /**
     * Алгоритм фабрики ключей. */
    @Value("${security.keyfactory.algorithm}")
    @Getter
    private String keyFactoryAlgorithm;

    /**
     * Путь к приватнуму ключу. */
    @Value("${security.privatekey.path}")
    @Getter
    private String privateKeyPath;

    /**
     * Путь к публичному ключу. */
    @Value("${security.publickey.path}")
    @Getter
    private String publicKeyPath;
}
