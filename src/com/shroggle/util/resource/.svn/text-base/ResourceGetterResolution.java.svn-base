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

import com.shroggle.util.html.HtmlUtil;
import net.sourceforge.stripes.action.StreamingResolution;

import java.io.InputStream;

/**
 * Automatically set content type by file`s extension
 *
 * @author Artem Stasuk
 */
public final class ResourceGetterResolution extends StreamingResolution {

    public ResourceGetterResolution(final InputStream inputStream) {
        this(inputStream, "application/force-download");
    }

    public ResourceGetterResolution(final String extension, final InputStream inputStream) {
        this(inputStream, HtmlUtil.getMimeByExtension(extension));
    }

    private ResourceGetterResolution(final InputStream inputStream, final String contentType) {
        super(contentType, inputStream);
    }
}
