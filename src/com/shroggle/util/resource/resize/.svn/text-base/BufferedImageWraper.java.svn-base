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

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Incapsulate information about original transparency, it need for correct
 * save gif image with transparent color. For example you load image resize it and save.
 *
 * @author Artem Stasuk
 * @link http://www.eichberger.de/2007/07/transparent-gifs-in-java.html
 */
class BufferedImageWraper {

    /**
     * Create object and take transparency from buffered image.
     *
     * @param image - image
     */
    public BufferedImageWraper(final BufferedImage image) {
        this.image = image;
        this.transparency = image.getColorModel().getTransparency();
    }

    /**
     * Create new clear image with predefine transparency information.
     * Need when you need resize image or transform. In this case you create
     * new image and keep transparency for correct save.
     *
     * @param original - original image need for get transparency information.
     * @param width    - width
     * @param height   - height
     */
    public BufferedImageWraper(final BufferedImageWraper original, final int width, final int height) {
        int type = BufferedImage.TYPE_4BYTE_ABGR;
        if (original.transparency == Transparency.OPAQUE) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        this.image = new BufferedImage(width, height, type);
        this.transparency = original.transparency;
    }

    /**
     * @return - return original image.
     */
    public BufferedImage get() {
        return image;
    }

    private final int transparency;
    private final BufferedImage image;

}
