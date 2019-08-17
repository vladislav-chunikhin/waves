package ru.opensolutions.fortune.configuration;

import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Общая настройка для деплоя на сервак. */
@Configuration
public class CommonConfig {

    /**
     * @return объект {@link ApplicationConversionService}. */
    @Bean
    ApplicationConversionService applicationConversionService() {
        return new ApplicationConversionService();
    }
}
