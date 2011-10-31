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
package com.shroggle.presentation.blogSummary;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class BlogSummaryLinksCreatorTest {


    @Test
    public void testCreate_forCurrentPage() {
        User user = TestUtil.createUserAndLogin("a@a.com");

        Site site1 = TestUtil.createSite("site name", "site url");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        //----------------------------------------------------Site1-----------------------------------------------------
        DraftBlog blog = TestUtil.createBlog(site1);
        blog.addBlogPost(TestUtil.createBlogPost("post text"));
        final Page page = TestUtil.createPage(site1);

        final PageManager pageVersion = TestUtil.createPageVersion(page, PageVersionType.WORK);

        final WidgetItem widgetBlog = TestUtil.createWidgetBlog(blog.getId());
        widgetBlog.setCrossWidgetId(1);
        pageVersion.getWorkPageSettings().addWidget(widgetBlog);
        //----------------------------------------------------Site1-----------------------------------------------------

        BlogSummaryDataForPreview dataForPreview = new BlogSummaryDataForPreview(blog.getId(), blog.getName(), widgetBlog);
        dataForPreview.setBlogPosts(ServiceLocator.getPersistance().getBlogPostsByBlog(blog.getId()));
        Assert.assertEquals(0, dataForPreview.getPostUrl().size());


        BlogSummaryLinksCreator.execute(dataForPreview, pageVersion, SiteShowOption.INSIDE_APP);


        Assert.assertEquals(ServiceLocator.getPersistance().getBlogPostsByBlog(blog.getId()).size(), dataForPreview.getPostUrl().size());
        Assert.assertEquals("javascript:showBlogPosts(" + widgetBlog.getWidgetId() + ", " + blog.getId() + ", " +
                "null, " + ServiceLocator.getPersistance().getBlogPostsByBlog(blog.getId()).get(0).getBlogPostId() + ");",
                dataForPreview.getPostUrl().get(ServiceLocator.getPersistance().getBlogPostsByBlog(blog.getId()).get(0).getBlogPostId()));
    }

    @Test
    public void testCreate_forCurrentPage_forPreviewDraftVersionWithChangedEqualFalse() {
        User user = TestUtil.createUserAndLogin("a@a.com");

        Site site1 = TestUtil.createSite("site name", "site url");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        //----------------------------------------------------Site1-----------------------------------------------------
        DraftBlog blog = TestUtil.createBlog(site1);
        blog.addBlogPost(TestUtil.createBlogPost("post text"));
        final Page page = TestUtil.createPage(site1);
        final PageManager pageVersion = TestUtil.createPageVersion(page);


        //----------------------------------------------Draft Page Version----------------------------------------------
        final WidgetItem draftWidgetBlog = TestUtil.createWidgetBlog(blog.getId());
        draftWidgetBlog.setCrossWidgetId(1);
        pageVersion.addWidget(draftWidgetBlog);
        //----------------------------------------------Draft Page Version----------------------------------------------
        //----------------------------------------------------Site1-----------------------------------------------------

        BlogSummaryDataForPreview dataForPreview = new BlogSummaryDataForPreview(blog.getId(), blog.getName(), draftWidgetBlog);
        dataForPreview.setBlogPosts(ServiceLocator.getPersistance().getBlogPostsByBlog(blog.getId()));
        Assert.assertEquals(0, dataForPreview.getPostUrl().size());


        BlogSummaryLinksCreator.execute(dataForPreview, pageVersion, SiteShowOption.INSIDE_APP);


        Assert.assertEquals(ServiceLocator.getPersistance().getBlogPostsByBlog(blog.getId()).size(), dataForPreview.getPostUrl().size());
        Assert.assertEquals("javascript:showBlogPosts(" + draftWidgetBlog.getWidgetId() + ", " + blog.getId() + ", " +
                "null, " + ServiceLocator.getPersistance().getBlogPostsByBlog(blog.getId()).get(0).getBlogPostId() + ");",
                dataForPreview.getPostUrl().get(ServiceLocator.getPersistance().getBlogPostsByBlog(blog.getId()).get(0).getBlogPostId()));
    }

    /*@Test
    public void testCreate_forCurrentPage_forPreviewDraftVersionWithChangedEqualFalse_withoutWidget() {
        User user = TestUtil.createUserAndLogin("a@a.com");

        Site site1 = TestUtil.createSite("site name", "site url");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        //----------------------------------------------------Site1-----------------------------------------------------
        DraftBlog blog = TestUtil.createBlog(site1);
        blog.addBlogPost(TestUtil.createBlogPost("post text"));
        final Page page = TestUtil.createPage(site1);
        final PageManager pageVersion = TestUtil.createPageVersion(page, PageVersionType.WORK);
        //----------------------------------------------Work Page Version-----------------------------------------------


        final WidgetItem workWidgetBlog = TestUtil.createWidgetBlog(blog.getId());
        workWidgetBlog.setCrossWidgetId(1);
        pageVersion.getWorkPageSettings().addWidget(workWidgetBlog);
        //----------------------------------------------Work Page Version-----------------------------------------------

        //----------------------------------------------Draft Page Version----------------------------------------------

        *//*final WidgetItem draftWidgetBlog = TestUtil.createWidgetBlog(blog.getId());
        draftWidgetBlog.setCrossWidgetId(1);
        draftPageVersion.addWidget(draftWidgetBlog);*//*
        //----------------------------------------------Draft Page Version----------------------------------------------
        //----------------------------------------------------Site1-----------------------------------------------------

        BlogSummaryDataForPreview dataForPreview = new BlogSummaryDataForPreview(blog.getId(), blog.getName(), workWidgetBlog);
        dataForPreview.setBlogPosts(ServiceLocator.getPersistance().getBlogPostsByBlog(blog.getId()));
        Assert.assertEquals(0, dataForPreview.getPostUrl().size());


        BlogSummaryLinksCreator.execute(dataForPreview, pageVersion, SiteShowOption.INSIDE_APP);


        Assert.assertEquals(ServiceLocator.getPersistance().getBlogPostsByBlog(blog.getId()).size(), dataForPreview.getPostUrl().size());
        Assert.assertEquals("#", dataForPreview.getPostUrl().get(ServiceLocator.getPersistance().getBlogPostsByBlog(blog.getId()).get(0).getBlogPostId()));
    }*/

    @Test
    public void testCreate_forNotCurrentPageINSIDE_APP() {
        User user = TestUtil.createUserAndLogin("a@a.com");

        Site site1 = TestUtil.createSite("site name", "site url");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        //----------------------------------------------------Site1-----------------------------------------------------
        DraftBlog blog = TestUtil.createBlog(site1);
        blog.addBlogPost(TestUtil.createBlogPost("post text"));
        final Page page = TestUtil.createPage(site1);

        final PageManager pageVersion = TestUtil.createPageVersion(page, PageVersionType.WORK);

        final WidgetItem widgetBlog = TestUtil.createWidgetBlog(blog.getId());
        widgetBlog.setCrossWidgetId(1);
        pageVersion.getWorkPageSettings().addWidget(widgetBlog);
        //----------------------------------------------------Site1-----------------------------------------------------

        BlogSummaryDataForPreview dataForPreview = new BlogSummaryDataForPreview(blog.getId(), blog.getName(), widgetBlog);
        dataForPreview.setBlogPosts(ServiceLocator.getPersistance().getBlogPostsByBlog(blog.getId()));
        Assert.assertEquals(0, dataForPreview.getPostUrl().size());


        Page newPage = TestUtil.createPage(site1);
        PageManager newPageVersion = TestUtil.createPageVersion(newPage);

        BlogSummaryLinksCreator.execute(dataForPreview, newPageVersion, SiteShowOption.INSIDE_APP);


        Assert.assertEquals(ServiceLocator.getPersistance().getBlogPostsByBlog(blog.getId()).size(), dataForPreview.getPostUrl().size());
        Assert.assertEquals("showPageVersion.action?pageId=" + pageVersion.getPageId() +
                "&siteShowOption=ON_USER_PAGES&selectedWidgetId=" + widgetBlog.getWidgetId() + "&blogPostId=" +
                ServiceLocator.getPersistance().getBlogPostsByBlog(blog.getId()).get(0).getBlogPostId() + "#widget1001blogPost1",
                dataForPreview.getPostUrl().get(ServiceLocator.getPersistance().getBlogPostsByBlog(blog.getId()).get(0).getBlogPostId()));
    }

    @Test
    public void testCreate_forNotCurrentPageOUTSIDE_APP() {
        User user = TestUtil.createUserAndLogin("a@a.com");

        Site site1 = TestUtil.createSite("site name", "site url");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        //----------------------------------------------------Site1-----------------------------------------------------
        DraftBlog blog = TestUtil.createBlog(site1);
        blog.addBlogPost(TestUtil.createBlogPost("post text"));
        final Page page = TestUtil.createPage(site1);

        final PageManager pageVersion = TestUtil.createPageVersion(page, PageVersionType.WORK);
        pageVersion.getWorkPageSettings().setUrl("pageVersion");

        final WidgetItem widgetBlog = TestUtil.createWidgetBlog(blog.getId());
        widgetBlog.setCrossWidgetId(1);
        pageVersion.getWorkPageSettings().addWidget(widgetBlog);
        //----------------------------------------------------Site1-----------------------------------------------------

        BlogSummaryDataForPreview dataForPreview = new BlogSummaryDataForPreview(blog.getId(), blog.getName(), widgetBlog);
        dataForPreview.setBlogPosts(ServiceLocator.getPersistance().getBlogPostsByBlog(blog.getId()));
        Assert.assertEquals(0, dataForPreview.getPostUrl().size());

        Page newPage = TestUtil.createPage(site1);
        PageManager newPageVersion = TestUtil.createPageVersion(newPage);

        BlogSummaryLinksCreator.execute(dataForPreview, newPageVersion, SiteShowOption.OUTSIDE_APP);


        Assert.assertEquals(ServiceLocator.getPersistance().getBlogPostsByBlog(blog.getId()).size(), dataForPreview.getPostUrl().size());
        Assert.assertEquals("/pageVersion?selectedWidgetId=" + widgetBlog.getWidgetId() + "&blogPostId=" +
                ServiceLocator.getPersistance().getBlogPostsByBlog(blog.getId()).get(0).getBlogPostId() + "#widget1001blogPost1",
                dataForPreview.getPostUrl().get(ServiceLocator.getPersistance().getBlogPostsByBlog(blog.getId()).get(0).getBlogPostId()));
    }
}
