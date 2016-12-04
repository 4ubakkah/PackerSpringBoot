package org.mobiquityinc.packer.impl;

import org.mobiquityinc.dao.DataLoader;
import org.mobiquityinc.dao.DataParser;
import org.mobiquityinc.model.ThingPackage;
import org.mobiquityinc.model.exception.ParseException;
import org.mobiquityinc.model.validation.impl.ThingsPackageValidator;
import org.mobiquityinc.packer.PackEngine;
import org.mobiquityinc.packer.Packer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SimplePacker implements Packer {

    @Autowired
    private DataLoader dataLoader;

    @Autowired
    private DataParser dataParser;

    @Autowired
    private PackEngine packEngine;

    @Autowired
    private ThingsPackageValidator validator;

    @Override
    public String pack(String filePath) {
        String result = null;
        try {
            List<String> dataLines = dataLoader.loadData(filePath);
            List<ThingPackage> nonOptimizedPackages = dataParser.parseLines(dataLines);
            List<ThingPackage> nonOptimizedValidPackages = validator.filterOutNonValid(nonOptimizedPackages);

            result = nonOptimizedValidPackages.stream().map(pack -> packEngine.composeOptimalPackage(pack)).map(ThingPackage::toString).collect(Collectors.joining("\n"));
        } catch (FileNotFoundException ex) {
            System.out.println("File is not found.");
        } catch (ParseException ex) {
            System.out.println("Could not parse input testdata.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return result;
    }
}
