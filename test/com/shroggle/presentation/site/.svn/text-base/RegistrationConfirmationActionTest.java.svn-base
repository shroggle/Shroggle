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

import com.shroggle.TestBaseWithMockService;
import com.shroggle.TestUtil;
import com.shroggle.logic.user.UserManager;
import com.shroggle.entity.User;
import com.shroggle.presentation.ResolutionMock;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * @author Stasuk Artem
 */
public class RegistrationConfirmationActionTest extends TestBaseWithMockService {

    @Test
    public void show() {
        User user = TestUtil.createUser("emai@email.com");
        user.setActiveted(null);
        user.setPassword("1");

        action.setRegistrationCode(new UserManager(user).createRegistrationCode());
        action.setUserId(user.getUserId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.show();

        Assert.assertEquals("/account/registration/registrationConfirmation.jsp", resolutionMock.getForwardToUrl());
        Assert.assertEquals(user, action.getLoginUser());
    }

    @Test
    public void cancel() {
        User user = TestUtil.createUser("emai@email.com");
        user.setActiveted(null);
        user.setPassword("1");

        action.setRegistrationCode(new UserManager(user).createRegistrationCode());
        action.setUserId(user.getUserId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.show();

        Assert.assertEquals("/account/registration/registrationConfirmation.jsp", resolutionMock.getForwardToUrl());
        Assert.assertEquals(user, action.getLoginUser());
    }

    @Test
    public void showWithNotEqualsCodeAndUser() {
        User user = TestUtil.createUser();
        user.setEmail("emai@email.com");
        user.setPassword("1");

        action.setRegistrationCode("1");
        action.setUserId(user.getUserId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.show();

        Assert.assertEquals("/account/registration/registrationLinkNotValid.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void showWithoutUserId() {
        final User user = TestUtil.createUser("emai@email.com");
        user.setPassword("1");

        action.setRegistrationCode("aa");
        final ResolutionMock resolutionMock = (ResolutionMock) action.show();

        Assert.assertEquals("/account/registration/registrationLinkNotValid.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void showIsExpired() {
        User user = new User();
        user.setRegistrationDate(new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24 * 10) + 1));
        persistance.putUser(user);

        action.setRegistrationCode("aa");
        action.show();

        Assert.assertNull("User can't be active with expired link", user.getActiveted());
    }

    @Test
    public void showWithLogin() {
        User user = TestUtil.createUserAndLogin("emai@email.com");
        user.setPassword("1");

        action.setRegistrationCode(new UserManager(user).createRegistrationCode());
        action.setUserId(user.getUserId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.show();

        Assert.assertEquals("/account/registration/registrationLinkActive.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void showWithActive() {
        User user = new User();
        user.setActiveted(new Date());
        persistance.putUser(user);

        action.setRegistrationCode(new UserManager(user).createRegistrationCode());
        action.setUserId(user.getUserId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.show();

        Assert.assertEquals("/account/registration/registrationLinkActive.jsp", resolutionMock.getForwardToUrl());
    }

    private final RegistrationConfirmationAction action = new RegistrationConfirmationAction();

}