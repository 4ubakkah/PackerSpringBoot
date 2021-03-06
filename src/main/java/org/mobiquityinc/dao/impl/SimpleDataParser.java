package org.mobiquityinc.dao.impl;

import org.mobiquityinc.dao.DataParser;
import org.mobiquityinc.model.ThingPackage;
import org.mobiquityinc.model.Thing;
import org.mobiquityinc.model.exception.ParseException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SimpleDataParser implements DataParser {

    private static final String METADATA_DELIMITER = ":";
    private static final String THINGS_DELIMITER = " ";
    private static final String NUMERIC_SYMBOLS_PATTERN = "[^\\d.]";
    private static final String THING_DETAILS_DELIMITER = ",";

    @Override
    public List<ThingPackage> parseLines(List<String> line) {
        try {
            return line.stream().map(l -> parseLine(l)).collect(Collectors.toList());
        } catch (Exception ex) {
            throw new ParseException(ex.getMessage(), ex);
        }
    }

    private ThingPackage parseLine(String line) {
        String[] metadata = line.split(METADATA_DELIMITER);
        BigDecimal weightLimit = new BigDecimal(metadata[0].trim());

        List<String> thingMetadata = Arrays.asList(metadata[1].trim().split(THINGS_DELIMITER));
        List<Thing> things = thingMetadata.stream()
                .map(thingAsString -> {
                    String[] thingDetails = thingAsString.trim().split(THING_DETAILS_DELIMITER);
                    int index = Integer.parseInt(getNumericSymbolsOnly(thingDetails[0]));
                    BigDecimal weight = new BigDecimal(thingDetails[1]);
                    BigDecimal price = new BigDecimal(getNumericSymbolsOnly(thingDetails[2]));
                    return new Thing(index, price, weight);
                })
                .collect(Collectors.toList());

        return new ThingPackage(things, weightLimit);
    }

    private String getNumericSymbolsOnly(String input) {
        return input.replaceAll(NUMERIC_SYMBOLS_PATTERN, "");
    }

}
