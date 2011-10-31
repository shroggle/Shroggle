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
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.User;
import com.shroggle.entity.UserOnSiteRight;
import com.shroggle.presentation.account.accessPermissions.InviteUserHelper;
import com.shroggle.presentation.site.SiteAccessLevelHolderRequest;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;
import com.shroggle.util.mail.MockMailSender;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class InviteUserHelperTest {

    final InviteUserHelper inviteUserHelper = new InviteUserHelper();

    @Test
    public void testHasAccessToUserSites_forNewInvitedUser() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final boolean result = inviteUserHelper.hasAccessToUserSites("newone", new ArrayList<SiteAccessLevelHolderRequest>() {{
            SiteAccessLevelHolderRequest request = new SiteAccessLevelHolderRequest();
            request.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);
            request.setSiteId(site.getSiteId());

            add(request);
        }});

        Assert.assertFalse(result);
    }

    @Test
    public void testHasAccessToUserSites_forExistingInvitedUserWithoutRights() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final User invitedUser = TestUtil.createUser("e@e.e");

        final boolean result = inviteUserHelper.hasAccessToUserSites(invitedUser.getEmail(), new ArrayList<SiteAccessLevelHolderRequest>() {{
            SiteAccessLevelHolderRequest request = new SiteAccessLevelHolderRequest();
            request.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);
            request.setSiteId(site.getSiteId());

            add(request);
        }});

        Assert.assertFalse(result);
    }

    @Test
    public void testHasAccessToUserSites_forExistingInvitedUserWithRightsOnOtherSites() {
        final User user = TestUtil.createUserAndLogin();
        final Site site1 = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site1);
        final Site site2 = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site2);

        final User invitedUser = TestUtil.createUser("e@e.e");
        TestUtil.createUserOnSiteRightActiveAdmin(invitedUser, site2);

        final boolean result = inviteUserHelper.hasAccessToUserSites(invitedUser.getEmail(), new ArrayList<SiteAccessLevelHolderRequest>() {{
            SiteAccessLevelHolderRequest request = new SiteAccessLevelHolderRequest();
            request.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);
            request.setSiteId(site1.getSiteId());

            add(request);
        }});

        Assert.assertFalse(result);
    }

    @Test
    public void testHasAccessToUserSites_forExistingInvitedUserWithRights() {
        final User user = TestUtil.createUserAndLogin();
        final Site site1 = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site1);
        final Site site2 = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site2);

        final User invitedUser = TestUtil.createUser("e@e.e");
        TestUtil.createUserOnSiteRightActiveAdmin(invitedUser, site1);

        final boolean result = inviteUserHelper.hasAccessToUserSites(invitedUser.getEmail(), new ArrayList<SiteAccessLevelHolderRequest>() {{
            SiteAccessLevelHolderRequest request = new SiteAccessLevelHolderRequest();
            request.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);
            request.setSiteId(site1.getSiteId());

            add(request);
        }});

        Assert.assertTrue(result);
    }

    @Test
    public void testGetUsersWithAccessToUserSites() throws Exception {
        final User user = TestUtil.createUserAndLogin();

        final Site site1 = TestUtil.createSite();
        final Site site2 = TestUtil.createSite();
        final Site site3 = TestUtil.createSite();
        final Site site4 = TestUtil.createSite();

        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user, site2, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightActive(user, site3, SiteAccessLevel.GUEST);
        TestUtil.createUserOnSiteRightActive(user, site4, SiteAccessLevel.VISITOR);


        final User userWithAccessToSite1 = TestUtil.createUser();
        TestUtil.createUserOnSiteRightInactive(userWithAccessToSite1, site1, SiteAccessLevel.ADMINISTRATOR);

        final User userWithAccessToSite2 = TestUtil.createUser();
        TestUtil.createUserOnSiteRightActive(userWithAccessToSite2, site2, SiteAccessLevel.EDITOR);

        final User userWithAccessToSite3 = TestUtil.createUser();
        TestUtil.createUserOnSiteRightActive(userWithAccessToSite3, site3, SiteAccessLevel.GUEST);

        final User userWithAccessToSite4 = TestUtil.createUser();
        TestUtil.createUserOnSiteRightActive(userWithAccessToSite4, site4, SiteAccessLevel.VISITOR);


        final Set<User> users = InviteUserHelper.getUsersWithAccessToUserSites(user.getUserId());
        Assert.assertEquals(3, users.size());
        Assert.assertEquals(true, users.contains(user));
        Assert.assertEquals(true, users.contains(userWithAccessToSite1));
        Assert.assertEquals(true, users.contains(userWithAccessToSite2));
        Assert.assertEquals(false, users.contains(userWithAccessToSite3));
        Assert.assertEquals(false, users.contains(userWithAccessToSite4));
    }

    @Test
    public void testCreateRights_createNew() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
