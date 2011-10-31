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

import com.shroggle.TestUtil;
import com.shroggle.TestBaseWithMockService;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.presentation.TestAction;
import com.shroggle.presentation.site.traffic.TrafficSitesAction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import net.sourceforge.stripes.mock.MockHttpSession;
import net.sourceforge.stripes.mock.MockServletContext;

/**
 * @author dmitry.solomadin
 */
public class TrafficSitesActionTest extends TestBaseWithMockService {

    private final TrafficSitesAction action = new TrafficSitesAction();

    @Before
    public void before() {
        action.setContext(new ActionBeanContext());
        final MockHttpServletRequest request = new MockHttpServletRequest("a", "b");
        request.setSession(new MockHttpSession(new MockServletContext("")));
        action.getContext().setRequest(request);
    }

    @Test
    public void show() {
        TestUtil.createUserAndLogin();

        final ResolutionMock resolutionMock = (ResolutionMock) action.show();

        Assert.assertEquals("/site/traffic/trafficSites.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void showWithoutUser() {
        final ResolutionMock resolutionMock = (ResolutionMock) action.show();

        Assert.assertEquals(action, resolutionMock.getLoginInUserAction());
    }
}
