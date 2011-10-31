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
import com.shroggle.entity.User;
import com.shroggle.presentation.MockWebContext;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk (artem)
 *         </p>
 *         Date: 3 вер 2008
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ShowLoginInUserServiceTest {

    @Test
    public void executeWithParameterExceptionNull() throws Exception {
        final User user = TestUtil.createUserAndLogin();


        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));

        service.execute(null);

        Assert.assertTrue("Must be without exception!", !service.getException());
        Assert.assertEquals(user, service.getLoginedUser());
    }

    @Test
    public void executeWithParameterExceptionNullAndWithoutUser() throws Exception {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));

        service.execute(null);

        Assert.assertTrue("Must be without exception!", !service.getException());
        Assert.assertNull(service.getLoginedUser());
    }

    @Test
    public void executeWithoutLoginWithParameterExceptionNull() throws Exception {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));

        service.execute(null);

        Assert.assertTrue("Must be without exception!", !service.getException());
        Assert.assertNull(service.getLoginedUser());
    }

    @Test
    public void executeWithoutLoginWithParameterExceptionFalse() throws Exception {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));

        service.execute(false);

        Assert.assertFalse("Must be with not exception!", service.getException());
        Assert.assertNull(service.getLoginedUser());
    }

    @Test
    public void executeWithoutLoginWithParameterExceptionTrue() throws Exception {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));

        service.execute(true);

        Assert.assertTrue("Must be with not exception!", service.getException());
        Assert.assertNull(service.getLoginedUser());
    }

    private final ShowLoginInAccountService service = new ShowLoginInAccountService();

}