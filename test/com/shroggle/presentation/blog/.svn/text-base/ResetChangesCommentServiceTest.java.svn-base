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
import com.shroggle.exception.CommentNotFoundException;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(TestRunnerWithMockServices.class)
public class ResetChangesCommentServiceTest {

    @Test
    public void execute() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        blog.setEditCommentRight(AccessGroup.OWNER);
        persistance.putItem(blog);

        final BlogPost blogPost = new BlogPost();
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        final Comment comment = new Comment();
        comment.setText("11");
        comment.setDraftText("22");
        blogPost.addComment(comment);
        persistance.putComment(comment);

        service.execute(comment.getCommentId());
        Assert.assertNotNull(persistance.getCommentById(comment.getCommentId()));
        Assert.assertEquals("11", comment.getText());
        Assert.assertNull(comment.getDraftText());
    }

    @Test
    public void executeWithNullText() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        blog.setEditCommentRight(AccessGroup.OWNER);
        persistance.putItem(blog);

        final BlogPost blogPost = new BlogPost();
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        final Comment comment = new Comment();
        comment.setDraftText("22");
        blogPost.addComment(comment);
        persistance.putComment(comment);

        service.execute(comment.getCommentId());
        Assert.assertNull(
                "Reset comment with null text must be deleted!",
                persistance.getCommentById(comment.getCommentId()));
    }

    @Test(expected = CommentNotFoundException.class)
    public void executeWithoutLogin() {
        final Site site = TestUtil.createSite();

        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        blog.setEditCommentRight(AccessGroup.OWNER);
        persistance.putItem(blog);

        final BlogPost blogPost = new BlogPost();
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        final Comment comment = new Comment();
        comment.setText("11");
        comment.setDraftText("22");
        blogPost.addComment(comment);
        persistance.putComment(comment);

        service.execute(comment.getCommentId());
    }

    @Test
    public void executeWithNullDraftText() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        blog.setEditCommentRight(AccessGroup.OWNER);
        persistance.putItem(blog);

        final BlogPost blogPost = new BlogPost();
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        final Comment comment = new Comment();
        comment.setText("11");
        comment.setDraftText(null);
        blogPost.addComment(comment);
        persistance.putComment(comment);

        service.execute(comment.getCommentId());
        Assert.assertNotNull(persistance.getCommentById(comment.getCommentId()));
        Assert.assertEquals("11", comment.getText());
        Assert.assertNull(comment.getDraftText());
    }

    @Test(expected = CommentNotFoundException.class)
    public void executeCommentNotFound() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        service.execute(1);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ResetChangesCommentService service = new ResetChangesCommentService();

}