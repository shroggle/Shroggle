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
package com.shroggle.util.html.optimization;

import com.shroggle.util.html.HtmlUtil;

/**
 * @author Artem Stasuk
 */
class PageResourcesInternalResult {

    public PageResourcesInternalResult(final String name, final PageResourcesValue value) {
        this.value = value.getValue();
        this.name = name;
    }

    public String getMime() {
        return HtmlUtil.getMimeByName(name);
    }

    public String getValue() {
        return value;
    }

    private final String name;
    private final String value;

}
