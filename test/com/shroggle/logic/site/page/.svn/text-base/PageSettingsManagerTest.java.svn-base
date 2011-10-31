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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.SiteManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class PageSettingsManagerTest {


    @Test
    public void testGetPublicUrl() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(TestUtil.createPage(site));
        draftPageSettings.setUrl("url");
        Assert.assertEquals(new SiteManager(site).getPublicUrl() + "/url", new PageSettingsManager(draftPageSettings).getPublicUrl());
    }

    @Test
    public void testGetPublicUrl_withoutPageSettings() throws Exception {
        Assert.assertEquals("", new PageSettingsManager(null).getPublicUrl());
    }

    @Test
    public void testGetWidgets() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(TestUtil.createPage(site));
        final List<Widget> widgets = new ArrayList<Widget>();
        widgets.add(TestUtil.createWidgetComposit());
        draftPageSettings.setWidgets(widgets);
        Assert.assertEquals(1, new PageSettingsManager(draftPageSettings).getWidgets().size());
    }

    @Test
    public void testGetWidgets_withoutPageSettings() throws Exception {
        Assert.assertEquals(0, new PageSettingsManager(null).getWidgets().size());
    }

    @Test
    public void testGetKeywordsGroups() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(TestUtil.createPage(site));
        final KeywordsGroup keywordsGroup = TestUtil.createKeywordsGroup();
        draftPageSettings.setKeywordsGroupsId(Arrays.asList(keywordsGroup.getKeywordsGroupId()));
        Assert.assertEquals(1, new PageSettingsManager(draftPageSettings).getKeywordsGroups().size());
        Assert.assertEquals(keywordsGroup, new PageSettingsManager(draftPageSettings).getKeywordsGroups().get(0));
    }

    @Test
    public void testGetKeywordsGroups_withoutPageSettings() throws Exception {
        Assert.assertEquals(0, new PageSettingsManager(null).getKeywordsGroups().size());
    }

    @Test
    public void testGetHtml() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(TestUtil.createPage(site));
        draftPageSettings.setHtml("html");
        Assert.assertEquals("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n" +
                "html", new PageSettingsManager(draftPageSettings).getHtml());
    }

    @Test
    public void testGetHtml_withoutPageSettings() throws Exception {
        Assert.assertEquals(null, new PageSettingsManager(null).getHtml());
    }

    @Test
    public void testGetLayoutFile() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(TestUtil.createPage(site));
        draftPageSettings.setLayoutFile("LayoutFile");
        Assert.assertEquals("LayoutFile", new PageSettingsManager(draftPageSettings).getLayoutFile());
    }

    @Test
    public void testGetLayoutFile_withoutPageSettings() throws Exception {
        Assert.assertEquals("", new PageSettingsManager(null).getLayoutFile());
    }

    @Test
    public void testGetThemeId() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(TestUtil.createPage(site));
        final ThemeId themeId = new ThemeId("templateDirectory", "themeCss");
        draftPageSettings.setThemeId(themeId);
        Assert.assertEquals(themeId, new PageSettingsManager(draftPageSettings).getThemeId());
    }

    @Test
    public void testGetThemeId_withoutPageSettings() throws Exception {
        Assert.assertEquals(null, new PageSettingsManager(null).getThemeId());
    }

    @Test
    public void testGetCss() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(TestUtil.createPage(site));
        draftPageSettings.setCss("css");
        Assert.assertEquals("css", new PageSettingsManager(draftPageSettings).getCss());
    }

    @Test
    public void testGetCss_withoutPageSettings() throws Exception {
        Assert.assertEquals(null, new PageSettingsManager(null).getCss());
    }

    @Test
    public void testGetName() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(TestUtil.createPage(site));
        draftPageSettings.setName("name");
        Assert.assertEquals("name", new PageSettingsManager(draftPageSettings).getName());
    }

    @Test
    public void testGetName_withoutPageSettings() throws Exception {
        Assert.assertEquals("", new PageSettingsManager(null).getName());
    }

    @Test
    public void testGetTitle() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(TestUtil.createPage(site));
        draftPageSettings.setTitle("title");
        Assert.assertEquals("title", new PageSettingsManager(draftPageSettings).getTitle());
    }

    @Test
    public void testGetTitle_withoutPageSettings() throws Exception {
        Assert.assertEquals("", new PageSettingsManager(null).getTitle());
    }

    @Test
    public void testGetUrl_Draft() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(TestUtil.createPage(site));
        draftPageSettings.setUrl("url");
        Assert.assertEquals("showPageVersion.action?pageId=" + draftPageSettings.getPage().getPageId() + "&siteShowOption=ON_USER_PAGES", new PageSettingsManager(draftPageSettings).getUrl());
    }

    @Test
    public void testGetUrl_Work() throws Exception {
        final Site site = TestUtil.createSite();
        final WorkPageSettings workPageSettings = TestUtil.createWorkPageSettings(TestUtil.createPageSettings(TestUtil.createPage(site)));
        workPageSettings.setUrl("url");
        Assert.assertEquals("/url", new PageSettingsManager(workPageSettings).getUrl());
    }

    @Test
    public void testGetUrl_withoutPageSettings() throws Exception {
        Assert.assertEquals("", new PageSettingsManager(null).getUrl());
    }

    @Test
    public void testGetRawUrl() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(TestUtil.createPage(site));
        draftPageSettings.setUrl("url");
        Assert.assertEquals("url", new PageSettingsManager(draftPageSettings).getRawUrl());
    }

    @Test
    public void testGetRawUrl_withoutPageSettings() throws Exception {
        Assert.assertEquals("", new PageSettingsManager(null).getRawUrl());
    }

    @Test
    public void testGetBorder() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(TestUtil.createPage(site));
        final Border border = TestUtil.createBorder();
        draftPageSettings.setBorder(border);
        Assert.assertEquals(border, new PageSettingsManager(draftPageSettings).getBorder());
    }

    @Test
    public void testGetBorder_withoutPageSettings() throws Exception {
        Assert.assertEquals(null, new PageSettingsManager(null).getBorder());
    }

    @Test
    public void testGetBackground() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(TestUtil.createPage(site));
        final Background background = TestUtil.createBackground();
        draftPageSettings.setBackground(background);
        Assert.assertEquals(background, new PageSettingsManager(draftPageSettings).getBackground());
    }

    @Test
    public void testGetBackground_withoutPageSettings() throws Exception {
        Assert.assertEquals(null, new PageSettingsManager(null).getBackground());
    }

    @Test
    public void testGetPageSettings() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(TestUtil.createPage(site));
        Assert.assertEquals(draftPageSettings, new PageSettingsManager(draftPageSettings).getPageSettings());
    }

    @Test
    public void testGetPageSettings_withoutPageSettings() throws Exception {
        Assert.assertEquals(null, new PageSettingsManager(null).getPageSettings());
    }

    @Test
    public void testGetDraftPageSettings() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(TestUtil.createPage(site));
        Assert.assertEquals(draftPageSettings, new PageSettingsManager(draftPageSettings).getDraftPageSettings());
    }

    @Test
    public void testGetDraftPageSettings_withoutPageSettings() throws Exception {
        Assert.assertEquals(null, new PageSettingsManager(null).getDraftPageSettings());
    }

    @Test
    public void testGetWorkPageSettings() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(TestUtil.createPage(site));
        final WorkPageSettings workPageSettings = TestUtil.createWorkPageSettings(draftPageSettings);
        Assert.assertEquals(workPageSettings, new PageSettingsManager(draftPageSettings).getWorkPageSettings());
    }

    @Test
    public void testGetWorkPageSettings_withDraftOnly() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(TestUtil.createPage(site));
        Assert.assertEquals(null, new PageSettingsManager(draftPageSettings).getWorkPageSettings());
    }

    @Test
    public void testGetWorkPageSettings_withoutPageSettings() throws Exception {
        Assert.assertEquals(null, new PageSettingsManager(null).getWorkPageSettings());
    }

    @Test
    public void testIsWork() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(TestUtil.createPage(site));
        Assert.assertEquals(false, new PageSettingsManager(draftPageSettings).isWork());
    }

    @Test
    public void testIsWork_withoutPageSettings() throws Exception {
        Assert.assertEquals(false, new PageSettingsManager(null).isWork());
    }

    @Test
    public void testIsWork_Work() throws Exception {
        final Site site = TestUtil.createSite();
        final WorkPageSettings workPageSettings = TestUtil.createWorkPageSettings(TestUtil.createPageSettings(TestUtil.createPage(site)));
        Assert.assertEquals(true, new PageSettingsManager(workPageSettings).isWork());
    }

    @Test
    public void testIsBlueprintLocked() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(TestUtil.createPage(site));
        draftPageSettings.setBlueprintLocked(true);
        Assert.assertEquals(true, new PageSettingsManager(draftPageSettings).isBlueprintLocked());
    }

    @Test
    public void testIsBlueprintLocked_withoutPageSettings() throws Exception {
        Assert.assertEquals(false, new PageSettingsManager(null).isBlueprintLocked());
    }

    @Test
    public void testIsBlueprintRequired() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(TestUtil.createPage(site));
        draftPageSettings.setBlueprintRequired(true);
        Assert.assertEquals(true, new PageSettingsManager(draftPageSettings).isBlueprintRequired());
    }

    @Test
    public void testIsBlueprintRequired_withoutPageSettings() throws Exception {
        Assert.assertEquals(false, new PageSettingsManager(null).isBlueprintRequired());
    }

    @Test
    public void testIsBlueprintNotEditable() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(TestUtil.createPage(site));
        draftPageSettings.setBlueprintNotEditable(true);
        Assert.assertEquals(true, new PageSettingsManager(draftPageSettings).isBlueprintNotEditable());
    }

    @Test
    public void testIsBlueprintNotEditable_withoutPageSettings() throws Exception {
        Assert.assertEquals(false, new PageSettingsManager(null).isBlueprintNotEditable());
    }

    @Test
    public void testGetAccessibleSettings() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(TestUtil.createPage(site));
        final AccessibleSettings accessibleSettings = TestUtil.createAccessibleSettings();
        draftPageSettings.setAccessibleSettings(accessibleSettings);
        Assert.assertEquals(accessibleSettings, new PageSettingsManager(draftPageSettings).getAccessibleSettings());
    }

    @Test
    public void testGetAccessibleSettings_withoutPageSettings() throws Exception {
        Assert.assertEquals(null, new PageSettingsManager(null).getAccessibleSettings());
    }

    @Test
    public void testGetPage() throws Exception {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(page);
        Assert.assertEquals(page, new PageSettingsManager(draftPageSettings).getPage());
    }

    @Test
    public void testGetPage_withoutPageSettings() throws Exception {
        Assert.assertEquals(null, new PageSettingsManager(null).getPage());
    }

    @Test
    public void testGetOwnDomainName() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(TestUtil.createPage(site));
        draftPageSettings.setOwnDomainName("OwnDomainName");
        Assert.assertEquals("OwnDomainName", new PageSettingsManager(draftPageSettings).getOwnDomainName());
    }

    @Test
    public void testGetOwnDomainName_withNullSaved() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(TestUtil.createPage(site));
        draftPageSettings.setOwnDomainName(null);
        Assert.assertEquals("", new PageSettingsManager(draftPageSettings).getOwnDomainName());
    }

    @Test
    public void testGetOwnDomainName_withoutPageSettings() throws Exception {
        Assert.assertEquals("", new PageSettingsManager(null).getOwnDomainName());
    }

    @Test
    public void testGetKeywords() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(TestUtil.createPage(site));
        draftPageSettings.setKeywords("Keywords");
        Assert.assertEquals("Keywords", new PageSettingsManager(draftPageSettings).getKeywords());
    }

    @Test
    public void testGetKeywords_withoutPageSettings() throws Exception {
        Assert.assertEquals("", new PageSettingsManager(null).getKeywords());
    }

    @Test
    public void testGetKeywordsGroupsId() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(TestUtil.createPage(site));
        draftPageSettings.setKeywordsGroupsId(Arrays.asList(1, 2, 3));
        Assert.assertEquals(3, new PageSettingsManager(draftPageSettings).getKeywordsGroupsId().size());
        Assert.assertEquals(true, new PageSettingsManager(draftPageSettings).getKeywordsGroupsId().contains(1));
        Assert.assertEquals(true, new PageSettingsManager(draftPageSettings).getKeywordsGroupsId().contains(2));
        Assert.assertEquals(true, new PageSettingsManager(draftPageSettings).getKeywordsGroupsId().contains(3));
    }

    @Test
    public void testGetKeywordsGroupsId_withoutPageSettings() throws Exception {
        Assert.assertEquals(0, new PageSettingsManager(null).getKeywordsGroupsId().size());
    }

    @Test
    public void testGetCreationDate() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(TestUtil.createPage(site));
        final Date date = new Date();
        draftPageSettings.setCreationDate(date);
        Assert.assertEquals(date, new PageSettingsManager(draftPageSettings).getCreationDate());
    }

    @Test
    public void testGetCreationDate_withoutPageSettings() throws Exception {
        Assert.assertEquals(null, new PageSettingsManager(null).getCreationDate());
    }

    @Test
    public void testGetPageSettingsId() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(TestUtil.createPage(site));
        Assert.assertEquals(draftPageSettings.getPageSettingsId(), new PageSettingsManager(draftPageSettings).getPageSettingsId());
    }

    @Test
    public void testGetPageSettingsId_withoutPageSettings() throws Exception {
        Assert.assertEquals(0, new PageSettingsManager(null).getPageSettingsId());
    }

    @Test
    public void testGetSeoSettings() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(TestUtil.createPage(site));
        final PageSEOSettings pageSEOSettings = new PageSEOSettings();
        draftPageSettings.setSeoSettings(pageSEOSettings);
        Assert.assertEquals(pageSEOSettings, new PageSettingsManager(draftPageSettings).getSeoSettings());
    }

    @Test
    public void testGetSeoSettings_withoutPageSettings() throws Exception {
        Assert.assertEquals(null, new PageSettingsManager(null).getSeoSettings());
    }

}
