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
        BigDecimal weightLimit = BigDecimal.valueOf(Double.parseDouble(metadata[0].trim()));

        List<String> thingMetadata = Arrays.asList(metadata[1].trim().split(" "));
        List<Thing> things = thingMetadata.stream()
                .map(thingAsString -> {
                    String[] thingDetails = thingAsString.trim().split(",");
                    int index = Integer.parseInt(thingDetails[0].substring(1));
                    BigDecimal weight = BigDecimal.valueOf(Double.parseDouble(thingDetails[1]));
                    BigDecimal price = BigDecimal.valueOf(Double.parseDouble(thingDetails[2].substring(1, thingDetails[2].length() - 1)));
                    return new Thing(index, price, weight);
                })
                .collect(Collectors.toList());

        return new ThingPackage(things, weightLimit);
    }

}