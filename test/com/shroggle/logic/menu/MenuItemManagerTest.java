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
package com.shroggle.logic.menu;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.resource.provider.ResourceGetterType;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class MenuItemManagerTest {

    @Test
    public void testGetName_withNameInMenuItemAndNameInPageVersion() {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.setName("pageVersionName");
        final DraftMenu menu = TestUtil.createMenu();

        final MenuItem menuItem = TestUtil.createMenuItem(pageVersion.getPage().getPageId(), menu, true);
        menuItem.setName("menuItemName");
        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());
        Assert.assertEquals("menuItemName", manager.getName());
    }

    @Test
    public void testGetName_withWorkAndUnchangedDraft() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager draftPageVersion = TestUtil.createPageVersion(page, PageVersionType.DRAFT);
        final PageManager workPageVersion = TestUtil.createPageVersion(page, PageVersionType.WORK);
        draftPageVersion.setName("draft");
        workPageVersion.setName("work");
        final DraftMenu menu = TestUtil.createMenu();

        final MenuItem menuItem = TestUtil.createMenuItem(page.getPageId(), menu, true);
        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());
        Assert.assertEquals("work", manager.getName());
    }

    @Test
    public void testGetName_withWorkAndChangedDraft() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = TestUtil.createPageVersion(page, PageVersionType.WORK);
        pageVersion.setName("draft");
        pageVersion.getWorkPageSettings().setName("work");
        final DraftMenu menu = TestUtil.createMenu();

        final MenuItem menuItem = TestUtil.createMenuItem(page.getPageId(), menu, true);
        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());
        Assert.assertEquals("draft", manager.getName());
    }

    @Test
    public void testGetName_withoutNameInMenuItemAndWithNameInPageVersion() {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.setName("pageVersionName");
        final DraftMenu menu = TestUtil.createMenu();

        final MenuItem menuItem = TestUtil.createMenuItem(pageVersion.getPage().getPageId(), menu, true);
        menuItem.setName(null);
        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());
        Assert.assertEquals("pageVersionName", manager.getName());
    }

    @Test
    public void testGetName_withoutNameInMenuItemAndNameInPageVersion() {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.setName(null);
        final DraftMenu menu = TestUtil.createMenu();

        final MenuItem menuItem = TestUtil.createMenuItem(pageVersion.getPage().getPageId(), menu, true);
        menuItem.setName(null);
        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());
        Assert.assertEquals("", manager.getName());
    }

    @Test
    public void testGetTitle_withNameInMenuItemAndNameInPageVersion() {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.setTitle("pageVersionName");
        final DraftMenu menu = TestUtil.createMenu();

        final MenuItem menuItem = TestUtil.createMenuItem(pageVersion.getPage().getPageId(), menu, true);
        menuItem.setTitle("menuItemName");
        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());
        Assert.assertEquals("menuItemName", manager.getTitle());
    }

    @Test
    public void testGetTitle_withoutNameInMenuItemAndWithNameInPageVersion() {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.setTitle("pageVersionName");
        final DraftMenu menu = TestUtil.createMenu();

        final MenuItem menuItem = TestUtil.createMenuItem(pageVersion.getPage().getPageId(), menu, true);
        menuItem.setTitle(null);
        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());
        Assert.assertEquals("pageVersionName", manager.getTitle());
    }

    @Test
    public void testGetTitle_withoutNameInMenuItemAndNameInPageVersion() {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.setTitle(null);
        final DraftMenu menu = TestUtil.createMenu();

        final MenuItem menuItem = TestUtil.createMenuItem(pageVersion.getPage().getPageId(), menu, true);
        menuItem.setTitle(null);

        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());

        Assert.assertEquals("", manager.getTitle());
    }

    @Test
    public void testGetMenuUrlType() {
        final MenuItem menuItem = TestUtil.createMenuItem();
        menuItem.setUrlType(MenuUrlType.CUSTOM_URL);

        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());

        Assert.assertEquals(MenuUrlType.CUSTOM_URL, manager.getMenuUrlType());
    }

    @Test
    public void testGetCustomUrl() {
        final MenuItem menuItem = TestUtil.createMenuItem();
        menuItem.setCustomUrl("url");

        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());

        Assert.assertEquals("url", manager.getCustomUrl());
    }

    @Test
    public void testGetCustomUrl_withoutUrl() {
        final MenuItem menuItem = TestUtil.createMenuItem();
        menuItem.setCustomUrl(null);

        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());

        Assert.assertEquals("", manager.getCustomUrl());
    }

    @Test
    public void testGetImageId() {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem = TestUtil.createMenuItem(pageVersion.getPage().getPageId(), menu, true);

        final MenuImage menuImage = TestUtil.createMenuImage(site.getSiteId());
        menuItem.setImageId(menuImage.getMenuImageId());


        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());
        Assert.assertEquals(menuImage.getMenuImageId(), manager.getImageId().intValue());
    }

    @Test
    public void testGetImageId_withoutImageId() {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem = TestUtil.createMenuItem(pageVersion.getPage().getPageId(), menu, true);

        TestUtil.createMenuImage(site.getSiteId());
        menuItem.setImageId(null);

        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());
        Assert.assertEquals(null, manager.getImageId());
    }

    @Test
    public void testGetImageId_withoutFile() {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem = TestUtil.createMenuItem(pageVersion.getPage().getPageId(), menu, true);

        final MenuImage menuImage = new MenuImage();
        ServiceLocator.getPersistance().putMenuImage(menuImage);
        menuItem.setImageId(menuImage.getMenuImageId());

        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());
        Assert.assertEquals(null, manager.getImageId());
    }

    @Test
    public void testGetThumbnailImageUrl() {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem = TestUtil.createMenuItem(pageVersion.getPage().getPageId(), menu, true);

        final MenuImage menuImage = TestUtil.createMenuImage(site.getSiteId());
        menuItem.setImageId(menuImage.getMenuImageId());


        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());
        Assert.assertEquals(ResourceGetterType.MENU_IMAGE.getUrl(menuItem.getImageId()), manager.getThumbnailImageUrl());
    }

    @Test
    public void testGetThumbnailImageUrl_withoutImageId() {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem = TestUtil.createMenuItem(pageVersion.getPage().getPageId(), menu, true);

        TestUtil.createMenuImage(site.getSiteId());
        menuItem.setImageId(null);

        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());
        Assert.assertEquals(null, manager.getThumbnailImageUrl());
    }

    @Test
    public void testGetThumbnailImageUrl_withoutFile() {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem = TestUtil.createMenuItem(pageVersion.getPage().getPageId(), menu, true);

        final MenuImage menuImage = new MenuImage();
        ServiceLocator.getPersistance().putMenuImage(menuImage);
        menuItem.setImageId(menuImage.getMenuImageId());

        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());
        Assert.assertEquals(null, manager.getThumbnailImageUrl());
    }

    @Test
    public void testGetImageUrl() {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem = TestUtil.createMenuItem(pageVersion.getPage().getPageId(), menu, true);

        final MenuImage menuImage = TestUtil.createMenuImage(site.getSiteId());
        menuItem.setImageId(menuImage.getMenuImageId());


        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());
        Assert.assertEquals(ResourceGetterType.MENU_IMAGE.getUrl(menuItem.getImageId()), manager.getImageUrl());
    }

    @Test
    public void testGetImageUrl_withoutImageId() {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem = TestUtil.createMenuItem(pageVersion.getPage().getPageId(), menu, true);

        TestUtil.createMenuImage(site.getSiteId());
        menuItem.setImageId(null);

        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());
        Assert.assertEquals("", manager.getImageUrl());
    }

    @Test
    public void testGetImageUrl_withoutFile() {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem = TestUtil.createMenuItem(pageVersion.getPage().getPageId(), menu, true);

        final MenuImage menuImage = new MenuImage();
        ServiceLocator.getPersistance().putMenuImage(menuImage);
        menuItem.setImageId(menuImage.getMenuImageId());

        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());
        Assert.assertEquals("", manager.getImageUrl());
    }

    @Test
    public void testIsImageExist() {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem = TestUtil.createMenuItem(pageVersion.getPage().getPageId(), menu, true);

        final MenuImage menuImage = TestUtil.createMenuImage(site.getSiteId());
        menuItem.setImageId(menuImage.getMenuImageId());

        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());
        Assert.assertTrue(manager.isImageExist());
    }

    @Test
    public void testIsImageExist_withoutImageId() {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem = TestUtil.createMenuItem(pageVersion.getPage().getPageId(), menu, true);

        TestUtil.createMenuImage(site.getSiteId());
        menuItem.setImageId(null);

        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());
        Assert.assertFalse(manager.isImageExist());
    }

    @Test
    public void testIsImageExist_withoutFile() {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem = TestUtil.createMenuItem(pageVersion.getPage().getPageId(), menu, true);

        final MenuImage menuImage = new MenuImage();
        ServiceLocator.getPersistance().putMenuImage(menuImage);
        menuItem.setImageId(menuImage.getMenuImageId());

        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());
        Assert.assertFalse(manager.isImageExist());
    }   

    /*----------------------------------------------------Get Url-----------------------------------------------------*/

    @Test
    public void testGetUrl_withUrlTypeSITE_PAGE() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager draftVersion = TestUtil.createPageVersion(page, PageVersionType.DRAFT);
        draftVersion.setName("pageVersionNameDraft");
        draftVersion.setUrl("pageVersionUrlDraft");

        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem = TestUtil.createMenuItem(page.getPageId(), menu, true);
        menuItem.setName("menuItemName");
        menuItem.setUrlType(MenuUrlType.SITE_PAGE);


        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());


        Assert.assertEquals("showPageVersion.action?pageId=" + draftVersion.getPageId() +
                "&siteShowOption=ON_USER_PAGES", manager.getUrl());
    }

    @Test
    public void testGetUrl_withUrlTypeCUSTOM_URL() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager draftVersion = TestUtil.createPageVersion(page, PageVersionType.DRAFT);
        draftVersion.setName("pageVersionNameDraft");
        draftVersion.setUrl("pageVersionUrlDraft");

        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem = TestUtil.createMenuItem(page.getPageId(), menu, true);
        menuItem.setName("menuItemName");
        menuItem.setUrlType(MenuUrlType.CUSTOM_URL);
        menuItem.setCustomUrl("customUrl");


        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());


        Assert.assertEquals("http://customUrl", manager.getUrl());
    }

    @Test
    public void testGetUrl_withUrlTypeCUSTOM_URL_withHttpPrefix() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager draftVersion = TestUtil.createPageVersion(page, PageVersionType.DRAFT);
        draftVersion.setName("pageVersionNameDraft");
        draftVersion.setUrl("pageVersionUrlDraft");

        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem = TestUtil.createMenuItem(page.getPageId(), menu, true);
        menuItem.setName("menuItemName");
        menuItem.setUrlType(MenuUrlType.CUSTOM_URL);
        menuItem.setCustomUrl("http://customUrl");


        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());


        Assert.assertEquals("http://customUrl", manager.getUrl());
    }

    @Test
    public void testGetUrl_withUrlTypeCUSTOM_URL_withHttpsPrefix() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager draftVersion = TestUtil.createPageVersion(page, PageVersionType.DRAFT);
        draftVersion.setName("pageVersionNameDraft");
        draftVersion.setUrl("pageVersionUrlDraft");

        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem = TestUtil.createMenuItem(page.getPageId(), menu, true);
        menuItem.setName("menuItemName");
        menuItem.setUrlType(MenuUrlType.CUSTOM_URL);
        menuItem.setCustomUrl("https://customUrl");


        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());


        Assert.assertEquals("https://customUrl", manager.getUrl());
    }

    @Test
    public void testGetUrl_withUrlTypeCUSTOM_URL_withFtpsPrefix() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager draftVersion = TestUtil.createPageVersion(page, PageVersionType.DRAFT);
        draftVersion.setName("pageVersionNameDraft");
        draftVersion.setUrl("pageVersionUrlDraft");

        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem = TestUtil.createMenuItem(page.getPageId(), menu, true);
        menuItem.setName("menuItemName");
        menuItem.setUrlType(MenuUrlType.CUSTOM_URL);
        menuItem.setCustomUrl("ftp://customUrl");


        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());


        Assert.assertEquals("ftp://customUrl", manager.getUrl());
    }
    /*----------------------------------------------------Get Url-----------------------------------------------------*/

    /*----------------------------------------------Create MenuItemData-----------------------------------------------*/

    @Test
    public void testCreateMenuItemData_CUSTOM_URL() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager draftVersion = TestUtil.createPageVersion(page, PageVersionType.DRAFT);
        draftVersion.setName("pageVersionNameDraft");
        draftVersion.setUrl("pageVersionUrlDraft");

        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem = TestUtil.createMenuItem(page.getPageId(), menu, true);
        menuItem.setName("menuItemName");
        menuItem.setUrlType(MenuUrlType.CUSTOM_URL);
        menuItem.setCustomUrl("customUrl");
        menuItem.setTitle("title");


        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());
        final MenuItemData data = manager.createMenuItemData(1, 1);

        Assert.assertEquals("http://customUrl", data.getHref());
        Assert.assertEquals("menuItemName", data.getName());
        Assert.assertEquals(1, data.getLevel());
        Assert.assertEquals("title", data.getDescription());
        Assert.assertEquals(false, data.isSelected());
        Assert.assertEquals(true, data.isExternalUrl());
        Assert.assertEquals(false, data.isShowImage());
        Assert.assertEquals("", data.getImageUrl());
        Assert.assertEquals(false, data.isLastRight());
        Assert.assertEquals(false, data.isLastBottom());
    }


    @Test
    public void testCreateMenuItemData_SITE_PAGE_OUTSIDE_APP() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = TestUtil.createPageVersion(page, PageVersionType.WORK);
        pageVersion.getWorkPageSettings().setName("pageVersionNameDraft");
        pageVersion.getWorkPageSettings().setTitle("pageVersionTitle");
        pageVersion.getWorkPageSettings().setUrl("pageVersionUrlDraft");

        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem = TestUtil.createMenuItem(page.getPageId(), menu, true);
        menuItem.setUrlType(MenuUrlType.SITE_PAGE);
        menuItem.setCustomUrl("customUrl");


        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getWorkOption());
        final MenuItemData data = manager.createMenuItemData(page.getPageId(), 1);

        Assert.assertEquals("javascript:void(0);", data.getHref());
        Assert.assertEquals("pageVersionNameDraft", data.getName());
        Assert.assertEquals(1, data.getLevel());
        Assert.assertEquals("pageVersionTitle", data.getDescription());
        Assert.assertEquals(true, data.isSelected());
        Assert.assertEquals(false, data.isExternalUrl());
        Assert.assertEquals(false, data.isShowImage());
        Assert.assertEquals("", data.getImageUrl());
        Assert.assertEquals(false, data.isLastRight());
        Assert.assertEquals(false, data.isLastBottom());
    }


    @Test
    public void testCreateMenuItemData_SITE_PAGE_INSIDE_APP() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager draftVersion = TestUtil.createPageVersion(page, PageVersionType.DRAFT);
        draftVersion.setName("pageVersionNameDraft");
        draftVersion.setTitle("pageVersionTitle");
        draftVersion.setUrl("pageVersionUrlDraft");

        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem = TestUtil.createMenuItem(page.getPageId(), menu, true);
        menuItem.setUrlType(MenuUrlType.SITE_PAGE);
        menuItem.setCustomUrl("customUrl");


        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());
        final MenuItemData data = manager.createMenuItemData(page.getPageId(), 1);

        Assert.assertEquals("javascript:void(0);", data.getHref());
        Assert.assertEquals("pageVersionNameDraft", data.getName());
        Assert.assertEquals(1, data.getLevel());
        Assert.assertEquals("pageVersionTitle", data.getDescription());
        Assert.assertEquals(true, data.isSelected());
        Assert.assertEquals(false, data.isExternalUrl());
        Assert.assertEquals(false, data.isShowImage());
        Assert.assertEquals("", data.getImageUrl());
        Assert.assertEquals(false, data.isLastRight());
        Assert.assertEquals(false, data.isLastBottom());
    }


    @Test
    public void testCreateMenuItemData_SITE_PAGE_OUTSIDE_APP_notSelected() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = TestUtil.createPageVersion(page, PageVersionType.WORK);
        pageVersion.getWorkPageSettings().setName("pageVersionNameDraft");
        pageVersion.getWorkPageSettings().setTitle("pageVersionTitle");
        pageVersion.getWorkPageSettings().setUrl("pageVersionUrlDraft");

        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem = TestUtil.createMenuItem(page.getPageId(), menu, true);
        menuItem.setUrlType(MenuUrlType.SITE_PAGE);
        menuItem.setCustomUrl("customUrl");


        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getWorkOption());
        final MenuItemData data = manager.createMenuItemData(page.getPageId(), 1);
        data.setSelected(false);

        Assert.assertEquals("/pageVersionUrlDraft", data.getHref());
        Assert.assertEquals("pageVersionNameDraft", data.getName());
        Assert.assertEquals(1, data.getLevel());
        Assert.assertEquals("pageVersionTitle", data.getDescription());
        Assert.assertEquals(false, data.isSelected());
        Assert.assertEquals(false, data.isExternalUrl());
        Assert.assertEquals(false, data.isShowImage());
        Assert.assertEquals("", data.getImageUrl());
        Assert.assertEquals(false, data.isLastRight());
        Assert.assertEquals(false, data.isLastBottom());
    }


    @Test
    public void testCreateMenuItemData_SITE_PAGE_INSIDE_APP_notSelected() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager draftVersion = TestUtil.createPageVersion(page, PageVersionType.DRAFT);
        draftVersion.setName("pageVersionNameDraft");
        draftVersion.setTitle("pageVersionTitle");
        draftVersion.setUrl("pageVersionUrlDraft");

        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem = TestUtil.createMenuItem(page.getPageId(), menu, true);
        menuItem.setUrlType(MenuUrlType.SITE_PAGE);
        menuItem.setCustomUrl("customUrl");


        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());
        final MenuItemData data = manager.createMenuItemData(page.getPageId(), 1);
        data.setSelected(false);

        Assert.assertEquals("showPageVersion.action?pageId=" + draftVersion.getPageId()
                + "&siteShowOption=ON_USER_PAGES", data.getHref());
        Assert.assertEquals("pageVersionNameDraft", data.getName());
        Assert.assertEquals(1, data.getLevel());
        Assert.assertEquals("pageVersionTitle", data.getDescription());
        Assert.assertEquals(false, data.isSelected());
        Assert.assertEquals(false, data.isExternalUrl());
        Assert.assertEquals(false, data.isShowImage());
        Assert.assertEquals("", data.getImageUrl());
        Assert.assertEquals(false, data.isLastRight());
        Assert.assertEquals(false, data.isLastBottom());
    }
    /*----------------------------------------------Create MenuItemData-----------------------------------------------*/
}
