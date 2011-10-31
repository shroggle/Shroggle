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
package com.shroggle.logic.image;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.shroggle.entity.Image;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.TestUtil;
import com.shroggle.TestRunnerWithMockServices;
import junit.framework.Assert;

/**
 * @author Balakirev Anatoliy
 *         Date: 25.08.2009
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ImageManagerTest {

    @Test
    public void testImageFileExist_withoutWidth() {
        Image image = new Image();
        image.setWidth(null);
        image.setHeight(1);
        Assert.assertFalse(ImageManager.imageFileExist(image));
        Integer imageId = image.getImageId();
        Assert.assertFalse(ImageManager.imageFileExist(imageId));
    }

    @Test
    public void testImageFileExist_withoutHeight() {
        Image image = new Image();
        image.setWidth(1);
        image.setHeight(null);
        Assert.assertFalse(ImageManager.imageFileExist(image));
        Integer imageId = image.getImageId();
        Assert.assertFalse(ImageManager.imageFileExist(imageId));
    }

    @Test
    public void testImageFileExist_withWidthEqual0() {
        Image image = new Image();
        image.setWidth(0);
        image.setHeight(10);
        Assert.assertFalse(ImageManager.imageFileExist(image));
        Integer imageId = image.getImageId();
        Assert.assertFalse(ImageManager.imageFileExist(imageId));
    }

    @Test
    public void testImageFileExist_withHeightEqual0() {
        Image image = new Image();
        image.setWidth(0);
        image.setHeight(10);
        Assert.assertFalse(ImageManager.imageFileExist(image));
        Integer imageId = image.getImageId();
        Assert.assertFalse(ImageManager.imageFileExist(imageId));
    }


    @Test
    public void testImageFileExist_withoutImage() {
        Image image = null;
        Assert.assertFalse(ImageManager.imageFileExist(image));
        Integer imageId = null;
        Assert.assertFalse(ImageManager.imageFileExist(imageId));
    }


    @Test
    public void testImageFileExist_withoutImageFile() {
        Image image = new Image();
        image.setWidth(10);
        image.setHeight(10);
        persistance.putImage(image);
        fileSystem.setResourceStream(image, TestUtil.getTempImageStream());
        Assert.assertTrue(ImageManager.imageFileExist(image));
        Integer imageId = image.getImageId();
        Assert.assertTrue(ImageManager.imageFileExist(imageId));
    }

    private final FileSystem fileSystem = ServiceLocator.getFileSystem();
    private final Persistance persistance = ServiceLocator.getPersistance();
}
