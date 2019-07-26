package ru.opensolutions.fortune.model;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Компонент настроек для waves аккаунтов и java security пакета. */
@Component
public class SecurityAndWavesParams {

    @Value("${waves.node.uri}")
    @Getter
    private String walletNodeUri;

    @Value("${waves.dApp.checkSign}")
    @Getter
    private String dAppCheckSign;

    @Value("${waves.dApp.withdraw}")
    @Getter
    private String dAppWithdraw;

    @Value("${waves.seed}")
    @Getter
    private String seed;

    @Value("${security.signature.algorithm}")
    @Getter
    private String signatureAlgorithm;

    @Value("${security.keyfactory.algorithm}")
    @Getter
    private String keyFactoryAlgorithm;

    @Value("${security.privatekey.path}")
    @Getter
    private String privateKeyPath;

    @Value("${security.publickey.path}")
    @Getter
    private String publicKeyPath;
}
