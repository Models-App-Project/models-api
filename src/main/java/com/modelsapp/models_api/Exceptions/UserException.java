package com.modelsapp.models_api.Exceptions;

public class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Exception cause) {
        super(message, cause);
    }
}