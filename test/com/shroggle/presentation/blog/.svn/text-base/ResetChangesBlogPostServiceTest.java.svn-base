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
import com.shroggle.entity.DraftBlog;
import com.shroggle.entity.BlogPost;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(TestRunnerWithMockServices.class)
public class ResetChangesBlogPostServiceTest {

    @Test
    public void execute() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setText("11");
        blogPost.setDraftText("22");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        service.execute(blogPost.getBlogPostId());
        Assert.assertEquals("11", blogPost.getText());
        Assert.assertNull(blogPost.getDraftText());
    }

    @Test
    public void executeWithNullText() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setDraftText("22");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        service.execute(blogPost.getBlogPostId());
        Assert.assertNull(persistance.getBlogPostById(blogPost.getBlogPostId()));
    }

    @Test
    public void executeWithNullDraftText() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setText("11");
        blogPost.setDraftText(null);
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        service.execute(blogPost.getBlogPostId());
        Assert.assertEquals("11", blogPost.getText());
        Assert.assertNull(blogPost.getDraftText());
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ResetChangesBlogPostService service = new ResetChangesBlogPostService();

}