package org.mobiquityinc.dao.impl;

import org.junit.Before;
import org.junit.Test;
import org.mobiquityinc.dao.DataParser;
import org.mobiquityinc.model.ThingPackage;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleDataParserTest {

    private DataParser dataParser;

    @Before
    public void setUp() {
        dataParser = new SimpleDataParser();
    }

    @Test(expected = Exception.class)
    public void shouldFailParseData_onAbsentPackages() throws Exception {
        String invalidInputData = "11.2";

        dataParser.parseLines(Arrays.asList(invalidInputData));
    }

    @Test(expected = Exception.class)
    public void shouldFailParseData_onAbsentWeightLimit() throws Exception {
        String invalidInputData = " : (1,1.1,€1.1) (2,2.2,€2.2)";

        dataParser.parseLines(Arrays.asList(invalidInputData));
    }

    @Test
    public void shouldSucceedParseData_ofValidFormat() throws Exception {
        String validInputData = " 11.2 : (1,1.1,€1.1) (2,2.2,€2.2) ";

        List<ThingPackage> result = dataParser.parseLines(Arrays.asList(validInputData));

        assertThat(result).hasSize(1);

        ThingPackage parsedPackage = result.get(0);

        assertThat(parsedPackage.getThings()).hasSize(2);
        assertThat(parsedPackage.getWeightLimit()).isEqualTo(BigDecimal.valueOf(11.2));

        assertThat(parsedPackage.getThings().get(0).getIndex()).isEqualTo(1);
        assertThat(parsedPackage.getThings().get(0).getPrice()).isEqualTo(BigDecimal.valueOf(1.1));
        assertThat(parsedPackage.getThings().get(0).getWeight()).isEqualTo(BigDecimal.valueOf(1.1));

        assertThat(parsedPackage.getThings().get(1).getIndex()).isEqualTo(2);
        assertThat(parsedPackage.getThings().get(1).getPrice()).isEqualTo(BigDecimal.valueOf(2.2));
        assertThat(parsedPackage.getThings().get(1).getWeight()).isEqualTo(BigDecimal.valueOf(2.2));
    }
}