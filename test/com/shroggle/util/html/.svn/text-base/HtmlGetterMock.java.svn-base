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
package com.shroggle.util.html;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Artem Stasuk (artem).
 * </p>
 * Date: 29.10.2008
 */
public class HtmlGetterMock implements HtmlGetter {

    public String get(
            final String url, final HttpServletRequest request, final HttpServletResponse response,
            final ServletContext servletContext) throws IOException, ServletException {
        int urlIndex = urls.size();
        urls.add(url);
        if (urlIndex >= htmls.size()) {
            return "";
        }
        return htmls.get(urlIndex);
    }

    public void addHtml(final String html) {
        htmls.add(html);
    }

    public List<String> getUrls() {
        return urls;
    }

    private final List<String> htmls = new ArrayList<String>();
    private final List<String> urls = new ArrayList<String>();

}
