package ru.opensolutions.fortune.exception;

import org.springframework.security.authentication.AccountStatusException;

/**
 * created by vlad on 21.07.19
 */
public class MalformedJwtException extends AccountStatusException {
    public MalformedJwtException(String message) {
        super(message);
    }
}
