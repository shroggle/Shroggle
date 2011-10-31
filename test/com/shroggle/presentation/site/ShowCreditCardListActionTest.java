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
import com.shroggle.presentation.ResolutionMock;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(TestRunnerWithMockServices.class)
public class ShowCreditCardListActionTest {

    @Test
    public void show() throws Exception {
        TestUtil.createUserAndLogin();

        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();
        Assert.assertEquals("/account/creditCardList.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void showWithoutLogin() {
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(action, resolutionMock.getLoginInUserAction());
    }

    private final ShowCreditCardListAction action = new ShowCreditCardListAction();

}