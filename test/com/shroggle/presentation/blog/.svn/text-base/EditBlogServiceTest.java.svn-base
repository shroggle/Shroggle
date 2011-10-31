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

package com.shroggle.presentation.blog;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.BlogNameNotUniqueException;
import com.shroggle.exception.BlogNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.site.FunctionalWidgetInfo;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(TestRunnerWithMockServices.class)
public class EditBlogServiceTest {

    @Test
    public void executeFromSiteEditPage() throws Exception {
        final Site site = TestUtil.createSite();
        final PageManager page = TestUtil.createPageVersionAndPage(site);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        blog.setName("aa");
        persistance.putItem(blog);

        final WidgetItem widget = TestUtil.createWidgetItem();
        widget.setDraftItem(blog);
        page.addWidget(widget);

        EditBlogRequest request = new EditBlogRequest();
        request.setWritePosts(AccessGroup.ALL);
        request.setWriteComments(null);
        request.setBlogId(blog.getId());
        request.setWidgetId(widget.getWidgetId());
        request.setWriteCommentsOnComments(null);
        request.setBlogName("new blog name");

        request.setDisplayAuthorEmailAddress(true);
        request.setDisplayAuthorScreenName(false);
        request.setDisplayDate(true);
        request.setDisplayBlogName(true);
        request.setDisplayNextAndPreviousLinks(false);
        request.setDisplayBackToTopLink(true);
        request.setDisplayPosts(DisplayPosts.DISPLAY_WITHIN_DATE_RANGE);
        request.setDisplayPostsFiniteNumber(5);
        request.setDisplayPostsWithinDateRange(10);

        final FunctionalWidgetInfo functionalWidgetInfo = service.execute(request);

        Assert.assertNotNull(functionalWidgetInfo);
        Assert.assertEquals(widget.getWidgetId(), functionalWidgetInfo.getWidget().getWidgetId());

        Assert.assertNotNull(blog);
        Assert.assertEquals("new blog name", blog.getName());
        Assert.assertEquals(true, blog.isDisplayBackToTopLink());
        Assert.assertEquals(false, blog.isDisplayNextAndPreviousLinks());
        Assert.assertEquals(true, blog.isDisplayBlogName());
        Assert.assertEquals(true, blog.isDisplayDate());
        Assert.assertEquals(false, blog.isDisplayAuthorScreenName());
        Assert.assertEquals(true, blog.isDisplayAuthorEmailAddress());
        Assert.assertEquals(DisplayPosts.DISPLAY_WITHIN_DATE_RANGE, blog.getDisplayPosts());
        Assert.assertEquals(5, blog.getDisplayPostsFiniteNumber());
        Assert.assertEquals(10, blog.getDisplayPostsWithinDateRange());
    }

    @Test
    public void executeFromManageItems() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        blog.setName("aa");
        persistance.putItem(blog);

        EditBlogRequest request = new EditBlogRequest();
        request.setWritePosts(AccessGroup.ALL);
        request.setWriteComments(null);
        request.setBlogId(blog.getId());
        request.setWriteCommentsOnComments(null);
        request.setBlogName("new blog name");

        request.setDisplayAuthorEmailAddress(true);
        request.setDisplayAuthorScreenName(false);
        request.setDisplayDate(true);
        request.setDisplayBlogName(true);
        request.setDisplayNextAndPreviousLinks(false);
        request.setDisplayBackToTopLink(true);
        request.setDisplayPosts(DisplayPosts.DISPLAY_WITHIN_DATE_RANGE);
        request.setDisplayPostsFiniteNumber(5);
        request.setDisplayPostsWithinDateRange(10);

        final FunctionalWidgetInfo functionalWidgetInfo = service.execute(request);

        Assert.assertNotNull(functionalWidgetInfo);
        Assert.assertNull(functionalWidgetInfo.getWidget());

