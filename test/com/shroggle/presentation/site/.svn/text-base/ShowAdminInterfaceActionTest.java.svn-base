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
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.presentation.TestAction;
import com.shroggle.presentation.account.dashboard.DashboardAction;
import junit.framework.Assert;
import org.junit.Test;

public class ShowAdminInterfaceActionTest extends TestAction<ShowAdminInterfaceAction> {

    public ShowAdminInterfaceActionTest() {
        super(ShowAdminInterfaceAction.class);
    }

    @Test
    public void show() {
        configStorage.get().setAdminLogin("admin");
        configStorage.get().setAdminPassword("111");

        TestUtil.createUserAndLogin("admin");

        actionOrService.setTitle("a");
        actionOrService.setUrl("kk");
        actionOrService.setLogin("te");
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.show();

        Assert.assertEquals("kk", actionOrService.getUrl());
        Assert.assertEquals("a", actionOrService.getTitle());
        Assert.assertEquals("te", actionOrService.getLogin());
        Assert.assertEquals("/account/showAdminInterface.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void showWithNotAdminLogin() {
        configStorage.get().setAdminLogin("admin");
        configStorage.get().setAdminPassword("111");

        TestUtil.createUserAndLogin();

        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.show();

        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void showWithoutLogin() {
        configStorage.get().setAdminLogin("admin");
        configStorage.get().setAdminPassword("111");

        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.show();

        Assert.assertEquals(actionOrService, resolutionMock.getLoginInUserAction());
    }

}
