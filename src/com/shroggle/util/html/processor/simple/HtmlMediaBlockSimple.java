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
package com.shroggle.util.html.processor.simple;

import com.shroggle.util.html.processor.HtmlMediaBlock;

/**
 * @author Artem Stasuk
 */
class HtmlMediaBlockSimple implements HtmlMediaBlock {

    @Override
    public void setHtml(final String html) {
        this.html = html;
    }

    public String getHtml() {
        return html;
    }

    private String html;

}
