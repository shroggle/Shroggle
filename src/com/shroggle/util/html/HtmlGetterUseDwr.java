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

import org.directwebremoting.util.SwallowingHttpServletResponse;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Enumeration;

/**
 * @author Artem Stasuk
 */
public class HtmlGetterUseDwr implements HtmlGetter {

    public String get(
            final String url, final HttpServletRequest request, final HttpServletResponse response,
            final ServletContext servletContext) throws IOException, ServletException {
        final StringWriter sout = new StringWriter();
        final StringBuffer buffer = sout.getBuffer();
        final HttpServletRequest fakeRequest = new HttpServletRequestWrapper(request) {

            public String getHeader(final String h) {
                return null;
            }

            public Enumeration getHeaderNames() {
                return null;
            }

            public int getIntHeader(String s) {
                return -1;
            }

            public long getDateHeader(String s) {
                return -1L;
            }

        };
        final HttpServletResponse fakeResponse =
                new SwallowingHttpServletResponse(response, sout, response.getCharacterEncoding());
        servletContext.getRequestDispatcher(url).forward(fakeRequest, fakeResponse);

        return buffer.toString();
    }

}
