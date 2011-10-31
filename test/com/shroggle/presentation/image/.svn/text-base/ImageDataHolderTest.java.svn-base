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

import org.junit.Test;
import org.junit.runner.RunWith;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.entity.*;

import java.util.List;
import java.util.ArrayList;

import junit.framework.Assert;


/**
 * @author Balakirev Anatoliy
 *         Date: 16.09.2009
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ImageDataHolderTest {


    @Test
    public void testGetFirstLineElements() {
        final List<ImageData> imageDatas = createImageDataElements();
        ImageDataHolder holder = new ImageDataHolder(imageDatas);
        Assert.assertEquals(5, holder.getImageDatas().size());
        Assert.assertEquals(2, holder.getCentralElementIndex());
        final List<ImageData> firstLineElements = holder.getFirstLineElements();
        Assert.assertEquals(3, firstLineElements.size());
        Assert.assertEquals(imageDatas.get(0), firstLineElements.get(0));
        Assert.assertEquals(imageDatas.get(1), firstLineElements.get(1));
        Assert.assertEquals(imageDatas.get(2), firstLineElements.get(2));
    }

    @Test
    public void testGetSecondLineElements() {
        final List<ImageData> imageDatas = createImageDataElements();
        ImageDataHolder holder = new ImageDataHolder(imageDatas);
        Assert.assertEquals(5, holder.getImageDatas().size());
        Assert.assertEquals(2, holder.getCentralElementIndex());
        final List<ImageData> secondLineElements = holder.getSecondLineElements();
        Assert.assertEquals(2, secondLineElements.size());
        Assert.assertEquals(imageDatas.get(3), secondLineElements.get(0));
        Assert.assertEquals(imageDatas.get(4), secondLineElements.get(1));
    }


    @Test
    public void testCreateImageDataHolder_withNotStandardSizes() {
        final ImageData imageData1 = new ImageData(1, "name", 800, 532, "url", 200, null);
        final ImageData imageData2 = new ImageData(1, "name", 532, 800, "url", 88, null);
        final ImageData imageData3 = new ImageData(1, "name", 532, 800, "url", 88, null);
        final List<ImageData> imageDatas = new ArrayList<ImageData>();
        imageDatas.add(imageData1);
        imageDatas.add(imageData2);
        imageDatas.add(imageData3);

        ImageDataHolder holder = new ImageDataHolder(imageDatas);

        Assert.assertEquals(3, holder.getImageDatas().size());
        final List<ImageData> firstLineElements = holder.getFirstLineElements();
        final List<ImageData> secondLineElements = holder.getSecondLineElements();
        Assert.assertEquals(1, firstLineElements.size());
        Assert.assertEquals(2, secondLineElements.size());
    }


    private List<ImageData> createImageDataElements() {
        final ImageData imageData1 = new ImageData(1, "name", 100, 100, "url", 100, null);
        final ImageData imageData2 = new ImageData(1, "name", 200, 200, "url", (200 / (200 / Image.THUMBNAIL_HEIGHT)), null);
        final ImageData imageData3 = new ImageData(1, "name", 300, 300, "url", (300 / (300 / Image.THUMBNAIL_HEIGHT)), null);
        final ImageData imageData4 = new ImageData(1, "name", 400, 400, "url", (400 / (400 / Image.THUMBNAIL_HEIGHT)), null);
        final ImageData imageData5 = new ImageData(1, "name", 500, 500, "url", (500 / (500 / Image.THUMBNAIL_HEIGHT)), null);

        final List<ImageData> imageDatas = new ArrayList<ImageData>();
        imageDatas.add(imageData1);
        imageDatas.add(imageData2);
        imageDatas.add(imageData3);
        imageDatas.add(imageData4);
        imageDatas.add(imageData5);
        return imageDatas;
    }
}
