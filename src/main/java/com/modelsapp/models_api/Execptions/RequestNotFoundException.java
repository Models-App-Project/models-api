package com.modelsapp.models_api.Execptions;

public class RequestNotFoundException extends Throwable {

    public RequestNotFoundException(String message) {
        super(message);
    }

    public RequestNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
