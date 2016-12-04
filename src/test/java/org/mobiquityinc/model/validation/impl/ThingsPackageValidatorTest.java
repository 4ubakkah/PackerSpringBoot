package org.mobiquityinc.model.validation.impl;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mobiquityinc.model.fixture.ThingPackageFixture;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class ThingsPackageValidatorTest {


    @InjectMocks
    private ThingsPackageValidator validator;

    @Mock
    private ThingValidator thingValidator;

    @Test
    public void packageIsValid() throws Exception {
        Mockito.when(thingValidator.isValid(Mockito.any())).thenReturn(true);

        assertThat(validator.isValid(ThingPackageFixture.defaultPackage())).isTrue();
    }

    @Test
    public void packageIsNonValid_dueToThingsCountExceeded() throws Exception {
        assertThat(validator.isValid(ThingPackageFixture.withNonValidThingCount())).isFalse();
    }

    @Test
    @Ignore(value = "Requires clarification if non valid things should fail package validation or have to be filtered out")
    public void thingIsNonValid_dueToExceededMaxWeight() throws Exception {
        assertThat(validator.isValid(ThingPackageFixture.withNonValidMaxWeight())).isFalse();
    }

    @Test
    @Ignore(value = "Requires clarification if non valid things should fail package validation or have to be filtered out")
    public void thingIsNonValid_dueToContainedNonValidThing() throws Exception {
        Mockito.when(thingValidator.isValid(Mockito.any())).thenReturn(false);

        assertThat(validator.isValid(ThingPackageFixture.withNonValidThing())).isFalse();
    }
}