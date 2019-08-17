package ru.opensolutions.fortune;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * Главный класс для SpringBootApplication. */
@SpringBootApplication
@PropertySource(value = {
        "classpath:application.properties",
        "classpath:waves.properties",
        "classpath:security.properties",
        "classpath:message.properties"
}, encoding = "UTF-8")
public class FortuneApplication {

    /**
     * @param args аргументы для запуска приложения. */
    public static void main(final String[] args) {
        SpringApplication.run(FortuneApplication.class, args);
    }

}
