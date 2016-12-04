package org.mobiquityinc.model.fixture;

import org.mobiquityinc.model.Thing;

import java.math.BigDecimal;

public class ThingFixture {

    private ThingFixture() {}

    public static Thing defaultThing() {
        return new Thing(1, new BigDecimal("1.0"), new BigDecimal("1.0"));
    }

    public static Thing withExceededWeight() {
        return new Thing(1, BigDecimal.ONE, new BigDecimal("101.0"));
    }

    public static Thing withExceededPrice() {
        return new Thing(1, new BigDecimal("101.0"), BigDecimal.ONE);
    }
}
