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
import com.shroggle.exception.AdminLoginNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk, dmitry.solomadin
 */
@RunWith(TestRunnerWithMockServices.class)
public class ConfigureAdminLoginServiceTest {

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ConfigureAdminLoginService service = new ConfigureAdminLoginService();

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

        DraftAdminLogin adminLogin = TestUtil.createAdminLogin(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);
        widget.setDraftItem(adminLogin);

        service.execute(widget.getWidgetId(), null);
        Assert.assertEquals(site, service.getSite());
        Assert.assertEquals(widget, service.getWidget());
        Assert.assertNotNull(service.getWidgetTitle());
        Assert.assertEquals(adminLogin.getId(), service.getAdminLogin().getId());
    }

    @Test
    public void showFromManageItems() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftAdminLogin adminLogin = TestUtil.createAdminLogin(site);

        service.execute(null, adminLogin.getFormId());
        Assert.assertEquals(adminLogin.getSiteId(), service.getSite().getSiteId());
        Assert.assertEquals(null, service.getWidget());
        Assert.assertNull(service.getWidgetTitle());
        Assert.assertEquals(adminLogin, service.getAdminLogin());
    }

    @Test(expected = AdminLoginNotFoundException.class)
    public void showWithEmptyWidget() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        service.execute(widget.getWidgetId(), null);
    }

    @Test(expected = AdminLoginNotFoundException.class)
    public void showWithNotFoundAdminLogin() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        service.execute(null, -1);
    }

    @Test(expected = UserNotLoginedException.class)
    public void showWithUserNotLogined() throws Exception {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        service.execute(widget.getWidgetId(), null);
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

        service.execute(widget.getWidgetId(), null);
    }

    @Test(expected = WidgetNotFoundException.class)
    public void executeWithNotFoundWidget() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        service.execute(0, null);
    }

}
