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

import java.util.List;

/**
 * @author Balakirev Anatoliy
 */

@RunWith(TestRunnerWithMockServices.class)
public class BlogSummaryDataCreatorTest {
    
    @Test
    public void testCreate() {
        //----------------------------------------------------Site1-----------------------------------------------------
        final Site site1 = TestUtil.createSite();
        DraftBlog blogForSite1 = TestUtil.createBlog(site1);
        blogForSite1.setName("blog1");

        //---------------------------------------------Blog without widget----------------------------------------------
        TestUtil.createBlog(site1);
        //---------------------------------------------Blog without widget----------------------------------------------

        final Page pageForSite1 = TestUtil.createPage(site1);

        final PageManager pageVersionForSite1 = TestUtil.createPageVersion(pageForSite1, PageVersionType.WORK);

        final WidgetItem widgetBlogWorkForSite1 = TestUtil.createWidgetBlog(blogForSite1.getId());
        widgetBlogWorkForSite1.setCrossWidgetId(1);
        pageVersionForSite1.getWorkPageSettings().addWidget(widgetBlogWorkForSite1);

        final WidgetItem widgetBlogDraftForSite1 = TestUtil.createWidgetBlog(blogForSite1.getId());
        widgetBlogDraftForSite1.setCrossWidgetId(1);
        pageVersionForSite1.addWidget(widgetBlogDraftForSite1);
        //----------------------------------------------------Site1-----------------------------------------------------

        //----------------------------------------------------Site2-----------------------------------------------------
        final Site site2 = TestUtil.createSite();
        DraftBlog blogForSite2 = TestUtil.createBlog(site2);
        blogForSite2.setName("blog2");

        final Page pageForSite2 = TestUtil.createPage(site2);

        final PageManager pageVersionForSite2 = TestUtil.createPageVersion(pageForSite2, PageVersionType.WORK);

        final WidgetItem widgetBlogWorkForSite2 = TestUtil.createWidgetBlog(blogForSite1.getId());
        widgetBlogWorkForSite2.setCrossWidgetId(2);
        pageVersionForSite2.getWorkPageSettings().addWidget(widgetBlogWorkForSite2);

        final WidgetItem widgetBlogDraft2ForSite2 = TestUtil.createWidgetBlog(blogForSite2.getId());
        widgetBlogDraft2ForSite2.setCrossWidgetId(3);
        pageVersionForSite2.addWidget(widgetBlogDraft2ForSite2);
        //----------------------------------------------------Site2-----------------------------------------------------

        //--------------------------------------------------Blueprint---------------------------------------------------
        final Site blueprint = TestUtil.createBlueprint();
        DraftBlog blogForBlueprint = TestUtil.createBlog(blueprint);
        blogForBlueprint.setName("blog3");

        final Page pageForBlueprint = TestUtil.createPage(blueprint);

        final PageManager pageVersionForBlueprint = TestUtil.createPageVersion(pageForBlueprint, PageVersionType.WORK);

        final WidgetItem widgetBlogWorkForBlueprint = TestUtil.createWidgetBlog(blogForBlueprint.getId());
        widgetBlogWorkForBlueprint.setCrossWidgetId(4);
        pageVersionForBlueprint.getWorkPageSettings().addWidget(widgetBlogWorkForBlueprint);

        final WidgetItem widgetBlogDraftForBlueprint = TestUtil.createWidgetBlog(blogForBlueprint.getId());
        widgetBlogDraftForBlueprint.setCrossWidgetId(5);
        pageVersionForBlueprint.addWidget(widgetBlogDraftForBlueprint);
        //--------------------------------------------------Blueprint---------------------------------------------------

        User user = TestUtil.createUserAndLogin("email");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user, site2, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user, blueprint, SiteAccessLevel.ADMINISTRATOR);


        BlogSummaryDataCreator creator = new BlogSummaryDataCreator(user.getUserId(), SiteShowOption.getDraftOption());
        List<BlogSummaryData> blogsFromAllSites = creator.getForAllSites();
        List<BlogSummaryData> blogsFromCurrentSite = creator.getForSite(site2.getSiteId());


        Assert.assertNotNull(blogsFromAllSites);
        Assert.assertNotNull(blogsFromCurrentSite);
        //------------------------------------------------from all sites------------------------------------------------
        Assert.assertEquals(3, blogsFromAllSites.size());
        Assert.assertEquals(blogForSite1.getId(), blogsFromAllSites.get(0).getBlogId());
        Assert.assertEquals(blogForSite1.getName(), blogsFromAllSites.get(0).getBlogName());
        Assert.assertEquals(1, blogsFromAllSites.get(0).getWidgetItems().size());
        Assert.assertTrue(blogsFromAllSites.get(0).getWidgetItems().contains(new BlogSummaryData.BlogSummaryDataWidget(widgetBlogDraftForSite1)));

        Assert.assertEquals(blogForSite2.getId(), blogsFromAllSites.get(1).getBlogId());
        Assert.assertEquals(blogForSite2.getName(), blogsFromAllSites.get(1).getBlogName());
        Assert.assertEquals(1, blogsFromAllSites.get(1).getWidgetItems().size());
        Assert.assertTrue(blogsFromAllSites.get(1).getWidgetItems().contains(new BlogSummaryData.BlogSummaryDataWidget(widgetBlogDraft2ForSite2)));

        Assert.assertEquals(blogForBlueprint.getId(), blogsFromAllSites.get(2).getBlogId());
        Assert.assertEquals(blogForBlueprint.getName(), blogsFromAllSites.get(2).getBlogName());
        Assert.assertEquals(1, blogsFromAllSites.get(2).getWidgetItems().size());
        Assert.assertTrue(blogsFromAllSites.get(2).getWidgetItems().contains(new BlogSummaryData.BlogSummaryDataWidget(widgetBlogDraftForBlueprint)));
        //------------------------------------------------from all sites------------------------------------------------

