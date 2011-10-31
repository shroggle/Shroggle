/*********************************************************************
 *                                                                   *
 * Copyright (c) 2007-2011 by Web-Deva.                              *
 * All rights reserved.                                              *
 *                                                                   *
 * This computer program is protected by copyright law and           *
 * international treaties. Unauthorized reproduction or distribution *
 * of this program, or any portion of it, may result in severe civil *
 * and criminal penalties, and will be prosecuted to the maximum     *
 * extent possible under the law.                                    *
 *                                                                   *
 *********************************************************************/
package com.shroggle.logic.countries;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.entity.Country;
import com.shroggle.exception.CountryNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class CountryManagerTest {

    @Test(expected = CountryNotFoundException.class)
    public void testCreate_withoutCountry() {
        new CountryManager(null);
    }

    @Test
    public void testGetStatesByCountry() {
        List<String> states;
        states = new CountryManager(Country.US).getStatesByCountry();
        Assert.assertNotNull(states);
        Assert.assertEquals(50, states.size());
        for (String state : states) {
            Assert.assertNotNull(state);
            Assert.assertFalse(state.contains(";"));
            Assert.assertFalse(state.isEmpty());
        }

        states = new CountryManager(Country.CA).getStatesByCountry();
        Assert.assertNotNull(states);
        Assert.assertEquals(12, states.size());
        for (String state : states) {
            Assert.assertNotNull(state);
            Assert.assertFalse(state.contains(";"));
            Assert.assertFalse(state.isEmpty());
        }

        states = new CountryManager(Country.UA).getStatesByCountry();
        Assert.assertNotNull(states);
        Assert.assertEquals(0, states.size());
    }

    @Test
    public void testgetCountryValue() {
        Assert.assertEquals("Ukraine", CountryManager.getCountryValue(Country.UA));
        Assert.assertEquals("USA", CountryManager.getCountryValue(Country.US));
        Assert.assertEquals("Canada", CountryManager.getCountryValue(Country.CA));
        Assert.assertEquals("Afghanistan", CountryManager.getCountryValue(Country.AF));
    }

    @Test
    public void testGetAllCountries() {
        Assert.assertEquals(239, CountryManager.getCountriesStringValues().size());
    }
}
