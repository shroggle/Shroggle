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

import org.junit.runner.RunWith;
import org.junit.Test;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.entity.User;
import com.shroggle.entity.BlogPost;
import junit.framework.Assert;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class BlogPostManagerTest {

    @Test
    public void getPostAuthor() {
        final User user = TestUtil.createUser();
        user.setLastName("userLastName");
        user.setFirstName("userFirstName");

        BlogPost blogPost = TestUtil.createBlogPost("post text ", user);

        Assert.assertEquals("userLastName userFirstName", new BlogPostManager(blogPost).getPostAuthor());
    }


    @Test
    public void getPostAuthorWithoutVisitorFirstAndLastName() {
        final User user = new User();
        user.setLastName("userLastName");
        user.setFirstName("userFirstName");
        ServiceLocator.getPersistance().putUser(user);

        BlogPost blogPost = TestUtil.createBlogPost("post text ", user);

        Assert.assertEquals("userLastName userFirstName", new BlogPostManager(blogPost).getPostAuthor());
    }

    @Test
    public void getPostAuthorWithEmptyLastName() {
        final User user = new User();
        user.setFirstName("visitorFirstName");
        ServiceLocator.getPersistance().putUser(user);
        BlogPost blogPost = TestUtil.createBlogPost("post text ", user);

        Assert.assertEquals("visitorFirstName", new BlogPostManager(blogPost).getPostAuthor());
    }

    @Test
    public void getPostAuthorWithEmptyFirstName() {

        final User user = new User();
        user.setLastName("visitorLastName");
        ServiceLocator.getPersistance().putUser(user);
        BlogPost blogPost = TestUtil.createBlogPost("post text ", user);

        Assert.assertEquals("visitorLastName", new BlogPostManager(blogPost).getPostAuthor());
    }

    @Test
    public void getPostAuthorWithEmptyFirstAndLastName() {
        final User user = new User();
        ServiceLocator.getPersistance().putUser(user);
        BlogPost blogPost = TestUtil.createBlogPost("post text ", user);

        Assert.assertEquals("anonymous", new BlogPostManager(blogPost).getPostAuthor());
    }


    @Test
    public void getPostAuthorWithoutVisitor() {
        final User user = new User();
        BlogPost blogPost = TestUtil.createBlogPost("post text ", user);

        Assert.assertEquals("anonymous", new BlogPostManager(blogPost).getPostAuthor());
    }

    @Test
    public void getPostAuthorWithoutUserId() {
        BlogPost blogPost = TestUtil.createBlogPost("post text ");

        Assert.assertEquals("anonymous", new BlogPostManager(blogPost).getPostAuthor());
    }
}
