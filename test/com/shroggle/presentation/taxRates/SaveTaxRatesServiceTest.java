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
package com.shroggle.presentation.taxRates;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.DraftTaxRateUS;
import com.shroggle.entity.DraftTaxRatesUS;
import com.shroggle.entity.States_US;
import com.shroggle.exception.TaxRatesNameNotUnique;
import com.shroggle.exception.TaxRatesNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class SaveTaxRatesServiceTest {

    private final SaveTaxRatesService service = new SaveTaxRatesService();
    private final Persistance persistance = ServiceLocator.getPersistance();

    @Test(expected = UserNotLoginedException.class)
    public void testExecute_withoutLoginedUser() throws Exception {
        service.execute(null);
    }

    @Test(expected = TaxRatesNotFoundException.class)
    public void testExecute_withoutTaxRates() throws Exception {
        TestUtil.createUserAndLogin();
        service.execute(new SaveTaxRatesRequest());
    }

    @Test(expected = TaxRatesNameNotUnique.class)
    public void testExecute_withDuplicateName() throws Exception {
        TestUtil.createUserAndLogin();

        final DraftTaxRatesUS taxRates = new DraftTaxRatesUS();
        taxRates.addTaxRate(new DraftTaxRateUS(States_US.AL));
        taxRates.setName("name");
        persistance.putItem(taxRates);



        final DraftTaxRatesUS newTaxRates = new DraftTaxRatesUS();
        persistance.putItem(newTaxRates);

        final SaveTaxRatesRequest request = new SaveTaxRatesRequest();
        request.setItemId(newTaxRates.getId());
        request.setName("name");

        service.execute(request);
    }

    @Test
    public void testExecute() throws Exception {
        TestUtil.createUserAndLogin();

        final DraftTaxRatesUS taxRates = new DraftTaxRatesUS();
        final DraftTaxRateUS draftTaxRateUS1 = TestUtil.createTaxRateUS(States_US.AL, true);
        taxRates.addTaxRate(draftTaxRateUS1);
        final DraftTaxRateUS draftTaxRateUS2 = TestUtil.createTaxRateUS(States_US.VT, true);
        taxRates.addTaxRate(draftTaxRateUS2);
        final DraftTaxRateUS draftTaxRateUS3 = TestUtil.createTaxRateUS(States_US.WA, true);
        taxRates.addTaxRate(draftTaxRateUS3);
        taxRates.setName("name");
        persistance.putItem(taxRates);


        final SaveTaxRatesRequest request = new SaveTaxRatesRequest();
        request.setItemId(taxRates.getId());
        request.setName("newName");
        request.setStatesTaxes(new HashMap<States_US, Double>(){{put(States_US.AL, 1234.12);}});

        service.execute(request);

        Assert.assertEquals("newName", taxRates.getName());
        Assert.assertEquals(3, taxRates.getTaxRates().size());
        Assert.assertEquals(true, taxRates.getTaxRates().get(0).isIncluded());
        Assert.assertEquals(1234.12, taxRates.getTaxRates().get(0).getTaxRate(), 0);

        Assert.assertEquals(false, taxRates.getTaxRates().get(1).isIncluded());
        Assert.assertEquals(6.0, taxRates.getTaxRates().get(1).getTaxRate(), 0);

        Assert.assertEquals(false, taxRates.getTaxRates().get(2).isIncluded());
        Assert.assertEquals(6.5, taxRates.getTaxRates().get(2).getTaxRate(), 0);
    }

}
