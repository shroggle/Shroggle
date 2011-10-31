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
package com.shroggle.presentation.account.accessPermissions;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.LoginedUserWithoutRightsException;
import com.shroggle.exception.OneSiteAdminException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.presentation.account.accessPermissions.AddEditPermissionsRequest;
import com.shroggle.presentation.account.accessPermissions.AddEditPermissionsService;
import com.shroggle.presentation.site.SiteAccessLevelHolderRequest;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class AddEditPermissionsServiceTest {

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    //////////////////invite user//////////////////////
    @Test(expected = UserNotLoginedException.class)
    public void inviteUserWithNotLoginedUser() throws Exception {
        AddEditPermissionsRequest request = new AddEditPermissionsRequest();
        request.setEmail("b@a");
        service.inviteUser(request);
    }

    @Test
    public void inviteUser() throws Exception {
        User loginedUser = TestUtil.createUserAndLogin("a@a.com");
        Site[] sites = new Site[10];
        for (int i = 0; i < 10; i++) {
            sites[i] = TestUtil.createSite("Site" + i, "url" + i);
            TestUtil.createUserOnSiteRightActive(loginedUser, sites[i], SiteAccessLevel.ADMINISTRATOR);
        }
        AddEditPermissionsRequest request = new AddEditPermissionsRequest();
        request.setEmail("b@a.com");
        request.setInvitationText("Hi, <first name last name>.\\nYou have been invited by Anatoliy Balakirev to be <a/an site/account> administrator for the: <site names> web site(s).");
        request.setLastName("Balakirev");
        request.setFirstName("Anatoliy");
        List<SiteAccessLevelHolderRequest> selectedSites = new ArrayList<SiteAccessLevelHolderRequest>();
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                selectedSites.add(new SiteAccessLevelHolderRequest(sites[i].getSiteId(), SiteAccessLevel.ADMINISTRATOR));
            } else {
                selectedSites.add(new SiteAccessLevelHolderRequest(sites[i].getSiteId(), SiteAccessLevel.EDITOR));
            }
        }
        request.setSelectedSites(selectedSites);
        request.setUserId(-1);
        Assert.assertNotSame("wrongEmail", service.inviteUser(request));
        User invitedUser = persistance.getUserByEmail("b@a.com");
        Assert.assertNotNull(invitedUser);
        Assert.assertEquals(10, invitedUser.getUserOnSiteRights().size());
        Assert.assertEquals(SiteAccessLevel.ADMINISTRATOR, persistance.getUserOnSiteRightById(new UserOnSiteRightId(invitedUser, sites[0])).getSiteAccessType());
        Assert.assertEquals(SiteAccessLevel.EDITOR, persistance.getUserOnSiteRightById(new UserOnSiteRightId(invitedUser, sites[1])).getSiteAccessType());
    }

    @Test
    public void inviteUserWithWrongEmail() throws Exception {
        User loginedUser = TestUtil.createUserAndLogin("a@a.com");
        Site[] sites = new Site[10];
        for (int i = 0; i < 10; i++) {
            sites[i] = TestUtil.createSite("Site" + i, "url" + i);
            TestUtil.createUserOnSiteRightActive(loginedUser, sites[i], SiteAccessLevel.ADMINISTRATOR);
        }
        AddEditPermissionsRequest request = new AddEditPermissionsRequest();
        request.setEmail("dasfasdf");
        request.setInvitationText("Hi, <first name last name>.\\nYou have been invited by Anatoliy Balakirev to be <a/an site/account> administrator for the: <site names> web site(s).");
        request.setLastName("Balakirev");
        request.setFirstName("Anatoliy");
        List<SiteAccessLevelHolderRequest> selectedSites = new ArrayList<SiteAccessLevelHolderRequest>();
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                selectedSites.add(new SiteAccessLevelHolderRequest(sites[i].getSiteId(), SiteAccessLevel.ADMINISTRATOR));
            } else {
                selectedSites.add(new SiteAccessLevelHolderRequest(sites[i].getSiteId(), SiteAccessLevel.EDITOR));
            }
        }
        request.setSelectedSites(selectedSites);
        request.setUserId(-1);
        Assert.assertEquals("wrongEmail", service.inviteUser(request));
        User invitedUser = persistance.getUserByEmail("b@a.com");
        Assert.assertNull(invitedUser);
    }


    @Test
    public void inviteExistingUser() throws Exception {
        User loginedUser = TestUtil.createUserAndLogin("a@a.com");
        User invitedUser = TestUtil.createUser("b@a.com");
        Site site = TestUtil.createSite("Site", "url");
        TestUtil.createUserOnSiteRightActive(invitedUser, site, SiteAccessLevel.ADMINISTRATOR);
        Site[] sites = new Site[10];
        for (int i = 0; i < 10; i++) {
            sites[i] = TestUtil.createSite("Site" + i, "url" + i);
            TestUtil.createUserOnSiteRightActive(loginedUser, sites[i], SiteAccessLevel.ADMINISTRATOR);
        }
        AddEditPermissionsRequest request = new AddEditPermissionsRequest();
        request.setEmail("b@a.com");
        request.setInvitationText("Hi, <first name last name>.\\nYou have been invited by Anatoliy Balakirev to be <a/an site/account> administrator for the: <site names> web site(s).");
        request.setLastName("Balakirev");
        request.setFirstName("Anatoliy");
        List<SiteAccessLevelHolderRequest> selectedSites = new ArrayList<SiteAccessLevelHolderRequest>();
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                selectedSites.add(new SiteAccessLevelHolderRequest(sites[i].getSiteId(), SiteAccessLevel.ADMINISTRATOR));
            } else {
                selectedSites.add(new SiteAccessLevelHolderRequest(sites[i].getSiteId(), SiteAccessLevel.EDITOR));
            }
        }
        request.setSelectedSites(selectedSites);
        request.setUserId(invitedUser.getUserId());
        Assert.assertEquals(1, invitedUser.getUserOnSiteRights().size());
        Assert.assertNotSame("wrongEmail", service.inviteUser(request));
        Assert.assertEquals(11, invitedUser.getUserOnSiteRights().size());
        Assert.assertEquals(SiteAccessLevel.ADMINISTRATOR, persistance.getUserOnSiteRightById(new UserOnSiteRightId(invitedUser, sites[0])).getSiteAccessType());
        Assert.assertEquals(SiteAccessLevel.EDITOR, persistance.getUserOnSiteRightById(new UserOnSiteRightId(invitedUser, sites[1])).getSiteAccessType());
    }

    @Test
    public void inviteExistingUserWithRightsOnExistingSite() throws Exception {
        TestUtil.createUserAndLogin("a@a.com");
        User invitedUser = TestUtil.createUser("b@a.com");
        Site site = TestUtil.createSite("Site", "url");
        TestUtil.createUserOnSiteRightActive(invitedUser, site, SiteAccessLevel.EDITOR);

        AddEditPermissionsRequest request = new AddEditPermissionsRequest();
        request.setEmail("b@a.com");
        request.setInvitationText("Hi, <first name last name>.\\nYou have been invited by Anatoliy Balakirev to be <a/an site/account> administrator for the: <site names> web site(s).");
        request.setLastName("Balakirev");
        request.setFirstName("Anatoliy");
        List<SiteAccessLevelHolderRequest> selectedSites = new ArrayList<SiteAccessLevelHolderRequest>();
        selectedSites.add(new SiteAccessLevelHolderRequest(site.getSiteId(), SiteAccessLevel.ADMINISTRATOR));

        request.setSelectedSites(selectedSites);
        request.setUserId(invitedUser.getUserId());
        Assert.assertEquals(1, invitedUser.getUserOnSiteRights().size());
        Assert.assertNotSame("wrongEmail", service.inviteUser(request));
        Assert.assertEquals("userExist", service.inviteUser(request));
        Assert.assertEquals(1, invitedUser.getUserOnSiteRights().size());
        Assert.assertEquals(SiteAccessLevel.EDITOR, persistance.getUserOnSiteRightById(new UserOnSiteRightId(invitedUser, site)).getSiteAccessType());
    }

    @Test
    public void inviteUserWithRightsOnSelectedSites() throws Exception {
        User loginedUser = TestUtil.createUserAndLogin("a@a.com");
        User invitedUser = TestUtil.createUser("b@a.com");
        Site[] sites = new Site[10];
        for (int i = 0; i < 10; i++) {
            sites[i] = TestUtil.createSite("Site" + i, "url" + i);
            TestUtil.createUserOnSiteRightActive(loginedUser, sites[i], SiteAccessLevel.ADMINISTRATOR);
        }
        TestUtil.createUserOnSiteRightActive(invitedUser, sites[1], SiteAccessLevel.ADMINISTRATOR);
        AddEditPermissionsRequest request = new AddEditPermissionsRequest();
        request.setEmail("b@a.com");
        request.setInvitationText("Hi, <first name last name>.\\nYou have been invited by Anatoliy Balakirev to be <a/an site/account> administrator for the: <site names> web site(s).");
        request.setLastName("Balakirev");
        request.setFirstName("Anatoliy");
        List<SiteAccessLevelHolderRequest> selectedSites = new ArrayList<SiteAccessLevelHolderRequest>();
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                selectedSites.add(new SiteAccessLevelHolderRequest(sites[i].getSiteId(), SiteAccessLevel.ADMINISTRATOR));
            } else {
                selectedSites.add(new SiteAccessLevelHolderRequest(sites[i].getSiteId(), SiteAccessLevel.EDITOR));
            }
        }
        request.setSelectedSites(selectedSites);
        request.setUserId(invitedUser.getUserId());
        Assert.assertEquals(1, invitedUser.getUserOnSiteRights().size());
        Assert.assertEquals("userExist", service.inviteUser(request));
        Assert.assertEquals(1, invitedUser.getUserOnSiteRights().size());
    }
    //////////////////invite user//////////////////////


    //////////////////delete user//////////////////////

    @Test(expected = UserNotLoginedException.class)
    public void deleteUsersWithNotLoginedUser() throws Exception {
        service.deleteUserRights(null);
    }

    @Test(expected = OneSiteAdminException.class)
    public void deleteUsersWithOneSiteAdmin() throws Exception {
        User loginedUser = TestUtil.createUserAndLogin("a@a");
        User[] users = new User[10];
        Site[] sites = new Site[10];
        List<Integer> selectedUsers = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            sites[i] = TestUtil.createSite("Site" + i, "url" + i);
            users[i] = TestUtil.createUser("a" + i + "@a");
            TestUtil.createUserOnSiteRightActive(loginedUser, sites[i], SiteAccessLevel.ADMINISTRATOR);
            TestUtil.createUserOnSiteRightActive(users[i], sites[i], SiteAccessLevel.EDITOR);
            selectedUsers.add(users[i].getUserId());
        }
        selectedUsers.add(loginedUser.getUserId());

        service.deleteUserRights(selectedUsers);
    }

    @Test(expected = OneSiteAdminException.class)
    public void deleteUsersWithAllSiteAdminsSelected() throws Exception {
        User loginedUser = TestUtil.createUserAndLogin("a@a");
        User newUser = TestUtil.createUser("aa1@a");
        User[] users = new User[10];
        Site[] sites = new Site[10];
        List<Integer> selectedUsers = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            sites[i] = TestUtil.createSite("Site" + i, "url" + i);
            users[i] = TestUtil.createUser("a" + i + "@a");
            TestUtil.createUserOnSiteRightActive(loginedUser, sites[i], SiteAccessLevel.ADMINISTRATOR);
            TestUtil.createUserOnSiteRightActive(newUser, sites[i], SiteAccessLevel.ADMINISTRATOR);
            TestUtil.createUserOnSiteRightActive(users[i], sites[i], SiteAccessLevel.EDITOR);
            selectedUsers.add(users[i].getUserId());
        }
        selectedUsers.add(loginedUser.getUserId());
        selectedUsers.add(newUser.getUserId());

        service.deleteUserRights(selectedUsers);
    }

    @Test(expected = LoginedUserWithoutRightsException.class)
    public void deleteUsersWithLoginedSiteAdminsSelected() throws Exception {
        User loginedUser = TestUtil.createUserAndLogin("a@a");
        User newUser = TestUtil.createUser("aa1@a");
        User[] users = new User[10];
        Site[] sites = new Site[10];
        List<Integer> selectedUsers = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            sites[i] = TestUtil.createSite("Site" + i, "url" + i);
            users[i] = TestUtil.createUser("a" + i + "@a");
            TestUtil.createUserOnSiteRightActive(loginedUser, sites[i], SiteAccessLevel.ADMINISTRATOR);
            TestUtil.createUserOnSiteRightActive(newUser, sites[i], SiteAccessLevel.ADMINISTRATOR);
            TestUtil.createUserOnSiteRightActive(users[i], sites[i], SiteAccessLevel.EDITOR);
            selectedUsers.add(users[i].getUserId());
        }
        selectedUsers.add(loginedUser.getUserId());

        service.deleteUserRights(selectedUsers);
    }

    @Test
    public void deleteUsersWithAccessToNotOnlyLoginedUserSites() throws Exception {
        User loginedUser = TestUtil.createUserAndLogin("a@a");
        User[] users = new User[10];
        Site[] sites = new Site[10];
        List<Integer> selectedUsers = new ArrayList<Integer>();

        for (int i = 0; i < 10; i++) {
            sites[i] = TestUtil.createSite("Site" + i, "url" + i);
            users[i] = TestUtil.createUser("a" + i + "@a");
            TestUtil.createUserOnSiteRightActive(loginedUser, sites[i], SiteAccessLevel.ADMINISTRATOR);
            TestUtil.createUserOnSiteRightActive(users[i], sites[i], SiteAccessLevel.EDITOR).setActive(false);
            selectedUsers.add(users[i].getUserId());
        }

        Site newSite = TestUtil.createSite("newSite", "newUrl");
        TestUtil.createUserOnSiteRightActive(users[8], newSite, SiteAccessLevel.EDITOR);
        Assert.assertNotSame("loginedUserDeleted", service.deleteUserRights(selectedUsers));

        for (int i = 0; i < 10; i++) {
            User user = persistance.getUserById(users[i].getUserId());
            if (i == 8) {
                Assert.assertNotNull(user);
            } else {
                Assert.assertNull(user);
            }
        }
        Assert.assertNotNull(persistance.getUserById(loginedUser.getUserId()));
        for (int i = 0; i < 10; i++) {
            Assert.assertNull(persistance.getUserOnSiteRightById(new UserOnSiteRightId(users[8], sites[i])));
        }
        Assert.assertEquals(SiteAccessLevel.EDITOR, persistance.getUserOnSiteRightById(new UserOnSiteRightId(users[8], newSite)).getSiteAccessType());
    }
    //////////////////delete user//////////////////////


    //////////////////change user info//////////////////////

    @Test(expected = UserNotLoginedException.class)
    public void changeUserInfoWithNotLoginedUser() throws Exception {
        service.changeUserInfo(null);
    }

    @Test
    public void changeUserInfo() throws Exception {
        User loginedUser = TestUtil.createUserAndLogin("a@a");
        User[] users = new User[10];
        Site[] sites = new Site[10];
        for (int i = 0; i < 10; i++) {
            sites[i] = TestUtil.createSite("Site" + i, "url" + i);
            users[i] = TestUtil.createUser("a" + i + "@a");
            TestUtil.createUserOnSiteRightActive(loginedUser, sites[i], SiteAccessLevel.ADMINISTRATOR);
            TestUtil.createUserOnSiteRightActive(users[i], sites[i], SiteAccessLevel.EDITOR);
        }
        AddEditPermissionsRequest request = new AddEditPermissionsRequest();
        List<SiteAccessLevelHolderRequest> selectedSites = new ArrayList<SiteAccessLevelHolderRequest>();
        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0) {
                selectedSites.add(new SiteAccessLevelHolderRequest(sites[i].getSiteId(), SiteAccessLevel.ADMINISTRATOR));
            } else {
                selectedSites.add(new SiteAccessLevelHolderRequest(sites[i].getSiteId(), SiteAccessLevel.EDITOR));
            }
        }
        request.setSelectedSites(selectedSites);
        request.setUserId(users[0].getUserId());

        Assert.assertEquals(1, users[0].getUserOnSiteRights().size());
        Assert.assertEquals(users[0].getUserOnSiteRights().get(0).getSiteAccessType(), SiteAccessLevel.EDITOR);
        service.changeUserInfo(request);
        Assert.assertEquals(5, users[0].getUserOnSiteRights().size());
        Assert.assertEquals(users[0].getUserOnSiteRights().get(0).getSiteAccessType(), SiteAccessLevel.ADMINISTRATOR);
    }
    //////////////////delete user//////////////////////


    //////////////////showShareYourSitesPage//////////////////////

    @Test(expected = UserNotLoginedException.class)
    public void showShareYourSitesPageWithNotLoginedUser() throws Exception {
        service.showShareYourSitesPage(-1);
    }

    @Test
    public void showShareYourSitesPage() throws Exception {
        User loginedUser = TestUtil.createUserAndLogin("a@a");
        User[] users = new User[10];
        Site[] sites = new Site[10];
        for (int i = 0; i < 10; i++) {
            sites[i] = TestUtil.createSite("Site" + i, "url" + i);
            users[i] = TestUtil.createUser("a" + i + "@a");
            TestUtil.createUserOnSiteRightActive(loginedUser, sites[i], SiteAccessLevel.ADMINISTRATOR);
            TestUtil.createUserOnSiteRightActive(users[i], sites[i], SiteAccessLevel.EDITOR);
        }
        service.showShareYourSitesPage(users[0].getUserId());
        Assert.assertEquals(users[0], service.getSelectedUser());
        Assert.assertEquals(false, service.isDisableWindow());
        Assert.assertEquals(loginedUser, service.getLoginedUser());
        Assert.assertEquals(10, service.getSelectedUserSites().size());
    }

    @Test
    public void showShareYourSitesPageWithLoginedUser() throws Exception {
        User loginedUser = TestUtil.createUserAndLogin("a@a");
        User[] users = new User[10];
        Site[] sites = new Site[10];
        for (int i = 0; i < 10; i++) {
            sites[i] = TestUtil.createSite("Site" + i, "url" + i);
            users[i] = TestUtil.createUser("a" + i + "@a");
            TestUtil.createUserOnSiteRightActive(loginedUser, sites[i], SiteAccessLevel.ADMINISTRATOR);
            TestUtil.createUserOnSiteRightActive(users[i], sites[i], SiteAccessLevel.EDITOR);
        }
        service.showShareYourSitesPage(loginedUser.getUserId());
        Assert.assertEquals(loginedUser, service.getSelectedUser());
        Assert.assertEquals(true, service.isDisableWindow());
        Assert.assertEquals(loginedUser, service.getLoginedUser());
        Assert.assertEquals(10, service.getSelectedUserSites().size());
    }

    @Test
    public void showShareYourSitesPageWithInvitationTextFromLoginedUser() throws Exception {
        User loginedUser = TestUtil.createUserAndLogin("a@a");
        User adminUser1 = TestUtil.createUser("admin1@a.com");
        User adminUser2 = TestUtil.createUser("admin2@a.com");
        User adminUser3 = TestUtil.createUser("admin3@a.com");
        User user = TestUtil.createUser("user@a.com");
        Site[] sites = new Site[10];
        for (int i = 0; i < 10; i++) {
            sites[i] = TestUtil.createSite("Site" + i, "url" + i);
            TestUtil.createUserOnSiteRightActive(loginedUser, sites[i], SiteAccessLevel.ADMINISTRATOR);
            TestUtil.createUserOnSiteRightActive(adminUser1, sites[i], SiteAccessLevel.ADMINISTRATOR);
            TestUtil.createUserOnSiteRightActive(adminUser2, sites[i], SiteAccessLevel.ADMINISTRATOR);
            TestUtil.createUserOnSiteRightActive(adminUser3, sites[i], SiteAccessLevel.ADMINISTRATOR);
        }

        UserOnSiteRight right1 = TestUtil.createUserOnSiteRightActive(user, sites[0], SiteAccessLevel.ADMINISTRATOR);
        right1.setInvitationText("Invitation Text 1");
        right1.setRequesterUserId(loginedUser.getUserId());


        UserOnSiteRight right2 = TestUtil.createUserOnSiteRightActive(user, sites[1], SiteAccessLevel.ADMINISTRATOR);
        right2.setInvitationText("Invitation Text 2");
        right2.setRequesterUserId(adminUser1.getUserId());


        UserOnSiteRight right3 = TestUtil.createUserOnSiteRightActive(user, sites[2], SiteAccessLevel.ADMINISTRATOR);
        right3.setInvitationText("Invitation Text 3");
        right3.setRequesterUserId(adminUser2.getUserId());


        UserOnSiteRight right4 = TestUtil.createUserOnSiteRightActive(user, sites[3], SiteAccessLevel.ADMINISTRATOR);
        right4.setInvitationText("Invitation Text 4");
        right4.setRequesterUserId(adminUser3.getUserId());

        service.showShareYourSitesPage(user.getUserId());
        Assert.assertEquals(user, service.getSelectedUser());
        Assert.assertEquals(false, service.isDisableWindow());
        Assert.assertEquals(loginedUser, service.getLoginedUser());
        Assert.assertEquals(10, service.getSelectedUserSites().size());
        Assert.assertEquals(right1.getInvitationText(), service.getInvitationText());
    }

    @Test
    public void showShareYourSitesPageWithInvitationTextFromNotLoginedUser() throws Exception {
        User loginedUser = TestUtil.createUserAndLogin("a@a");
        User adminUser1 = TestUtil.createUser("admin1@a.com");
        User adminUser2 = TestUtil.createUser("admin2@a.com");
        User adminUser3 = TestUtil.createUser("admin3@a.com");
        User user = TestUtil.createUser("user@a.com");
        Site[] sites = new Site[10];
        for (int i = 0; i < 10; i++) {
            sites[i] = TestUtil.createSite("Site" + i, "url" + i);
            TestUtil.createUserOnSiteRightActive(loginedUser, sites[i], SiteAccessLevel.ADMINISTRATOR);
            TestUtil.createUserOnSiteRightActive(adminUser1, sites[i], SiteAccessLevel.ADMINISTRATOR);
            TestUtil.createUserOnSiteRightActive(adminUser2, sites[i], SiteAccessLevel.ADMINISTRATOR);
            TestUtil.createUserOnSiteRightActive(adminUser3, sites[i], SiteAccessLevel.ADMINISTRATOR);
        }

        UserOnSiteRight right3 = TestUtil.createUserOnSiteRightActive(user, sites[2], SiteAccessLevel.ADMINISTRATOR);
        right3.setInvitationText("Invitation Text 3");
        right3.setRequesterUserId(adminUser2.getUserId());

        UserOnSiteRight right2 = TestUtil.createUserOnSiteRightActive(user, sites[1], SiteAccessLevel.ADMINISTRATOR);
        right2.setInvitationText("Invitation Text 2");
        right2.setRequesterUserId(adminUser1.getUserId());


        UserOnSiteRight right4 = TestUtil.createUserOnSiteRightActive(user, sites[3], SiteAccessLevel.ADMINISTRATOR);
        right4.setInvitationText("Invitation Text 4");
        right4.setRequesterUserId(adminUser3.getUserId());

        service.showShareYourSitesPage(user.getUserId());
        Assert.assertEquals(user, service.getSelectedUser());
        Assert.assertEquals(false, service.isDisableWindow());
        Assert.assertEquals(loginedUser, service.getLoginedUser());
        Assert.assertEquals(10, service.getSelectedUserSites().size());
        Assert.assertEquals(right2.getInvitationText(), service.getInvitationText());
    }

    @Test
    public void showShareYourSitesPageWithEmptyInvitationText() throws Exception {
        User loginedUser = TestUtil.createUserAndLogin("a@a");
        User adminUser1 = TestUtil.createUser("admin1@a.com");
        User adminUser2 = TestUtil.createUser("admin2@a.com");
        User adminUser3 = TestUtil.createUser("admin3@a.com");
        User user = TestUtil.createUser("user@a.com");
        Site[] sites = new Site[10];
        for (int i = 0; i < 10; i++) {
            sites[i] = TestUtil.createSite("Site" + i, "url" + i);
            TestUtil.createUserOnSiteRightActive(loginedUser, sites[i], SiteAccessLevel.ADMINISTRATOR);
            TestUtil.createUserOnSiteRightActive(adminUser1, sites[i], SiteAccessLevel.ADMINISTRATOR);
            TestUtil.createUserOnSiteRightActive(adminUser2, sites[i], SiteAccessLevel.ADMINISTRATOR);
            TestUtil.createUserOnSiteRightActive(adminUser3, sites[i], SiteAccessLevel.ADMINISTRATOR);
        }

        service.showShareYourSitesPage(user.getUserId());
        Assert.assertEquals(user, service.getSelectedUser());
        Assert.assertEquals(false, service.isDisableWindow());
        Assert.assertEquals(loginedUser, service.getLoginedUser());
        Assert.assertEquals(10, service.getSelectedUserSites().size());
        Assert.assertEquals("", service.getInvitationText());
    }
    //////////////////showShareYourSitesPage//////////////////////


    private final AddEditPermissionsService service = new AddEditPermissionsService();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();

}
