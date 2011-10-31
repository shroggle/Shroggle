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
import com.shroggle.exception.ChildSiteRegistrationNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;

@RunWith(value = TestRunnerWithMockServices.class)
public class ConfigureChildSiteRegistrationServiceTest {

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void executeFromManageItems() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setSiteId(site.getSiteId());
        childSiteRegistration.setName("aa");
        persistance.putItem(childSiteRegistration);

        service.execute(null, childSiteRegistration.getId());
        Assert.assertEquals(childSiteRegistration, service.getChildSiteRegistration());
        Assert.assertNull(service.getWidgetTitle());
        Assert.assertEquals(site.getSiteId(), service.getSite().getSiteId());
    }

    @Test
    public void executeFromSiteEditPage() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final PageManager page = TestUtil.createPageVersionAndPage(site);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setSiteId(site.getSiteId());
        childSiteRegistration.setName("aa");
        persistance.putItem(childSiteRegistration);

        WidgetItem widgetChildSiteRegistration = TestUtil.createWidgetItem();
        widgetChildSiteRegistration.setDraftItem(childSiteRegistration);
        page.addWidget(widgetChildSiteRegistration);

        //Verify that service don't render page.
        service.execute(widgetChildSiteRegistration.getWidgetId(), null);
        Assert.assertEquals(childSiteRegistration, service.getChildSiteRegistration());
        Assert.assertNotNull(service.getWidgetTitle());
        Assert.assertEquals(site.getSiteId(), service.getSite().getSiteId());
    }

    @Test(expected = ChildSiteRegistrationNotFoundException.class)
    public void executeFromSiteEditPageWithWidgetWithoutItem() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final PageManager page = TestUtil.createPageVersionAndPage(site);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        WidgetItem childSiteRegistrationWidget = TestUtil.createWidgetItem();
        page.addWidget(childSiteRegistrationWidget);

        service.execute(childSiteRegistrationWidget.getWidgetId(), null);
    }

    @Test(expected = ChildSiteRegistrationNotFoundException.class)
    public void executeWithNotFoundCSR() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        service.execute(null, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void executeWithBothWidgetAndCSRIdsEmpty() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftChildSiteRegistration childSiteRegistration = new DraftChildSiteRegistration();
        childSiteRegistration.setSiteId(site.getSiteId());
        childSiteRegistration.setName("aa");
        persistance.putItem(childSiteRegistration);

        service.execute(null, null);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithUserNotLogined() throws Exception {
        service.execute(null, -1);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ConfigureChildSiteRegistrationService service = new ConfigureChildSiteRegistrationService();

}