package org.mobiquityinc.dao;

import java.io.IOException;
import java.util.List;

public interface DataLoader {

    /**
     * Loads ThingPackage from provided file.
     * @param filePath, path to file containing list of packages.
     * @return List<@String> list of packages in string presentation.
     */
    List<String> loadData(String filePath) throws IOException;

}
