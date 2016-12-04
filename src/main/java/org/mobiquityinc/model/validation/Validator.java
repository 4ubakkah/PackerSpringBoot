package org.mobiquityinc.model.validation;

public interface Validator<T> {

    boolean isValid(T item);
}
