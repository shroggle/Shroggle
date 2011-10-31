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

package com.shroggle.presentation.forum;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.presentation.site.ConfigureForumService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;

@RunWith(TestRunnerWithMockServices.class)
public class ConfigureForumServiceTest {

    @Test
    public void executeFromManageItems() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftForum forum = new DraftForum();
        forum.setSiteId(site.getSiteId());
        forum.setName("aa");
        persistance.putItem(forum);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));

        service.execute(null, forum.getId());
        Assert.assertEquals(forum, service.getSelectedForum());
        Assert.assertNull(service.getWidgetTitle());
        Assert.assertEquals(site.getSiteId(), service.getSite().getSiteId());
    }

    @Test
    public void executeFromSiteEditPage() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final PageManager page = TestUtil.createPageVersionAndPage(site);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftForum forum = new DraftForum();
        forum.setSiteId(site.getSiteId());
        forum.setName("aa");
        persistance.putItem(forum);

        WidgetItem forumWidget = TestUtil.createWidgetItem();
        forumWidget.setDraftItem(forum);
        page.addWidget(forumWidget);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));

        service.execute(forumWidget.getWidgetId(), null);
        Assert.assertEquals(forum, service.getSelectedForum());
        Assert.assertNotNull(service.getWidgetTitle());
        Assert.assertEquals(site.getSiteId(), service.getSite().getSiteId());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void executeFromSiteEditPageWithWidgetWithoutItem() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final PageManager page = TestUtil.createPageVersionAndPage(site);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        WidgetItem forumWidget = TestUtil.createWidgetItem();
        page.addWidget(forumWidget);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));

        service.execute(forumWidget.getWidgetId(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void executeWithBothWidgetAndForumIdsEmpty() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftForum forum = new DraftForum();
        forum.setSiteId(site.getSiteId());
        forum.setName("aa");
        persistance.putItem(forum);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));

        service.execute(null, null);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWihoutLogin() throws IOException, ServletException {
        service.execute(null, -1);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ConfigureForumService service = new ConfigureForumService();

}