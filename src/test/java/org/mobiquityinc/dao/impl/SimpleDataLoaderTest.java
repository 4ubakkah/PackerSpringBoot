package org.mobiquityinc.dao.impl;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mobiquityinc.dao.DataLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleDataLoaderTest {

    private static final String TEMP_FILE_NAME = "tempTestData.txt";

    private DataLoader dataLoader;

    @Before
    public void setUp() {
        dataLoader = new SimpleDataLoader();
    }

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test(expected = FileNotFoundException.class)
    public void shouldFailLoadData_onNonExistingFilePathProvided() throws Exception {
        dataLoader.loadData("SOME_DUMMY_PATH");
    }

    @Test
    public void shouldSucceedLoadData() throws Exception {
        String fileData = "DUMMY_DATA";
        File tempFile = tempFolder.newFile(TEMP_FILE_NAME);
        FileUtils.writeStringToFile(tempFile, fileData);

        List<String> lines = dataLoader.loadData(tempFile.getAbsolutePath());

        assertThat(lines).isNotNull().hasSize(1);
        assertThat(lines.get(0)).contains(fileData);
    }
}