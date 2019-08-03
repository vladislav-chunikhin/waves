package ru.opensolutions.fortune;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = {
        "classpath:application.properties",
        "classpath:waves.properties",
        "classpath:security.properties"
}, encoding = "UTF-8")
public class FortuneApplication {

    public static void main(String[] args) {
        SpringApplication.run(FortuneApplication.class, args);
    }

}
