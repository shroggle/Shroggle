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
import junit.framework.Assert;
import org.junit.Test;

public class UserLoginActionTest extends TestAction<ShowLoginAction> {

    public UserLoginActionTest() {
        super(ShowLoginAction.class, true);
    }

    @Test
    public void executeWithoutLoginWithoutEnterUrl() {
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.execute();
        Assert.assertNull(actionOrService.getEnterUrl());
        Assert.assertEquals("/account/loginInAccountBlank.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void executeWithoutLogin() {
        actionOrService.getContext().getRequest().setAttribute("enterUrl", "aaa");
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.execute();
        Assert.assertEquals("aaa", actionOrService.getEnterUrl());
        Assert.assertEquals("/account/loginInAccountBlank.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void executeWithLogin() {
        TestUtil.createUserAndLogin();

        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.execute();
        Assert.assertNull(actionOrService.getEnterUrl());
        Assert.assertEquals("/account/loginInAccountBlank.jsp", resolutionMock.getForwardToUrl());
    }

}