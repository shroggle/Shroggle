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

import com.shroggle.entity.Page;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.config.Config;
import com.shroggle.util.persistance.Persistance;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Artem Stasuk
 */
public class PagesFilter implements Filter {

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(
            final ServletRequest servletRequest, final ServletResponse servletResponse,
            final FilterChain filterChain) throws IOException, ServletException {
        final HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String url = servletRequest.getServerName();

        url = StringUtil.trimCutIfNeedAndLower(url, 250);
        if (url.startsWith("www.")) {
            url = url.substring(4, url.length());
        }

        final Config config = ServiceLocator.getConfigStorage().get();
        if (!config.getNotUserSiteUrls().contains(url)) {

            final Persistance persistance = ServiceLocator.getPersistance();
            final Page page = persistance.getPageByOwnDomainName(url);
            if (page != null) {
                final SiteShowOption siteShowOption = null;
                httpResponse.sendRedirect(new PageManager(page, siteShowOption).getWorkPublicUrl());
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

}
