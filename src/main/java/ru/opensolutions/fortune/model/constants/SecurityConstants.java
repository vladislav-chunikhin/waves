package ru.opensolutions.fortune.model.constants;

/**
 * Параметры для настройки spring security и jwt. */
public final class SecurityConstants {

    /**
     * Запрещаем создавать эксземпляр данного класса. */
    private SecurityConstants() {
        throw new RuntimeException();
    }

    /**
     * Ключ jwt для ролей пользователя. */
    public static final String ROLES_CLAIM = "roles";
    /**
     * Тип апи. */
    public static final String AUDIENCE_WEB = "web";
    /**
     * Ключ jwt для логина пользователя. */
    public static final String LOGIN_CLAIM = "login";
    /**
     * Настройка для хэша пароля. */
    public static final Integer ENCODER_STRENGTH = 11;
    /**
     * Префикс хедера авторизации. */
    public static final String PREFIX_AUTH_HEADER = "Bearer ";
    /**
     * Путь к секретному ключу. */
    public static final String SECRET_FILE = "/secret.key";
    /**
     * Список путей которые не требуют авторизации. */
    public static final String[] NONE_SECURITY = new String[] {
            "/fortune/api/v2/api-docs",
            "/fortune/api/swagger-resources/**",
            "/fortune/api/swagger-ui.html/**",
            "/fortune/api/webjars/**",
            "/fortune/api/favicon.ico",
            "/fortune/api/data/media/**"
    };
    /**
     * Путь к апи для авторизации. */
    public static final String AUTH_URL = "/fortune/api/security/auth";
    /**
     * Время жизни токена доступа. */
    public static final Integer EXPIRATION_IN_MINUTES_FOR_ACCESS_TOKEN = 1440;
    /**
     * Погрешность во времени для истечения время жизни токена в секундах.
     */
    public static final Long SKEW = 60L;
    /**
     * Роль пользователя по умолчанию. */
    public static final String DEFAULT_ROLE = "ROLE_ADMIN";
    /**
     * Логин пользователя по умолчанию. */
    public static final String DEFAULT_LOGIN = "open-solutions";
    /**
     * Пароль пользователя по умолчанию. */
    public static final String DEFAULT_PASSWORD = "$2a$11$C4CfCC2tED2TO/hh.RYgdu0n8R7McommC99vNH3S5EtExnjIeY3Wq";
    /**
     * Роль пользователя по умолчанию. */
    public static final String ADMIN_ROLE = "admin";
}
