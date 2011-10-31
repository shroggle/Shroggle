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
package com.shroggle.util.process.synchronize.annotation;

import com.shroggle.presentation.account.dashboard.DashboardAction;
import com.shroggle.TestRunnerWithMockServices;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class SynchronizeForStripesInterceptorTest {

    @Test
    public void intercept() throws Exception {
        SynchronizeForStripesInterceptor interceptor = new SynchronizeForStripesInterceptor();
        ExecutionContext executionContext = new ExecutionContext();
        executionContext.setActionBean(new DashboardAction());
        Method method = DashboardAction.class.getMethod("show");
        executionContext.setHandler(method);
        Interceptor emptyInterceptor = new EmptyStripesInterceptor();
        executionContext.setInterceptors(Arrays.asList(emptyInterceptor));
        executionContext.wrap(interceptor);
    }

}