        //-------------------------------------------from current site (site2)------------------------------------------
        Assert.assertEquals(1, blogsFromCurrentSite.size());
        Assert.assertEquals(blogForSite2.getId(), blogsFromCurrentSite.get(0).getBlogId());
        Assert.assertEquals(blogForSite2.getName(), blogsFromCurrentSite.get(0).getBlogName());
        Assert.assertEquals(1, blogsFromCurrentSite.get(0).getWidgetItems().size());
        Assert.assertTrue(blogsFromCurrentSite.get(0).getWidgetItems().contains(new BlogSummaryData.BlogSummaryDataWidget(widgetBlogDraft2ForSite2)));
        //-------------------------------------------from current site (site2)------------------------------------------
    }

    @Test
    public void testCreate_withBlogCreatedOnOneSiteAndUsedOnSeveral() {
        //----------------------------------------------------Site1-----------------------------------------------------
        final Site site1 = TestUtil.createSite();
        final PageManager pageVersionForSite1 = TestUtil.createPageVersion(TestUtil.createPage(site1), PageVersionType.WORK);

        DraftBlog blogForSite1 = TestUtil.createBlog(site1);
        blogForSite1.setName("blog1");

        final WidgetItem widgetBlogDraftForSite1 = TestUtil.createWidgetBlog(blogForSite1.getId());
        widgetBlogDraftForSite1.setCrossWidgetId(1);
        pageVersionForSite1.addWidget(widgetBlogDraftForSite1);
        //----------------------------------------------------Site1-----------------------------------------------------

        //----------------------------------------------------Site2-----------------------------------------------------
        final Site site2 = TestUtil.createSite();
        final PageManager pageVersionForSite2 = TestUtil.createPageVersion(TestUtil.createPage(site2), PageVersionType.WORK);

        DraftBlog blogForSite2 = TestUtil.createBlog(site2);
        blogForSite2.setName("blog2");

        final WidgetItem widgetBlogDraft2ForSite2 = TestUtil.createWidgetBlog(blogForSite2.getId());
        widgetBlogDraft2ForSite2.setCrossWidgetId(3);
        pageVersionForSite2.addWidget(widgetBlogDraft2ForSite2);

        /*---This blog also inserted on other site. We should not include this widget in BlogSummaryData`s widgets----*/
        final WidgetItem widgetBlogDraftForSite1_withBlogFromSite2 = TestUtil.createWidgetBlog(blogForSite2.getId());
        widgetBlogDraftForSite1_withBlogFromSite2.setCrossWidgetId(1123);
        pageVersionForSite1.addWidget(widgetBlogDraftForSite1_withBlogFromSite2);
        /*---This blog also inserted on other site. We should not include this widget in BlogSummaryData`s widgets----*/
        //----------------------------------------------------Site2-----------------------------------------------------


        final User user = TestUtil.createUserAndLogin("email");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user, site2, SiteAccessLevel.ADMINISTRATOR);


        final BlogSummaryDataCreator creator = new BlogSummaryDataCreator(user.getUserId(), SiteShowOption.getDraftOption());
        final List<BlogSummaryData> blogsFromCurrentSite = creator.getForSite(site2.getSiteId());


        //-------------------------------------------from current site (site2)------------------------------------------
        Assert.assertEquals(1, blogsFromCurrentSite.size());
        Assert.assertEquals(blogForSite2.getId(), blogsFromCurrentSite.get(0).getBlogId());
        Assert.assertEquals(blogForSite2.getName(), blogsFromCurrentSite.get(0).getBlogName());
        Assert.assertEquals(1, blogsFromCurrentSite.get(0).getWidgetItems().size());
        Assert.assertTrue(blogsFromCurrentSite.get(0).getWidgetItems().contains(new BlogSummaryData.BlogSummaryDataWidget(widgetBlogDraft2ForSite2)));
        //-------------------------------------------from current site (site2)------------------------------------------
    }

    @Test
    public void testGetName() throws Exception {
        User user = TestUtil.createUserAndLogin("a@a.com");

        Site site = TestUtil.createSite("site title", "site url");
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site, "page name");

        DraftBlogSummary blogSummary = TestUtil.createBlogSummary(site);

        WidgetItem widgetItem = TestUtil.createWidgetBlogSummaryForPageVersion(pageVersion, blogSummary.getId());

        BlogSummaryData.BlogSummaryDataWidget blogSummaryDataWidget = new BlogSummaryData.BlogSummaryDataWidget(widgetItem);

        Assert.assertEquals("site title http://site url.shroggle.com, page name", blogSummaryDataWidget.getLocation());
    }

    @Test
    public void testGetName_withLongNames() throws Exception {
        final User user = TestUtil.createUserAndLogin("a@a.com");

        final Site site = TestUtil.createSite("siteTitleeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee", "siteUrllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll");
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site, "pageNameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        final DraftBlogSummary blogSummary = TestUtil.createBlogSummary(site);
        final WidgetItem widgetItem = TestUtil.createWidgetBlogSummaryForPageVersion(pageVersion, blogSummary.getId());
        final BlogSummaryData.BlogSummaryDataWidget blogSummaryDataWidget = new BlogSummaryData.BlogSummaryDataWidget(widgetItem);


        Assert.assertEquals("siteTitleeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee" +
                " http://siteUrllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll.shroggle.com, " +
                "pageNameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee", blogSummaryDataWidget.getLocation());
    }
}
