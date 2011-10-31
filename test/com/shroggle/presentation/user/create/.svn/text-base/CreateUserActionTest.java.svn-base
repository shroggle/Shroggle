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

package com.shroggle.presentation.user.create;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.User;
import com.shroggle.entity.UserOnSiteRight;
import com.shroggle.logic.user.ConfirmCodeGetter;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.ActionTestUtil;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.presentation.site.CreateSiteAction;
import com.shroggle.presentation.site.RegistrationFinishedAction;
import com.shroggle.presentation.account.dashboard.DashboardAction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.mail.MockMailSender;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.action.ActionBeanContext;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

/**
 * @author Stasuk Artem
 */
@RunWith(TestRunnerWithMockServices.class)
public class CreateUserActionTest {

    @Before
    public void before() {
        action.setContext(new ActionBeanContext());
    }

    @Test
    public void showForNew() {
        ResolutionMock resolutionMock = (ResolutionMock) action.show();

        Assert.assertNull(action.getLoginUser());
        Assert.assertFalse(action.getRequest().isAgree());
        Assert.assertEquals(CreateUserMode.NEW, action.getRequest().getMode());
        Assert.assertFalse(action.getRequest().isNotWantNewUser());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertNull(action.getRequest().getHasAccessToSitesMessage());
        Assert.assertEquals(resolutionMock.getForwardToUrl(), "/account/createUser.jsp");
    }

    @Test
    public void showForNewWithLogin() {
        final User loginUser = TestUtil.createUserAndLogin();

        ResolutionMock resolutionMock = (ResolutionMock) action.show();

        Assert.assertEquals(loginUser, action.getLoginUser());
        Assert.assertFalse(action.getRequest().isAgree());
        Assert.assertEquals(CreateUserMode.NEW, action.getRequest().getMode());
        Assert.assertFalse(action.getRequest().isNotWantNewUser());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(resolutionMock.getForwardToUrl(), "/account/createUser.jsp");
    }

    @Test
    public void showForInvitedExist() {
        User user = TestUtil.createUser("aa1");
        user.setPassword("1");
        user.setCity("ff1");

        User invitedUser = TestUtil.createUser("a");
        invitedUser.setPassword("1");
        invitedUser.setCity("USA");

        Site[] sites = new Site[10];
        for (int i = 0; i < sites.length; i++) {
            sites[i] = TestUtil.createSite("Site" + i, "url" + i);
            final UserOnSiteRight userOnSiteRight = TestUtil.createUserOnSiteRightActive(
                    invitedUser, sites[i], SiteAccessLevel.EDITOR);
            userOnSiteRight.setActive(false);
            TestUtil.createUserOnSiteRightActive(user, sites[i], SiteAccessLevel.ADMINISTRATOR);
        }

        action.getRequest().setConfirmCode(ConfirmCodeGetter.execute(invitedUser, user));
        action.getRequest().setUserId(user.getUserId());
        action.getRequest().setInvitedUserId(invitedUser.getUserId());
        ResolutionMock resolutionMock = (ResolutionMock) action.showForInvited();

        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(resolutionMock.getForwardToUrl(), "/account/createUser.jsp");
        Assert.assertEquals(CreateUserMode.INVITED_EXSIST, action.getRequest().getMode());
        Assert.assertEquals("a", action.getRequest().getOriginalEmail());
        Assert.assertEquals("a", action.getRequest().getEmail());
       Assert.assertEquals("You are invited to be an editor for the following site(s): Site0, Site1, Site2, Site3, Site4, Site5, Site6, Site7, Site8, Site9.",
                action.getRequest().getHasAccessToSitesMessage());
        Assert.assertTrue(action.getRequest().isAgree());
        Assert.assertTrue(action.getRequest().isNotWantNewUser());
        Assert.assertNull(action.getLoginUser());
    }

    @Test
    public void showForInvitedNew() {
        User user = TestUtil.createUser("aa1");
        user.setPassword("1");
        user.setCity("ff1");

        User invitedUser = TestUtil.createUser("a");
        invitedUser.setPassword(null);
        invitedUser.setCity("USA");

        Site[] sites = new Site[2];
        for (int i = 0; i < sites.length; i++) {
            sites[i] = TestUtil.createSite("Site" + i, "url" + i);
            final UserOnSiteRight userOnSiteRight = TestUtil.createUserOnSiteRightActive(
                    invitedUser, sites[i], SiteAccessLevel.EDITOR);
            userOnSiteRight.setActive(false);
            TestUtil.createUserOnSiteRightActive(user, sites[i], SiteAccessLevel.ADMINISTRATOR);
        }

        action.getRequest().setConfirmCode(ConfirmCodeGetter.execute(invitedUser, user));
        action.getRequest().setUserId(user.getUserId());
        action.getRequest().setInvitedUserId(invitedUser.getUserId());
        ResolutionMock resolutionMock = (ResolutionMock) action.showForInvited();

        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(resolutionMock.getForwardToUrl(), "/account/createUser.jsp");
        Assert.assertEquals(CreateUserMode.INVITED_NEW, action.getRequest().getMode());
        Assert.assertNull(action.getRequest().getOriginalEmail());
        Assert.assertEquals("a", action.getRequest().getEmail());
        Assert.assertEquals("a", action.getRequest().getEmailConfirm());
        Assert.assertEquals("You are invited to be an editor for the following site(s): Site0, Site1.",
                action.getRequest().getHasAccessToSitesMessage());
        Assert.assertTrue(action.getRequest().isAgree());
        Assert.assertTrue(action.getRequest().isNotWantNewUser());
        Assert.assertNull(action.getLoginUser());
    }

