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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.MenuNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.logic.menu.MenuPageCheck;
import com.shroggle.logic.menu.MenuPageCheckAlways;
import com.shroggle.logic.menu.MenuPageCheckSometimes;
import com.shroggle.logic.site.page.MenuPagesHtmlTextCreator;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.presentation.menu.ConfigureMenuService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;


@RunWith(TestRunnerWithMockServices.class)
public class ConfigureMenuServiceTest {

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void executeFromSiteEditPage() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);

        final MenuItem defaultMenuItem = TestUtil.createMenuItem(page.getPageId(), site.getMenu());
        defaultMenuItem.setParent(null);

        DraftMenu menu = new DraftMenu();
        menu.setName("name1");
        menu.setSiteId(site.getSiteId());
        persistance.putMenu(menu);
        final MenuItem menuItem = TestUtil.createMenuItem(page.getPageId(), menu);
        menuItem.setParent(null);
        menu.setSiteId(site.getSiteId());

        WidgetItem widgetMenu = new WidgetItem();
        widgetMenu.setDraftItem(menu);
        persistance.putWidget(widgetMenu);
        pageVersion.addWidget(widgetMenu);

        service.execute(widgetMenu.getWidgetId(), null);

        Assert.assertEquals(widgetMenu.getWidgetId(), service.getWidget().getWidgetId());
        Assert.assertEquals(menu.getId(), service.getMenu().getId());

        final MenuPageCheck menuPageCheck = (menu.getId() <= 0 ? new MenuPageCheckAlways() : new MenuPageCheckSometimes(menu));
        final MenuPagesHtmlTextCreator pagesHtmlTextCreator = new MenuPagesHtmlTextCreator(menu, menuPageCheck, SiteShowOption.getDraftOption());
        Assert.assertEquals(pagesHtmlTextCreator.getHtml(), service.getMenuTreeHtml());
        Assert.assertEquals(1, service.getPages().size());
        Assert.assertEquals(menuItem, service.getSelectedItem());
    }

    @Test
    public void executeFromManageItems() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final Page page = TestUtil.createPage(site);

        final MenuItem defaultMenuItem = TestUtil.createMenuItem(page.getPageId(), site.getMenu());
        defaultMenuItem.setParent(null);

        DraftMenu menu = new DraftMenu();
        menu.setName("name1");
        menu.setSiteId(site.getSiteId());
        persistance.putMenu(menu);
        final MenuItem menuItem = TestUtil.createMenuItem(page.getPageId(), menu);
        menuItem.setParent(null);
        menu.setSiteId(site.getSiteId());

        service.execute(null, menu.getId());

        Assert.assertNull(service.getWidget());
        Assert.assertEquals(menu.getId(), service.getMenu().getId());

        final MenuPageCheck menuPageCheck = (menu.getId() <= 0 ? new MenuPageCheckAlways() : new MenuPageCheckSometimes(menu));
        final MenuPagesHtmlTextCreator pagesHtmlTextCreator = new MenuPagesHtmlTextCreator(menu, menuPageCheck, SiteShowOption.getDraftOption());
        Assert.assertEquals(pagesHtmlTextCreator.getHtml(), service.getMenuTreeHtml());
        Assert.assertEquals(1, service.getPages().size());
        Assert.assertEquals(menuItem, service.getSelectedItem());
    }

    @Test
    public void execute_withRemovedMenu() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        final PageManager previewPageVersion = TestUtil.createPageVersion(page);
        previewPageVersion.setCreationDate(new Date(System.currentTimeMillis()));

        DraftMenu menu = new DraftMenu();
        menu.setName("name1");
        menu.setSiteId(site.getSiteId());
        persistance.putMenu(menu);

        final MenuItem menuItem = TestUtil.createMenuItem(page.getPageId(), menu);
        menuItem.setParent(null);

        WidgetItem widgetMenu = new WidgetItem();
        widgetMenu.setDraftItem(menu);
        persistance.putWidget(widgetMenu);
        pageVersion.addWidget(widgetMenu);

        service.execute(widgetMenu.getWidgetId(), null);

        Assert.assertEquals(widgetMenu.getWidgetId(), service.getWidget().getWidgetId());

        final MenuPageCheck menuPageCheck = (menu.getId() <= 0 ? new MenuPageCheckAlways() : new MenuPageCheckSometimes(menu));
        final MenuPagesHtmlTextCreator pagesHtmlTextCreator = new MenuPagesHtmlTextCreator(menu, menuPageCheck, SiteShowOption.getDraftOption());
        Assert.assertEquals(pagesHtmlTextCreator.getHtml(), service.getMenuTreeHtml());
    }

    @Test(expected = MenuNotFoundException.class)
    public void executeWithNotFoundMenu() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final Page page = TestUtil.createPage(site);

        final MenuItem defaultMenuItem = TestUtil.createMenuItem(page.getPageId(), site.getMenu());
        defaultMenuItem.setParent(null);

        service.execute(null, -1);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() throws Exception {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        DraftMenu menu = new DraftMenu();
        persistance.putMenu(menu);

        WidgetItem widgetMenu = new WidgetItem();
        widgetMenu.setDraftItem(menu);
        persistance.putWidget(widgetMenu);
        pageVersion.addWidget(widgetMenu);

        service.execute(widgetMenu.getWidgetId(), null);
    }

    @Test(expected = WidgetNotFoundException.class)
    public void executeWithoutWidget() throws Exception {
        TestUtil.createUserAndLogin();

        service.execute(1, null);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ConfigureMenuService service = new ConfigureMenuService();

}