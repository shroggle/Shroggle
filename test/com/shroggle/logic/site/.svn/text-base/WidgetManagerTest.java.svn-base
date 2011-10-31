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
package com.shroggle.logic.site;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.exception.WidgetRightsNotFoundException;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.site.cssParameter.CreateFontsAndColorsRequest;
import com.shroggle.presentation.site.cssParameter.CssParameter;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class WidgetManagerTest {

    @Test
    public void executeFromPage() {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Widget widget1 = TestUtil.createWidgetItem();
        widget1.setPosition(4);
        persistance.putWidget(widget1);
        pageVersion.addWidget(widget1);

        Widget widget2 = TestUtil.createWidgetItem();
        widget2.setPosition(2);
        persistance.putWidget(widget2);
        pageVersion.addWidget(widget2);

        final WidgetManager widgetManager = new WidgetManager(widget1.getWidgetId());
        widgetManager.remove();

        final List<Widget> widgetsOfPage = new PageManager(pageVersion.getPage()).getWidgets();
        Assert.assertEquals(1, widgetsOfPage.size());
        Assert.assertEquals(2, widget2.getPosition());
        Assert.assertEquals(widget2, widgetsOfPage.get(0));
    }

    @Test
    public void executeFromComposit() {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        WidgetComposit widgetComposit = new WidgetComposit();
        widgetComposit.setPosition(2);
        persistance.putWidget(widgetComposit);
        pageVersion.addWidget(widgetComposit);


        Widget widget1 = TestUtil.createWidgetItem();
        widget1.setPosition(1);
        widgetComposit.addChild(widget1);
        persistance.putWidget(widget1);
        pageVersion.addWidget(widget1);


        Widget widget2 = TestUtil.createWidgetItem();
        widget2.setPosition(0);
        widgetComposit.addChild(widget2);
        persistance.putWidget(widget2);
        pageVersion.addWidget(widget2);


        final WidgetManager widgetManager = new WidgetManager(widget1.getWidgetId());
        widgetManager.remove();

        final List<Widget> widgetsOfPage = pageVersion.getWidgets();
        Assert.assertEquals(1, widgetComposit.getChilds().size());
        Assert.assertEquals(widget2, widgetComposit.getChilds().get(0));
        Assert.assertEquals(0, widget2.getPosition());
        Assert.assertEquals(2, widgetsOfPage.size());
        Assert.assertEquals(widgetComposit, widgetsOfPage.get(0));
        Assert.assertEquals(widget2, widgetsOfPage.get(1));
    }

    @Test(expected = WidgetRightsNotFoundException.class)
    public void executeWithNotMy() {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserAndLogin();

        Widget widget1 = TestUtil.createWidgetItem();
        widget1.setPosition(0);
        persistance.putWidget(widget1);
        pageVersion.addWidget(widget1);


        Widget widget2 = TestUtil.createWidgetItem();
        widget2.setPosition(1);
        persistance.putWidget(widget2);
        pageVersion.addWidget(widget2);


        final WidgetManager widgetManager = new WidgetManager(widget1.getWidgetId());
        widgetManager.remove();
    }

    @Test(expected = WidgetNotFoundException.class)
    public void executeWithNotFound() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final WidgetManager widgetManager = new WidgetManager(-1);
        widgetManager.remove();
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() {
        final WidgetManager widgetManager = new WidgetManager(-1);
        widgetManager.remove();
    }

    /*----------------------------------------------------ItemSize----------------------------------------------------*/

    @Test
    public void testGetItemSize_ItemSizeInItem() throws Exception {
        TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        final DraftItem draftItem = TestUtil.createBlog(site);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(TestUtil.createPageVersion(TestUtil.createPage(site)));
        widgetItem.setDraftItem(draftItem);
        widgetItem.setItemSizeId(null);
        draftItem.setItemSizeId(TestUtil.createItemSize().getId());

        final WidgetManager widgetManager = new WidgetManager(widgetItem);
        Assert.assertEquals(draftItem.getItemSizeId().intValue(), widgetManager.getItemSize(SiteShowOption.getDraftOption()).getId());
    }

    @Test
    public void testGetItemSize_ItemSizeInWidget() throws Exception {
        TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        final DraftItem draftItem = TestUtil.createBlog(site);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(TestUtil.createPageVersion(TestUtil.createPage(site)));
        widgetItem.setDraftItem(draftItem);
        draftItem.setItemSizeId(TestUtil.createItemSize().getId());

        final ItemSize itemSize = TestUtil.createItemSize();
        itemSize.setCreateClearDiv(false);
        itemSize.setWidth(1111111111);
        widgetItem.setItemSizeId(itemSize.getId());


        final WidgetManager widgetManager = new WidgetManager(widgetItem);
        Assert.assertEquals(itemSize, widgetManager.getItemSize(SiteShowOption.getDraftOption()));
        Assert.assertNotSame(draftItem.getItemSizeId(), widgetManager.getItemSize(SiteShowOption.getDraftOption()).getId());
    }

    @Test
    public void testSetItemSizeInCurrentPlace_withItemSizeInWidget() throws Exception {
        TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        final DraftItem draftItem = TestUtil.createBlog(site);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(TestUtil.createPageVersion(TestUtil.createPage(site)));
        widgetItem.setDraftItem(draftItem);
        draftItem.setItemSizeId(TestUtil.createItemSize().getId());

        final ItemSize itemSize = TestUtil.createItemSize();
        itemSize.setCreateClearDiv(false);
        itemSize.setWidth(1111111111);
        widgetItem.setItemSizeId(itemSize.getId());
        final int oldItemSizeId = itemSize.getId();


        final WidgetManager widgetManager = new WidgetManager(widgetItem);


        final ItemSize tempItemSize = TestUtil.createItemSize();
        tempItemSize.setWidth(22222222);
        tempItemSize.setHeight(33333333);
        widgetManager.setItemSizeInCurrentPlace(tempItemSize);


        Assert.assertNotSame(tempItemSize.getId(), persistance.getItemSize(widgetItem.getItemSizeId()).getId());
        Assert.assertEquals(oldItemSizeId, persistance.getItemSize(widgetItem.getItemSizeId()).getId());
        Assert.assertEquals(tempItemSize.getWidth(), persistance.getItemSize(widgetItem.getItemSizeId()).getWidth());
        Assert.assertEquals(tempItemSize.getHeight(), persistance.getItemSize(widgetItem.getItemSizeId()).getHeight());
        Assert.assertEquals(tempItemSize.getHeightSizeType(), persistance.getItemSize(widgetItem.getItemSizeId()).getHeightSizeType());
        Assert.assertEquals(tempItemSize.getWidthSizeType(), persistance.getItemSize(widgetItem.getItemSizeId()).getWidthSizeType());
        Assert.assertEquals(tempItemSize.getOverflow_x(), persistance.getItemSize(widgetItem.getItemSizeId()).getOverflow_x());
        Assert.assertEquals(tempItemSize.getOverflow_y(), persistance.getItemSize(widgetItem.getItemSizeId()).getOverflow_y());
        Assert.assertEquals(tempItemSize.isCreateClearDiv(), persistance.getItemSize(widgetItem.getItemSizeId()).isCreateClearDiv());
        Assert.assertEquals(tempItemSize.isFloatable(), persistance.getItemSize(widgetItem.getItemSizeId()).isFloatable());
    }

    @Test
    public void testSetItemSizeInCurrentPlace_withoutItemSizeInWidget() throws Exception {
        TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        final DraftItem draftItem = TestUtil.createBlog(site);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(TestUtil.createPageVersion(TestUtil.createPage(site)));
        widgetItem.setDraftItem(draftItem);
        draftItem.setItemSizeId(TestUtil.createItemSize().getId());
        widgetItem.setItemSizeId(null);


        final WidgetManager widgetManager = new WidgetManager(widgetItem);


        final ItemSize tempItemSize = TestUtil.createItemSize();
        tempItemSize.setWidth(22222222);
        tempItemSize.setHeight(33333333);

        widgetManager.setItemSizeInCurrentPlace(tempItemSize);


        Assert.assertNotSame(tempItemSize.getId(), persistance.getItemSize(widgetItem.getItemSizeId()).getId());
        Assert.assertNotNull(persistance.getItemSize(widgetItem.getItemSizeId()).getId());
        Assert.assertEquals(tempItemSize.getWidth(), persistance.getItemSize(widgetItem.getItemSizeId()).getWidth());
        Assert.assertEquals(tempItemSize.getHeight(), persistance.getItemSize(widgetItem.getItemSizeId()).getHeight());
        Assert.assertEquals(tempItemSize.getHeightSizeType(), persistance.getItemSize(widgetItem.getItemSizeId()).getHeightSizeType());
        Assert.assertEquals(tempItemSize.getWidthSizeType(), persistance.getItemSize(widgetItem.getItemSizeId()).getWidthSizeType());
        Assert.assertEquals(tempItemSize.getOverflow_x(), persistance.getItemSize(widgetItem.getItemSizeId()).getOverflow_x());
        Assert.assertEquals(tempItemSize.getOverflow_y(), persistance.getItemSize(widgetItem.getItemSizeId()).getOverflow_y());
        Assert.assertEquals(tempItemSize.isCreateClearDiv(), persistance.getItemSize(widgetItem.getItemSizeId()).isCreateClearDiv());
        Assert.assertEquals(tempItemSize.isFloatable(), persistance.getItemSize(widgetItem.getItemSizeId()).isFloatable());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSetItemSizeInAllPlaces_forWidgetComposit() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final Widget widgetComposit = new WidgetComposit();
        widgetComposit.setDraftPageSettings((DraftPageSettings) TestUtil.createPageSettings(TestUtil.createPage(site)));

        final WidgetManager widgetManager = new WidgetManager(widgetComposit);
        widgetManager.setItemSizeInAllPlaces(null);
    }

    @Test
    public void testSetItemSizeInAllPlaces_forOwner() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final DraftItem draftItem = TestUtil.createBlog(site);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(TestUtil.createPageVersion(TestUtil.createPage(site)));
        widgetItem.setDraftItem(draftItem);
        draftItem.setItemSizeId(TestUtil.createItemSize().getId());

        final ItemSize itemSize = TestUtil.createItemSize();
        itemSize.setCreateClearDiv(false);
        itemSize.setWidth(1111111111);
        widgetItem.setItemSizeId(itemSize.getId());
        final int oldItemSizeId = draftItem.getItemSizeId();


        final WidgetManager widgetManager = new WidgetManager(widgetItem);


        final ItemSize tempItemSize = TestUtil.createItemSize();
        tempItemSize.setWidth(22222222);
        tempItemSize.setHeight(33333333);
        widgetManager.setItemSizeInAllPlaces(tempItemSize);


        Assert.assertEquals(null, persistance.getItemSize(widgetItem.getItemSizeId()));
        Assert.assertEquals(oldItemSizeId, persistance.getItemSize(draftItem.getItemSizeId()).getId());
        Assert.assertEquals(tempItemSize.getWidth(), persistance.getItemSize(draftItem.getItemSizeId()).getWidth());
        Assert.assertEquals(tempItemSize.getHeight(), persistance.getItemSize(draftItem.getItemSizeId()).getHeight());
        Assert.assertEquals(tempItemSize.getHeightSizeType(), persistance.getItemSize(draftItem.getItemSizeId()).getHeightSizeType());
        Assert.assertEquals(tempItemSize.getWidthSizeType(), persistance.getItemSize(draftItem.getItemSizeId()).getWidthSizeType());
        Assert.assertEquals(tempItemSize.getOverflow_x(), persistance.getItemSize(draftItem.getItemSizeId()).getOverflow_x());
        Assert.assertEquals(tempItemSize.getOverflow_y(), persistance.getItemSize(draftItem.getItemSizeId()).getOverflow_y());
        Assert.assertEquals(tempItemSize.isCreateClearDiv(), persistance.getItemSize(draftItem.getItemSizeId()).isCreateClearDiv());
        Assert.assertEquals(tempItemSize.isFloatable(), persistance.getItemSize(draftItem.getItemSizeId()).isFloatable());
    }

    @Test
    public void testSetItemSizeInAllPlaces_withoutRights() throws Exception {
        TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        final DraftItem draftItem = TestUtil.createBlog(site);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(TestUtil.createPageVersion(TestUtil.createPage(site)));
        widgetItem.setDraftItem(draftItem);
        draftItem.setItemSizeId(TestUtil.createItemSize().getId());

        final ItemSize itemSize = TestUtil.createItemSize();
        itemSize.setCreateClearDiv(false);
        itemSize.setWidth(1111111111);
        widgetItem.setItemSizeId(itemSize.getId());
        final int oldItemSizeId = itemSize.getId();


        final WidgetManager widgetManager = new WidgetManager(widgetItem);


        final ItemSize tempItemSize = TestUtil.createItemSize();
        tempItemSize.setWidth(22222222);
        tempItemSize.setHeight(33333333);
        widgetManager.setItemSizeInAllPlaces(tempItemSize);


        Assert.assertNotSame(tempItemSize.getId(), persistance.getItemSize(widgetItem.getItemSizeId()).getId());
        Assert.assertEquals(oldItemSizeId, persistance.getItemSize(widgetItem.getItemSizeId()).getId());
        Assert.assertEquals(tempItemSize.getWidth(), persistance.getItemSize(widgetItem.getItemSizeId()).getWidth());
        Assert.assertEquals(tempItemSize.getHeight(), persistance.getItemSize(widgetItem.getItemSizeId()).getHeight());
        Assert.assertEquals(tempItemSize.getHeightSizeType(), persistance.getItemSize(widgetItem.getItemSizeId()).getHeightSizeType());
        Assert.assertEquals(tempItemSize.getWidthSizeType(), persistance.getItemSize(widgetItem.getItemSizeId()).getWidthSizeType());
        Assert.assertEquals(tempItemSize.getOverflow_x(), persistance.getItemSize(widgetItem.getItemSizeId()).getOverflow_x());
        Assert.assertEquals(tempItemSize.getOverflow_y(), persistance.getItemSize(widgetItem.getItemSizeId()).getOverflow_y());
        Assert.assertEquals(tempItemSize.isCreateClearDiv(), persistance.getItemSize(widgetItem.getItemSizeId()).isCreateClearDiv());
        Assert.assertEquals(tempItemSize.isFloatable(), persistance.getItemSize(widgetItem.getItemSizeId()).isFloatable());
    }
    /*----------------------------------------------------ItemSize----------------------------------------------------*/

    /*-----------------------------------------------AccessibleSettings-----------------------------------------------*/

    @Test
    public void testGetAccessibleSettings_AccessibleSettingsInItem() throws Exception {
        TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        final DraftItem draftItem = TestUtil.createBlog(site);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(TestUtil.createPageVersion(TestUtil.createPage(site)));
        widgetItem.setDraftItem(draftItem);
        WidgetManager widgetManager = new WidgetManager(widgetItem);
        widgetManager.setAccessibleSettings(null);
        
        final ItemManager itemManager = new ItemManager(draftItem);
        itemManager.setAccessibleSettings(TestUtil.createAccessibleSettings());

        Assert.assertEquals(itemManager.getAccessibleSettings(SiteShowOption.getDraftOption()), widgetManager.getAccessibleSettings());
    }

    @Test
    public void testGetAccessibleSettings_AccessibleSettingsInWidget() throws Exception {
        TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        final DraftItem draftItem = TestUtil.createBlog(site);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(TestUtil.createPageVersion(TestUtil.createPage(site)));
        widgetItem.setDraftItem(draftItem);
        
        final ItemManager itemManager = new ItemManager(draftItem);
        itemManager.setAccessibleSettings(TestUtil.createAccessibleSettings());

        final AccessibleSettings accessibleSettings = TestUtil.createAccessibleSettings();
        accessibleSettings.setAdministrators(false);
        
        final WidgetManager widgetManager = new WidgetManager(widgetItem);
        widgetManager.setAccessibleSettings(accessibleSettings);


        Assert.assertEquals(accessibleSettings, widgetManager.getAccessibleSettings());
        Assert.assertNotSame(itemManager.getAccessibleSettings(SiteShowOption.getDraftOption()), widgetManager.getAccessibleSettings());
    }

    @Test
    public void testSetAccessibleSettingsInCurrentPlace_withAccessibleSettingsInWidget() throws Exception {
        TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        final DraftItem draftItem = TestUtil.createBlog(site);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(TestUtil.createPageVersion(TestUtil.createPage(site)));
        widgetItem.setDraftItem(draftItem);
        
        final ItemManager itemManager = new ItemManager(draftItem);
        itemManager.setAccessibleSettings(TestUtil.createAccessibleSettings());

        final AccessibleSettings accessibleSettings = TestUtil.createAccessibleSettings();
        accessibleSettings.setAdministrators(false);
        
        final WidgetManager widgetManager = new WidgetManager(widgetItem);
        widgetManager.setAccessibleSettings(accessibleSettings);
        final int oldAccessibleSettingsId = accessibleSettings.getAccessibleSettingsId();

        


        final AccessibleSettings tempAccessibleSettings = TestUtil.createAccessibleSettings();
        tempAccessibleSettings.setVisitors(false);
        widgetManager.setAccessibleSettingsInCurrentPlace(tempAccessibleSettings);


        Assert.assertNotSame(tempAccessibleSettings.getAccessibleSettingsId(), widgetManager.getAccessibleSettings().getAccessibleSettingsId());
        Assert.assertEquals(oldAccessibleSettingsId, widgetManager.getAccessibleSettings().getAccessibleSettingsId());
        Assert.assertEquals(tempAccessibleSettings.getAccess(), widgetManager.getAccessibleSettings().getAccess());
        Assert.assertEquals(tempAccessibleSettings.getVisitorsGroups(), widgetManager.getAccessibleSettings().getVisitorsGroups());
        Assert.assertEquals(tempAccessibleSettings.isAdministrators(), widgetManager.getAccessibleSettings().isAdministrators());
        Assert.assertEquals(tempAccessibleSettings.isVisitors(), widgetManager.getAccessibleSettings().isVisitors());
    }

    @Test
    public void testSetAccessibleSettingsInCurrentPlace_withoutAccessibleSettingsInWidget() throws Exception {
        TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        final DraftItem draftItem = TestUtil.createBlog(site);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(TestUtil.createPageVersion(TestUtil.createPage(site)));
        widgetItem.setDraftItem(draftItem);
        
        final ItemManager itemManager = new ItemManager(draftItem);
        itemManager.setAccessibleSettings(TestUtil.createAccessibleSettings());
        
        final WidgetManager widgetManager = new WidgetManager(widgetItem);
        widgetManager.setAccessibleSettings(null);



        final AccessibleSettings tempAccessibleSettings = TestUtil.createAccessibleSettings();
        tempAccessibleSettings.setVisitors(false);

        widgetManager.setAccessibleSettingsInCurrentPlace(tempAccessibleSettings);


        Assert.assertNotSame(tempAccessibleSettings.getAccessibleSettingsId(), widgetManager.getAccessibleSettings().getAccessibleSettingsId());
        Assert.assertNotNull(widgetManager.getAccessibleSettings().getAccessibleSettingsId());
        Assert.assertEquals(tempAccessibleSettings.getAccess(), widgetManager.getAccessibleSettings().getAccess());
        Assert.assertEquals(tempAccessibleSettings.getVisitorsGroups(), widgetManager.getAccessibleSettings().getVisitorsGroups());
        Assert.assertEquals(tempAccessibleSettings.isAdministrators(), widgetManager.getAccessibleSettings().isAdministrators());
        Assert.assertEquals(tempAccessibleSettings.isVisitors(), widgetManager.getAccessibleSettings().isVisitors());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSetAccessibleSettingsInAllPlaces_forWidgetComposit() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final Widget widgetComposit = new WidgetComposit();
        widgetComposit.setDraftPageSettings((DraftPageSettings) TestUtil.createPageSettings(TestUtil.createPage(site)));

        final WidgetManager widgetManager = new WidgetManager(widgetComposit);
        widgetManager.setAccessibleSettingsInAllPlaces(null);
    }

    @Test
    public void testSetAccessibleSettingsInAllPlaces_forOwner() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final DraftItem draftItem = TestUtil.createBlog(site);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(TestUtil.createPageVersion(TestUtil.createPage(site)));
        widgetItem.setDraftItem(draftItem);
        
        final ItemManager itemManager = new ItemManager(draftItem);
        itemManager.setAccessibleSettings(TestUtil.createAccessibleSettings());

        final AccessibleSettings accessibleSettings = TestUtil.createAccessibleSettings();
        accessibleSettings.setAdministrators(false);
        
        final WidgetManager widgetManager = new WidgetManager(widgetItem);
        widgetManager.setAccessibleSettings(accessibleSettings);
        final int oldAccessibleSettingsId = draftItem.getAccessibleSettingsId();


        final AccessibleSettings tempAccessibleSettings = TestUtil.createAccessibleSettings();
        tempAccessibleSettings.setVisitors(false);
        widgetManager.setAccessibleSettingsInAllPlaces(tempAccessibleSettings);


        Assert.assertEquals(null, widgetItem.getAccessibleSettingsId());
        Assert.assertEquals(oldAccessibleSettingsId, itemManager.getAccessibleSettings(SiteShowOption.getDraftOption()).getAccessibleSettingsId());
        Assert.assertEquals(tempAccessibleSettings.getAccess(), itemManager.getAccessibleSettings(SiteShowOption.getDraftOption()).getAccess());
        Assert.assertEquals(tempAccessibleSettings.getVisitorsGroups(), itemManager.getAccessibleSettings(SiteShowOption.getDraftOption()).getVisitorsGroups());
        Assert.assertEquals(tempAccessibleSettings.isAdministrators(), itemManager.getAccessibleSettings(SiteShowOption.getDraftOption()).isAdministrators());
        Assert.assertEquals(tempAccessibleSettings.isVisitors(), itemManager.getAccessibleSettings(SiteShowOption.getDraftOption()).isVisitors());
    }

    @Test
    public void testSetAccessibleSettingsInAllPlaces_withoutRights() throws Exception {
        TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        final DraftItem draftItem = TestUtil.createBlog(site);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(TestUtil.createPageVersion(TestUtil.createPage(site)));
        widgetItem.setDraftItem(draftItem);
        
        final ItemManager itemManager = new ItemManager(draftItem);
        itemManager.setAccessibleSettings(TestUtil.createAccessibleSettings());

        final AccessibleSettings accessibleSettings = TestUtil.createAccessibleSettings();
        accessibleSettings.setAdministrators(false);
        
        final WidgetManager widgetManager = new WidgetManager(widgetItem);
        widgetManager.setAccessibleSettings(accessibleSettings);
        final int oldAccessibleSettingsId = accessibleSettings.getAccessibleSettingsId();


        final AccessibleSettings tempAccessibleSettings = TestUtil.createAccessibleSettings();
        tempAccessibleSettings.setVisitors(false);
        widgetManager.setAccessibleSettingsInAllPlaces(tempAccessibleSettings);


        Assert.assertNotSame(tempAccessibleSettings.getAccessibleSettingsId(), widgetManager.getAccessibleSettings().getAccessibleSettingsId());
        Assert.assertEquals(oldAccessibleSettingsId, widgetManager.getAccessibleSettings().getAccessibleSettingsId());
        Assert.assertEquals(tempAccessibleSettings.getAccess(), widgetManager.getAccessibleSettings().getAccess());
        Assert.assertEquals(tempAccessibleSettings.getVisitorsGroups(), widgetManager.getAccessibleSettings().getVisitorsGroups());
        Assert.assertEquals(tempAccessibleSettings.isAdministrators(), widgetManager.getAccessibleSettings().isAdministrators());
        Assert.assertEquals(tempAccessibleSettings.isVisitors(), widgetManager.getAccessibleSettings().isVisitors());
    }
    /*-----------------------------------------------AccessibleSettings-----------------------------------------------*/

    /*-----------------------------------------------Background-----------------------------------------------*/

    @Test
    public void testGetBackground_BackgroundInItem() throws Exception {
        TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        final DraftItem draftItem = TestUtil.createBlog(site);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(TestUtil.createPageVersion(TestUtil.createPage(site)));
        widgetItem.setDraftItem(draftItem);
        WidgetManager widgetManager = new WidgetManager(widgetItem);
        widgetItem.setBackgroundId(null);
        
        final ItemManager itemManager = new ItemManager(draftItem);
        itemManager.setBackground(TestUtil.createBackground());

        Assert.assertEquals(itemManager.getBackground(SiteShowOption.getDraftOption()), widgetManager.getBackground(SiteShowOption.getDraftOption()));
    }

    @Test
    public void testGetBackground_BackgroundInWidget() throws Exception {
        TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        final DraftItem draftItem = TestUtil.createBlog(site);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(TestUtil.createPageVersion(TestUtil.createPage(site)));
        widgetItem.setDraftItem(draftItem);
        
        final ItemManager itemManager = new ItemManager(draftItem);
        itemManager.setBackground(TestUtil.createBackground());

        final Background background = TestUtil.createBackground();
        background.setBackgroundImageId(123123);
        
        final WidgetManager widgetManager = new WidgetManager(widgetItem);
        widgetItem.setBackgroundId(background.getId());


        Assert.assertEquals(background, widgetManager.getBackground(SiteShowOption.getDraftOption()));
        Assert.assertNotSame(itemManager.getBackground(SiteShowOption.getDraftOption()), widgetManager.getBackground(SiteShowOption.getDraftOption()));
    }

    @Test
    public void testSetBackgroundInCurrentPlace_withBackgroundInWidget() throws Exception {
        TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        final DraftItem draftItem = TestUtil.createBlog(site);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(TestUtil.createPageVersion(TestUtil.createPage(site)));
        widgetItem.setDraftItem(draftItem);
        
        final ItemManager itemManager = new ItemManager(draftItem);
        itemManager.setBackground(TestUtil.createBackground());

        final Background background = TestUtil.createBackground();
        background.setBackgroundImageId(123123);
        
        final WidgetManager widgetManager = new WidgetManager(widgetItem);
        widgetItem.setBackgroundId(background.getId());
        final int oldBackgroundId = background.getId();        


        final Background tempBackground = TestUtil.createBackground();
        tempBackground.setBackgroundImageId(1);
        widgetManager.setBackgroundInCurrentPlace(tempBackground);


        Assert.assertNotSame(tempBackground.getId(), widgetManager.getBackground(SiteShowOption.getDraftOption()).getId());
        Assert.assertEquals(oldBackgroundId, widgetManager.getBackground(SiteShowOption.getDraftOption()).getId());
        Assert.assertEquals(tempBackground.getBackgroundRepeat(), widgetManager.getBackground(SiteShowOption.getDraftOption()).getBackgroundRepeat());
        Assert.assertEquals(tempBackground.getBackgroundColor(), widgetManager.getBackground(SiteShowOption.getDraftOption()).getBackgroundColor());
        Assert.assertEquals(tempBackground.getBackgroundImageId(), widgetManager.getBackground(SiteShowOption.getDraftOption()).getBackgroundImageId());
        Assert.assertEquals(tempBackground.getBackgroundPosition(), widgetManager.getBackground(SiteShowOption.getDraftOption()).getBackgroundPosition());
    }

    @Test
    public void testSetBackgroundInCurrentPlace_withoutBackgroundInWidget() throws Exception {
        TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        final DraftItem draftItem = TestUtil.createBlog(site);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(TestUtil.createPageVersion(TestUtil.createPage(site)));
        widgetItem.setDraftItem(draftItem);
        
        final ItemManager itemManager = new ItemManager(draftItem);
        itemManager.setBackground(TestUtil.createBackground());
        
        final WidgetManager widgetManager = new WidgetManager(widgetItem);
        widgetItem.setBackgroundId(null);



        final Background tempBackground = TestUtil.createBackground();
        tempBackground.setBackgroundImageId(1);

        widgetManager.setBackgroundInCurrentPlace(tempBackground);


        Assert.assertNotSame(tempBackground.getId(), widgetManager.getBackground(SiteShowOption.getDraftOption()).getId());
        Assert.assertNotNull(widgetManager.getBackground(SiteShowOption.getDraftOption()).getId());
        Assert.assertEquals(tempBackground.getBackgroundRepeat(), widgetManager.getBackground(SiteShowOption.getDraftOption()).getBackgroundRepeat());
        Assert.assertEquals(tempBackground.getBackgroundColor(), widgetManager.getBackground(SiteShowOption.getDraftOption()).getBackgroundColor());
        Assert.assertEquals(tempBackground.getBackgroundImageId(), widgetManager.getBackground(SiteShowOption.getDraftOption()).getBackgroundImageId());
        Assert.assertEquals(tempBackground.getBackgroundPosition(), widgetManager.getBackground(SiteShowOption.getDraftOption()).getBackgroundPosition());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSetBackgroundInAllPlaces_forWidgetComposit() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final Widget widgetComposit = new WidgetComposit();
        widgetComposit.setDraftPageSettings((DraftPageSettings) TestUtil.createPageSettings(TestUtil.createPage(site)));

        final WidgetManager widgetManager = new WidgetManager(widgetComposit);
        widgetManager.setBackgroundInAllPlaces(null);
    }

    @Test
    public void testSetBackgroundInAllPlaces_forOwner() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final DraftItem draftItem = TestUtil.createBlog(site);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(TestUtil.createPageVersion(TestUtil.createPage(site)));
        widgetItem.setDraftItem(draftItem);
        
        final ItemManager itemManager = new ItemManager(draftItem);
        itemManager.setBackground(TestUtil.createBackground());

        final Background background = TestUtil.createBackground();
        background.setBackgroundImageId(123123);
        
        final WidgetManager widgetManager = new WidgetManager(widgetItem);
        widgetItem.setBackgroundId(background.getId());
        final int oldBackgroundId = draftItem.getBackgroundId();


        final Background tempBackground = TestUtil.createBackground();
        tempBackground.setBackgroundImageId(1);
        widgetManager.setBackgroundInAllPlaces(tempBackground);


        Assert.assertEquals(null, widgetItem.getBackgroundId());
        Assert.assertEquals(oldBackgroundId, itemManager.getBackground(SiteShowOption.getDraftOption()).getId());
        Assert.assertEquals(tempBackground.getBackgroundRepeat(), itemManager.getBackground(SiteShowOption.getDraftOption()).getBackgroundRepeat());
        Assert.assertEquals(tempBackground.getBackgroundColor(), itemManager.getBackground(SiteShowOption.getDraftOption()).getBackgroundColor());
        Assert.assertEquals(tempBackground.getBackgroundImageId(), itemManager.getBackground(SiteShowOption.getDraftOption()).getBackgroundImageId());
        Assert.assertEquals(tempBackground.getBackgroundPosition(), itemManager.getBackground(SiteShowOption.getDraftOption()).getBackgroundPosition());
    }

    @Test
    public void testSetBackgroundInAllPlaces_withoutRights() throws Exception {
        TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        final DraftItem draftItem = TestUtil.createBlog(site);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(TestUtil.createPageVersion(TestUtil.createPage(site)));
        widgetItem.setDraftItem(draftItem);
        
        final ItemManager itemManager = new ItemManager(draftItem);
        itemManager.setBackground(TestUtil.createBackground());

        final Background background = TestUtil.createBackground();
        background.setBackgroundImageId(123123);
        
        final WidgetManager widgetManager = new WidgetManager(widgetItem);
        widgetItem.setBackgroundId(background.getId());
        final int oldBackgroundId = background.getId();


        final Background tempBackground = TestUtil.createBackground();
        tempBackground.setBackgroundImageId(1);
        widgetManager.setBackgroundInAllPlaces(tempBackground);


        Assert.assertNotSame(tempBackground.getId(), widgetManager.getBackground(SiteShowOption.getDraftOption()).getId());
        Assert.assertEquals(oldBackgroundId, widgetManager.getBackground(SiteShowOption.getDraftOption()).getId());
        Assert.assertEquals(tempBackground.getBackgroundRepeat(), widgetManager.getBackground(SiteShowOption.getDraftOption()).getBackgroundRepeat());
        Assert.assertEquals(tempBackground.getBackgroundColor(), widgetManager.getBackground(SiteShowOption.getDraftOption()).getBackgroundColor());
        Assert.assertEquals(tempBackground.getBackgroundImageId(), widgetManager.getBackground(SiteShowOption.getDraftOption()).getBackgroundImageId());
        Assert.assertEquals(tempBackground.getBackgroundPosition(), widgetManager.getBackground(SiteShowOption.getDraftOption()).getBackgroundPosition());
    }
    /*-----------------------------------------------Background-----------------------------------------------*/
    
    /*-----------------------------------------------Border-----------------------------------------------*/

    @Test
    public void testGetBorder_BorderInItem() throws Exception {
        TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        final DraftItem draftItem = TestUtil.createBlog(site);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(TestUtil.createPageVersion(TestUtil.createPage(site)));
        widgetItem.setDraftItem(draftItem);
        WidgetManager widgetManager = new WidgetManager(widgetItem);
        widgetItem.setBorderId(null);
        
        final ItemManager itemManager = new ItemManager(draftItem);
        itemManager.setBorder(TestUtil.createBorder());

        Assert.assertEquals(itemManager.getBorder(SiteShowOption.getDraftOption()), widgetManager.getBorder(SiteShowOption.getDraftOption()));
    }

    @Test
    public void testGetBorder_BorderInWidget() throws Exception {
        TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        final DraftItem draftItem = TestUtil.createBlog(site);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(TestUtil.createPageVersion(TestUtil.createPage(site)));
        widgetItem.setDraftItem(draftItem);
        
        final ItemManager itemManager = new ItemManager(draftItem);
        itemManager.setBorder(TestUtil.createBorder());

        final Border border = TestUtil.createBorder();
        final StyleValue styleValue = new StyleValue();
        styleValue.setTopValue("top");
        styleValue.setRightValue("right");
        styleValue.setBottomValue("bottom");
        styleValue.setLeftValue("left");
        border.getBorderColor().setValues(styleValue);
        
        final WidgetManager widgetManager = new WidgetManager(widgetItem);
        widgetItem.setBorderId(border.getId());


        Assert.assertEquals(border, widgetManager.getBorder(SiteShowOption.getDraftOption()));
        Assert.assertNotSame(itemManager.getBorder(SiteShowOption.getDraftOption()), widgetManager.getBorder(SiteShowOption.getDraftOption()));
    }

    @Test
    public void testSetBorderInCurrentPlace_withBorderInWidget() throws Exception {
        TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        final DraftItem draftItem = TestUtil.createBlog(site);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(TestUtil.createPageVersion(TestUtil.createPage(site)));
        widgetItem.setDraftItem(draftItem);
        
        final ItemManager itemManager = new ItemManager(draftItem);
        itemManager.setBorder(TestUtil.createBorder());

        final Border border = TestUtil.createBorder();
        final StyleValue styleValue = new StyleValue();
        styleValue.setTopValue("top");
        styleValue.setRightValue("right");
        styleValue.setBottomValue("bottom");
        styleValue.setLeftValue("left");
        border.getBorderColor().setValues(styleValue);
        
        final WidgetManager widgetManager = new WidgetManager(widgetItem);
        widgetItem.setBorderId(border.getId());
        final int oldBorderId = border.getId();        


        final Border tempBorder = TestUtil.createBorder();
        final StyleValue styleValue2 = new StyleValue();
        styleValue2.setTopValue("top2");
        styleValue2.setRightValue("right2");
        styleValue2.setBottomValue("bottom2");
        styleValue2.setLeftValue("left2");
        tempBorder.getBorderColor().setValues(styleValue2);
        widgetManager.setBorderInCurrentPlace(tempBorder);


        Assert.assertNotSame(tempBorder.getId(), widgetManager.getBorder(SiteShowOption.getDraftOption()).getId());
        Assert.assertEquals(oldBorderId, widgetManager.getBorder(SiteShowOption.getDraftOption()).getId());
        Assert.assertEquals(tempBorder.getBorderColor().getValues().getTopValue(), widgetManager.getBorder(SiteShowOption.getDraftOption()).getBorderColor().getValues().getTopValue());
        Assert.assertEquals(tempBorder.getBorderColor().getValues().getRightValue(), widgetManager.getBorder(SiteShowOption.getDraftOption()).getBorderColor().getValues().getRightValue());
        Assert.assertEquals(tempBorder.getBorderColor().getValues().getBottomValue(), widgetManager.getBorder(SiteShowOption.getDraftOption()).getBorderColor().getValues().getBottomValue());
        Assert.assertEquals(tempBorder.getBorderColor().getValues().getLeftValue(), widgetManager.getBorder(SiteShowOption.getDraftOption()).getBorderColor().getValues().getLeftValue());
    }

    @Test
    public void testSetBorderInCurrentPlace_withoutBorderInWidget() throws Exception {
        TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        final DraftItem draftItem = TestUtil.createBlog(site);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(TestUtil.createPageVersion(TestUtil.createPage(site)));
        widgetItem.setDraftItem(draftItem);
        
        final ItemManager itemManager = new ItemManager(draftItem);
        itemManager.setBorder(TestUtil.createBorder());
        
        final WidgetManager widgetManager = new WidgetManager(widgetItem);
        widgetItem.setBorderId(null);



        final Border tempBorder = TestUtil.createBorder();
        final StyleValue styleValue2 = new StyleValue();
        styleValue2.setTopValue("top2");
        styleValue2.setRightValue("right2");
        styleValue2.setBottomValue("bottom2");
        styleValue2.setLeftValue("left2");
        tempBorder.getBorderColor().setValues(styleValue2);

        widgetManager.setBorderInCurrentPlace(tempBorder);


        Assert.assertNotSame(tempBorder.getId(), widgetManager.getBorder(SiteShowOption.getDraftOption()).getId());
        Assert.assertNotNull(widgetManager.getBorder(SiteShowOption.getDraftOption()).getId());
        Assert.assertEquals(tempBorder.getBorderColor().getValues().getTopValue(), widgetManager.getBorder(SiteShowOption.getDraftOption()).getBorderColor().getValues().getTopValue());
        Assert.assertEquals(tempBorder.getBorderColor().getValues().getRightValue(), widgetManager.getBorder(SiteShowOption.getDraftOption()).getBorderColor().getValues().getRightValue());
        Assert.assertEquals(tempBorder.getBorderColor().getValues().getBottomValue(), widgetManager.getBorder(SiteShowOption.getDraftOption()).getBorderColor().getValues().getBottomValue());
        Assert.assertEquals(tempBorder.getBorderColor().getValues().getLeftValue(), widgetManager.getBorder(SiteShowOption.getDraftOption()).getBorderColor().getValues().getLeftValue());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSetBorderInAllPlaces_forWidgetComposit() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final Widget widgetComposit = new WidgetComposit();
        widgetComposit.setDraftPageSettings((DraftPageSettings) TestUtil.createPageSettings(TestUtil.createPage(site)));

        final WidgetManager widgetManager = new WidgetManager(widgetComposit);
        widgetManager.setBorderInAllPlaces(null);
    }

    @Test
    public void testSetBorderInAllPlaces_forOwner() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final DraftItem draftItem = TestUtil.createBlog(site);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(TestUtil.createPageVersion(TestUtil.createPage(site)));
        widgetItem.setDraftItem(draftItem);
        
        final ItemManager itemManager = new ItemManager(draftItem);
        itemManager.setBorder(TestUtil.createBorder());

        final Border border = TestUtil.createBorder();
        final StyleValue styleValue = new StyleValue();
        styleValue.setTopValue("top");
        styleValue.setRightValue("right");
        styleValue.setBottomValue("bottom");
        styleValue.setLeftValue("left");
        border.getBorderColor().setValues(styleValue);
        
        final WidgetManager widgetManager = new WidgetManager(widgetItem);
        widgetItem.setBorderId(border.getId());
        final int oldBorderId = draftItem.getBorderId();


        final Border tempBorder = TestUtil.createBorder();
        final StyleValue styleValue2 = new StyleValue();
        styleValue2.setTopValue("top2");
        styleValue2.setRightValue("right2");
        styleValue2.setBottomValue("bottom2");
        styleValue2.setLeftValue("left2");
        tempBorder.getBorderColor().setValues(styleValue2);
        widgetManager.setBorderInAllPlaces(tempBorder);


        Assert.assertEquals(null, widgetItem.getBorderId());
        Assert.assertEquals(oldBorderId, itemManager.getBorder(SiteShowOption.getDraftOption()).getId());
        Assert.assertEquals(tempBorder.getBorderColor().getValues().getTopValue(), widgetManager.getBorder(SiteShowOption.getDraftOption()).getBorderColor().getValues().getTopValue());
        Assert.assertEquals(tempBorder.getBorderColor().getValues().getRightValue(), widgetManager.getBorder(SiteShowOption.getDraftOption()).getBorderColor().getValues().getRightValue());
        Assert.assertEquals(tempBorder.getBorderColor().getValues().getBottomValue(), widgetManager.getBorder(SiteShowOption.getDraftOption()).getBorderColor().getValues().getBottomValue());
        Assert.assertEquals(tempBorder.getBorderColor().getValues().getLeftValue(), widgetManager.getBorder(SiteShowOption.getDraftOption()).getBorderColor().getValues().getLeftValue());
    }

    @Test
    public void testSetBorderInAllPlaces_withoutRights() throws Exception {
        TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        final DraftItem draftItem = TestUtil.createBlog(site);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(TestUtil.createPageVersion(TestUtil.createPage(site)));
        widgetItem.setDraftItem(draftItem);
        
        final ItemManager itemManager = new ItemManager(draftItem);
        itemManager.setBorder(TestUtil.createBorder());

        final Border border = TestUtil.createBorder();
        final StyleValue styleValue = new StyleValue();
        styleValue.setTopValue("top");
        styleValue.setRightValue("right");
        styleValue.setBottomValue("bottom");
        styleValue.setLeftValue("left");
        border.getBorderColor().setValues(styleValue);
        
        final WidgetManager widgetManager = new WidgetManager(widgetItem);
        widgetItem.setBorderId(border.getId());
        final int oldBorderId = border.getId();


        final Border tempBorder = TestUtil.createBorder();
        final StyleValue styleValue2 = new StyleValue();
        styleValue2.setTopValue("top2");
        styleValue2.setRightValue("right2");
        styleValue2.setBottomValue("bottom2");
        styleValue2.setLeftValue("left2");
        tempBorder.getBorderColor().setValues(styleValue2);
        widgetManager.setBorderInAllPlaces(tempBorder);


        Assert.assertNotSame(tempBorder.getId(), widgetManager.getBorder(SiteShowOption.getDraftOption()).getId());
        Assert.assertEquals(oldBorderId, widgetManager.getBorder(SiteShowOption.getDraftOption()).getId());
        Assert.assertEquals(tempBorder.getBorderColor().getValues().getTopValue(), widgetManager.getBorder(SiteShowOption.getDraftOption()).getBorderColor().getValues().getTopValue());
        Assert.assertEquals(tempBorder.getBorderColor().getValues().getRightValue(), widgetManager.getBorder(SiteShowOption.getDraftOption()).getBorderColor().getValues().getRightValue());
        Assert.assertEquals(tempBorder.getBorderColor().getValues().getBottomValue(), widgetManager.getBorder(SiteShowOption.getDraftOption()).getBorderColor().getValues().getBottomValue());
        Assert.assertEquals(tempBorder.getBorderColor().getValues().getLeftValue(), widgetManager.getBorder(SiteShowOption.getDraftOption()).getBorderColor().getValues().getLeftValue());
    }
    /*-----------------------------------------------Border-----------------------------------------------*/
    
    /*-----------------------------------------------FontsAndColors-----------------------------------------------*/

    @Test
    public void testGetFontsAndColors_FontsAndColorsInItem() throws Exception {
        TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        final DraftItem draftItem = TestUtil.createBlog(site);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(TestUtil.createPageVersion(TestUtil.createPage(site)));
        widgetItem.setDraftItem(draftItem);
        widgetItem.setFontsAndColorsId(null);
        draftItem.setFontsAndColorsId(TestUtil.createFontsAndColorsWithOneCssValue().getId());

        final WidgetManager widgetManager = new WidgetManager(widgetItem);
        Assert.assertEquals(draftItem.getFontsAndColorsId().intValue(), widgetManager.getFontsAndColors(SiteShowOption.getDraftOption()).getId());
    }

    @Test
    public void testGetFontsAndColors_FontsAndColorsInWidget() throws Exception {
        TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        final DraftItem draftItem = TestUtil.createBlog(site);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(TestUtil.createPageVersion(TestUtil.createPage(site)));
        widgetItem.setDraftItem(draftItem);
        draftItem.setFontsAndColorsId(TestUtil.createFontsAndColorsWithOneCssValue().getId());

        final FontsAndColors fontsAndColors = TestUtil.createFontsAndColorsWithOneCssValue();
        fontsAndColors.setCssValues(new ArrayList<FontsAndColorsValue>());
        widgetItem.setFontsAndColorsId(fontsAndColors.getId());


        final WidgetManager widgetManager = new WidgetManager(widgetItem);
        Assert.assertEquals(fontsAndColors, widgetManager.getFontsAndColors(SiteShowOption.getDraftOption()));
        Assert.assertNotSame(draftItem.getFontsAndColorsId(), widgetManager.getFontsAndColors(SiteShowOption.getDraftOption()).getId());
    }

    @Test
    public void testSetFontsAndColorsInCurrentPlace_withFontsAndColorsInWidget() throws Exception {
        TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        final DraftItem draftItem = TestUtil.createBlog(site);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(TestUtil.createPageVersion(TestUtil.createPage(site)));
        widgetItem.setDraftItem(draftItem);
        draftItem.setFontsAndColorsId(TestUtil.createFontsAndColorsWithOneCssValue().getId());

        final FontsAndColors fontsAndColors = TestUtil.createFontsAndColorsWithOneCssValue();
        fontsAndColors.setCssValues(new ArrayList<FontsAndColorsValue>());
        widgetItem.setFontsAndColorsId(fontsAndColors.getId());
        final int oldFontsAndColorsId = fontsAndColors.getId();


        final WidgetManager widgetManager = new WidgetManager(widgetItem);


        final CssParameter cssParameter1 = TestUtil.createCssParameter("name", "selector", "value");
        final CreateFontsAndColorsRequest request = new CreateFontsAndColorsRequest();
        request.setCssParameters(Arrays.asList(cssParameter1));
        widgetManager.setFontsAndColorsInCurrentPlace(request);


        Assert.assertEquals(oldFontsAndColorsId, widgetItem.getFontsAndColorsId().intValue());
        Assert.assertEquals(1, widgetManager.getFontsAndColors(SiteShowOption.getDraftOption()).getCssValues().size());
        Assert.assertNotNull(ServiceLocator.getPersistance().getFontsAndColors(oldFontsAndColorsId));
    }

    @Test
    public void testSetFontsAndColorsInCurrentPlace_withoutFontsAndColorsInWidget() throws Exception {
        TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        final DraftItem draftItem = TestUtil.createBlog(site);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(TestUtil.createPageVersion(TestUtil.createPage(site)));
        widgetItem.setDraftItem(draftItem);
        draftItem.setFontsAndColorsId(TestUtil.createFontsAndColorsWithOneCssValue().getId());
        widgetItem.setFontsAndColorsId(null);


        final WidgetManager widgetManager = new WidgetManager(widgetItem);


        final CssParameter cssParameter1 = TestUtil.createCssParameter("name", "selector", "value");
        final CreateFontsAndColorsRequest request = new CreateFontsAndColorsRequest();
        request.setCssParameters(Arrays.asList(cssParameter1));
        widgetManager.setFontsAndColorsInCurrentPlace(request);


        Assert.assertNotNull(ServiceLocator.getPersistance().getFontsAndColors(widgetItem.getFontsAndColorsId()));
        Assert.assertEquals(1, widgetManager.getFontsAndColors(SiteShowOption.getDraftOption()).getCssValues().size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSetFontsAndColorsInAllPlaces_forWidgetComposit() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final Widget widgetComposit = new WidgetComposit();
        widgetComposit.setDraftPageSettings((DraftPageSettings) TestUtil.createPageSettings(TestUtil.createPage(site)));

        final WidgetManager widgetManager = new WidgetManager(widgetComposit);
        widgetManager.setFontsAndColorsInAllPlaces(null);
    }

    @Test
    public void testSetFontsAndColorsInAllPlaces_forOwner() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final DraftItem draftItem = TestUtil.createBlog(site);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(TestUtil.createPageVersion(TestUtil.createPage(site)));
        widgetItem.setDraftItem(draftItem);
        draftItem.setFontsAndColorsId(TestUtil.createFontsAndColorsWithOneCssValue().getId());

        final FontsAndColors fontsAndColors = TestUtil.createFontsAndColorsWithOneCssValue();
        fontsAndColors.setCssValues(new ArrayList<FontsAndColorsValue>());
        widgetItem.setFontsAndColorsId(fontsAndColors.getId());
        final int oldFontsAndColorsId = draftItem.getFontsAndColorsId();


        final WidgetManager widgetManager = new WidgetManager(widgetItem);


        final CssParameter cssParameter1 = TestUtil.createCssParameter("name", "selector", "value");
        final CreateFontsAndColorsRequest request = new CreateFontsAndColorsRequest();
        request.setCssParameters(Arrays.asList(cssParameter1));
        widgetManager.setFontsAndColorsInAllPlaces(request);


        Assert.assertEquals(true, widgetItem.getFontsAndColorsId() == null);
        Assert.assertNotNull(ServiceLocator.getPersistance().getFontsAndColors(draftItem.getFontsAndColorsId()));
        Assert.assertEquals(1, ServiceLocator.getPersistance().getFontsAndColors(draftItem.getFontsAndColorsId()).getCssValues().size());
    }

    @Test
    public void testSetFontsAndColorsInAllPlaces_withoutRights() throws Exception {
        TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        final DraftItem draftItem = TestUtil.createBlog(site);
        final WidgetItem widgetItem = TestUtil.createWidgetItem(TestUtil.createPageVersion(TestUtil.createPage(site)));
        widgetItem.setDraftItem(draftItem);
        draftItem.setFontsAndColorsId(TestUtil.createFontsAndColorsWithOneCssValue().getId());

        final FontsAndColors fontsAndColors = TestUtil.createFontsAndColorsWithOneCssValue();
        fontsAndColors.setCssValues(new ArrayList<FontsAndColorsValue>());
        widgetItem.setFontsAndColorsId(fontsAndColors.getId());
        final int oldFontsAndColorsId = fontsAndColors.getId();


        final WidgetManager widgetManager = new WidgetManager(widgetItem);


        final CssParameter cssParameter1 = TestUtil.createCssParameter("name", "selector", "value");
        final CreateFontsAndColorsRequest request = new CreateFontsAndColorsRequest();
        request.setCssParameters(Arrays.asList(cssParameter1));
        widgetManager.setFontsAndColorsInAllPlaces(request);


        Assert.assertEquals(oldFontsAndColorsId, widgetItem.getFontsAndColorsId().intValue());
//        Assert.assertEquals(oldFontsAndColorsId, widgetItem.getFontsAndColorsId());
        Assert.assertEquals(1, widgetManager.getFontsAndColors(SiteShowOption.getDraftOption()).getCssValues().size());
    }
    /*-----------------------------------------------FontsAndColors-----------------------------------------------*/

    @Test
    public void getLocation() throws Exception {
        User user = TestUtil.createUserAndLogin("a@a.com");

        Site site = TestUtil.createSite("site title", "site url");
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site, "page name");

        DraftBlogSummary blogSummary = TestUtil.createBlogSummary(site);

        WidgetItem widgetItem = TestUtil.createWidgetBlogSummaryForPageVersion(pageVersion, blogSummary.getId());
        Assert.assertEquals("site title http://site url.shroggle.com, page name", new WidgetManager(widgetItem).getLocation());
    }

    @Test
    public void getLocation_withLongNames() throws Exception {
        User user = TestUtil.createUserAndLogin("a@a.com");

        Site site = TestUtil.createSite("siteTitleeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee", "siteUrllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll");
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site, "pageNameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");

        DraftBlogSummary blogSummary = TestUtil.createBlogSummary(site);

        WidgetItem widgetItem = TestUtil.createWidgetBlogSummaryForPageVersion(pageVersion, blogSummary.getId());
        Assert.assertEquals("siteTitleeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee http://siteUrllllllllllll" +
                "llllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll.shroggle.com, pag" +
                "eNameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee" +
                "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee", new WidgetManager(widgetItem).getLocation());
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
}
