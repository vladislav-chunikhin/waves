package ru.opensolutions.fortune.configuration.validation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.opensolutions.fortune.util.MessageUtils;
import ru.opensolutions.fortune.util.enums.AuthOptionType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * Валидация настроек приложения. */
@Configuration
public class ValidationConfig {

    /**
     * Настройка по включению | выключению авторизации в проекте. */
    @Value("${auth.switch}")
    private String authSwitcher;

    /**
     * Проверка значений для файл свойств. */
    @Bean
    public void checkAppProperties() {
        final List<String> allOptions = Arrays
                .stream(AuthOptionType.values())
                .map(AuthOptionType::getName)
                .collect(Collectors.toList());
        final boolean isValidOption = allOptions.contains(authSwitcher);
        if (!isValidOption) {
            throw new IllegalArgumentException(
                    MessageUtils.getMessage("auth.switch.exception", allOptions, authSwitcher));
        }
    }
}
