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
package com.shroggle.logic.blog;

import com.shroggle.PersistanceMock;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.BlogNotFoundException;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class BlogManagerTest {

    @Test
    public void createWithNullLoginVisitorId() {
        DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);

        new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP);
    }

    @Test
    public void isAllowComments() {
        DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);

        Assert.assertTrue(new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP).isAllowComments());
    }

    @Test
    public void isAllowAddCommentsForOwner() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        blog.setAddCommentOnPostRight(AccessGroup.OWNER);
        persistance.putItem(blog);

        Assert.assertTrue(new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP).isAllowAddComment());
    }

    @Test
    public void isAllowAddCommentsForOwnerWithDisable() {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        blog.setAddCommentOnPostRight(null);
        persistance.putItem(blog);

        Assert.assertFalse(new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP).isAllowAddComment());
    }

    @Test
    public void isAllowCommentsWithDisableComments() {
        DraftBlog blog = new DraftBlog();
        blog.setAddCommentOnPostRight(null);
        persistance.putItem(blog);

        Assert.assertFalse(new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP).isAllowComments());
    }

    @Test(expected = BlogNotFoundException.class)
    public void createWithNotFoundBlog() {
        new BlogManager(1, SiteShowOption.INSIDE_APP);
    }

    @Test
    public void getWorkBlogPostsWithoutLogin() {
        DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setText("f");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        BlogManager blogManager = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP);
        List<BlogPostManager> blogPostManagers = new BlogPostsManager(blogManager, 0, null).getItems();

        Assert.assertNotNull(blogPostManagers);
        Assert.assertEquals(1, blogPostManagers.size());
        BlogPostManager blogPostManager = blogPostManagers.get(0);
        Assert.assertFalse(blogPostManager.isOwner());
        Assert.assertEquals(blogPost, blogPostManager.getBlogPost());
        Assert.assertFalse(blogPostManager.isAuthor());
        Assert.assertFalse(blogPostManager.isEditable());
    }

    @Test
    public void getWorkBlogPostsWithoutLoginWithEditForAll() {
        DraftBlog blog = new DraftBlog();
        blog.setEditBlogPostRight(AccessGroup.ALL);
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setText("f");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        BlogManager blogManager = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP);
        List<BlogPostManager> blogPostManagers = new BlogPostsManager(blogManager, 0, null).getItems();

        Assert.assertNotNull(blogPostManagers);
        Assert.assertEquals(1, blogPostManagers.size());
        BlogPostManager blogPostManager = blogPostManagers.get(0);
        Assert.assertFalse(blogPostManager.isOwner());
        Assert.assertEquals(blogPost, blogPostManager.getBlogPost());
        Assert.assertFalse(blogPostManager.isAuthor());
        Assert.assertTrue(blogPostManager.isEditable());
    }

    @Test
    public void getDraftBlogPostsWithoutLoginWithEditForAll() {
        DraftBlog blog = new DraftBlog();
        blog.setEditBlogPostRight(AccessGroup.ALL);
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setDraftText("f");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        BlogManager blogManager = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP);
        List<BlogPostManager> blogPostManagers = new BlogPostsManager(blogManager, 0, null).getItems();

        Assert.assertNotNull(blogPostManagers);
        Assert.assertEquals(1, blogPostManagers.size());
        BlogPostManager blogPostManager = blogPostManagers.get(0);
        Assert.assertFalse(blogPostManager.isOwner());
        Assert.assertEquals(blogPost, blogPostManager.getBlogPost());
        Assert.assertFalse(blogPostManager.isAuthor());
        Assert.assertTrue(blogPostManager.isEditable());
        Assert.assertTrue(blogPostManager.isDraft());
    }

    @Test
    public void getWorkBlogPostsWithoutLoginWithEditForVisitors() {
        DraftBlog blog = new DraftBlog();
        blog.setEditBlogPostRight(AccessGroup.VISITORS);
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setText("f");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        BlogManager blogManager = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP);
        List<BlogPostManager> blogPostManagers = new BlogPostsManager(blogManager, 0, null).getItems();

        Assert.assertNotNull(blogPostManagers);
        Assert.assertEquals(1, blogPostManagers.size());
        BlogPostManager blogPostManager = blogPostManagers.get(0);
        Assert.assertFalse(blogPostManager.isOwner());
        Assert.assertEquals(blogPost, blogPostManager.getBlogPost());
        Assert.assertFalse(blogPostManager.isAuthor());
        Assert.assertFalse(blogPostManager.isEditable());
    }

    @Test
    public void getWorkDraftBlogPostsWithoutLogin() {
        DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setText("f");
        blogPost.setDraftText("11");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        BlogManager blogManager = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP);
        List<BlogPostManager> blogPostManagers = new BlogPostsManager(blogManager, 0, null).getItems();

        Assert.assertNotNull(blogPostManagers);
        Assert.assertEquals(1, blogPostManagers.size());
        BlogPostManager blogPostManager = blogPostManagers.get(0);
        Assert.assertFalse(blogPostManager.isOwner());
        Assert.assertFalse(blogPostManager.isDraft());
        Assert.assertEquals(blogPost, blogPostManager.getBlogPost());
        Assert.assertFalse(blogPostManager.isAuthor());
    }

    @Test
    public void getDraftBlogPostsWithoutLogin() {
        DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setDraftText("f");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        BlogManager blogManager = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP);
        List<BlogPostManager> blogPostManagers = new BlogPostsManager(blogManager, 0, null).getItems();

        Assert.assertNotNull(blogPostManagers);
        Assert.assertTrue(blogPostManagers.isEmpty());
    }

    @Test
    public void getWorkBlogPostsWithAuthor() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site);

        DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setText("f");
        blogPost.setVisitorId(user.getUserId());
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        BlogManager blogManager = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP);
        List<BlogPostManager> blogPostManagers = new BlogPostsManager(blogManager, 0, null).getItems();

        Assert.assertNotNull(blogPostManagers);
        Assert.assertEquals(1, blogPostManagers.size());
        BlogPostManager blogPostManager = blogPostManagers.get(0);
        Assert.assertEquals(blogPost, blogPostManager.getBlogPost());
        Assert.assertFalse(blogPostManager.isOwner());
        Assert.assertFalse(blogPostManager.isDraft());
        Assert.assertTrue(blogPostManager.isAuthor());
    }

    @Test
    public void getDraftBlogPostsWithAuthor() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site);

        DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setDraftText("f");
        blogPost.setVisitorId(user.getUserId());
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        BlogManager blogManager = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP);
        List<BlogPostManager> blogPostManagers = new BlogPostsManager(blogManager, 0, null).getItems();

        Assert.assertNotNull(blogPostManagers);
        Assert.assertEquals(1, blogPostManagers.size());
        BlogPostManager blogPostManager = blogPostManagers.get(0);
        Assert.assertEquals(blogPost, blogPostManager.getBlogPost());
        Assert.assertFalse(blogPostManager.isOwner());
        Assert.assertTrue(blogPostManager.isAuthor());
        Assert.assertTrue(blogPostManager.isDraft());
    }

    @Test
    public void getDraftBlogPostsWithOtherAuthor() {
        User user = new User();
        persistance.putUser(user);

        User otherUser = new User();
        persistance.putUser(otherUser);

        DraftBlog blog = new DraftBlog();
        blog.setEditBlogPostRight(AccessGroup.OWNER);
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setDraftText("f");
        blogPost.setVisitorId(otherUser.getUserId());
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        BlogManager blogManager = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP);
        List<BlogPostManager> blogPostManagers = new BlogPostsManager(blogManager, 0, null).getItems();

        Assert.assertNotNull(blogPostManagers);
        Assert.assertTrue(blogPostManagers.isEmpty());
    }

    @Test
    public void getWorkBlogPostsWithOwner() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setText("f");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        BlogManager blogManager = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP);
        List<BlogPostManager> blogPostManagers = new BlogPostsManager(blogManager, 0, null).getItems();

        Assert.assertNotNull(blogPostManagers);
        Assert.assertEquals(1, blogPostManagers.size());
        BlogPostManager blogPostManager = blogPostManagers.get(0);
        Assert.assertFalse(blogPostManager.isAuthor());
        Assert.assertEquals(blogPost, blogPostManager.getBlogPost());
        Assert.assertFalse(blogPostManager.isDraft());
        Assert.assertTrue(blogPostManager.isOwner());
    }

    @Test
    public void getWorkDraftBlogPostsWithOwner() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setText("f1");
        blogPost.setDraftText("f");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        BlogManager blogManager = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP);
        List<BlogPostManager> blogPostManagers = new BlogPostsManager(blogManager, 0, null).getItems();

        Assert.assertNotNull(blogPostManagers);
        Assert.assertEquals(1, blogPostManagers.size());
        BlogPostManager blogPostManager = blogPostManagers.get(0);
        Assert.assertFalse(blogPostManager.isAuthor());
        Assert.assertEquals(blogPost, blogPostManager.getBlogPost());
        Assert.assertTrue(blogPostManager.isDraft());
        Assert.assertTrue(blogPostManager.isOwner());
    }

    @Test
    public void getDraftBlogPostsWithOwner() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setDraftText("f");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        BlogManager blogManager = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP);
        List<BlogPostManager> blogPostManagers = new BlogPostsManager(blogManager, 0, null).getItems();

        Assert.assertNotNull(blogPostManagers);
        Assert.assertEquals(1, blogPostManagers.size());
        BlogPostManager blogPostManager = blogPostManagers.get(0);
        Assert.assertEquals(blogPost, blogPostManager.getBlogPost());
        Assert.assertFalse(blogPostManager.isAuthor());
        Assert.assertTrue(blogPostManager.isDraft());
        Assert.assertTrue(blogPostManager.isOwner());
    }

    @Test
    public void getWorkBlogPostWorkComments() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        blog.setEditBlogPostRight(AccessGroup.ALL);
        blog.setEditCommentRight(AccessGroup.VISITORS);
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setText("f");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        Comment comment = new Comment();
        blogPost.addComment(comment);
        comment.setText("fg");
        persistance.putComment(comment);

        BlogManager blogManager = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP);
        List<CommentSecurity> commentSecurities = blogManager.getBlogPostComments(blogPost);
        Assert.assertNotNull(commentSecurities);
        Assert.assertEquals(1, commentSecurities.size());
        CommentSecurity commentSecurity = commentSecurities.get(0);
        Assert.assertEquals(comment, commentSecurity.getComment());
        Assert.assertFalse(commentSecurity.isDraft());
        Assert.assertTrue(commentSecurity.isEditable());
        Assert.assertTrue(commentSecurity.isOwner());
    }

    @Test
    public void getDraftBlogPostDraftCommentsForAll() {
        DraftBlog blog = new DraftBlog();
        blog.setEditBlogPostRight(AccessGroup.ALL);
        blog.setEditCommentRight(AccessGroup.ALL);
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setDraftText("a");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        Comment comment = new Comment();
        comment.setDraftText("f");
        blogPost.addComment(comment);
        persistance.putComment(comment);

        BlogManager blogManager = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP);
        List<CommentSecurity> commentSecurities = blogManager.getBlogPostComments(blogPost);
        Assert.assertNotNull(commentSecurities);
        Assert.assertEquals(1, commentSecurities.size());
        CommentSecurity commentSecurity = commentSecurities.get(0);
        Assert.assertEquals(comment, commentSecurity.getComment());
        Assert.assertTrue(commentSecurity.isDraft());
        Assert.assertTrue(commentSecurity.isEditable());
        Assert.assertFalse(commentSecurity.isOwner());
    }

    @Test
    public void getDraftBlogPostDraftCommentsWithoutLogin() {
        DraftBlog blog = new DraftBlog();
        blog.setEditCommentRight(AccessGroup.OWNER);
        blog.setEditBlogPostRight(AccessGroup.OWNER);
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setDraftText("f");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        Comment comment = new Comment();
        blogPost.addComment(comment);
        comment.setDraftText("fg");
        persistance.putComment(comment);

        BlogManager blogManager = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP);
        List<CommentSecurity> commentSecurities = blogManager.getBlogPostComments(blogPost);
        Assert.assertNotNull(commentSecurities);
        Assert.assertTrue(commentSecurities.isEmpty());
    }

    @Test
    public void getWorkBlogPostDraftCommentsWithoutLogin() {
        DraftBlog blog = new DraftBlog();
        blog.setEditCommentRight(AccessGroup.OWNER);
        blog.setEditBlogPostRight(AccessGroup.OWNER);
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setText("f");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        Comment comment = new Comment();
        blogPost.addComment(comment);
        comment.setDraftText("fg");
        persistance.putComment(comment);

        BlogManager blogManager = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP);
        List<CommentSecurity> commentSecurities = blogManager.getBlogPostComments(blogPost);
        Assert.assertNotNull(commentSecurities);
        Assert.assertTrue(commentSecurities.isEmpty());
    }

    @Test
    public void getWorkBlogPostDraftCommentsWithOtherAuthor() {
        User user = new User();
        persistance.putUser(user);

        DraftBlog blog = new DraftBlog();
        blog.setEditCommentRight(AccessGroup.OWNER);
        blog.setEditBlogPostRight(AccessGroup.OWNER);
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setText("f");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        Comment comment = new Comment();
        blogPost.addComment(comment);
        comment.setDraftText("fg");
        persistance.putComment(comment);

        BlogManager blogManager = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP);
        List<CommentSecurity> commentSecurities = blogManager.getBlogPostComments(blogPost);
        Assert.assertNotNull(commentSecurities);
        Assert.assertTrue(commentSecurities.isEmpty());
    }

    @Before
    public void before() {
        persistance = new PersistanceMock();
        ServiceLocator.setPersistance(persistance);
    }

    @Test
    public void testCreateDisplayedPostsSize_DISPLAY_WITHIN_DATE_RANGE() throws Exception {
        ServiceLocator.getConfigStorage().get().setShowBlogPostCount(100);
        final Site site = TestUtil.createSite();
        final DraftBlog blog = TestUtil.createBlog(site);
        blog.setDisplayPosts(DisplayPosts.DISPLAY_WITHIN_DATE_RANGE);
        int displayedPostsSize = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP).getDisplayedPostsSize();
        Assert.assertEquals(displayedPostsSize, ServiceLocator.getConfigStorage().get().getShowBlogPostCount());
    }

    @Test
    public void testCreateDisplayedPostsSize_DISPLAY_ALL() throws Exception {
        ServiceLocator.getConfigStorage().get().setShowBlogPostCount(100);
        final Site site = TestUtil.createSite();
        final DraftBlog blog = TestUtil.createBlog(site);
        blog.setDisplayPosts(DisplayPosts.DISPLAY_ALL);
        int displayedPostsSize = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP).getDisplayedPostsSize();
        Assert.assertEquals(displayedPostsSize, -1);
    }

    @Test
    public void testCreateDisplayedPostsSize_DISPLAY_FINITE_NUMBER() throws Exception {
        ServiceLocator.getConfigStorage().get().setShowBlogPostCount(100);
        final Site site = TestUtil.createSite();
        final DraftBlog blog = TestUtil.createBlog(site);
        blog.setDisplayPostsFiniteNumber(123);
        blog.setDisplayPosts(DisplayPosts.DISPLAY_FINITE_NUMBER);
        int displayedPostsSize = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP).getDisplayedPostsSize();
        Assert.assertEquals(displayedPostsSize, 123);
    }

    @Ignore
    @Test
    public void testGetNotOlderThanDateForPosts_with_DISPLAY_WITHIN_DATE_RANGE() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftBlog blog = TestUtil.createBlog(site);
        blog.setDisplayPosts(DisplayPosts.DISPLAY_WITHIN_DATE_RANGE);
        blog.setDisplayPostsWithinDateRange(10);

        final Calendar currentDate = new GregorianCalendar();
        currentDate.set(Calendar.YEAR, 2009);
        currentDate.set(Calendar.MONTH, 1);
        currentDate.set(Calendar.HOUR, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MILLISECOND, 0);


        final Date notOlderThan = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP).getNotOlderThanDateForPosts(currentDate.getTime());
        Calendar notOlderThanCalendar = new GregorianCalendar();
        notOlderThanCalendar.setTime(notOlderThan);
        Assert.assertEquals(2008, notOlderThanCalendar.get(Calendar.YEAR));
        Assert.assertEquals(3, notOlderThanCalendar.get(Calendar.MONTH));
    }

    @Test
    public void testGetNotOlderThanDateForPosts_without_DISPLAY_WITHIN_DATE_RANGE() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftBlog blog = TestUtil.createBlog(site);
        blog.setDisplayPosts(DisplayPosts.DISPLAY_ALL);
        blog.setDisplayPostsWithinDateRange(10);

        final Date notOlderThan = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP).getNotOlderThanDateForPosts(new Date());
        Assert.assertNull(notOlderThan);
    }

    private Persistance persistance;

}
