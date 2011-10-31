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
import com.shroggle.exception.SiteAlreadyConnectedToBlueprintException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;


/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class SiteCopierFromBlueprintTest {

    @Before
    public void before() {
        ServiceLocator.setSiteCopierFromBlueprint(new SiteCopierFromBlueprintReal());
        ServiceLocator.setSiteCopierFromActivatedBlueprint(new SiteCopierFromActivatedBlueprintReal());
    }

    @Test(expected = SiteAlreadyConnectedToBlueprintException.class)
    public void connectAlreadyConnected() {
        final Site site = TestUtil.createSite();
        final Site blueprint = TestUtil.createSite();
        blueprint.addBlueprintChild(site);

        final Site newBlueprint = TestUtil.createSite();
        new SiteCopierFromBlueprintReal().execute(new SiteManager(newBlueprint).getSite(), site, false);
    }

    @Test(expected = SiteAlreadyConnectedToBlueprintException.class)
    public void connectAlredyConnectedToSameBlueprint() {
        final Site site = TestUtil.createSite();
        final Site blueprint = TestUtil.createSite();
        blueprint.addBlueprintChild(site);

        new SiteCopierFromBlueprintReal().execute(new SiteManager(blueprint).getSite(), site, false);
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
        blueprintPageVersion.setCreationDate(new Date(1000));
        MenuItem menuItem = TestUtil.createMenuItem(blueprintPageVersion.getPage().getPageId(), blueprint.getMenu());
        menuItem.setIncludeInMenu(true);
        menuItem.setParent(null);
        blueprintPageVersion.getWorkPageSettings().setKeywords("gggg");

        new SiteCopierFromBlueprintReal().execute(blueprint, site, false);

        Assert.assertEquals(2, site.getPages().size());
        final Page page = site.getPages().get(1);

        final PageManager pageVersion = new PageManager(page);
        final List<MenuItem> menuItems = site.getMenu().getMenuItems();
        Assert.assertEquals(blueprint.getMenu().getMenuItems().size(), menuItems.size());
        Assert.assertNotSame(blueprint.getMenu().getMenuItems().get(0), menuItems.get(0));
        Assert.assertEquals(page.getPageId(), menuItems.get(0).getPageId().intValue());
        Assert.assertTrue(menuItems.get(0).isIncludeInMenu());
        Assert.assertEquals("gggg", pageVersion.getKeywords());
    }

    @Test
    public void connectWithPageVersionTreeStructure() {
        final Site site = TestUtil.createSite();
        final Site blueprint = TestUtil.createSite();
        TestUtil.createPageVersionAndPage(blueprint, PageVersionType.WORK);

        // Next page shouldn't be included into blueprint.
        TestUtil.createPageVersionAndPage(blueprint);

        TestUtil.createPageVersionAndPage(blueprint, PageVersionType.WORK);

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

        final WidgetItem blueprintWidgetText = TestUtil.createWidgetItem();
        blueprintWidgetText.setDraftItem(blueprintText);
        persistance.putWidget(blueprintWidgetText);
        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidgetText);

        blueprintWidgetText.setDraftItem(blueprintText);

        Image image = TestUtil.createImage(-1, "", "");

        final WidgetItem blueprintWidgetImage = TestUtil.createWidgetImage(image.getImageId(), image.getImageId());
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
        final DraftText draftText = (DraftText) widgetItem.getDraftItem();
        Assert.assertEquals("g", draftText.getText());

        final WidgetItem widgetImage = (WidgetItem) pageVersion.getWidgets().get(1);
        final DraftImage draftImage = (DraftImage) widgetImage.getDraftItem();
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
        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidgetText);

        blueprintWidgetText.setDraftItem(text);

        Image image = TestUtil.createImage(-1, "", "");
        Image rollOverImage = TestUtil.createImage(-1, "", "");
        WidgetItem blueprintWidgetImage = TestUtil.createWidgetImage(image.getImageId(), rollOverImage.getImageId());
        final DraftImage bluepringDraftImage = (DraftImage) blueprintWidgetImage.getDraftItem();//persistance.getDraftItem(blueprintWidgetImage.getDraftItem());

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
    public void connectWithWidgetsAndPublish() {
        final Site site = TestUtil.createSite();
        final Site blueprint = TestUtil.createSite();
        final PageManager blueprintPageVersion = TestUtil.createPageVersionAndPage(blueprint, PageVersionType.WORK);


        final DraftText text = new DraftText();
        text.setText("g");
        persistance.putItem(text);

        final WidgetItem blueprintWidgetText = TestUtil.createWidgetItem();
        blueprintWidgetText.setDraftItem(text);
        persistance.putWidget(blueprintWidgetText);
        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidgetText);

        blueprintWidgetText.setDraftItem(text);

        ServiceLocator.getSiteCopierFromBlueprint().execute(new SiteManager(blueprint).getSite(), site, true);

        Assert.assertEquals(2, site.getPages().size());
        final Page page = site.getPages().get(1);
        final PageManager pageVersionDraft = new PageManager(page);
        Assert.assertEquals(false, pageVersionDraft.isChanged());
        Assert.assertEquals(1, pageVersionDraft.getWidgets().size());

        final WidgetItem widgetText = (WidgetItem) pageVersionDraft.getWidgets().get(0);
        final DraftText copiedDraftText = (DraftText) widgetText.getDraftItem();
        Assert.assertEquals("g", copiedDraftText.getText());

        final WorkText copiedWorkText = persistance.getWorkItem(copiedDraftText.getId());
        Assert.assertNotNull("When publish is true, we must publish items on site!", copiedWorkText);
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
    public void connectWithCopySharedForum() {
        final Site blueprint = TestUtil.createSite();
        final DraftForum forum = new DraftForum();
        forum.setSiteId(blueprint.getSiteId());
        forum.setAllowPolls(true);
        forum.setCreatePollRight(AccessGroup.OWNER);
        forum.addSubForum(new SubForum());
        persistance.putItem(forum);

        final PageManager blueprintPageVersion = TestUtil.createPageVersionAndPage(blueprint, PageVersionType.WORK);

        final WidgetItem blueprintWidgetForum = new WidgetItem();
        blueprintWidgetForum.setBlueprintShareble(false);
        blueprintWidgetForum.setDraftItem(forum);
        persistance.putWidget(blueprintWidgetForum);
        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidgetForum);


        final Site site = TestUtil.createSite();
        new SiteCopierFromBlueprintReal().execute(new SiteManager(blueprint).getSite(), site, false);

        final Page page = site.getPages().get(1);
        final PageManager pageVersion = new PageManager(page);
        final WidgetItem widgetForum = (WidgetItem) pageVersion.getWidgets().get(0);

        Assert.assertNotSame(forum.getId(), widgetForum.getDraftItem().getId());
        final DraftForum copiedForum = (DraftForum) widgetForum.getDraftItem();//persistance.getDraftItem(widgetForum.getDraftItem());
        Assert.assertEquals(site.getSiteId(), copiedForum.getSiteId());
        Assert.assertTrue(copiedForum.isAllowPolls());
        Assert.assertEquals(AccessGroup.OWNER, copiedForum.getCreatePollRight());
        Assert.assertEquals(1, persistance.getSiteOnItemsByItem(copiedForum.getId()).size());
        Assert.assertEquals(0, persistance.getSubForumsByForumId(copiedForum.getId()).size());
    }

    @Test
    public void connectWithCopySharedCustomForm() {
        final Site blueprint = TestUtil.createSite();
        final DraftCustomForm blueprintCustomForm = new DraftCustomForm();
        blueprintCustomForm.setSiteId(blueprint.getSiteId());
        blueprintCustomForm.addFilter(new DraftFormFilter());
        persistance.putItem(blueprintCustomForm);

        final DraftFormItem blueprintFormItem1 = new DraftFormItem();
        blueprintFormItem1.setFormItemName(FormItemName.URL);
        blueprintCustomForm.getFormItems().add(blueprintFormItem1);

        final DraftFormItem blueprintFormItem2 = new DraftFormItem();
        blueprintFormItem2.setItemName("2");
        blueprintCustomForm.getFormItems().add(blueprintFormItem2);

        final PageManager blueprintPageVersion = TestUtil.createPageVersionAndPage(blueprint, PageVersionType.WORK);

        final WidgetItem blueprintWidget = new WidgetItem();
        blueprintWidget.setBlueprintShareble(false);
        blueprintWidget.setDraftItem(blueprintCustomForm);
        persistance.putWidget(blueprintWidget);
        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidget);


        final Site site = TestUtil.createSite();
        new SiteCopierFromBlueprintReal().execute(new SiteManager(blueprint).getSite(), site, false);

        final Page page = site.getPages().get(1);
        final PageManager pageVersion = new PageManager(page);
        final WidgetItem widget = (WidgetItem) pageVersion.getWidgets().get(0);

        Assert.assertNotSame(blueprintCustomForm.getId(), widget.getDraftItem().getId());
        final DraftCustomForm customForm = (DraftCustomForm) widget.getDraftItem();//persistance.getCustomFormById(widget.getDraftItem());
        Assert.assertEquals(site.getSiteId(), customForm.getSiteId());
        Assert.assertEquals(2, customForm.getFormItems().size());
        final DraftFormItem formItem1 = persistance.getFormItemById(customForm.getFormItems().get(0).getFormItemId());
        final DraftFormItem formItem2 = persistance.getFormItemById(customForm.getFormItems().get(1).getFormItemId());
        Assert.assertEquals(FormItemName.URL, formItem1.getFormItemName());
        Assert.assertEquals("2", formItem2.getItemName());
        Assert.assertEquals(0, customForm.getFilters().size());
        Assert.assertEquals(1, persistance.getSiteOnItemsByItem(customForm.getId()).size());
    }

    @Test
    public void connectWithCopySharedGallery() {
        final Site blueprint = TestUtil.createSite();
        final DraftCustomForm blueprintCustomForm = new DraftCustomForm();
        blueprintCustomForm.setSiteId(blueprint.getSiteId());
        blueprintCustomForm.addFilter(new DraftFormFilter());
        persistance.putItem(blueprintCustomForm);

        final DraftFormItem blueprintFormItem = new DraftFormItem();
        blueprintFormItem.setFormItemName(FormItemName.URL);
        blueprintCustomForm.getFormItems().add(blueprintFormItem);
        persistance.putFormItem(blueprintFormItem);

        final DraftGallery blueprintGallery = new DraftGallery();
        blueprintGallery.setFormId1(blueprintCustomForm.getFormId());
        persistance.putItem(blueprintGallery);

        final DraftGalleryLabel blueprintGalleryLabel = new DraftGalleryLabel();
        blueprintGallery.addLabel(blueprintGalleryLabel);
        blueprintGalleryLabel.setAlign(GalleryAlign.RIGHT);
        blueprintGalleryLabel.getId().setFormItemId(blueprintFormItem.getFormItemId());
        persistance.putGalleryLabel(blueprintGalleryLabel);

        final DraftGalleryItem blueprintGalleryItem = new DraftGalleryItem();
        blueprintGalleryItem.setHeight(112);
        blueprintGalleryItem.getId().setFormItemId(blueprintFormItem.getFormItemId());
        blueprintGallery.addItem(blueprintGalleryItem);
        persistance.putGalleryItem(blueprintGalleryItem);

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

        final DraftGallery gallery = (DraftGallery) widget.getDraftItem();//persistance.getGalleryById(widget.getDraftItem());
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

        final DraftFormItem blueprintFormItem = new DraftFormItem();
        blueprintFormItem.setFormItemName(FormItemName.URL);
        blueprintCustomForm.getFormItems().add(blueprintFormItem);
        persistance.putFormItem(blueprintFormItem);

        final DraftFormFilter blueprintFormFilter = new DraftFormFilter();
        blueprintCustomForm.addFilter(blueprintFormFilter);
        persistance.putFormFilter(blueprintFormFilter);

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

        final DraftGalleryItem blueprintGalleryItem = new DraftGalleryItem();
        blueprintGalleryItem.setHeight(112);
        blueprintGalleryItem.getId().setFormItemId(blueprintFormItem.getFormItemId());
        blueprintGallery.addItem(blueprintGalleryItem);
        persistance.putGalleryItem(blueprintGalleryItem);

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

        final DraftGallery gallery = (DraftGallery) widget.getDraftItem();//persistance.getGalleryById(widget.getDraftItem());
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
    public void connectWithGalleryWidget() {
        final Site blueprint = TestUtil.createSite();
        final DraftForm form = TestUtil.createCustomForm(blueprint.getSiteId(), "s");
        final DraftGallery draftGallery = new DraftGallery();
        draftGallery.setFormId1(form.getFormId());
        draftGallery.setSiteId(blueprint.getSiteId());
        persistance.putItem(draftGallery);

        final PageManager blueprintPageVersion = TestUtil.createPageVersionAndPage(blueprint, PageVersionType.WORK);

        final WidgetItem blueprintWidgetGallery = new WidgetItem();
        blueprintWidgetGallery.setDraftItem(draftGallery);
        blueprintWidgetGallery.setBlueprintShareble(true);
        persistance.putWidget(blueprintWidgetGallery);
        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidgetGallery);


        final Site site = TestUtil.createSite();
        new SiteCopierFromBlueprintReal().execute(new SiteManager(blueprint).getSite(), site, false);

        Assert.assertEquals(2, site.getPages().size());
        final Page page = site.getPages().get(1);

        final PageManager pageVersion = new PageManager(page);
        Assert.assertEquals(1, pageVersion.getWidgets().size());
        final WidgetItem widgetGallery = (WidgetItem) pageVersion.getWidgets().get(0);
        Assert.assertEquals(draftGallery.getId(), widgetGallery.getDraftItem().getId());
        final WorkGallery workGallery = persistance.getWorkItem(draftGallery.getId());
        Assert.assertNull("We don't want publish gallery!", workGallery);
    }

    @Test
    public void connectWithGalleryWidgetWithAlredySharedFormEdit() {
        final Site blueprint = TestUtil.createSite();
        final DraftForm form = TestUtil.createCustomForm(blueprint.getSiteId(), "s");
        final DraftGallery gallery = new DraftGallery();
        gallery.setFormId1(form.getFormId());
        gallery.setSiteId(blueprint.getSiteId());
        persistance.putItem(gallery);

        final PageManager blueprintPageVersion = TestUtil.createPageVersionAndPage(blueprint, PageVersionType.WORK);

        final WidgetItem blueprintWidgetGallery = new WidgetItem();
        blueprintWidgetGallery.setDraftItem(gallery);
        blueprintWidgetGallery.setBlueprintShareble(true);
        persistance.putWidget(blueprintWidgetGallery);
        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidgetGallery);


        final Site site = TestUtil.createSite();
        final SiteOnItem siteOnItemRight = form.createSiteOnItemRight(site);
        siteOnItemRight.setType(SiteOnItemRightType.EDIT);
        persistance.putSiteOnItem(siteOnItemRight);

        new SiteCopierFromBlueprintReal().execute(new SiteManager(blueprint).getSite(), site, false);

        Assert.assertEquals(2, site.getPages().size());
        final Page page = site.getPages().get(1);

        final PageManager pageVersion = new PageManager(page);
        Assert.assertEquals(1, pageVersion.getWidgets().size());
        final WidgetItem widgetGallery = (WidgetItem) pageVersion.getWidgets().get(0);
        Assert.assertEquals(gallery.getId(), widgetGallery.getDraftItem().getId()/*persistance.getDraftItem(widgetGallery.getDraftItem())*/);
    }

    @Test
    public void connectWithGalleryWidgetWithAlredySharedFormRead() {
        final Site blueprint = TestUtil.createSite();
        final DraftForm form = TestUtil.createCustomForm(blueprint.getSiteId(), "s");
        final DraftGallery gallery = new DraftGallery();
        gallery.setFormId1(form.getFormId());
        gallery.setSiteId(blueprint.getSiteId());
        persistance.putItem(gallery);

        final PageManager blueprintPageVersion = TestUtil.createPageVersionAndPage(blueprint, PageVersionType.WORK);

        final WidgetItem blueprintWidgetGallery = new WidgetItem();
        blueprintWidgetGallery.setDraftItem(gallery);
        blueprintWidgetGallery.setBlueprintShareble(true);
        persistance.putWidget(blueprintWidgetGallery);
        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidgetGallery);


        final Site site = TestUtil.createSite();
        final SiteOnItem siteOnItemRight = form.createSiteOnItemRight(site);
        siteOnItemRight.setType(SiteOnItemRightType.READ);
        persistance.putSiteOnItem(siteOnItemRight);

        new SiteCopierFromBlueprintReal().execute(new SiteManager(blueprint).getSite(), site, false);

        Assert.assertEquals(2, site.getPages().size());
        final Page page = site.getPages().get(1);

        final PageManager pageVersion = new PageManager(page);
        Assert.assertEquals(1, pageVersion.getWidgets().size());
        final WidgetItem widgetGallery = (WidgetItem) pageVersion.getWidgets().get(0);
        Assert.assertEquals(gallery.getId(), widgetGallery.getDraftItem().getId()//
                /*persistance.getDraftItem(
                widgetGallery.getDraftItem())*/);
    }

    @Test
    public void connectWithDoubleGalleryWidget() {
        final Site blueprint = TestUtil.createSite();
        final DraftForm form = TestUtil.createCustomForm(blueprint.getSiteId(), "s");
        final DraftGallery gallery = new DraftGallery();
        gallery.setFormId1(form.getFormId());
        gallery.setSiteId(blueprint.getSiteId());
        persistance.putItem(gallery);

        final PageManager blueprintPageVersion = TestUtil.createPageVersionAndPage(blueprint, PageVersionType.WORK);


        final WidgetItem blueprintWidgetGallery1 = new WidgetItem();
        blueprintWidgetGallery1.setDraftItem(gallery);
        blueprintWidgetGallery1.setBlueprintShareble(true);
        persistance.putWidget(blueprintWidgetGallery1);
        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidgetGallery1);


        final WidgetItem blueprintWidgetGallery2 = new WidgetItem();
        blueprintWidgetGallery2.setDraftItem(gallery);
        blueprintWidgetGallery2.setBlueprintShareble(true);
        persistance.putWidget(blueprintWidgetGallery2);
        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidgetGallery2);


        final Site site = TestUtil.createSite();
        new SiteCopierFromBlueprintReal().execute(new SiteManager(blueprint).getSite(), site, false);

        final Page page = site.getPages().get(1);
        final PageManager pageVersion = new PageManager(page);
        Assert.assertEquals(2, pageVersion.getWidgets().size());
        final WidgetItem widgetGallery1 = (WidgetItem) pageVersion.getWidgets().get(0);
        final WidgetItem widgetGallery2 = (WidgetItem) pageVersion.getWidgets().get(1);
        Assert.assertNotSame(gallery.getId(), widgetGallery1.getDraftItem().getId());
        Assert.assertNotSame(gallery.getId(), widgetGallery2.getDraftItem().getId());
        Assert.assertEquals(widgetGallery1.getDraftItem().getId(), widgetGallery2.getDraftItem().getId());
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
    public void connectWithWidgetBlogSummary() {
        final Site blueprint = TestUtil.createSite();
        final DraftBlogSummary blueprintBlogSummary = new DraftBlogSummary();
        blueprintBlogSummary.setSiteId(blueprint.getSiteId());
        persistance.putItem(blueprintBlogSummary);

        final PageManager blueprintPageVersion = TestUtil.createPageVersionAndPage(blueprint, PageVersionType.WORK);

        final WidgetItem blueprintWidgetBlogSummary = TestUtil.createWidgetItem();
        blueprintWidgetBlogSummary.setDraftItem(blueprintBlogSummary);
        persistance.putWidget(blueprintWidgetBlogSummary);
        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidgetBlogSummary);


        final WidgetItem blueprintWidgetBlog = new WidgetItem();
        persistance.putWidget(blueprintWidgetBlog);
        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidgetBlog);


        blueprintBlogSummary.getIncludedCrossWidgetId().add(blueprintWidgetBlog.getCrossWidgetId());

        TestUtil.createPageVersionAndPage(blueprint);

        final Site site = TestUtil.createSite();
        new SiteCopierFromBlueprintReal().execute(new SiteManager(blueprint).getSite(), site, false);

        Assert.assertEquals(2, site.getPages().size());
        final Page page = site.getPages().get(1);

        final PageManager pageVersion = new PageManager(page);
        Assert.assertEquals(2, pageVersion.getWidgets().size());
        final WidgetItem widgetBlogSummary = (WidgetItem) pageVersion.getWidgets().get(0);
        final WidgetItem widgetBlog = (WidgetItem) pageVersion.getWidgets().get(1);
        Assert.assertNotSame(blueprintBlogSummary.getId(), widgetBlogSummary.getDraftItem().getId());
        final DraftBlogSummary blogSummary = (DraftBlogSummary) widgetBlogSummary.getDraftItem();
        TestUtil.assertIntAndBigInt(site.getSiteId(), blogSummary.getSiteId());
        Assert.assertEquals(1, blogSummary.getIncludedCrossWidgetId().size());
        Assert.assertNotSame(blueprintWidgetBlog.getCrossWidgetId(), widgetBlog.getCrossWidgetId());
        Assert.assertEquals(1, blogSummary.getIncludedCrossWidgetId().size());
        TestUtil.assertIntAndBigInt(widgetBlog.getCrossWidgetId(), blogSummary.getIncludedCrossWidgetId().get(0));
    }

    @Test
    public void connectWithDoubleMenuWidget() {
        final Site blueprint = TestUtil.createSite();

        final PageManager blueprintPageVersion = TestUtil.createPageVersionAndPage(blueprint, PageVersionType.WORK);


        final DraftMenu blueprintMenu = new DraftMenu();
        blueprintMenu.setSiteId(blueprint.getSiteId());
        MenuItem menuItem1 = TestUtil.createMenuItem(blueprintPageVersion.getPage().getPageId(), blueprintMenu);
        MenuItem menuItem2 = TestUtil.createMenuItem(blueprintPageVersion.getPage().getPageId(), blueprint.getMenu());
        menuItem1.setParent(null);
        menuItem2.setParent(null);

        persistance.putMenu(blueprintMenu);
        persistance.putMenu(blueprint.getMenu());

        final WidgetItem blueprintWidgetMenu1 = new WidgetItem();
        persistance.putWidget(blueprintWidgetMenu1);
        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidgetMenu1);

        blueprintWidgetMenu1.setDraftItem(blueprintMenu);


        final WidgetItem blueprintWidgetMenu2 = new WidgetItem();
        persistance.putWidget(blueprintWidgetMenu2);
        blueprintPageVersion.getWorkPageSettings().addWidget(blueprintWidgetMenu2);

        blueprintWidgetMenu2.setDraftItem(blueprintMenu);

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

        final DraftMenu menu1 = (DraftMenu) widgetMenu1.getDraftItem();
        final DraftMenu menu2 = (DraftMenu) widgetMenu2.getDraftItem();
        Assert.assertEquals(1, menu1.getMenuItems().size());
        Assert.assertEquals(page.getPageId(), menu1.getMenuItems().get(0).getPageId().intValue());

        Assert.assertNotSame(blueprintMenu.getId(), menu1.getId());
        Assert.assertNotSame(blueprintMenu.getId(), menu2.getId());
        Assert.assertEquals(widgetMenu1.getDraftItem().getId(), widgetMenu2.getDraftItem().getId());
    }

    private final Persistance persistance = ServiceLocator.getPersistance();

}
