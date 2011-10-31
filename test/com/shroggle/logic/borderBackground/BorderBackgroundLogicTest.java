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

package com.shroggle.logic.borderBackground;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.Dimension;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.resource.provider.ResourceGetterType;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class BorderBackgroundLogicTest {
    @Test
    public void getBackgroundStyle() {
        Background borderBackground = new Background();
        borderBackground.setBackgroundRepeat("repeat");
        borderBackground.setBackgroundPosition("left");
        borderBackground.setBackgroundColor("red");
        persistance.putBackground(borderBackground);
        BackgroundImage backgroundImage = new BackgroundImage();
        backgroundImage.setSiteId(1);
        ServiceLocator.getPersistance().putBackgroundImage(backgroundImage);
        ServiceLocator.getFileSystem().setResourceStream(backgroundImage, TestUtil.getTempImageStream());
        borderBackground.setBackgroundImageId(backgroundImage.getBackgroundImageId());

        RenderBorderBackground logic = new RenderBorderBackground(null, borderBackground);
        Assert.assertEquals("background: repeat left url(\"" + ServiceLocator.getResourceGetter().get(ResourceGetterType.BACKGROUND_IMAGE, borderBackground.getBackgroundImageId(), 0, 0, 0, false) + "\") red !important; ", logic.getBackgroundStyle());
    }

    @Test
    public void getBackgroundStyle_withImageInDbButWithoutImageFile() {
        Background borderBackground = new Background();
        borderBackground.setBackgroundRepeat("repeat");
        borderBackground.setBackgroundPosition("left");
        borderBackground.setBackgroundColor("red");
        persistance.putBackground(borderBackground);

        BackgroundImage backgroundImage = new BackgroundImage();
        backgroundImage.setSiteId(1);
        ServiceLocator.getPersistance().putBackgroundImage(backgroundImage);
        borderBackground.setBackgroundImageId(backgroundImage.getBackgroundImageId());

        RenderBorderBackground logic = new RenderBorderBackground(null, borderBackground);
        Assert.assertEquals("background: repeat left red !important; ", logic.getBackgroundStyle());
    }

    @Test
    public void getBackgroundStyle_withoutImage() {
        Background borderBackground = new Background();
        borderBackground.setBackgroundRepeat("repeat");
        borderBackground.setBackgroundPosition("left");
        borderBackground.setBackgroundColor("red");
        borderBackground.setBackgroundImageId(-1);
        persistance.putBackground(borderBackground);


        RenderBorderBackground logic = new RenderBorderBackground(null, borderBackground);
        Assert.assertEquals("background: repeat left red !important; ", logic.getBackgroundStyle());
    }

    @Test
    public void createBorderBackground_defaultWithShowForPageFalse() {
        Border borderBackground = BorderLogic.create(-1, null, -1);
        Assert.assertNotNull(borderBackground);
        Assert.assertEquals(-1, borderBackground.getId());
    }

    @Test
    public void createBorderBackground_defaultWithShowForPageFalse_withSite_withBorderBackground() {
        Site site = new Site();
        persistance.putSite(site);
        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        Page page = TestUtil.createPage(site);
        persistance.putPage(page);
        new PageManager(page).addWidget(widget);

        Border borderBackground = new Border();
        persistance.putBorder(borderBackground);
        widget.setBorderId(borderBackground.getId());

        Border newBorderBackground = BorderLogic.create(-1, null, null);
        Assert.assertNotNull(newBorderBackground);
        Assert.assertEquals(-1, newBorderBackground.getId());
    }

    @Test
    public void createBorderBackground_widgetIdWithShowForPageFalse() {
        final PageManager pageManager = TestUtil.createPageVersion(TestUtil.createPage(TestUtil.createSite()));
        Widget widget = TestUtil.createWidgetItem();
        pageManager.addWidget(widget);

        Border border = new Border();
        persistance.putBorder(border);

        widget.setBorderId(border.getId());

        Border newBorderBackground = BorderLogic.create(widget.getWidgetId(), null, null);
        Assert.assertNotNull(newBorderBackground);
        Assert.assertEquals(border.getId(), newBorderBackground.getId());
    }

    @Test
    public void createBorderBackground_pageVersionIdWithShowForPageFalse() {
        PageManager pageVersion = new PageManager(TestUtil.createPageAndSite());

        Border border = new Border();
        persistance.putBorder(border);

        pageVersion.setBorder(border);

        Border newBorderBackground = BorderLogic.create(pageVersion.getPageId(), null, null);
        Assert.assertNotNull(newBorderBackground);
        Assert.assertEquals(-1, newBorderBackground.getId());
    }

    @Test
    public void createBorderBackground_borderBackgroundIdWithShowForPageFalse() {
        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        Border widgetBorderBackground = new Border();
        persistance.putBorder(widgetBorderBackground);
        widget.setBorderId(widgetBorderBackground.getId());

        PageManager pageVersion = new PageManager(TestUtil.createPageAndSite());
        Border versionBorderBackground = new Border();
        persistance.putBorder(versionBorderBackground);
        pageVersion.setBorder(versionBorderBackground);


        Border borderBackground = new Border();
        persistance.putBorder(borderBackground);


        Border newBorderBackground = BorderLogic.create(pageVersion.getPageId(), null, borderBackground.getId());

        Assert.assertNotNull(newBorderBackground);
        Assert.assertEquals(borderBackground.getId(), newBorderBackground.getId());
    }    

    @Test
    public void createImageSize() {
        Style padding = TestUtil.createStyle("padding", StyleType.ALL_SIDES, MeasureUnit.PX, "2");
        Style margin = TestUtil.createStyle("margin", StyleType.ALL_SIDES, MeasureUnit.PX, "1");
        Style width = TestUtil.createStyle("border-width", StyleType.ALL_SIDES, MeasureUnit.PX, "10");
        Style style = TestUtil.createStyle("border-style", StyleType.ALL_SIDES, MeasureUnit.PX, "NONE");

        Border borderBackground = new Border();

        Dimension dimension;

        dimension = RenderBorderBackground.createImageSize(borderBackground);
        Assert.assertNotNull(dimension);
        Assert.assertEquals(dimension.getWidth(), BorderLogic.WIDTH);
        Assert.assertEquals(dimension.getHeight(), BorderLogic.HEIGHT);

        //------------------------------------------with margin---------------------------------------------------------
        borderBackground.setBorderMargin(margin);
        dimension = RenderBorderBackground.createImageSize(borderBackground);
        Assert.assertNotNull(dimension);
        Assert.assertEquals(dimension.getWidth(), (BorderLogic.WIDTH - 2));
        Assert.assertEquals(dimension.getHeight(), (BorderLogic.HEIGHT - 2));

        //-------------------------------------with margin, padding-----------------------------------------------------
        borderBackground.setBorderPadding(padding);
        dimension = RenderBorderBackground.createImageSize(borderBackground);
        Assert.assertNotNull(dimension);
        Assert.assertEquals(dimension.getWidth(), (BorderLogic.WIDTH - 2 - 4));
        Assert.assertEquals(dimension.getHeight(), (BorderLogic.HEIGHT - 2 - 4));

        //-----------------------------with margin, padding, width (without style)--------------------------------------
        borderBackground.setBorderWidth(width);
        dimension = RenderBorderBackground.createImageSize(borderBackground);
        Assert.assertNotNull(dimension);
        Assert.assertEquals(dimension.getWidth(), (BorderLogic.WIDTH - 2 - 4));
        Assert.assertEquals(dimension.getHeight(), (BorderLogic.HEIGHT - 2 - 4));


        //----------------------------with margin, padding, width (with "NONE" style)-----------------------------------
        borderBackground.setBorderStyle(style);
        dimension = RenderBorderBackground.createImageSize(borderBackground);
        Assert.assertNotNull(dimension);
        Assert.assertEquals(dimension.getWidth(), (BorderLogic.WIDTH - 2 - 4));
        Assert.assertEquals(dimension.getHeight(), (BorderLogic.HEIGHT - 2 - 4));


        //------------------------------with margin, padding, width (with "" style)-------------------------------------
        style = TestUtil.createStyle("border-style", StyleType.ALL_SIDES, MeasureUnit.PX, "");
        borderBackground.setBorderStyle(style);
        dimension = RenderBorderBackground.createImageSize(borderBackground);
        Assert.assertNotNull(dimension);
        Assert.assertEquals(dimension.getWidth(), (BorderLogic.WIDTH - 2 - 4));
        Assert.assertEquals(dimension.getHeight(), (BorderLogic.HEIGHT - 2 - 4));


        //----------------------------with margin, padding, width (with null style)-------------------------------------
        style = TestUtil.createStyle("border-style", StyleType.ALL_SIDES, MeasureUnit.PX, null);
        borderBackground.setBorderStyle(style);
        dimension = RenderBorderBackground.createImageSize(borderBackground);
        Assert.assertNotNull(dimension);
        Assert.assertEquals(dimension.getWidth(), (BorderLogic.WIDTH - 2 - 4));
        Assert.assertEquals(dimension.getHeight(), (BorderLogic.HEIGHT - 2 - 4));


        //----------------------------with margin, padding, width (with "solid" style)----------------------------------
        style = TestUtil.createStyle("border-style", StyleType.ALL_SIDES, MeasureUnit.PX, "solid");
        borderBackground.setBorderStyle(style);
        dimension = RenderBorderBackground.createImageSize(borderBackground);
        Assert.assertNotNull(dimension);
        Assert.assertEquals(dimension.getWidth(), (BorderLogic.WIDTH - 2 - 4 - 20));
        Assert.assertEquals(dimension.getHeight(), (BorderLogic.HEIGHT - 2 - 4 - 20));


        //----------------------------with margin, padding, width (with "solid" style)----------------------------------
        StyleValue values = new StyleValue();
        values.setTopValue("10");
        values.setRightValue("1");
        values.setBottomValue("20");
        values.setLeftValue("7");
        width.setValues(values);
        borderBackground.setBorderWidth(width);

        style = TestUtil.createStyle("border-style", StyleType.ALL_SIDES, MeasureUnit.PX, "solid");
        borderBackground.setBorderStyle(style);
        dimension = RenderBorderBackground.createImageSize(borderBackground);
        Assert.assertNotNull(dimension);
        Assert.assertEquals(dimension.getWidth(), (BorderLogic.WIDTH - 2 - 4 - 1 - 7));
        Assert.assertEquals(dimension.getHeight(), (BorderLogic.HEIGHT - 2 - 4 - 10 - 20));


        //-----------------------------------with margin, padding, imageSize < 0----------------------------------------
        width = TestUtil.createStyle("border-width", StyleType.ALL_SIDES, MeasureUnit.PX, "200");
        borderBackground.setBorderWidth(width);

        style = TestUtil.createStyle("border-style", StyleType.ALL_SIDES, MeasureUnit.PX, "solid");
        borderBackground.setBorderStyle(style);
        dimension = RenderBorderBackground.createImageSize(borderBackground);
        Assert.assertNotNull(dimension);
        Assert.assertEquals(dimension.getWidth(), 0);
        Assert.assertEquals(dimension.getHeight(), 0);

        //----------------------------with margin, padding, width (with "solid" style)----------------------------------
        values = new StyleValue();
        values.setTopValue("10");
        values.setRightValue("1");
        values.setBottomValue("20");
        values.setLeftValue("7");
        width.setValues(values);
        borderBackground.setBorderWidth(width);

        values = new StyleValue();
        values.setTopValue("1");
        values.setRightValue("5");
        values.setBottomValue("8");
        values.setLeftValue("7");
        padding.setValues(values);
        borderBackground.setBorderPadding(padding);

        style = TestUtil.createStyle("border-style", StyleType.ALL_SIDES, MeasureUnit.PX, "solid");
        borderBackground.setBorderStyle(style);
        dimension = RenderBorderBackground.createImageSize(borderBackground);
        Assert.assertNotNull(dimension);
        Assert.assertEquals(dimension.getWidth(), (BorderLogic.WIDTH - 2 - 5 - 7 - 1 - 7));
        Assert.assertEquals(dimension.getHeight(), (BorderLogic.HEIGHT - 2 - 1 - 8 - 10 - 20));
    }


    private final Persistance persistance = ServiceLocator.getPersistance();
}