    @Test
    public void showForInvitedNewWithLogin() {
        User user = TestUtil.createUserAndLogin("aa1");
        user.setPassword("1");
        user.setCity("ff1");

        User invitedUser = TestUtil.createUser("a");
        invitedUser.setPassword(null);
        invitedUser.setCity("USA");

        Site[] sites = new Site[2];
        for (int i = 0; i < sites.length; i++) {
            sites[i] = TestUtil.createSite("Site" + i, "url" + i);
            final UserOnSiteRight userOnSiteRight = TestUtil.createUserOnSiteRightActive(
                    invitedUser, sites[i], SiteAccessLevel.EDITOR);
            userOnSiteRight.setActive(false);
            TestUtil.createUserOnSiteRightActive(user, sites[i], SiteAccessLevel.ADMINISTRATOR);
        }

        action.getRequest().setConfirmCode(ConfirmCodeGetter.execute(invitedUser, user));
        action.getRequest().setUserId(user.getUserId());
        action.getRequest().setInvitedUserId(invitedUser.getUserId());
        ResolutionMock resolutionMock = (ResolutionMock) action.showForInvited();

        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(resolutionMock.getForwardToUrl(), "/account/createUser.jsp");
        Assert.assertEquals(CreateUserMode.INVITED_NEW, action.getRequest().getMode());
        Assert.assertNull(action.getRequest().getOriginalEmail());
        Assert.assertEquals(user, action.getLoginUser());
        Assert.assertEquals("a", action.getRequest().getEmail());
        Assert.assertEquals("a", action.getRequest().getEmailConfirm());
        Assert.assertTrue(action.getRequest().isAgree());
        Assert.assertTrue(action.getRequest().isNotWantNewUser());
    }

    @Test
    public void showForInvitedNewWithoutInvitedUser() {
        configStorage.get().getRegistration().setConfirm(true);
        final User user = TestUtil.createUser();
        action.getRequest().setUserId(user.getUserId());

        ResolutionMock resolutionMock = (ResolutionMock) action.showForInvited();
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(resolutionMock.getForwardToUrl(), "/account/createUser.jsp");
        Assert.assertEquals(CreateUserMode.NEW, action.getRequest().getMode());
        Assert.assertTrue(action.getRequest().isAgree());
        Assert.assertTrue(action.getRequest().isNotWantNewUser());
        Assert.assertNull(action.getLoginUser());
    }

    @Test
    public void showForInvitedNewWithoutUser() {
        configStorage.get().getRegistration().setConfirm(true);
        final User invitedUser = TestUtil.createUser();
        invitedUser.setEmail("a");
        invitedUser.setCity("USA");

        action.getRequest().setInvitedUserId(invitedUser.getUserId());
        ResolutionMock resolutionMock = (ResolutionMock) action.showForInvited();

        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(resolutionMock.getForwardToUrl(), "/account/createUser.jsp");
        Assert.assertEquals(CreateUserMode.NEW, action.getRequest().getMode());
        Assert.assertTrue(action.getRequest().isAgree());
        Assert.assertTrue(action.getRequest().isNotWantNewUser());
        Assert.assertNull(action.getLoginUser());
    }

    @Test
    public void showForInviteDeleted() {
        final User user = TestUtil.createUser();
        user.setEmail("lll");

        User invitedUser = TestUtil.createUser();
        invitedUser.setEmail("a");
        invitedUser.setCity("fff");

        final Site site = TestUtil.createSite("Site", "url");
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        action.getRequest().setConfirmCode(ConfirmCodeGetter.execute(invitedUser, user));
        action.getRequest().setInvitedUserId(invitedUser.getUserId());
        action.getRequest().setUserId(user.getUserId());
        ResolutionMock resolutionMock = (ResolutionMock) action.showForInvited();

        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(resolutionMock.getForwardToUrl(), "/account/createUser.jsp");
        Assert.assertEquals(CreateUserMode.INVITED_DELETE, action.getRequest().getMode());
        Assert.assertEquals("a", action.getRequest().getEmail());
        Assert.assertEquals("", action.getRequest().getHasAccessToSitesMessage());
        Assert.assertTrue(action.getRequest().isNotWantNewUser());
        Assert.assertNull(action.getLoginUser());
    }

