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

import com.shroggle.entity.Image;
import com.shroggle.util.ServiceLocator;


/**
 * @author Balakirev Anatoliy
 */
public class ImageManager {

    public static boolean imageFileExist(final Integer imageId) {
        return imageFileExist(ServiceLocator.getPersistance().getImageById(imageId));
    }

    public static boolean imageFileExist(final Image image) {
        return image != null && image.getWidth() != null && image.getWidth() > 0 && image.getHeight() != null &&
                image.getHeight() > 0 && ServiceLocator.getFileSystem().isResourceExist(image);
    }

    public final static int LOGO_WIDTH = 364;
    public final static int LOGO_HEIGHT = 85;

    public final static int FOOTER_IMAGE_WIDTH = 364;
    public final static int FOOTER_IMAGE_HEIGHT = 85;
}