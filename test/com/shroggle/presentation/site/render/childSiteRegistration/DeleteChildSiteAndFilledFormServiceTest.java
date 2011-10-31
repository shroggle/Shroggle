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
package com.shroggle.presentation.site.render.childSiteRegistration;

import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;
import org.junit.runner.RunWith;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import net.sourceforge.stripes.mock.MockHttpServletRequest;

/**
 * @author Balakirev Anatoliy
 *         Date: 13.08.2009
 */
@RunWith(TestRunnerWithMockServices.class)
public class DeleteChildSiteAndFilledFormServiceTest {

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void testExecute_withChildSite() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final User userForChildSite = TestUtil.createUser();

        final Site parentSite = TestUtil.createSite();

        final Site childSite = TestUtil.createChildSite();
        childSite.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_MONTHLY_FEE));

        final DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("", "", parentSite);
        Widget widget = TestUtil.createWidgetChildSiteRegistration(childSiteRegistration.getId());
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(childSiteRegistration, parentSite, childSite);

        final FilledForm filledForm = new FilledForm();
        filledForm.setChildSiteSettingsId(childSiteSettings.getChildSiteSettingsId());
        persistance.putFilledForm(filledForm);
        childSiteSettings.setFilledFormId(filledForm.getFilledFormId());

        Assert.assertNotNull(parentSite.getChildSiteRegistrationsId());
        Assert.assertEquals(1, parentSite.getChildSiteRegistrationsId().size());
        Assert.assertEquals(childSiteRegistration.getFormId(), parentSite.getChildSiteRegistrationsId().get(0).intValue());

        Assert.assertNotNull(childSite.getChildSiteSettings());
        Assert.assertEquals(childSiteSettings, childSite.getChildSiteSettings());
        childSite.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_MONTHLY_FEE));
        Assert.assertEquals(childSiteSettings.getFilledFormId(), filledForm.getFilledFormId());

        TestUtil.createUserOnSiteRightActive(userForChildSite, childSite, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user, parentSite, SiteAccessLevel.ADMINISTRATOR);
        UserOnSiteRight right = TestUtil.createUserOnSiteRightActive(user, childSite, SiteAccessLevel.ADMINISTRATOR);
        right.setFromNetwork(true);

        Assert.assertEquals(2, childSite.getUserOnSiteRights().size());
        Assert.assertNotNull(persistance.getChildSiteSettingsById(childSiteSettings.getChildSiteSettingsId()));


        service.execute(widget.getWidgetId(), filledForm.getFilledFormId());


        Assert.assertNull(persistance.getFilledFormById(filledForm.getFilledFormId()));
        Assert.assertNull(persistance.getChildSiteSettingsById(childSiteSettings.getChildSiteSettingsId()));
        Assert.assertNull(persistance.getSite(childSite.getSiteId()));
    }


    @Test
    public void testExecute_withoutChildSite() throws Exception {
        TestUtil.createUserAndLogin();
        final DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("", "");
        Widget widget = TestUtil.createWidgetChildSiteRegistration(childSiteRegistration.getId());

        ChildSiteSettings settings = new ChildSiteSettings();
        settings.setChildSiteRegistration(childSiteRegistration);
        settings.setAccessLevel(childSiteRegistration.getAccessLevel());
        settings.setEndDate(childSiteRegistration.getEndDate() != null ? childSiteRegistration.getEndDate() : null);
        settings.setStartDate(childSiteRegistration.getStartDate() != null ? childSiteRegistration.getStartDate() : null);
        settings.setPrice1gb(childSiteRegistration.getPrice1gb());
        settings.setPrice250mb(childSiteRegistration.getPrice250mb());
        settings.setPrice500mb(childSiteRegistration.getPrice500mb());
        settings.setPrice3gb(childSiteRegistration.getPrice3gb());
        settings.setUserId(1);
        settings.setConfirmCode("ConfirmCode");
        ServiceLocator.getPersistance().putChildSiteSettings(settings);

        final FilledForm filledForm = new FilledForm();
        filledForm.setChildSiteSettingsId(settings.getChildSiteSettingsId());
        persistance.putFilledForm(filledForm);
        settings.setFilledFormId(filledForm.getFilledFormId());

        Assert.assertEquals(settings.getFilledFormId(), filledForm.getFilledFormId());
        Assert.assertNotNull(persistance.getChildSiteSettingsById(settings.getChildSiteSettingsId()));


        service.execute(widget.getWidgetId(), filledForm.getFilledFormId());


        Assert.assertNull(persistance.getFilledFormById(filledForm.getFilledFormId()));
        Assert.assertNull(persistance.getChildSiteSettingsById(settings.getChildSiteSettingsId()));
    }

    @Test
    public void testExecute_withoutChildSiteSettings() throws Exception {
        TestUtil.createUserAndLogin();
        final DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("", "");
        Widget widget = TestUtil.createWidgetChildSiteRegistration(childSiteRegistration.getId());


        final FilledForm filledForm = new FilledForm();
        persistance.putFilledForm(filledForm);


        service.execute(widget.getWidgetId(), filledForm.getFilledFormId());


        Assert.assertNull(persistance.getFilledFormById(filledForm.getFilledFormId()));
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final DeleteChildSiteAndFilledFormService service = new DeleteChildSiteAndFilledFormService();
}
