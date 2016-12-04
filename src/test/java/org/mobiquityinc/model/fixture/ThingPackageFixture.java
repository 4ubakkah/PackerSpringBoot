package org.mobiquityinc.model.fixture;

import org.mobiquityinc.model.Thing;
import org.mobiquityinc.model.ThingPackage;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import static org.mobiquityinc.model.constants.PackageConstants.MAX_PACKAGE_WEIGHT;
import static org.mobiquityinc.model.constants.PackageConstants.MAX_THINGS_COUNT_PER_PACKAGE;

public class ThingPackageFixture {

    private ThingPackageFixture() {}

    public static ThingPackage defaultPackage() {
        return new ThingPackage(Arrays.asList(ThingFixture.defaultThing()), BigDecimal.TEN);
    }

    public static ThingPackage withNonValidThing() {
        return new ThingPackage(Arrays.asList(ThingFixture.withExceededPrice()), BigDecimal.TEN);
    }

    public static ThingPackage withNonValidMaxWeight() {
        return new ThingPackage(Arrays.asList(ThingFixture.withExceededPrice()), MAX_PACKAGE_WEIGHT);
    }

    public static ThingPackage withNonValidThingCount() {
        List<Thing> things = new LinkedList<>();
        IntStream.rangeClosed(1, MAX_THINGS_COUNT_PER_PACKAGE + 1)
                .forEach(i -> things.add(ThingFixture.defaultThing()));

        return new ThingPackage(things, BigDecimal.TEN);
    }
}
