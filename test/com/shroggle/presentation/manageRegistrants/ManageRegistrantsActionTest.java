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
package com.shroggle.presentation.manageRegistrants;

import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.presentation.TestAction;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.Before;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import net.sourceforge.stripes.mock.MockHttpSession;
import net.sourceforge.stripes.mock.MockServletContext;
import net.sourceforge.stripes.action.ActionBeanContext;

import javax.servlet.http.Cookie;

/**
 * @author dmitry.solomadin
 */
public class ManageRegistrantsActionTest extends TestAction<ManageRegistratnsAction> {

    public ManageRegistrantsActionTest() {
        super(ManageRegistratnsAction.class);
    }

    @Before
    public void before() {
        actionOrService.setContext(new ActionBeanContext());
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest("", "");
        mockHttpServletRequest.setSession(new MockHttpSession(new MockServletContext("")));
        mockHttpServletRequest.setCookies(new Cookie[0]);
        actionOrService.getContext().setRequest(mockHttpServletRequest);
        actionOrService.getContext().getRequest().getSession();
    }

    @Test
    public void show() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        actionOrService.setSiteId(site.getSiteId());
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.show();

        Assert.assertEquals("/account/manageRegistrants/manageRegistrantsForAction.jsp", resolutionMock.getForwardToUrl());
        Assert.assertEquals(user, actionOrService.getLoginUser());
        Assert.assertEquals(site.getSiteId(), actionOrService.getSiteId());
    }

    @Test
    public void showWithVisitor() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final User visitor = TestUtil.createUser();
        DraftForm form = TestUtil.createRegistrationForm(site.getSiteId());
        TestUtil.createDefaultRegistrationFilledFormForVisitorAndFormId(visitor, site, form.getFormId());

        actionOrService.setSiteId(site.getSiteId());
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.show();

        Assert.assertEquals("/account/manageRegistrants/manageRegistrantsForAction.jsp", resolutionMock.getForwardToUrl());
        Assert.assertEquals(user, actionOrService.getLoginUser());
        Assert.assertEquals(site.getSiteId(), actionOrService.getSiteId());
    }

    @Test
    public void showNotLoginedUser() {
        final Site site = TestUtil.createSite();

        actionOrService.setSiteId(site.getSiteId());
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.show();

        Assert.assertEquals(actionOrService, resolutionMock.getLoginInUserAction());
    }
    
}