//        final UserOnSiteRight userOnSiteRight = TestUtil.createUserOnSiteRightInactive(user, site, SiteAccessLevel.EDITOR);

        /*----------------------------------------------Before execution----------------------------------------------*/
        Assert.assertEquals(0, user.getUserOnSiteRights().size());
//        Assert.assertEquals(userOnSiteRight, user.getUserOnSiteRights().get(0));
//        Assert.assertEquals(SiteAccessLevel.EDITOR, userOnSiteRight.getSiteAccessType());
        /*----------------------------------------------Before execution----------------------------------------------*/

        InviteUserHelper.createRightsForExistingUser(Arrays.asList(new SiteAccessLevelHolderRequest(site.getSiteId(), SiteAccessLevel.ADMINISTRATOR)), user, false, "invitation", -1);


        /*----------------------------------------------After execution-----------------------------------------------*/
        Assert.assertEquals(1, user.getUserOnSiteRights().size());
        Assert.assertEquals(site.getUserOnSiteRights().get(0).getSiteAccessType(), user.getUserOnSiteRights().get(0).getSiteAccessType());
        Assert.assertEquals(SiteAccessLevel.ADMINISTRATOR, user.getUserOnSiteRights().get(0).getSiteAccessType());


        Assert.assertEquals(false, user.getUserOnSiteRights().get(0).isActive());
        Assert.assertEquals("invitation", user.getUserOnSiteRights().get(0).getInvitationText());
        Assert.assertEquals(-1, user.getUserOnSiteRights().get(0).getRequesterUserId().intValue());
        /*----------------------------------------------After execution-----------------------------------------------*/
    }

    @Test
    public void testCreateRights_upgradeExisting() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final UserOnSiteRight userOnSiteRight = TestUtil.createUserOnSiteRightInactive(user, site, SiteAccessLevel.EDITOR);

        /*----------------------------------------------Before execution----------------------------------------------*/
        Assert.assertEquals(1, user.getUserOnSiteRights().size());
        Assert.assertEquals(userOnSiteRight, user.getUserOnSiteRights().get(0));
        Assert.assertEquals(SiteAccessLevel.EDITOR, userOnSiteRight.getSiteAccessType());
        /*----------------------------------------------Before execution----------------------------------------------*/

        InviteUserHelper.createRightsForExistingUser(Arrays.asList(new SiteAccessLevelHolderRequest(site.getSiteId(), SiteAccessLevel.ADMINISTRATOR)), user, false, "", -1);


        /*----------------------------------------------After execution-----------------------------------------------*/
        Assert.assertEquals(1, user.getUserOnSiteRights().size());
        Assert.assertEquals(userOnSiteRight, user.getUserOnSiteRights().get(0));
        Assert.assertEquals(SiteAccessLevel.ADMINISTRATOR, userOnSiteRight.getSiteAccessType());
        /*----------------------------------------------After execution-----------------------------------------------*/
    }

    @Test
    public void testCreateRights_createNew_withoutInvitation() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();

        /*----------------------------------------------Before execution----------------------------------------------*/
        Assert.assertEquals(0, user.getUserOnSiteRights().size());
        /*----------------------------------------------Before execution----------------------------------------------*/

        InviteUserHelper.createRightsForExistingUser(Arrays.asList(new SiteAccessLevelHolderRequest(site.getSiteId(), SiteAccessLevel.ADMINISTRATOR)), user, false);


        /*----------------------------------------------After execution-----------------------------------------------*/
        Assert.assertEquals(1, user.getUserOnSiteRights().size());
        Assert.assertEquals(site.getUserOnSiteRights().get(0).getSiteAccessType(), user.getUserOnSiteRights().get(0).getSiteAccessType());
        Assert.assertEquals(SiteAccessLevel.ADMINISTRATOR, user.getUserOnSiteRights().get(0).getSiteAccessType());


        Assert.assertEquals(false, user.getUserOnSiteRights().get(0).isActive());
        Assert.assertEquals(null, user.getUserOnSiteRights().get(0).getInvitationText());
        Assert.assertEquals(null, user.getUserOnSiteRights().get(0).getRequesterUserId());
        /*----------------------------------------------After execution-----------------------------------------------*/
    }

    @Test
    public void testCreateRights_createNew_withoutInvitation_setActive() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();

        /*----------------------------------------------Before execution----------------------------------------------*/
        Assert.assertEquals(0, user.getUserOnSiteRights().size());
        /*----------------------------------------------Before execution----------------------------------------------*/

        InviteUserHelper.createRightsForExistingUser(Arrays.asList(new SiteAccessLevelHolderRequest(site.getSiteId(), SiteAccessLevel.ADMINISTRATOR)), user, true);


        /*----------------------------------------------After execution-----------------------------------------------*/
        Assert.assertEquals(1, user.getUserOnSiteRights().size());
        Assert.assertEquals(site.getUserOnSiteRights().get(0).getSiteAccessType(), user.getUserOnSiteRights().get(0).getSiteAccessType());
        Assert.assertEquals(SiteAccessLevel.ADMINISTRATOR, user.getUserOnSiteRights().get(0).getSiteAccessType());


        Assert.assertEquals(true, user.getUserOnSiteRights().get(0).isActive());
        Assert.assertEquals(null, user.getUserOnSiteRights().get(0).getInvitationText());
        Assert.assertEquals(null, user.getUserOnSiteRights().get(0).getRequesterUserId());
        /*----------------------------------------------After execution-----------------------------------------------*/
    }

    @Test
    public void testCreateRights_upgradeExisting_withoutInvitation() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final UserOnSiteRight userOnSiteRight = TestUtil.createUserOnSiteRightInactive(user, site, SiteAccessLevel.EDITOR);

        /*----------------------------------------------Before execution----------------------------------------------*/
        Assert.assertEquals(1, user.getUserOnSiteRights().size());
        Assert.assertEquals(userOnSiteRight, user.getUserOnSiteRights().get(0));
        Assert.assertEquals(SiteAccessLevel.EDITOR, userOnSiteRight.getSiteAccessType());
        /*----------------------------------------------Before execution----------------------------------------------*/

        InviteUserHelper.createRightsForExistingUser(Arrays.asList(new SiteAccessLevelHolderRequest(site.getSiteId(), SiteAccessLevel.ADMINISTRATOR)), user, false);


        /*----------------------------------------------After execution-----------------------------------------------*/
        Assert.assertEquals(1, user.getUserOnSiteRights().size());
        Assert.assertEquals(userOnSiteRight, user.getUserOnSiteRights().get(0));
        Assert.assertEquals(SiteAccessLevel.ADMINISTRATOR, userOnSiteRight.getSiteAccessType());
        Assert.assertEquals(null, user.getUserOnSiteRights().get(0).getInvitationText());
        Assert.assertEquals(null, user.getUserOnSiteRights().get(0).getRequesterUserId());
        /*----------------------------------------------After execution-----------------------------------------------*/
    }

    @Test
    public void testSendMessageForInvitedUser_forSimpleUser() throws Exception {
        final Config config = ServiceLocator.getConfigStorage().get();
        config.setApplicationUrl("testApplicationUrl");
        config.setApplicationName("testApplicationName");
        config.setSupportEmail("support@email.com");

        final User loginedUser = TestUtil.createUserAndLogin("loginedUser@email.com");
        final User invitedUser = TestUtil.createUser("invitedUser@email.com");

        InviteUserHelper.sendMessageForInvitedUser(invitedUser.getEmail(), "invitation text", loginedUser.getUserId());

        final MockMailSender mailSender = (MockMailSender)ServiceLocator.getMailSender();

        Assert.assertEquals(1, mailSender.getMails().size());
        Assert.assertEquals("invitedUser@email.com", mailSender.getMails().get(0).getTo());
        Assert.assertEquals("Site admin invitation", mailSender.getMails().get(0).getSubject());
        Assert.assertEquals("invitation text\n" +
                "Please, follow the link:\n" +
                "http://" + config.getApplicationUrl() + "/account/createUser.action?request.invitedUserId=2&request.userId=1&showForInvited=true&request.confirmCode=d2cd9f7e9832642465345e0089ff698e\n" +
                "to accept the invitation.\n" +
                "\n" +
                "These sites are powered by " + config.getApplicationName() + " A simple web based tool for building feature rich, professional web sites and networks of sites.\n" +
                "\n" +
                "Please, visit site builder at: http://" + config.getApplicationUrl() + ".\n" +
                "\n" +
                "Please feel free to contact us directly with any questions, " + config.getSupportEmail() + ".", mailSender.getMails().get(0).getText());
    }

    @Test
    public void testSendMessageForInvitedUser_forNetworkUser() throws Exception {
        final Config config = ServiceLocator.getConfigStorage().get();
        config.setApplicationUrl("testApplicationUrl");
        config.setApplicationName("testApplicationName");
        config.setSupportEmail("support@email.com");

        final User loginedUser = TestUtil.createUserAndLogin("loginedUser@email.com");
        final User invitedUser = TestUtil.createUser("invitedUser@email.com");

        final Site site = TestUtil.createChildSite();
        site.getChildSiteSettings().getChildSiteRegistration().setFromEmail("from@email.com");
        site.getChildSiteSettings().getChildSiteRegistration().setName("Network Name");
        TestUtil.createUserOnSiteRightActiveAdmin(loginedUser, site);

        InviteUserHelper.sendMessageForInvitedUser(invitedUser.getEmail(), "invitation text", loginedUser.getUserId());

        final MockMailSender mailSender = (MockMailSender)ServiceLocator.getMailSender();

        Assert.assertEquals(1, mailSender.getMails().size());
        Assert.assertEquals("invitedUser@email.com", mailSender.getMails().get(0).getTo());
        Assert.assertEquals("Site admin invitation", mailSender.getMails().get(0).getSubject());
        Assert.assertEquals("invitation text\n" +
                "Please, follow the link:\n" +
                "http://" + config.getApplicationUrl() + "/account/createUser.action?request.invitedUserId=2&request.userId=1&showForInvited=true&request.confirmCode=d2cd9f7e9832642465345e0089ff698e\n" +
                "to accept the invitation.\n" +
                "\n" +
                 "These sites are powered by " + site.getChildSiteSettings().getChildSiteRegistration().getName() + " A simple web based tool for building feature rich, professional web sites and networks of sites.\n" +
                "\n" +
                "Please, visit site builder at: http://" + config.getApplicationUrl() + ".\n" +
                "\n" +
                "Please feel free to contact us directly with any questions, " + site.getChildSiteSettings().getChildSiteRegistration().getFromEmail() + ".", mailSender.getMails().get(0).getText());
    }

    @Test
    public void testSendMessageForInvitedUser_forNetworkUser_withEmptyFromEmail() throws Exception {
        final Config config = ServiceLocator.getConfigStorage().get();
        config.setApplicationUrl("testApplicationUrl");
        config.setApplicationName("testApplicationName");
        config.setSupportEmail("support@email.com");

        final User loginedUser = TestUtil.createUserAndLogin("loginedUser@email.com");
        final User invitedUser = TestUtil.createUser("invitedUser@email.com");

        final Site site = TestUtil.createChildSite();
        site.getChildSiteSettings().getChildSiteRegistration().setFromEmail("");
        site.getChildSiteSettings().getChildSiteRegistration().setName("Network Name");
        TestUtil.createUserOnSiteRightActiveAdmin(loginedUser, site);

        InviteUserHelper.sendMessageForInvitedUser(invitedUser.getEmail(), "invitation text", loginedUser.getUserId());

        final MockMailSender mailSender = (MockMailSender)ServiceLocator.getMailSender();

        Assert.assertEquals(1, mailSender.getMails().size());
        Assert.assertEquals("invitedUser@email.com", mailSender.getMails().get(0).getTo());
        Assert.assertEquals("Site admin invitation", mailSender.getMails().get(0).getSubject());
        Assert.assertEquals("invitation text\n" +
                "Please, follow the link:\n" +
                "http://" + config.getApplicationUrl() + "/account/createUser.action?request.invitedUserId=2&request.userId=1&showForInvited=true&request.confirmCode=d2cd9f7e9832642465345e0089ff698e\n" +
                "to accept the invitation.\n" +
                "\n" +
                 "These sites are powered by " + site.getChildSiteSettings().getChildSiteRegistration().getName() + " A simple web based tool for building feature rich, professional web sites and networks of sites.\n" +
                "\n" +
                "Please, visit site builder at: http://" + config.getApplicationUrl() + ".\n" +
                "\n" +
                "Please feel free to contact us directly with any questions, " + config.getSupportEmail() + ".", mailSender.getMails().get(0).getText());
    }
}
