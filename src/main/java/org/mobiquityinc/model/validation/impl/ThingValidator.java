package org.mobiquityinc.model.validation.impl;

import org.mobiquityinc.model.Thing;
import org.mobiquityinc.model.validation.Validator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ThingValidator implements Validator<Thing> {

    public static final BigDecimal MAX_WEIGHT = BigDecimal.valueOf(100);
    public static final BigDecimal MAX_PRICE  = BigDecimal.valueOf(100);

    @Override
    public boolean isValid(Thing item) {
        return item.getPrice().compareTo(MAX_PRICE) <= 0
                && item.getWeight().compareTo(MAX_WEIGHT) <= 0;
    }
}
