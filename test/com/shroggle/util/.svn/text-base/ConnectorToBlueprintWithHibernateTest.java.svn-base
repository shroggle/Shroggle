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
package com.shroggle.util;

import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.SiteCopierFromActivatedBlueprintReal;
import com.shroggle.logic.site.SiteCopierFromBlueprintReal;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.filesystem.FileSystemMock;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.hibernate.HibernateManager;
import com.shroggle.util.persistance.hibernate.TestRunnerWithHibernateService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithHibernateService.class)
public class ConnectorToBlueprintWithHibernateTest {

    @Before
    public void before() {
        ServiceLocator.setFileSystem(new FileSystemMock());
        ServiceLocator.setSiteCopierFromActivatedBlueprint(new SiteCopierFromActivatedBlueprintReal());
        persistance = ServiceLocator.getPersistance();
    }


    @Test
    public void connectEmpty() {
        final Site site = TestUtil.createSite();
        final Site blueprint = TestUtil.createSite();

        new SiteCopierFromBlueprintReal().execute(new SiteManager(blueprint).getSite(), site, false);

        Assert.assertEquals(blueprint, site.getBlueprintParent());
        Assert.assertEquals(1, blueprint.getBlueprintChilds().size());
        Assert.assertEquals(site, blueprint.getBlueprintChilds().get(0));
    }

    @Test
    public void connectWithPageVersion() {
        final Site site = TestUtil.createSite();
        final Site blueprint = TestUtil.createSite();
        final PageManager blueprintPageVersion = TestUtil.createPageVersionAndPage(blueprint, PageVersionType.WORK);
        blueprintPageVersion.getWorkPageSettings().setCreationDate(new Date(System.currentTimeMillis() / 2));
        MenuItem menuItem = TestUtil.createMenuItem(blueprintPageVersion.getPageId(), blueprint.getMenu());
        menuItem.setIncludeInMenu(true);
        menuItem.setParent(null);
        blueprintPageVersion.getWorkPageSettings().setKeywords("gggg");

        new SiteCopierFromBlueprintReal().execute(new SiteManager(blueprint).getSite(), site, false);

        Assert.assertEquals(2, site.getPages().size());
        final Page page = site.getPages().get(1);
        final PageManager pageVersion = new PageManager(page);
        final List<MenuItem> menuItems = site.getMenu().getMenuItems();
        Assert.assertEquals(blueprint.getMenu().getMenuItems().size(), menuItems.size());
        Assert.assertNotSame(blueprint.getMenu().getMenuItems().get(0), menuItems.get(0));
        Assert.assertEquals(page.getPageId(), menuItems.get(0).getPageId().intValue());
        Assert.assertTrue(menuItems.get(0).isIncludeInMenu());
        Assert.assertTrue(blueprintPageVersion.getCreationDate().getTime() < pageVersion.getCreationDate().getTime());
        Assert.assertEquals("gggg", pageVersion.getKeywords());
    }

    @Test
    public void connectWithPageVersionTreeStructure() {
        final Site site = TestUtil.createSite();
        final Site blueprint = TestUtil.createSite();
        final PageManager blueprintPageVersionLevel1 = TestUtil.createPageVersionAndPage(blueprint, PageVersionType.WORK);

        // Next page shouldn't be included into blueprint.
        final PageManager blueprintPageVersionLevel2_darft = TestUtil.createPageVersionAndPage(blueprint);

        final PageManager blueprintPageVersionLevel3 = TestUtil.createPageVersionAndPage(blueprint, PageVersionType.WORK);

        new SiteCopierFromBlueprintReal().execute(new SiteManager(blueprint).getSite(), site, false);

        Assert.assertEquals(3, site.getPages().size());
//        Page copiedFirstLevelPage = site.getPages().get(1);
//        Page copiedThirdLevelPage = site.getPages().get(2);
    }

    @Test
    public void connectWithWidgets_withWidgetImageAndSameImageAndRollOverImageId() {
        final Site site = TestUtil.createSite();
        final Site blueprint = TestUtil.createSite();
        final PageManager blueprintPageVersion = TestUtil.createPageVersionAndPage(blueprint, PageVersionType.WORK);

        final DraftText blueprintText = new DraftText();
        blueprintText.setText("g");
        persistance.putItem(blueprintText);

        final WidgetItem blueprintWidgetText = new WidgetItem();
        blueprintWidgetText.setDraftItem(blueprintText);
        persistance.putWidget(blueprintWidgetText);
        blueprintWidgetText.setDraftItem(blueprintText);
        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidgetText);

        Image image = TestUtil.createImage(-1, "", "");

