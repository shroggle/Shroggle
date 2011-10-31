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
package com.shroggle.presentation.site.template;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.SiteCopierFromBlueprintMock;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.pageversion.PageVersionNormalizerMock;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.presentation.site.QuicklyCreatePagesAction;
import com.shroggle.presentation.account.dashboard.DashboardAction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystemMock;
import com.shroggle.util.persistance.Persistance;
import net.sourceforge.stripes.action.ActionBeanContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class SetSiteTemplateActionTest {

    @Before
    public void before() {
        action.setContext(new ActionBeanContext());
    }

    @Test
    public void executeWithoutSelectedTemplate() {
        Template template = new Template();
        template.setDirectory("f");
        fileSystemMock.putTemplate(template);

        Theme theme = new Theme();
        theme.setFile("g");
        template.getThemes().add(theme);

        final Site site = TestUtil.createSite();
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f0");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        action.setSelectTemplateId(null);
        action.setSelectThemeId(theme.getFile().hashCode());
        action.setSiteId(site.getSiteId());
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals("/site/setTemplate/setSiteTemplate.jsp", resolutionMock.getForwardToUrl());
        Assert.assertNotNull(action.getContext().getValidationErrors().get("error"));
    }

    @Test
    public void executeWithoutSelectedTemplate_EDITOR() {
        Template template = new Template();
        template.setDirectory("f");
        fileSystemMock.putTemplate(template);

        Theme theme = new Theme();
        theme.setFile("g");
        template.getThemes().add(theme);

        final Site site = TestUtil.createSite();
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f0");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.EDITOR);
        action.setSelectTemplateId(null);
        action.setSelectThemeId(theme.getFile().hashCode());
        action.setSiteId(site.getSiteId());
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals("/site/setTemplate/setSiteTemplate.jsp", resolutionMock.getForwardToUrl());
        Assert.assertNotNull(action.getContext().getValidationErrors().get("error"));
    }

    @Test
    public void executeWithNotFoundSelectedTemplate() {
        Template template = new Template();
        template.setDirectory("f");
        fileSystemMock.putTemplate(template);

        Theme theme = new Theme();
        theme.setFile("g");
        template.getThemes().add(theme);

        final Site site = TestUtil.createSite();
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f0");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        action.setSelectTemplateId(-1);
        action.setSelectThemeId(theme.getFile().hashCode());
        action.setSiteId(site.getSiteId());
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals("/site/setTemplate/setSiteTemplate.jsp", resolutionMock.getForwardToUrl());
        Assert.assertNotNull(action.getContext().getValidationErrors().get("error"));
    }

    @Test
    public void execute() {
        Template template = new Template();
        template.setDirectory("f");
        fileSystemMock.putTemplate(template);

        Theme theme = new Theme();
        theme.setFile("g");
        template.getThemes().add(theme);

        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        action.setSelectTemplateId(template.getDirectory().hashCode());
        action.setSelectThemeId(theme.getFile().hashCode());
        action.setSiteId(site.getSiteId());
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertNull(site.getBlueprintParent());
        Assert.assertEquals("f", site.getThemeId().getTemplateDirectory());
        Assert.assertEquals("g", site.getThemeId().getThemeCss());
        Assert.assertEquals(QuicklyCreatePagesAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeWithReset() {
        Template template = new Template();
        template.setDirectory("f");
        fileSystemMock.putTemplate(template);

        final Theme theme = new Theme();
        theme.setFile("g");
        template.getThemes().add(theme);

        final Layout layout = new Layout();
        layout.setFile("1");
        template.getLayouts().add(layout);
        fileSystemMock.addTemplateResource("f", "1", "");

        final Site site = TestUtil.createSite();
        final PageManager draftPageManager = TestUtil.createPageVersionAndPage(site);
        draftPageManager.setHtml("fff");
        draftPageManager.setLayoutFile("1");
        draftPageManager.setChanged(false);
        draftPageManager.setCss("00");

        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        action.setSelectTemplateId(template.getDirectory().hashCode());
        action.setSelectThemeId(theme.getFile().hashCode());
        action.setSiteId(site.getSiteId());
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(draftPageManager.getPage(), pageVersionNormalizerMock.getPageManager().getPage());
        Assert.assertNull(site.getBlueprintParent());
        Assert.assertNull(draftPageManager.getHtml());
        Assert.assertNull(draftPageManager.getCss());
        Assert.assertTrue(draftPageManager.isChanged());
        Assert.assertEquals("1", draftPageManager.getLayoutFile());
        Assert.assertEquals("f", site.getThemeId().getTemplateDirectory());
        Assert.assertEquals("g", site.getThemeId().getThemeCss());
        Assert.assertEquals(QuicklyCreatePagesAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeWithResetAndWithThemeIdInDraft() {
        Template template = new Template();
        template.setDirectory("f");
        fileSystemMock.putTemplate(template);

        final Theme theme = new Theme();
        theme.setFile("g");
        template.getThemes().add(theme);

        final Layout layout = new Layout();
        layout.setFile("1");
        template.getLayouts().add(layout);
        fileSystemMock.addTemplateResource("f", "1", "");

        final Site site = TestUtil.createSite();
        final PageManager draftPageManager = TestUtil.createPageVersionAndPage(site);
        draftPageManager.setLayoutFile("1");
        draftPageManager.setThemeId(new ThemeId("a", "b"));

        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        action.setSelectTemplateId(template.getDirectory().hashCode());
        action.setSelectThemeId(theme.getFile().hashCode());
        action.setSiteId(site.getSiteId());
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertNull(
                "We must reset theme id for draft page! For details see according " +
                        "field in draft page settings.", draftPageManager.getThemeId());
        Assert.assertEquals(QuicklyCreatePagesAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeWithResetWithSystem() {
        Template template = new Template();
        template.setDirectory("f");
        fileSystemMock.putTemplate(template);

        final Theme theme = new Theme();
        theme.setFile("g");
        template.getThemes().add(theme);

        final Layout layout = new Layout();
        layout.setFile("1");
        template.getLayouts().add(layout);
        fileSystemMock.addTemplateResource("f", "1", "");

        final Site site = TestUtil.createSite();
        final PageManager draftPageManager = TestUtil.createPageVersionAndPage(site);
        draftPageManager.getPage().setSystem(true);
        draftPageManager.setHtml("fff");
        draftPageManager.setLayoutFile("1");
        draftPageManager.setCss("00");

        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        action.setSelectTemplateId(template.getDirectory().hashCode());
        action.setSelectThemeId(theme.getFile().hashCode());
        action.setSiteId(site.getSiteId());
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertNull(pageVersionNormalizerMock.getPageManager());
        Assert.assertEquals("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n" +
                "fff", draftPageManager.getHtml());
        Assert.assertEquals("00", draftPageManager.getCss());
        Assert.assertEquals("1", draftPageManager.getLayoutFile());
        Assert.assertEquals("f", site.getThemeId().getTemplateDirectory());
        Assert.assertEquals("g", site.getThemeId().getThemeCss());
        Assert.assertEquals(QuicklyCreatePagesAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeWithResetWithWork() {
        Template template = new Template();
        template.setDirectory("f");
        fileSystemMock.putTemplate(template);

        final Theme theme = new Theme();
        theme.setFile("g");
        template.getThemes().add(theme);

        final Layout layout = new Layout();
        layout.setFile("1");
        template.getLayouts().add(layout);
        fileSystemMock.addTemplateResource("f", "1", "");

        final Site site = TestUtil.createSite();
        final PageManager draftPageManager = TestUtil.createPageVersionAndPage(site);
        draftPageManager.setHtml("fff");
        draftPageManager.setLayoutFile("1");
        draftPageManager.setCss("00");

        final WorkPageSettings workPageSettings =
                TestUtil.createWorkPageSettings(draftPageManager.getDraftPageSettings());
        workPageSettings.setHtml("1");
        workPageSettings.setCss("1");

        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        action.setSelectTemplateId(template.getDirectory().hashCode());
        action.setSelectThemeId(theme.getFile().hashCode());
        action.setSiteId(site.getSiteId());
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals("1", workPageSettings.getHtml());
        Assert.assertEquals("1", workPageSettings.getCss());

        Assert.assertEquals(draftPageManager.getPage(), pageVersionNormalizerMock.getPageManager().getPage());
        Assert.assertNull(draftPageManager.getHtml());
        Assert.assertTrue(draftPageManager.isChanged());
        Assert.assertNull("00", draftPageManager.getCss());
        Assert.assertEquals("1", draftPageManager.getLayoutFile());
        Assert.assertEquals("f", site.getThemeId().getTemplateDirectory());
        Assert.assertEquals("g", site.getThemeId().getThemeCss());
        Assert.assertEquals(QuicklyCreatePagesAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeWithResetAndLayoutNotFound() {
        Template template = new Template();
        template.setDirectory("f");
        fileSystemMock.putTemplate(template);

        final Theme theme = new Theme();
        theme.setFile("g");
        template.getThemes().add(theme);

        final Layout layout1 = new Layout();
        layout1.setFile("1");
        template.getLayouts().add(layout1);
        fileSystemMock.addTemplateResource("f", "1", "");

        final Layout layout2 = new Layout();
        layout2.setFile("qq");
        layout2.setUseAsDefault(true);
        template.getLayouts().add(layout2);
        fileSystemMock.addTemplateResource("f", "qq", "");

        final Site site = TestUtil.createSite();
        final PageManager draftPageManager = TestUtil.createPageVersionAndPage(site);
        draftPageManager.setHtml("fff");
        draftPageManager.setCss("00");

        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        action.setSelectTemplateId(template.getDirectory().hashCode());
        action.setSelectThemeId(theme.getFile().hashCode());
        action.setSiteId(site.getSiteId());
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(draftPageManager.getPage(), pageVersionNormalizerMock.getPageManager().getPage());
        Assert.assertNull(site.getBlueprintParent());
        Assert.assertNull(draftPageManager.getHtml());
        Assert.assertNull(draftPageManager.getCss());
        Assert.assertEquals("f", site.getThemeId().getTemplateDirectory());
        Assert.assertEquals(
                "If we can't find layout with same file we must use default if it exist!",
                "qq", draftPageManager.getLayoutFile());
        Assert.assertEquals("g", site.getThemeId().getThemeCss());
        Assert.assertEquals(QuicklyCreatePagesAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeWithResetAndLayoutNotFoundAndNotDefault() {
        Template template = new Template();
        template.setDirectory("f");
        fileSystemMock.putTemplate(template);

        final Theme theme = new Theme();
        theme.setFile("g");
        template.getThemes().add(theme);

        final Layout layout1 = new Layout();
        layout1.setFile("1");
        template.getLayouts().add(layout1);
        fileSystemMock.addTemplateResource("f", "1", "");

        final Layout layout2 = new Layout();
        layout2.setFile("qq");
        template.getLayouts().add(layout2);
        fileSystemMock.addTemplateResource("f", "qq", "");

        final Site site = TestUtil.createSite();
        final PageManager draftPageManager = TestUtil.createPageVersionAndPage(site);
        draftPageManager.setHtml("fff");
        draftPageManager.setCss("00");

        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        action.setSelectTemplateId(template.getDirectory().hashCode());
        action.setSelectThemeId(theme.getFile().hashCode());
        action.setSiteId(site.getSiteId());
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(draftPageManager.getPage(), pageVersionNormalizerMock.getPageManager().getPage());
        Assert.assertNull(site.getBlueprintParent());
        Assert.assertNull(draftPageManager.getHtml());
        Assert.assertNull(draftPageManager.getCss());
        Assert.assertEquals("f", site.getThemeId().getTemplateDirectory());
        Assert.assertEquals(
                "If we can't find layout with same file we must use default or first!",
                "1", draftPageManager.getLayoutFile());
        Assert.assertEquals("g", site.getThemeId().getThemeCss());
        Assert.assertEquals(QuicklyCreatePagesAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeCreateSiteOnBlueprint() {
        Template template = new Template();
        template.setDirectory("f");
        fileSystemMock.putTemplate(template);

        Theme theme = new Theme();
        theme.setFile("g");
        template.getThemes().add(theme);

        final Site blueprint = TestUtil.createSite();
        blueprint.setType(SiteType.BLUEPRINT);

        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        action.setSelectBlueprintId(blueprint.getSiteId());
        action.setSelectTemplateId(template.getDirectory().hashCode());
        action.setSelectThemeId(theme.getFile().hashCode());
        action.setSiteId(site.getSiteId());
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertTrue(siteCopierFromBlueprintMock.isCalled());
        Assert.assertEquals(QuicklyCreatePagesAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeCreateSiteOnBlueprintWithGallery() {
        Template template = new Template();
        template.setDirectory("f");
        fileSystemMock.putTemplate(template);

        Theme theme = new Theme();
        theme.setFile("g");
        template.getThemes().add(theme);

        final Site blueprint = TestUtil.createSite();
        blueprint.setType(SiteType.BLUEPRINT);

        final DraftForm form = TestUtil.createCustomForm(blueprint.getSiteId(), "f");

        final DraftGallery blueprintGallery = new DraftGallery();
        blueprintGallery.setFormId1(form.getFormId());
        blueprintGallery.setDataCrossWidgetId(2);
        blueprintGallery.setSiteId(blueprint.getSiteId());
        persistance.putItem(blueprintGallery);

        final DraftGalleryItem galleryItem = new DraftGalleryItem();
        galleryItem.getId().setFormItemId(23);
        blueprintGallery.addItem(galleryItem);
        persistance.putGalleryItem(galleryItem);

        final DraftGalleryLabel galleryLabel = new DraftGalleryLabel();
        galleryLabel.getId().setFormItemId(44);
        blueprintGallery.addLabel(galleryLabel);
        persistance.putGalleryLabel(galleryLabel);

        final PageManager blueprintPageVersion = TestUtil.createPageVersionAndPage(blueprint, PageVersionType.WORK);
        final WidgetItem blueprintWidgetGallery = new WidgetItem();
        blueprintWidgetGallery.setBlueprintShareble(true);
        blueprintWidgetGallery.setDraftItem(blueprintGallery);
        blueprintWidgetGallery.setCrossWidgetId(123);
        persistance.putWidget(blueprintWidgetGallery);
        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidgetGallery);

        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        action.setSelectBlueprintId(blueprint.getSiteId());
        action.setSelectTemplateId(template.getDirectory().hashCode());
        action.setSelectThemeId(theme.getFile().hashCode());
        action.setSiteId(site.getSiteId());
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertTrue(siteCopierFromBlueprintMock.isCalled());
//        Assert.assertEquals(1, blueprint.getBlueprintChilds().size());
//        Assert.assertEquals(blueprint, site.getBlueprintParent());
        Assert.assertEquals(QuicklyCreatePagesAction.class, resolutionMock.getRedirectByAction());
//        final PageManager pageVersion = new PageManager(site.getPages().get(1));
//        final WidgetItem widgetGallery = (WidgetItem) pageVersion.getWidgets().get(0);
//        final DraftGallery gallery = (DraftGallery) widgetGallery.getDraftItem();//persistance.getDraftItem(widgetGallery.getDraftItem());
//        TestUtil.assertIntAndBigInt(blueprintWidgetGallery.getCrossWidgetId(), widgetGallery.getParentCrossWidgetId());
//        Assert.assertEquals(blueprintGallery.getId(), gallery.getId());
//        Assert.assertNotNull(gallery.getDataCrossWidgetId());
//        Assert.assertEquals(1, gallery.getItems().size());
//        Assert.assertEquals(1, gallery.getLabels().size());
//        Assert.assertEquals(gallery, gallery.getLabels().get(0).getId().getGallery());
//        Assert.assertEquals(44, gallery.getLabels().get(0).getId().getFormItemId());

//        final List<SiteOnItem> siteOnItems = persistance.getSiteOnItemsByItem(form.getId());
//        Assert.assertEquals(1, siteOnItems.size());
//        Assert.assertEquals(site, siteOnItems.get(0).getSite());
    }

    @Test
    public void executeCreateSiteOnBlueprintWithGalleryWithNotFoundForm() {
        Template template = new Template();
        template.setDirectory("f");
        fileSystemMock.putTemplate(template);

        Theme theme = new Theme();
        theme.setFile("g");
        template.getThemes().add(theme);

        final Site blueprint = TestUtil.createSite();
        blueprint.setType(SiteType.BLUEPRINT);

        final DraftGallery blueprintGallery = new DraftGallery();
        blueprintGallery.setDataCrossWidgetId(2);
        blueprintGallery.setSiteId(blueprint.getSiteId());
        blueprintGallery.setFormId1(33);
        persistance.putItem(blueprintGallery);

        final DraftGalleryItem galleryItem = new DraftGalleryItem();
        galleryItem.getId().setFormItemId(23);
        blueprintGallery.addItem(galleryItem);
        persistance.putGalleryItem(galleryItem);

        final DraftGalleryLabel galleryLabel = new DraftGalleryLabel();
        galleryLabel.getId().setFormItemId(44);
        blueprintGallery.addLabel(galleryLabel);
        persistance.putGalleryLabel(galleryLabel);

        final PageManager blueprintPageVersion = TestUtil.createPageVersionAndPage(blueprint, PageVersionType.WORK);
        final WidgetItem blueprintWidgetGallery = new WidgetItem();
        blueprintWidgetGallery.setDraftItem(blueprintGallery);
        blueprintWidgetGallery.setBlueprintShareble(true);
        persistance.putWidget(blueprintWidgetGallery);
        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidgetGallery);

        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        action.setSelectBlueprintId(blueprint.getSiteId());
        action.setSelectTemplateId(template.getDirectory().hashCode());
        action.setSelectThemeId(theme.getFile().hashCode());
        action.setSiteId(site.getSiteId());
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertTrue(siteCopierFromBlueprintMock.isCalled());
        Assert.assertEquals(QuicklyCreatePagesAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeCreateSiteOnBlueprintForChildPage() {
        Template template = new Template();
        template.setDirectory("f");
        fileSystemMock.putTemplate(template);

        Theme theme = new Theme();
        theme.setFile("g");
        template.getThemes().add(theme);

        final Site blueprint = TestUtil.createSite();
        blueprint.setType(SiteType.BLUEPRINT);

        TestUtil.createPage(blueprint);
        final Page pageChild = TestUtil.createPage(blueprint);
        TestUtil.createPageVersion(pageChild, PageVersionType.WORK);

        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        action.setSelectBlueprintId(blueprint.getSiteId());
        action.setSelectTemplateId(template.getDirectory().hashCode());
        action.setSelectThemeId(theme.getFile().hashCode());
        action.setSiteId(site.getSiteId());
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertTrue(siteCopierFromBlueprintMock.isCalled());
        Assert.assertEquals(QuicklyCreatePagesAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeCreateSiteOnBlueprintWithItemsOverRight() {
        final Template template = new Template();
        template.setDirectory("f");
        fileSystemMock.putTemplate(template);

        final Theme theme = new Theme();
        theme.setFile("g");
        template.getThemes().add(theme);

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(TestUtil.createSite(), PageVersionType.WORK);
        final Site blueprint = pageVersion.getPage().getSite();
        blueprint.setType(SiteType.BLUEPRINT);

        final WidgetItem widgetBlog = TestUtil.createWidgetItem();
        widgetBlog.setBlueprintShareble(true);
        persistance.putWidget(widgetBlog);
        final DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);
        widgetBlog.setDraftItem(blog);
        pageVersion.getWorkPageSettings().addWidget(widgetBlog);

        final SiteOnItem blueprintItemRight = blog.createSiteOnItemRight(blueprint);
        persistance.putSiteOnItem(blueprintItemRight);

        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        action.setSelectBlueprintId(blueprint.getSiteId());
        action.setSelectTemplateId(template.getDirectory().hashCode());
        action.setSelectThemeId(theme.getFile().hashCode());
        action.setSiteId(site.getSiteId());
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertTrue(siteCopierFromBlueprintMock.isCalled());
        Assert.assertEquals(QuicklyCreatePagesAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeCreateSiteOnBlueprintWithItemsOverOwner() {
        Template template = new Template();
        template.setDirectory("f");
        fileSystemMock.putTemplate(template);

        Theme theme = new Theme();
        theme.setFile("g");
        template.getThemes().add(theme);

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(TestUtil.createSite(), PageVersionType.WORK);
        final Site blueprint = pageVersion.getSite();
        blueprint.setType(SiteType.BLUEPRINT);

        final WidgetItem widgetBlog = TestUtil.createWidgetItem();
        widgetBlog.setBlueprintShareble(true);
        persistance.putWidget(widgetBlog);
        final DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);
        widgetBlog.setDraftItem(blog);
        pageVersion.getWorkPageSettings().addWidget(widgetBlog);

        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        action.setSelectBlueprintId(blueprint.getSiteId());
        action.setSelectTemplateId(template.getDirectory().hashCode());
        action.setSelectThemeId(theme.getFile().hashCode());
        action.setSiteId(site.getSiteId());
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertTrue(siteCopierFromBlueprintMock.isCalled());
        Assert.assertEquals(QuicklyCreatePagesAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeSiteOnBlueprint() {
        Template template = new Template();
        template.setDirectory("f");
        fileSystemMock.putTemplate(template);

        Theme theme = new Theme();
        theme.setFile("g");
        template.getThemes().add(theme);

        Theme theme2 = new Theme();
        theme2.setFile("g1");
        template.getThemes().add(theme2);

        final Layout layout1 = new Layout();
        layout1.setFile("1");
        template.getLayouts().add(layout1);
        fileSystemMock.addTemplateResource("f", "1", "");

        final Site blueprint = TestUtil.createSite();
        blueprint.setType(SiteType.BLUEPRINT);

        TestUtil.createPageVersionAndPage(blueprint, PageVersionType.WORK);

        final Site site = TestUtil.createSite();
        blueprint.addBlueprintChild(site);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        TestUtil.createPageVersionAndPage(site, PageVersionType.WORK);

        action.setSelectBlueprintId(blueprint.getSiteId());
        action.setSelectTemplateId(template.getDirectory().hashCode());
        action.setSelectThemeId(theme2.getFile().hashCode());
        action.setSiteId(site.getSiteId());
        action.setEditingMode(true);
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(1, blueprint.getBlueprintChilds().size());
        Assert.assertEquals(2, site.getPages().size());
        Assert.assertEquals(blueprint, site.getBlueprintParent());
        Assert.assertEquals(theme2.getFile(), site.getThemeId().getThemeCss());
        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeSiteOnBlueprintResetBlueprint() {
        Template template = new Template();
        template.setDirectory("f");
        fileSystemMock.putTemplate(template);

        final Theme theme = new Theme();
        theme.setFile("g");
        template.getThemes().add(theme);

        final Theme theme2 = new Theme();
        theme2.setFile("g1");
        template.getThemes().add(theme2);

        final Layout layout1 = new Layout();
        layout1.setFile("1");
        template.getLayouts().add(layout1);
        fileSystemMock.addTemplateResource("f", "1", "");

        final Site blueprint = TestUtil.createSite();
        blueprint.setType(SiteType.BLUEPRINT);

        TestUtil.createPageVersionAndPage(blueprint, PageVersionType.WORK);

        final Site site = TestUtil.createSite();
        blueprint.addBlueprintChild(site);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);

        final SiteOnItem itemRight = blog.createSiteOnItemRight(site);
        persistance.putSiteOnItem(itemRight);
        final SiteOnItem itemRightBlueprint = blog.createSiteOnItemRight(site);
        itemRightBlueprint.setFromBlueprint(true);
        persistance.putSiteOnItem(itemRightBlueprint);

        TestUtil.createPageVersionAndPage(site, PageVersionType.WORK);

        action.setSelectTemplateId(template.getDirectory().hashCode());
        action.setSelectThemeId(theme2.getFile().hashCode());
        action.setSiteId(site.getSiteId());
        action.setEditingMode(true);
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(0, blueprint.getBlueprintChilds().size());
        Assert.assertEquals(2, site.getPages().size());
        Assert.assertNull(site.getBlueprintParent());
        Assert.assertEquals(SiteType.COMMON, site.getType());
        Assert.assertEquals(theme2.getFile(), site.getThemeId().getThemeCss());
        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void showEditSiteOnBlueprint() {
        Template template = new Template();
        template.setDirectory("f");
        fileSystemMock.putTemplate(template);

        Theme theme = new Theme();
        theme.setFile("g");
        template.getThemes().add(theme);

        final Site blueprint = TestUtil.createSite();
        blueprint.getThemeId().setTemplateDirectory("f");
        blueprint.getThemeId().setThemeCss("g");
        blueprint.setType(SiteType.BLUEPRINT);

        final Site site = TestUtil.createSite();
        blueprint.addBlueprintChild(site);
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("g");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        action.setSiteId(site.getSiteId());
        action.setEditingMode(true);
        ResolutionMock resolutionMock = (ResolutionMock) action.show();

        Assert.assertEquals(1, blueprint.getBlueprintChilds().size());
        Assert.assertEquals(blueprint, site.getBlueprintParent());
        Assert.assertEquals((Integer) template.getDirectory().hashCode(), action.getSelectTemplateId());
        Assert.assertEquals((Integer) blueprint.getSiteId(), action.getSelectBlueprintId());
        Assert.assertEquals((Integer) theme.getFile().hashCode(), action.getSelectThemeId());
        Assert.assertEquals("/site/setTemplate/setSiteTemplate.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void showNew() {
        Template template = new Template();
        template.setDirectory("f");
        fileSystemMock.putTemplate(template);

        Theme theme = new Theme();
        theme.setFile("g");
        template.getThemes().add(theme);

        final Site site = TestUtil.createSite();
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("g");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        action.setSiteId(site.getSiteId());
        action.setEditingMode(false);
        final ResolutionMock resolutionMock = (ResolutionMock) action.show();

        Assert.assertNull(action.getSelectTemplateId());
        Assert.assertNull(action.getSelectThemeId());
        Assert.assertEquals("/site/setTemplate/setSiteTemplate.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void showEdit() {
        Template template = new Template();
        template.setDirectory("f");
        fileSystemMock.putTemplate(template);

        Theme theme = new Theme();
        theme.setFile("g");
        template.getThemes().add(theme);

        final Site site = TestUtil.createSite();
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("g");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        action.setSiteId(site.getSiteId());
        action.setEditingMode(true);
        final ResolutionMock resolutionMock = (ResolutionMock) action.show();

        TestUtil.assertIntAndBigInt("f".hashCode(), action.getSelectTemplateId());
        TestUtil.assertIntAndBigInt("g".hashCode(), action.getSelectThemeId());
        Assert.assertEquals("/site/setTemplate/setSiteTemplate.jsp", resolutionMock.getForwardToUrl());
    }

    private final SetSiteTemplateAction action = new SetSiteTemplateAction();
    private final FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final SiteCopierFromBlueprintMock siteCopierFromBlueprintMock =
            (SiteCopierFromBlueprintMock) ServiceLocator.getSiteCopierFromBlueprint();
    private final PageVersionNormalizerMock pageVersionNormalizerMock
            = (PageVersionNormalizerMock) ServiceLocator.getPageVersionNormalizer();

}
