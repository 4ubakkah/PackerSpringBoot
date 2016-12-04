package org.mobiquityinc.dao;

import org.mobiquityinc.model.ThingPackage;

import java.util.List;

public interface DataParser {

    /**
     * Returns list of parsed ThingPackage in domain presentation.
     * @param @List<String> line, list of packages in string presentation.
     * @return List<@ThingPackage>
     */
    List<ThingPackage> parseLines(List<String> line);

}
