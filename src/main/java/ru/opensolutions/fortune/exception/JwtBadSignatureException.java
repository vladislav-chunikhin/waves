package ru.opensolutions.fortune.exception;

/**
 * Исключение при плохой сигнатуре токена. */
public class JwtBadSignatureException extends RuntimeException {

    /**
     * @param message сообщение ошибки. */
    public JwtBadSignatureException(final String message) {
        super(message);
    }
}
