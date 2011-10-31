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
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.resource.ResourceRequest;

/**
 * @author Stasuk Artem
 */
class GalleryDataProvider implements ResourceProvider {

    public Resource get(final ResourceRequest request) {
        final Persistance persistance = ServiceLocator.getPersistance();

        final DraftGalleryItem galleryItem = persistance.getGalleryItemById(
                request.getSizeId(), request.getSizeAdditionId());
        if (galleryItem == null) {
            return null;
        }

        final Resource resource = persistance.getFormFileById(request.getId());
        if (resource == null) {
            return null;
        }

        final ResourceSize resourceSize = new ResourceSizeCustom(
                galleryItem.getWidth(), galleryItem.getHeight(), true, galleryItem.isCrop());
        return ResourceCustom.copyWithSize(resource, resourceSize);
    }

}