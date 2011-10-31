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
import com.shroggle.entity.DraftTaxRatesUS;
import com.shroggle.entity.States_US;
import com.shroggle.util.ServiceLocator;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class TaxRatesUSManagerTest {

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithNull() throws Exception {
        new TaxRatesUSManager(null);
    }

    @Test
    public void testUpdateTaxByState() throws Exception {
        final DraftTaxRateUS taxRate = new DraftTaxRateUS(States_US.AL);
        taxRate.setIncluded(false);
        Assert.assertEquals(States_US.AL, taxRate.getState());
        Assert.assertEquals(4.0, taxRate.getTaxRate());
        Assert.assertEquals(false, taxRate.isIncluded());

        final DraftTaxRatesUS taxRates = new DraftTaxRatesUS();
        taxRates.addTaxRate(taxRate);

        final TaxRatesUSManager manager = new TaxRatesUSManager(taxRates);
        manager.updateTaxByState(States_US.AL, 11212.21, true);

        Assert.assertEquals(States_US.AL, manager.getTaxRates().get(0).getState());
        Assert.assertEquals(11212.21, manager.getTaxRates().get(0).getTaxRate());
        Assert.assertEquals(true, manager.getTaxRates().get(0).isIncluded());
    }

    @Test
    public void testUpdateTaxByState_withoutItem() throws Exception {
        final DraftTaxRateUS taxRate = new DraftTaxRateUS(States_US.AL);
        taxRate.setIncluded(false);
        Assert.assertEquals(States_US.AL, taxRate.getState());
        Assert.assertEquals(4.0, taxRate.getTaxRate());
        Assert.assertEquals(false, taxRate.isIncluded());

        final DraftTaxRatesUS taxRates = new DraftTaxRatesUS();
        taxRates.addTaxRate(taxRate);

        final TaxRatesUSManager manager = new TaxRatesUSManager(taxRates);
        manager.updateTaxByState(States_US.AK, 11212.21, true);

        Assert.assertEquals(States_US.AL, manager.getTaxRates().get(0).getState());
        Assert.assertEquals(4.0, manager.getTaxRates().get(0).getTaxRate());
        Assert.assertEquals(false, manager.getTaxRates().get(0).isIncluded());
    }

    @Test
    public void testUpdateTaxByState_byNullState() throws Exception {
        final DraftTaxRateUS taxRate = new DraftTaxRateUS(States_US.AL);
        taxRate.setIncluded(false);
        Assert.assertEquals(States_US.AL, taxRate.getState());
        Assert.assertEquals(4.0, taxRate.getTaxRate());
        Assert.assertEquals(false, taxRate.isIncluded());

        final DraftTaxRatesUS taxRates = new DraftTaxRatesUS();
        taxRates.addTaxRate(taxRate);

        final TaxRatesUSManager manager = new TaxRatesUSManager(taxRates);
        manager.updateTaxByState(null, 11212.21, true);

        Assert.assertEquals(States_US.AL, manager.getTaxRates().get(0).getState());
        Assert.assertEquals(4.0, manager.getTaxRates().get(0).getTaxRate());
        Assert.assertEquals(false, manager.getTaxRates().get(0).isIncluded());
    }

    @Test
    public void testGetId() throws Exception {
        final DraftTaxRatesUS taxRates = new DraftTaxRatesUS();
        ServiceLocator.getPersistance().putItem(taxRates);

        final TaxRatesUSManager manager = new TaxRatesUSManager(taxRates);
        Assert.assertEquals(taxRates.getId(), manager.getId());
    }

    @Test
    public void testGetSetName() throws Exception {
        final DraftTaxRatesUS taxRates = new DraftTaxRatesUS();
        taxRates.setName("name");
        ServiceLocator.getPersistance().putItem(taxRates);

        final TaxRatesUSManager manager = new TaxRatesUSManager(taxRates);
        Assert.assertEquals(taxRates.getName(), manager.getName());

        manager.setName("newName");

        Assert.assertEquals("newName", manager.getName());
        Assert.assertEquals("newName", taxRates.getName());
    }

    @Test
    public void testGetTaxRates() throws Exception {
        final DraftTaxRatesUS taxRates = new DraftTaxRatesUS();
        taxRates.addTaxRate(new DraftTaxRateUS(States_US.AL));

        final TaxRatesUSManager manager = new TaxRatesUSManager(taxRates);
        Assert.assertEquals(1, manager.getTaxRates().size());
        Assert.assertEquals(States_US.AL, manager.getTaxRates().get(0).getState());
    }
}
