package ru.opensolutions.fortune.configuration;

/**
 * Параметры для настройки spring security и jwt. */
public interface SecurityParamsConfig {
    String ROLES_CLAIM = "roles";
    String AUDIENCE_WEB = "web";
    String LOGIN_CLAIM = "login";
    Integer ENCODER_STRENGTH = 11;
    String PREFIX_AUTH_HEADER = "Bearer ";
    String SECRET_FILE = "/secret.key";
    String[] NONE_SECURITY = new String[] {
            "/fortune/api/v2/api-docs",
            "/fortune/api/swagger-resources/**",
            "/fortune/api/swagger-ui.html/**",
            "/fortune/api/webjars/**",
            "/fortune/api/favicon.ico",
            "/fortune/api/data/media/**"
    };
    String AUTH_URL = "/fortune/api/security/auth";
    Integer EXPIRATION_IN_MINUTES_FOR_ACCESS_TOKEN = 1440;
}
