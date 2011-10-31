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

import com.shroggle.TestUtil;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.User;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.presentation.TestAction;
import junit.framework.Assert;
import org.junit.Test;


public class LoginHelpActionTest extends TestAction<LoginHelpAction> {

    public LoginHelpActionTest() {
        super(LoginHelpAction.class);
    }

    @Test
    public void showForUserAdmin() throws Exception {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.show();

        Assert.assertEquals("/account/loginHelp.jsp", resolutionMock.getForwardToUrl());
        Assert.assertEquals(user, actionOrService.getLoginUser());
    }

    @Test
    public void showForSiteAdmin() throws Exception {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.EDITOR);

        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.show();

        Assert.assertEquals("/account/loginHelp.jsp", resolutionMock.getForwardToUrl());
        Assert.assertEquals(user, actionOrService.getLoginUser());
    }

    @Test
    public void showWithoutLogin() throws Exception {
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.show();

        Assert.assertEquals(actionOrService, resolutionMock.getLoginInUserAction());
    }

}