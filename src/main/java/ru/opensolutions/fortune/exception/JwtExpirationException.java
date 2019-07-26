package ru.opensolutions.fortune.exception;

/**
 * created by vlad on 21.07.19
 */
public class JwtExpirationException extends RuntimeException {
    public JwtExpirationException(String message) {
        super(message);
    }
}
