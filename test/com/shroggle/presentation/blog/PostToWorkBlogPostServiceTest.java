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
import com.shroggle.exception.BlogPostNotFoundException;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(TestRunnerWithMockServices.class)
public class PostToWorkBlogPostServiceTest {

    @Test
    public void executeWithoutWorkWithLoginOwnerVisitors() {
        executeWithoutWorkWithLoginOwnerInternal(AccessGroup.VISITORS);
    }

    @Test
    public void executeWithoutWorkWithLoginOwnerOwner() {
        executeWithoutWorkWithLoginOwnerInternal(AccessGroup.OWNER);
    }

    @Test
    public void executeWithoutWorkWithLoginOwnerGuest() {
        executeWithoutWorkWithLoginOwnerInternal(AccessGroup.GUEST);
    }

    @Test
    public void executeWithoutWorkWithLoginOwnerAll() {
        executeWithoutWorkWithLoginOwnerInternal(AccessGroup.ALL);
    }

    public void executeWithoutWorkWithLoginOwnerInternal(final AccessGroup editBlogPostRight) {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        blog.setEditBlogPostRight(editBlogPostRight);
        persistance.putItem(blog);

        final BlogPost blogPost = new BlogPost();
        blogPost.setVisitorId(user.getUserId());
        blogPost.setText(null);
        blogPost.setDraftText("22");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        service.execute(blogPost.getBlogPostId());

        Assert.assertEquals("22", blogPost.getText());
        Assert.assertNull(blogPost.getDraftText());
    }

    @Test(expected = BlogPostNotFoundException.class)
    public void executeWithLoginVisitorDeniend() {
        executeWithLoginVisitorWithGuestRightsInternal(AccessGroup.OWNER);
    }

    @Test(expected = BlogPostNotFoundException.class)
    public void executeWithNotFoundLoginVisitorOwner() {
        executeWithNotFoundLoginVisitorInternal(AccessGroup.OWNER);
    }

    @Test(expected = BlogPostNotFoundException.class)
    public void executeWithNotFoundLoginVisitorVisitors() {
        executeWithNotFoundLoginVisitorInternal(AccessGroup.VISITORS);
    }

    @Test(expected = BlogPostNotFoundException.class)
    public void executeWithNotFoundLoginVisitorGuest() {
        executeWithNotFoundLoginVisitorInternal(AccessGroup.GUEST);
    }

    @Test
    public void executeWithNotFoundLoginVisitorAll() {
        executeWithNotFoundLoginVisitorInternal(AccessGroup.ALL);
    }

    @Test
    public void executeWithLoginVisitorVisitors() {
        executeWithLoginVisitorWithGuestRightsInternal(AccessGroup.VISITORS);
    }

    @Test
    public void executeWithLoginVisitorAll() {
        executeWithLoginVisitorWithGuestRightsInternal(AccessGroup.ALL);
    }

    @Test
    public void executeWithLoginVisitorGuest() {
        executeWithLoginVisitorWithGuestRightsInternal(AccessGroup.GUEST);
    }

    public void executeWithLoginVisitorWithGuestRightsInternal(final AccessGroup editBlogPostRight) {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.GUEST);

        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        blog.setEditBlogPostRight(editBlogPostRight);
        persistance.putItem(blog);

        final BlogPost blogPost = new BlogPost();
        blogPost.setText(null);
        blogPost.setDraftText("22");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        service.execute(blogPost.getBlogPostId());

        Assert.assertEquals("22", blogPost.getText());
        Assert.assertNull(blogPost.getDraftText());
    }

    public void executeWithNotFoundLoginVisitorInternal(final AccessGroup editBlogPostRight) {
        TestUtil.createUser();

        final DraftBlog blog = new DraftBlog();
        blog.setEditBlogPostRight(editBlogPostRight);
        persistance.putItem(blog);

        final BlogPost blogPost = new BlogPost();
        blogPost.setVisitorId(-1);
        blogPost.setText(null);
        blogPost.setDraftText("22");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        service.execute(blogPost.getBlogPostId());

        Assert.assertEquals("22", blogPost.getText());
        Assert.assertNull(blogPost.getDraftText());
    }

    @Test(expected = BlogPostNotFoundException.class)
    public void executeWithoutLoginAuhtorOrOwnerVisitor() {
        executeWithoutLoginAuhtorOrOwnerInternal(AccessGroup.VISITORS);
    }

    @Test(expected = BlogPostNotFoundException.class)
    public void executeWithoutLoginAuhtorOrOwnerOwner() {
        executeWithoutLoginAuhtorOrOwnerInternal(AccessGroup.OWNER);
    }

    @Test(expected = BlogPostNotFoundException.class)
    public void executeWithoutLoginAuhtorOrOwnerGuest() {
        executeWithoutLoginAuhtorOrOwnerInternal(AccessGroup.GUEST);
    }

    @Test
    public void executeWithoutLoginAuhtorOrOwnerAll() {
        executeWithoutLoginAuhtorOrOwnerInternal(AccessGroup.ALL);
    }

    public void executeWithoutLoginAuhtorOrOwnerInternal(final AccessGroup editBlogPostRight) {
        final User account = TestUtil.createUser();

        final DraftBlog blog = new DraftBlog();
        blog.setEditBlogPostRight(editBlogPostRight);
        persistance.putItem(blog);

        final BlogPost blogPost = new BlogPost();
        blogPost.setText(null);
        blogPost.setDraftText("22");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        service.execute(blogPost.getBlogPostId());

        Assert.assertEquals("22", blogPost.getText());
        Assert.assertNull(blogPost.getDraftText());
    }

    @Test
    public void executeWithLoginAuhtorGuest() {
        executeWithLoginAuhtorInternal(AccessGroup.GUEST);
    }

    @Test
    public void executeWithLoginAuhtorVisitors() {
        executeWithLoginAuhtorInternal(AccessGroup.VISITORS);
    }

    @Test
    public void executeWithLoginAuhtorOwner() {
        executeWithLoginAuhtorInternal(AccessGroup.OWNER);
    }

    @Test
    public void executeWithLoginAuhtorAll() {
        executeWithLoginAuhtorInternal(AccessGroup.ALL);
    }

    private void executeWithLoginAuhtorInternal(final AccessGroup editBlogPostRight) {
        final User user = TestUtil.createUserAndLogin();


        final DraftBlog blog = new DraftBlog();
        blog.setEditBlogPostRight(editBlogPostRight);
        persistance.putItem(blog);

        final BlogPost blogPost = new BlogPost();
        blogPost.setVisitorId(user.getUserId());
        blogPost.setText("f");
        blogPost.setDraftText("22");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        service.execute(blogPost.getBlogPostId());

        Assert.assertEquals("22", blogPost.getText());
        Assert.assertNull(blogPost.getDraftText());
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
    private final PostToWorkBlogPostService service = new PostToWorkBlogPostService();

}