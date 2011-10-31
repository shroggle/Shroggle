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
package com.shroggle.logic.site.page;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.entity.*;
import junit.framework.Assert;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class SystemPagesCreatorTest {

    @Test
    public void testCreateDefaultLoginPage() {
        Site site = TestUtil.createSite();
        final Page loginPage = SystemPagesCreator.createDefaultLoginPageForSite(site);
        Assert.assertNotNull(loginPage);
        Assert.assertNotNull(site.getLoginPage());
        Assert.assertEquals(loginPage, site.getLoginPage());
        Assert.assertEquals(PageType.LOGIN, loginPage.getType());
        Assert.assertTrue(loginPage.isSystem());
        Assert.assertEquals(loginPage, ServiceLocator.getPersistance().getPage(loginPage.getPageId()));
        PageManager pageVersion = new PageManager(loginPage, SiteShowOption.getWorkOption());
        Assert.assertNotNull(pageVersion);
        Assert.assertEquals(loginPage, pageVersion.getPage());
        Assert.assertNotNull(pageVersion.getWorkPageSettings());
        Assert.assertEquals("defaultLoginPage", pageVersion.getName());
        Assert.assertEquals("/defaultLoginPage", pageVersion.getUrl());
        Assert.assertEquals(ServiceLocator.getFileSystem().getLoginPageDefaultHtml(), pageVersion.getHtml());
        Assert.assertEquals(3, pageVersion.getWidgets().size());
        Widget widget = pageVersion.getWidgets().get(0);
        Assert.assertNotNull(widget);
        Assert.assertEquals(true, widget.isWidgetComposit());
        Assert.assertEquals(widget, ServiceLocator.getPersistance().getWidget(widget.getWidgetId()));
    }

    @Test
    public void testCreateAdminLoginPage() {
        Site site = TestUtil.createSite();
        final Page loginPage = SystemPagesCreator.createAdminLoginPageForSite(site);
        Assert.assertNotNull(loginPage);
        Assert.assertNotNull(site.getLoginAdminPage());
        Assert.assertEquals(loginPage, site.getLoginAdminPage());
        Assert.assertEquals(PageType.LOGIN, loginPage.getType());
        Assert.assertTrue(loginPage.isSystem());
        Assert.assertEquals(loginPage, ServiceLocator.getPersistance().getPage(loginPage.getPageId()));
        PageManager pageVersion = new PageManager(loginPage, SiteShowOption.getWorkOption());
        Assert.assertNotNull(pageVersion);
        Assert.assertEquals(loginPage, pageVersion.getPage());
        Assert.assertNotNull(pageVersion.getWorkPageSettings());
        Assert.assertEquals("defaultLoginAdminPage", pageVersion.getName());
        Assert.assertEquals("/defaultLoginAdminPage", pageVersion.getUrl());
        Assert.assertEquals(ServiceLocator.getFileSystem().getLoginAdminPageDefaultHtml(), pageVersion.getHtml());
        Assert.assertEquals(3, pageVersion.getWidgets().size());
        Widget widget = pageVersion.getWidgets().get(0);
        Assert.assertNotNull(widget);
        Assert.assertEquals(true, widget.isWidgetComposit());
        Assert.assertEquals(widget, ServiceLocator.getPersistance().getWidget(widget.getWidgetId()));
    }
}
