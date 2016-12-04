package org.mobiquityinc.model.validation.impl;

import org.mobiquityinc.model.ThingPackage;
import org.mobiquityinc.model.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.mobiquityinc.model.constants.PackageConstants.MAX_PACKAGE_WEIGHT;
import static org.mobiquityinc.model.constants.PackageConstants.MAX_THINGS_COUNT_PER_PACKAGE;

@Service
public class ThingsPackageValidator implements Validator<ThingPackage> {

    //TODO remove if is not required after clarification on thing validation
    @Autowired
    private ThingValidator thingValidator;

    @Override
    public boolean isValid(ThingPackage item) {
        return item.getWeightLimit().compareTo(MAX_PACKAGE_WEIGHT) <= 0
                && item.getThings().size() <= MAX_THINGS_COUNT_PER_PACKAGE;

                //TODO clarify if non valid things should be filtered out or whole Package should be considered non-valid.
                //&& item.getThings().stream().allMatch(thing -> thingValidator.isValid(thing));
    }

    public List<ThingPackage> filterOutNonValid(List<ThingPackage> thingPackages) {
        return thingPackages.stream().filter(thingPackage ->  isValid(thingPackage))
                .collect(Collectors.toList());
    }
}
