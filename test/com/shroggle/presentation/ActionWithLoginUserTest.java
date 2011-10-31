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
package com.shroggle.presentation;

import com.shroggle.ParameterizedTestRunner;
import com.shroggle.ParameterizedTestRunnerWithServiceLocator;
import com.shroggle.ParameterizedUsedRunner;
import com.shroggle.TestUtil;
import com.shroggle.entity.User;
import com.shroggle.presentation.video.ActionWithLoginUser;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Artem Stasuk
 */
@RunWith(ParameterizedTestRunner.class)
@ParameterizedUsedRunner(ParameterizedTestRunnerWithServiceLocator.class)
public class ActionWithLoginUserTest {

    @Parameterized.Parameters
    public static Collection getParemeters() {
        return Arrays.asList(new Object[][]{
                {"/start.jsp", StartAction.class}
        });
    }

    @Before
    public void before() throws IllegalAccessException, InstantiationException {
        action = actionClass.newInstance();
    }

    public ActionWithLoginUserTest(
            final String destination, final Class<? extends ActionWithLoginUser> actionClass) {
        this.destination = destination;
        this.actionClass = actionClass;
    }

    @Test
    public void executeWithoutLogin() throws Exception {
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();
        Assert.assertNull(action.getLoginUser());
        Assert.assertEquals(destination, resolutionMock.getForwardToUrl());
    }

    @Test
    public void executeWithLogin() throws Exception {
        User user = TestUtil.createUserAndLogin();

        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();
        Assert.assertEquals(user, action.getLoginUser());
        Assert.assertEquals(destination, resolutionMock.getForwardToUrl());
    }

    private ActionWithLoginUser action;
    private final Class<? extends ActionWithLoginUser> actionClass;
    private final String destination;
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
    private final Persistance persistance = ServiceLocator.getPersistance();

}