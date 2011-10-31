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
import com.shroggle.exception.SiteItemNotFoundException;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.SiteOnItemRightExistException;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.mail.MockMailSender;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class SendRequestContentServiceTest {

    @Test
    public void execute() {
        final User requester = TestUtil.createUserAndLogin("a@a");
        requester.setFirstName("BlogsRequesterFirstName");
        requester.setLastName("BlogsRequesterLastName");

        final Site targetSite = TestUtil.createSite("siteWithoutBlog", "siteWithoutBlogUrl");
        TestUtil.createUserOnSiteRightActive(requester, targetSite, SiteAccessLevel.ADMINISTRATOR);

        final User itemOwner = TestUtil.createUser("b@b");
        itemOwner.setFirstName("BlogsOwnerFirstName");
        itemOwner.setLastName("BlogsOwnerLastName");
        final Site site = TestUtil.createSite("siteWithCoolBlog", "url");
        TestUtil.createUserOnSiteRightActive(itemOwner, site, SiteAccessLevel.ADMINISTRATOR);
        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        blog.setName("My cool blog");
        persistance.putItem(blog);

        service.execute(targetSite.getSiteId(), blog.getId(), ItemType.BLOG, null);

        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertEquals("b@b", mockMailSender.getMails().get(0).getTo());
        
        Assert.assertEquals(true, mockMailSender.getMails().get(0).getText().startsWith("Hi BlogsOwnerFirstName,\n" +
                "\n" +
                "BlogsRequesterFirstName BlogsRequesterLastName is requesting that you please share your Blog called 'My cool blog' with his/her siteWithoutBlog site (http://siteWithoutBlogUrl.shroggle.com).\n" +
                "\n" +
                "\n" +
                "Accept the request: http://testApplicationUrl/site/acceptRequestContent.action?itemType=BLOG&siteItemId=100006&targetSiteId=1&acceptCode="));

        Assert.assertEquals(1, persistance.getSiteOnItemsByItem(blog.getId()).size());
        SiteOnItem siteOnItemRight1 = persistance.getSiteOnItemsByItem(blog.getId()).get(0);
        Assert.assertEquals(blog.getId(), siteOnItemRight1.getItem().getId());
        Assert.assertEquals((Integer) requester.getUserId(), siteOnItemRight1.getRequestedUserId());
        Assert.assertNull(siteOnItemRight1.getAcceptDate());
        Assert.assertNotNull(siteOnItemRight1.getSendDate());
        Assert.assertNotNull(siteOnItemRight1.getAcceptCode());
    }

    @Test(expected = SiteOnItemRightExistException.class)
    public void executeForOwner() {
        final User user = TestUtil.createUserAndLogin("b@b");
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        service.execute(site.getSiteId(), blog.getId(), ItemType.BLOG, null);

        Assert.assertEquals(0, mockMailSender.getMails().size());
        Assert.assertEquals(0, persistance.getSiteOnItemsByItem(blog.getId()).size());
    }

    @Test(expected = SiteOnItemRightExistException.class)
    public void executeDouble() {
        final Site targetSite = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(targetSite, SiteAccessLevel.ADMINISTRATOR);

        final Site site = TestUtil.createSite();
        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        final SiteOnItem siteOnBlogRight = new SiteOnItem();
        siteOnBlogRight.getId().setSite(targetSite);
        siteOnBlogRight.getId().setItem(blog);
        persistance.putSiteOnItem(siteOnBlogRight);

        service.execute(targetSite.getSiteId(), blog.getId(), ItemType.BLOG, null);

        Assert.assertEquals(0, mockMailSender.getMails().size());
        Assert.assertEquals(1, persistance.getSiteOnItemsByItem(blog.getId()).size());
        Assert.assertEquals(blog.getId(), siteOnBlogRight.getItem().getId());
    }

    @Test(expected = SiteItemNotFoundException.class)
    public void executeWithoutSiteItemId() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        service.execute(site.getSiteId(), -1, ItemType.BLOG, null);
    }

    @Test(expected = SiteNotFoundException.class)
    public void executeWithoutSite() {
        final DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);

        TestUtil.createUserAndLogin();

        service.execute(1, blog.getId(), ItemType.BLOG, null);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() {
        final Site site = TestUtil.createSite();
        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        service.execute(site.getSiteId(), blog.getId(), ItemType.BLOG, null);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final MockMailSender mockMailSender = (MockMailSender) ServiceLocator.getMailSender();
    private final SendRequestContentService service = new SendRequestContentService();

}
