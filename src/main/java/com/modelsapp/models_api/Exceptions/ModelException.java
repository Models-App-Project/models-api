package com.modelsapp.models_api.Exceptions;

public class ModelException extends Exception {
    public ModelException(String message) {
        super(message);
    }

    public ModelException(String message, Exception cause) {
        super(message, cause);
    }
}
