package com.api.taskManagement.exception;

public class TokenValidationException extends BusinessLogicException {
    public TokenValidationException(String message) {
        super (message);
    }
}
