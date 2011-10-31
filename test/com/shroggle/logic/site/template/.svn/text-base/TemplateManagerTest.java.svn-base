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
package com.shroggle.logic.site.template;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.TemplateNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystemMock;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class TemplateManagerTest {

    @Test(expected = UnsupportedOperationException.class)
    public void createPageWithNotFoundLayout() {
        Template template = new Template();
        TemplateManager templateManager = new TemplateManager(template);
        final Site site = TestUtil.createSite();

        templateManager.createPage(PageType.BLOG, site, "f");
    }

    @Test
    public void createPageWithoutPattern() {
        final Template template = new Template();
        template.setDirectory("gh");
        final Layout layout = new Layout();
        layout.setFile("g");
        template.getLayouts().add(layout);
        TemplateManager templateManager = new TemplateManager(template);
        final Site site = TestUtil.createSite();
        site.getThemeId().setThemeCss("g");
        site.getThemeId().setTemplateDirectory("gh");
        fileSystemMock.addTemplateResource("gh", "g", "<!-- MEDIA_BLOCK --><!-- MEDIA_BLOCK -->");

        final PageManager pageVersion = templateManager.createPage(PageType.BLOG, site, "f").getPageManager();

        Assert.assertEquals(PageType.BLOG, pageVersion.getPage().getType());
        Assert.assertEquals("f", pageVersion.getName());
        Assert.assertEquals(3, pageVersion.getWidgets().size());
        WidgetComposit composit = (WidgetComposit) pageVersion.getWidgets().get(1);
        Assert.assertEquals(1, composit.getChilds().size());
        Assert.assertEquals(true, composit.getChilds().get(0).isWidgetItem());
    }

    @Test
    public void createPage() {
        final Template template = new Template();
        template.setDirectory("gh");

        final Layout otherLayout = TestUtil.createLayout(template, "g");
        TestUtil.createLayoutPattern(otherLayout, PageType.BLANK);

        final Layout layout = TestUtil.createLayout(template, "g");
        final LayoutPattern otherPattern = TestUtil.createLayoutPattern(layout, PageType.BLANK);
        TestUtil.createPatternPosition(otherPattern, ItemType.BLOG, 0);
        final LayoutPattern pattern = TestUtil.createLayoutPattern(layout, PageType.BLOG);
        TestUtil.createPatternPosition(pattern, ItemType.BLOG, 2);

        final Site site = TestUtil.createSite();
        site.getThemeId().setTemplateDirectory("gh");
        fileSystemMock.addTemplateResource(
                "gh", "g", "<!-- MEDIA_BLOCK --><!-- MEDIA_BLOCK --><!-- MEDIA_BLOCK -->");

        final TemplateManager templateManager = new TemplateManager(template);
        final PageManager pageVersion = templateManager.createPage(PageType.BLOG, site, "f").getPageManager();

        Assert.assertEquals(PageType.BLOG, pageVersion.getPage().getType());
        Assert.assertEquals("f", pageVersion.getName());
        Assert.assertEquals(4, pageVersion.getWidgets().size());
        WidgetComposit composit = (WidgetComposit) pageVersion.getWidgets().get(2);
        Assert.assertEquals(1, composit.getChilds().size());
        Assert.assertEquals(true, composit.getChilds().get(0).isWidgetItem());
    }

    @Test
    public void createPageWithSiteHtml() {
        final Template template = new Template();
        template.setDirectory("gh");

        final Layout otherLayout = TestUtil.createLayout(template, "g");
        TestUtil.createLayoutPattern(otherLayout, PageType.BLANK);

        final Layout layout = TestUtil.createLayout(template, "g");
        final LayoutPattern otherPattern = TestUtil.createLayoutPattern(layout, PageType.BLANK);
        TestUtil.createPatternPosition(otherPattern, ItemType.BLOG, 0);
        final LayoutPattern pattern = TestUtil.createLayoutPattern(layout, PageType.BLOG);
        TestUtil.createPatternPosition(pattern, ItemType.BLOG, 2);

        final Site site = TestUtil.createSite();
        site.setHtml(new Html());
        site.getHtml().setValue("<!-- MEDIA_BLOCK -->1");
        site.getThemeId().setTemplateDirectory("gh");
        fileSystemMock.addTemplateResource(
                "gh", "g", "<!-- MEDIA_BLOCK --><!-- MEDIA_BLOCK --><!-- MEDIA_BLOCK -->");

        final TemplateManager templateManager = new TemplateManager(template);
        final PageManager pageVersion = templateManager.createPage(PageType.BLOG, site, "f").getPageManager();

        Assert.assertEquals(PageType.BLOG, pageVersion.getPage().getType());
        Assert.assertEquals("f", pageVersion.getName());
        Assert.assertEquals("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n" +
                "<!-- MEDIA_BLOCK -->1", pageVersion.getHtml());
        Assert.assertEquals(2, pageVersion.getWidgets().size());
        WidgetComposit composit = (WidgetComposit) pageVersion.getWidgets().get(0);
        Assert.assertEquals(1, composit.getChilds().size());
        Assert.assertEquals(true, composit.getChilds().get(0).isWidgetItem());
    }

    @Test
    public void createPageContact() {
        final Template template = new Template();
        template.setDirectory("gh");

        final Layout layout = TestUtil.createLayout(template, "g");
        final LayoutPattern pattern = TestUtil.createLayoutPattern(layout, PageType.CONTACT);
        TestUtil.createPatternPosition(pattern, ItemType.CONTACT_US, 0);

        final Site site = TestUtil.createSite();
        site.getThemeId().setTemplateDirectory("gh");
        fileSystemMock.addTemplateResource("gh", "g", "<!-- MEDIA_BLOCK -->");

        final TemplateManager templateManager = new TemplateManager(template);
        final PageManager pageVersion = templateManager.createPage(PageType.CONTACT, site, "f").getPageManager();

        Assert.assertEquals(PageType.CONTACT, pageVersion.getPage().getType());
        final WidgetComposit composit = (WidgetComposit) pageVersion.getWidgets().get(0);
        Assert.assertEquals(1, composit.getChilds().size());
        final WidgetItem contactUsWidget = (WidgetItem) composit.getChilds().get(0);
        Assert.assertEquals(true, contactUsWidget.isWidgetItem());
        final DraftForm form = (DraftForm)contactUsWidget.getDraftItem();//persistance.getFormById(contactUsWidget.getDraftItem());
        Assert.assertEquals(ItemType.CONTACT_US, form.getItemType());
        Assert.assertEquals("Contact Us1", form.getName());
        Assert.assertEquals(5, form.getFormItems().size());
    }

    @Test
    public void createPageWithoutPosition() {
        final Template template = new Template();
        template.setDirectory("gh");

        final Layout layout = new Layout();
        layout.setFile("g");
        template.getLayouts().add(layout);

        TestUtil.createLayoutPattern(layout, PageType.BLOG);

        final Site site = TestUtil.createSite();
        site.getThemeId().setThemeCss("g");
        site.getThemeId().setTemplateDirectory("gh");
        fileSystemMock.addTemplateResource(
                "gh", "g", "<!-- MEDIA_BLOCK --><!-- MEDIA_BLOCK --><!-- MEDIA_BLOCK -->");

        final TemplateManager templateManager = new TemplateManager(template);
        final PageManager pageVersion = templateManager.createPage(PageType.BLOG, site, "f").getPageManager();

        Assert.assertEquals(PageType.BLOG, pageVersion.getPage().getType());
        Assert.assertEquals("f", pageVersion.getName());
        Assert.assertEquals(4, pageVersion.getWidgets().size());
        WidgetComposit composit = (WidgetComposit) pageVersion.getWidgets().get(1);
        Assert.assertEquals(1, composit.getChilds().size());
        Assert.assertEquals(true, composit.getChilds().get(0).isWidgetItem());
    }

    @Test
    public void createPageWithDefaultPattern() {
        final Template template = new Template();
        template.setDirectory("gh");

        final Layout layout = new Layout();
        layout.setFile("g");
        template.getLayouts().add(layout);

        final LayoutPattern otherPattern = TestUtil.createLayoutPattern(layout, PageType.BLANK);
        TestUtil.createPatternPosition(otherPattern, ItemType.BLOG, 0);
        final LayoutPattern pattern = TestUtil.createLayoutPattern(layout);
        TestUtil.createPatternPosition(pattern, ItemType.BLOG, 2);

        final Site site = TestUtil.createSite();
        site.getThemeId().setThemeCss("g");
        site.getThemeId().setTemplateDirectory("gh");
        fileSystemMock.addTemplateResource(
                "gh", "g", "<!-- MEDIA_BLOCK --><!-- MEDIA_BLOCK --><!-- MEDIA_BLOCK -->");

        final TemplateManager templateManager = new TemplateManager(template);
        final PageManager pageVersion = templateManager.createPage(PageType.BLOG, site, "f").getPageManager();

        Assert.assertEquals(PageType.BLOG, pageVersion.getPage().getType());
        Assert.assertEquals("f", pageVersion.getName());
        Assert.assertEquals(4, pageVersion.getWidgets().size());
        WidgetComposit composit = (WidgetComposit) pageVersion.getWidgets().get(2);
        Assert.assertEquals(1, composit.getChilds().size());
        Assert.assertEquals(true, composit.getChilds().get(0).isWidgetItem());
    }

    @Test
    public void getThemeColorTileUrlsWithoutTheme() {
        Template template = new Template();
        TemplateManager templateManager = new TemplateManager(template);

        Map<Theme, String> themeColorTileUrls = templateManager.getThemeColorTileUrls();
        Assert.assertNotNull(themeColorTileUrls);
        Assert.assertEquals(0, themeColorTileUrls.size());
    }

    @Test
    public void getThemeColorTileUrls() {
        Template template = new Template();
        template.setDirectory("red");
        Theme theme0 = new Theme();
        theme0.setColorTileFile("simple");
        theme0.setTemplate(template);
        template.getThemes().add(theme0);
        Theme theme1 = new Theme();
        theme1.setColorTileFile("ear");
        template.getThemes().add(theme1);
        theme1.setTemplate(template);
        TemplateManager templateManager = new TemplateManager(template);
        fileSystemMock.setThemeColorTileUrl(theme0, "aa");
        fileSystemMock.setThemeColorTileUrl(theme1, "bb");

        Map<Theme, String> themeColorTileUrls = templateManager.getThemeColorTileUrls();
        Assert.assertNotNull(themeColorTileUrls);
        Assert.assertEquals(2, themeColorTileUrls.size());
        Assert.assertEquals("aa", themeColorTileUrls.get(theme0));
        Assert.assertEquals("bb", themeColorTileUrls.get(theme1));
    }

    @Test
    public void getCorrectName() {
        Template template = new Template();
        template.setName("f");
        template.setDirectory("f1");
        TemplateManager templateManager = new TemplateManager(template);
        Assert.assertEquals("f", templateManager.getCorrectName());
    }

    @Test
    public void createWithDirectory() {
        Template template = new Template();
        template.setName("f");
        template.setDirectory("f1");
        fileSystemMock.putTemplate(template);

        TemplateManager templateManager = new TemplateManager("f1");
        Assert.assertEquals(template, templateManager.getTemplate());
    }

    @Test(expected = TemplateNotFoundException.class)
    public void createWithUnknownDirectory() {
        new TemplateManager("f1");
    }

    @Test
    public void getCorrectNameWithoutName() {
        Template template = new Template();
        template.setDirectory("f1");
        TemplateManager templateManager = new TemplateManager(template);
        Assert.assertEquals("f1", templateManager.getCorrectName());
    }

    @Test
    public void compareWithName() {
        Template template1 = new Template();
        template1.setName("f1");

        Template template2 = new Template();
        template2.setName("f2");

        TemplateManager templateWorker1 = new TemplateManager(template1);
        TemplateManager templateWorker2 = new TemplateManager(template2);

        Assert.assertEquals(-1, templateWorker1.compareTo(templateWorker2));
    }

    @Test
    public void compareWithNameInDifferendCasae() {
        Template template1 = new Template();
        template1.setName("a");

        Template template2 = new Template();
        template2.setName("A");

        TemplateManager templateWorker1 = new TemplateManager(template1);
        TemplateManager templateWorker2 = new TemplateManager(template2);

        Assert.assertEquals(0, templateWorker1.compareTo(templateWorker2));
    }

    @Test
    public void compareWithSameOrder() {
        Template template1 = new Template();
        template1.setOrder(1);

        Template template2 = new Template();
        template2.setOrder(1);

        TemplateManager templateWorker1 = new TemplateManager(template1);
        TemplateManager templateWorker2 = new TemplateManager(template2);

        Assert.assertEquals(0, templateWorker1.compareTo(templateWorker2));
    }

    @Test
    public void compareWithOrder() {
        Template template1 = new Template();
        template1.setOrder(1);

        Template template2 = new Template();
        template2.setOrder(12);

        TemplateManager templateWorker1 = new TemplateManager(template1);
        TemplateManager templateWorker2 = new TemplateManager(template2);

        Assert.assertEquals(-1, templateWorker1.compareTo(templateWorker2));
    }

    @Test
    public void compareWithoutOrderInOne() {
        Template template1 = new Template();
        template1.setOrder(1);

        Template template2 = new Template();
        template2.setName("a");

        TemplateManager templateWorker1 = new TemplateManager(template1);
        TemplateManager templateWorker2 = new TemplateManager(template2);

        Assert.assertEquals(-1, templateWorker1.compareTo(templateWorker2));
    }

    private final FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
