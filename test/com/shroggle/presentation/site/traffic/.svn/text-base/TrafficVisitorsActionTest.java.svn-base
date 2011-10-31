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

package com.shroggle.presentation.site.traffic;

import com.shroggle.TestBaseWithMockService;
import com.shroggle.TestUtil;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.User;
import com.shroggle.exception.PageNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.ResolutionMock;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import net.sourceforge.stripes.mock.MockHttpSession;
import net.sourceforge.stripes.mock.MockServletContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author dmitry.solomadin
 */
public class TrafficVisitorsActionTest extends TestBaseWithMockService {

    @Before
    public void before() {
        action.setContext(new ActionBeanContext());
        final MockHttpServletRequest request = new MockHttpServletRequest("a", "b");
        request.setSession(new MockHttpSession(new MockServletContext("")));
        action.getContext().setRequest(request);
    }

    @Test
    public void show() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        action.pageId = pageVersion.getPage().getPageId();
        final ResolutionMock resolutionMock = (ResolutionMock) action.show();

        Assert.assertEquals("/site/traffic/trafficVisitors.jsp", resolutionMock.getForwardToUrl());
    }

    @Test(expected = PageNotFoundException.class)
    public void showWhithoutPage() {
        TestUtil.createUserAndLogin();

        action.show();
    }

    @Test
    public void showWithoutUser() {
        final ResolutionMock resolutionMock = (ResolutionMock) action.show();

        Assert.assertEquals(action, resolutionMock.getLoginInUserAction());
    }

    private final TrafficVisitorsAction action = new TrafficVisitorsAction();

}
