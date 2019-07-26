package ru.opensolutions.fortune.configuration.validation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

import static java.lang.String.format;

/**
 * Настройка для валидации настроек приложения.
 */
@Configuration
public class ValidationConfig {

    @Value("${auth.switch}")
    private String authSwitcher;

    @Bean
    public void checkAppProperties(){
        if (!Arrays.asList("on", "off").contains(authSwitcher)) {
            throw new IllegalArgumentException(format(
                    "Параметр настройки auth.switch может принимать только значения on или off." +
                            "Текущее значение: %s", authSwitcher));
        }
    }
}
