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
package com.shroggle.util.process.timecounter;

import com.shroggle.util.ServiceLocator;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Artem Stasuk 
 */
public class TimeCounterFilter implements Filter {

    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(
            final ServletRequest servletRequest, final ServletResponse servletResponse,
            final FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        final TimeCounterCreator timeCounterCreator = ServiceLocator.getTimeCounterCreator();
        final TimeCounter timeCounter = timeCounterCreator.create(httpRequest.getRequestURL().toString());
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            timeCounter.stop();
        }
    }

    public void destroy() {
    }

}
