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

/**
 * @author Balakirev Anatoliy
 */
public class ImageData {

    public ImageData(
            Integer imageId, String name, Integer width, Integer height,
            String url, Integer thumbnailWidth, Integer thumbnailHeight) {
        this.imageId = imageId;
        this.name = name;
        this.width = width;
        this.height = height;
        this.url = url;
        this.thumbnailWidth = thumbnailWidth;
        this.thumbnailHeight = thumbnailHeight;
    }

    public Integer getThumbnailHeight() {
        return thumbnailHeight;
    }

    public Integer getThumbnailWidth() {
        return thumbnailWidth;
    }

    public Integer getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public String getUrl() {
        return url;
    }

    private final Integer imageId;
    private final String name;
    private final Integer width;
    private final Integer height;
    private final String url;
    private final Integer thumbnailWidth;
    private final Integer thumbnailHeight;

}