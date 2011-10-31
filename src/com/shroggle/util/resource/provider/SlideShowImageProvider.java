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
package com.shroggle.util.resource.provider;

import com.shroggle.entity.*;
import com.shroggle.logic.slideShow.SlideShowImageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.resource.ResourceRequest;

/**
 * @author dmitry.solomadin
 */
class SlideShowImageProvider implements ResourceProvider {

    public Resource get(final ResourceRequest request) {
        final Image image = ServiceLocator.getPersistance().getImageById(request.getId());

        if (image == null) {
            return null;
        }

        return ResourceCustom.copyWithSize(image,
                ResourceSizeCustom.createByWidthHeight(image.getWidth(),
                        image.getHeight()));
    }

}
