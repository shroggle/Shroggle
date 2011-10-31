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
 * @author Stasuk Artem
 */
class GalleryProvider implements ResourceProvider {

    public Resource get(final ResourceRequest request) {
        final DraftGallery gallery = ServiceLocator.getPersistance().getDraftItem(request.getSizeId());
        if (gallery == null) {
            return null;
        }

        final Resource resource = ServiceLocator.getPersistance().getFormFileById(request.getId());
        if (resource == null) {
            return null;
        }

        final ResourceSize resourceSize = ResourceSizeCustom.createByWidthHeight(
                gallery.getThumbnailWidth(), gallery.getThumbnailHeight());
        return ResourceCustom.copyWithSize(resource, resourceSize);
    }

}