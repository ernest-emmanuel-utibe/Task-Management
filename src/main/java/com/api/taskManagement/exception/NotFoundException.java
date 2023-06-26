package com.api.taskManagement.exception;

public class NotFoundException extends BusinessLogicException {
    public NotFoundException(String message) {
        super(message);
    }
}
