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

import com.shroggle.TestBaseWithMockService;
import com.shroggle.TestUtil;
import com.shroggle.entity.AccessGroup;
import com.shroggle.entity.BlogPost;
import com.shroggle.entity.DraftBlog;
import com.shroggle.exception.BlogPostNotFoundException;
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class EditBlogPostServiceTest extends TestBaseWithMockService {

    @Test(expected = BlogPostNotFoundException.class)
    public void executePostBlogNotFound() {
        final EditBlogPostRequest request = new EditBlogPostRequest();
        request.setBlogPostId(-1);
        request.setBlogPostText("fff");
        service.execute(request);
    }

    @Test
    public void executeAsDraft() {
        final DraftBlog blog = new DraftBlog();
        blog.setEditBlogPostRight(AccessGroup.ALL);
        persistance.putItem(blog);

        final BlogPost blogPost = new BlogPost();
        blogPost.setText("11");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        final EditBlogPostRequest request = new EditBlogPostRequest();
        request.setBlogPostId(blogPost.getBlogPostId());
        request.setBlogPostText("aaa");
        request.setAsDraft(true);
        service.execute(request);
        Assert.assertNotNull(persistance.getBlogPostById(blogPost.getBlogPostId()));
        Assert.assertEquals("11", blogPost.getText());
        Assert.assertEquals("aaa", blogPost.getDraftText());
    }

    @Test
    public void execute() {
        final DraftBlog blog = new DraftBlog();
        blog.setEditBlogPostRight(AccessGroup.ALL);
        persistance.putItem(blog);

        final BlogPost blogPost = new BlogPost();
        blogPost.setText("11");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        final EditBlogPostRequest request = new EditBlogPostRequest();
        request.setBlogPostId(blogPost.getBlogPostId());
        request.setBlogPostText("aaa");
        request.setAsDraft(false);
        service.execute(request);
        Assert.assertNotNull(persistance.getBlogPostById(blogPost.getBlogPostId()));
        Assert.assertEquals("aaa", blogPost.getText());
        Assert.assertNull(blogPost.getDraftText());
    }


    @Test
    public void execute_withCreationDateNull() {
        final DraftBlog blog = new DraftBlog();
        blog.setEditBlogPostRight(AccessGroup.ALL);
        persistance.putItem(blog);

        final BlogPost blogPost = new BlogPost();
        final Date creationDate = new Date(System.currentTimeMillis() / 2);
        blogPost.setCreationDate(creationDate);
        blogPost.setText("11");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        final EditBlogPostRequest request = new EditBlogPostRequest();
        request.setBlogPostId(blogPost.getBlogPostId());
        request.setBlogPostText("aaa");
        request.setAsDraft(false);
        request.setCreationDateString(null);

        String creationDateString = service.execute(request);
        Assert.assertNotNull(persistance.getBlogPostById(blogPost.getBlogPostId()));
        Assert.assertEquals("aaa", blogPost.getText());
        Assert.assertEquals(creationDate, blogPost.getCreationDate());
        Assert.assertEquals(creationDateString, DateUtil.getDateForBlogAndBlogSummary(blogPost.getCreationDate()));
        Assert.assertNull(blogPost.getDraftText());
    }

    @Test
    public void execute_withCreationDateNotNull() {
        final DraftBlog blog = new DraftBlog();
        blog.setEditBlogPostRight(AccessGroup.ALL);
        persistance.putItem(blog);

        final BlogPost blogPost = new BlogPost();
        final Calendar calendar = new GregorianCalendar(1, 1, 1);
        calendar.set(Calendar.MINUTE, 10);
        calendar.set(Calendar.SECOND, 20);
        calendar.set(Calendar.MILLISECOND, 30);
        blogPost.setCreationDate(calendar.getTime());
        blogPost.setText("11");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        final EditBlogPostRequest request = new EditBlogPostRequest();
        request.setBlogPostId(blogPost.getBlogPostId());
        request.setBlogPostText("aaa");
        request.setAsDraft(false);

        request.setCreationDateString("01/20/2010");

        String creationDateString = service.execute(request);
        Assert.assertNotNull(persistance.getBlogPostById(blogPost.getBlogPostId()));
        Assert.assertEquals("aaa", blogPost.getText());
        Assert.assertEquals(creationDateString, DateUtil.getDateForBlogAndBlogSummary(blogPost.getCreationDate()));

        final Calendar creationDateAfterExecution = Calendar.getInstance();
        creationDateAfterExecution.setTime(blogPost.getCreationDate());

        Assert.assertEquals("01/20/2010", DateUtil.toMonthDayAndYear(creationDateAfterExecution.getTime()));

        Assert.assertEquals(10, creationDateAfterExecution.get(Calendar.MINUTE));
        Assert.assertEquals(20, creationDateAfterExecution.get(Calendar.SECOND));
        Assert.assertEquals(30, creationDateAfterExecution.get(Calendar.MILLISECOND));
    }

    @Test
    public void executeWithEmptyText() {
        final DraftBlog blog = new DraftBlog();
        blog.setEditBlogPostRight(AccessGroup.ALL);
        persistance.putItem(blog);

        final BlogPost blogPost = new BlogPost();
        blogPost.setText("ccc");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        final EditBlogPostRequest request = new EditBlogPostRequest();
        request.setBlogPostId(blogPost.getBlogPostId());
        request.setBlogPostText(" ");
        service.execute(request);

        Assert.assertNotNull(persistance.getBlogPostById(blogPost.getBlogPostId()));
        Assert.assertEquals("ccc", blogPost.getText());
    }

    @Test(expected = BlogPostNotFoundException.class)
    public void executeWithLoginNotOwner() {
        TestUtil.createUserAndUserOnSiteRightAndLogin(TestUtil.createSite());

        final DraftBlog blog = new DraftBlog();
        blog.setEditBlogPostRight(AccessGroup.OWNER);
        persistance.putItem(blog);

        final BlogPost blogPost = new BlogPost();
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        final EditBlogPostRequest request = new EditBlogPostRequest();
        request.setBlogPostId(blogPost.getBlogPostId());
        request.setBlogPostText("bbb");
        service.execute(request);
        Assert.assertNotNull(persistance.getBlogPostById(blogPost.getBlogPostId()));
    }

    @Test(expected = BlogPostNotFoundException.class)
    public void executeWithoutLogin() {
        final DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);

        final BlogPost blogPost = new BlogPost();
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        final EditBlogPostRequest request = new EditBlogPostRequest();
        request.setBlogPostId(blogPost.getBlogPostId());
        request.setBlogPostText("bbb");
        service.execute(request);
    }

    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
    private final EditBlogPostService service = new EditBlogPostService();

}