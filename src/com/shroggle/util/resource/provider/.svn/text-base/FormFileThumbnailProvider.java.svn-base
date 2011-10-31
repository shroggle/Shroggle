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

import com.shroggle.entity.FormFile;
import com.shroggle.entity.Resource;
import com.shroggle.entity.ResourceCustom;
import com.shroggle.entity.ResourceSizeCustom;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.resource.ResourceRequest;

/**
 * @author Stasuk Artem
 */
class FormFileThumbnailProvider implements ResourceProvider {

    public Resource get(final ResourceRequest request) {
        final FormFile resource = ServiceLocator.getPersistance().getFormFileById(request.getId());
        if (resource == null) {
            return null;
        }

        return ResourceCustom.copyWithSize(resource,
                ResourceSizeCustom.createByWidth(FORM_FILE_THUMBNAIL_WIDTH));
    }

    public static final int FORM_FILE_THUMBNAIL_WIDTH = 150;

}