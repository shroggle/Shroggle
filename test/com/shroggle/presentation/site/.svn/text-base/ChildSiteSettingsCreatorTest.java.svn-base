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
package com.shroggle.presentation.site;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;


/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class ChildSiteSettingsCreatorTest {

    @Test
    public void testExecuteInTransaction_withFilledForm() {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment", site);
        childSiteRegistration.setEndDate(new Date());
        childSiteRegistration.setStartDate(null);
        childSiteRegistration.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);
        childSiteRegistration.setPrice250mb(250);
        childSiteRegistration.setPrice500mb(500);
        childSiteRegistration.setPrice1gb(1000);
        childSiteRegistration.setPrice3gb(3000);
        childSiteRegistration.setRequiredToUseSiteBlueprint(true);
        childSiteRegistration.setTermsAndConditions("TermsAndConditions");
        persistance.putItem(childSiteRegistration);

        FilledForm filledForm1 = new FilledForm();
        filledForm1.setChildSiteSettingsId(-1);
        user.addFilledForm(filledForm1);

        FilledForm filledForm2 = new FilledForm();
        filledForm2.setChildSiteSettingsId(-1);
        user.addFilledForm(filledForm2);

        FilledForm filledForm3 = new FilledForm();
        filledForm3.setChildSiteSettingsId(-1);

        Assert.assertEquals(new Integer(-1), filledForm1.getChildSiteSettingsId());
        Assert.assertEquals(new Integer(-1), filledForm2.getChildSiteSettingsId());
        Assert.assertEquals(0, user.getChildSiteSettingsId().size());


        ChildSiteSettings childSiteSettings = CreateChildSiteService.createChildSiteSettings(childSiteRegistration, user, site, filledForm3);


        Assert.assertEquals(new Integer(-1), filledForm1.getChildSiteSettingsId());
        Assert.assertEquals(new Integer(-1), filledForm2.getChildSiteSettingsId());


        Assert.assertNotNull(childSiteSettings);
        Assert.assertNotNull(filledForm3.getChildSiteSettingsId());
        Assert.assertEquals(new Integer(childSiteSettings.getChildSiteSettingsId()), filledForm3.getChildSiteSettingsId());
        Assert.assertEquals(childSiteSettings.getFilledFormId(), filledForm3.getFilledFormId());

        Assert.assertEquals(user.getUserId(), childSiteSettings.getUserId());
        Assert.assertEquals(1, user.getChildSiteSettingsId().size());
        Assert.assertEquals(new Integer(childSiteSettings.getChildSiteSettingsId()), user.getChildSiteSettingsId().get(0));
        Assert.assertEquals(childSiteSettings.getParentSite().getSiteId(), site.getSiteId());

        Assert.assertNotNull(childSiteSettings.getChildSiteRegistration());
        Assert.assertEquals(childSiteSettings.getChildSiteRegistration().getFormId(), childSiteRegistration.getFormId());
        Assert.assertEquals(childSiteSettings.getAccessLevel(), childSiteRegistration.getAccessLevel());
        Assert.assertEquals(childSiteSettings.getEndDate(), childSiteRegistration.getEndDate());
        Assert.assertNull(childSiteSettings.getStartDate());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSiteRegistration.getPrice250mb());
        Assert.assertEquals(childSiteSettings.getPrice500mb(), childSiteRegistration.getPrice500mb());
        Assert.assertEquals(childSiteSettings.getPrice1gb(), childSiteRegistration.getPrice1gb());
        Assert.assertEquals(childSiteSettings.getPrice3gb(), childSiteRegistration.getPrice3gb());
        Assert.assertEquals(childSiteSettings.getWelcomeText(), childSiteRegistration.getWelcomeText());
        Assert.assertEquals(childSiteSettings.getLogoId(), childSiteRegistration.getLogoId());
        Assert.assertEquals(childSiteSettings.getTermsAndConditions(), childSiteRegistration.getTermsAndConditions());
        Assert.assertEquals(childSiteSettings.isRequiredToUseSiteBlueprint(), childSiteRegistration.isRequiredToUseSiteBlueprint());
        Assert.assertNotNull(childSiteSettings.getCreatedDate());
        Assert.assertNotNull(childSiteSettings.getConfirmCode());

        Assert.assertNotNull(persistance.getChildSiteSettingsById(childSiteSettings.getChildSiteSettingsId()));
    }


    @Test
    public void testExecuteInTransaction_withoutFilledForm() {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment", site);
        childSiteRegistration.setEndDate(new Date());
        childSiteRegistration.setStartDate(null);
        childSiteRegistration.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);
        childSiteRegistration.setPrice250mb(250);
        childSiteRegistration.setPrice500mb(500);
        childSiteRegistration.setPrice1gb(1000);
        childSiteRegistration.setPrice3gb(3000);
        childSiteRegistration.setRequiredToUseSiteBlueprint(true);
        childSiteRegistration.setTermsAndConditions("TermsAndConditions");
        persistance.putItem(childSiteRegistration);

        FilledForm filledForm1 = new FilledForm();
        filledForm1.setChildSiteSettingsId(-1);
        user.addFilledForm(filledForm1);

        FilledForm filledForm2 = new FilledForm();
        filledForm2.setChildSiteSettingsId(-1);
        user.addFilledForm(filledForm2);

        FilledForm filledForm3 = new FilledForm();
        filledForm3.setChildSiteSettingsId(-1);

        Assert.assertEquals(new Integer(-1), filledForm1.getChildSiteSettingsId());
        Assert.assertEquals(new Integer(-1), filledForm2.getChildSiteSettingsId());
        Assert.assertEquals(0, user.getChildSiteSettingsId().size());

        ChildSiteSettings childSiteSettings = CreateChildSiteService.executeInTransaction(childSiteRegistration, user, site);

        Assert.assertEquals(new Integer(-1), filledForm1.getChildSiteSettingsId());
        Assert.assertEquals(new Integer(-1), filledForm2.getChildSiteSettingsId());
        Assert.assertEquals(new Integer(-1), filledForm3.getChildSiteSettingsId());

        Assert.assertNotNull(childSiteSettings);
        Assert.assertNotNull(filledForm3.getChildSiteSettingsId());

        Assert.assertEquals(user.getUserId(), childSiteSettings.getUserId());
        Assert.assertEquals(1, user.getChildSiteSettingsId().size());
        Assert.assertEquals(new Integer(childSiteSettings.getChildSiteSettingsId()), user.getChildSiteSettingsId().get(0));
        Assert.assertEquals(childSiteSettings.getFilledFormId(), filledForm3.getFilledFormId());
        Assert.assertEquals(childSiteSettings.getParentSite().getSiteId(), site.getSiteId());

        Assert.assertNotNull(childSiteSettings.getChildSiteRegistration());
        Assert.assertEquals(childSiteSettings.getChildSiteRegistration().getFormId(), childSiteRegistration.getFormId());
        Assert.assertEquals(childSiteSettings.getAccessLevel(), childSiteRegistration.getAccessLevel());
        Assert.assertEquals(childSiteSettings.getEndDate(), childSiteRegistration.getEndDate());
        Assert.assertNull(childSiteSettings.getStartDate());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSiteRegistration.getPrice250mb());
        Assert.assertEquals(childSiteSettings.getPrice500mb(), childSiteRegistration.getPrice500mb());
        Assert.assertEquals(childSiteSettings.getPrice1gb(), childSiteRegistration.getPrice1gb());
        Assert.assertEquals(childSiteSettings.getPrice3gb(), childSiteRegistration.getPrice3gb());
        Assert.assertEquals(childSiteSettings.getTermsAndConditions(), childSiteRegistration.getTermsAndConditions());
        Assert.assertEquals(childSiteSettings.isRequiredToUseSiteBlueprint(), childSiteRegistration.isRequiredToUseSiteBlueprint());
        Assert.assertNotNull(childSiteSettings.getCreatedDate());
        Assert.assertNotNull(childSiteSettings.getConfirmCode());

        Assert.assertNotNull(persistance.getChildSiteSettingsById(childSiteSettings.getChildSiteSettingsId()));

    }

    @Test
    public void testExecuteInTransaction_withStartDateBeforeCurrent() {
        final Site site = TestUtil.createSite();
        final DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment", site);
        childSiteRegistration.setStartDate(new Date(System.currentTimeMillis() - (15 * 24 * 60 * 60 * 1000L)));
        childSiteRegistration.setEndDate(null);
        persistance.putItem(childSiteRegistration);

        final User user = TestUtil.createUser();
        final ChildSiteSettings childSiteSettings = CreateChildSiteService.executeInTransaction(childSiteRegistration, user, site);
        Assert.assertNotNull(persistance.getChildSiteSettingsById(childSiteSettings.getChildSiteSettingsId()));
        Assert.assertTrue(childSiteSettings.isCanBePublishedMessageSent());
        Assert.assertEquals(user.getUserId(), childSiteSettings.getSitePaymentSettings().getUserId().intValue());
    }

    @Test
    public void testExecuteInTransaction_withoutStartDate() {
        final Site site = TestUtil.createSite();
        final DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment", site);
        childSiteRegistration.setStartDate(null);
        persistance.putItem(childSiteRegistration);

        final User user = TestUtil.createUser();
        final ChildSiteSettings childSiteSettings = CreateChildSiteService.executeInTransaction(childSiteRegistration, user, site);
        Assert.assertNotNull(persistance.getChildSiteSettingsById(childSiteSettings.getChildSiteSettingsId()));
        Assert.assertTrue(childSiteSettings.isCanBePublishedMessageSent());
        Assert.assertEquals(user.getUserId(), childSiteSettings.getSitePaymentSettings().getUserId().intValue());
    }

    @Test
    public void testExecuteInTransaction_withStartDateAsterCurrent() {
        final Site site = TestUtil.createSite();
        final DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment", site);
        childSiteRegistration.setStartDate(new Date(System.currentTimeMillis() + (15 * 24 * 60 * 60 * 1000L)));
        childSiteRegistration.setEndDate(null);
        persistance.putItem(childSiteRegistration);

        final User user = TestUtil.createUser();
        final ChildSiteSettings childSiteSettings = CreateChildSiteService.executeInTransaction(childSiteRegistration, user, site);
        Assert.assertNotNull(persistance.getChildSiteSettingsById(childSiteSettings.getChildSiteSettingsId()));
        Assert.assertFalse(childSiteSettings.isCanBePublishedMessageSent());
        Assert.assertEquals(user.getUserId(), childSiteSettings.getSitePaymentSettings().getUserId().intValue());
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
}
