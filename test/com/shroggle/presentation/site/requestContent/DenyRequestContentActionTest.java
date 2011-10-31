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
public class DenyRequestContentActionTest {

    @Before
    public void before(){
        ServiceLocator.getConfigStorage().get().setSupportEmail("support@shroggle.com");
    }

    @Test
    public void execute() {
        final Site ownerSite = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(ownerSite, SiteAccessLevel.ADMINISTRATOR);
        final DraftBlog blog = new DraftBlog();
        blog.setName("My cool blog");
        blog.setSiteId(ownerSite.getSiteId());
        persistance.putItem(blog);

        final User user = TestUtil.createUser("f");
        final Site site = TestUtil.createSite();
        site.setCustomUrl("test.com.ua");
        final SiteOnItem siteOnBlogRight = new SiteOnItem();
        siteOnBlogRight.getId().setItem(blog);
        siteOnBlogRight.getId().setSite(site);
        siteOnBlogRight.setSendDate(new Date());
        siteOnBlogRight.setRequestedUserId(user.getUserId());
        siteOnBlogRight.setAcceptCode("a");
        persistance.putSiteOnItem(siteOnBlogRight);

        action.setTargetSiteId(site.getSiteId());
        action.setSiteItemId(blog.getId());
        action.setAcceptCode("a");
        action.setSiteItemType(ItemType.BLOG);
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertEquals("f", mockMailSender.getMails().get(0).getTo());
        Assert.assertEquals("/site/denyRequestContent.jsp", resolutionMock.getForwardToUrl());
        Assert.assertNull(persistance.getSiteOnItemRightBySiteIdItemIdAndType(
                ownerSite.getSiteId(), blog.getId(), ItemType.BLOG));

        Assert.assertEquals("Request for Content Denied", mockMailSender.getMails().get(0).getSubject());
        Assert.assertEquals(
                "Your request to display the Blog called 'My cool blog' on your site: 'title' (http://test.com.ua), has been:\n" +
                "\n" +
                "Denied\n" +
                "\n" +
                "To contact technical support please email us at support@shroggle.com.",
                mockMailSender.getMails().get(0).getText());
    }

    @Test
    public void executeWithoutRequestUser() {
        final Site ownerSite = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(ownerSite, SiteAccessLevel.ADMINISTRATOR);
        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(ownerSite.getSiteId());
        persistance.putItem(blog);

        final Site site = TestUtil.createSite();
        final SiteOnItem siteOnBlogRight = new SiteOnItem();
        siteOnBlogRight.getId().setItem(blog);
        siteOnBlogRight.getId().setSite(site);
        siteOnBlogRight.setSendDate(new Date());
        siteOnBlogRight.setAcceptCode("a");
        persistance.putSiteOnItem(siteOnBlogRight);

        action.setTargetSiteId(site.getSiteId());
        action.setSiteItemId(blog.getId());
        action.setAcceptCode("a");
        action.setSiteItemType(ItemType.BLOG);
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(0, mockMailSender.getMails().size());
        Assert.assertEquals("/site/denyRequestContent.jsp", resolutionMock.getForwardToUrl());
        Assert.assertNull(persistance.getSiteOnItemRightBySiteIdItemIdAndType(
                ownerSite.getSiteId(), blog.getId(), ItemType.BLOG));
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
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        final Site targetSite = TestUtil.createSite();

        action.setTargetSiteId(targetSite.getSiteId());
        action.setSiteItemId(blog.getId());
        action.setAcceptCode("a");
        action.setSiteItemType(ItemType.BLOG);
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeWithoutSiteItem() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);

        final Site targetSite = TestUtil.createSite();
        final SiteOnItem siteOnBlogRight = new SiteOnItem();
        siteOnBlogRight.getId().setSite(targetSite);
        siteOnBlogRight.setSendDate(new Date());
        siteOnBlogRight.setAcceptCode("a");
        persistance.putSiteOnItem(siteOnBlogRight);

        action.setTargetSiteId(targetSite.getSiteId());
        action.setSiteItemId(1);
        action.setAcceptCode("a");
        action.setSiteItemType(ItemType.BLOG);
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    private final DenyRequestContentAction action = new DenyRequestContentAction();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final MockMailSender mockMailSender = (MockMailSender) ServiceLocator.getMailSender();

}