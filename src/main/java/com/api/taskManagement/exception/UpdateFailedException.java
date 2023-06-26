package com.api.taskManagement.exception;

public class UpdateFailedException extends BusinessLogicException{
    public UpdateFailedException(String message) {
        super(message);
    }
}
