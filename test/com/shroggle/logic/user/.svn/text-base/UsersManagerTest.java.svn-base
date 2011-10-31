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
package com.shroggle.logic.user;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.exception.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.Context;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class UsersManagerTest {

    @Test(expected = UserNotFoundException.class)
    public void loginWithNull() {
        usersManager.login(null, null, null);
        Assert.assertNull(context.getUserId());
    }

    @Test(expected = UserNotFoundException.class)
    public void loginWithNullPassword() {
        TestUtil.createUser("a@a");

        usersManager.login("a@a", null, null);
        Assert.assertNull(context.getUserId());
    }

    @Test
    public void login() {
        final User user = TestUtil.createUser("a@a");
        user.setPassword("1");

        final UserManager userManager = usersManager.login("a@a", "1", null);
        Assert.assertEquals(user, userManager.getUser());
        Assert.assertEquals((Integer) user.getUserId(), context.getUserId());
    }

    @Test
    public void loginWithOtherCase() {
        final User user = TestUtil.createUser("a@a");
        user.setPassword("1");

        final UserManager userManager = usersManager.login("a@A", "1", null);
        Assert.assertEquals(user, userManager.getUser());
        Assert.assertEquals((Integer) user.getUserId(), context.getUserId());
    }

    @Test
    public void loginWithSpaces() {
        final User user = TestUtil.createUser("a@a");
        user.setPassword("1");

        final UserManager userManager = usersManager.login("   a@a   ", "1", null);
        Assert.assertEquals(user, userManager.getUser());
        Assert.assertEquals((Integer) user.getUserId(), context.getUserId());
    }

    @Test(expected = UserWithWrongPasswordException.class)
    public void loginWithDifferentPassword() {
        final User user = TestUtil.createUser("a@a");
        user.setPassword("1");

        usersManager.login("a@a", "2", null);
        Assert.assertNull(context.getUserId());
    }

    @Test(expected = UserWithWrongPasswordException.class)
    public void loginWithOtherCasePassword() {
        final User user = TestUtil.createUser("a@a");
        user.setPassword("b");

        usersManager.login("a@a", "B", null);
        Assert.assertNull(context.getUserId());
    }

    @Test
    public void loginWithSite() {
        final User user = TestUtil.createUser("a@a");
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        user.setPassword("1");

        final UserManager userManager = usersManager.login("a@a", "1", site.getSiteId());
        Assert.assertEquals(user, userManager.getUser());
        Assert.assertEquals((Integer) user.getUserId(), context.getUserId());
    }

    @Test(expected = SiteNotFoundException.class)
    public void loginWithNotFoundSite() {
        final User user = TestUtil.createUser("a@a");
        user.setPassword("1");

        usersManager.login("a@a", "1", 1);
        Assert.assertNull(context.getUserId());
    }

    @Test(expected = UserWithoutRightException.class)
    public void loginWithoutRightOnSite() {
        final User user = TestUtil.createUser("a@a");
        final Site site = TestUtil.createSite();
        user.setPassword("1");

        usersManager.login("a@a", "1", site.getSiteId());
        Assert.assertNull(context.getUserId());
    }

    @Test(expected = UserNotActivatedException.class)
    public void loginNotActivated() {
        final User user = TestUtil.createUser("a@a");
        user.setPassword("1");
        user.setActiveted(null);

        usersManager.login("a@a", "1", null);
        Assert.assertNull(context.getUserId());
    }

    @Test(expected = UserNotActivatedException.class)
    public void loginNotRegistered() {
        final User user = TestUtil.createUser("a@a");
        user.setPassword("1");
        user.setRegistrationDate(null);

        usersManager.login("a@a", "1", null);
        Assert.assertNull(context.getUserId());
    }

    @Test(expected = UserNotActivatedException.class)
    public void getLoginedNotRegistered() {
        final User user = TestUtil.createUser("a@a");
        user.setPassword("1");
        user.setRegistrationDate(null);
        context.setUserId(user.getUserId());

        usersManager.getLogined();
    }

    @Test(expected = UserNotActivatedException.class)
    public void getLoginedNotActivated() {
        final User user = TestUtil.createUser("a@a");
        user.setPassword("1");
        user.setActiveted(null);
        context.setUserId(user.getUserId());

        usersManager.getLogined();
    }

    @Test(expected = UserNotLoginedException.class)
    public void getLoginedNotFound() {
        context.setUserId(1);

        usersManager.getLogined();
    }

    @Test(expected = UserNotLoginedException.class)
    public void getLoginedWithoutUser() {
        usersManager.getLogined();
    }

    @Test
    public void getLogined() {
        final User user = TestUtil.createUser("a@a");
        user.setPassword("1");
        context.setUserId(user.getUserId());

        Assert.assertEquals(user, usersManager.getLogined().getUser());
    }

    @Test
    public void getLoginedUser() {
        final User user = TestUtil.createUser("a@a");
        user.setPassword("1");
        context.setUserId(user.getUserId());

        Assert.assertEquals(user, usersManager.getLoginedUser());
    }


    @Test
    public void getLoginedUser_withoutUserIdInContext() {
        final User user = TestUtil.createUser("a@a");
        user.setPassword("1");

        Assert.assertNull(usersManager.getLoginedUser());
    }

    @Test
    public void getLoginedUser_withoutUserInDB() {
        context.setUserId(-1);

        Assert.assertNull(usersManager.getLoginedUser());
    }

    @Test
    public void getLoginedUser_NotActivated() {
        final User user = TestUtil.createUser("a@a");
        user.setPassword("1");
        user.setActiveted(null);
        context.setUserId(user.getUserId());

        Assert.assertNull(usersManager.getLoginedUser());
    }

    /*@Test
    public void testIsUserLoginedAndRegisteredFromRightForm() {
        final User user = TestUtil.createUser("a@a");
        user.setPassword("1");
        context.setUserId(user.getUserId());

        Form form = TestUtil.createRegistrationForm();
        FilledForm filledForm = TestUtil.createFilledForm(form);

        UserOnSiteRight userOnSiteRight = TestUtil.createUserOnSiteRightActiveAdmin(user, new Site());
        userOnSiteRight.setFilledRegistrationForm(filledForm);

        Assert.assertTrue(new UsersManager().isUserLoginedAndRegisteredFromRightForm(filledForm.getFormId()));
    }

    @Test
    public void testIsUserLoginedAndRegisteredFromRightForm_withNegativeFilledFormId() {
        final User user = TestUtil.createUser("a@a");
        user.setPassword("1");
        context.setUserId(user.getUserId());

        Assert.assertTrue(new UsersManager().isUserLoginedAndRegisteredFromRightForm(-1));
    }

    @Test
    public void testIsUserLoginedAndRegisteredFromRightForm_withoutRightsWithSelectedFilledForm() {
        final User user = TestUtil.createUser("a@a");
        user.setPassword("1");
        context.setUserId(user.getUserId());

        Form form = TestUtil.createRegistrationForm();
        FilledForm filledForm = TestUtil.createFilledForm(form);

        Assert.assertFalse(new UsersManager().isUserLoginedAndRegisteredFromRightForm(filledForm.getFormId()));
    }

    @Test
    public void testIsUserLoginedAndRegisteredFromRightForm_withNotValidRightsWithSelectedFilledForm() {
        final User user = TestUtil.createUser("a@a");
        user.setPassword("1");
        context.setUserId(user.getUserId());

        Form form = TestUtil.createRegistrationForm();
        FilledForm filledForm = TestUtil.createFilledForm(form);

        UserOnSiteRight userOnSiteRight = TestUtil.createUserOnSiteRightActiveAdmin(user, new Site());
        userOnSiteRight.setFilledRegistrationForm(filledForm);
        userOnSiteRight.setActive(false);

        Assert.assertFalse(new UsersManager().isUserLoginedAndRegisteredFromRightForm(filledForm.getFormId()));
    }

    @Test
    public void testIsUserLoginedAndRegisteredFromRightForm_withoutUserIdInContext() {
        final User user = TestUtil.createUser("a@a");
        user.setPassword("1");

        Form form = TestUtil.createRegistrationForm();
        FilledForm filledForm = TestUtil.createFilledForm(form);

        UserOnSiteRight userOnSiteRight = TestUtil.createUserOnSiteRightActiveAdmin(user, new Site());
        userOnSiteRight.setFilledRegistrationForm(filledForm);

        Assert.assertFalse(new UsersManager().isUserLoginedAndRegisteredFromRightForm(filledForm.getFormId()));
    }

    @Test
    public void testIsUserLoginedAndRegisteredFromRightForm_withoutUserInDB() {
        context.setUserId(-1);

        Form form = TestUtil.createRegistrationForm();
        FilledForm filledForm = TestUtil.createFilledForm(form);

        Assert.assertFalse(new UsersManager().isUserLoginedAndRegisteredFromRightForm(filledForm.getFormId()));
    }

    @Test
    public void testIsUserLoginedAndRegisteredFromRightForm_NotActivated() {
        final User user = TestUtil.createUser("a@a");
        user.setPassword("1");
        user.setActiveted(null);
        context.setUserId(user.getUserId());

        Form form = TestUtil.createRegistrationForm();
        FilledForm filledForm = TestUtil.createFilledForm(form);

        UserOnSiteRight userOnSiteRight = TestUtil.createUserOnSiteRightActiveAdmin(user, new Site());
        userOnSiteRight.setFilledRegistrationForm(filledForm);

        Assert.assertFalse(new UsersManager().isUserLoginedAndRegisteredFromRightForm(filledForm.getFormId()));
    }*/


    @Test
    public void testIsUserLoginedAndHasRightsToSite() {
        final User user = TestUtil.createUser("a@a");
        user.setPassword("1");
        context.setUserId(user.getUserId());
        Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        Assert.assertTrue(new UsersManager().isUserLoginedAndHasRightsToSite(site.getSiteId()));
    }

    @Test
    public void testIsUserLoginedAndHasRightsToSite_withoutRights() {
        final User user = TestUtil.createUser("a@a");
        user.setPassword("1");
        context.setUserId(user.getUserId());
        Site site = TestUtil.createSite();

        Assert.assertFalse(new UsersManager().isUserLoginedAndHasRightsToSite(site.getSiteId()));
    }

    @Test
    public void testIsUserLoginedAndHasRightsToSite_withoutLogined() {
        final User user = TestUtil.createUser("a@a");
        user.setPassword("1");
        Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        Assert.assertFalse(new UsersManager().isUserLoginedAndHasRightsToSite(site.getSiteId()));
    }

    @Test
    public void testIsUserLoginedAndHasRightsToSite_withoutLoginedButWithLoginedId() {
        final User user = TestUtil.createUser("a@a");
        user.setPassword("1");
        context.setUserId(-1);
        Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        Assert.assertFalse(new UsersManager().isUserLoginedAndHasRightsToSite(site.getSiteId()));
    }

    @Test
    public void testIsUserLoginedAndHasRightsToSite_withNotActivated() {
        final User user = TestUtil.createUser("a@a");
        user.setPassword("1");
        user.setActiveted(null);
        context.setUserId(user.getUserId());
        Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        Assert.assertFalse(new UsersManager().isUserLoginedAndHasRightsToSite(site.getSiteId()));
    }

    @Test
    public void testIsUserLoginedAndHasRightsToSite_withoutRegistrationDate() {
        final User user = TestUtil.createUser("a@a");
        user.setPassword("1");
        user.setRegistrationDate(null);
        context.setUserId(user.getUserId());
        Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        Assert.assertFalse(new UsersManager().isUserLoginedAndHasRightsToSite(site.getSiteId()));
    }

    @Test
    public void testGetHisNetworkLogoUrl() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createChildSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        Assert.assertEquals("/resourceGetter.action?resourceId=1&resourceSizeId=0&resourceSizeAdditionId=0&resourceGetterType=LOGO&resourceVersion=0&resourceDownload=false",
                UsersManager.getHisNetworkLogoUrl());
    }

    @Test
    public void testGetHisNetworkLogoUrl_forNotLogined() throws Exception {
        Assert.assertEquals("", UsersManager.getHisNetworkLogoUrl());
    }

    @Test
    public void testGetHisNetworkSiteUrl() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createChildSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        site.getChildSiteSettings().getParentSite().setSubDomain("parentSite");

        Assert.assertEquals("http://parentSite.shroggle.com",
                UsersManager.getParentSiteUrl());
    }

    @Test
    public void testGetHisNetworkSiteUrl_forNotLogined() throws Exception {
        Assert.assertEquals("#", UsersManager.getParentSiteUrl());
    }

    @Test
    public void testGetNetworkNameForNetworkUserOrOurAppName() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createChildSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        site.getChildSiteSettings().getChildSiteRegistration().setName("Network Name");

        Assert.assertEquals("Network Name", UsersManager.getNetworkNameForNetworkUserOrOurAppName());
    }

    @Test
    public void testGetNetworkNameForNetworkUserOrOurAppName_forNotLogined() throws Exception {
        ServiceLocator.getConfigStorage().get().setApplicationName("Our application name");
        Assert.assertEquals(ServiceLocator.getConfigStorage().get().getApplicationName(), UsersManager.getNetworkNameForNetworkUserOrOurAppName());
    }

    @Test
    public void testGetNetworkNameForNetworkUserOrOurAppName_forNotNetworkUser() throws Exception {
        TestUtil.createUserAndLogin();
        ServiceLocator.getConfigStorage().get().setApplicationName("Our application name");
        Assert.assertEquals(ServiceLocator.getConfigStorage().get().getApplicationName(), UsersManager.getNetworkNameForNetworkUserOrOurAppName());
    }

    @Test
    public void testGetNetworkEmailForNetworkUserOrOurAppEmail() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createChildSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        site.getChildSiteSettings().getChildSiteRegistration().setFromEmail("From email");

        Assert.assertEquals(site.getChildSiteSettings().getChildSiteRegistration().getFromEmail(), UsersManager.getNetworkEmailForNetworkUserOrOurAppEmail());
    }

    @Test
    public void testGetNetworkEmailForNetworkUserOrOurAppEmail_withEmptyFromEmail() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createChildSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        site.getChildSiteSettings().getChildSiteRegistration().setFromEmail("");

        Assert.assertEquals(ServiceLocator.getConfigStorage().get().getSupportEmail(), UsersManager.getNetworkEmailForNetworkUserOrOurAppEmail());
    }

    @Test
    public void testGetNetworkEmailForNetworkUserOrOurAppEmail_withNullFromEmail() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createChildSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        site.getChildSiteSettings().getChildSiteRegistration().setFromEmail(null);

        Assert.assertEquals(ServiceLocator.getConfigStorage().get().getSupportEmail(), UsersManager.getNetworkEmailForNetworkUserOrOurAppEmail());
    }

    @Test
    public void testGetNetworkEmailForNetworkUserOrOurAppEmail_forNotLogined() throws Exception {
        ServiceLocator.getConfigStorage().get().setSupportEmail("Our support email");
        Assert.assertEquals(ServiceLocator.getConfigStorage().get().getSupportEmail(), UsersManager.getNetworkEmailForNetworkUserOrOurAppEmail());
    }

    @Test
    public void testGetNetworkEmailForNetworkUserOrOurAppEmail_forNotNetworkUser() throws Exception {
        TestUtil.createUserAndLogin();
        ServiceLocator.getConfigStorage().get().setApplicationName("Our support email");
        Assert.assertEquals(ServiceLocator.getConfigStorage().get().getSupportEmail(), UsersManager.getNetworkEmailForNetworkUserOrOurAppEmail());
    }

    @Test
    public void testIsLoginedUserShroggleAdmin() throws Exception {
        ServiceLocator.getConfigStorage().get().setAdminEmails(Arrays.asList("email1", "email2"));
        final User user = TestUtil.createUserAndLogin();
        user.setEmail("email1");


        Assert.assertTrue(UsersManager.isLoginedUserAppAdmin());
    }

    @Test
    public void testIsLoginedUserShroggleAdmin_notAdmin() throws Exception {
        ServiceLocator.getConfigStorage().get().setAdminEmails(Arrays.asList("email1", "email2"));
        final User user = TestUtil.createUserAndLogin();
        user.setEmail("dsafsadfasdf");


        Assert.assertFalse(UsersManager.isLoginedUserAppAdmin());
    }

    @Test
    public void testIsLoginedUserShroggleAdmin_notLogined() throws Exception {
        ServiceLocator.getConfigStorage().get().setAdminEmails(Arrays.asList("email1", "email2"));

        Assert.assertFalse(UsersManager.isLoginedUserAppAdmin());
    }

    private final UsersManager usersManager = new UsersManager();
    private final Context context = ServiceLocator.getContextStorage().get();

}