    @Test
    public void executeForNewWithoutRegistrationConfirm() {
        sessionStorage.setNoBotCode(null, "createUser", "a");
        action.getRequest().setEmail("a@a.com");
        action.getRequest().setEmailConfirm("a@a.com");
        action.getRequest().setPass("1");
        action.getRequest().setPasswordConfirm("1");
        action.getRequest().setAgree(true);
        action.getRequest().setNoBotCodeConfirm("a");
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();
        final User createdUser = persistance.getUserById(action.getRequest().getInvitedUserId());

        Assert.assertEquals(0, mockMailSender.getMails().size());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertNull(action.getLoginUser());
        Assert.assertEquals(createdUser.getUserId(), new UsersManager().getLoginedUser().getUserId());
        Assert.assertNotNull("Without confirm action must set activate!", createdUser.getActiveted());
        Assert.assertEquals(CreateSiteAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeForNewWithoutRegistrationConfirmWithLogin() {
        final User user = TestUtil.createUserAndLogin();
        user.setEmail("b@a.com");
        user.setCity("fff");

        sessionStorage.setNoBotCode(null, "createUser", "a");
        action.getRequest().setEmail("a@a.com");
        action.getRequest().setEmailConfirm("a@a.com");
        action.getRequest().setPass("1");
        action.getRequest().setPasswordConfirm("1");
        action.getRequest().setAgree(true);
        action.getRequest().setNoBotCodeConfirm("a");
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertNull(action.getLoginUser());
        Assert.assertEquals(0, mockMailSender.getMails().size());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        final User createdUser = persistance.getUserById(action.getRequest().getInvitedUserId());
        Assert.assertEquals(createdUser.getUserId(), new UsersManager().getLoginedUser().getUserId());
        Assert.assertNotNull("Without confirm action must set activate!", createdUser.getActiveted());
        Assert.assertEquals(CreateSiteAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeWithIncorrectEmail() {
        action.getRequest().setEmail("a");
        action.getRequest().setPass("1");
        action.getRequest().setPasswordConfirm("1");

        ResolutionMock resolutionMock = (ResolutionMock) action.execute();
        Assert.assertFalse(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(resolutionMock.getForwardToUrl(), "/account/createUser.jsp");
    }

    @Test
    public void executeWithEmptyEmail() throws Exception {
        action.getRequest().setEmail("");
        action.getRequest().setPass("1");
        action.getRequest().setPasswordConfirm("1");
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertFalse(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(resolutionMock.getForwardToUrl(), "/account/createUser.jsp");
    }

    @Test
    public void executeWithEmailContainSpace() {
        action.getRequest().setEmail("a @ a");
        action.getRequest().setPass("1");
        action.getRequest().setPasswordConfirm("1");

        ResolutionMock resolutionMock = (ResolutionMock) action.execute();
        Assert.assertFalse(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(resolutionMock.getForwardToUrl(), "/account/createUser.jsp");
    }

    @Test
    public void executeWithNotUniqueEmail() {
        User user = new User();
        user.setEmail("a@a");
        persistance.putUser(user);

        sessionStorage.setNoBotCode(null, "createUser", "a");
        action.getRequest().setEmail("a@a");
        action.getRequest().setEmailConfirm("a@a");
        action.getRequest().setPass("1");
        action.getRequest().setPasswordConfirm("1");
        action.getRequest().setAgree(true);
        action.getRequest().setNoBotCodeConfirm("a");

        ResolutionMock resolutionMock = (ResolutionMock) action.execute();
        Assert.assertFalse(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(resolutionMock.getForwardToUrl(), "/account/createUser.jsp");
    }

    @Test
    public void executeWithUpEmail() {
        configStorage.get().getRegistration().setConfirm(true);
        sessionStorage.setNoBotCode(null, "createUser", "a");
        action.getRequest().setNotWantNewUser(true);
        action.getRequest().setEmail("a@A.com");
        action.getRequest().setLastName("aaaaaaa");
        action.getRequest().setTelephone("121324234234");
        action.getRequest().setEmailConfirm("a@A.com");
        action.getRequest().setPass("1");
        action.getRequest().setPasswordConfirm("1");
        action.getRequest().setAgree(true);
        action.getRequest().setNoBotCodeConfirm("a");

        ResolutionMock resolutionMock = (ResolutionMock) action.execute();
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertNotNull("a@a.com", persistance.getUserByEmail("a@a.com"));
        Assert.assertEquals(RegistrationFinishedAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeWithNotEqualsNoBotCode() {
        action.getRequest().setEmail("a@a.com");
        action.getRequest().setMode(CreateUserMode.NEW);
        action.execute();

        Assert.assertEquals(1, action.getContext().getValidationErrors().get("noBotCodeConfirm").size());
    }

    @Test
    public void executeWithEmptyPasswordForNew() {
        sessionStorage.setNoBotCode(null, "createUser", "a");

        action.getRequest().setEmail("a@A.com");
        action.getRequest().setEmailConfirm("a@a.com");
        action.getRequest().setAgree(true);
        action.getRequest().setNoBotCodeConfirm("a");
        action.getRequest().setPasswordConfirm("1");

        ResolutionMock resolutionMock = (ResolutionMock) action.execute();
        Assert.assertEquals(1, action.getContext().getValidationErrors().get("password").size());
        Assert.assertEquals("/account/createUser.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void executeInvitedExistWithEmptyPassword() {
        final Site site = TestUtil.createSite();

        final User user = TestUtil.createUser();
        final UserOnSiteRight userOnSiteRight = TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        userOnSiteRight.setActive(true);

        final User invitedUser = TestUtil.createUser();
        invitedUser.setEmail("a");
        invitedUser.setCity("fff");
        invitedUser.setActiveted(new Date());
        invitedUser.setEmail("a@a");

        final UserOnSiteRight invitedUserOnSiteRight = TestUtil.createUserOnSiteRightActiveAdmin(invitedUser, site);
        invitedUserOnSiteRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        invitedUserOnSiteRight.setActive(false);

        sessionStorage.setNoBotCode(null, "createUser", "a");

        action.getRequest().setConfirmCode(ConfirmCodeGetter.execute(invitedUser, user));
        action.getRequest().setMode(CreateUserMode.INVITED_EXSIST);
        action.getRequest().setEmail("a@a");
        action.getRequest().setInvitedUserId(invitedUser.getUserId());
        action.getRequest().setUserId(user.getUserId());
        action.getRequest().setEmailConfirm("a@a");
        action.getRequest().setAgree(true);
        action.getRequest().setNotWantNewUser(true);
        action.getRequest().setNoBotCodeConfirm("a");
        action.getRequest().setPasswordConfirm("1");

        ResolutionMock resolutionMock = (ResolutionMock) action.execute();
        Assert.assertEquals(0, action.getContext().getValidationErrors().size());
        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeWithNotEqualsPassword() {
        sessionStorage.setNoBotCode(null, "createUser", "a");
        action.getRequest().setNoBotCodeConfirm("a");
        action.getRequest().setEmail("artem.stasuk@gmail.com");
        action.getRequest().setEmailConfirm("artem.stasuk@gmail.com");
        action.getRequest().setPass("1");
        action.getRequest().setAgree(true);
        action.getRequest().setPasswordConfirm("a@A");

        action.execute();

        ActionTestUtil.assertValidationErrors(action, "password");
    }

    @Test
    public void executeWithNotUniqueUserEmailInOtherCase() {
        User user = new User();
        user.setEmail("a@a");
        persistance.putUser(user);

        sessionStorage.setNoBotCode(null, "createUser", "a");
        action.getRequest().setEmail("A@a");
        action.getRequest().setEmailConfirm("a@a");
        action.getRequest().setPass("1");
        action.getRequest().setPasswordConfirm("1");
        action.getRequest().setAgree(true);
        action.getRequest().setNoBotCodeConfirm("a");

        ResolutionMock resolutionMock = (ResolutionMock) action.execute();
        Assert.assertEquals(1, action.getContext().getValidationErrors().get("email").size());
        Assert.assertEquals(resolutionMock.getForwardToUrl(), "/account/createUser.jsp");
    }

    @Test
    public void executeForNew() {
        configStorage.get().getRegistration().setConfirm(true);
        sessionStorage.setNoBotCode(null, "createUser", "a");

        action.getRequest().setMode(CreateUserMode.NEW);
        action.getRequest().setNotWantNewUser(true);
        action.getRequest().setEmail("a@a.com");
        action.getRequest().setEmailConfirm("a@a.com");
        action.getRequest().setPass("1");
        action.getRequest().setPasswordConfirm("1");
        action.getRequest().setTelephone("t1");
        action.getRequest().setLastName("tema");
        action.getRequest().setAgree(true);
        action.getRequest().setPostalCode("pc");
        action.getRequest().setFirstName("fn");
        action.getRequest().setNoBotCodeConfirm("a");

        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        final User createdUser = persistance.getUserById(action.getRequest().getInvitedUserId());
        Assert.assertEquals("t1", action.getRequest().getTelephone());
        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(RegistrationFinishedAction.class, resolutionMock.getRedirectByAction());
        Assert.assertNull(new UsersManager().getLoginedUser());

        Assert.assertNull(createdUser.getActiveted());
        Assert.assertNull(
                "Action must reset capcha after use it.",
                sessionStorage.getNoBotCode(action, "createUser"));
        Assert.assertEquals(0, createdUser.getUserOnSiteRights().size());
        Assert.assertEquals(action.getRequest().getEmail(), createdUser.getEmail());
        Assert.assertEquals(action.getRequest().getPass(), createdUser.getPassword());
        Assert.assertEquals(action.getRequest().getTelephone(), createdUser.getTelephone());
        Assert.assertEquals(action.getRequest().getLastName(), createdUser.getLastName());
        Assert.assertEquals(action.getRequest().getPostalCode(), createdUser.getPostalCode());
        Assert.assertEquals(action.getRequest().getFirstName(), createdUser.getFirstName());
    }

    @Test
    public void executeForInvitedExist() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUser();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final User invitedUser = TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.EDITOR);
        invitedUser.setEmail("a");
        invitedUser.setCity("fff");

        invitedUser.getUserOnSiteRights().get(0).setActive(false);
        invitedUser.setEmail("a@b");

        configStorage.get().getRegistration().setConfirm(true);
        sessionStorage.setNoBotCode(null, "createUser", "a");

        action.getRequest().setConfirmCode(ConfirmCodeGetter.execute(invitedUser, user));
        action.getRequest().setUserId(user.getUserId());
        action.getRequest().setInvitedUserId(invitedUser.getUserId());
        action.getRequest().setNotWantNewUser(true);
        action.getRequest().setEmail("a@b");
        action.getRequest().setMode(CreateUserMode.INVITED_EXSIST);
        action.getRequest().setAgree(true);
        action.getRequest().setNoBotCodeConfirm("a");

        ResolutionMock resolutionMock = (ResolutionMock) action.execute();
        Assert.assertEquals(0, mockMailSender.getMails().size());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
        Assert.assertEquals(2, persistance.getUsersCount());
        final User createdUser = persistance.getUserById(action.getRequest().getInvitedUserId());
        Assert.assertEquals(invitedUser.getUserId(), new UsersManager().getLoginedUser().getUserId());
        Assert.assertEquals(invitedUser, createdUser);
        Assert.assertTrue(createdUser.getUserOnSiteRights().get(0).isActive());
    }

    @Test
    public void executeForInvitedExistWithoutActivation() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUser();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final User invitedUser = TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.EDITOR);
        invitedUser.setActiveted(null);
        invitedUser.setEmail("a");
        invitedUser.setCity("fff");

        invitedUser.getUserOnSiteRights().get(0).setActive(false);
        invitedUser.setEmail("a@b");

        configStorage.get().getRegistration().setConfirm(true);
        sessionStorage.setNoBotCode(null, "createUser", "a");

        action.getRequest().setConfirmCode(ConfirmCodeGetter.execute(invitedUser, user));
        action.getRequest().setUserId(user.getUserId());
        action.getRequest().setInvitedUserId(invitedUser.getUserId());
        action.getRequest().setNotWantNewUser(true);
        action.getRequest().setEmail("a@b");
        action.getRequest().setMode(CreateUserMode.INVITED_EXSIST);
        action.getRequest().setAgree(true);
        action.getRequest().setNoBotCodeConfirm("a");

        ResolutionMock resolutionMock = (ResolutionMock) action.execute();
        Assert.assertEquals(0, mockMailSender.getMails().size());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
        Assert.assertEquals(2, persistance.getUsersCount());
        final User createdUser = persistance.getUserById(action.getRequest().getInvitedUserId());
        Assert.assertEquals(invitedUser.getUserId(), new UsersManager().getLoginedUser().getUserId());
        Assert.assertEquals(invitedUser, createdUser);
        Assert.assertTrue(createdUser.getUserOnSiteRights().get(0).isActive());
    }

    @Test
    public void executeForInvitedExistWithoutNoBotCode() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUser();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final User invitedUser = TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.EDITOR);
        invitedUser.setEmail("a");
        invitedUser.setCity("fff");

        invitedUser.getUserOnSiteRights().get(0).setActive(false);
        invitedUser.setEmail("a@b");

        configStorage.get().getRegistration().setConfirm(true);
        sessionStorage.setNoBotCode(null, "createUser", "a");

        action.getRequest().setConfirmCode(ConfirmCodeGetter.execute(invitedUser, user));
        action.getRequest().setUserId(user.getUserId());
        action.getRequest().setInvitedUserId(invitedUser.getUserId());
        action.getRequest().setNotWantNewUser(true);
        action.getRequest().setEmail("a@b");
        action.getRequest().setMode(CreateUserMode.INVITED_EXSIST);
        action.getRequest().setAgree(true);

        ResolutionMock resolutionMock = (ResolutionMock) action.execute();
        Assert.assertEquals(0, mockMailSender.getMails().size());
        Assert.assertTrue(action.getContext().getValidationErrors().containsKey("noBotCodeConfirm"));
        Assert.assertEquals("/account/createUser.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void executeForInvitedExistWithoutRight() {
        final User user = TestUtil.createUser();
        final User invitedUser = TestUtil.createUser();
        invitedUser.setEmail("a");
        invitedUser.setCity("fff");

        configStorage.get().getRegistration().setConfirm(true);
        sessionStorage.setNoBotCode(null, "createUser", "a");

        action.getRequest().setConfirmCode(ConfirmCodeGetter.execute(invitedUser, user));
        action.getRequest().setInvitedUserId(invitedUser.getUserId());
        action.getRequest().setUserId(user.getUserId());
        action.getRequest().setNotWantNewUser(true);
        action.getRequest().setEmail("a@be.com");
        action.getRequest().setEmailConfirm("a@be.com");
        action.getRequest().setPass("f");
        action.getRequest().setPasswordConfirm("f");
        action.getRequest().setTelephone("1");
        action.getRequest().setMode(CreateUserMode.INVITED_EXSIST);
        action.getRequest().setAgree(true);
        action.getRequest().setLastName("hh");
        action.getRequest().setNoBotCodeConfirm("a");

        ResolutionMock resolutionMock = (ResolutionMock) action.execute();
        Assert.assertEquals(0, mockMailSender.getMails().size());
        Assert.assertEquals(CreateUserMode.INVITED_DELETE, action.getRequest().getMode());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(CreateSiteAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeForInvitedExistWithoutRightAndAgree() {
        final User user = TestUtil.createUser();
        final User invitedUser = TestUtil.createUser();
        invitedUser.setEmail("a");
        invitedUser.setCity("fff");

        configStorage.get().getRegistration().setConfirm(true);
        sessionStorage.setNoBotCode(null, "createUser", "a");

        action.getRequest().setConfirmCode(ConfirmCodeGetter.execute(invitedUser, user));
        action.getRequest().setInvitedUserId(invitedUser.getUserId());
        action.getRequest().setUserId(user.getUserId());
        action.getRequest().setNotWantNewUser(true);
        action.getRequest().setEmail("a@be.com");
        action.getRequest().setEmailConfirm("a@be.com");
        action.getRequest().setPass("f");
        action.getRequest().setPasswordConfirm("f");
        action.getRequest().setTelephone("1");
        action.getRequest().setMode(CreateUserMode.INVITED_EXSIST);
        action.getRequest().setAgree(false);
        action.getRequest().setLastName("hh");
        action.getRequest().setNoBotCodeConfirm("a");

        ResolutionMock resolutionMock = (ResolutionMock) action.execute();
        Assert.assertEquals(0, mockMailSender.getMails().size());
        Assert.assertEquals(CreateUserMode.INVITED_DELETE, action.getRequest().getMode());
        Assert.assertEquals(1, action.getContext().getValidationErrors().size());
        Assert.assertNotNull(action.getContext().getValidationErrors().get("error"));
        Assert.assertEquals("/account/createUser.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void executeForInvitedExistWithoutUser() {
        final User invitedUser = TestUtil.createUser();

        configStorage.get().getRegistration().setConfirm(true);
        sessionStorage.setNoBotCode(null, "createUser", "a");

        action.getRequest().setInvitedUserId(invitedUser.getUserId());
        action.getRequest().setNotWantNewUser(true);
        action.getRequest().setEmail("a@b");
        action.getRequest().setMode(CreateUserMode.INVITED_EXSIST);
        action.getRequest().setAgree(true);
        action.getRequest().setNoBotCodeConfirm("a");

        ResolutionMock resolutionMock = (ResolutionMock) action.execute();
        Assert.assertEquals(CreateUserMode.NEW, action.getRequest().getMode());
        Assert.assertFalse(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals("/account/createUser.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void executeForInvitedExistWithoutInvitedUser() {
        final User user = TestUtil.createUser();
        user.setEmail("a");
        user.setCity("fff");

        configStorage.get().getRegistration().setConfirm(true);
        sessionStorage.setNoBotCode(null, "createUser", "a");

        action.getRequest().setUserId(user.getUserId());
        action.getRequest().setNotWantNewUser(true);
        action.getRequest().setEmail("a@b");
        action.getRequest().setMode(CreateUserMode.INVITED_EXSIST);
        action.getRequest().setAgree(true);
        action.getRequest().setNoBotCodeConfirm("a");

        ResolutionMock resolutionMock = (ResolutionMock) action.execute();
        Assert.assertEquals(CreateUserMode.NEW, action.getRequest().getMode());
        Assert.assertFalse(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals("/account/createUser.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void executeForInvitedExistWithWantNewUser() {
        final Site site = TestUtil.createSite();

        final User user = TestUtil.createUser();
        final UserOnSiteRight userOnSiteRight = TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        userOnSiteRight.setActive(true);

        final User invitedUser = TestUtil.createUser();
        invitedUser.setEmail("F@a.com");

        final UserOnSiteRight invitedUserOnSiteRight = TestUtil.createUserOnSiteRightActiveAdmin(invitedUser, site);
        invitedUserOnSiteRight.setActive(false);

        configStorage.get().getRegistration().setConfirm(true);
        sessionStorage.setNoBotCode(null, "createUser", "a");

        action.getRequest().setConfirmCode(ConfirmCodeGetter.execute(invitedUser, user));
        action.getRequest().setMode(CreateUserMode.INVITED_EXSIST);
        action.getRequest().setEmail("a@a.com");
        action.getRequest().setEmailConfirm("a@a.com");
        action.getRequest().setPass("1");
        action.getRequest().setPasswordConfirm("1");
        action.getRequest().setTelephone("t");
        action.getRequest().setLastName("ln");
        action.getRequest().setAgree(true);
        action.getRequest().setUserId(user.getUserId());
        action.getRequest().setInvitedUserId(invitedUser.getUserId());
        action.getRequest().setNotWantNewUser(false);
        action.getRequest().setNoBotCodeConfirm("a");

        ResolutionMock resolutionMock = (ResolutionMock) action.execute();
        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(RegistrationFinishedAction.class, resolutionMock.getRedirectByAction());
        Assert.assertEquals(3, persistance.getUsersCount());
        final User createdUser = persistance.getUserById(action.getRequest().getInvitedUserId());
        Assert.assertTrue(
                "Action must remove rights on site and move it in new user!",
                invitedUser.getUserOnSiteRights().isEmpty());
        Assert.assertTrue(invitedUser.getUserOnSiteRights().isEmpty());
        Assert.assertNull(new UsersManager().getLoginedUser());
        Assert.assertNotSame(invitedUser, createdUser);
        Assert.assertTrue(createdUser.getUserOnSiteRights().get(0).isActive());
    }

    @Test
    public void executeForInvitedExistWithWantNewUserErrorPassword() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUser();
        final UserOnSiteRight userOnSiteRight = TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        userOnSiteRight.setActive(true);

        final User invitedUser = TestUtil.createUser();
        invitedUser.setEmail("F@a.com");

        final UserOnSiteRight invitedUserOnSiteRight = TestUtil.createUserOnSiteRightActiveAdmin(invitedUser, site);
        invitedUserOnSiteRight.setActive(false);

        configStorage.get().getRegistration().setConfirm(true);
        sessionStorage.setNoBotCode(null, "createUser", "a");

        action.getRequest().setConfirmCode(ConfirmCodeGetter.execute(invitedUser, user));
        action.getRequest().setMode(CreateUserMode.INVITED_EXSIST);
        action.getRequest().setEmail("a@a.com");
        action.getRequest().setEmailConfirm("a@a.com");
        action.getRequest().setPass("12");
        action.getRequest().setPasswordConfirm("1");
        action.getRequest().setTelephone("t");
        action.getRequest().setLastName("ln");
        action.getRequest().setAgree(true);
        action.getRequest().setUserId(user.getUserId());
        action.getRequest().setInvitedUserId(invitedUser.getUserId());
        action.getRequest().setNotWantNewUser(false);
        action.getRequest().setNoBotCodeConfirm("a");

        ResolutionMock resolutionMock = (ResolutionMock) action.execute();
        ActionTestUtil.assertValidationErrors(action, "password");
        Assert.assertEquals("/account/createUser.jsp", resolutionMock.getForwardToUrl());
        Assert.assertEquals(2, persistance.getUsersCount());
    }

    @Ignore//todo fix this. Artem
    @Test
    public void executeForInvitedExistWithWantNewUserWithoutConfirmation() {
        final Site site = TestUtil.createSite();

        final User user = TestUtil.createUser();
        final UserOnSiteRight userOnSiteRight = TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        userOnSiteRight.setActive(true);

        final User invitedUser = TestUtil.createUser();
        invitedUser.setEmail("F@a.com");

        final UserOnSiteRight invitedUserOnSiteRight = TestUtil.createUserOnSiteRightActiveAdmin(invitedUser, site);
        invitedUserOnSiteRight.setActive(false);

        configStorage.get().getRegistration().setConfirm(false);
        sessionStorage.setNoBotCode(null, "createUser", "a");

        action.getRequest().setConfirmCode(ConfirmCodeGetter.execute(invitedUser, user));
        action.getRequest().setMode(CreateUserMode.INVITED_EXSIST);
        action.getRequest().setEmail("a@a.com");
        action.getRequest().setEmailConfirm("a@a.com");
        action.getRequest().setPass("1");
        action.getRequest().setPasswordConfirm("1");
        action.getRequest().setTelephone("t");
        action.getRequest().setLastName("ln");
        action.getRequest().setAgree(true);
        action.getRequest().setUserId(user.getUserId());
        action.getRequest().setInvitedUserId(invitedUser.getUserId());
        action.getRequest().setNotWantNewUser(false);
        action.getRequest().setNoBotCodeConfirm("a");



        ResolutionMock resolutionMock = (ResolutionMock) action.execute();



        Assert.assertEquals(0, mockMailSender.getMails().size());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(CreateSiteAction.class, resolutionMock.getRedirectByAction());
        Assert.assertEquals(3, persistance.getUsersCount());
        final User createdUser = persistance.getUserById(action.getRequest().getInvitedUserId());
        Assert.assertTrue(
                "Action must remove rights on site and move it in new user!",
                invitedUser.getUserOnSiteRights().isEmpty());
        Assert.assertTrue(invitedUser.getUserOnSiteRights().isEmpty());
        Assert.assertNull(createdUser.getActiveted());
        Assert.assertEquals(createdUser.getUserId(), new UsersManager().getLoginedUser().getUserId());
        Assert.assertNotSame(invitedUser, createdUser);
        Assert.assertTrue(createdUser.getUserOnSiteRights().get(0).isActive());
    }

    @Test
    public void executeForInvitedExistWithWantNewUserWithSameEmail() {
        final Site site = TestUtil.createSite();

        final User user = TestUtil.createUser("a@b");
        final UserOnSiteRight userOnSiteRight = TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        userOnSiteRight.setActive(true);

        final User invitedUser = TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.EDITOR);
        invitedUser.getUserOnSiteRights().get(0).setActive(false);
        invitedUser.setEmail("a@b");

        configStorage.get().getRegistration().setConfirm(true);
        sessionStorage.setNoBotCode(null, "createUser", "a");

        action.getRequest().setMode(CreateUserMode.INVITED_EXSIST);
        action.getRequest().setEmail("a@b");
        action.getRequest().setEmailConfirm("a@b");
        action.getRequest().setPass("1");
        action.getRequest().setPasswordConfirm("1");
        action.getRequest().setTelephone("t");
        action.getRequest().setLastName("ln");
        action.getRequest().setAgree(true);
        action.getRequest().setInvitedUserId(invitedUser.getUserId());
        action.getRequest().setNotWantNewUser(false);
        action.getRequest().setNoBotCodeConfirm("a");
        action.execute();

        Assert.assertFalse(
                "Action create new user with not unqiue email!",
                action.getContext().getValidationErrors().isEmpty());
    }

    @Test
    public void executeForInvitedNewWithNewEmail() {
        final User user = TestUtil.createUser("a@b.com");
        final Site site = TestUtil.createSite();
        final UserOnSiteRight userOnSiteRight = TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        userOnSiteRight.setActive(true);

        final User invitedUser = TestUtil.createUser();
        invitedUser.setActiveted(null);
        invitedUser.setEmail("a@b.com");

        final UserOnSiteRight invitedUserOnSiteRight = TestUtil.createUserOnSiteRightActiveAdmin(invitedUser, site);
        invitedUserOnSiteRight.setActive(false);

        sessionStorage.setNoBotCode(null, "createUser", "a");
        configStorage.get().getRegistration().setConfirm(true);

        action.getRequest().setConfirmCode(ConfirmCodeGetter.execute(invitedUser, user));
        action.getRequest().setMode(CreateUserMode.INVITED_NEW);
        action.getRequest().setEmail("a@a.com");
        action.getRequest().setEmailConfirm("a@a.com");
        action.getRequest().setPass("1");
        action.getRequest().setPasswordConfirm("1");
        action.getRequest().setAgree(true);
        action.getRequest().setUserId(user.getUserId());
        action.getRequest().setInvitedUserId(invitedUser.getUserId());
        action.getRequest().setNoBotCodeConfirm("a");
        action.getRequest().setTelephone("a");
        action.getRequest().setLastName("a");
        action.getRequest().setNotWantNewUser(true);

        ResolutionMock resolutionMock = (ResolutionMock) action.execute();
        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(RegistrationFinishedAction.class, resolutionMock.getRedirectByAction());
        Assert.assertEquals(2, persistance.getUsersCount());
        final User createdUser = persistance.getUserById(action.getRequest().getInvitedUserId());
        Assert.assertEquals("a@a.com", createdUser.getEmail());
        Assert.assertEquals(invitedUser, createdUser);
        Assert.assertTrue(createdUser.getUserOnSiteRights().get(0).isActive());
    }

    @Test
    public void executeForInvitedNew() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUser();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final User invitedUser = TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.EDITOR);
        invitedUser.getUserOnSiteRights().get(0).setActive(false);
        invitedUser.setEmail("a@b.com");

        configStorage.get().getRegistration().setConfirm(true);
        sessionStorage.setNoBotCode(null, "createUser", "a");

        action.getRequest().setConfirmCode(ConfirmCodeGetter.execute(invitedUser, user));
        action.getRequest().setMode(CreateUserMode.INVITED_NEW);
        action.getRequest().setEmail("a@b.com");
        action.getRequest().setEmailConfirm("a@a.com");
        action.getRequest().setPass("1");
        action.getRequest().setPasswordConfirm("1");
        action.getRequest().setTelephone("t1");
        action.getRequest().setLastName("tema");
        action.getRequest().setAgree(true);
        action.getRequest().setInvitedUserId(invitedUser.getUserId());
        action.getRequest().setUserId(user.getUserId());
        action.getRequest().setNoBotCodeConfirm("a");

        ResolutionMock resolutionMock = (ResolutionMock) action.execute();
        Assert.assertEquals("t1", action.getRequest().getTelephone());
        Assert.assertEquals(0, mockMailSender.getMails().size());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
        Assert.assertEquals(2, persistance.getUsersCount());
        final User createdUser = persistance.getUserById(action.getRequest().getInvitedUserId());
        Assert.assertNotNull("Action must activate invited users!", createdUser.getActiveted());
        Assert.assertEquals(new UsersManager().getLoginedUser().getUserId(), createdUser.getUserId());
        Assert.assertEquals(invitedUser, createdUser);
        Assert.assertTrue(createdUser.getUserOnSiteRights().get(0).isActive());
    }

    @Test
    public void executeForInvitedNewWithoutConfirmCode() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUser();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final User invitedUser = TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.EDITOR);
        invitedUser.getUserOnSiteRights().get(0).setActive(false);
        invitedUser.setEmail("a@b.com");

        configStorage.get().getRegistration().setConfirm(true);
        sessionStorage.setNoBotCode(null, "createUser", "a");

        action.getRequest().setMode(CreateUserMode.INVITED_NEW);
        action.getRequest().setEmail("a@b.com");
        action.getRequest().setEmailConfirm("a@a.com");
        action.getRequest().setPass("1");
        action.getRequest().setPasswordConfirm("1");
        action.getRequest().setTelephone("t1");
        action.getRequest().setLastName("tema");
        action.getRequest().setAgree(true);
        action.getRequest().setInvitedUserId(invitedUser.getUserId());
        action.getRequest().setUserId(user.getUserId());
        action.getRequest().setNoBotCodeConfirm("a");

        ResolutionMock resolutionMock = (ResolutionMock) action.execute();
        Assert.assertEquals(CreateUserMode.NEW, action.getRequest().getMode());
        Assert.assertEquals("/account/createUser.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void executeForInvitedDelete() {
        final User user = TestUtil.createUser();
        final User invitedUser = TestUtil.createUser();
        invitedUser.setActiveted(null);
        invitedUser.setEmail("a@b.com");

        configStorage.get().getRegistration().setConfirm(true);
        sessionStorage.setNoBotCode(null, "createUser", "a");

        action.getRequest().setConfirmCode(ConfirmCodeGetter.execute(invitedUser, user));
        action.getRequest().setMode(CreateUserMode.INVITED_DELETE);
        action.getRequest().setEmail("a@b.com");
        action.getRequest().setEmailConfirm("a@a.com");
        action.getRequest().setPass("1");
        action.getRequest().setPasswordConfirm("1");
        action.getRequest().setTelephone("t1");
        action.getRequest().setLastName("tema");
        action.getRequest().setAgree(true);
        action.getRequest().setInvitedUserId(invitedUser.getUserId());
        action.getRequest().setUserId(user.getUserId());
        action.getRequest().setNoBotCodeConfirm("a");

        ResolutionMock resolutionMock = (ResolutionMock) action.execute();
        Assert.assertEquals(CreateUserMode.INVITED_DELETE, action.getRequest().getMode());
        Assert.assertEquals("t1", action.getRequest().getTelephone());
        Assert.assertEquals(0, mockMailSender.getMails().size());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(CreateSiteAction.class, resolutionMock.getRedirectByAction());
        Assert.assertEquals(2, persistance.getUsersCount());
        final User createdUser = persistance.getUserById(action.getRequest().getInvitedUserId());
        Assert.assertNotNull("Action must activate invited users!", createdUser.getActiveted());
        Assert.assertEquals(invitedUser.getUserId(), new UsersManager().getLoginedUser().getUserId());
        Assert.assertEquals(new UsersManager().getLoginedUser().getUserId(), createdUser.getUserId());
        Assert.assertEquals(invitedUser, createdUser);
        Assert.assertEquals(0, createdUser.getUserOnSiteRights().size());
    }

    @Test
    public void executeWithoutLastName() throws Exception {
        configStorage.get().getRegistration().setConfirm(true);
        sessionStorage.setNoBotCode(null, "createUser", "a");

        action.getRequest().setEmail("a@a.com");
        action.getRequest().setEmailConfirm("a@a.com");
        action.getRequest().setPass("1");
        action.getRequest().setPasswordConfirm("1");
        action.getRequest().setNoBotCodeConfirm("a");

        action.execute();

        Assert.assertTrue(action.getContext().getValidationErrors().size() > 0);
    }

    private final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
    private final MockMailSender mockMailSender = (MockMailSender) ServiceLocator.getMailSender();
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
    private final CreateUserAction action = new CreateUserAction();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
