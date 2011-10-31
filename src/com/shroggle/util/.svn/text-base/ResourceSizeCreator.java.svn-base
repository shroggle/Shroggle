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

/**
 * @author Balakirev Antoliy
 */
public class ResourceSizeCreator {

    public static Dimension execute(final Integer requiredWidth, final Integer requiredHeight,
                                    final int sourceWidth, final int sourceHeight, final boolean saveRatio) {
        if (requiredWidth != null && requiredHeight != null) {
            if (saveRatio) {
                final Dimension dimensionWidth = ResourceSizeCreator.executeForWidth(requiredWidth, sourceWidth, sourceHeight);
                final Dimension dimensionHeight = ResourceSizeCreator.executeForHeight(requiredHeight, sourceWidth, sourceHeight);
                if (dimensionWidth.getWidth() <= requiredWidth && dimensionWidth.getHeight() <= requiredHeight) {
                    return dimensionWidth;
                } else {
                    return dimensionHeight;
                }
            } else {
                double scaleX = requiredWidth.doubleValue() / sourceWidth;
                double scaleY = requiredHeight.doubleValue() / sourceHeight;
                int destinationWidth = (int) (scaleX * sourceWidth);
                int destinationHeight = (int) (scaleY * sourceHeight);
                return new Dimension(destinationWidth, destinationHeight, scaleX, scaleY);
            }
        } else if (requiredWidth != null) {
            return executeForWidth(requiredWidth, sourceWidth, sourceHeight);
        } else if (requiredHeight != null) {
            return executeForHeight(requiredHeight, sourceWidth, sourceHeight);
        } else {
            return new Dimension(sourceWidth, sourceHeight, 1, 1);
        }
    }


    private static Dimension executeForWidth(final Integer requiredWidth, final int sourceWidth, final int sourceHeight) {
        int destinationWidth, destinationHeight;
        double scale = requiredWidth.doubleValue() / sourceWidth;
        destinationWidth = requiredWidth;
        destinationHeight = (int) (scale * sourceHeight);
        return new Dimension(destinationWidth, destinationHeight, scale, scale);
    }


    private static Dimension executeForHeight(final Integer requiredHeight, final int sourceWidth, final int sourceHeight) {
        int destinationWidth, destinationHeight;
        double scale = requiredHeight.doubleValue() / sourceHeight;
        destinationHeight = requiredHeight;
        destinationWidth = (int) (scale * sourceWidth);
        return new Dimension(destinationWidth, destinationHeight, scale, scale);
    }
}
