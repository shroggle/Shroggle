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

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Stasuk Artem
 */
public class CacheControlFilter implements Filter {

    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(
            final ServletRequest servletRequest, final ServletResponse servletResponse,
            final FilterChain filterChain) throws IOException, ServletException {
        // add headers for cache result
        final HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        httpResponse.addHeader("Cache-Control", "cache");
        httpResponse.addHeader("Pragma", "cache");
        httpResponse.addDateHeader("Expires", System.currentTimeMillis() * 2);
        httpResponse.addDateHeader("Last-Modified", 0);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {
    }

}
