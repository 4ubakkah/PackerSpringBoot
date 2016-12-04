package org.mobiquityinc.model.exception;

public class ParseException extends RuntimeException {
    public ParseException(String message, Throwable t) {
        super(message, t);
    }
}
