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
package com.shroggle.presentation.site.requestContent;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.presentation.account.dashboard.DashboardAction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.mail.MockMailSender;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class AcceptRequestContentActionTest {

    @Before
    public void before(){
        ServiceLocator.getConfigStorage().get().setSupportEmail("support@shroggle.com");
    }

    @Test
    public void show() {
        ServiceLocator.getConfigStorage().get().setUserSitesUrl("shroggle.com");

        final Site site = TestUtil.createSite();
        site.setTitle("f");
        final User user = TestUtil.createUserAndLogin();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final DraftBlog blog = new DraftBlog();
        blog.setName("a");
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        final User requestedUser = TestUtil.createUser();
        final Site targetSite = TestUtil.createSite();
        targetSite.setSubDomain("siteUrlPrefix");
        targetSite.setTitle("t");
        final SiteOnItem siteOnBlogRight = new SiteOnItem();
        siteOnBlogRight.getId().setItem(blog);
        siteOnBlogRight.getId().setSite(targetSite);
        siteOnBlogRight.setSendDate(new Date());
        siteOnBlogRight.setAcceptCode("a");
        siteOnBlogRight.setRequestedUserId(requestedUser.getUserId());
        persistance.putSiteOnItem(siteOnBlogRight);

        action.setTargetSiteId(targetSite.getSiteId());
        action.setSiteItemId(blog.getId());
        action.setAcceptCode("a");
        action.setSiteItemType(ItemType.BLOG);
        final ResolutionMock resolutionMock = (ResolutionMock) action.show();

        Assert.assertEquals("/site/acceptRequestContent.jsp", resolutionMock.getForwardToUrl());
        Assert.assertEquals("http://siteUrlPrefix.shroggle.com", action.getTargetSiteUrl());
        Assert.assertEquals("f", action.getSiteTitle());
        Assert.assertEquals("a", action.getSiteItemName());
    }

    @Test
    public void showWithoutRequestedUser() {
        configStorage.get().setApplicationUrl("d");

        final Site site = TestUtil.createSite();
        site.setTitle("f");
        final User user = TestUtil.createUserAndLogin();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final DraftBlog blog = new DraftBlog();
        blog.setName("a");
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        final Site targetSite = TestUtil.createSite();
        targetSite.setTitle("t");
        final SiteOnItem siteOnBlogRight = new SiteOnItem();
        siteOnBlogRight.getId().setItem(blog);
        siteOnBlogRight.getId().setSite(targetSite);
        siteOnBlogRight.setSendDate(new Date());
        siteOnBlogRight.setAcceptCode("a");
        persistance.putSiteOnItem(siteOnBlogRight);

        action.setTargetSiteId(targetSite.getSiteId());
        action.setSiteItemId(blog.getId());
        action.setAcceptCode("a");
        action.setSiteItemType(ItemType.BLOG);
        final ResolutionMock resolutionMock = (ResolutionMock) action.show();

        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void showWithNotFoundRequestedUser() {
        configStorage.get().setApplicationUrl("d");

        final Site site = TestUtil.createSite();
        site.setTitle("f");
        final User user = TestUtil.createUserAndLogin();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final DraftBlog blog = new DraftBlog();
        blog.setName("a");
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        final Site targetSite = TestUtil.createSite();
        targetSite.setTitle("t");
        final SiteOnItem siteOnBlogRight = new SiteOnItem();
        siteOnBlogRight.setRequestedUserId(-1);
        siteOnBlogRight.getId().setItem(blog);
        siteOnBlogRight.getId().setSite(targetSite);
        siteOnBlogRight.setSendDate(new Date());
        siteOnBlogRight.setAcceptCode("a");
        persistance.putSiteOnItem(siteOnBlogRight);

        action.setTargetSiteId(targetSite.getSiteId());
        action.setSiteItemId(blog.getId());
        action.setAcceptCode("a");
        action.setSiteItemType(ItemType.BLOG);
        final ResolutionMock resolutionMock = (ResolutionMock) action.show();

        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void showAfterAccept() {
        configStorage.get().setApplicationUrl("d");

        final Site site = TestUtil.createSite();
        site.setTitle("f");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final DraftBlog blog = new DraftBlog();
        blog.setName("a");
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        final Site targetSite = TestUtil.createSite();
        targetSite.setTitle("t");
        final SiteOnItem siteOnBlogRight = new SiteOnItem();
        siteOnBlogRight.setAcceptDate(new Date());
        siteOnBlogRight.getId().setItem(blog);
        siteOnBlogRight.getId().setSite(targetSite);
        siteOnBlogRight.setSendDate(new Date());
        siteOnBlogRight.setAcceptCode("a");
        persistance.putSiteOnItem(siteOnBlogRight);

        action.setTargetSiteId(targetSite.getSiteId());
        action.setSiteItemId(blog.getId());
        action.setAcceptCode("a");
        action.setSiteItemType(ItemType.BLOG);
        final ResolutionMock resolutionMock = (ResolutionMock) action.show();

        Assert.assertEquals("/site/acceptRequestContentTwice.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void showWithIncorrectAccept() {
        final Site site = TestUtil.createSite();
        site.setTitle("f");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final DraftBlog blog = new DraftBlog();
        blog.setName("a");
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        final Site targetSite = TestUtil.createSite();
        targetSite.setTitle("t");
        final SiteOnItem siteOnBlogRight = new SiteOnItem();
        siteOnBlogRight.getId().setItem(blog);
        siteOnBlogRight.getId().setSite(targetSite);
        siteOnBlogRight.setSendDate(new Date());
        siteOnBlogRight.setAcceptCode("a");
        persistance.putSiteOnItem(siteOnBlogRight);

        action.setTargetSiteId(targetSite.getSiteId());
        action.setSiteItemId(blog.getId());
        action.setAcceptCode("");
        action.setSiteItemType(ItemType.BLOG);
        final ResolutionMock resolutionMock = (ResolutionMock) action.show();

        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void execute() {
        final User owner = TestUtil.createUser();
        owner.setFirstName("OwnerFirstName");
        owner.setLastName("OwnerLastName");

        final Site site = TestUtil.createSite();
        final User requestedUser = TestUtil.createUserAndUserOnSiteRightAndLogin(
                site, SiteAccessLevel.ADMINISTRATOR);
        requestedUser.setFirstName("RequesterFirstName");
        requestedUser.setLastName("RequesterLastName");

        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        blog.setName("My cool blog");
        persistance.putItem(blog);

        final Site targetSite = TestUtil.createSite();
        targetSite.setCustomUrl("test.com.ua");
        TestUtil.createUserAndUserOnSiteRight(targetSite, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserAndUserOnSiteRight(targetSite, SiteAccessLevel.ADMINISTRATOR);

        final SiteOnItem siteOnBlogRight = new SiteOnItem();
        siteOnBlogRight.getId().setItem(blog);
        siteOnBlogRight.getId().setSite(targetSite);
        siteOnBlogRight.setSendDate(new Date());
        siteOnBlogRight.setAcceptCode("a");
        siteOnBlogRight.setRequestedUserId(requestedUser.getUserId());
        persistance.putSiteOnItem(siteOnBlogRight);

        action.setTargetSiteId(targetSite.getSiteId());
        action.setSiteItemId(blog.getId());
        action.setAcceptCode("a");
        action.setSiteItemType(ItemType.BLOG);
        action.setShowOnItemRightType(SiteOnItemRightType.READ);
        action.setOwnerId(owner.getUserId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertTrue(action.isSucessRead());
        Assert.assertEquals(2, mailSender.getMails().size());
        Assert.assertEquals("/site/acceptRequestContent.jsp", resolutionMock.getForwardToUrl());
        Assert.assertNotNull("Action did not accept request!", siteOnBlogRight.getAcceptDate());
        
        Assert.assertEquals("Request for Content Accepted", mailSender.getMails().get(0).getSubject());
        Assert.assertEquals("Dear OwnerFirstName OwnerLastName,\n" +
                "\n" +
                "Regarding: A Request for Content\n" +
                "\n" +
                "You have chosen to share your Blog called 'My cool blog', found on your site called 'title'.\n" +
                "This has successfully been shared with the site called 'title'.\n" +
                "\n" +
                "The requester, RequesterFirstName RequesterLastName now has Read-Only access to this Blog.\n" +
                "\n" +
                "To view or manage shared content, please `login to your dashboard` on the http://www.Web-Deva.comand select the `Manage Blogs` option.\n" +
                "\n" +
                "To contact technical support please email us at support@shroggle.com.", mailSender.getMails().get(0).getText());

        Assert.assertEquals("Request for Content Accepted", mailSender.getMails().get(1).getSubject());
        Assert.assertEquals("Your request to display the Blog called 'My cool blog' on your site: 'title' (http://test.com.ua), has been:\n" +
                "\n" +
                "Accepted\n" +
                "\n" +
                "The content administrator OwnerFirstName OwnerLastName has granted you Read-Only access permission on  Blog, 'My cool blog'.\n" +
                "\n" +
                "To insert this content module on your site: Go to the page where you would like to place the item, select `insert content module` select the module type, and then from the list of existing modules on that page, select this item.\n" +
                "\n" +
                "To view or manage shared content, please `login to your dashboard` on the http://www.Web-Deva.comand select the `Manage Blogs` option.\n" +
                "\n" +
                "To contact technical support please email us at support@shroggle.com.", mailSender.getMails().get(1).getText());
    }

    @Test
    public void executeWithNotFoundRequestedUser() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        final Site targetSite = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(targetSite, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserAndUserOnSiteRight(targetSite, SiteAccessLevel.ADMINISTRATOR);

        final SiteOnItem siteOnBlogRight = new SiteOnItem();
        siteOnBlogRight.getId().setItem(blog);
        siteOnBlogRight.getId().setSite(targetSite);
        siteOnBlogRight.setSendDate(new Date());
        siteOnBlogRight.setAcceptCode("a");
        siteOnBlogRight.setRequestedUserId(-1);
        persistance.putSiteOnItem(siteOnBlogRight);

        action.setTargetSiteId(targetSite.getSiteId());
        action.setSiteItemId(blog.getId());
        action.setAcceptCode("a");
        action.setSiteItemType(ItemType.BLOG);
        action.setShowOnItemRightType(SiteOnItemRightType.READ);
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeWithoutRequestedUser() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        final Site targetSite = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(targetSite, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserAndUserOnSiteRight(targetSite, SiteAccessLevel.ADMINISTRATOR);

        final SiteOnItem siteOnBlogRight = new SiteOnItem();
        siteOnBlogRight.getId().setItem(blog);
        siteOnBlogRight.getId().setSite(targetSite);
        siteOnBlogRight.setSendDate(new Date());
        siteOnBlogRight.setAcceptCode("a");
        persistance.putSiteOnItem(siteOnBlogRight);

        action.setTargetSiteId(targetSite.getSiteId());
        action.setSiteItemId(blog.getId());
        action.setAcceptCode("a");
        action.setSiteItemType(ItemType.BLOG);
        action.setShowOnItemRightType(SiteOnItemRightType.READ);
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeAfterAccept() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        final Site targetSite = TestUtil.createSite();
        final SiteOnItem siteOnBlogRight = new SiteOnItem();
        siteOnBlogRight.setAcceptDate(new Date());
        siteOnBlogRight.getId().setItem(blog);
        siteOnBlogRight.getId().setSite(targetSite);
        siteOnBlogRight.setSendDate(new Date());
        siteOnBlogRight.setAcceptCode("a");
        persistance.putSiteOnItem(siteOnBlogRight);

        action.setTargetSiteId(targetSite.getSiteId());
        action.setSiteItemId(blog.getId());
        action.setAcceptCode("a");
        action.setSiteItemType(ItemType.BLOG);
        action.setShowOnItemRightType(SiteOnItemRightType.READ);
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals("/site/acceptRequestContentTwice.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void executeWithIncorrectAcceptCode() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        final Site targetSite = TestUtil.createSite();
        final SiteOnItem siteOnBlogRight = new SiteOnItem();
        siteOnBlogRight.getId().setItem(blog);
        siteOnBlogRight.getId().setSite(targetSite);
        siteOnBlogRight.setSendDate(new Date());
        siteOnBlogRight.setAcceptCode("a");
        persistance.putSiteOnItem(siteOnBlogRight);

        action.setTargetSiteId(targetSite.getSiteId());
        action.setSiteItemId(blog.getId());
        action.setAcceptCode("A");
        action.setSiteItemType(ItemType.BLOG);
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeWithoutSiteOnItemRight() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);

        final Site targetSite = TestUtil.createSite();

        action.setTargetSiteId(targetSite.getSiteId());
        action.setAcceptCode("a");
        action.setSiteItemType(ItemType.BLOG);
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void showWithoutSiteOnItemRight() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);

        final Site targetSite = TestUtil.createSite();
        action.setTargetSiteId(targetSite.getSiteId());
        action.setAcceptCode("a");
        action.setSiteItemType(ItemType.BLOG);
        final ResolutionMock resolutionMock = (ResolutionMock) action.show();

        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    private final AcceptRequestContentAction action = new AcceptRequestContentAction();
    private final MockMailSender mailSender = (MockMailSender) ServiceLocator.getMailSender();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ConfigStorage configStorage = ServiceLocator.getConfigStorage();

}
