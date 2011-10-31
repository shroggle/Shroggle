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
package com.shroggle.util.persistance.hibernate;

import com.shroggle.entity.DraftBlog;
import com.shroggle.entity.BlogPost;
import com.shroggle.entity.User;
import com.shroggle.entity.Comment;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Artem Stasuk
 */
public class HibernateRemoveUserWithResetBlogTest extends HibernatePersistanceTestBase {

    @Before
    public void before() {
        super.before();

        final User user1 = new User();
        user1.setEmail("a1@a");
        persistance.putUser(user1);

        final User user2 = new User();
        user2.setEmail("a2@a");
        persistance.putUser(user2);

        final DraftBlog blog = new DraftBlog();
        blog.setName("gg");
        persistance.putItem(blog);

        final BlogPost blogPost1 = new BlogPost();
        blog.addBlogPost(blogPost1);
        blogPost1.setVisitorId(user1.getUserId());
        persistance.putBlogPost(blogPost1);
        blogPostId1 = blogPost1.getBlogPostId();

        final BlogPost blogPost2 = new BlogPost();
        blog.addBlogPost(blogPost2);
        blogPost2.setVisitorId(user2.getUserId());
        persistance.putBlogPost(blogPost2);
        blogPostId2 = blogPost2.getBlogPostId();

        final Comment comment1 = new Comment();
        blogPost1.addComment(comment1);
        comment1.setVisitorId(user1.getUserId());
        persistance.putComment(comment1);
        commentId1 = comment1.getCommentId();

        final Comment comment2 = new Comment();
        blogPost2.addComment(comment2);
        comment2.setVisitorId(user2.getUserId());
        persistance.putComment(comment2);
        commentId2 = comment2.getCommentId();

        userId1 = user1.getUserId();
    }

    @Test
    public void execute() {
        persistance.removeUser(persistance.getUserById(userId1));

        Assert.assertNull(persistance.getBlogPostById(blogPostId1).getVisitorId());
        Assert.assertNull(persistance.getCommentById(commentId1).getVisitorId());
        Assert.assertNotNull(persistance.getCommentById(commentId2).getVisitorId());
        Assert.assertNotNull(persistance.getBlogPostById(blogPostId2).getVisitorId());
    }

    private int userId1;
    private int blogPostId1;
    private int commentId1;
    private int blogPostId2;
    private int commentId2;

}