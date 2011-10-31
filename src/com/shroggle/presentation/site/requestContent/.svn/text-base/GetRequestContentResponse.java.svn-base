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
package com.shroggle.presentation.site.requestContent;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

import java.util.List;

/**
 * @author Artem Stasuk
 */
@DataTransferObject
public class GetRequestContentResponse {

    public GetRequestContentResponse(final String previewUrl, final List<RequestContentItem> items) {
        this.previewUrl = previewUrl;
        this.items = items;
    }

    @RemoteProperty
    public String getPreviewUrl() {
        return previewUrl;
    }

    @RemoteProperty
    public List<RequestContentItem> getItems() {
        return items;
    }

    private final String previewUrl;
    private final List<RequestContentItem> items;

}
