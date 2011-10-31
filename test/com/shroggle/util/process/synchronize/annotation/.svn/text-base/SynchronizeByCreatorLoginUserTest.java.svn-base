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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.entity.User;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.Context;
import com.shroggle.util.context.ContextManual;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.SynchronizeRequest;
import com.shroggle.util.process.synchronize.SynchronizeRequestEntity;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class SynchronizeByCreatorLoginUserTest {

    @Test
    public void create() {
        Context context = new ContextManual();
        context.setUserId(112);
        ServiceLocator.getContextStorage().set(context);
        SynchronizeRequest synchronizeRequest = new SynchronizeByCreatorLoginUser().create(null);
        Assert.assertEquals(
                new SynchronizeRequestEntity(User.class, SynchronizeMethod.READ, 112).getPoints(),
                synchronizeRequest.getPoints());
    }

    @Test
    public void createWithoutUser() {
        Context context = new ContextManual();
        context.setUserId(null);
        ServiceLocator.getContextStorage().set(context);
        SynchronizeRequest synchronizeRequest = new SynchronizeByCreatorLoginUser().create(null);
        Assert.assertEquals(
                new SynchronizeRequestEntity(User.class, SynchronizeMethod.READ, null).getPoints(),
                synchronizeRequest.getPoints());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createWithoutContext() {
        ServiceLocator.getContextStorage().set(null);
        new SynchronizeByCreatorLoginUser().create(null);
    }

}
