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
package com.shroggle.util;

import net.sourceforge.stripes.action.Resolution;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This resolution add denied cache headers.
 *
 * @author Artem Stasuk
 */
public class WithoutCacheResolution implements Resolution {

    public WithoutCacheResolution(final Resolution resolution) {
        if (resolution == null) {
            throw new UnsupportedOperationException(
                    "Can't create with null resolution!");
        }
        this.resolution = resolution;
    }

    public void execute(
            final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        response.addHeader("Expires", "Wed, 26 Feb 1997 08:21:57 GMT");
        response.addHeader("Cache-Control", "no-cache");
        response.addHeader("Pragma", "no-cache");
        response.addDateHeader("Expires", 0);
        resolution.execute(request, response);
    }

    private final Resolution resolution;

}