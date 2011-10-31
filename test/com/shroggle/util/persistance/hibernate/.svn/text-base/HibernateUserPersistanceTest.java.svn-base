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

package com.shroggle.util.persistance.hibernate;

import com.shroggle.entity.ChildSiteSettings;
import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.entity.UserOnSiteRight;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class HibernateUserPersistanceTest extends HibernatePersistanceTestBase {

    @Test
    public void put() {
        User user1 = new User();
        user1.setEmail("aa");
        user1.setRegistrationDate(new Date());
        persistance.putUser(user1);

        User user2 = new User();
        user2.setRegistrationDate(new Date());
        user2.setEmail("aa1");

        persistance.putUser(user2);

        User findUser1 = HibernateManager.get().find(User.class, user1.getUserId());
        User findUser2 = HibernateManager.get().find(User.class, user2.getUserId());
        Assert.assertEquals("aa", findUser1.getEmail());
        Assert.assertEquals("aa1", findUser2.getEmail());
    }

    @Test
    public void getChildSiteSettingsByUserIdWithout() {
        User user = new User();
        user.setEmail("aa");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        final List<ChildSiteSettings> childSiteSettingses =
                persistance.getChildSiteSettingsByUserId(user.getUserId());
        Assert.assertNotNull(childSiteSettingses);
        Assert.assertEquals(0, childSiteSettingses.size());
    }

    @Test
    public void getChildSiteSettingsByUserId() {
        User user = new User();
        user.setEmail("aa");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        ChildSiteSettings childSiteSettings = new ChildSiteSettings();childSiteSettings.getSitePaymentSettings().setUserId(-1);
        childSiteSettings.setWelcomeText("");
        childSiteSettings.setUserId(user.getUserId());
        childSiteSettings.setCreatedDate(new Date());
        persistance.putChildSiteSettings(childSiteSettings);

        final List<ChildSiteSettings> childSiteSettingses =
                persistance.getChildSiteSettingsByUserId(user.getUserId());
        Assert.assertNotNull(childSiteSettingses);
        Assert.assertEquals(1, childSiteSettingses.size());
        Assert.assertEquals(childSiteSettings, childSiteSettingses.get(0));
    }

    @Test
    public void getChildSiteSettingsByUserIdNotForMe() {
        User user1 = new User();
        user1.setEmail("aa");
        user1.setRegistrationDate(new Date());
        persistance.putUser(user1);

        User user2 = new User();
        user2.setEmail("aas");
        user2.setRegistrationDate(new Date());
        persistance.putUser(user2);

        ChildSiteSettings childSiteSettings = new ChildSiteSettings();childSiteSettings.getSitePaymentSettings().setUserId(-1);
        childSiteSettings.getSitePaymentSettings().setUserId(-1);
        childSiteSettings.setWelcomeText("");
        childSiteSettings.setUserId(user1.getUserId());
        childSiteSettings.setCreatedDate(new Date());
        persistance.putChildSiteSettings(childSiteSettings);

        final List<ChildSiteSettings> childSiteSettingses =
                persistance.getChildSiteSettingsByUserId(user2.getUserId());
        Assert.assertNotNull(childSiteSettingses);
        Assert.assertEquals(0, childSiteSettingses.size());
    }

    @Test
    public void getNotActiveUserOnSiteRightsByUserAndInvitedUserWithoutSites() {
        User invitedUser = new User();
        invitedUser.setEmail("aa");
        invitedUser.setRegistrationDate(new Date());
        persistance.putUser(invitedUser);

        User user = new User();
        user.setRegistrationDate(new Date());
        user.setEmail("aa1");
        persistance.putUser(user);

        final List<UserOnSiteRight> userOnSiteRights =
                persistance.getNotActiveUserOnSiteRightsByUserAndInvitedUser(
                        user.getUserId(), invitedUser.getUserId());
        Assert.assertEquals(0, userOnSiteRights.size());
    }

    @Test
    public void getNotActiveUserOnSiteRightsByUserAndInvitedUserWithoutNotActive() {
        User invitedUser = new User();
        invitedUser.setEmail("aa");
        invitedUser.setRegistrationDate(new Date());
        persistance.putUser(invitedUser);

        final Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.getThemeId().setTemplateDirectory("a");
        site.getThemeId().setThemeCss("a");
        site.setTitle("g");
        site.setSubDomain("a");
        persistance.putSite(site);

        User user = new User();
        user.setRegistrationDate(new Date());
        user.setEmail("aa1");
        persistance.putUser(user);

        final UserOnSiteRight userOnSiteRight = new UserOnSiteRight();
        site.addUserOnSiteRight(userOnSiteRight);
        user.addUserOnSiteRight(userOnSiteRight);
        userOnSiteRight.setActive(true);
        persistance.putUserOnSiteRight(userOnSiteRight);

        final UserOnSiteRight invitedUserOnSiteRight = new UserOnSiteRight();
        site.addUserOnSiteRight(invitedUserOnSiteRight);
        invitedUserOnSiteRight.setActive(true);
        invitedUser.addUserOnSiteRight(invitedUserOnSiteRight);
        persistance.putUserOnSiteRight(invitedUserOnSiteRight);

        final List<UserOnSiteRight> userOnSiteRights =
                persistance.getNotActiveUserOnSiteRightsByUserAndInvitedUser(
                        user.getUserId(), invitedUser.getUserId());
        Assert.assertEquals(0, userOnSiteRights.size());
    }

    @Test
    public void getNotActiveUserOnSiteRightsByUserAndInvitedUserWithoutActive() {
        User invitedUser = new User();
        invitedUser.setEmail("aa");
        invitedUser.setRegistrationDate(new Date());
        persistance.putUser(invitedUser);

        final Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.getThemeId().setTemplateDirectory("a");
        site.getThemeId().setThemeCss("a");
        site.setTitle("g");
        site.setSubDomain("a");
        persistance.putSite(site);

        User user = new User();
        user.setRegistrationDate(new Date());
        user.setEmail("aa1");
        persistance.putUser(user);

        final UserOnSiteRight userOnSiteRight = new UserOnSiteRight();
        site.addUserOnSiteRight(userOnSiteRight);
        user.addUserOnSiteRight(userOnSiteRight);
        userOnSiteRight.setActive(false);
        persistance.putUserOnSiteRight(userOnSiteRight);

        final UserOnSiteRight invitedUserOnSiteRight = new UserOnSiteRight();
        site.addUserOnSiteRight(invitedUserOnSiteRight);
        invitedUserOnSiteRight.setActive(false);
        invitedUser.addUserOnSiteRight(invitedUserOnSiteRight);
        persistance.putUserOnSiteRight(invitedUserOnSiteRight);

        final List<UserOnSiteRight> userOnSiteRights =
                persistance.getNotActiveUserOnSiteRightsByUserAndInvitedUser(
                        user.getUserId(), invitedUser.getUserId());
        Assert.assertEquals(0, userOnSiteRights.size());
    }

    @Test
    public void getNotActiveUserOnSiteRightsByUserAndInvitedUserWithMany() {
        User invitedUser = new User();
        invitedUser.setEmail("aa");
        invitedUser.setRegistrationDate(new Date());
        persistance.putUser(invitedUser);

        final Site site1 = new Site();site1.getSitePaymentSettings().setUserId(-1);
        site1.getThemeId().setTemplateDirectory("a");
        site1.getThemeId().setThemeCss("a");
        site1.setTitle("g");
        site1.setSubDomain("a");
        persistance.putSite(site1);

        final Site site2 = new Site();site2.getSitePaymentSettings().setUserId(-1);
        site2.getThemeId().setTemplateDirectory("a");
        site2.getThemeId().setThemeCss("a");
        site2.setTitle("g");
        site2.setSubDomain("da");
        persistance.putSite(site2);

        User user = new User();
        user.setRegistrationDate(new Date());
        user.setEmail("aa1");
        persistance.putUser(user);

        final UserOnSiteRight userOnSiteRight1 = new UserOnSiteRight();
        site1.addUserOnSiteRight(userOnSiteRight1);
        user.addUserOnSiteRight(userOnSiteRight1);
        userOnSiteRight1.setActive(true);
        persistance.putUserOnSiteRight(userOnSiteRight1);

        final UserOnSiteRight userOnSiteRight2 = new UserOnSiteRight();
        site2.addUserOnSiteRight(userOnSiteRight2);
        user.addUserOnSiteRight(userOnSiteRight2);
        userOnSiteRight2.setActive(true);
        persistance.putUserOnSiteRight(userOnSiteRight2);

        final UserOnSiteRight invitedUserOnSiteRight1 = new UserOnSiteRight();
        site1.addUserOnSiteRight(invitedUserOnSiteRight1);
        invitedUserOnSiteRight1.setActive(false);
        invitedUser.addUserOnSiteRight(invitedUserOnSiteRight1);
        persistance.putUserOnSiteRight(invitedUserOnSiteRight1);

        final UserOnSiteRight invitedUserOnSiteRight2 = new UserOnSiteRight();
        site2.addUserOnSiteRight(invitedUserOnSiteRight2);
        invitedUserOnSiteRight2.setActive(false);
        invitedUser.addUserOnSiteRight(invitedUserOnSiteRight2);
        persistance.putUserOnSiteRight(invitedUserOnSiteRight2);

        final List<UserOnSiteRight> userOnSiteRights =
                persistance.getNotActiveUserOnSiteRightsByUserAndInvitedUser(user.getUserId(), invitedUser.getUserId());
        Assert.assertEquals(2, userOnSiteRights.size());
    }

    @Test
    public void getNotActiveUserOnSiteRightsByUserAndInvitedUser() {
        User invitedUser = new User();
        invitedUser.setEmail("aa");
        invitedUser.setRegistrationDate(new Date());
        persistance.putUser(invitedUser);

        final Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.getThemeId().setTemplateDirectory("a");
        site.getThemeId().setThemeCss("a");
        site.setTitle("g");
        site.setSubDomain("a");
        persistance.putSite(site);

        User user = new User();
        user.setRegistrationDate(new Date());
        user.setEmail("aa1");
        persistance.putUser(user);

        final UserOnSiteRight userOnSiteRight = new UserOnSiteRight();
        site.addUserOnSiteRight(userOnSiteRight);
        user.addUserOnSiteRight(userOnSiteRight);
        userOnSiteRight.setActive(true);
        persistance.putUserOnSiteRight(userOnSiteRight);

        final UserOnSiteRight invitedUserOnSiteRight = new UserOnSiteRight();
        site.addUserOnSiteRight(invitedUserOnSiteRight);
        invitedUserOnSiteRight.setActive(false);
        invitedUser.addUserOnSiteRight(invitedUserOnSiteRight);
        persistance.putUserOnSiteRight(invitedUserOnSiteRight);

        final List<UserOnSiteRight> userOnSiteRights =
                persistance.getNotActiveUserOnSiteRightsByUserAndInvitedUser(
                        user.getUserId(), invitedUser.getUserId());
        Assert.assertEquals(1, userOnSiteRights.size());
        Assert.assertEquals(invitedUserOnSiteRight, userOnSiteRights.get(0));
    }

    @Test
    public void getNotActivateUsersWithoutUsers() {
        Assert.assertEquals(0, persistance.getNotActivatedUsers(new Date(), 1).size());
    }

    @Test
    public void getNotActivateUsers() {
        User user = new User();
        user.setEmail("a1@a");
        persistance.putUser(user);

        final List<User> users = persistance.getNotActivatedUsers(new Date(System.currentTimeMillis() * 2), 1);
        Assert.assertEquals(1, users.size());
        Assert.assertEquals(user, users.get(0));
    }

    @Test
    public void getNotActivateUsersWithoutPeriod() {
        User user = new User();
        user.setEmail("a1@a");
        persistance.putUser(user);

        Assert.assertEquals(0, persistance.getNotActivatedUsers(new Date(System.currentTimeMillis() / 2), 1).size());
    }

    @Test
    public void getNotActivateUsersMoreCount() {
        User user1 = new User();
        user1.setEmail("a1@a");
        persistance.putUser(user1);

        User user2 = new User();
        user2.setEmail("a12@a");
        user2.setRegistrationDate(new Date(System.currentTimeMillis() / 2));
        persistance.putUser(user2);

        final List<User> users = persistance.getNotActivatedUsers(new Date(System.currentTimeMillis() * 2), 1);
        Assert.assertEquals(1, users.size());
        Assert.assertEquals("This method must sort user by registration date!", user2, users.get(0));
    }

    @Test
    public void putUserOnSiteRight() {
        User user = new User();
        user.setEmail("a1@a");
        persistance.putUser(user);

        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        site.setSubDomain("f");
        persistance.putSite(site);

        UserOnSiteRight userOnSiteRight = new UserOnSiteRight();
        user.addUserOnSiteRight(userOnSiteRight);
        site.addUserOnSiteRight(userOnSiteRight);
        persistance.putUserOnSiteRight(userOnSiteRight);
    }

    @Test
    public void getById() {
        User account = new User();
        account.setEmail("aa");
        account.setRegistrationDate(new Date());
        persistance.putUser(account);
        User getUser = persistance.getUserById(account.getUserId());
        Assert.assertEquals("aa", getUser.getEmail());
        Assert.assertEquals(account.getUserId(), getUser.getUserId());
    }

    @Test
    public void getByLogin() {
        User account = new User();
        account.setEmail("aa");
        account.setRegistrationDate(new Date());
        persistance.putUser(account);
        User getUser = persistance.getUserByEmail("aa");
        Assert.assertEquals("aa", getUser.getEmail());
        //Assert.assertNull(getUser.getPassword());
        Assert.assertEquals(account.getUserId(), getUser.getUserId());
    }
}
