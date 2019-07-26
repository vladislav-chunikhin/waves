package ru.opensolutions.fortune.exception;

/**
 * created by vlad on 21.07.19
 */
public class JwtBadSignatureException extends RuntimeException {
    public JwtBadSignatureException(String message) {
        super(message);
    }
}
