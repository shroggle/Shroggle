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
package com.shroggle.logic.site.taxRates;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.entity.DraftTaxRateUS;
import com.shroggle.entity.States_US;
import com.shroggle.util.ServiceLocator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class TaxRateUSManagerTest {
    
    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithNull() throws Exception {
        new TaxRateUSManager(null);
    }

    @Test
    public void testGetSetTaxRate() throws Exception {
        final DraftTaxRateUS taxRate = new DraftTaxRateUS(States_US.AL);
        taxRate.setIncluded(false);
        taxRate.setTaxRate(12341.0);

        final TaxRateUSManager manager = new TaxRateUSManager(taxRate);
        Assert.assertEquals(12341.0, manager.getTaxRate(), 0);

        manager.setTaxRate(12343452346789.1321);
        Assert.assertEquals(12343452346789.1321, manager.getTaxRate(), 10);
        Assert.assertEquals(12343452346789.1321, taxRate.getTaxRate(), 10);
        Assert.assertEquals("12343452346789.133", manager.getTaxRateAsString());

        manager.setTaxRate(null);
        Assert.assertEquals("", manager.getTaxRateAsString());
    }

    @Test
    public void testGetState() throws Exception {
        final DraftTaxRateUS taxRate = new DraftTaxRateUS(States_US.AL);
        taxRate.setIncluded(false);
        taxRate.setTaxRate(12341.0);

        final TaxRateUSManager manager = new TaxRateUSManager(taxRate);
        Assert.assertEquals(States_US.AL, manager.getState());
    }

    @Test
    public void testGetId() throws Exception {
        final DraftTaxRateUS taxRate = new DraftTaxRateUS(States_US.AL);
        taxRate.setIncluded(false);
        taxRate.setTaxRate(12341.0);
        ServiceLocator.getPersistance().putTaxRate(taxRate);

        final TaxRateUSManager manager = new TaxRateUSManager(taxRate);
        Assert.assertEquals(taxRate.getId(), manager.getId());
    }

    @Test
    public void testIsIncluded() throws Exception {
        final DraftTaxRateUS taxRate = new DraftTaxRateUS(States_US.AL);
        taxRate.setIncluded(false);
        taxRate.setTaxRate(12341.0);
        ServiceLocator.getPersistance().putTaxRate(taxRate);

        final TaxRateUSManager manager = new TaxRateUSManager(taxRate);
        Assert.assertEquals(false, manager.isIncluded());


        manager.setIncluded(true);
        Assert.assertEquals(true, manager.isIncluded());
        Assert.assertEquals(true, taxRate.isIncluded());
    }

    @Test
    public void testGetStateName() throws Exception {
        final DraftTaxRateUS taxRate = new DraftTaxRateUS(States_US.AL);
        taxRate.setIncluded(false);
        taxRate.setTaxRate(12341.0);
        ServiceLocator.getPersistance().putTaxRate(taxRate);

        final TaxRateUSManager manager = new TaxRateUSManager(taxRate);
        Assert.assertEquals("Alabama", manager.getStateName());
    }
}
