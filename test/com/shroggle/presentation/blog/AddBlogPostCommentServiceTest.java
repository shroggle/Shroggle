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
import com.shroggle.exception.CommentAddWithoutRightException;
import com.shroggle.exception.CommentWithNullTextException;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class AddBlogPostCommentServiceTest {

    @Test
    public void execute() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        final BlogPost blogPost = new BlogPost();
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        request.setCommentText("ffff");
        request.setAsDraft(true);
        request.setBlogPostId(blogPost.getBlogPostId());
        service.execute(request);

        Assert.assertEquals(1, blogPost.getComments().size());
        Comment comment = blogPost.getComments().get(0);
        Assert.assertNotNull(comment.getCreated());
        Assert.assertEquals("ffff", comment.getDraftText());
        Assert.assertNull(comment.getText());
        Assert.assertEquals((Integer) user.getUserId(), comment.getVisitorId());
    }

    @Test
    public void executeWithoutDraft() {
        final Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        final BlogPost blogPost = new BlogPost();
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        request.setCommentText("ffff");
        request.setAsDraft(false);
        request.setBlogPostId(blogPost.getBlogPostId());
        service.execute(request);

        Assert.assertEquals(1, blogPost.getComments().size());
        Comment comment = blogPost.getComments().get(0);
        Assert.assertNotNull(comment.getCreated());
        Assert.assertEquals("ffff", comment.getText());
        Assert.assertNull(comment.getDraftText());
        Assert.assertEquals((Integer) user.getUserId(), comment.getVisitorId());
    }

    @Test(expected = CommentWithNullTextException.class)
    public void executeWithoutText() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        final BlogPost blogPost = new BlogPost();
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        request.setBlogPostId(blogPost.getBlogPostId());
        service.execute(request);
    }

    @Test
    public void executeWithoutAuthor() {
        final DraftBlog blog = new DraftBlog();
        blog.setAddCommentOnPostRight(AccessGroup.ALL);
        persistance.putItem(blog);

        final BlogPost blogPost = new BlogPost();
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        request.setCommentText("r44");
        request.setAsDraft(true);
        request.setBlogPostId(blogPost.getBlogPostId());
        service.execute(request);

        Assert.assertEquals(1, blogPost.getComments().size());
        Comment comment = blogPost.getComments().get(0);
        Assert.assertNotNull(comment.getCreated());
        Assert.assertEquals("r44", comment.getDraftText());
        Assert.assertNull(comment.getText());
        Assert.assertNull(comment.getVisitorId());
    }

    @Test(expected = CommentAddWithoutRightException.class)
    public void executeWithNotFoundBlogPost() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);

        request.setBlogPostId(1);
        service.execute(request);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final AddBlogPostCommentRequest request = new AddBlogPostCommentRequest();
    private final AddBlogPostCommentService service = new AddBlogPostCommentService();

}
