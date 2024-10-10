package com.modelsapp.models_api.Execptions;

public class ModelException extends Throwable {
    public ModelException(String message) {
        super(message);
    }

    public ModelException(String message, Throwable cause) {
        super(message, cause);
    }
}
