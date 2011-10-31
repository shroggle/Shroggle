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

package com.shroggle.presentation.site;

import com.shroggle.TestBaseWithMockService;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.BlogSummaryNameNotUnique;
import com.shroggle.exception.BlogSummaryNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.presentation.blogSummary.CreateBlogSummaryRequest;
import com.shroggle.presentation.blogSummary.CreateBlogSummaryService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;


/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class CreateBlogSummaryServiceTest extends TestBaseWithMockService {
    
    @Test
    public void executeFromSiteEditPage() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);

        DraftBlogSummary blogSummary = TestUtil.createBlogSummary(site);
        blogSummary.setName("bs_name");

        WidgetItem widgetBlogSummary = TestUtil.createWidgetItem();
        widgetBlogSummary.setDraftItem(blogSummary);
        persistance.putWidget(widgetBlogSummary);
        pageVersion.addWidget(widgetBlogSummary);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        request.setBlogSummaryHeader("Header");
        request.setBlogSummaryName("Name");
        request.setAllSiteBlogs(true);
        request.setPostDisplayCriteria(PostDisplayCriteria.MOST_COMMENTED);
        request.setPostSortCriteria(PostSortCriteria.ALPHABETICALLY_BY_POST_TITLE);
        request.setShowBlogName(true);
        request.setCurrentSiteBlogs(true);
        request.setShowPostContents(true);
        request.setIncludedCrossWidgetId(new ArrayList<Integer>(){{
            add(1);
        }});
        request.setIncludedPostNumber(1);
        request.setShowPostAuthor(true);
        request.setShowPostDate(true);
        request.setShowPostName(true);
        request.setSelectedBlogSummaryId(blogSummary.getId());
        request.setWidgetId(widgetBlogSummary.getWidgetId());

        FunctionalWidgetInfo response = service.execute(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(widgetBlogSummary.getWidgetId(), response.getWidget().getWidgetId());

        Assert.assertEquals("Header", blogSummary.getBlogSummaryHeader());
        Assert.assertEquals("Name", blogSummary.getName());
        Assert.assertEquals(true, blogSummary.isAllSiteBlogs());
        Assert.assertEquals(true, blogSummary.isShowBlogName());
        Assert.assertEquals(true, blogSummary.isCurrentSiteBlogs());
        Assert.assertEquals(true, blogSummary.isShowPostContents());
        Assert.assertEquals(true, blogSummary.isShowPostAuthor());
        Assert.assertEquals(true, blogSummary.isShowPostDate());
        Assert.assertEquals(true, blogSummary.isShowBlogName());
        Assert.assertEquals(1, blogSummary.getIncludedPostNumber());
        Assert.assertEquals(1, (int) blogSummary.getIncludedCrossWidgetId().get(0));
        Assert.assertEquals(PostDisplayCriteria.MOST_COMMENTED, blogSummary.getPostDisplayCriteria());
        Assert.assertEquals(PostSortCriteria.ALPHABETICALLY_BY_POST_TITLE, blogSummary.getPostSortCriteria());
    }

    @Test
    public void executeFromManageItems() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftBlogSummary blogSummary = TestUtil.createBlogSummary(site);
        blogSummary.setName("bs_name");

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        request.setBlogSummaryHeader("Header");
        request.setBlogSummaryName("Name");
        request.setAllSiteBlogs(true);
        request.setPostDisplayCriteria(PostDisplayCriteria.MOST_COMMENTED);
        request.setPostSortCriteria(PostSortCriteria.ALPHABETICALLY_BY_POST_TITLE);
        request.setShowBlogName(true);
        request.setCurrentSiteBlogs(true);
        request.setShowPostContents(true);
        request.setIncludedCrossWidgetId(new ArrayList<Integer>(){{
            add(1);
        }});
        request.setIncludedPostNumber(1);
        request.setShowPostAuthor(true);
        request.setShowPostDate(true);
        request.setShowPostName(true);
        request.setSelectedBlogSummaryId(blogSummary.getId());

        FunctionalWidgetInfo response = service.execute(request);
        Assert.assertNotNull(response);
        Assert.assertNull(response.getWidget());

        Assert.assertEquals("Header", blogSummary.getBlogSummaryHeader());
        Assert.assertEquals("Name", blogSummary.getName());
        Assert.assertEquals(true, blogSummary.isAllSiteBlogs());
        Assert.assertEquals(true, blogSummary.isShowBlogName());
        Assert.assertEquals(true, blogSummary.isCurrentSiteBlogs());
        Assert.assertEquals(true, blogSummary.isShowPostContents());
        Assert.assertEquals(true, blogSummary.isShowPostAuthor());
        Assert.assertEquals(true, blogSummary.isShowPostDate());
        Assert.assertEquals(true, blogSummary.isShowBlogName());
        Assert.assertEquals(1, blogSummary.getIncludedPostNumber());
        Assert.assertEquals(1, (int) blogSummary.getIncludedCrossWidgetId().get(0));
        Assert.assertEquals(PostDisplayCriteria.MOST_COMMENTED, blogSummary.getPostDisplayCriteria());
        Assert.assertEquals(PostSortCriteria.ALPHABETICALLY_BY_POST_TITLE, blogSummary.getPostSortCriteria());
    }

    @Test(expected = BlogSummaryNameNotUnique.class)
    public void executeWithNotUniqueName() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftBlogSummary blogSummary = TestUtil.createBlogSummary(site);
        blogSummary.setName("name2");

        DraftBlogSummary blogSummary2 = TestUtil.createBlogSummary(site);
        blogSummary2.setName("name");

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        request.setBlogSummaryHeader("Header");
        request.setBlogSummaryName("name");
        request.setAllSiteBlogs(true);
        request.setPostDisplayCriteria(PostDisplayCriteria.MOST_COMMENTED);
        request.setPostSortCriteria(PostSortCriteria.ALPHABETICALLY_BY_POST_TITLE);
        request.setShowBlogName(true);
        request.setCurrentSiteBlogs(true);
        request.setShowPostContents(true);
        request.setIncludedCrossWidgetId(new ArrayList<Integer>());
        request.setIncludedPostNumber(1);
        request.setShowPostAuthor(true);
        request.setShowPostDate(true);
        request.setShowPostName(true);
        request.setSelectedBlogSummaryId(blogSummary.getId());

        service.execute(request);
    }
    
    @Test(expected = BlogSummaryNotFoundException.class)
    public void executeWithNotFoundBlogSummary() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        request.setBlogSummaryHeader("Header");
        request.setBlogSummaryName("Name");
        request.setAllSiteBlogs(true);
        request.setPostDisplayCriteria(PostDisplayCriteria.MOST_COMMENTED);
        request.setPostSortCriteria(PostSortCriteria.ALPHABETICALLY_BY_POST_TITLE);
        request.setShowBlogName(true);
        request.setCurrentSiteBlogs(true);
        request.setShowPostContents(true);
        request.setIncludedCrossWidgetId(new ArrayList<Integer>(){{
            add(1);
        }});
        request.setIncludedPostNumber(1);
        request.setShowPostAuthor(true);
        request.setShowPostDate(true);
        request.setShowPostName(true);
        request.setSelectedBlogSummaryId(-1);

        service.execute(request);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() throws IOException, ServletException {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        service.execute(request);
    }

    private final CreateBlogSummaryService service = new CreateBlogSummaryService();
    private final CreateBlogSummaryRequest request = new CreateBlogSummaryRequest();
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();

}