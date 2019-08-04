package ru.opensolutions.fortune.exception;

/**
 * Исключение при плохой сигнатуре токена. */
public class JwtBadSignatureException extends RuntimeException {
    public JwtBadSignatureException(String message) {
        super(message);
    }
}
