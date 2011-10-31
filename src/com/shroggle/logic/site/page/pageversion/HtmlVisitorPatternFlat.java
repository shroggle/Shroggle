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
package com.shroggle.logic.site.page.pageversion;

/**
 * @author Artem Stasuk
 */
class HtmlVisitorPatternFlat implements HtmlVisitorPattern {

    @Override
    public HtmlVisitorPlace find(final StringBuilder html, final int start) {
        final int index = html.indexOf(pattern, start);
        if (index > -1) {
            return new HtmlVisitorPlaceFlat(html, index, index + pattern.length());
        }
        return null;
    }

    private static final String pattern = "mediaBlock=\"true\"";

}