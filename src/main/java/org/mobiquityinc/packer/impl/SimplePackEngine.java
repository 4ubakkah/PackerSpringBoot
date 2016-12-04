package org.mobiquityinc.packer.impl;

import org.mobiquityinc.model.ThingPackage;
import org.mobiquityinc.model.Thing;
import org.mobiquityinc.model.constants.PackageConstants;
import org.mobiquityinc.model.validation.impl.ThingValidator;
import org.mobiquityinc.packer.PackEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class SimplePackEngine implements PackEngine {

    @Autowired
    private ThingValidator thingValidator;

    @Override
    public ThingPackage composeOptimalPackage(ThingPackage thingPackage) {
        List<Thing> onlyValidThings = filterNonValidThings(thingPackage).getThings();

        List<List<Thing>> combinations = generateAllPossibleCombinations(onlyValidThings);
        List<List<Thing>> validCombinations = filterCombinationsWithOverWeight(combinations, thingPackage.getWeightLimit());

        return getOptimalPackage(validCombinations, thingPackage.getWeightLimit());
    }

    private ThingPackage filterNonValidThings(ThingPackage thingPackage) {
         List<Thing> validThings = thingPackage.getThings().stream().filter(thing -> thingValidator.isValid(thing)).collect(Collectors.toList());

        return new ThingPackage(validThings, thingPackage.getWeightLimit());
    }

    private List<List<Thing>> filterCombinationsWithOverWeight(List<List<Thing>> input, BigDecimal weightLimit) {
        return new LinkedList<>(input.stream().filter(hasOverWeight(weightLimit)).collect(Collectors.toList()));
    }

    private static Predicate<List<Thing>> hasOverWeight(BigDecimal weightLimit) {
        return things -> computeTotalWeight(things).compareTo(weightLimit) < 0;
    }

    private List<List<Thing>> generateAllPossibleCombinations(List<Thing> things) {
        List<List<Thing>> allPossibleCombinations = new LinkedList<>();

        for (Thing thing : things) {
            List<List<Thing>> newPossibleCombinations = new LinkedList<>();

            for (List<Thing> subset : allPossibleCombinations) {
                List<Thing> newSubset = new LinkedList<>(subset);
                newSubset.add(thing);
                newPossibleCombinations.add(newSubset);
            }

            allPossibleCombinations.addAll(newPossibleCombinations);
            allPossibleCombinations.add(Arrays.asList(thing));

        }
        return allPossibleCombinations;
    }

    private ThingPackage getOptimalPackage(List<List<Thing>> combinations, BigDecimal weightLimit) {
        List<Thing> optimalListOfThings = new LinkedList<>();

        BigDecimal currentBestWeight = PackageConstants.MAX_PACKAGE_WEIGHT;
        BigDecimal currentBestPrice = BigDecimal.ZERO;

        for (List<Thing> combination : combinations) {

            BigDecimal combinationTotalWeight = computeTotalWeight(combination);
            BigDecimal combinationTotalPrice = computeTotalPrice(combination);

            if (combinationTotalPrice.compareTo(currentBestPrice) == 1
                    || (combinationTotalPrice.compareTo(currentBestPrice) == 0 && combinationTotalWeight.compareTo(currentBestWeight) == -1)) {
                optimalListOfThings = combination;
                currentBestWeight = combinationTotalWeight;
                currentBestPrice = combinationTotalPrice;
            }
        }

        return new ThingPackage(optimalListOfThings, weightLimit);
    }

    private static BigDecimal computeTotalWeight(List<Thing> combination) {
        return combination.stream().map(Thing::getWeight).reduce((a, b) -> a.add(b)).get();
    }

    private static BigDecimal computeTotalPrice(List<Thing> combination) {
        return combination.stream().map(Thing::getPrice).reduce((a, b) -> a.add(b)).get();
    }
}
