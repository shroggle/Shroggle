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
package com.shroggle.presentation.site.render;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dmitry.solomadin
 */
public class ShowPageVersionUrlGetter {

    public static String get(final HttpServletRequest httpServletRequest) {
        return httpServletRequest != null && httpServletRequest.getAttribute("showPageVersionUrl") != null ?
                (String) httpServletRequest.getAttribute("showPageVersionUrl") : "";
    }

}