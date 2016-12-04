package org.mobiquityinc.model.exception;


public class APIException extends IllegalArgumentException {
    public APIException(String message, Throwable t) {
        super(message, t);
    }
}