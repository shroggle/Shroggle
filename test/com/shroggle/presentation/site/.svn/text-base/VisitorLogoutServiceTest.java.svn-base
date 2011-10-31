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
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(TestRunnerWithMockServices.class)
public class VisitorLogoutServiceTest {

    @Test
    public void execute() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        TestUtil.createUserAndLogin();

        service.execute();

        Assert.assertNull(new UsersManager().getLoginedUser());
    }

    private final VisitorLogoutService service = new VisitorLogoutService();
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();

}