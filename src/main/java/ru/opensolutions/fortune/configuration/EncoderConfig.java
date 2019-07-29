package ru.opensolutions.fortune.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Криптография для spring security пароля. */
@Configuration
public class EncoderConfig {

    @Bean
    public PasswordEncoder userPasswordEncoder() {
        return new BCryptPasswordEncoder(SecurityParamsConfig.ENCODER_STRENGTH);
    }
}
