package org.mobiquityinc.packer.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mobiquityinc.model.Thing;
import org.mobiquityinc.model.ThingPackage;
import org.mobiquityinc.model.fixture.ThingFixture;
import org.mobiquityinc.model.fixture.ThingPackageFixture;
import org.mobiquityinc.model.validation.impl.ThingValidator;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SimplePackEngineTest {

    @InjectMocks
    private SimplePackEngine packEngine;

    @Mock
    private ThingValidator thingValidator;

    @Test
    public void shouldComposeOptimalPackage() {
        List<Thing> things = Arrays.asList(new Thing(1, new BigDecimal("29"), new BigDecimal("85.31")),
                new Thing(2, new BigDecimal("74"), new BigDecimal("14.55")),
                new Thing(3, new BigDecimal("16"), new BigDecimal("3.98")),
                new Thing(4, new BigDecimal("55"), new BigDecimal("26.24")),
                new Thing(5, new BigDecimal("52"), new BigDecimal("63.69")),
                new Thing(6, new BigDecimal("75"), new BigDecimal("76.25")),
                new Thing(7, new BigDecimal("74"), new BigDecimal("60.02")),
                new Thing(8, new BigDecimal("35"), new BigDecimal("93.18")),
                new Thing(9, new BigDecimal("78"), new BigDecimal("89.95")));
        ThingPackage thingPackage = new ThingPackage(things, new BigDecimal("75"));

        Mockito.when(thingValidator.isValid(Mockito.any())).thenReturn(true);

        ThingPackage optimalPackage = packEngine.composeOptimalPackage(thingPackage);

        assertThat(optimalPackage.getThings()).hasSize(2);
        assertThat(optimalPackage.getThings()).extracting(thing -> thing.getIndex()).containsOnly(2, 7);

        BigDecimal packageWeight = optimalPackage.getThings().stream().map(Thing::getWeight).reduce((a, b) -> a.add(b)).get();
        assertThat(packageWeight).isLessThan(optimalPackage.getWeightLimit());
    }

    @Test
    public void shouldComposeEmptyPackage_dueToOverload() {
        Thing thing = ThingFixture.defaultThing();
        ThingPackage thingPackage = new ThingPackage(Collections.singletonList(thing), thing.getWeight().subtract(BigDecimal.ONE));
        ThingPackage optimalPackage = packEngine.composeOptimalPackage(thingPackage);

        assertThat(optimalPackage.getThings()).isEmpty();
    }

    @Test
    public void shouldComposeEmptyPackage_dueToNoValidThings() {
        Mockito.when(thingValidator.isValid(Mockito.any())).thenReturn(false);

        ThingPackage thingPackage =  ThingPackageFixture.defaultPackage();
        ThingPackage optimalPackage = packEngine.composeOptimalPackage(thingPackage);

        assertThat(optimalPackage.getThings()).isEmpty();
    }

}