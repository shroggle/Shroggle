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

import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class HibernateBlogPersistanceTest extends HibernatePersistanceTestBase {

    @Test
    public void putSiteItem() {
        DraftBlog blog = new DraftBlog();
        blog.setName("aa");
        persistance.putItem(blog);

        DraftBlog findBlog = persistance.getDraftItem(blog.getId());
        Assert.assertEquals(blog, findBlog);
    }

    @Test
    public void putSiteOnBlogRight() {
        DraftBlog blog = new DraftBlog();
        blog.setName("aa");
        persistance.putItem(blog);

        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.getSitePaymentSettings().setUserId(-1);

        site.setTitle("f");
        site.setSubDomain("g");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        persistance.putSite(site);

        SiteOnItem siteOnBlogRight = new SiteOnItem();
        siteOnBlogRight.getId().setItem(blog);
        siteOnBlogRight.getId().setSite(site);
        persistance.putSiteOnItem(siteOnBlogRight);
    }


    @Test
    public void getBlogByNameAndSiteId() {
        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.getSitePaymentSettings().setUserId(-1);
        site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("f");
        site.setSubDomain("g");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        persistance.putSite(site);

        DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        blog.setName("aa");
        persistance.putItem(blog);

        Assert.assertEquals(blog, persistance.getBlogByNameAndSiteId("aa", site.getSiteId()));
    }

    @Test
    public void getBlogByNameAndSiteIdName() {
        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("f");
        site.setSubDomain("g");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        persistance.putSite(site);

        DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        blog.setName("aa1");
        persistance.putItem(blog);

        Assert.assertNull(persistance.getBlogByNameAndSiteId("aa", site.getSiteId()));
    }

    @Test
    public void getBlogByNameAndSiteIdWithNotSite() {
        Site site1 = new Site();site1.getSitePaymentSettings().setUserId(-1);
        site1.setTitle("f");
        site1.setSubDomain("g");
        site1.getThemeId().setTemplateDirectory("f");
        site1.getThemeId().setThemeCss("f");
        persistance.putSite(site1);

        Site site2 = new Site();site2.getSitePaymentSettings().setUserId(-1);
        site2.setTitle("f");
        site2.setSubDomain("2g");
        site2.getThemeId().setTemplateDirectory("f");
        site2.getThemeId().setThemeCss("f");
        persistance.putSite(site2);

        DraftBlog blog = new DraftBlog();
        blog.setSiteId(site2.getSiteId());
        blog.setName("aa");
        persistance.putItem(blog);

        Assert.assertNull(persistance.getBlogByNameAndSiteId("aa", site1.getSiteId()));
    }

    @Test
    public void getBlogByNameAndSiteIdWithoutBlog() {
        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("f");
        site.setSubDomain("g");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        persistance.putSite(site);

        Assert.assertNull(persistance.getBlogByNameAndSiteId("aa", site.getSiteId()));
    }

    @Test
    public void removeBlog() {
        DraftBlog blog = new DraftBlog();
        blog.setName("aa");
        persistance.putItem(blog);
        persistance.removeDraftItem(blog);

        Assert.assertNull(persistance.getDraftItem(blog.getId()));
    }

    @Test
    public void removeBlogFromBlogWidget() {
        DraftBlog blog = new DraftBlog();
        blog.setName("aa");
        persistance.putItem(blog);

        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        site.setSubDomain("f");
        persistance.putSite(site);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);

        WidgetItem widgetBlog = new WidgetItem();
        widgetBlog.setDraftItem(blog);
        persistance.putWidget(widgetBlog);
        pageVersion.addWidget(widgetBlog);

        persistance.removeDraftItem(blog);

        Assert.assertNull(persistance.getDraftItem(blog.getId()));
        Assert.assertNotNull(widgetBlog.getDraftItem());
    }

    @Test
    public void putBlogSummary() {
        DraftBlogSummary blogSummary = new DraftBlogSummary();
        blogSummary.setName("aaa");

        persistance.putItem(blogSummary);

        DraftBlogSummary findBlogSummary = persistance.getDraftItem(blogSummary.getId());
        Assert.assertEquals(blogSummary, findBlogSummary);
    }

    @Test
    public void getBlogSummaryByNullBlogSummaryId() {
        Assert.assertNull(persistance.getDraftItem(null));
    }

    @Test
    public void getBlogSummariesByUserId() {
        final User user = new User();
        user.setEmail("a1@a");
        persistance.putUser(user);

        final Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("a");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("1");
        site.setSubDomain("f");
        persistance.putSite(site);

        final UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        site.addUserOnSiteRight(userOnUserRight);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        userOnUserRight.setActive(true);
        persistance.putUserOnSiteRight(userOnUserRight);

        final DraftBlogSummary blogSummary = new DraftBlogSummary();
        blogSummary.setSiteId(site.getSiteId());
        blogSummary.setName("aaa");
        persistance.putItem(blogSummary);

        final List<DraftItem> blogSummaries =
                persistance.getDraftItemsByUserId(user.getUserId(), ItemType.BLOG_SUMMARY);

        Assert.assertNotNull(blogSummaries);
        Assert.assertEquals(1, blogSummaries.size());
        Assert.assertEquals(blogSummary, blogSummaries.get(0));
    }

    @Test
    public void getBlogSummariesByUserIdWithoutActive() {
        final User user = new User();
        user.setEmail("a1@a");
        persistance.putUser(user);

        final Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("a");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("1");
        site.setSubDomain("f");
        persistance.putSite(site);

        final UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        site.addUserOnSiteRight(userOnUserRight);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        userOnUserRight.setActive(false);
        persistance.putUserOnSiteRight(userOnUserRight);

        final DraftBlogSummary blogSummary = new DraftBlogSummary();
        blogSummary.setSiteId(site.getSiteId());
        blogSummary.setName("aaa");
        persistance.putItem(blogSummary);

        final List<DraftItem> blogSummaries =
                persistance.getDraftItemsByUserId(user.getUserId(), ItemType.BLOG_SUMMARY);

        Assert.assertNotNull(blogSummaries);
        Assert.assertEquals(0, blogSummaries.size());
    }

    @Test
    public void putBlogPost() {
        DraftBlog blog = new DraftBlog();
        blog.setName("aaa");
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setText("aaa");
        blogPost.setBlog(blog);
        persistance.putBlogPost(blogPost);

        blog.addBlogPost(blogPost);

        DraftBlog findBlog = persistance.getDraftItem(blog.getId());
        BlogPost findBlogPost = persistance.getBlogPostById(blogPost.getBlogPostId());
        Assert.assertEquals(blog, findBlog);
        Assert.assertEquals(1, ServiceLocator.getPersistance().getBlogPostsByBlog(findBlog.getId()).size());
        Assert.assertEquals(blogPost, ServiceLocator.getPersistance().getBlogPostsByBlog(findBlog.getId()).get(0));
        Assert.assertEquals(findBlogPost, ServiceLocator.getPersistance().getBlogPostsByBlog(findBlog.getId()).get(0));
    }

    @Test
    public void getBlogPostsByBlogAndVisitor() {
        DraftBlog blog = new DraftBlog();
        blog.setName("aaa");
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setText("aaa");
        blogPost.setBlog(blog);
        persistance.putBlogPost(blogPost);

        blog.addBlogPost(blogPost);

        List<BlogPost> blogPosts = persistance.getBlogPosts(
                blog.getId(), null, null, 0, -1, null);
        Assert.assertEquals(1, blogPosts.size());
        Assert.assertEquals(blogPost, blogPosts.get(0));
    }

    @Test
    public void isBlogPostsAfterByBlogAndVisitorForOne() {
        DraftBlog blog = new DraftBlog();
        blog.setName("aaa");
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setText("aaa");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);


        Assert.assertEquals(0, persistance.getBlogPostsAfterByBlogAndUserId(
                blog.getId(), null, blogPost.getBlogPostId()));
    }

    @Test
    public void isBlogPostsAfterByBlogAndVisitor() {
        DraftBlog blog = new DraftBlog();
        blog.setName("aaa");
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setText("aaa");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        BlogPost afterBlogPost = new BlogPost();
        afterBlogPost.setText("aaa");
        blog.addBlogPost(afterBlogPost);
        afterBlogPost.setCreationDate(new Date(System.currentTimeMillis() * 2));
        persistance.putBlogPost(afterBlogPost);

        Assert.assertEquals(1, persistance.getBlogPostsAfterByBlogAndUserId(
                blog.getId(), null, blogPost.getBlogPostId()));
    }

    @Test
    public void isBlogPostsAfterByBlogAndVisitorForAnonymWithDraft() {
        DraftBlog blog = new DraftBlog();
        blog.setName("aaa");
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setText("aaa");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        BlogPost afterBlogPost = new BlogPost();
        afterBlogPost.setDraftText("aaa");
        blog.addBlogPost(afterBlogPost);
        afterBlogPost.setCreationDate(new Date(System.currentTimeMillis() * 2));
        persistance.putBlogPost(afterBlogPost);

        Assert.assertEquals(0, persistance.getBlogPostsAfterByBlogAndUserId(
                blog.getId(), Integer.MIN_VALUE, blogPost.getBlogPostId()));
    }

    @Test
    public void isBlogPostsAfterByBlogAndVisitorForAnonymWithDraftAndText() {
        DraftBlog blog = new DraftBlog();
        blog.setName("aaa");
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setText("aaa");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        BlogPost afterBlogPost = new BlogPost();
        afterBlogPost.setDraftText("aaa");
        afterBlogPost.setText("2aaa");
        blog.addBlogPost(afterBlogPost);
        afterBlogPost.setCreationDate(new Date(System.currentTimeMillis() * 2));
        persistance.putBlogPost(afterBlogPost);

        Assert.assertEquals(1, persistance.getBlogPostsAfterByBlogAndUserId(
                blog.getId(), Integer.MIN_VALUE, blogPost.getBlogPostId()));
    }

    @Test
    public void isBlogPostsAfterByBlogAndVisitorForAuthor() {
        DraftBlog blog = new DraftBlog();
        blog.setName("aaa");
        persistance.putItem(blog);

        User user = new User();
        user.setEmail("a");
        persistance.putUser(user);

        BlogPost blogPost = new BlogPost();
        blogPost.setText("aaa");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        BlogPost afterBlogPost = new BlogPost();
        afterBlogPost.setDraftText("aaa");
        afterBlogPost.setVisitorId(user.getUserId());
        blog.addBlogPost(afterBlogPost);
        afterBlogPost.setCreationDate(new Date(System.currentTimeMillis() * 2));
        persistance.putBlogPost(afterBlogPost);

        Assert.assertEquals(1, persistance.getBlogPostsAfterByBlogAndUserId(
                blog.getId(), user.getUserId(), blogPost.getBlogPostId()));
    }

    @Test
    public void isBlogPostsAfterByBlogAndVisitorForNoHisAuthor() {
        DraftBlog blog = new DraftBlog();
        blog.setName("aaa");
        persistance.putItem(blog);

        User user = new User();
        user.setEmail("a");
        persistance.putUser(user);

        BlogPost blogPost = new BlogPost();
        blogPost.setText("aaa");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        BlogPost afterBlogPost = new BlogPost();
        afterBlogPost.setDraftText("aaa");
        afterBlogPost.setVisitorId(user.getUserId());
        blog.addBlogPost(afterBlogPost);
        afterBlogPost.setCreationDate(new Date(System.currentTimeMillis() * 2));
        persistance.putBlogPost(afterBlogPost);

        Assert.assertEquals(0, persistance.getBlogPostsAfterByBlogAndUserId(
                blog.getId(), Integer.MAX_VALUE, blogPost.getBlogPostId()));
    }

    @Test
    public void isBlogPostsAfterByBlogAndVisitorForOneAndAnonym() {
        DraftBlog blog = new DraftBlog();
        blog.setName("aaa");
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setText("aaa");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        Assert.assertEquals(0, persistance.getBlogPostsAfterByBlogAndUserId(
                blog.getId(), Integer.MIN_VALUE, blogPost.getBlogPostId()));
    }

    @Test
    public void isBlogPostsBeforeByBlogAndVisitorForOneAndAnonym() {
        DraftBlog blog = new DraftBlog();
        blog.setName("aaa");
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setText("aaa");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        Assert.assertEquals(0, persistance.getBlogPostsBeforeByBlogAndUserId(
                blog.getId(), Integer.MIN_VALUE, blogPost.getBlogPostId()));
    }

    @Test
    public void isBlogPostsBeforeByBlogAndVisitor() {
        DraftBlog blog = new DraftBlog();
        blog.setName("aaa");
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setText("aaa");
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        BlogPost afterBlogPost = new BlogPost();
        afterBlogPost.setText("aaa");
        blog.addBlogPost(afterBlogPost);
        afterBlogPost.setCreationDate(new Date(System.currentTimeMillis() / 2));
        persistance.putBlogPost(afterBlogPost);

        Assert.assertEquals(1, persistance.getBlogPostsBeforeByBlogAndUserId(
                blog.getId(), null, blogPost.getBlogPostId()));
    }

    @Test
    public void isBlogPostsBeforeByBlogAndVisitorForOne() {
        DraftBlog blog = new DraftBlog();
        blog.setName("aaa");
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setText("aaa");
        blogPost.setBlog(blog);
        persistance.putBlogPost(blogPost);

        blog.addBlogPost(blogPost);

        Assert.assertEquals(0, persistance.getBlogPostsBeforeByBlogAndUserId(
                blog.getId(), null, blogPost.getBlogPostId()));
    }

    @Test
    public void getBlogPostsByBlogAndVisitorStartBlogPost() {
        DraftBlog blog = new DraftBlog();
        blog.setName("aaa");
        persistance.putItem(blog);

        BlogPost blogPost1 = new BlogPost();
        blogPost1.setText("aaa");
        blogPost1.setBlog(blog);
        blogPost1.setCreationDate(new Date());
        persistance.putBlogPost(blogPost1);

        BlogPost blogPost2 = new BlogPost();
        blogPost2.setText("aaa");
        blogPost2.setBlog(blog);
        blogPost2.setCreationDate(new Date(System.currentTimeMillis() * 2));
        persistance.putBlogPost(blogPost2);

        blog.addBlogPost(blogPost1);

        List<BlogPost> blogPosts = persistance.getBlogPosts(
                blog.getId(), null, blogPost2.getBlogPostId(), 0, -1, null);
        Assert.assertEquals(2, blogPosts.size());
        Assert.assertEquals(blogPost2, blogPosts.get(0));
    }


    @Test
    public void getBlogPosts_withNotOlderThanDate() {
        DraftBlog blog = new DraftBlog();
        blog.setName("aaa");
        persistance.putItem(blog);

        final Date notOlderThan = new Date(System.currentTimeMillis() / 2);

        BlogPost blogPost1 = new BlogPost();
        blogPost1.setText("aaa");
        blogPost1.setBlog(blog);
        blogPost1.setCreationDate(new Date(notOlderThan.getTime() / 2));
        persistance.putBlogPost(blogPost1);

        BlogPost blogPost2 = new BlogPost();
        blogPost2.setText("aaa");
        blogPost2.setBlog(blog);
        blogPost2.setCreationDate(new Date(notOlderThan.getTime() * 2));
        persistance.putBlogPost(blogPost2);

        blog.addBlogPost(blogPost1);

        List<BlogPost> blogPosts = persistance.getBlogPosts(
                blog.getId(), null, blogPost2.getBlogPostId(), 0, -1, notOlderThan);
        Assert.assertEquals(1, blogPosts.size());
        Assert.assertEquals(blogPost2, blogPosts.get(0));
    }

    @Test
    public void getBlogPostsByBlogAndVisitorStartBlogPostWithCount() {
        DraftBlog blog = new DraftBlog();
        blog.setName("aaa");
        persistance.putItem(blog);

        BlogPost blogPost2 = new BlogPost();
        blogPost2.setText("aaa");
        blogPost2.setBlog(blog);
        persistance.putBlogPost(blogPost2);

        BlogPost blogPost1 = new BlogPost();
        blogPost1.setText("aaa");
        blogPost1.setBlog(blog);
        persistance.putBlogPost(blogPost1);

        blog.addBlogPost(blogPost1);

        List<BlogPost> blogPosts = persistance.getBlogPosts(
                blog.getId(), null, blogPost2.getBlogPostId(), 0, 1, null);
        Assert.assertEquals(1, blogPosts.size());
        Assert.assertEquals(blogPost2, blogPosts.get(0));
    }

    @Test
    public void getBlogPostsByBlogAndVisitorStartBlogPostWithCountMany() {
        DraftBlog blog = new DraftBlog();
        blog.setName("aaa");
        persistance.putItem(blog);

        BlogPost blogPost1 = new BlogPost();
        blogPost1.setText("aaa");
        blogPost1.setBlog(blog);
        blogPost1.setCreationDate(new Date());
        persistance.putBlogPost(blogPost1);

        BlogPost blogPost2 = new BlogPost();
        blogPost2.setText("aaa");
        blogPost2.setBlog(blog);
        blogPost2.setCreationDate(new Date(System.currentTimeMillis() * 2));
        persistance.putBlogPost(blogPost2);

        blog.addBlogPost(blogPost1);

        List<BlogPost> blogPosts = persistance.getBlogPosts(
                blog.getId(), null, blogPost2.getBlogPostId(), 0, 2, null);
        Assert.assertEquals(2, blogPosts.size());
        Assert.assertEquals(blogPost2, blogPosts.get(0));
        Assert.assertEquals(blogPost1, blogPosts.get(1));
    }

    @Test
    public void getBlogPostsByBlogAndVisitorByVisitorNotMyDraft() {
        DraftBlog blog = new DraftBlog();
        blog.setName("aaa");
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setDraftText("aaa");
        blogPost.setBlog(blog);
        persistance.putBlogPost(blogPost);

        blog.addBlogPost(blogPost);

        List<BlogPost> blogPosts = persistance.getBlogPosts(
                blog.getId(), Integer.MIN_VALUE, null, 0, -1, null);
        Assert.assertEquals(0, blogPosts.size());
    }

    @Test
    public void getBlogPostsByBlogAndVisitorByVisitorStartBlogPostNotMyDraft() {
        DraftBlog blog = new DraftBlog();
        blog.setName("aaa");
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setDraftText("aaa");
        blogPost.setBlog(blog);
        persistance.putBlogPost(blogPost);

        blog.addBlogPost(blogPost);

        List<BlogPost> blogPosts = persistance.getBlogPosts(
                blog.getId(), Integer.MIN_VALUE, blogPost.getBlogPostId(), 0, -1, null);
        Assert.assertEquals(0, blogPosts.size());
    }

    @Test
    public void getBlogPostsByBlogAndVisitorByVisitorStartBlogPost() {
        DraftBlog blog = new DraftBlog();
        blog.setName("aaa");
        persistance.putItem(blog);

        User user = new User();
        user.setEmail("f#f");
        persistance.putUser(user);

        BlogPost blogPost2 = new BlogPost();
        blogPost2.setBlog(blog);
        blogPost2.setVisitorId(user.getUserId());
        blogPost2.setDraftText("aaa");
        persistance.putBlogPost(blogPost2);

        BlogPost blogPost1 = new BlogPost();
        blogPost1.setBlog(blog);
        blogPost1.setVisitorId(user.getUserId());
        blogPost1.setDraftText("aaa");
        persistance.putBlogPost(blogPost1);


        blog.addBlogPost(blogPost1);
        blog.addBlogPost(blogPost2);

        List<BlogPost> blogPosts = persistance.getBlogPosts(
                blog.getId(), user.getUserId(), blogPost2.getBlogPostId(), 0, 1, null);
        Assert.assertEquals(1, blogPosts.size());
        Assert.assertEquals(blogPost2, blogPosts.get(0));
    }

    @Test
    public void getBlogPostsByBlogAndVisitorByVisitorStartBlogPostMany() {
        DraftBlog blog = new DraftBlog();
        blog.setName("aaa");
        persistance.putItem(blog);

        User user = new User();
        user.setEmail("f#f");
        persistance.putUser(user);

        BlogPost blogPost1 = new BlogPost();
        blogPost1.setBlog(blog);
        blogPost1.setVisitorId(user.getUserId());
        blogPost1.setDraftText("aaa");
        blogPost1.setCreationDate(new Date());
        persistance.putBlogPost(blogPost1);

        BlogPost blogPost2 = new BlogPost();
        blogPost2.setBlog(blog);
        blogPost2.setVisitorId(user.getUserId());
        blogPost2.setDraftText("aaa");
        blogPost2.setCreationDate(new Date(System.currentTimeMillis() * 2));
        persistance.putBlogPost(blogPost2);

        blog.addBlogPost(blogPost1);
        blog.addBlogPost(blogPost2);

        List<BlogPost> blogPosts = persistance.getBlogPosts(
                blog.getId(), user.getUserId(), blogPost2.getBlogPostId(), 0, -1, null);
        Assert.assertEquals(2, blogPosts.size());
        Assert.assertEquals(blogPost2, blogPosts.get(0));
        Assert.assertEquals(blogPost1, blogPosts.get(1));
    }

    @Test
    public void getBlogPostsByBlogAndVisitorByVisitorMyDraft() {
        DraftBlog blog = new DraftBlog();
        blog.setName("aaa");
        persistance.putItem(blog);

        User user = new User();
        user.setEmail("f#f");
        persistance.putUser(user);

        BlogPost blogPost = new BlogPost();
        blogPost.setDraftText("aaa");
        blogPost.setBlog(blog);
        blogPost.setVisitorId(user.getUserId());
        persistance.putBlogPost(blogPost);

        blog.addBlogPost(blogPost);

        List<BlogPost> blogPosts = persistance.getBlogPosts(
                blog.getId(), user.getUserId(), null, 0, -1, null);
        Assert.assertEquals(1, blogPosts.size());
        Assert.assertEquals(blogPost, blogPosts.get(0));
    }

    @Test
    public void getBlogPostsByBlogAndVisitorByVisitorMany() {
        DraftBlog blog = new DraftBlog();
        blog.setName("aaa");
        persistance.putItem(blog);

        User user = new User();
        user.setEmail("f#f");
        persistance.putUser(user);

        BlogPost blogPost1 = new BlogPost();
        blogPost1.setDraftText("aaa");
        blogPost1.setBlog(blog);
        blogPost1.setVisitorId(user.getUserId());
        persistance.putBlogPost(blogPost1);

        BlogPost blogPost2 = new BlogPost();
        blogPost2.setDraftText("aaa");
        blogPost2.setBlog(blog);
        blogPost2.setCreationDate(new Date(System.currentTimeMillis() * 2));
        blogPost2.setVisitorId(user.getUserId());
        persistance.putBlogPost(blogPost2);

        blog.addBlogPost(blogPost2);

        List<BlogPost> blogPosts = persistance.getBlogPosts(
                blog.getId(), user.getUserId(), null, 0, -1, null);
        Assert.assertEquals(2, blogPosts.size());
        Assert.assertEquals(blogPost2, blogPosts.get(0));
        Assert.assertEquals(blogPost1, blogPosts.get(1));
    }

    @Test
    public void getBlogPostsByBlogAndVisitorByVisitorNotMy() {
        DraftBlog blog = new DraftBlog();
        blog.setName("aaa");
        persistance.putItem(blog);

        User user = new User();
        user.setEmail("f#f");
        persistance.putUser(user);

        BlogPost blogPost = new BlogPost();
        blogPost.setText("aaa");
        blogPost.setBlog(blog);
        persistance.putBlogPost(blogPost);

        blog.addBlogPost(blogPost);

        List<BlogPost> blogPosts = persistance.getBlogPosts(
                blog.getId(), user.getUserId(), null, 0, -1, null);
        Assert.assertEquals(1, blogPosts.size());
        Assert.assertEquals(blogPost, blogPosts.get(0));
    }

    @Test
    public void getBlogPostsByBlogAndVisitorByVisitorNotMyWorkAndDraft() {
        DraftBlog blog = new DraftBlog();
        blog.setName("aaa");
        persistance.putItem(blog);

        User user = new User();
        user.setEmail("f#f");
        persistance.putUser(user);

        BlogPost blogPost = new BlogPost();
        blogPost.setText("aaa");
        blogPost.setDraftText("1aaa");
        blogPost.setBlog(blog);
        persistance.putBlogPost(blogPost);

        blog.addBlogPost(blogPost);

        List<BlogPost> blogPosts = persistance.getBlogPosts(
                blog.getId(), user.getUserId(), null, 0, -1, null);
        Assert.assertEquals(1, blogPosts.size());
        Assert.assertEquals(blogPost, blogPosts.get(0));
    }

    @Test
    public void getBlogPostsByBlogAndVisitorPart() {
        DraftBlog blog = new DraftBlog();
        blog.setName("aaa");
        persistance.putItem(blog);

        BlogPost blogPost1 = new BlogPost();
        blogPost1.setText("aaa");
        blogPost1.setBlog(blog);
        persistance.putBlogPost(blogPost1);
        blog.addBlogPost(blogPost1);

        BlogPost blogPost2 = new BlogPost();
        blogPost2.setCreationDate(new Date(System.currentTimeMillis() / 2));
        blogPost2.setText("aaad");
        blogPost2.setBlog(blog);
        persistance.putBlogPost(blogPost2);
        blog.addBlogPost(blogPost2);

        List<BlogPost> blogPosts = persistance.getBlogPosts(
                blog.getId(), null, null, 1, 2, null);
        Assert.assertEquals(1, blogPosts.size());
        Assert.assertEquals(blogPost2, blogPosts.get(0));
    }

    @Test
    public void getBlogPostsByBlogAndVisitorMany() {
        DraftBlog blog = new DraftBlog();
        blog.setName("aaa");
        persistance.putItem(blog);

        BlogPost blogPost1 = new BlogPost();
        blogPost1.setText("aaa");
        blogPost1.setBlog(blog);
        persistance.putBlogPost(blogPost1);
        blog.addBlogPost(blogPost1);

        BlogPost blogPost2 = new BlogPost();
        blogPost2.setCreationDate(new Date(System.currentTimeMillis() / 2));
        blogPost2.setText("aaad");
        blogPost2.setBlog(blog);
        persistance.putBlogPost(blogPost2);
        blog.addBlogPost(blogPost2);

        List<BlogPost> blogPosts = persistance.getBlogPosts(
                blog.getId(), null, null, 0, -1, null);
        Assert.assertEquals(2, blogPosts.size());
        Assert.assertEquals(blogPost2, blogPosts.get(1));
        Assert.assertEquals(blogPost1, blogPosts.get(0));
    }

    @Test
    public void getBlogsByUser() {
        User account = new User();
        account.setEmail("a1@a");
        account.setRegistrationDate(new Date());
        persistance.putUser(account);
    }

    @Test
    public void putComment() {
        DraftBlog blog = new DraftBlog();
        blog.setName("aaa");
        persistance.putItem(blog);

        BlogPost blogPost = new BlogPost();
        blogPost.setText("aaa");
        blogPost.setBlog(blog);
        persistance.putBlogPost(blogPost);

        blog.addBlogPost(blogPost);

        Comment comment = new Comment();
        comment.setCreated(new Date());
        comment.setText("fff");
        blogPost.addComment(comment);
        persistance.putComment(comment);

        Comment findComment = persistance.getCommentById(comment.getCommentId());
        Assert.assertEquals(comment, findComment);
    }

}