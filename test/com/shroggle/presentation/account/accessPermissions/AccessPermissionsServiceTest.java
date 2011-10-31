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
package com.shroggle.presentation.account.accessPermissions;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class AccessPermissionsServiceTest {

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test(expected = UserNotLoginedException.class)
    public void getUsersByUserIdWithNotFoundSelectedUser() throws IOException, ServletException {
        service.getUsersTable();
    }


   @Test
    public void show() throws Exception {
        TestUtil.createUserAndLogin();

        
        service.getUsersTable();


        final AccessPermissionsModel model = (AccessPermissionsModel)service.getRequest().getAttribute("model");
        Assert.assertNotNull(model);
    }


    private final AccessPermissionsService service = new AccessPermissionsService();
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();

}