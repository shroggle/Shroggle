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
import com.shroggle.exception.RegistrationFormNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.presentation.registration.ConfigureRegistrationService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author dmitry.solomadin
 * Date: 15.09.2009
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ConfigureRegistrationServiceTest {

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ConfigureRegistrationService service = new ConfigureRegistrationService();

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void showFromSiteEditPage() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);
        widget.setDraftItem(registrationForm);

        service.show(widget.getWidgetId(), null);
        Assert.assertEquals(site, service.getSite());
        Assert.assertNotNull(service.getInitFormItems());
        Assert.assertFalse(service.getInitFormItems().isEmpty());
        Assert.assertNotNull(service.getExistingFormItems());
        Assert.assertFalse(service.getExistingFormItems().isEmpty());
        Assert.assertEquals(widget, service.getWidgetRegistration());
        Assert.assertEquals(registrationForm.getId(), service.getRegistrationForm().getId());
    }

    @Test
    public void showFromManageItems() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm(site.getSiteId());

        service.show(null, registrationForm.getFormId());
        Assert.assertEquals(registrationForm.getSiteId(), service.getSite().getSiteId());
        Assert.assertEquals(null, service.getWidgetRegistration());
        Assert.assertEquals(registrationForm, service.getRegistrationForm());
    }

    @Test(expected = RegistrationFormNotFoundException.class)
    public void showWithEmptyWidget() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        service.show(widget.getWidgetId(), null);
    }

    @Test(expected = RegistrationFormNotFoundException.class)
    public void showWithNotFoundRegistrationForm() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        service.show(null, -1);
    }

    @Test(expected = UserNotLoginedException.class)
    public void showWithUserNotLogined() throws Exception {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        service.show(widget.getWidgetId(), null);
    }

    @Test(expected = WidgetNotFoundException.class)
    public void showWithNotMy() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndLogin();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        service.show(widget.getWidgetId(), null);
    }

    @Test(expected = WidgetNotFoundException.class)
    public void executeWithNotFoundWidget() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        service.show(0, null);
    }

}