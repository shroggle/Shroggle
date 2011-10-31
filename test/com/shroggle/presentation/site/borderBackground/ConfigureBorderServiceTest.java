/*
*/
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


package com.shroggle.presentation.site.borderBackground;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(value = TestRunnerWithMockServices.class)
public class ConfigureBorderServiceTest {

    @Before
    public void before() {
        final MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void execute() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion1 = new PageManager(page);

        final Border border = new Border();
        border.setSiteId(site.getSiteId());
        persistance.putBorder(border);

        WidgetItem widgetItem = TestUtil.createWidgetItem();
        widgetItem.setBorderId(border.getId());
        persistance.putWidget(widgetItem);
        pageVersion1.addWidget(widgetItem);

        service.execute(widgetItem.getWidgetId(), null, null);

        Assert.assertNotNull(service.getDraftBorder());
        Assert.assertEquals(new WidgetManager(widgetItem).getBorder(SiteShowOption.getDraftOption()), service.getDraftBorder());
        Assert.assertEquals(site.getSiteId(), service.getSiteId());
        Assert.assertNotNull(service.getWidgetTitle());
    }

    @Test
    public void execute_ForDraftItem() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion1 = new PageManager(page);

        final Border border = new Border();
        border.setSiteId(site.getSiteId());
        persistance.putBorder(border);

        WidgetItem widgetItem = TestUtil.createTextWidget();
        widgetItem.getDraftItem().setBorderId(border.getId());
        widgetItem.getDraftItem().setSiteId(site.getSiteId());
        persistance.putWidget(widgetItem);
        pageVersion1.addWidget(widgetItem);


        service.execute(null, widgetItem.getDraftItem().getId(), null);

        Assert.assertNotNull(service.getDraftBorder());
        Assert.assertEquals(new ItemManager(widgetItem.getDraftItem()).getBorder(SiteShowOption.getDraftOption()), service.getDraftBorder());
        Assert.assertEquals(site.getSiteId(), service.getSiteId());
        Assert.assertNull(service.getWidgetTitle());
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutRight() throws Exception {
        final Site site = TestUtil.createSite();
        //TestUtil.createUserAndLogin();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final Border background = new Border();
        persistance.putBorder(background);

        final WidgetItem widgetBlog = new WidgetItem();
        widgetBlog.setBorderId(background.getId());
        persistance.putWidget(widgetBlog);
        pageVersion.addWidget(widgetBlog);

        service.execute(widgetBlog.getWidgetId(), null, null);
    }

    @Test(expected = UserNotLoginedException.class)
    public void getCheckedBackgroundByIdWithoutLogin() throws Exception {
        Site site = new Site();
        persistance.putSite(site);

        PageManager pageVersion = new PageManager(TestUtil.createPage(site));

        DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);

        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setDraftItem(blog);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        service.execute(widget.getWidgetId(), null, null);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() throws Exception {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final WidgetItem widget = new WidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        service.execute(widget.getWidgetId(), null, null);
    }


    @Test(expected = WidgetNotFoundException.class)
    public void executeWithoutWidget() throws Exception {
        TestUtil.createUserAndLogin();
        service.execute(1, null, null);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ConfigureBorderService service = new ConfigureBorderService();

}
