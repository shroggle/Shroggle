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
import com.shroggle.entity.*;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;


@RunWith(value = TestRunnerWithMockServices.class)
public class ConfigureBlogServiceTest {

    @Test
    public void executeFromManageItems() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        blog.setName("aa");
        persistance.putItem(blog);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));

        service.execute(null, blog.getId());
        Assert.assertEquals(blog, service.getSelectedBlog());
        Assert.assertNull(service.getWidgetTitle());
        Assert.assertEquals(site.getSiteId(), service.getSite().getSiteId());
    }

    @Test
    public void executeFromSiteEditPage() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final PageManager page = TestUtil.createPageVersionAndPage(site);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        blog.setName("aa");
        persistance.putItem(blog);

        WidgetItem blogWidget = TestUtil.createWidgetItem();
        blogWidget.setDraftItem(blog);
        page.addWidget(blogWidget);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));

        //Verify that service don't render page.
        service.execute(blogWidget.getWidgetId(), null);
        Assert.assertEquals(blog, service.getSelectedBlog());
        Assert.assertNotNull(service.getWidgetTitle());
        Assert.assertEquals(site.getSiteId(), service.getSite().getSiteId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void executeFromSiteEditPageWithWidgetWithoutItem() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final PageManager page = TestUtil.createPageVersionAndPage(site);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        WidgetItem blogWidget = TestUtil.createWidgetItem();
        page.addWidget(blogWidget);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));

        service.execute(blogWidget.getWidgetId(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void executeWithBothWidgetAndBlogIdsEmpty() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftBlog blog = new DraftBlog();
        blog.setSiteId(site.getSiteId());
        blog.setName("aa");
        persistance.putItem(blog);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));

        service.execute(null, null);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() throws IOException, ServletException {
        service.execute(null, null);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ConfigureBlogService service = new ConfigureBlogService();

}