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
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;
import com.shroggle.util.mail.MockMailSender;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class RemoveUserRightsUtilTest {

    @Test
    public void testRemoveRight() throws Exception {
        final User user = TestUtil.createUserAndLogin();

        final Site site1 = TestUtil.createSite();
        site1.setTitle("asfdasfasdf");
        final Site site2 = TestUtil.createSite();
        final Site site3 = TestUtil.createSite();
        final Site site4 = TestUtil.createSite();

        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user, site2, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightActive(user, site3, SiteAccessLevel.GUEST);
        TestUtil.createUserOnSiteRightActive(user, site4, SiteAccessLevel.VISITOR);

        final User user2 = TestUtil.createUser();
        TestUtil.createUserOnSiteRightActive(user2, site1, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user2, site2, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightActive(user2, site3, SiteAccessLevel.GUEST);
        TestUtil.createUserOnSiteRightActive(user2, site4, SiteAccessLevel.VISITOR);
        Assert.assertEquals(4, user2.getUserOnSiteRights().size());


        final RemoveUserRightsUtil.RemovedRightsResponse removedRightsResponse = new RemoveUserRightsUtil().removeRight(user2.getUserId(), false, false);

        Assert.assertNotNull(removedRightsResponse);
        Assert.assertEquals(1, removedRightsResponse.getRemovedRights().size());
        Assert.assertEquals(user.getEmail(), removedRightsResponse.getRemovedRights().get(0).getEmail());
        Assert.assertEquals(site1.getTitle(), removedRightsResponse.getRemovedRights().get(0).getSiteTitle());
        Assert.assertEquals(0, removedRightsResponse.getRemovedUsers().size());

        Assert.assertEquals(3, user2.getUserOnSiteRights().size());
        for (UserOnSiteRight userOnSiteRight : user2.getUserOnSiteRights()) {
            final int siteId = userOnSiteRight.getId().getSite().getSiteId();
            Assert.assertEquals(true, siteId != site1.getSiteId());

            Assert.assertEquals(true, (siteId == site2.getSiteId() || siteId == site3.getSiteId() || siteId == site4.getSiteId()));
        }
    }

    @Test
    public void testRemoveRight_andRemoveUser() throws Exception {
        final User user = TestUtil.createUserAndLogin();

        final Site site1 = TestUtil.createSite();
        site1.setTitle("asfdasfasdf");
        final Site site2 = TestUtil.createSite();
        final Site site3 = TestUtil.createSite();
        final Site site4 = TestUtil.createSite();

        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user, site2, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightActive(user, site3, SiteAccessLevel.GUEST);
        TestUtil.createUserOnSiteRightActive(user, site4, SiteAccessLevel.VISITOR);

        final User user2 = TestUtil.createUser();
        TestUtil.createUserOnSiteRightActive(user2, site1, SiteAccessLevel.ADMINISTRATOR);
        Assert.assertEquals(1, user2.getUserOnSiteRights().size());


        final RemoveUserRightsUtil.RemovedRightsResponse removedRightsResponse = new RemoveUserRightsUtil().removeRight(user2.getUserId(), true, false);

        Assert.assertNotNull(removedRightsResponse);
        Assert.assertEquals(1, removedRightsResponse.getRemovedRights().size());
        Assert.assertEquals(user.getEmail(), removedRightsResponse.getRemovedRights().get(0).getEmail());
        Assert.assertEquals(site1.getTitle(), removedRightsResponse.getRemovedRights().get(0).getSiteTitle());
        Assert.assertEquals(1, removedRightsResponse.getRemovedUsers().size());
        Assert.assertEquals(user2.getEmail(), removedRightsResponse.getRemovedUsers().get(0).getEmail());
        Assert.assertNull(ServiceLocator.getPersistance().getUserById(user2.getUserId()));


        Assert.assertEquals(0, user2.getUserOnSiteRights().size());
    }

    @Test
    public void testRemoveRight_andRemoveUser_butWithOneInactiveRight() throws Exception {
        final User user = TestUtil.createUserAndLogin();

        final Site site1 = TestUtil.createSite();
        site1.setTitle("asfdasfasdf");
        final Site site2 = TestUtil.createSite();
        final Site site3 = TestUtil.createSite();
        final Site site4 = TestUtil.createSite();

        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user, site2, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightActive(user, site3, SiteAccessLevel.GUEST);
        TestUtil.createUserOnSiteRightActive(user, site4, SiteAccessLevel.VISITOR);

        final User user2 = TestUtil.createUser();
        TestUtil.createUserOnSiteRightActive(user2, site1, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightInactive(user2, site2, SiteAccessLevel.ADMINISTRATOR);
        Assert.assertEquals(2, user2.getUserOnSiteRights().size());


        final RemoveUserRightsUtil.RemovedRightsResponse removedRightsResponse = new RemoveUserRightsUtil().removeRight(user2.getUserId(), true, false);

        Assert.assertNotNull(removedRightsResponse);
        Assert.assertEquals(1, removedRightsResponse.getRemovedRights().size());
        Assert.assertEquals(user.getEmail(), removedRightsResponse.getRemovedRights().get(0).getEmail());
        Assert.assertEquals(site1.getTitle(), removedRightsResponse.getRemovedRights().get(0).getSiteTitle());
        Assert.assertEquals(0, removedRightsResponse.getRemovedUsers().size());


        Assert.assertEquals(1, user2.getUserOnSiteRights().size());
        Assert.assertEquals(site2.getSiteId(), user2.getUserOnSiteRights().get(0).getSiteId());
    }

    @Test
    public void testSendMessageAboutRepealedAccess_forSimpleUser() throws Exception {
        final Config config = ServiceLocator.getConfigStorage().get();
        config.setApplicationUrl("testApplicationUrl");
        config.setApplicationName("testApplicationName");
        config.setSupportEmail("support@email.com");


        final User loginedUser = TestUtil.createUserAndLogin();
        loginedUser.setFirstName("LoginedAdminFirstName");
        loginedUser.setLastName("LoginedAdminLastName");
        final User user = TestUtil.createUser("invitedUser@email.com");
        user.setFirstName("First Name");
        user.setLastName("Last Name");


        new RemoveUserRightsUtil().sendMessageAboutRepealedAccess(user, loginedUser.getUserId(), "siteName");

        final MockMailSender mailSender = (MockMailSender)ServiceLocator.getMailSender();

        Assert.assertEquals(1, mailSender.getMails().size());
        Assert.assertEquals("invitedUser@email.com", mailSender.getMails().get(0).getTo());
        Assert.assertEquals("Web site admin invitation has been withdrawn", mailSender.getMails().get(0).getSubject());
        Assert.assertEquals("Hi, Last Name First Name\n" +
                "\n" +
                "LoginedAdminLastName LoginedAdminFirstName, has withdrawn your access to the siteName site.\n" +
                "\n" +
                config.getApplicationName() + " is a web based tool for quickly and easily creating content rich web sites, to your own specifications. " + config.getApplicationUrl() + "\n" +
                "Please feel free to contact us directly with any questions, " + config.getSupportEmail() + ".", mailSender.getMails().get(0).getText());
    }

    @Test
    public void testSendMessageAboutRepealedAccess_forNetworkUser() throws Exception {
        final Config config = ServiceLocator.getConfigStorage().get();
        config.setApplicationUrl("testApplicationUrl");
        config.setApplicationName("testApplicationName");
        config.setSupportEmail("support@email.com");


        final User loginedUser = TestUtil.createUserAndLogin();
        loginedUser.setFirstName("LoginedAdminFirstName");
        loginedUser.setLastName("LoginedAdminLastName");
        final User user = TestUtil.createUser("invitedUser@email.com");
        user.setFirstName("First Name");
        user.setLastName("Last Name");

        final Site site = TestUtil.createChildSite();
        site.getChildSiteSettings().getChildSiteRegistration().setFromEmail("from@email.com");
        site.getChildSiteSettings().getChildSiteRegistration().setName("Network Name");
        TestUtil.createUserOnSiteRightActiveAdmin(loginedUser, site);


        new RemoveUserRightsUtil().sendMessageAboutRepealedAccess(user, loginedUser.getUserId(), "siteName");

        final MockMailSender mailSender = (MockMailSender)ServiceLocator.getMailSender();

        Assert.assertEquals(1, mailSender.getMails().size());
        Assert.assertEquals("invitedUser@email.com", mailSender.getMails().get(0).getTo());
        Assert.assertEquals("Web site admin invitation has been withdrawn", mailSender.getMails().get(0).getSubject());
        Assert.assertEquals("Hi, Last Name First Name\n" +
                "\n" +
                "LoginedAdminLastName LoginedAdminFirstName, has withdrawn your access to the siteName site.\n" +
                "\n" +
                site.getChildSiteSettings().getChildSiteRegistration().getName() + " is a web based tool for quickly and easily creating content rich web sites, to your own specifications. " + config.getApplicationUrl() + "\n" +
                "Please feel free to contact us directly with any questions, " + site.getChildSiteSettings().getChildSiteRegistration().getFromEmail() + ".",
                mailSender.getMails().get(0).getText());
    }

    @Test
    public void testSendMessageAboutRepealedAccess_forNetworkUser_withEmptyFromEmail() throws Exception {
        final Config config = ServiceLocator.getConfigStorage().get();
        config.setApplicationUrl("testApplicationUrl");
        config.setApplicationName("testApplicationName");
        config.setSupportEmail("support@email.com");


        final User loginedUser = TestUtil.createUserAndLogin();
        loginedUser.setFirstName("LoginedAdminFirstName");
        loginedUser.setLastName("LoginedAdminLastName");
        final User user = TestUtil.createUser("invitedUser@email.com");
        user.setFirstName("First Name");
        user.setLastName("Last Name");

        final Site site = TestUtil.createChildSite();
        site.getChildSiteSettings().getChildSiteRegistration().setFromEmail("");
        site.getChildSiteSettings().getChildSiteRegistration().setName("Network Name");
        TestUtil.createUserOnSiteRightActiveAdmin(loginedUser, site);


        new RemoveUserRightsUtil().sendMessageAboutRepealedAccess(user, loginedUser.getUserId(), "siteName");

        final MockMailSender mailSender = (MockMailSender)ServiceLocator.getMailSender();

        Assert.assertEquals(1, mailSender.getMails().size());
        Assert.assertEquals("invitedUser@email.com", mailSender.getMails().get(0).getTo());
        Assert.assertEquals("Web site admin invitation has been withdrawn", mailSender.getMails().get(0).getSubject());
        Assert.assertEquals("Hi, Last Name First Name\n" +
                "\n" +
                "LoginedAdminLastName LoginedAdminFirstName, has withdrawn your access to the siteName site.\n" +
                "\n" +
                site.getChildSiteSettings().getChildSiteRegistration().getName() + " is a web based tool for quickly and easily creating content rich web sites, to your own specifications. " + config.getApplicationUrl() + "\n" +
                "Please feel free to contact us directly with any questions, " + config.getSupportEmail() + ".", mailSender.getMails().get(0).getText());
    }
}
