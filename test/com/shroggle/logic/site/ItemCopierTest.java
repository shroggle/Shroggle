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
package com.shroggle.logic.site;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.item.ItemCopierContext;
import com.shroggle.logic.site.item.ItemCopierSimple;
import com.shroggle.logic.site.item.ItemCopyResult;
import com.shroggle.logic.site.item.ItemNamingNextFreeName;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class ItemCopierTest {

    @Test
    public void testName() throws Exception {
        final DraftForm draftForm1 = TestUtil.createChildSiteRegistration(new Site());
        DraftForm draftForm = draftForm1.getClass().newInstance();
        Assert.assertEquals(true, draftForm instanceof ChildSiteRegistration);
    }

    @Test
    public void copyChildSiteRegistration() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration(site);
        TestUtil.createFormItem(FormItemName.NAME, childSiteRegistration, 0);
        TestUtil.createFormItem(FormItemName.ACADEMIC_DEGREE, childSiteRegistration, 1);
        TestUtil.createFormItem(FormItemName.ACADEMIC_EXPERIENCE_SCHOOL, childSiteRegistration, 2);

        final ItemCopierContext context = new ItemCopierContext();
        context.setCopiedSite(persistance.getSite(childSiteRegistration.getSiteId()));
        context.setItemNaming(new ItemNamingNextFreeName());
        final ItemCopyResult result = new ItemCopierSimple().execute(context, childSiteRegistration, null);

        final DraftChildSiteRegistration draftCustomForm = persistance.getDraftItem(result.getDraftItem().getId());
        Assert.assertEquals(persistance.getFontsAndColors(childSiteRegistration.getFontsAndColorsId()), persistance.getFontsAndColors(draftCustomForm.getFontsAndColorsId()));
        Assert.assertEquals(childSiteRegistration.getFormItems().size(), draftCustomForm.getFormItems().size());
        Assert.assertNotSame(childSiteRegistration.getFormItems(), draftCustomForm.getFormItems());
        Assert.assertEquals(childSiteRegistration.getDescription(), draftCustomForm.getDescription());
        Assert.assertEquals(childSiteRegistration.getSiteId(), draftCustomForm.getSiteId());
    }

    @Test
    public void copyMenu() {
        final Site site = TestUtil.createSite();

        final DraftMenu draftMenu = new DraftMenu();
        persistance.putItem(draftMenu);

        final DraftMenuItem draftMenuItem = new DraftMenuItem();
        draftMenu.addChild(draftMenuItem);
        persistance.putMenuItem(draftMenuItem);

        final ItemCopierContext context = new ItemCopierContext();
        context.setCopiedSite(site);
        context.setItemNaming(new ItemNamingNextFreeName());

        final ItemCopyResult itemCopyResult = new ItemCopierSimple().execute(context, draftMenu, null);
        final DraftMenu copiedDraftMenu = (DraftMenu) itemCopyResult.getDraftItem();

        Assert.assertEquals(site.getSiteId(), copiedDraftMenu.getSiteId());
        Assert.assertEquals("Copier copied menu items or use ItemCopierWithSameMenuItems!", 0, copiedDraftMenu.getMenuItems().size());
    }

    @Test
    public void testCopyItemProperties_taxRates() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftTaxRatesUS draftTaxRatesUS = new DraftTaxRatesUS();
        draftTaxRatesUS.setSiteId(site.getSiteId());
        persistance.putItem(draftTaxRatesUS);

        final DraftTaxRateUS draftTaxRateUS = new DraftTaxRateUS(States_US.CO);
        draftTaxRatesUS.addTaxRate(draftTaxRateUS);
        persistance.putTaxRate(draftTaxRateUS);


        final ItemCopierContext context = new ItemCopierContext();
        context.setCopiedSite(persistance.getSite(draftTaxRatesUS.getSiteId()));
        context.setItemNaming(new ItemNamingNextFreeName());
        final ItemCopyResult result = new ItemCopierSimple().execute(context, draftTaxRatesUS, null);

        DraftTaxRatesUS copiedDraftTaxRatesUS = persistance.getDraftItem(result.getDraftItem().getId());
        Assert.assertEquals(1, copiedDraftTaxRatesUS.getTaxRates().size());
        Assert.assertEquals(draftTaxRateUS.getTaxRate(), copiedDraftTaxRatesUS.getTaxRates().get(0).getTaxRate());
        Assert.assertEquals(draftTaxRateUS.getState(), copiedDraftTaxRatesUS.getTaxRates().get(0).getState());
        Assert.assertEquals(copiedDraftTaxRatesUS, copiedDraftTaxRatesUS.getTaxRates().get(0).getTaxRates());

        Assert.assertNotSame(draftTaxRateUS.getId(), copiedDraftTaxRatesUS.getTaxRates().get(0).getId());
        Assert.assertNotSame(draftTaxRateUS.getTaxRates(), copiedDraftTaxRatesUS.getTaxRates().get(0).getTaxRates());
        Assert.assertNotSame(copiedDraftTaxRatesUS.getId(), draftTaxRatesUS.getId());
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
}
