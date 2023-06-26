package com.api.taskManagement.exception;

public class TokenExpiredException extends BusinessLogicException{
    public TokenExpiredException(String message) {
        super(message);
    }
}
