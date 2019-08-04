package ru.opensolutions.fortune.exception;

import org.springframework.security.authentication.AccountStatusException;

/**
 * Общее исключение для токена. */
public class MalformedJwtException extends AccountStatusException {
    public MalformedJwtException(String message) {
        super(message);
    }
}
