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
package com.shroggle.presentation.account.items;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;
import com.shroggle.util.mail.Mail;
import com.shroggle.util.mail.MockMailSender;
import com.shroggle.util.persistance.Persistance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class RemoveUserItemAccessActionTest {

    @Test
    public void executeForOwnSite() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(-1);
        persistance.putItem(blog);

        SiteOnItem siteOnItemRight = blog.createSiteOnItemRight(site);
        siteOnItemRight.setAcceptDate(new Date());
        persistance.putSiteOnItem(siteOnItemRight);

        action.setSiteId(site.getSiteId());
        action.setItemId(blog.getId());
        action.setItemType(ItemType.BLOG);
        action.setItemType(ItemType.BLOG);
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        junit.framework.Assert.assertEquals(ManageItemsAction.class, resolutionMock.getRedirectByAction());
        junit.framework.Assert.assertEquals(1, mockMailSender.getMails().size());
        junit.framework.Assert.assertNull(persistance.getSiteOnItemRightBySiteIdItemIdAndType(
                site.getSiteId(), blog.getId(), ItemType.BLOG));
    }

    @Test
    public void executeForOtherSite() throws Exception {
        final User ownerUser = TestUtil.createUserAndLogin();
        final Site ownerSite = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(ownerUser, ownerSite, SiteAccessLevel.ADMINISTRATOR);
        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(ownerSite.getSiteId());
        persistance.putItem(blog);

        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final SiteOnItem siteOnItemRight = blog.createSiteOnItemRight(site);
        siteOnItemRight.setAcceptDate(new Date());
        persistance.putSiteOnItem(siteOnItemRight);

        action.setSiteId(site.getSiteId());
        action.setItemId(blog.getId());
        action.setItemType(ItemType.BLOG);
        action.setItemType(ItemType.BLOG);
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        junit.framework.Assert.assertEquals(ManageItemsAction.class, resolutionMock.getRedirectByAction());
        junit.framework.Assert.assertEquals(1, mockMailSender.getMails().size());
        junit.framework.Assert.assertNull(persistance.getSiteOnItemRightBySiteIdItemIdAndType(
                site.getSiteId(), blog.getId(), ItemType.BLOG));
    }

    @Test
    public void testExecute() throws Exception {
        final Config config = ServiceLocator.getConfigStorage().get();
        config.setUserSitesUrl("userSitesUrl.com");
        config.setApplicationUrl("www.userSitesUrl.com");
        config.setSupportEmail("support@email.com");

        final User user1 = TestUtil.createUserAndLogin("email");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        final User user2 = TestUtil.createUserAndLogin("email");
        final Site site = TestUtil.createSite();
        site.setSubDomain("site");
        TestUtil.createUserOnSiteRightActiveAdmin(user1, site);
        TestUtil.createUserOnSiteRightActive(user2, site, SiteAccessLevel.EDITOR);
        final DraftBlog draftBlog = TestUtil.createBlog(TestUtil.createSite());
        final SiteOnItem right = TestUtil.createSiteOnItemRight(site, draftBlog);
        right.setAcceptDate(new Date());


        final RemoveUserItemAccessAction action = new RemoveUserItemAccessAction();
        action.setItemId(draftBlog.getId());
        action.setItemType(ItemType.BLOG);
        action.setSiteId(site.getSiteId());


        action.execute();


        final SiteOnItem siteOnItemRight =
                        ServiceLocator.getPersistance().getSiteOnItemRightBySiteIdItemIdAndType(site.getSiteId(), draftBlog.getId(), ItemType.BLOG);
        Assert.assertEquals(null, siteOnItemRight);
        final MockMailSender mailSender = (MockMailSender)ServiceLocator.getMailSender();


        Assert.assertEquals("Emails must be sent to admins only.", 1, mailSender.getMails().size());
        final Mail mail = mailSender.getMails().get(0);
        Assert.assertEquals(user1.getEmail(), mail.getTo());
        Assert.assertEquals("Dear firstName lastName, \n" +
                "Permission to display Blog: 'Blog1' on your site: http://site.userSitesUrl.com has been\n" +
                "Withdrawn\n" +
                "Please note that the 'Blog1' has been removed from your site. We recommend that you check your site immediately.\n" +
                "To view or manage shared content, please login to your dashboard on the " + config.getApplicationUrl() + " and depending on the type of content you want to manage, select the Manage Blogs, or Manage Forums option.\n" +
                "To contact support please email us at " + config.getSupportEmail(), mail.getText());
    }

    private final RemoveUserItemAccessAction action = new RemoveUserItemAccessAction();
    private final MockMailSender mockMailSender = (MockMailSender) ServiceLocator.getMailSender();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
