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
package com.shroggle.presentation.image;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.resource.ResourceGetterUrl;
import com.shroggle.util.resource.provider.ResourceGetterType;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 *         Date: 16.09.2009
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ConfigureImageDataTest {

    private final ResourceGetterUrl resourceGetterUrl = ServiceLocator.getResourceGetter();
    private final Persistance persistance = ServiceLocator.getPersistance();


    /*-------------------------------------------------get image url--------------------------------------------------*/

    @Test
    public void testGetImageUrl() {
        final Site site = TestUtil.createSite();
        final Image image = TestUtil.createImage(site.getSiteId(), "name", "jpeg");
        final WidgetItem widgetItem = TestUtil.createWidgetImage(image.getImageId(), null);
        final DraftImage draftImage = (DraftImage)(widgetItem.getDraftItem());
        ConfigureImageData data = new ConfigureImageData(draftImage, widgetItem.getWidgetId());

        Assert.assertEquals(resourceGetterUrl.get(ResourceGetterType.WIDGET_IMAGE, draftImage.getId(), 0, 0, draftImage.getVersion(), false), data.getImageUrl());
    }

    @Test
    public void testGetImageUrl_withoutImage() {
        final WidgetItem widgetImage = TestUtil.createWidgetImage(null, null);
        ConfigureImageData data = new ConfigureImageData((DraftImage) widgetImage.getDraftItem(), widgetImage.getWidgetId());

        Assert.assertEquals("undefined", data.getImageUrl());
    }


    @Test
    public void testGetImageUrl_withoutImageFile() {
        final Site site = TestUtil.createSite();
        final Image image = new Image();
        image.setSiteId(site.getSiteId());
        persistance.putImage(image);
        final WidgetItem widgetImage = TestUtil.createWidgetImage(image.getImageId(), null);
        ConfigureImageData data = new ConfigureImageData((DraftImage) widgetImage.getDraftItem(), widgetImage.getWidgetId());

        Assert.assertEquals("undefined", data.getImageUrl());
    }
    /*-------------------------------------------------get image url--------------------------------------------------*/

    /*------------------------------------------get roll over image url-----------------------------------------------*/

    @Test
    public void testGetRollOverImageUrl() {
        final Site site = TestUtil.createSite();
        final Image image = TestUtil.createImage(site.getSiteId(), "name", "jpeg");
        final WidgetItem widgetItem = TestUtil.createWidgetImage(null, image.getImageId());
        final DraftImage draftImage = (DraftImage)(widgetItem.getDraftItem());
        ConfigureImageData data = new ConfigureImageData(draftImage, widgetItem.getWidgetId());

        Assert.assertEquals(resourceGetterUrl.get(ResourceGetterType.WIDGET_IMAGE_ROLLOVER, draftImage.getId(), 0, 0, draftImage.getVersion(), false), data.getRollOverImageUrl());
    }

    @Test
    public void testGetRollOverImageUrl_withoutImage() {
        final WidgetItem widgetImage = TestUtil.createWidgetImage(null, null);
        ConfigureImageData data = new ConfigureImageData((DraftImage) widgetImage.getDraftItem(), widgetImage.getWidgetId());

        Assert.assertEquals("undefined", data.getRollOverImageUrl());
    }

    @Test
    public void testGetRollOverImageUrl_withoutImageFile() {
        final Site site = TestUtil.createSite();
        final Image image = new Image();
        image.setSiteId(site.getSiteId());
        persistance.putImage(image);
        final WidgetItem widgetImage = TestUtil.createWidgetImage(null, image.getImageId());
        ConfigureImageData data = new ConfigureImageData((DraftImage) widgetImage.getDraftItem(), widgetImage.getWidgetId());

        Assert.assertEquals("undefined", data.getRollOverImageUrl());
    }
    /*------------------------------------------get roll over image url-----------------------------------------------*/


    /*-----------------------------------------------------margin-----------------------------------------------------*/

    @Test
    public void testGetMargin() {
        final Site site = TestUtil.createSite();
        final Image image = TestUtil.createImage(site.getSiteId(), "name", "jpeg");
        final WidgetItem widgetItem = TestUtil.createWidgetImage(null, image.getImageId());
        final DraftImage draftImage = (DraftImage)(widgetItem.getDraftItem());

        draftImage.setMargin(100);

        ConfigureImageData data = new ConfigureImageData(draftImage, widgetItem.getWidgetId());
        Assert.assertEquals(100, data.getMargin());
    }

    @Test
    public void testGetMargin_withoutPx() {
        final Site site = TestUtil.createSite();
        final Image image = TestUtil.createImage(site.getSiteId(), "name", "jpeg");
        final WidgetItem widgetItem = TestUtil.createWidgetImage(null, image.getImageId());
        final DraftImage draftImage = (DraftImage)(widgetItem.getDraftItem());

        draftImage.setMargin(100);

        ConfigureImageData data = new ConfigureImageData(draftImage, widgetItem.getWidgetId());
        Assert.assertEquals(100, data.getMargin());
    }

    /*-----------------------------------------------------margin-----------------------------------------------------*/

    /*----------------------------------------------------versions----------------------------------------------------*/

    @Test
    public void testGetVersions() {
        final Site site = TestUtil.createSite();
        final Image image = TestUtil.createImage(site.getSiteId(), "name", "jpeg");
        final WidgetItem widgetImage = TestUtil.createWidgetImage(null, image.getImageId());
        widgetImage.getDraftItem().setSiteId(site.getSiteId());

        Page page = TestUtil.createPage(site);
        Page page2 = TestUtil.createPage(site);

        widgetImage.setDraftPageSettings(page.getPageSettings());

        ConfigureImageData data = new ConfigureImageData((DraftImage) widgetImage.getDraftItem(), widgetImage.getWidgetId());
        List<PageManager> pageVersions = data.getPageManagers();
        Assert.assertEquals(2, pageVersions.size());
        Assert.assertNotNull(new PageManager(page).getName(), pageVersions.get(0).getName());
        Assert.assertNotNull(new PageManager(page2).getName(), pageVersions.get(1).getName());
    }

    /*----------------------------------------------------versions----------------------------------------------------*/

    /*---------------------------------------------------image files--------------------------------------------------*/

    @Test
    public void testCreateImageFiles() {
        final Site site = TestUtil.createSite();

        final Date currentDate = new Date();

        ImageFile imageFile1 = TestUtil.createImageFile(site.getSiteId(), "", "", ImageFileType.IMAGE);
        imageFile1.setCreated(currentDate);
        ImageFile imageFile2 = TestUtil.createImageFile(site.getSiteId(), "", "", ImageFileType.IMAGE);
        imageFile2.setCreated(new Date(currentDate.getTime() - 10000000000L));
        ImageFile imageFile3 = TestUtil.createImageFile(site.getSiteId(), "", "", ImageFileType.IMAGE);
        imageFile3.setCreated(new Date(currentDate.getTime() + 10000000000L));
        TestUtil.createImageFile(site.getSiteId(), "", "", ImageFileType.AUDIO);
        TestUtil.createImageFile(site.getSiteId(), "", "", ImageFileType.CAD);
        TestUtil.createImageFile(site.getSiteId(), "", "", ImageFileType.FLASH);
        TestUtil.createImageFile(site.getSiteId(), "", "", ImageFileType.PDF);

        final Image image = TestUtil.createImage(site.getSiteId(), "name", "jpeg");
        final WidgetItem widgetImage = TestUtil.createWidgetImage(null, image.getImageId());
        widgetImage.getDraftItem().setSiteId(site.getSiteId());

        Page page = TestUtil.createPage(site);
        widgetImage.setDraftPageSettings(page.getPageSettings());

        ConfigureImageData data = new ConfigureImageData((DraftImage) widgetImage.getDraftItem(), widgetImage.getWidgetId());

        final List<ImageFile> imageFiles = data.createImageFiles(ImageFileType.IMAGE, null);
        Assert.assertEquals(3, imageFiles.size());
        Assert.assertEquals(imageFile3, imageFiles.get(0));
        Assert.assertEquals(imageFile1, imageFiles.get(1));
        Assert.assertEquals(imageFile2, imageFiles.get(2));
    }

    /*---------------------------------------------------image files--------------------------------------------------*/


    /*-------------------------------------------------------title----------------------------------------------------*/

    @Test
    public void testGetTitle_withDescriptionInAlt() {
        final Site site = TestUtil.createSite();
        final Image image = TestUtil.createImage(site.getSiteId(), "name", "jpeg");
        final WidgetItem widgetItem = TestUtil.createWidgetImage(image.getImageId(), null);
        final DraftImage draftImage = (DraftImage)(widgetItem.getDraftItem());

        draftImage.setDescription("description");
        draftImage.setTitle("title");
        draftImage.setShowDescriptionOnMouseOver(true);

        ConfigureImageData data = new ConfigureImageData(draftImage, widgetItem.getWidgetId());
        Assert.assertEquals(draftImage.getDescription(), data.getTitle());
    }

    @Test
    public void testGetTitle_withoutDescriptionInAlt() {
        final Site site = TestUtil.createSite();
        final Image image = TestUtil.createImage(site.getSiteId(), "name", "jpeg");
        final WidgetItem widgetItem = TestUtil.createWidgetImage(image.getImageId(), null);
        final DraftImage draftImage = (DraftImage)(widgetItem.getDraftItem());

        draftImage.setDescription("description");
        draftImage.setTitle("title");
        draftImage.setShowDescriptionOnMouseOver(false);

        ConfigureImageData data = new ConfigureImageData(draftImage, widgetItem.getWidgetId());
        Assert.assertEquals(draftImage.getTitle(), data.getTitle());
    }


    /*-------------------------------------------------------title----------------------------------------------------*/

    @Test
    public void testGetWidth() {
        final Site site = TestUtil.createSite();
        final Image image = TestUtil.createImage(site.getSiteId(), "name", "jpeg");
        final WidgetItem widgetItem = TestUtil.createWidgetImage(image.getImageId(), null);
        final DraftImage draftImage = (DraftImage)(widgetItem.getDraftItem());

        draftImage.setThumbnailWidth(123123);
        ConfigureImageData data = new ConfigureImageData(draftImage, widgetItem.getWidgetId());
        Assert.assertEquals(draftImage.getThumbnailWidth(), data.getWidth());
    }

    @Test
    public void testGetHeight() {
        final Site site = TestUtil.createSite();
        final Image image = TestUtil.createImage(site.getSiteId(), "name", "jpeg");
        final WidgetItem widgetItem = TestUtil.createWidgetImage(image.getImageId(), null);
        final DraftImage draftImage = (DraftImage)(widgetItem.getDraftItem());

        draftImage.setThumbnailHeight(6578678);
        ConfigureImageData data = new ConfigureImageData(draftImage, widgetItem.getWidgetId());
        Assert.assertEquals(draftImage.getThumbnailHeight(), data.getHeight());
    }

    @Test
    public void testIsLabelIsALinnk() {
        WidgetItem widgetImage = TestUtil.createWidgetItem();
        DraftImage image1 = new DraftImage();
        persistance.putItem(image1);
        widgetImage.setDraftItem(image1);
        image1.setLabelIsALinnk(true);
        ConfigureImageData configureImageData = new ConfigureImageData(image1, widgetImage.getWidgetId());
        Assert.assertTrue(configureImageData.isLabelIsALinnk());
    }

    @Test
    public void testIsLabelIsALinnk_false() {
        WidgetItem widgetImage = TestUtil.createWidgetItem();
        DraftImage image1 = new DraftImage();
        persistance.putItem(image1);
        widgetImage.setDraftItem(image1);
        image1.setLabelIsALinnk(false);
        ConfigureImageData configureImageData = new ConfigureImageData(image1, widgetImage.getWidgetId());
        Assert.assertFalse(configureImageData.isLabelIsALinnk());
    }

    @Test
    public void testIsImageIsALinnk() {
        WidgetItem widgetImage = TestUtil.createWidgetItem();
        DraftImage image1 = new DraftImage();
        persistance.putItem(image1);
        widgetImage.setDraftItem(image1);
        image1.setImageIsALinnk(true);
        ConfigureImageData configureImageData = new ConfigureImageData(image1, widgetImage.getWidgetId());
        Assert.assertTrue(configureImageData.isImageIsALinnk());
    }

    @Test
    public void testIsImageIsALinnk_false() {
        WidgetItem widgetImage = TestUtil.createWidgetItem();
        DraftImage image1 = new DraftImage();
        persistance.putItem(image1);
        widgetImage.setDraftItem(image1);
        image1.setImageIsALinnk(false);
        ConfigureImageData configureImageData = new ConfigureImageData(image1, widgetImage.getWidgetId());
        Assert.assertFalse(configureImageData.isImageIsALinnk());
    }

    @Test
    public void testIsDisableLinksArea_withImageLink() throws Exception {
        final WidgetItem widgetImage = TestUtil.createWidgetItem();
        DraftImage image1 = new DraftImage();
        persistance.putItem(image1);
        widgetImage.setDraftItem(image1);
        image1.setImageIsALinnk(true);
        image1.setLabelIsALinnk(false);
        final ConfigureImageData configureImageData = new ConfigureImageData(image1, widgetImage.getWidgetId());
        Assert.assertFalse(configureImageData.isDisableLinksArea());
    }

    @Test
    public void testIsDisableLinksArea_withLabelLink() throws Exception {
        final WidgetItem widgetImage = TestUtil.createWidgetItem();
        DraftImage image1 = new DraftImage();
        persistance.putItem(image1);
        widgetImage.setDraftItem(image1);
        image1.setImageIsALinnk(false);
        image1.setLabelIsALinnk(true);
        final ConfigureImageData configureImageData = new ConfigureImageData(image1, widgetImage.getWidgetId());
        Assert.assertFalse(configureImageData.isDisableLinksArea());
    }

    @Test
    public void testIsDisableLinksArea_withoutLabelAndImageLink() throws Exception {
        final WidgetItem widgetImage = TestUtil.createWidgetItem();
        DraftImage image1 = new DraftImage();
        persistance.putItem(image1);
        widgetImage.setDraftItem(image1);
        image1.setImageIsALinnk(false);
        image1.setLabelIsALinnk(false);
        final ConfigureImageData configureImageData = new ConfigureImageData(image1, widgetImage.getWidgetId());
        Assert.assertTrue(configureImageData.isDisableLinksArea());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testWithoutDraftImage() throws Exception {
        new ConfigureImageData(null, null);
    }
}
