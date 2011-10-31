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
package com.shroggle.presentation.site.render;

import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;
import org.junit.runner.RunWith;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.logic.site.billingInfo.ChargeTypeManager;
import com.shroggle.entity.*;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.ServiceLocator;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.presentation.site.render.childSiteRegistration.OptOutFilledFormFromNetworkService;
import net.sourceforge.stripes.mock.MockHttpServletRequest;


/**
 * @author Balakirev Anatoliy
 *         Date: 13.08.2009
 */
@RunWith(TestRunnerWithMockServices.class)
public class OptOutFilledFormFromNetworkServiceTest {

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
        Assert.assertEquals(childSiteSettings.getFilledFormId(), filledForm.getFilledFormId());

        TestUtil.createUserOnSiteRightActive(userForChildSite, childSite, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user, parentSite, SiteAccessLevel.ADMINISTRATOR);
        UserOnSiteRight right = TestUtil.createUserOnSiteRightActive(user, childSite, SiteAccessLevel.ADMINISTRATOR);
        right.setFromNetwork(true);

        Assert.assertEquals(2, childSite.getUserOnSiteRights().size());
        Assert.assertNotNull(persistance.getChildSiteSettingsById(childSiteSettings.getChildSiteSettingsId()));


        service.execute(widget.getWidgetId(), filledForm.getFilledFormId());


        Assert.assertNull(filledForm.getChildSiteSettingsId());
        Assert.assertNull(persistance.getChildSiteSettingsById(childSiteSettings.getChildSiteSettingsId()));
        Assert.assertNull(childSite.getChildSiteSettings());
        Assert.assertEquals(1, childSite.getUserOnSiteRights().size());
        Assert.assertEquals(userForChildSite, childSite.getUserOnSiteRights().get(0).getId().getUser());
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_250MB_MONTHLY_FEE).getPrice(), childSite.getSitePaymentSettings().getPrice(), 0);
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


        Assert.assertNull(filledForm.getChildSiteSettingsId());
        Assert.assertNull(persistance.getChildSiteSettingsById(settings.getChildSiteSettingsId()));
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final OptOutFilledFormFromNetworkService service = new OptOutFilledFormFromNetworkService();
}
