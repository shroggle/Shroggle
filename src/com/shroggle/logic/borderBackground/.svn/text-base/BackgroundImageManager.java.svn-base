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

import com.shroggle.entity.BackgroundImage;
import com.shroggle.entity.ResourceSize;
import com.shroggle.entity.ResourceSizeCustom;
import com.shroggle.exception.BackgroundImageNotFoundException;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.ServiceLocator;

import java.awt.image.BufferedImage;

/**
 * @author Balakirev Anatoliy
 *         Date: 23.09.2009
 */
public class BackgroundImageManager {

    public BackgroundImageManager(BackgroundImage backgroundImage) {
        if (backgroundImage == null) {
            throw new BackgroundImageNotFoundException("Can`t create BackgroundImageManager by null BackgroundImage!");
        }
        this.backgroundImage = backgroundImage;
    }

    public ResourceSize getResourceSize() {
        final BufferedImage bufferedImage = fileSystem.getResource(backgroundImage);
        return bufferedImage != null ? ResourceSizeCustom.create(bufferedImage.getWidth(), bufferedImage.getHeight(), true) : null;
    }

    private final BackgroundImage backgroundImage;
    private final FileSystem fileSystem = ServiceLocator.getFileSystem();
}
