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
import com.shroggle.exception.BlogSummaryNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.widget.WidgetTitle;
import com.shroggle.presentation.MockWebContext;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class ConfigureBlogSummaryServiceTest {

    ConfigureBlogSummaryService service = new ConfigureBlogSummaryService();

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void testExecuteFromSiteEditPage() throws Exception {
        //----------------------------------------------------Site1-----------------------------------------------------
        final Site site1 = TestUtil.createSite();
        DraftBlog blogForSite1 = TestUtil.createBlog(site1);

        final Page pageForSite1 = TestUtil.createPage(site1);

        final PageManager draftPageVersionForSite1 = TestUtil.createPageVersion(pageForSite1, PageVersionType.DRAFT);

        final PageManager workPageVersionForSite1 = TestUtil.createPageVersion(pageForSite1, PageVersionType.WORK);

        final WidgetItem widgetBlogWorkForSite1 = TestUtil.createWidgetBlog(blogForSite1.getId());
        widgetBlogWorkForSite1.setCrossWidgetId(1);
        workPageVersionForSite1.addWidget(widgetBlogWorkForSite1);

        final WidgetItem widgetBlogDraftForSite1 = TestUtil.createWidgetBlog(blogForSite1.getId());
        widgetBlogDraftForSite1.setCrossWidgetId(1);
        draftPageVersionForSite1.addWidget(widgetBlogDraftForSite1);
        //----------------------------------------------------Site1-----------------------------------------------------

        User user = TestUtil.createUserAndLogin("email");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        DraftBlogSummary blogSummary = TestUtil.createBlogSummary(site1);

        WidgetItem widgetBlogSummary = TestUtil.createWidgetBlogSummaryForPageVersion(workPageVersionForSite1,
                blogSummary.getId());
        widgetBlogSummary.setDraftItem(blogSummary);

        service.execute(widgetBlogSummary.getWidgetId(), null);
        List<BlogSummaryData> blogsFromAllSites = service.getBlogsFromAllSites();
        List<BlogSummaryData> blogsFromCurrentSite = service.getBlogsFromCurrentSite();
        Assert.assertEquals(blogsFromCurrentSite, blogsFromAllSites);

        Assert.assertEquals(1, blogsFromAllSites.size());
        Assert.assertEquals(widgetBlogSummary.getWidgetId(), service.getWidget().getWidgetId());
        WidgetTitle title = service.getWidgetTitle();
        Assert.assertNotNull(title.getWidgetTitle());
        Assert.assertEquals("pageName", title.getPageVersionTitle());
        Assert.assertEquals("title", title.getSiteTitle());
        Assert.assertEquals("100005", service.getBlogsIdsFromCurrentAccount());
        Assert.assertEquals("100005", service.getBlogsIdsFromCurrentSite());
        Assert.assertEquals(blogSummary.getId(), service.getBlogSummary().getId());
        Assert.assertEquals(0, service.getSelectedBlogIds().size());
    }

    @Test
    public void testExecuteFromManageItems() throws Exception {
        //----------------------------------------------------Site1-----------------------------------------------------
        final Site site1 = TestUtil.createSite();
        DraftBlog blogForSite1 = TestUtil.createBlog(site1);

        final Page pageForSite1 = TestUtil.createPage(site1);

        final PageManager draftPageVersionForSite1 = TestUtil.createPageVersion(pageForSite1, PageVersionType.DRAFT);

        final PageManager workPageVersionForSite1 = TestUtil.createPageVersion(pageForSite1, PageVersionType.WORK);

        final WidgetItem widgetBlogWorkForSite1 = TestUtil.createWidgetBlog(blogForSite1.getId());
        widgetBlogWorkForSite1.setCrossWidgetId(1);
        workPageVersionForSite1.addWidget(widgetBlogWorkForSite1);

        final WidgetItem widgetBlogDraftForSite1 = TestUtil.createWidgetBlog(blogForSite1.getId());
        widgetBlogDraftForSite1.setCrossWidgetId(1);
        draftPageVersionForSite1.addWidget(widgetBlogDraftForSite1);
        //----------------------------------------------------Site1-----------------------------------------------------

        User user = TestUtil.createUserAndLogin("email");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        DraftBlogSummary blogSummary = TestUtil.createBlogSummary(site1);

        service.execute(null, blogSummary.getId());
        List<BlogSummaryData> blogsFromAllSites = service.getBlogsFromAllSites();
        List<BlogSummaryData> blogsFromCurrentSite = service.getBlogsFromCurrentSite();
        Assert.assertEquals(blogsFromCurrentSite, blogsFromAllSites);

        Assert.assertEquals(1, blogsFromAllSites.size());
        Assert.assertEquals(null, service.getWidget());
        WidgetTitle title = service.getWidgetTitle();
        Assert.assertNull(title);
        Assert.assertEquals("100005", service.getBlogsIdsFromCurrentAccount());
        Assert.assertEquals("100005", service.getBlogsIdsFromCurrentSite());
        Assert.assertEquals(blogSummary.getId(), service.getBlogSummary().getId());
        Assert.assertEquals(0, service.getSelectedBlogIds().size());
    }

    @Test(expected = BlogSummaryNotFoundException.class)
    public void executeWithNotFoundItem() throws Exception {
        TestUtil.createUserAndLogin("email");

        service.execute(null, -1);
    }

    @Test(expected = UserNotLoginedException.class)
    public void testExecute_notLoginedUser() throws Exception {
        service.execute(-1, -1);
    }

    @Test(expected = WidgetNotFoundException.class)
    public void testExecute_withoutWidget() throws Exception {
        TestUtil.createUserAndLogin("a@a.com");
        service.execute(-1, -1);
    }

}
