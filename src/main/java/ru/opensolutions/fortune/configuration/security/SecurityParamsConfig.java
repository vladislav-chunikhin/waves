package ru.opensolutions.fortune.configuration.security;

/**
 * Параметры для настройки spring security и jwt. */
public interface SecurityParamsConfig {
    /**
     * Ключ jwt для ролей пользователя. */
    String ROLES_CLAIM = "roles";
    /**
     * Тип апи. */
    String AUDIENCE_WEB = "web";
    /**
     * Ключ jwt для логина пользователя. */
    String LOGIN_CLAIM = "login";
    /**
     * Настройка для хэша пароля. */
    Integer ENCODER_STRENGTH = 11;
    /**
     * Префикс хедера авторизации. */
    String PREFIX_AUTH_HEADER = "Bearer ";
    /**
     * Путь к секретному ключу. */
    String SECRET_FILE = "/secret.key";
    /**
     * Список путей которые не требуют авторизации. */
    String[] NONE_SECURITY = new String[] {
            "/fortune/api/v2/api-docs",
            "/fortune/api/swagger-resources/**",
            "/fortune/api/swagger-ui.html/**",
            "/fortune/api/webjars/**",
            "/fortune/api/favicon.ico",
            "/fortune/api/data/media/**"
    };
    /**
     * Путь к апи для авторизации. */
    String AUTH_URL = "/fortune/api/security/auth";
    /**
     * Время жизни токена доступа. */
    Integer EXPIRATION_IN_MINUTES_FOR_ACCESS_TOKEN = 1440;
}
