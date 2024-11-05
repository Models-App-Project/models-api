package com.modelsapp.models_api.Exceptions;

public class RequestException extends Throwable {

    public RequestException(String message) {
        super(message);
    }

    public RequestException(String message, Exception cause) {
        super(message, cause);
    }
}
