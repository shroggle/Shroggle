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
class WidgetImageProvider implements ResourceProvider {

    public Resource get(final ResourceRequest request) {
        final Persistance persistance = ServiceLocator.getPersistance();
        final DraftImage draftItem = persistance.getDraftItem(request.getId());
        final Image image = persistance.getImageById(draftItem.getImageId());
        return ResourceCustom.copyWithSize(image, new ResourceSizeCustom(
                draftItem.getThumbnailWidth(), draftItem.getThumbnailHeight(), true));
    }

}