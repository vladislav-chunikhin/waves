package ru.opensolutions.fortune.exception;

import org.springframework.security.authentication.AccountStatusException;

/**
 * Общее исключение для токена. */
public class MalformedJwtException extends AccountStatusException {
    /**
     * @param message сообщение ошибки. */
    public MalformedJwtException(final String message) {
        super(message);
    }
}