        final WidgetItem blueprintWidgetImage = TestUtil.createWidgetImage(image.getImageId(), image.getImageId(), blueprintPageVersion);
        final DraftImage blueprintDraftImage = (DraftImage) blueprintWidgetImage.getDraftItem();


        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidgetImage);

        final FontsAndColors fontsAndColors = new FontsAndColors();
        persistance.putFontsAndColors(fontsAndColors);
        final FontsAndColorsValue value = new FontsAndColorsValue();
        fontsAndColors.addCssValue(value);
        value.setName("margin");
        value.setValue("100px");
        value.setSelector("selector");
        value.setDescription("description");
        persistance.putFontsAndColorsValue(value);
        blueprintDraftImage.setFontsAndColorsId(fontsAndColors.getId());


        List<Image> images = persistance.getImagesByOwnerSiteId(site.getSiteId());
        Assert.assertEquals(0, images.size());

        new SiteCopierFromBlueprintReal().execute(new SiteManager(blueprint).getSite(), site, false);

        Assert.assertEquals(2, site.getPages().size());
        final Page page = site.getPages().get(1);
        final PageManager pageVersion = new PageManager(page);

        Assert.assertEquals(true, pageVersion.isChanged());
        Assert.assertEquals(2, pageVersion.getWidgets().size());
        final WidgetItem widgetItem = (WidgetItem) pageVersion.getWidgets().get(0);
        final DraftText draftText = (DraftText) widgetItem.getDraftItem();//persistance.getDraftItem(widgetItem.getDraftItem());
        Assert.assertEquals("g", draftText.getText());

        final WidgetItem widgetImage = (WidgetItem) pageVersion.getWidgets().get(1);
        final DraftImage draftImage = (DraftImage) widgetImage.getDraftItem();//persistance.getDraftItem(widgetImage.getDraftItem());

        Assert.assertEquals(1, persistance.getFontsAndColors(draftImage.getFontsAndColorsId()).getCssValues().size());
        FontsAndColorsValue widgetImageCssParameterValue = persistance.getFontsAndColors(draftImage.getFontsAndColorsId()).getCssValues().get(0);
        Assert.assertEquals(value.getName(), widgetImageCssParameterValue.getName());
        Assert.assertEquals(value.getValue(), widgetImageCssParameterValue.getValue());
        Assert.assertEquals(value.getSelector(), widgetImageCssParameterValue.getSelector());
        Assert.assertEquals(value.getDescription(), widgetImageCssParameterValue.getDescription());
        Assert.assertNotSame(blueprintDraftImage, draftImage);
        Assert.assertNotSame(blueprintDraftImage.getImageId(), draftImage.getImageId());
        Assert.assertNotSame(blueprintDraftImage.getRollOverImageId(), draftImage.getRollOverImageId());

        images = persistance.getImagesByOwnerSiteId(site.getSiteId());
        Assert.assertEquals(1, images.size());
        Assert.assertEquals(draftImage.getImageId().intValue(), images.get(0).getImageId());
        Assert.assertEquals(draftImage.getRollOverImageId().intValue(), images.get(0).getImageId());
    }

    @Test
    public void connectWithWidgets_withWidgetImageAndNotSameImageAndRollOverImageId() {
        final Site site = TestUtil.createSite();
        final Site blueprint = TestUtil.createSite();
        final PageManager blueprintPageVersion = TestUtil.createPageVersionAndPage(blueprint, PageVersionType.WORK);

        final DraftText text = new DraftText();
        text.setText("g");
        persistance.putItem(text);

        final WidgetItem blueprintWidgetText = TestUtil.createWidgetItem();
        blueprintWidgetText.setDraftItem(text);
        persistance.putWidget(blueprintWidgetText);
        blueprintWidgetText.setDraftItem(text);
        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidgetText);

        Image image = TestUtil.createImage(-1, "", "");
        Image rollOverImage = TestUtil.createImage(-1, "", "");
        WidgetItem blueprintWidgetImage = TestUtil.createWidgetImage(image.getImageId(), rollOverImage.getImageId(), blueprintPageVersion);
        final DraftImage bluepringDraftImage = (DraftImage) blueprintWidgetImage.getDraftItem();

        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidgetImage);

        List<Image> images = persistance.getImagesByOwnerSiteId(site.getSiteId());
        Assert.assertEquals(0, images.size());


        new SiteCopierFromBlueprintReal().execute(new SiteManager(blueprint).getSite(), site, false);

        Assert.assertEquals(2, site.getPages().size());
        final Page page = site.getPages().get(1);
        final PageManager pageVersion = new PageManager(page);

        Assert.assertEquals(true, pageVersion.isChanged());
        Assert.assertEquals(2, pageVersion.getWidgets().size());
        final WidgetItem widgetItem = (WidgetItem) pageVersion.getWidgets().get(0);
        final DraftText draftText = (DraftText) widgetItem.getDraftItem();//persistance.getDraftItem(widgetItem.getDraftItem());
        Assert.assertEquals("g", draftText.getText());

        final WidgetItem widgetImage = (WidgetItem) pageVersion.getWidgets().get(1);
        final DraftImage draftImage = (DraftImage) widgetImage.getDraftItem();//persistance.getDraftItem(widgetImage.getDraftItem());
        Assert.assertNotSame(bluepringDraftImage.getImageId(), draftImage.getImageId());
        Assert.assertNotSame(bluepringDraftImage.getRollOverImageId(), draftImage.getRollOverImageId());

        images = persistance.getImagesByOwnerSiteId(site.getSiteId());
        Assert.assertEquals(2, images.size());
        Assert.assertEquals(draftImage.getImageId().intValue(), images.get(0).getImageId());
        Assert.assertEquals(draftImage.getRollOverImageId().intValue(), images.get(1).getImageId());
    }

    @Test
    public void connectWithWidgets_WORK() {
        final Site site = TestUtil.createSite();
        final Site blueprint = TestUtil.createSite();
        final PageManager blueprintPageVersion = TestUtil.createPageVersionAndPage(blueprint, PageVersionType.WORK);

        final DraftText text = new DraftText();
        text.setText("g");
        persistance.putItem(text);

        final WidgetItem blueprintWidgetText = TestUtil.createWidgetItem();
        blueprintWidgetText.setDraftItem(text);
        persistance.putWidget(blueprintWidgetText);
        blueprintWidgetText.setDraftItem(text);
        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidgetText);

        new SiteCopierFromBlueprintReal().execute(new SiteManager(blueprint).getSite(), site, true);

        Assert.assertEquals(2, site.getPages().size());
        final Page page = site.getPages().get(1);
        final PageManager pageVersionDraft = new PageManager(page);
        Assert.assertEquals(false, pageVersionDraft.isChanged());
        Assert.assertEquals(1, pageVersionDraft.getWidgets().size());
        final WidgetItem widgetText = (WidgetItem) pageVersionDraft.getWidgets().get(0);
        final DraftText draftText = (DraftText) widgetText.getDraftItem();
        Assert.assertEquals("g", draftText.getText());
    }

    @Test
    public void connectWithEmptySharedWidget() {
        final Site site = TestUtil.createSite();
        final Site blueprint = TestUtil.createSite();
        final PageManager blueprintPageVersion = TestUtil.createPageVersionAndPage(blueprint, PageVersionType.WORK);
        final WidgetItem blueprintWidgetBlog = new WidgetItem();
        persistance.putWidget(blueprintWidgetBlog);
        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidgetBlog);

        new SiteCopierFromBlueprintReal().execute(new SiteManager(blueprint).getSite(), site, false);

        Assert.assertEquals(2, site.getPages().size());
        final Page page = site.getPages().get(1);
        final PageManager pageVersion = new PageManager(page);
        Assert.assertEquals(1, pageVersion.getWidgets().size());
        final WidgetItem widgetBlog = (WidgetItem) pageVersion.getWidgets().get(0);
        Assert.assertNull(widgetBlog.getDraftItem());
    }

    @Test
    public void connectWithSharedWidget() {
        final Site blueprint = TestUtil.createSite();
        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(blueprint.getSiteId());
        persistance.putItem(blog);

        final PageManager blueprintPageVersion = TestUtil.createPageVersionAndPage(blueprint, PageVersionType.WORK);
        final WidgetItem blueprintWidgetBlog = new WidgetItem();
        blueprintWidgetBlog.setDraftItem(blog);
        blueprintWidgetBlog.setBlueprintShareble(true);
        persistance.putWidget(blueprintWidgetBlog);
        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidgetBlog);

        final Site site = TestUtil.createSite();
        new SiteCopierFromBlueprintReal().execute(new SiteManager(blueprint).getSite(), site, false);

        Assert.assertEquals(2, site.getPages().size());
        final Page page = site.getPages().get(1);
        final PageManager pageVersion = new PageManager(page);
        Assert.assertEquals(1, pageVersion.getWidgets().size());
        final WidgetItem widgetBlog = (WidgetItem) pageVersion.getWidgets().get(0);
        TestUtil.assertIntAndBigInt(blog.getId(), widgetBlog.getDraftItem().getId());
    }

    @Test
    public void connectWithCopySharedBlog() {
        final Site blueprint = TestUtil.createSite();
        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(blueprint.getSiteId());
        blog.addBlogPost(new BlogPost());
        persistance.putItem(blog);

        final PageManager blueprintPageVersion = TestUtil.createPageVersionAndPage(blueprint, PageVersionType.WORK);
        final WidgetItem blueprintWidgetBlog = new WidgetItem();
        blueprintWidgetBlog.setBlueprintShareble(false);
        blueprintWidgetBlog.setDraftItem(blog);
        persistance.putWidget(blueprintWidgetBlog);
        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidgetBlog);

        final Site site = TestUtil.createSite();
        new SiteCopierFromBlueprintReal().execute(new SiteManager(blueprint).getSite(), site, false);

        final Page page = site.getPages().get(1);
        final PageManager pageVersion = new PageManager(page);
        final WidgetItem widgetBlog = (WidgetItem) pageVersion.getWidgets().get(0);

        Assert.assertNotSame(blog.getId(), widgetBlog.getDraftItem().getId());
        final DraftBlog copiedBlog = (DraftBlog) widgetBlog.getDraftItem();//persistance.getDraftItem(widgetBlog.getDraftItem());
        Assert.assertEquals(site.getSiteId(), copiedBlog.getSiteId());
        Assert.assertEquals(1, persistance.getSiteOnItemsByItem(copiedBlog.getId()).size());
        Assert.assertEquals(1, persistance.getBlogPostsByBlog(copiedBlog.getId()).size());
    }

    @Test
    public void connectWithCopySharedGallery() {
        final Site blueprint = TestUtil.createSite();
        final DraftCustomForm blueprintCustomForm = new DraftCustomForm();
        blueprintCustomForm.setSiteId(blueprint.getSiteId());
        TestUtil.createFormFilter(blueprintCustomForm);
        persistance.putItem(blueprintCustomForm);

        final DraftFormItem blueprintFormItem = TestUtil.createFormItem(FormItemName.URL, blueprintCustomForm, 1);//

        final DraftGallery blueprintGallery = new DraftGallery();
        blueprintGallery.setFormId1(blueprintCustomForm.getFormId());
        persistance.putItem(blueprintGallery);

        final DraftGalleryLabel blueprintGalleryLabel = new DraftGalleryLabel();
        blueprintGallery.addLabel(blueprintGalleryLabel);
        blueprintGalleryLabel.setAlign(GalleryAlign.RIGHT);
        blueprintGalleryLabel.getId().setFormItemId(blueprintFormItem.getFormItemId());
        persistance.putGalleryLabel(blueprintGalleryLabel);

        TestUtil.createGalleryItem(1, 112, blueprintGallery, blueprintFormItem.getFormItemId());//new DraftGalleryItem();

        final PageManager blueprintPageVersion = TestUtil.createPageVersionAndPage(blueprint, PageVersionType.WORK);
        final WidgetItem blueprintWidget = new WidgetItem();
        blueprintWidget.setBlueprintShareble(false);
        blueprintWidget.setDraftItem(blueprintGallery);
        persistance.putWidget(blueprintWidget);
        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidget);

        final Site site = TestUtil.createSite();
        new SiteCopierFromBlueprintReal().execute(new SiteManager(blueprint).getSite(), site, false);

        final Page page = site.getPages().get(1);
        final PageManager pageVersion = new PageManager(page);
        final WidgetItem widget = (WidgetItem) pageVersion.getWidgets().get(0);

        Assert.assertNotSame(blueprintCustomForm.getId(), widget.getDraftItem().getId());

        final DraftGallery gallery = (DraftGallery) widget.getDraftItem();
        Assert.assertNotSame(blueprintCustomForm.getFormId(), gallery.getFormId1());
        Assert.assertEquals(site.getSiteId(), gallery.getSiteId());
        Assert.assertEquals(1, gallery.getLabels().size());
        Assert.assertEquals(1, gallery.getItems().size());

        final DraftCustomForm customForm = persistance.getCustomFormById(gallery.getFormId1());
        Assert.assertEquals(1, customForm.getFormItems().size());
        Assert.assertEquals(site.getSiteId(), customForm.getSiteId());
        final DraftFormItem formItem = persistance.getFormItemById(customForm.getFormItems().get(0).getFormItemId());

        final GalleryLabel galleryLabel = gallery.getLabels().get(0);
        Assert.assertEquals(GalleryAlign.RIGHT, galleryLabel.getAlign());
        Assert.assertEquals(formItem.getFormItemId(), galleryLabel.getId().getFormItemId());

        final GalleryItem galleryItem = gallery.getItems().get(0);
        Assert.assertEquals(new Integer(112), galleryItem.getHeight());
        Assert.assertEquals(formItem.getFormItemId(), galleryItem.getId().getFormItemId());

        Assert.assertEquals(1, persistance.getSiteOnItemsByItem(gallery.getId()).size());
        Assert.assertEquals(blueprint, persistance.getSiteOnItemsByItem(gallery.getId()).get(0).getSite());
        Assert.assertEquals(SiteOnItemRightType.READ, persistance.getSiteOnItemsByItem(gallery.getId()).get(0).getType());
    }

    @Test
    public void connectWithCopySharedGalleryWithFilter() {
        final Site blueprint = TestUtil.createSite();
        final DraftCustomForm blueprintCustomForm = new DraftCustomForm();
        blueprintCustomForm.setSiteId(blueprint.getSiteId());
        persistance.putItem(blueprintCustomForm);

        final DraftFormItem blueprintFormItem = TestUtil.createFormItem(FormItemName.URL, blueprintCustomForm, 1);

        final DraftFormFilter blueprintFormFilter = TestUtil.createFormFilter(blueprintCustomForm);

        Assert.assertEquals(1, blueprintCustomForm.getFormItems().size());

        final DraftFormFilterRule blueprintFilterRule = new DraftFormFilterRule();
        blueprintFilterRule.setFormItemId(blueprintFormItem.getFormItemId());
        blueprintFormFilter.addRule(blueprintFilterRule);

        final DraftGallery blueprintGallery = new DraftGallery();
        blueprintGallery.setFormFilterId(blueprintFormFilter.getFormFilterId());
        persistance.putItem(blueprintGallery);

        final DraftGalleryLabel blueprintGalleryLabel = new DraftGalleryLabel();
        blueprintGallery.addLabel(blueprintGalleryLabel);
        blueprintGalleryLabel.setAlign(GalleryAlign.RIGHT);
        blueprintGalleryLabel.getId().setFormItemId(blueprintFormItem.getFormItemId());
        persistance.putGalleryLabel(blueprintGalleryLabel);

        TestUtil.createGalleryItem(1, 112, blueprintGallery, blueprintFormItem.getFormItemId());

        final PageManager blueprintPageVersion = TestUtil.createPageVersionAndPage(blueprint, PageVersionType.WORK);
        final WidgetItem blueprintWidget = new WidgetItem();
        blueprintWidget.setBlueprintShareble(false);
        blueprintWidget.setDraftItem(blueprintGallery);
        persistance.putWidget(blueprintWidget);
        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidget);

        final Site site = TestUtil.createSite();
        new SiteCopierFromBlueprintReal().execute(new SiteManager(blueprint).getSite(), site, false);

        final Page page = site.getPages().get(1);
        final PageManager pageVersion = new PageManager(page);
        final WidgetItem widget = (WidgetItem) pageVersion.getWidgets().get(0);

        Assert.assertNotSame(blueprintCustomForm.getId(), widget.getDraftItem().getId());

        final DraftGallery gallery = (DraftGallery) widget.getDraftItem();
        Assert.assertNotSame(blueprintCustomForm.getFormId(), gallery.getFormId1());
        Assert.assertEquals(site.getSiteId(), gallery.getSiteId());
        Assert.assertEquals(1, gallery.getLabels().size());
        Assert.assertEquals(1, gallery.getItems().size());

        final DraftFormFilter formFilter = persistance.getFormFilterById(gallery.getFormFilterId());
        Assert.assertEquals(1, formFilter.getRules().size());

        final DraftCustomForm customForm = (DraftCustomForm) formFilter.getForm();
        Assert.assertEquals(1, customForm.getFormItems().size());
        final DraftFormItem formItem = persistance.getFormItemById(customForm.getFormItems().get(0).getFormItemId());

        final DraftFormFilterRule filterRule = persistance.getFormFilterRuleById(formFilter.getRules().get(0).getFormFilterRuleId());
        Assert.assertEquals(formItem.getFormItemId(), filterRule.getFormItemId());

        final GalleryLabel galleryLabel = gallery.getLabels().get(0);
        Assert.assertEquals(GalleryAlign.RIGHT, galleryLabel.getAlign());
        Assert.assertEquals(formItem.getFormItemId(), galleryLabel.getId().getFormItemId());

        final GalleryItem galleryItem = gallery.getItems().get(0);
        Assert.assertEquals(new Integer(112), galleryItem.getHeight());
        Assert.assertEquals(formItem.getFormItemId(), galleryItem.getId().getFormItemId());

        Assert.assertEquals(1, persistance.getSiteOnItemsByItem(gallery.getId()).size());
        Assert.assertEquals(blueprint, persistance.getSiteOnItemsByItem(gallery.getId()).get(0).getSite());
        Assert.assertEquals(SiteOnItemRightType.READ, persistance.getSiteOnItemsByItem(gallery.getId()).get(0).getType());
    }

    @Test
    public void connectWithDoubleCopySharedBlog() {
        final Site blueprint = TestUtil.createSite();
        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(blueprint.getSiteId());
        blog.addBlogPost(new BlogPost());
        persistance.putItem(blog);

        final PageManager blueprintPageVersion = TestUtil.createPageVersionAndPage(blueprint, PageVersionType.WORK);

        final WidgetItem blueprintWidgetBlog1 = new WidgetItem();
        blueprintWidgetBlog1.setBlueprintShareble(false);
        blueprintWidgetBlog1.setDraftItem(blog);
        persistance.putWidget(blueprintWidgetBlog1);
        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidgetBlog1);

        final WidgetItem blueprintWidgetBlog2 = new WidgetItem();
        blueprintWidgetBlog2.setBlueprintShareble(false);
        blueprintWidgetBlog2.setDraftItem(blog);
        persistance.putWidget(blueprintWidgetBlog2);
        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidgetBlog2);

        final Site site = TestUtil.createSite();
        new SiteCopierFromBlueprintReal().execute(new SiteManager(blueprint).getSite(), site, false);

        final Page page = site.getPages().get(1);
        final PageManager pageVersion = new PageManager(page);
        final WidgetItem widgetBlog1 = (WidgetItem) pageVersion.getWidgets().get(0);
        final WidgetItem widgetBlog2 = (WidgetItem) pageVersion.getWidgets().get(1);

        Assert.assertNotSame(blog.getId(), widgetBlog1.getDraftItem().getId());
        final DraftBlog copiedBlog1 = (DraftBlog) widgetBlog1.getDraftItem();//persistance.getDraftItem(widgetBlog1.getDraftItem());
        final DraftBlog copiedBlog2 = (DraftBlog) widgetBlog2.getDraftItem();//persistance.getDraftItem(widgetBlog2.getDraftItem());
        Assert.assertEquals(site.getSiteId(), copiedBlog1.getSiteId());
        Assert.assertEquals(copiedBlog1, copiedBlog2);
        Assert.assertEquals(1, persistance.getSiteOnItemsByItem(copiedBlog1.getId()).size());
        Assert.assertEquals(1, persistance.getBlogPostsByBlog(copiedBlog1.getId()).size());
    }

    @Test
    public void testExecute_withBlogAndBlogSummary() throws Exception {
        final Site blueprint = TestUtil.createBlueprint();
        for (Page page : new ArrayList<Page>(blueprint.getPages())) {
            blueprint.removePage(page);
        }
        TestUtil.createMenu(blueprint);
        persistance.putSite(blueprint);

        /*----------------------------------------------------Blog----------------------------------------------------*/
        final Page pageWithBlog = TestUtil.createPage(blueprint);

        final DraftBlog draftBlog = TestUtil.createBlog(blueprint, "draftBlog");

        final BlogPost blogPost = TestUtil.createBlogPost(draftBlog, "Blog post`s text", "Blog post`s title");

        final Comment comment = TestUtil.createComment(blogPost, "Comment`s text");
        final Comment commentOnComment = TestUtil.createComment("Child comment`s text");
        comment.addChildComment(commentOnComment);

        final WidgetItem widgetBlog = TestUtil.createWidgetBlog(draftBlog.getId());
        widgetBlog.setCrossWidgetId(12345);

        final PageManager pageManagerWithBlog = new PageManager(pageWithBlog);
        pageManagerWithBlog.setName("pageWithBlog");
        pageManagerWithBlog.addWidget(widgetBlog);
        pageManagerWithBlog.publish();
        /*----------------------------------------------------Blog----------------------------------------------------*/

        /*------------------------------------------------Blog Summary------------------------------------------------*/
        final Page pageWithBlogSummary = TestUtil.createPage(blueprint);
        final DraftBlogSummary draftBlogSummary = TestUtil.createBlogSummary(blueprint, "draftBlogSummary");
        draftBlogSummary.setIncludedCrossWidgetId(Arrays.asList(widgetBlog.getCrossWidgetId()));

        final WidgetItem widgetBlogSummary = TestUtil.createWidgetItem();
        widgetBlogSummary.setDraftItem(draftBlogSummary);

        final PageManager pageManagerWithBlogSummary = new PageManager(pageWithBlogSummary);
        pageManagerWithBlogSummary.setName("pageWithBlogSummary");
        pageManagerWithBlogSummary.addWidget(widgetBlogSummary);
        pageManagerWithBlogSummary.publish();
        /*------------------------------------------------Blog Summary------------------------------------------------*/

        final Site site = TestUtil.createSite();
        for (Page page : new ArrayList<Page>(site.getPages())) {
            site.removePage(page);
        }
        persistance.putSite(site);
        final SiteCopierFromActivatedBlueprintReal copier = new SiteCopierFromActivatedBlueprintReal();
        copier.execute(blueprint, site, false);

        Assert.assertEquals(2, site.getPages().size());
        /*---------------------------------------Checking first page with blog----------------------------------------*/
        final PageManager copiedPageWithBlog = new PageManager(site.getPages().get(0));
        Assert.assertEquals("pageWithBlog", copiedPageWithBlog.getName());

        Assert.assertEquals(1, copiedPageWithBlog.getWidgets().size());
        final WidgetItem copiedWidgetWithBlog = (WidgetItem) copiedPageWithBlog.getWidgets().get(0);

        HibernateManager.get().flush();
        HibernateManager.get().refresh(copiedWidgetWithBlog);
        
        Assert.assertEquals(widgetBlog.getCrossWidgetId(), copiedWidgetWithBlog.getParentCrossWidgetId().intValue());
        Assert.assertEquals("Don`t replace this with Assert.assertNotSame(). It`s not overloaded and compares objects " +
                "only (so Assert.assertNotSame(12345, 12345) will not throw error). Tolik",
                false, widgetBlog.getCrossWidgetId() == copiedWidgetWithBlog.getCrossWidgetId());
        Assert.assertEquals(copiedWidgetWithBlog.getWidgetId(), copiedWidgetWithBlog.getCrossWidgetId());
        Assert.assertNotSame("crossWidgetId is not updatable so we must check that it was setted correct and correct value is saved in DB. Tolik",
                widgetBlog.getCrossWidgetId(), persistance.getWidget(copiedWidgetWithBlog.getWidgetId()).getCrossWidgetId());
        final DraftBlog copiedBlog = (DraftBlog) copiedWidgetWithBlog.getDraftItem();
        Assert.assertEquals("draftBlog", copiedBlog.getName());
        Assert.assertEquals(1, copiedBlog.getBlogPosts().size());
        final BlogPost copiedBlogPost = copiedBlog.getBlogPosts().get(0);
        Assert.assertEquals("Blog post`s text", copiedBlogPost.getText());
        Assert.assertEquals("Blog post`s title", copiedBlogPost.getPostTitle());
        Assert.assertNotSame(blogPost.getBlogPostId(), copiedBlogPost.getBlogPostId());

        Assert.assertEquals(1, copiedBlogPost.getComments().size());
        final Comment copiedComment = copiedBlogPost.getComments().get(0);
        Assert.assertEquals("Comment`s text", copiedComment.getText());
        Assert.assertNotSame(comment.getCommentId(), copiedComment.getCommentId());

        Assert.assertEquals(1, copiedComment.getAnswerComments().size());
        final Comment copiedCommentOnComment = copiedComment.getAnswerComments().get(0);
        Assert.assertEquals("Child comment`s text", copiedCommentOnComment.getText());
        Assert.assertNotSame(commentOnComment.getCommentId(), copiedCommentOnComment.getCommentId());

        /*---------------------------------------Checking first page with blog----------------------------------------*/

        /*-----------------------------------Checking second page with blogSummary------------------------------------*/
        final PageManager copiedPageWithBlogSummary = new PageManager(site.getPages().get(1));
        Assert.assertEquals("pageWithBlogSummary", copiedPageWithBlogSummary.getName());

        Assert.assertEquals(1, copiedPageWithBlogSummary.getWidgets().size());
        final WidgetItem copiedWidgetWithBlogSummary = (WidgetItem) copiedPageWithBlogSummary.getWidgets().get(0);
        Assert.assertEquals(widgetBlogSummary.getCrossWidgetId(), copiedWidgetWithBlogSummary.getParentCrossWidgetId().intValue());
        final DraftBlogSummary copiedBlogSummary = (DraftBlogSummary) copiedWidgetWithBlogSummary.getDraftItem();
        Assert.assertEquals("draftBlogSummary", copiedBlogSummary.getName());
        Assert.assertEquals(1, copiedBlogSummary.getIncludedCrossWidgetId().size());
        Assert.assertEquals(copiedWidgetWithBlog.getCrossWidgetId(), copiedBlogSummary.getIncludedCrossWidgetId().get(0).intValue());
        /*-----------------------------------Checking second page with blogSummary------------------------------------*/
    }

    @Test
    public void connectWithDoubleSharedWidget() {
        final Site blueprint = TestUtil.createSite();
        final DraftBlog blog = new DraftBlog();
        blog.setSiteId(blueprint.getSiteId());
        persistance.putItem(blog);

        final PageManager blueprintPageVersion = TestUtil.createPageVersionAndPage(blueprint, PageVersionType.WORK);
        final WidgetItem blueprintWidgetBlog1 = new WidgetItem();
        blueprintWidgetBlog1.setDraftItem(blog);
        blueprintWidgetBlog1.setBlueprintShareble(true);
        persistance.putWidget(blueprintWidgetBlog1);
        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidgetBlog1);

        final WidgetItem blueprintWidgetBlog2 = new WidgetItem();
        blueprintWidgetBlog2.setDraftItem(blog);
        blueprintWidgetBlog2.setBlueprintShareble(true);
        persistance.putWidget(blueprintWidgetBlog2);
        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidgetBlog2);

        final Site site = TestUtil.createSite();
        new SiteCopierFromBlueprintReal().execute(new SiteManager(blueprint).getSite(), site, false);

        Assert.assertEquals(2, site.getPages().size());
        final Page page = site.getPages().get(1);
        final PageManager pageVersion = new PageManager(page);
        Assert.assertEquals(2, pageVersion.getWidgets().size());
        final WidgetItem widgetBlog1 = (WidgetItem) pageVersion.getWidgets().get(0);
        final WidgetItem widgetBlog2 = (WidgetItem) pageVersion.getWidgets().get(1);
        TestUtil.assertIntAndBigInt(blog.getId(), widgetBlog1.getDraftItem().getId());
        TestUtil.assertIntAndBigInt(blog.getId(), widgetBlog2.getDraftItem().getId());
    }


    @Test
    public void connectWithMenuWidget() {
        final Site blueprint = TestUtil.createSite();
        final DraftMenu blueprintMenu = new DraftMenu();
        blueprintMenu.setSiteId(blueprint.getSiteId());
        persistance.putMenu(blueprintMenu);

        final PageManager blueprintPageVersion = TestUtil.createPageVersionAndPage(blueprint, PageVersionType.WORK);
        final WidgetItem blueprintWidgetMenu = new WidgetItem();
        blueprintWidgetMenu.setDraftItem(blueprintMenu);
        persistance.putWidget(blueprintWidgetMenu);
        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidgetMenu);

        TestUtil.createPageVersionAndPage(blueprint);

        final Site site = TestUtil.createSite();
        new SiteCopierFromBlueprintReal().execute(new SiteManager(blueprint).getSite(), site, false);

        Assert.assertNotNull(site.getMenu());
        Assert.assertEquals(site.getSiteId(), site.getMenu().getSiteId());

        Assert.assertEquals(2, site.getPages().size());
        final Page page = site.getPages().get(1);
        final PageManager pageVersion = new PageManager(page);
        Assert.assertEquals(1, pageVersion.getWidgets().size());
        final WidgetItem widgetMenu = (WidgetItem) pageVersion.getWidgets().get(0);
        Assert.assertNotSame(blueprintMenu.getId(), widgetMenu.getDraftItem().getId());
        final DraftMenu menu = (DraftMenu) widgetMenu.getDraftItem();//persistance.getMenuById(widgetMenu.getDraftItem());
        TestUtil.assertIntAndBigInt(site.getSiteId(), menu.getSiteId());
    }

    @Test
    public void connectWithDoubleMenuWidget() {
        final Site blueprint = TestUtil.createSite();

        final PageManager blueprintPageVersion = TestUtil.createPageVersionAndPage(blueprint, PageVersionType.WORK);

        final DraftMenu blueprintMenu = new DraftMenu();
        blueprintMenu.setSiteId(blueprint.getSiteId());
        MenuItem menuItem1 = TestUtil.createMenuItem(blueprintPageVersion.getPageId(), blueprintMenu);
        MenuItem menuItem2 = TestUtil.createMenuItem(blueprintPageVersion.getPageId(), blueprint.getMenu());
        menuItem1.setParent(null);
        menuItem2.setParent(null);

        persistance.putMenu(blueprintMenu);
        persistance.putMenu(blueprint.getMenu());

        final WidgetItem blueprintWidgetMenu1 = new WidgetItem();
        persistance.putWidget(blueprintWidgetMenu1);
        blueprintWidgetMenu1.setDraftItem(blueprintMenu);
        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidgetMenu1);


        final WidgetItem blueprintWidgetMenu2 = new WidgetItem();
        persistance.putWidget(blueprintWidgetMenu2);
        blueprintWidgetMenu2.setDraftItem(blueprintMenu);
        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidgetMenu2);

        TestUtil.createPageVersionAndPage(blueprint);

        final Site site = TestUtil.createSite();
        MenuItem menuItem11 = TestUtil.createMenuItem(0, site.getMenu());
        MenuItem menuItem12 = TestUtil.createMenuItem(0, site.getMenu());
        menuItem11.setParent(null);
        menuItem12.setParent(null);


        new SiteCopierFromBlueprintReal().execute(new SiteManager(blueprint).getSite(), site, false);


        final Page page = site.getPages().get(1);
        final PageManager pageVersion = new PageManager(page);
        Assert.assertEquals(2, pageVersion.getWidgets().size());
        final WidgetItem widgetMenu1 = (WidgetItem) pageVersion.getWidgets().get(0);
        final WidgetItem widgetMenu2 = (WidgetItem) pageVersion.getWidgets().get(1);

        final DraftMenu menu1 = (DraftMenu) widgetMenu1.getDraftItem();//persistance.getMenuById(widgetMenu1.getDraftItem());
        final DraftMenu menu2 = (DraftMenu) widgetMenu2.getDraftItem();// persistance.getMenuById(widgetMenu2.getDraftItem());
        //Assert.assertTrue(menu1.getItems().getUserId() > 0);
        //Assert.assertTrue(menu2.getItems().getUserId() > 0);
        //Assert.assertEquals(menu2.getItems().getUserId(), menu1.getItems().getUserId());
        Assert.assertEquals(1, menu1.getMenuItems().size());
        Assert.assertEquals(page.getPageId(), menu1.getMenuItems().get(0).getPageId().intValue());

        Assert.assertNotSame(blueprintMenu.getId(), menu1.getId());
        Assert.assertNotSame(blueprintMenu.getId(), menu2.getId());
        Assert.assertEquals(widgetMenu1.getDraftItem().getId(), widgetMenu2.getDraftItem().getId());
    }

    private Persistance persistance;// = ServiceLocator.getPersistance();

}