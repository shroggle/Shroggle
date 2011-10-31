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
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.resource.ResourceRequest;

/**
 * @author Balakirev Anatoliy
 */
class GalleryDataSourceSizeProvider implements ResourceProvider {

    public Resource get(ResourceRequest request) {
        final FormFile resource = ServiceLocator.getPersistance().getFormFileById(request.getId());
        if (resource.getWidth() > FormFile.MAX_SOURCE_IMAGE_WIDTH || resource.getHeight() > FormFile.MAX_SOURCE_IMAGE_HEIGHT) {
            final ResourceSize resourceSize = ResourceSizeCustom.createByWidthHeight(
                    FormFile.MAX_SOURCE_IMAGE_WIDTH, FormFile.MAX_SOURCE_IMAGE_HEIGHT);
            return ResourceCustom.copyWithSize(resource, resourceSize);
        }
        return resource;
    }

}
