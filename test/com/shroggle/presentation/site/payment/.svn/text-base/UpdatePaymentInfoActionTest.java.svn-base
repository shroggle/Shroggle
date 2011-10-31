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

package com.shroggle.presentation.site.payment;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.User;
import com.shroggle.presentation.ResolutionMock;
import junit.framework.Assert;
import net.sourceforge.stripes.action.ActionBeanContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */

@RunWith(value = TestRunnerWithMockServices.class)
public class UpdatePaymentInfoActionTest {

    @Before
    public void before() {
        action.setContext(new ActionBeanContext());
    }

    @Test
    public void show() throws Exception {
        Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        user.setPassword("1");
        user.setEmail("a@a");
        final ResolutionMock resolutionMock = (ResolutionMock) action.show();
        Assert.assertEquals("/payment/updatePaymentInfo.jsp", resolutionMock.getForwardToUrl());
        Assert.assertTrue(action.getData().isHasAdminAccessToThisSite());
    }

    private final UpdatePaymentInfoAction action = new UpdatePaymentInfoAction();

}
