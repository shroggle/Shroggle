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
package com.shroggle.presentation.site.accessibilityForRender;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class CreateAccessibilitySettingsServiceTest {

    @Test
    public void execute() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Page page = TestUtil.createPage(site);
        persistance.putSite(site);

        final CreateAccessibilitySettingsRequest request = new CreateAccessibilitySettingsRequest();
        request.setElementId(site.getSiteId());
        request.setElementType(AccessibleElementType.SITE);

        final AccessibleSettings accessibleSettings = new AccessibleSettings();
        accessibleSettings.setAccess(AccessForRender.UNLIMITED);
        accessibleSettings.setAdministrators(false);
        accessibleSettings.setVisitors(true);
        accessibleSettings.setVisitorsGroups(Arrays.asList(1, 2));
        request.setAccessibleSettings(accessibleSettings);

        Assert.assertNull(service.execute(request).getFunctionalWidgetInfo());

        Assert.assertEquals(AccessForRender.UNLIMITED, site.getAccessibleSettings().getAccess());
        Assert.assertEquals(false, site.getAccessibleSettings().isAdministrators());
        Assert.assertEquals(true, site.getAccessibleSettings().isVisitors());
        Assert.assertEquals(2, site.getAccessibleSettings().getVisitorsGroups().size());
        Assert.assertEquals(true, site.getAccessibleSettings().getVisitorsGroups().contains(1));
        Assert.assertEquals(true, site.getAccessibleSettings().getVisitorsGroups().contains(2));
        Assert.assertEquals(false, site.getAccessibleSettings().getVisitorsGroups().contains(3));
    }

    @Test
    public void execute_forDraftItem() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Page page = TestUtil.createPage(site);
        persistance.putSite(site);

        final DraftImage draftImage = TestUtil.createDraftImage(site);

        final CreateAccessibilitySettingsRequest request = new CreateAccessibilitySettingsRequest();
        request.setElementId(draftImage.getId());
        request.setElementType(AccessibleElementType.ITEM);

        final AccessibleSettings accessibleSettings = new AccessibleSettings();
        accessibleSettings.setAccess(AccessForRender.UNLIMITED);
        accessibleSettings.setAdministrators(false);
        accessibleSettings.setVisitors(true);
        accessibleSettings.setVisitorsGroups(Arrays.asList(1, 2));
        request.setAccessibleSettings(accessibleSettings);

        Assert.assertNull(service.execute(request).getFunctionalWidgetInfo());

        Assert.assertEquals(AccessForRender.UNLIMITED, new ItemManager(draftImage).getAccessibleSettings(SiteShowOption.getDraftOption()).getAccess());
        Assert.assertEquals(false, new ItemManager(draftImage).getAccessibleSettings(SiteShowOption.getDraftOption()).isAdministrators());
        Assert.assertEquals(true, new ItemManager(draftImage).getAccessibleSettings(SiteShowOption.getDraftOption()).isVisitors());
        Assert.assertEquals(2, new ItemManager(draftImage).getAccessibleSettings(SiteShowOption.getDraftOption()).getVisitorsGroups().size());
        Assert.assertEquals(true, new ItemManager(draftImage).getAccessibleSettings(SiteShowOption.getDraftOption()).getVisitorsGroups().contains(1));
        Assert.assertEquals(true, new ItemManager(draftImage).getAccessibleSettings(SiteShowOption.getDraftOption()).getVisitorsGroups().contains(2));
        Assert.assertEquals(false, new ItemManager(draftImage).getAccessibleSettings(SiteShowOption.getDraftOption()).getVisitorsGroups().contains(3));
    }

    @Test
    public void execute_withSettings() throws Exception {
        final Site site = TestUtil.createSite();
        site.getAccessibleSettings().setVisitorsGroups(Arrays.asList(3, 4));
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Page page = TestUtil.createPage(site);
        persistance.putSite(site);

        final CreateAccessibilitySettingsRequest request = new CreateAccessibilitySettingsRequest();
        request.setElementId(site.getSiteId());
        request.setElementType(AccessibleElementType.SITE);

        final AccessibleSettings accessibleSettings = new AccessibleSettings();
        accessibleSettings.setAccess(AccessForRender.UNLIMITED);
        accessibleSettings.setAdministrators(false);
        accessibleSettings.setVisitors(true);
        accessibleSettings.setVisitorsGroups(Arrays.asList(1, 2));
        request.setAccessibleSettings(accessibleSettings);

        Assert.assertNull(service.execute(request).getFunctionalWidgetInfo());

        Assert.assertEquals(AccessForRender.UNLIMITED, site.getAccessibleSettings().getAccess());
        Assert.assertEquals(false, site.getAccessibleSettings().isAdministrators());
        Assert.assertEquals(true, site.getAccessibleSettings().isVisitors());
        Assert.assertEquals(2, site.getAccessibleSettings().getVisitorsGroups().size());
        Assert.assertEquals(true, site.getAccessibleSettings().getVisitorsGroups().contains(1));
        Assert.assertEquals(true, site.getAccessibleSettings().getVisitorsGroups().contains(2));
        Assert.assertEquals(false, site.getAccessibleSettings().getVisitorsGroups().contains(3));
    }

    @Test
    public void execute_widget() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createTextWidget();
        WidgetManager widgetManager = new WidgetManager(widget);
        widgetManager.getAccessibleSettings().setAccess(AccessForRender.UNLIMITED);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        final CreateAccessibilitySettingsRequest request = new CreateAccessibilitySettingsRequest();

        request.setElementId(widget.getWidgetId());
        request.setElementType(AccessibleElementType.WIDGET);

        final AccessibleSettings accessibleSettings = new AccessibleSettings();
        accessibleSettings.setAccess(AccessForRender.UNLIMITED);
        accessibleSettings.setAdministrators(false);
        accessibleSettings.setVisitors(false);
        accessibleSettings.setVisitorsGroups(Arrays.asList(1, 2));
        request.setAccessibleSettings(accessibleSettings);

        Assert.assertNotNull(service.execute(request));
        Assert.assertEquals(AccessForRender.UNLIMITED, widgetManager.getAccessibleSettings().getAccess());
        Assert.assertEquals(true, pageVersion.isChanged());
    }

    @Test
    public void executeWithNull_widget() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createTextWidget();
        WidgetManager widgetManager = new WidgetManager(widget);
        widgetManager.getAccessibleSettings().setAccess(AccessForRender.UNLIMITED);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        final CreateAccessibilitySettingsRequest request = new CreateAccessibilitySettingsRequest();

        request.setElementId(widget.getWidgetId());
        request.setElementType(AccessibleElementType.WIDGET);

        final AccessibleSettings accessibleSettings = new AccessibleSettings();
        accessibleSettings.setAccess(AccessForRender.UNLIMITED);
        accessibleSettings.setAdministrators(false);
        accessibleSettings.setVisitors(false);
        accessibleSettings.setVisitorsGroups(Arrays.asList(1, 2));
        request.setAccessibleSettings(accessibleSettings);

        Assert.assertNotNull(service.execute(request));
        Assert.assertEquals(AccessForRender.UNLIMITED, widgetManager.getAccessibleSettings().getAccess());
        Assert.assertEquals(true, pageVersion.isChanged());
    }

    @Test
    public void execute_pageVersion() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final CreateAccessibilitySettingsRequest request = new CreateAccessibilitySettingsRequest();

        request.setElementId(pageVersion.getPageId());
        request.setElementType(AccessibleElementType.PAGE);

        final AccessibleSettings accessibleSettings = new AccessibleSettings();
        accessibleSettings.setAccess(AccessForRender.UNLIMITED);
        accessibleSettings.setAdministrators(false);
        accessibleSettings.setVisitors(false);
        accessibleSettings.setVisitorsGroups(Arrays.asList(1, 2));
        request.setAccessibleSettings(accessibleSettings);

        Assert.assertNull(service.execute(request).getFunctionalWidgetInfo());
        Assert.assertEquals(AccessForRender.UNLIMITED, pageVersion.getAccessibleSettings().getAccess());
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutWidget() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);

        service.execute(null);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutUser() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);

        Page page = TestUtil.createPage(site);

        service.execute(null);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final CreateAccessibilitySettingsService service = new CreateAccessibilitySettingsService();
}
