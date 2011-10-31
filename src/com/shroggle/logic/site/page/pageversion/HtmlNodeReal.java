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
public class HtmlNodeReal implements HtmlNode {

    public HtmlNodeReal(final StringBuilder html, final int start, final int finish) {
        this.html = html;
        this.start = start;
        this.finish = finish;
    }

    @Override
    public void setValue(final String value) {
        html.replace(start, finish, value);
    }

    private final int start;
    private final int finish;
    private final StringBuilder html;

}
