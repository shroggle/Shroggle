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
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class BlogSummaryDataForPreviewCreatorTest {

    @Test
    public void testCreate_MOST_COMMENTED() throws Exception {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);

        final DraftBlog blog1 = TestUtil.createBlog(site);
        final Widget widget1 = TestUtil.createWidgetBlog(blog1.getId());

        final DraftBlog blog2 = TestUtil.createBlog(site);
        final Widget widget2 = TestUtil.createWidgetBlog(blog2.getId());

        final DraftBlog blog3 = TestUtil.createBlog(site);
        final Widget widget3 = TestUtil.createWidgetBlog(blog3.getId());

        pageVersion.addWidget(widget1);
        pageVersion.addWidget(widget2);
        pageVersion.addWidget(widget3);


        /*-----------------------------------------------Creating posts-----------------------------------------------*/
        final BlogPost blogPost1 = TestUtil.createBlogPost("text1", 1);
        blog1.addBlogPost(blogPost1);

        final BlogPost blogPost2 = TestUtil.createBlogPost("text2", 2);
        blog2.addBlogPost(blogPost2);

        final BlogPost blogPost3 = TestUtil.createBlogPost("text3", 3);
        blog3.addBlogPost(blogPost3);
        /*-----------------------------------------------Creating posts-----------------------------------------------*/


        final DraftBlogSummary blogSummary = TestUtil.createBlogSummary(site);
        blogSummary.setIncludedCrossWidgetId(Arrays.asList(widget1.getCrossWidgetId(), widget2.getCrossWidgetId(), widget3.getCrossWidgetId()));
        blogSummary.setIncludedPostNumber(1);
        blogSummary.setPostDisplayCriteria(PostDisplayCriteria.MOST_COMMENTED);


        final List<BlogSummaryDataForPreview> blogSummaryDataForPreview = BlogSummaryDataForPreviewCreator.create(blogSummary, pageVersion, SiteShowOption.getDraftOption());


        Assert.assertEquals(1, blogSummaryDataForPreview.size());
        Assert.assertEquals(blog3.getId(), blogSummaryDataForPreview.get(0).getBlogId());
        Assert.assertEquals(blog3.getName(), blogSummaryDataForPreview.get(0).getBlogName());
        Assert.assertEquals(widget3, blogSummaryDataForPreview.get(0).getWidget());
        Assert.assertEquals(1, blogSummaryDataForPreview.get(0).getBlogPosts().size());
        Assert.assertEquals(blogPost3, blogSummaryDataForPreview.get(0).getBlogPosts().get(0));
    }

    @Test
    public void testCreate_MOST_READ() throws Exception {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);

        final DraftBlog blog1 = TestUtil.createBlog(site);
        final Widget widget1 = TestUtil.createWidgetBlog(blog1.getId());

        final DraftBlog blog2 = TestUtil.createBlog(site);
        final Widget widget2 = TestUtil.createWidgetBlog(blog2.getId());

        final DraftBlog blog3 = TestUtil.createBlog(site);
        final Widget widget3 = TestUtil.createWidgetBlog(blog3.getId());

        pageVersion.addWidget(widget1);
        pageVersion.addWidget(widget2);
        pageVersion.addWidget(widget3);


        /*-----------------------------------------------Creating posts-----------------------------------------------*/
        final BlogPost blogPost1 = TestUtil.createBlogPost("text1");
        blogPost1.setPostRead(1);
        blog1.addBlogPost(blogPost1);

        final BlogPost blogPost2 = TestUtil.createBlogPost("text2");
        blogPost2.setPostRead(2);
        blog2.addBlogPost(blogPost2);

        final BlogPost blogPost3 = TestUtil.createBlogPost("text3");
        blogPost3.setPostRead(3);
        blog3.addBlogPost(blogPost3);
        /*-----------------------------------------------Creating posts-----------------------------------------------*/


        final DraftBlogSummary blogSummary = TestUtil.createBlogSummary(site);
        blogSummary.setIncludedCrossWidgetId(Arrays.asList(widget1.getCrossWidgetId(), widget2.getCrossWidgetId(), widget3.getCrossWidgetId()));
        blogSummary.setIncludedPostNumber(1);
        blogSummary.setPostDisplayCriteria(PostDisplayCriteria.MOST_READ);


        final List<BlogSummaryDataForPreview> blogSummaryDataForPreview = BlogSummaryDataForPreviewCreator.create(blogSummary, pageVersion, SiteShowOption.getDraftOption());


        Assert.assertEquals(1, blogSummaryDataForPreview.size());
        Assert.assertEquals(blog3.getId(), blogSummaryDataForPreview.get(0).getBlogId());
        Assert.assertEquals(blog3.getName(), blogSummaryDataForPreview.get(0).getBlogName());
        Assert.assertEquals(widget3, blogSummaryDataForPreview.get(0).getWidget());
        Assert.assertEquals(1, blogSummaryDataForPreview.get(0).getBlogPosts().size());
        Assert.assertEquals(blogPost3, blogSummaryDataForPreview.get(0).getBlogPosts().get(0));
    }

    @Test
    public void testCreate_MOST_RECENT() throws Exception {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);

        final DraftBlog blog1 = TestUtil.createBlog(site);
        final Widget widget1 = TestUtil.createWidgetBlog(blog1.getId());

        final DraftBlog blog2 = TestUtil.createBlog(site);
        final Widget widget2 = TestUtil.createWidgetBlog(blog2.getId());

        final DraftBlog blog3 = TestUtil.createBlog(site);
        final Widget widget3 = TestUtil.createWidgetBlog(blog3.getId());

        pageVersion.addWidget(widget1);
        pageVersion.addWidget(widget2);
        pageVersion.addWidget(widget3);


        /*-----------------------------------------------Creating posts-----------------------------------------------*/
        final int initialTime = 100000;
        final BlogPost blogPost1 = TestUtil.createBlogPost("text1", 1);
        blogPost1.setCreationDate(new Date(initialTime));
        blog1.addBlogPost(blogPost1);

        final BlogPost blogPost2 = TestUtil.createBlogPost("text2", 2);
        blogPost2.setCreationDate(new Date(initialTime * 2));
        blog2.addBlogPost(blogPost2);

        final BlogPost blogPost3 = TestUtil.createBlogPost("text3", 3);
        blogPost3.setCreationDate(new Date(initialTime * 3));
        blog3.addBlogPost(blogPost3);
        /*-----------------------------------------------Creating posts-----------------------------------------------*/


        final DraftBlogSummary blogSummary = TestUtil.createBlogSummary(site);
        blogSummary.setIncludedCrossWidgetId(Arrays.asList(widget1.getCrossWidgetId(), widget2.getCrossWidgetId(), widget3.getCrossWidgetId()));
        blogSummary.setIncludedPostNumber(1);
        blogSummary.setPostDisplayCriteria(PostDisplayCriteria.MOST_RECENT);


        final List<BlogSummaryDataForPreview> blogSummaryDataForPreview = BlogSummaryDataForPreviewCreator.create(blogSummary, pageVersion, SiteShowOption.getDraftOption());


        Assert.assertEquals(1, blogSummaryDataForPreview.size());
        Assert.assertEquals(blog3.getId(), blogSummaryDataForPreview.get(0).getBlogId());
        Assert.assertEquals(blog3.getName(), blogSummaryDataForPreview.get(0).getBlogName());
        Assert.assertEquals(widget3, blogSummaryDataForPreview.get(0).getWidget());
        Assert.assertEquals(1, blogSummaryDataForPreview.get(0).getBlogPosts().size());
        Assert.assertEquals(blogPost3, blogSummaryDataForPreview.get(0).getBlogPosts().get(0));
    }

    @Test
    public void createByCrossWidgetsIds() {
        User user = TestUtil.createUserAndLogin("a@a.com");

        Site site1 = TestUtil.createSite("site name", "site url");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);


        //----------------------------------------------------Site1-----------------------------------------------------
        DraftBlog blogForSite1 = TestUtil.createBlog(site1);
        for (int i = 0; i < 10; i++) {
            blogForSite1.addBlogPost(TestUtil.createBlogPost("post text " + i));
        }
        for (int i = 0; i < 10; i++) {
            blogForSite1.addBlogPost(TestUtil.createBlogPost(null));
        }

        final Page pageForSite1 = TestUtil.createPage(site1);

        final PageManager pageVersionForSite1 = TestUtil.createPageVersion(pageForSite1, PageVersionType.WORK);

        final WidgetItem widgetBlogWorkForSite1 = TestUtil.createWidgetBlog(blogForSite1.getId());
        widgetBlogWorkForSite1.setCrossWidgetId(1);
        pageVersionForSite1.getWorkPageSettings().addWidget(widgetBlogWorkForSite1);

        final WidgetItem widgetBlogDraftForSite1 = TestUtil.createWidgetBlog(blogForSite1.getId());
        widgetBlogDraftForSite1.setCrossWidgetId(1);
        pageVersionForSite1.addWidget(widgetBlogDraftForSite1);

        final WidgetItem widgetBlogDraft2ForSite1 = TestUtil.createWidgetBlog(blogForSite1.getId());
        widgetBlogDraft2ForSite1.setCrossWidgetId(2);
        pageVersionForSite1.addWidget(widgetBlogDraft2ForSite1);
        //----------------------------------------------------Site1-----------------------------------------------------

        List<Integer> crossWidgetsIds = Arrays.asList(1, 2);


        List<BlogSummaryDataForPreview> blogSummaryDataForPreview =
                BlogSummaryDataForPreviewCreator.createByCrossWidgetsIds(crossWidgetsIds, pageVersionForSite1, SiteShowOption.getDraftOption());


        Assert.assertNotNull(blogSummaryDataForPreview);
        Assert.assertEquals(2, blogSummaryDataForPreview.size());

        Assert.assertEquals(blogForSite1.getName(), blogSummaryDataForPreview.get(0).getBlogName());
        Assert.assertEquals(blogForSite1.getId(), blogSummaryDataForPreview.get(0).getBlogId());
        Assert.assertEquals(widgetBlogDraftForSite1, blogSummaryDataForPreview.get(0).getWidget());
        Assert.assertEquals(10, blogSummaryDataForPreview.get(0).getBlogPosts().size());
        for (BlogPost blogPost : blogSummaryDataForPreview.get(0).getBlogPosts()) {
            Assert.assertNotNull(blogPost.getText());
            Assert.assertNotNull(blogSummaryDataForPreview.get(0).getPostUrl().get(blogPost.getBlogPostId()));
        }

        Assert.assertEquals(blogForSite1.getName(), blogSummaryDataForPreview.get(1).getBlogName());
        Assert.assertEquals(blogForSite1.getId(), blogSummaryDataForPreview.get(1).getBlogId());
        Assert.assertEquals(widgetBlogDraft2ForSite1, blogSummaryDataForPreview.get(1).getWidget());
        Assert.assertEquals(10, blogSummaryDataForPreview.get(1).getBlogPosts().size());
        for (BlogPost blogPost : blogSummaryDataForPreview.get(1).getBlogPosts()) {
            Assert.assertNotNull(blogPost.getText());
            Assert.assertNotNull(blogSummaryDataForPreview.get(0).getPostUrl().get(blogPost.getBlogPostId()));
        }
    }
}
