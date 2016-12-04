package org.mobiquityinc.dao.impl;

import org.mobiquityinc.dao.DataLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
public class SimpleDataLoader implements DataLoader {

    public List<String> loadData(String filePath) throws IOException {
        List<String> dataLines = new LinkedList<>();
        File file = new File(filePath);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = br.readLine()) != null) {
                dataLines.add(line);
            }
        }

        return dataLines;
    }
}
