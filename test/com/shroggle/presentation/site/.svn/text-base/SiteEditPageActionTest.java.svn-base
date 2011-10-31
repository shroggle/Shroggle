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
import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.presentation.account.dashboard.DashboardAction;
import com.shroggle.util.ServiceLocator;
import junit.framework.Assert;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stasuk Artem
 */
@RunWith(TestRunnerWithMockServices.class)
public class SiteEditPageActionTest {

    @Before
    public void before() {
        action.setContext(new ActionBeanContext());
        action.getContext().setRequest(new MockHttpServletRequest("a", "b"));
    }

    @Test
    public void showWithoutLogin() throws Exception {
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(action, resolutionMock.getLoginInUserAction());
    }

    @Test
    public void showWithoutSite() throws Exception {
        TestUtil.createUserAndLogin();
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeWithNotMySite() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndLogin();

        action.setSiteId(site.getSiteId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void execute() throws Exception {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndLogin();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        action.setSiteId(site.getSiteId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals("/site/siteEditPage/siteEditPage.jsp", resolutionMock.getForwardToUrl());
        Assert.assertNull(ServiceLocator.getSessionStorage().getCurrentlyViewedPageId(action.getContext().getRequest().getSession(), site.getSiteId()));
    }

    @Test
    public void executeWithPreviouslyViewedPage() throws Exception {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndLogin();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.setHtml("HTML");

        action.setSiteId(site.getSiteId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        ServiceLocator.getSessionStorage().putCurrentlyViewedPageId(
                action.getContext().getRequest().getSession(), site.getSiteId(), pageVersion.getPage().getPageId());

        Assert.assertEquals("/site/siteEditPage/siteEditPage.jsp", resolutionMock.getForwardToUrl());
        Assert.assertEquals(pageVersion.getPage().getPageId(),
                (int) ServiceLocator.getSessionStorage().getCurrentlyViewedPageId(action.getContext().getRequest().getSession(), site.getSiteId()));
        Assert.assertEquals("2", action.getPagesIds());
    }

    private final SiteEditPageAction action = new SiteEditPageAction();

}