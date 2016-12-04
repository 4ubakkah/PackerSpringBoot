package org.mobiquityinc.model.constants;

import java.math.BigDecimal;

public class PackageConstants {

    /**
     * Maximum allowed weight of full package counted from contained Things
     */
    public static final BigDecimal MAX_PACKAGE_WEIGHT = BigDecimal.valueOf(100);

    /**
     * Maximum allowed count of Things contained by single ThingPackage
     */
    public static final int MAX_THINGS_COUNT_PER_PACKAGE = 15;
}
