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
public class SecurityCheckerUserTest {

    @Test
    public void executeWithUserId() {
        Context context = new ContextManual();
        context.setUserId(1);
        ServiceLocator.getContextStorage().set(context);

        SecurityCheck securityCheck = new SecurityCheckUser();
        Assert.assertTrue(securityCheck.execute(null));
    }

    @Test
    public void executeWithoutUserId() {
        Context context = new ContextManual();
        ServiceLocator.getContextStorage().set(context);

        SecurityCheck securityCheck = new SecurityCheckUser();
        Assert.assertFalse(securityCheck.execute(null));
    }

}
