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
import com.shroggle.exception.ContactUsNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(value = TestRunnerWithMockServices.class)
public class ConfigureContactUsServiceTest {

    @Test
    public void showFromSiteEditPage() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        DraftContactUs contactUs = TestUtil.createContactUs(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);
        widget.setDraftItem(contactUs);

        //Verify that service don't render page.
        service.execute(widget.getWidgetId(), null);
        Assert.assertEquals(site, service.getSite());
        Assert.assertNotNull(service.getInitFormItems());
        Assert.assertFalse(service.getInitFormItems().isEmpty());
        Assert.assertNotNull(service.getExistingFormItems());
        Assert.assertFalse(service.getExistingFormItems().isEmpty());
        Assert.assertEquals(widget, service.getWidget());
        Assert.assertEquals(contactUs.getId(), service.getContactUs().getId());
    }

    @Test(expected = ContactUsNotFoundException.class)
    public void showFromManageItems() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        service.execute(widget.getWidgetId(), null);
    }

    @Test(expected = ContactUsNotFoundException.class)
    public void executeWithWidgetWithoutForm() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));

        service.execute(null, -1);
    }

    @Test(expected = ContactUsNotFoundException.class)
    public void executeWithNotFoundForm() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));

        service.execute(null, -1);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() throws Exception {
        WidgetItem widgetContactUs = TestUtil.createContactUsWidget();

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));

        service.execute(widgetContactUs.getWidgetId(), null);
    }

    @Test(expected = WidgetNotFoundException.class)
    public void executeWithoutWidget() throws Exception {
        TestUtil.createUserAndLogin();
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));

        service.execute(-1, null);
    }

    private final ConfigureContactUsService service = new ConfigureContactUsService();
    private final Persistance persistance = ServiceLocator.getPersistance();

}