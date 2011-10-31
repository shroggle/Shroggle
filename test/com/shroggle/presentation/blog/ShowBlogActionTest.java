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
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.ServiceLocator;
import com.shroggle.entity.*;
import com.shroggle.presentation.ResolutionMock;
import junit.framework.Assert;
import net.sourceforge.stripes.action.ActionBeanContext;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class ShowBlogActionTest {

    @Test
    public void executeWithoutBlog() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        ResolutionMock resolutionMock = (ResolutionMock) action.execute();
        Assert.assertEquals(action, resolutionMock.getLoginInUserAction());
    }

    @Test
    public void executeWithoutLogin() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftBlog blog = TestUtil.createBlog(site);

        action.setBlogId(blog.getId());
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();
        Assert.assertEquals(action, resolutionMock.getLoginInUserAction());
    }

    @Test
    public void execute() {
        final Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftBlog blog = TestUtil.createBlog(site);

        action.setContext(new ActionBeanContext());
        action.setBlogId(blog.getId());
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();
        Assert.assertEquals((Integer) user.getUserId(), resolutionMock.getShowWidgetPreviewVisitorId());
        final WidgetItem widgetBlog = (WidgetItem) resolutionMock.getShowWidgetPreviewWidget();
        Assert.assertEquals(true, widgetBlog.isWidgetItem());
        Assert.assertEquals(blog.getId(), widgetBlog.getDraftItem().getId());
    }

    @Test
    public void executeReadOnly() {
        final Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);

        SiteOnItem siteOnItemRight = blog.createSiteOnItemRight(site);
        siteOnItemRight.setType(SiteOnItemRightType.READ);
        siteOnItemRight.setAcceptDate(new Date());
        persistance.putSiteOnItem(siteOnItemRight);

        action.setContext(new ActionBeanContext());
        action.setBlogId(blog.getId());
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();
        Assert.assertEquals((Integer) user.getUserId(), resolutionMock.getShowWidgetPreviewVisitorId());
        final WidgetItem widgetBlog = (WidgetItem) resolutionMock.getShowWidgetPreviewWidget();
        Assert.assertEquals(true, widgetBlog.isWidgetItem());
        Assert.assertEquals(blog.getId(), widgetBlog.getDraftItem().getId());
    }

    private final ShowBlogAction action = new ShowBlogAction();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
