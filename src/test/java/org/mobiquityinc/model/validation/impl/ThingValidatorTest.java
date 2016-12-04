package org.mobiquityinc.model.validation.impl;

import org.junit.Before;
import org.junit.Test;
import org.mobiquityinc.model.Thing;
import org.mobiquityinc.model.fixture.ThingFixture;
import org.mobiquityinc.model.validation.Validator;


import static org.assertj.core.api.Assertions.assertThat;


public class ThingValidatorTest {

    private Validator<Thing> validator;

    @Before
    public void setUp() {
        validator = new ThingValidator();
    }

    @Test
    public void thingIsValid() throws Exception {
        assertThat(validator.isValid(ThingFixture.defaultThing())).isTrue();
    }

    @Test
    public void thingIsNonValid_dueToExceededPrice() throws Exception {
        assertThat(validator.isValid(ThingFixture.withExceededPrice())).isFalse();
    }

    @Test
    public void thingIsNonValid_dueToExceededWeight() throws Exception {
        assertThat(validator.isValid(ThingFixture.withExceededWeight())).isFalse();
    }

}