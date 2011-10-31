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
package com.shroggle.util.resource.resize;

import com.shroggle.entity.ResourceSizeCustom;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author Artem Stasuk
 */
@RunWith(Parameterized.class)
public class ResourceResizerTest {

    @Parameterized.Parameters
    public static Collection<ResourceResizer[]> data() {
        return Arrays.asList(
                new ResourceResizer[] {new ResourceResizerStandart()},
                new ResourceResizer[] {new ResourceResizerHighQuality()});
    }

    public ResourceResizerTest(final ResourceResizer resourceResizer) {
        this.resourceResizer = resourceResizer;
    }

    @Test
    public void execute() throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        resourceResizer.execute(
                this.getClass().getResourceAsStream("test.png"),
                outputStream, "png", ResourceSizeCustom.createByWidthHeight(10, 10));

        final BufferedImage image = ImageIO.read(
                new ByteArrayInputStream(outputStream.toByteArray()));
        Assert.assertEquals(10, image.getWidth());
        Assert.assertEquals(10, image.getHeight());
    }

    @Test(expected = ResourceResizeException.class)
    public void executeWithNegativeSize() throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        resourceResizer.execute(
                this.getClass().getResourceAsStream("test.png"),
                outputStream, "png", ResourceSizeCustom.createByWidthHeight(-10, -10));
    }

    @Test(expected = ResourceResizeException.class)
    public void executeWithZeroSize() throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        resourceResizer.execute(
                this.getClass().getResourceAsStream("test.png"),
                outputStream, "png", ResourceSizeCustom.createByWidthHeight(0, 0));
    }

    @Test(expected = ResourceResizeException.class)
    public void executeWithNotSupportExt() throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        resourceResizer.execute(
                this.getClass().getResourceAsStream("test.png"),
                outputStream, "1", ResourceSizeCustom.createByWidthHeight(10, 10));
    }

    @Test(expected = ResourceResizeException.class)
    public void executeWithNullExtension() throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        resourceResizer.execute(
                this.getClass().getResourceAsStream("test.png"),
                outputStream, null, ResourceSizeCustom.createByWidthHeight(10, 10));
    }

    @Test(expected = ResourceResizeException.class)
    public void executeWithNullDestination() throws IOException {
        resourceResizer.execute(
                this.getClass().getResourceAsStream("test.png"),
                null, "png", ResourceSizeCustom.createByWidthHeight(10, 10));
    }

    @Test(expected = ResourceResizeException.class)
    public void executeWithNullSource() throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        resourceResizer.execute(
                null, outputStream, "png", ResourceSizeCustom.createByWidthHeight(10, 10));
    }

    @Test
    public void executeForGif() throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        resourceResizer.execute(
                this.getClass().getResourceAsStream("test.gif"),
                outputStream, "gif", ResourceSizeCustom.createByWidthHeight(10, 10));

        final BufferedImage image = ImageIO.read(
                new ByteArrayInputStream(outputStream.toByteArray()));
        Assert.assertEquals(374, image.getWidth());
        Assert.assertEquals(304, image.getHeight());
    }

    @Test(expected = ResourceResizeException.class)
    public void executeForGifWithNotFound() throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        resourceResizer.execute(
                null, outputStream, "gif", ResourceSizeCustom.createByWidthHeight(10, 10));
    }

    @Test
    public void executeForTransparentGif() throws IOException {
        final BufferedImageWraper source = new BufferedImageWraper(ImageIO.read(getClass().getResource("test.gif")));
        Assert.assertEquals(Transparency.BITMASK, source.get().getColorModel().getTransparency());

        final File outFile = File.createTempFile("testImageUtilOut", ".gif", null);
        resourceResizer.execute(
                getClass().getResourceAsStream("test.gif"), new FileOutputStream(outFile), "gif",
                ResourceSizeCustom.createByWidthHeight(100, 120));

        final BufferedImage test = ImageIO.read(outFile);
        Assert.assertEquals(Transparency.BITMASK, test.getColorModel().getTransparency());
        Assert.assertEquals(374, test.getWidth());
        Assert.assertEquals(304, test.getHeight());
    }

    @Test
    public void executeForJpg() throws IOException {
        final BufferedImageWraper source = new BufferedImageWraper(ImageIO.read(getClass().getResource("test.jpg")));
        Assert.assertEquals(Transparency.OPAQUE, source.get().getColorModel().getTransparency());

        final File outFile = File.createTempFile("testImageUtilOut", ".jpg", null);
        resourceResizer.execute(
                getClass().getResourceAsStream("test.jpg"), new FileOutputStream(outFile), "jpg",
                ResourceSizeCustom.createByWidthHeight(50, 70));

        final BufferedImage test = ImageIO.read(outFile);
        Assert.assertEquals(Transparency.OPAQUE, test.getColorModel().getTransparency());
        Assert.assertEquals(50, test.getWidth());
        Assert.assertEquals(70, test.getHeight());
    }

    @Test
    public void executeForTransparentPng() throws IOException {
        final BufferedImageWraper source = new BufferedImageWraper(ImageIO.read(getClass().getResource("test.png")));
        Assert.assertEquals(Transparency.TRANSLUCENT, source.get().getColorModel().getTransparency());

        final File outFile = File.createTempFile("testImageUtilOut", ".png", null);
        resourceResizer.execute(
                getClass().getResourceAsStream("test.png"), new FileOutputStream(outFile), "png",
                ResourceSizeCustom.createByWidthHeight(50, 70));

        final BufferedImage test = ImageIO.read(outFile);
        Assert.assertEquals(Transparency.TRANSLUCENT, test.getColorModel().getTransparency());
        Assert.assertEquals(50, test.getWidth());
        Assert.assertEquals(70, test.getHeight());
    }

    private final ResourceResizer resourceResizer;

}
