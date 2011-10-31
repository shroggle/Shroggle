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

import com.shroggle.exception.ImageDataNotFoundException;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Balakirev Anatoliy
 *         Date: 16.09.2009
 */
public class ImageDataHolder {

    public ImageDataHolder(List<ImageData> imageDatas) {
        if (imageDatas == null) {
            throw new ImageDataNotFoundException("Can`t create ImageDataHolder by null ImageData list!");
        }
        this.imageDatas = imageDatas;
        this.centralElementIndex = createCentralElementIndex();
    }

    public List<ImageData> getImageDatas() {
        return imageDatas;
    }

    public List<ImageData> getFirstLineElements() {
        final List<ImageData> imageDatas = new ArrayList<ImageData>();
        if (centralElementIndex >= 0) {
            for (int i = 0; i < centralElementIndex + 1; i++) {
                imageDatas.add(this.imageDatas.get(i));
            }
        }
        return imageDatas;
    }

    public List<ImageData> getSecondLineElements() {
        final List<ImageData> imageDatas = new ArrayList<ImageData>();
        if (centralElementIndex >= 0 && (centralElementIndex + 1 <= this.imageDatas.size())) {
            for (int i = centralElementIndex + 1; i < this.imageDatas.size(); i++) {
                imageDatas.add(this.imageDatas.get(i));
            }
        }
        return imageDatas;
    }

    private int createCentralElementIndex() {
        int totalWidth = createElementsThumbnailWidth();
        int currentWidth = 0;
        for (int i = 0; i < imageDatas.size(); i++) {
            currentWidth += imageDatas.get(i).getThumbnailWidth();
            if (currentWidth > (totalWidth / 2)) {
                return i;
            }
        }
        return -1;
    }

    private int createElementsThumbnailWidth() {
        int width = 0;
        for (ImageData imageData : imageDatas) {
            width += imageData.getThumbnailWidth();
        }
        return width;
    }

    public int getCentralElementIndex() {
        return centralElementIndex;
    }

    private final List<ImageData> imageDatas;
    private final int centralElementIndex;
}
