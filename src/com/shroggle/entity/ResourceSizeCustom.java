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
package com.shroggle.entity;

/**
 * @author Artem Stasuk
 */
public class ResourceSizeCustom implements ResourceSize {

    public static ResourceSize createByWidthHeight(final Integer width, final Integer height) {
        return new ResourceSizeCustom(width, height, false);
    }

    public static ResourceSize createByHeight(final Integer height) {
        return new ResourceSizeCustom(null, height, false);
    }

    public static ResourceSize createByWidth(final Integer width) {
        return new ResourceSizeCustom(width, null, false);
    }

    public static ResourceSize create(final Integer width, final Integer height, final boolean saveRation) {
        return new ResourceSizeCustom(width, height, saveRation);
    }

    public ResourceSizeCustom(final Integer width, final Integer height, final boolean saveRatio) {
        this(width, height, saveRatio, false);
    }

    public ResourceSizeCustom(
            final Integer width, final Integer height,
            final boolean saveRatio, final boolean crop) {
        this.width = width;
        this.height = height;
        this.saveRatio = saveRatio;
        this.crop = crop;
    }

    @Override
    public Integer getWidth() {
        return width;
    }

    @Override
    public Integer getHeight() {
        return height;
    }

    @Override
    public boolean isSaveRatio() {
        return saveRatio;
    }

    @Override
    public boolean isCrop() {
        return crop;
    }

    private final Integer width;
    private final Integer height;
    private final boolean saveRatio;
    private final boolean crop;

}
