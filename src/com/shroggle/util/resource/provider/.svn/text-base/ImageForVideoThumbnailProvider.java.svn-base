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

import com.shroggle.entity.Resource;
import com.shroggle.entity.ResourceCustom;
import com.shroggle.entity.ResourceSizeCustom;
import com.shroggle.entity.ImageForVideo;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.resource.ResourceRequest;

class ImageForVideoThumbnailProvider implements ResourceProvider {

    public Resource get(final ResourceRequest request) {
        final Resource resource = ServiceLocator.getPersistance().getImageForVideoById(request.getId());
        if (resource == null) {
            return null;
        }

        return ResourceCustom.copyWithSize(resource, ResourceSizeCustom.createByHeight(ImageForVideo.THUMBNAIL_HEIGHT));
    }

}