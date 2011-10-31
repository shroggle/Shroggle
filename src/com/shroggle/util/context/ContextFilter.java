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
package com.shroggle.util.context;

import com.shroggle.util.ServiceLocator;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Author: Artem Stasuk (artem).
 * </p>
 * Date: 17.10.2008
 */
public class ContextFilter implements Filter {

    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(
            final ServletRequest request, final ServletResponse response,
            final FilterChain filterChain) throws IOException, ServletException {
        final ContextCreator contextCreator = ServiceLocator.getContextCreator();
        final Context context = contextCreator.create((HttpServletRequest) request);
        try {
            ServiceLocator.getContextStorage().set(context);
            filterChain.doFilter(request, response);
        } finally {
            ServiceLocator.getContextStorage().remove();
        }
    }

    public void destroy() {
    }

}
