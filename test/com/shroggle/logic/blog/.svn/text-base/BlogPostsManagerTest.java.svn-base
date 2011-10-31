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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.entity.DraftBlog;
import com.shroggle.entity.BlogPost;
import com.shroggle.entity.DisplayPosts;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class BlogPostsManagerTest {

    @Test
    public void getStartBefore() {
        final DraftBlog blog = new DraftBlog();
        blog.setDisplayPosts(DisplayPosts.DISPLAY_WITHIN_DATE_RANGE);
        persistance.putItem(blog);
        final BlogManager blogManager = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP);
        final BlogPostsManager blogPostsManager = new BlogPostsManager(blogManager, 0, null);

        Assert.assertEquals(0, blogPostsManager.getStartBefore());
    }

    @Test
    public void getStartAfter() {
        final DraftBlog blog = new DraftBlog();
        blog.setDisplayPosts(DisplayPosts.DISPLAY_WITHIN_DATE_RANGE);
        persistance.putItem(blog);
        final BlogManager blogManager = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP);
        final BlogPostsManager blogPostsManager = new BlogPostsManager(blogManager, 0, null);

        Assert.assertEquals(0, blogPostsManager.getStartAfter());
    }

    @Test
    public void getStartAfterWithFirstBlogPost() {
        final DraftBlog blog = new DraftBlog();
        blog.setDisplayPosts(DisplayPosts.DISPLAY_WITHIN_DATE_RANGE);
        persistance.putItem(blog);
        final BlogPost blogPost = new BlogPost();
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        final BlogManager blogManager = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP);
        final BlogPostsManager blogPostsManager = new BlogPostsManager(blogManager, 0, blogPost.getBlogPostId());

        Assert.assertEquals(0, blogPostsManager.getStartAfter());
    }

    @Test
    public void getStartAfterWithBlogPost() {
        final DraftBlog blog = new DraftBlog();
        blog.setDisplayPosts(DisplayPosts.DISPLAY_WITHIN_DATE_RANGE);
        persistance.putItem(blog);
        final BlogPost blogPost1 = new BlogPost();
        blog.addBlogPost(blogPost1);
        blogPost1.setCreationDate(new Date(System.currentTimeMillis() * 2));
        persistance.putBlogPost(blogPost1);
        final BlogPost blogPost2 = new BlogPost();
        blog.addBlogPost(blogPost2);
        persistance.putBlogPost(blogPost2);

        final BlogManager blogManager = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP);
        final BlogPostsManager blogPostsManager = new BlogPostsManager(blogManager, 0, blogPost2.getBlogPostId());

        Assert.assertEquals(0, blogPostsManager.getStartAfter());
    }

    @Test
    public void getStartAfterWithBlogPostMorePageSize() {
        configStorage.get().setShowBlogPostCount(1);

        final DraftBlog blog = new DraftBlog();
        blog.setDisplayPosts(DisplayPosts.DISPLAY_WITHIN_DATE_RANGE);
        persistance.putItem(blog);
        final BlogPost blogPost1 = new BlogPost();
        blog.addBlogPost(blogPost1);
        blogPost1.setCreationDate(new Date(System.currentTimeMillis() * 2));
        persistance.putBlogPost(blogPost1);
        final BlogPost blogPost2 = new BlogPost();
        blog.addBlogPost(blogPost2);
        blogPost2.setCreationDate(new Date(System.currentTimeMillis() * 2));
        persistance.putBlogPost(blogPost2);
        final BlogPost blogPost3 = new BlogPost();
        blog.addBlogPost(blogPost3);
        persistance.putBlogPost(blogPost3);

        final BlogManager blogManager = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP);
        final BlogPostsManager blogPostsManager = new BlogPostsManager(blogManager, 0, blogPost3.getBlogPostId());

        Assert.assertEquals(1, blogPostsManager.getStartAfter());
    }


    @Test
    public void getItemsSize_DISPLAY_FINITE_NUMBER_2() {
        configStorage.get().setShowBlogPostCount(1);

        final DraftBlog blog = new DraftBlog();
        blog.setDisplayPosts(DisplayPosts.DISPLAY_FINITE_NUMBER);
        blog.setDisplayPostsFiniteNumber(2);
        persistance.putItem(blog);
        final BlogPost blogPost1 = new BlogPost();
        blog.addBlogPost(blogPost1);
        blogPost1.setCreationDate(new Date(System.currentTimeMillis() * 2));
        persistance.putBlogPost(blogPost1);
        final BlogPost blogPost2 = new BlogPost();
        blog.addBlogPost(blogPost2);
        blogPost2.setCreationDate(new Date(System.currentTimeMillis() * 2));
        persistance.putBlogPost(blogPost2);
        final BlogPost blogPost3 = new BlogPost();
        blog.addBlogPost(blogPost3);
        persistance.putBlogPost(blogPost3);

        final BlogManager blogManager = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP);
        final BlogPostsManager blogPostsManager = new BlogPostsManager(blogManager, 0, null);

        Assert.assertEquals(2, blogPostsManager.getItems().size());
    }

    @Test
    public void getItemsSize_DISPLAY_EXACT_POST() {
        configStorage.get().setShowBlogPostCount(1);

        final DraftBlog blog = new DraftBlog();
        blog.setDisplayPosts(DisplayPosts.DISPLAY_FINITE_NUMBER);
        blog.setDisplayPostsFiniteNumber(2);
        persistance.putItem(blog);

        final Calendar creationDate1 = new GregorianCalendar(2009, 1, 1);
        final BlogPost blogPost1 = new BlogPost();
        blog.addBlogPost(blogPost1);
        blogPost1.setCreationDate(creationDate1.getTime());
        persistance.putBlogPost(blogPost1);

        final Calendar creationDate2 = new GregorianCalendar(2010, 1, 1);
        final BlogPost blogPost2 = new BlogPost();
        blog.addBlogPost(blogPost2);
        blogPost2.setCreationDate(creationDate2.getTime());
        persistance.putBlogPost(blogPost2);

        final Calendar creationDate3 = new GregorianCalendar(2011, 1, 1);
        final BlogPost blogPost3 = new BlogPost();
        blog.addBlogPost(blogPost3);
        blogPost3.setCreationDate(creationDate3.getTime());
        persistance.putBlogPost(blogPost3);

        final BlogManager blogManager = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP);
        final BlogPostsManager blogPostsManager = new BlogPostsManager(blogManager, 0, blogPost2.getBlogPostId());

        Assert.assertEquals(1, blogPostsManager.getItems().size());
        Assert.assertEquals(blogPost3.getBlogPostId(), (int) blogPostsManager.getNextBlogPostId());
        Assert.assertEquals(blogPost1.getBlogPostId(), (int) blogPostsManager.getPrevBlogPostId());
    }

    @Test
    public void getItemsSize_DISPLAY_ALL() {
        configStorage.get().setShowBlogPostCount(1);

        final DraftBlog blog = new DraftBlog();
        blog.setDisplayPosts(DisplayPosts.DISPLAY_ALL);
        blog.setDisplayPostsFiniteNumber(2);
        persistance.putItem(blog);
        final BlogPost blogPost1 = new BlogPost();
        blog.addBlogPost(blogPost1);
        blogPost1.setCreationDate(new Date(System.currentTimeMillis() * 2));
        persistance.putBlogPost(blogPost1);
        final BlogPost blogPost2 = new BlogPost();
        blog.addBlogPost(blogPost2);
        blogPost2.setCreationDate(new Date(System.currentTimeMillis() * 2));
        persistance.putBlogPost(blogPost2);
        final BlogPost blogPost3 = new BlogPost();
        blog.addBlogPost(blogPost3);
        persistance.putBlogPost(blogPost3);

        final BlogManager blogManager = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP);
        final BlogPostsManager blogPostsManager = new BlogPostsManager(blogManager, 0, null);

        Assert.assertEquals(3, blogPostsManager.getItems().size());
    }

    @Test
    public void getItemsSize_byShowBlogPostCountInCofig() {
        configStorage.get().setShowBlogPostCount(1);

        final DraftBlog blog = new DraftBlog();
        blog.setDisplayPosts(DisplayPosts.DISPLAY_WITHIN_DATE_RANGE);
        blog.setDisplayPostsWithinDateRange(0);
        persistance.putItem(blog);
        final BlogPost blogPost1 = new BlogPost();
        blog.addBlogPost(blogPost1);
        blogPost1.setCreationDate(new Date(System.currentTimeMillis() * 2));
        persistance.putBlogPost(blogPost1);
        final BlogPost blogPost2 = new BlogPost();
        blog.addBlogPost(blogPost2);
        blogPost2.setCreationDate(new Date(System.currentTimeMillis() * 2));
        persistance.putBlogPost(blogPost2);
        final BlogPost blogPost3 = new BlogPost();
        blog.addBlogPost(blogPost3);
        persistance.putBlogPost(blogPost3);

        final BlogManager blogManager = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP);
        final BlogPostsManager blogPostsManager = new BlogPostsManager(blogManager, 0, blogPost1.getBlogPostId());

        Assert.assertEquals(1, blogPostsManager.getItems().size());
    }

    
    @Test
    public void getItemsSize_DISPLAY_WITHIN_DATE_RANGE_minusTwoMonths() {
        configStorage.get().setShowBlogPostCount(100);

        final Date currentDate = new Date();
        final Calendar currentDateMinusTenMonths = new GregorianCalendar();
        currentDateMinusTenMonths.setTime(currentDate);
        currentDateMinusTenMonths.set(Calendar.MONTH, currentDateMinusTenMonths.get(Calendar.MONTH) - 10);


        final DraftBlog blog = new DraftBlog();
        blog.setDisplayPosts(DisplayPosts.DISPLAY_WITHIN_DATE_RANGE);
        blog.setDisplayPostsWithinDateRange(2);
        persistance.putItem(blog);
        final BlogPost blogPost1 = new BlogPost();
        blog.addBlogPost(blogPost1);
        blogPost1.setCreationDate(currentDateMinusTenMonths.getTime());
        persistance.putBlogPost(blogPost1);
        final BlogPost blogPost2 = new BlogPost();
        blog.addBlogPost(blogPost2);
        blogPost2.setCreationDate(new Date(System.currentTimeMillis() * 2));
        persistance.putBlogPost(blogPost2);
        final BlogPost blogPost3 = new BlogPost();
        blogPost3.setCreationDate(new Date());
        blog.addBlogPost(blogPost3);
        persistance.putBlogPost(blogPost3);

        final BlogManager blogManager = new BlogManager(blog.getId(), SiteShowOption.INSIDE_APP);
        final BlogPostsManager blogPostsManager = new BlogPostsManager(blogManager, 0, null);

        Assert.assertEquals(2, blogPostsManager.getItems().size());
        Assert.assertEquals(blogPost2, blogPostsManager.getItems().get(0).getBlogPost());
        Assert.assertEquals(blogPost3, blogPostsManager.getItems().get(1).getBlogPost());
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ConfigStorage configStorage = ServiceLocator.getConfigStorage();

}
