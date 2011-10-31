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
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(TestRunnerWithMockServices.class)
public class EditCommentServiceTest {

    @Test
    public void executeAsDraft() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        Comment comment = new Comment();
        comment.setText("11");
        blogPost.addComment(comment);
        persistance.putComment(comment);

        EditCommentRequest request = new EditCommentRequest();
        request.setCommentId(comment.getCommentId());
        request.setCommentText("aaa");
        request.setAsDraft(true);
        service.execute(request);
        Assert.assertNotNull(persistance.getCommentById(comment.getCommentId()));
        Assert.assertEquals("11", comment.getText());
        Assert.assertEquals("aaa", comment.getDraftText());
    }

    @Test
    public void execute() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        Comment comment = new Comment();
        comment.setText("11");
        blogPost.addComment(comment);
        persistance.putComment(comment);

        EditCommentRequest request = new EditCommentRequest();
        request.setCommentId(comment.getCommentId());
        request.setCommentText("aaa");
        service.execute(request);
        Assert.assertNotNull(persistance.getCommentById(comment.getCommentId()));
        Assert.assertNull(comment.getDraftText());
        Assert.assertEquals("aaa", comment.getText());
    }

    @Test
    public void executeWithEmptyText() {
        final DraftBlog blog = new DraftBlog();
        blog.setEditCommentRight(AccessGroup.ALL);
        persistance.putItem(blog);

        final BlogPost blogPost = new BlogPost();
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        final Comment comment = new Comment();
        comment.setText("ccc");
        blogPost.addComment(comment);
        persistance.putComment(comment);

        final EditCommentRequest request = new EditCommentRequest();
        request.setCommentId(comment.getCommentId());
        request.setCommentText(" ");
        service.execute(request);
        Assert.assertNotNull(persistance.getCommentById(comment.getCommentId()));
        Assert.assertEquals("ccc", comment.getText());
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final EditCommentService service = new EditCommentService();

}