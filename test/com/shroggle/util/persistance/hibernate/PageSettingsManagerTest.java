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
package com.shroggle.util.persistance.hibernate;

import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageSettingsManager;
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithHibernateService.class)
public class PageSettingsManagerTest {

    @Test
    public void testCopyPageSettingsTo() throws Exception {
        final Persistance persistance = ServiceLocator.getPersistance();
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final Page newPage = TestUtil.createPage(site);


        final DraftPageSettings pageSettings = new DraftPageSettings();
        pageSettings.setPage(page);
        final Date oldCreationDate = new Date(System.currentTimeMillis() - 1000000000L);
        pageSettings.setCreationDate(oldCreationDate);

        /*----------------------------------------------General Settings----------------------------------------------*/
        pageSettings.setBlueprintLocked(true);
        pageSettings.setBlueprintNotEditable(true);
        pageSettings.setBlueprintRequired(true);
        pageSettings.setCss("oldCss");
        pageSettings.setHtml("oldHtml");
        pageSettings.setKeywords("oldKeywords");
        pageSettings.setLayoutFile("oldLayoutFile");
        pageSettings.setName("oldName");
        pageSettings.setOwnDomainName("oldDomainName");
        pageSettings.setTitle("oldTitle");
        pageSettings.setUrl("oldUrl");

        final KeywordsGroup keywordsGroup = new KeywordsGroup();
        keywordsGroup.setValue("value");
        keywordsGroup.setName("name");
        keywordsGroup.setSite(site);
        persistance.putKeywordsGroup(keywordsGroup);

        pageSettings.setKeywordsGroupsId(Arrays.asList(keywordsGroup.getKeywordsGroupId()));
        final PageSEOSettings pageSEOSettings = new PageSEOSettings();
        pageSEOSettings.setPageDescription("oldPageDescription");
        pageSEOSettings.setTitleMetaTag("oldTitleMetaTag");

        pageSEOSettings.setAuthorMetaTag("AuthorMetaTag");
        pageSEOSettings.setCopyrightMetaTag("CopyrightMetaTag");
        pageSEOSettings.setCustomMetaTagList(Arrays.asList("customMetaTag1", "customMetaTag2"));
        final SEOHtmlCode seoHtmlCode = new SEOHtmlCode();
        seoHtmlCode.setCode("code");
        seoHtmlCode.setCodePlacement(CodePlacement.BEGINNING);
        seoHtmlCode.setName("name");
        pageSEOSettings.setHtmlCodeList(Arrays.asList(seoHtmlCode));
        pageSEOSettings.setRobotsMetaTag("RobotsMetaTag");

        pageSettings.setSeoSettings(pageSEOSettings);
        final ThemeId themeId = new ThemeId();
        themeId.setTemplateDirectory("oldTemplateDirectory");
        themeId.setThemeCss("oldThemeCss");
        pageSettings.setThemeId(themeId);

        final AccessibleSettings accessibleSettings = new AccessibleSettings();
        accessibleSettings.setAccess(AccessForRender.RESTRICTED);
        accessibleSettings.setAdministrators(true);
        accessibleSettings.setVisitors(true);
        accessibleSettings.setVisitorsGroups(Arrays.asList(5, 6, 7));
        persistance.putAccessibleSettings(accessibleSettings);
        pageSettings.setAccessibleSettings(accessibleSettings);

        final Background background = TestUtil.createBackground();
        pageSettings.setBackground(background);

        final Border border = TestUtil.createBorder();
        pageSettings.setBorder(border);
        /*----------------------------------------------General Settings----------------------------------------------*/

        /*---------------------------------------------------Widgets--------------------------------------------------*/
        final WidgetComposit widgetComposit = TestUtil.createWidgetComposit();
        persistance.getItemSize(widgetComposit.getItemSizeId()).setWidth(100);
        widgetComposit.setCrossWidgetId(100);

        final WidgetComposit widgetComposit2 = TestUtil.createWidgetComposit();
        persistance.getItemSize(widgetComposit2.getItemSizeId()).setWidth(200);
        widgetComposit2.setCrossWidgetId(200);

        final WidgetItem widgetItem = TestUtil.createWidgetItem();
        persistance.getItemSize(widgetItem.getItemSizeId()).setWidth(300);
        widgetItem.setCrossWidgetId(300);

        final WidgetItem widgetItem2 = TestUtil.createWidgetItem();
        persistance.getItemSize(widgetItem2.getItemSizeId()).setWidth(400);
        widgetItem2.setCrossWidgetId(400);

        final DraftText text = new DraftText();
        text.setText("text");
        persistance.putItem(text);
        widgetItem2.setDraftItem(text);

        widgetComposit.addChild(widgetItem);
        widgetComposit2.addChild(widgetItem2);
        HibernateManager.get().flush();


        final List<Widget> widgetsId = Arrays.asList(widgetComposit, widgetComposit2, widgetItem, widgetItem2);
        pageSettings.setWidgets(widgetsId);
        /*---------------------------------------------------Widgets--------------------------------------------------*/


        final DraftPageSettings copiedPageSettings = new DraftPageSettings();
        copiedPageSettings.setPageSettingsId(12345);
        copiedPageSettings.setPage(newPage);
        new PageSettingsManager(pageSettings).copyPageSettingsTo(copiedPageSettings);

        

        Assert.assertNotSame(pageSettings.getPageSettingsId(), copiedPageSettings.getPageSettingsId());
        Assert.assertEquals("PageSettingsId must not be changed after copying.", 12345, copiedPageSettings.getPageSettingsId());
        Assert.assertEquals("Page must not be changed after copying.", newPage, copiedPageSettings.getPage());
        Assert.assertNotSame(oldCreationDate, copiedPageSettings.getCreationDate());
        Assert.assertNotSame("CreationDate must be equals to current date after copying. ", DateUtil.toMonthDayAndYear(new Date()), DateUtil.toMonthDayAndYear(copiedPageSettings.getCreationDate()));
        /*-----------------------------------------Checking General Settings------------------------------------------*/
        Assert.assertEquals(true, copiedPageSettings.isBlueprintLocked());
        Assert.assertEquals(true, copiedPageSettings.isBlueprintLocked());
        Assert.assertEquals(true, copiedPageSettings.isBlueprintNotEditable());
        Assert.assertEquals(true, copiedPageSettings.isBlueprintRequired());
        Assert.assertEquals("oldCss", copiedPageSettings.getCss());
        Assert.assertEquals("oldHtml", copiedPageSettings.getHtml());
        Assert.assertEquals("oldKeywords", copiedPageSettings.getKeywords());
        Assert.assertEquals("oldLayoutFile", copiedPageSettings.getLayoutFile());
        Assert.assertEquals("oldName", copiedPageSettings.getName());
        Assert.assertEquals("oldDomainName", copiedPageSettings.getOwnDomainName());
        Assert.assertEquals("oldTitle", copiedPageSettings.getTitle());
        Assert.assertEquals("oldUrl", copiedPageSettings.getUrl());
        Assert.assertEquals(1, copiedPageSettings.getKeywordsGroupsId().size());
        Assert.assertEquals(false, copiedPageSettings.getKeywordsGroupsId().contains(keywordsGroup.getKeywordsGroupId()));
        final KeywordsGroup newKeywordsGroup = ServiceLocator.getPersistance().getKeywordsGroupById(copiedPageSettings.getKeywordsGroupsId().get(0));
        Assert.assertNotSame(keywordsGroup.getKeywordsGroupId(), newKeywordsGroup.getKeywordsGroupId());
        Assert.assertEquals("name", newKeywordsGroup.getName());
        Assert.assertEquals("value", newKeywordsGroup.getValue());
        Assert.assertEquals(site, newKeywordsGroup.getSite());

        Assert.assertEquals("oldPageDescription", copiedPageSettings.getSeoSettings().getPageDescription());
        Assert.assertEquals("oldTitleMetaTag", copiedPageSettings.getSeoSettings().getTitleMetaTag());
        Assert.assertEquals("AuthorMetaTag", copiedPageSettings.getSeoSettings().getAuthorMetaTag());
        Assert.assertEquals("CopyrightMetaTag", copiedPageSettings.getSeoSettings().getCopyrightMetaTag());

        Assert.assertEquals(2, copiedPageSettings.getSeoSettings().getCustomMetaTagList().size());
        Assert.assertEquals(true, copiedPageSettings.getSeoSettings().getCustomMetaTagList().contains("customMetaTag1"));
        Assert.assertEquals(true, copiedPageSettings.getSeoSettings().getCustomMetaTagList().contains("customMetaTag2"));
        Assert.assertEquals(1, copiedPageSettings.getSeoSettings().getHtmlCodeList().size());
        Assert.assertEquals("code", copiedPageSettings.getSeoSettings().getHtmlCodeList().get(0).getCode());
        Assert.assertEquals("name", copiedPageSettings.getSeoSettings().getHtmlCodeList().get(0).getName());
        Assert.assertEquals(CodePlacement.BEGINNING, copiedPageSettings.getSeoSettings().getHtmlCodeList().get(0).getCodePlacement());
        Assert.assertEquals("RobotsMetaTag", copiedPageSettings.getSeoSettings().getRobotsMetaTag());

        Assert.assertEquals("oldTemplateDirectory", copiedPageSettings.getThemeId().getTemplateDirectory());
        Assert.assertEquals("oldThemeCss", copiedPageSettings.getThemeId().getThemeCss());

        Assert.assertEquals(AccessForRender.RESTRICTED, copiedPageSettings.getAccessibleSettings().getAccess());
        Assert.assertEquals(true, copiedPageSettings.getAccessibleSettings().isAdministrators());
        Assert.assertEquals(true, copiedPageSettings.getAccessibleSettings().isVisitors());
        Assert.assertEquals(3, copiedPageSettings.getAccessibleSettings().getVisitorsGroups().size());
        Assert.assertEquals(true, copiedPageSettings.getAccessibleSettings().getVisitorsGroups().contains(5));
        Assert.assertEquals(true, copiedPageSettings.getAccessibleSettings().getVisitorsGroups().contains(6));
        Assert.assertEquals(true, copiedPageSettings.getAccessibleSettings().getVisitorsGroups().contains(7));

        Assert.assertNotSame(border.getId(), copiedPageSettings.getBorder().getId());
        Assert.assertEquals(border.getSiteId(), copiedPageSettings.getBorder().getSiteId());

        Assert.assertNotSame(background.getId(), copiedPageSettings.getBackground().getId());
        Assert.assertEquals(background.getSiteId(), copiedPageSettings.getBackground().getSiteId());
        Assert.assertEquals(background.getBackgroundPosition(), copiedPageSettings.getBackground().getBackgroundPosition());
        Assert.assertEquals(background.getBackgroundColor(), copiedPageSettings.getBackground().getBackgroundColor());
        Assert.assertEquals(background.getBackgroundImageId(), copiedPageSettings.getBackground().getBackgroundImageId());
        /*-----------------------------------------Checking General Settings------------------------------------------*/

        /*----------------------------------------------Checking Widgets----------------------------------------------*/
        Assert.assertEquals(4, copiedPageSettings.getWidgets().size());
        Assert.assertNotSame(widgetsId, copiedPageSettings.getWidgets());
        final List<Widget> copiedWidgets = new PageSettingsManager(copiedPageSettings).getWidgets();

        final WidgetComposit widgetCompositCopy = (WidgetComposit) selectWidgetByCrossWidgetId(copiedWidgets, widgetComposit.getCrossWidgetId());
        Assert.assertNotNull(widgetCompositCopy);
        Assert.assertEquals(100, persistance.getItemSize(widgetCompositCopy.getItemSizeId()).getWidth());
        Assert.assertEquals(100, widgetCompositCopy.getCrossWidgetId());
        Assert.assertEquals(1, widgetCompositCopy.getChilds().size());
        Assert.assertNotSame(widgetItem.getWidgetId(), widgetCompositCopy.getChilds().get(0).getWidgetId());
        Assert.assertTrue(copiedPageSettings.getWidgets().contains(widgetCompositCopy.getChilds().get(0)));
        Assert.assertEquals(300, persistance.getItemSize(widgetCompositCopy.getChilds().get(0).getItemSizeId()).getWidth());


        final WidgetComposit widgetComposit2Copy = (WidgetComposit) selectWidgetByCrossWidgetId(copiedWidgets, widgetComposit2.getCrossWidgetId());
        Assert.assertNotNull(widgetComposit2Copy);
        Assert.assertEquals(200, persistance.getItemSize(widgetComposit2Copy.getItemSizeId()).getWidth());
        Assert.assertEquals(200, widgetComposit2Copy.getCrossWidgetId());
        Assert.assertEquals(1, widgetComposit2Copy.getChilds().size());
        Assert.assertNotSame(widgetItem2.getWidgetId(), widgetComposit2Copy.getChilds().get(0).getWidgetId());
        Assert.assertTrue(copiedPageSettings.getWidgets().contains(widgetComposit2Copy.getChilds().get(0)));
        Assert.assertEquals(400, persistance.getItemSize(widgetComposit2Copy.getChilds().get(0).getItemSizeId()).getWidth());


        final WidgetItem widgetItemCopy = (WidgetItem) selectWidgetByCrossWidgetId(copiedWidgets, widgetItem.getCrossWidgetId());
        Assert.assertNotNull(widgetItemCopy);
        Assert.assertEquals(300, persistance.getItemSize(widgetItemCopy.getItemSizeId()).getWidth());
        Assert.assertEquals(300, widgetItemCopy.getCrossWidgetId());
        Assert.assertNull(widgetItemCopy.getDraftItem());
        Assert.assertNotNull(widgetItemCopy.getParent());
        Assert.assertNotSame(widgetComposit.getWidgetId(), widgetItemCopy.getParent().getWidgetId());
        Assert.assertEquals(widgetCompositCopy.getWidgetId(), widgetItemCopy.getParent().getWidgetId());


        final WidgetItem widgetItem2Copy = (WidgetItem) selectWidgetByCrossWidgetId(copiedWidgets, widgetItem2.getCrossWidgetId());
        Assert.assertNotNull(widgetItem2Copy);
        Assert.assertEquals(400, persistance.getItemSize(widgetItem2Copy.getItemSizeId()).getWidth());
        Assert.assertEquals(400, widgetItem2Copy.getCrossWidgetId());
        Assert.assertEquals(text, widgetItem2Copy.getDraftItem());
        Assert.assertNotNull(widgetItem2Copy.getParent());
        Assert.assertNotSame(widgetComposit2.getWidgetId(), widgetItem2Copy.getParent().getWidgetId());
        Assert.assertEquals(widgetComposit2Copy.getWidgetId(), widgetItem2Copy.getParent().getWidgetId());
        /*----------------------------------------------Checking Widgets----------------------------------------------*/

    }

    private Widget selectWidgetByCrossWidgetId(final List<Widget> widgets, final int crossWidgetId) {
        for (Widget widget : widgets) {
            if (widget.getCrossWidgetId() == crossWidgetId) {
                return widget;
            }
        }
        return null;
    }
}