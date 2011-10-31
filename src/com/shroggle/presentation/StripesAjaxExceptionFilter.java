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
package com.shroggle.presentation;

import com.shroggle.exception.StripesAjaxException;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
public class StripesAjaxExceptionFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(final ServletRequest req, final ServletResponse resp, final FilterChain chain) throws IOException, ServletException {
        final HttpServletResponse httpResponse = (HttpServletResponse) resp;

        try {
            chain.doFilter(req, resp);
        } catch (RuntimeException e) {
            final Throwable stripesServletException = e.getCause();
            final Throwable shroggleException = stripesServletException.getCause();
            
            if (shroggleException instanceof StripesAjaxException) {
                httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                httpResponse.setHeader("exception", e.getCause().getCause().getClass().getCanonicalName());
                return;
            }

            throw new RuntimeException(e);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}