        Assert.assertNotNull(blog);
        Assert.assertEquals("new blog name", blog.getName());
        Assert.assertEquals(true, blog.isDisplayBackToTopLink());
        Assert.assertEquals(false, blog.isDisplayNextAndPreviousLinks());
        Assert.assertEquals(true, blog.isDisplayBlogName());
        Assert.assertEquals(true, blog.isDisplayDate());
        Assert.assertEquals(false, blog.isDisplayAuthorScreenName());
        Assert.assertEquals(true, blog.isDisplayAuthorEmailAddress());
        Assert.assertEquals(DisplayPosts.DISPLAY_WITHIN_DATE_RANGE, blog.getDisplayPosts());
        Assert.assertEquals(5, blog.getDisplayPostsFiniteNumber());
        Assert.assertEquals(10, blog.getDisplayPostsWithinDateRange());
    }

    @Test(expected = BlogNameNotUniqueException.class)
    public void executeWithDuplicateBlogName() throws Exception {
        final Site site = TestUtil.createSite();
        final PageManager page = TestUtil.createPageVersionAndPage(site);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        blog.setName("aa");
        persistance.putItem(blog);

        final DraftBlog blog2 = new DraftBlog();
        blog2.setSiteId(site.getSiteId());
        blog2.setName("bb");
        persistance.putItem(blog2);

        final WidgetItem widget = TestUtil.createWidgetItem();
        widget.setDraftItem(blog);
        page.addWidget(widget);

        EditBlogRequest request = new EditBlogRequest();
        request.setWritePosts(AccessGroup.ALL);
        request.setWriteComments(null);
        request.setBlogId(blog.getId());
        request.setWriteCommentsOnComments(null);
        request.setBlogName("bb");

        request.setDisplayAuthorEmailAddress(true);
        request.setDisplayAuthorScreenName(false);
        request.setDisplayDate(true);
        request.setDisplayBlogName(true);
        request.setDisplayNextAndPreviousLinks(false);
        request.setDisplayBackToTopLink(true);
        request.setDisplayPosts(DisplayPosts.DISPLAY_WITHIN_DATE_RANGE);
        request.setDisplayPostsFiniteNumber(5);
        request.setDisplayPostsWithinDateRange(10);

        service.execute(request);
    }

    @Test(expected = BlogNotFoundException.class)
    public void executeWithNotFoundBlog() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        EditBlogRequest request = new EditBlogRequest();
        request.setWritePosts(AccessGroup.ALL);
        request.setWriteComments(null);
        request.setBlogId(-1);
        request.setWriteCommentsOnComments(null);
        request.setBlogName("bb");

        request.setDisplayAuthorEmailAddress(true);
        request.setDisplayAuthorScreenName(false);
        request.setDisplayDate(true);
        request.setDisplayBlogName(true);
        request.setDisplayNextAndPreviousLinks(false);
        request.setDisplayBackToTopLink(true);
        request.setDisplayPosts(DisplayPosts.DISPLAY_WITHIN_DATE_RANGE);
        request.setDisplayPostsFiniteNumber(5);
        request.setDisplayPostsWithinDateRange(10);

        service.execute(request);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLoginedUser() throws Exception {
        service.execute(new EditBlogRequest());
    }

    @Test(expected = WidgetNotFoundException.class)
    public void executeWithNotFoundWidget() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftForum forum = new DraftForum();
        forum.setSiteId(site.getSiteId());
        forum.setName("aa");
        persistance.putItem(forum);

        EditBlogRequest request = new EditBlogRequest();
        request.setWritePosts(AccessGroup.ALL);
        request.setWriteComments(null);
        request.setBlogId(-1);
        request.setWidgetId(-1);
        request.setWriteCommentsOnComments(null);
        request.setBlogName("bb");

        request.setDisplayAuthorEmailAddress(true);
        request.setDisplayAuthorScreenName(false);
        request.setDisplayDate(true);
        request.setDisplayBlogName(true);
        request.setDisplayNextAndPreviousLinks(false);
        request.setDisplayBackToTopLink(true);
        request.setDisplayPosts(DisplayPosts.DISPLAY_WITHIN_DATE_RANGE);
        request.setDisplayPostsFiniteNumber(5);
        request.setDisplayPostsWithinDateRange(10);

        service.execute(request);
    }

    private final EditBlogService service = new EditBlogService();
    private final Persistance persistance = ServiceLocator.getPersistance();

}