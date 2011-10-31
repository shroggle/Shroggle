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
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.presentation.account.dashboard.DashboardAction;
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
public class TrafficPagesActionTest extends TestBaseWithMockService {

    @Before
    public void before() {
        action.setContext(new ActionBeanContext());
        final MockHttpServletRequest request = new MockHttpServletRequest("a", "b");
        request.setSession(new MockHttpSession(new MockServletContext("")));
        action.getContext().setRequest(request);
    }

    @Test
    public void show() throws Exception {
        Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        action.setSiteId(site.getSiteId());
        ResolutionMock resolutionMock = (ResolutionMock) action.show();

        Assert.assertEquals("/site/traffic/trafficPages.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void showWithoutSite() {
        TestUtil.createUserAndLogin();

        ResolutionMock resolutionMock = (ResolutionMock) action.show();

        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void showWithoutUser() {
        ResolutionMock resolutionMock = (ResolutionMock) action.show();

        Assert.assertNotNull(resolutionMock.getLoginInUserAction());
    }

    private final TrafficPagesAction action = new TrafficPagesAction();

}
