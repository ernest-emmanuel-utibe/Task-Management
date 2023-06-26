package com.api.taskManagement.exception;

public class ResourceNotFoundException extends BusinessLogicException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
