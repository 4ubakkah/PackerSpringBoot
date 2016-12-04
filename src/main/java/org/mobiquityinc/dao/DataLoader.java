package org.mobiquityinc.dao;

import java.io.IOException;
import java.util.List;

public interface DataLoader {

    List<String> loadData(String filePath) throws IOException;

}
