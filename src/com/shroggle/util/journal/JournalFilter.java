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
package com.shroggle.util.journal;

import com.shroggle.entity.JournalItemPriority;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Artem Stasuk
 */
public class JournalFilter implements Filter {

    public void init(final FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(
            final ServletRequest request, final ServletResponse response,
            final FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        try {
            journalEasy.add(JournalItemPriority.INFO, "Start " + httpRequest.getRequestURI());
            filterChain.doFilter(request, response);
        } finally {
            journalEasy.add(JournalItemPriority.INFO, "Finish " + httpRequest.getRequestURI());
        }
    }

    public void destroy() {

    }

    private final JournalEasy journalEasy = new JournalEasy(JournalFilter.class);

}
