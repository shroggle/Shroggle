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
package com.shroggle.util.security;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.util.ExecutionContextMock;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.Context;
import com.shroggle.util.context.ContextManual;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class SecurityForStripesInterpectorTest {

    @Test
    public void interceptWithoutAccess() throws Exception {
        final Action action = new SecurityForStripesActionMock();
        executionContextMock.setActionBean(action);
        executionContextMock.setHandler(action.getClass().getMethod("test"));
        final ResolutionMock resolutionMock = (ResolutionMock) interceptor.intercept(executionContextMock);
        Assert.assertEquals(action, resolutionMock.getLoginInUserAction());
        Assert.assertFalse(executionContextMock.isProceed());
    }

    @Test
    public void interceptWithAccess() throws Exception {
        final Context context = new ContextManual();
        context.setUserId(1);
        ServiceLocator.getContextStorage().set(context);

        final Action action = new SecurityForStripesActionMock();

        executionContextMock.setActionBean(action);
        executionContextMock.setHandler(action.getClass().getMethod("test"));
        interceptor.intercept(executionContextMock);
        Assert.assertTrue(executionContextMock.isProceed());
    }

    @Test
    public void interceptWithoutAnnotation() throws Exception {
        final Action action = new SecurityForStripesActionMock();
        executionContextMock.setActionBean(action);
        executionContextMock.setHandler(action.getClass().getMethod("testWithoutAnnotation"));
        interceptor.intercept(executionContextMock);
        Assert.assertTrue(executionContextMock.isProceed());
    }

    private final SecurityForStripesInterceptor interceptor = new SecurityForStripesInterceptor();
    private final ExecutionContextMock executionContextMock = new ExecutionContextMock();

}
