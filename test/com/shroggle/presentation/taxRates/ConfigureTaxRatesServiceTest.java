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
import com.shroggle.entity.*;
import com.shroggle.exception.TaxRatesNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.taxRates.TaxRatesUSManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class ConfigureTaxRatesServiceTest {

    private final ConfigureTaxRatesService service = new ConfigureTaxRatesService();
    private final Persistance persistance = ServiceLocator.getPersistance();

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void testExecute_withItemId() throws Exception {
        TestUtil.createUserAndLogin();

        final DraftTaxRatesUS taxRates = new DraftTaxRatesUS();
        taxRates.addTaxRate(new DraftTaxRateUS(States_US.AL));
        persistance.putItem(taxRates);

        service.execute(null, taxRates.getId());

        Assert.assertNull(service.getWidgetTitle());
        Assert.assertEquals(taxRates.getId(), service.getDraftTaxRates().getId());
    }

    @Test
    public void testExecute_withWidgetId() throws Exception {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndLogin();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final DraftTaxRatesUS taxRates = new DraftTaxRatesUS();
        taxRates.addTaxRate(new DraftTaxRateUS(States_US.AL));
        persistance.putItem(taxRates);

        final PageManager page = TestUtil.createPageVersion(TestUtil.createPage(site), PageVersionType.DRAFT);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(page);

        TestUtil.createWidgetItem();
        widgetItem.setDraftItem(taxRates);
        persistance.putWidget(widgetItem);

        service.execute(widgetItem.getWidgetId(), null);

        Assert.assertNotNull(service.getWidgetTitle());
        Assert.assertEquals(taxRates.getId(), service.getDraftTaxRates().getId());
    }

    @Test
    public void testExecute_withWidgetAndItemId() throws Exception {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndLogin();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final DraftTaxRatesUS taxRates = new DraftTaxRatesUS();
        taxRates.addTaxRate(new DraftTaxRateUS(States_US.AL));
        persistance.putItem(taxRates);

        final PageManager page = TestUtil.createPageVersion(TestUtil.createPage(site), PageVersionType.DRAFT);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(page);

        TestUtil.createWidgetItem();
        widgetItem.setDraftItem(taxRates);
        persistance.putWidget(widgetItem);


        final DraftTaxRatesUS secondTaxRates = new DraftTaxRatesUS();
        persistance.putItem(secondTaxRates);

        service.execute(widgetItem.getWidgetId(), secondTaxRates.getId());

        Assert.assertNotNull(service.getWidgetTitle());
        Assert.assertEquals(taxRates.getId(), service.getDraftTaxRates().getId());
    }

    @Test(expected = UserNotLoginedException.class)
    public void testExecute_withoutLoginedUser() throws Exception {
        service.execute(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecute_withoutTaxRates() throws Exception {
        TestUtil.createUserAndLogin();
        service.execute(null, null);
    }

}
