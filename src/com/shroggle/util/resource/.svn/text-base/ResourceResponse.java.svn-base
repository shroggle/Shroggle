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

import com.shroggle.entity.Resource;

import java.io.InputStream;

/**
 * @author Artem Stasuk
 */
public final class ResourceResponse {

    public ResourceResponse() {
        this(null, null);
    }

    public ResourceResponse(final Resource resource, final InputStream data) {
        this.resource = resource;
        this.data = data;
    }

    public Resource getResource() {
        return resource;
    }

    public InputStream getData() {
        return data;
    }

    private final Resource resource;
    private final InputStream data;

}
