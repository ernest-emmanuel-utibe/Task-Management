package com.api.taskManagement.exception;

public class UserExistsException extends BusinessLogicException {

    public UserExistsException(String message) {
        super(message);
    }
}
