package org.mobiquityinc.packer;

import org.mobiquityinc.model.ThingPackage;

public interface PackEngine {

    /**
     * Composes optimal package from non-optimal one.
     * @param @ThingPackage nonSortedPack, non-optimal package.
     * @return @ThingPackage optimized package with rules applied.
     */
    ThingPackage composeOptimalPackage(ThingPackage nonSortedPack);
}
