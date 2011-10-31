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

import com.shroggle.util.StringUtil;

/**
 * @author Artem Stasuk
 */
public class HtmlVisitorPlaceStandart implements HtmlVisitorPlace {

    public HtmlVisitorPlaceStandart(
            final StringBuilder html, final int start, final int finish) {
        this.html = html;
        this.start = start;
        this.finish = finish;
    }

    @Override
    public int getStart() {
        return start;
    }

    @Override
    public void call(final HtmlVisitorListener listener, final int position) {
        listener.onPosition(position, new HtmlNodeReal(html, start, finish));
    }

    private final int start;
    private final int finish;
    private final StringBuilder html;

}
