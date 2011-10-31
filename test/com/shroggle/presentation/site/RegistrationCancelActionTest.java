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

import com.shroggle.TestUtil;
import com.shroggle.logic.user.UserManager;
import com.shroggle.entity.User;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.presentation.StartAction;
import com.shroggle.presentation.TestAction;
import junit.framework.Assert;
import org.junit.Test;

/**
 * @author Stasuk Artem
 */
public class RegistrationCancelActionTest extends TestAction<RegistrationCancelAction> {

    public RegistrationCancelActionTest() {
        super(RegistrationCancelAction.class);
    }

    @Test
    public void showWithoutRegistrationCode() {
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.show();

        Assert.assertEquals(RegistrationConfirmationAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeWithoutRegistrationCode() {
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.show();

        Assert.assertEquals(RegistrationConfirmationAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void show() {
        final User user = TestUtil.createUser();
        user.setActiveted(null);

        actionOrService.setUserId(user.getUserId());
        actionOrService.setRegistrationCode(new UserManager(user).createRegistrationCode());
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.show();

        Assert.assertNull(actionOrService.getLoginUser());
        Assert.assertEquals("/account/registration/registrationCancel.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void showWithNotEqualsCode() {
        final User user = TestUtil.createUser();
        user.setActiveted(null);

        actionOrService.setRegistrationCode("t");
        actionOrService.setUserId(user.getUserId());
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.show();

        Assert.assertEquals(RegistrationConfirmationAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void execute() {
        configStorage.get().setSupportEmail("f");

        final User user = TestUtil.createUser();
        user.setEmail("fgg");
        user.setActiveted(null);

        actionOrService.setRegistrationCode(new UserManager(user).createRegistrationCode());
        actionOrService.setUserId(user.getUserId());
        actionOrService.setCancel("a");
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.execute();

        Assert.assertEquals("a", actionOrService.getCancel());
        Assert.assertEquals(1, mailSender.getMails().size());
        Assert.assertEquals(configStorage.get().getSupportEmail(), mailSender.getMails().get(0).getTo());
        Assert.assertEquals(StartAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeWithNotEqualsCode() {
        configStorage.get().setSupportEmail("f");

        final User user = TestUtil.createUser();
        user.setEmail("fgg");
        user.setActiveted(null);

        actionOrService.setRegistrationCode("t");
        actionOrService.setUserId(user.getUserId());
        actionOrService.setCancel("a");
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.execute();

        Assert.assertEquals(RegistrationConfirmationAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeWithoutUserId() {
        User user = TestUtil.createUser();
        persistance.putUser(user);

        actionOrService.setRegistrationCode(new UserManager(user).createRegistrationCode());
        actionOrService.setCancel("a");
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.execute();

        Assert.assertEquals(RegistrationConfirmationAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void showWithNotFoundUser() {
        actionOrService.setRegistrationCode("t");
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.show();

        Assert.assertEquals(RegistrationConfirmationAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeWithNotFoundUserByRegistrationCode() {
        final User user = TestUtil.createUser();

        actionOrService.setUserId(user.getUserId());
        actionOrService.setRegistrationCode("t");
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.execute();

        Assert.assertEquals(RegistrationConfirmationAction.class, resolutionMock.getRedirectByAction());
    }

}