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
package com.shroggle.util.resource;

import com.shroggle.util.resource.provider.ResourceGetterType;

/**
 * @author Artem Stasuk
 */
public class ResourceGetterUrlInternal implements ResourceGetterUrl {

    @Override
    public String get(
            final ResourceGetterType getterType, final int resourceId,
            final int sizeId, int sizeAdditionId, final int version, final boolean download) {
        return "/resourceGetter.action?resourceId=" + resourceId + "&resourceSizeId=" + sizeId
                + "&resourceSizeAdditionId=" + sizeAdditionId + "&resourceGetterType="
                + getterType + "&resourceVersion=" + version + "&resourceDownload=" + download;
    }

}
