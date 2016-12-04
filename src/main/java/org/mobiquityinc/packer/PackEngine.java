package org.mobiquityinc.packer;

import org.mobiquityinc.model.ThingPackage;

public interface PackEngine {

    ThingPackage composeOptimalPackage(ThingPackage nonSortedPack);
}
