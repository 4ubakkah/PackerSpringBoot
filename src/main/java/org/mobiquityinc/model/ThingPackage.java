package org.mobiquityinc.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ThingPackage {
    private final List<Thing> things;

    private final BigDecimal weightLimit;

    public ThingPackage(List<Thing> things, BigDecimal weightLimit) {
        this.things = things;
        this.weightLimit = weightLimit;
    }

    public List<Thing> getThings() {
        return things;
    }

    public BigDecimal getWeightLimit() {
        return weightLimit;
    }

    @Override
    public String toString() {
        return things.isEmpty() ? "-" : things.stream().map(thing -> String.valueOf(thing.getIndex())).collect(Collectors.joining(","));
    }
}
