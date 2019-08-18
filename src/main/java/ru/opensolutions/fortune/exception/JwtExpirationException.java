package ru.opensolutions.fortune.exception;

/**
 * Исключение при валидации токена. */
public class JwtExpirationException extends RuntimeException {
    /**
     * @param message сообщение ошибки. */
    public JwtExpirationException(final String message) {
        super(message);
    }
}
