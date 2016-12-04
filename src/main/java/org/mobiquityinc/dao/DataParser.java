package org.mobiquityinc.dao;

import org.mobiquityinc.model.ThingPackage;

import java.util.List;

public interface DataParser {

    List<ThingPackage> parseLines(List<String> line);


}
