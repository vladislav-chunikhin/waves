package ru.opensolutions.fortune.exception;

/**
 * Исключение при валидации токена. */
public class JwtExpirationException extends RuntimeException {
    public JwtExpirationException(String message) {
        super(message);
    }
}
