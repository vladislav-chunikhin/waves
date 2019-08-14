package ru.opensolutions.fortune.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.opensolutions.fortune.model.constants.SecurityConstants;

/**
 * Криптография для spring security пароля. */
@Configuration
public class EncoderConfig {

    /**
     * Настройка для хеширования пароля.
     * @return {@link PasswordEncoder}. */
    @Bean
    public PasswordEncoder userPasswordEncoder() {
        return new BCryptPasswordEncoder(SecurityConstants.ENCODER_STRENGTH);
    }
}
