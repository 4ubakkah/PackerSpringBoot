package org.mobiquityinc.packer.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mobiquityinc.dao.impl.SimpleDataLoader;
import org.mobiquityinc.dao.impl.SimpleDataParser;
import org.mobiquityinc.model.ThingPackage;
import org.mobiquityinc.model.fixture.ThingPackageFixture;
import org.mobiquityinc.model.validation.impl.ThingsPackageValidator;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class SimplePackerTest {

    @InjectMocks
    private SimplePacker simplePacker;

    @Mock
    private SimpleDataParser simpleDataParser;

    @Mock
    private SimpleDataLoader simpleDataLoader;

    @Mock
    private SimplePackEngine simplePackEngine;

    @Mock
    private ThingsPackageValidator validator;

    @Test
    public void verifyDelegationChainOnPack() throws Exception {
        String filePath = "DUMMY_FILE_PATH";

        List<String> loadedData = new LinkedList<>();
        List<ThingPackage> parsedPackages = new LinkedList<>();
        parsedPackages.add(ThingPackageFixture.defaultPackage());

        Mockito.when(simpleDataLoader.loadData(filePath)).thenReturn(loadedData);
        Mockito.when(simpleDataParser.parseLines(loadedData)).thenReturn(parsedPackages);
        Mockito.when(validator.filterOutNonValid(parsedPackages)).thenReturn(parsedPackages);

        simplePacker.pack(filePath);

        Mockito.verify(simpleDataLoader).loadData(filePath);
        Mockito.verify(simpleDataParser).parseLines(loadedData);

        Mockito.verify(simplePackEngine).composeOptimalPackage(parsedPackages.get(0));

        Mockito.verifyNoMoreInteractions(simplePackEngine);
    }

    @Test
    public void verifyDelegationChainOnPack_isBrokenDueAbsenceOfPackages() throws Exception {
        String filePath = "DUMMY_FILE_PATH";

        List<String> loadedData = new LinkedList<>();
        List<ThingPackage> parsedPackages = new LinkedList<>();
        parsedPackages.add(ThingPackageFixture.defaultPackage());

        Mockito.when(simpleDataLoader.loadData(filePath)).thenReturn(loadedData);
        Mockito.when(simpleDataParser.parseLines(loadedData)).thenReturn(parsedPackages);
        Mockito.when(validator.filterOutNonValid(parsedPackages)).thenReturn(new LinkedList<>());

        simplePacker.pack(filePath);

        Mockito.verify(simpleDataLoader).loadData(filePath);
        Mockito.verify(simpleDataParser).parseLines(loadedData);

        Mockito.verifyZeroInteractions(simplePackEngine);

        Mockito.verifyNoMoreInteractions(simplePackEngine);
    }



}