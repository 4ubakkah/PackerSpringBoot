package org.mobiquityinc.model.validation;

public interface Validator<T> {

    /**
     * Validates item if it doest conform to applied rules.
     */
    boolean isValid(T item);
}
