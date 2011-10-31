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

import com.shroggle.entity.*;
import com.shroggle.logic.site.NotUsersSiteUrlsNormalizer;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.context.ContextStorage;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.url.UrlValidator;

import javax.servlet.Filter;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * getRequestURL = http://localhost:8080/index.jsp
 * getServerName = localhost
 * getServerName = a.localhost
 *
 * @author Stasuk Artem
 * @see com.shroggle.presentation.site.ShowPageVersionAction
 */
public class SitesFilter implements Filter {
    public void destroy() {
    }

    public void init(final FilterConfig config) throws ServletException {
    }

    public void doFilter(
            final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws ServletException, IOException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final HttpServletResponse httpResponse = (HttpServletResponse) response;
        final String url = httpRequest.getServerName();
        final String fullUrl = httpRequest.getRequestURI();

        // if this url used for main application without www
        if (!url.toLowerCase().contains("localhost") && !url.toLowerCase().startsWith("www")
                && !url.toLowerCase().contains(".amazonaws.com") && !UrlValidator.isIpAddress(httpRequest.getServerName())) {
            final String normalizedUrl = new NotUsersSiteUrlsNormalizer(httpRequest).getNormalizedUrl();
            httpResponse.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            httpResponse.setHeader("Location", normalizedUrl);
            httpResponse.setHeader("Connection", "close");
            return;
        }

        if (fullUrl.toLowerCase().endsWith("sitemap.xml")) {
            final Site site = ServiceLocator.getSiteByUrlGetter().get(url);
            if (site != null) {
                httpRequest.getRequestDispatcher("/site/siteMapGenerator.action?siteId=" +
                        site.getSiteId()).forward(request, response);
            }
            return;
        }

        if (fullUrl.toLowerCase().endsWith("robots.txt")) {
            final Site site = ServiceLocator.getSiteByUrlGetter().get(url);
            if (site != null) {
                httpRequest.getRequestDispatcher("/site/robotsTxtGenerator.action?siteId=" +
                        site.getSiteId()).forward(request, response);
            }
            return;
        }

        // if this url used for main application
        if (configStorage.get().getNotUserSiteUrls().contains(url)) {
            chain.doFilter(request, response);
            return;
        }

        final Site site = ServiceLocator.getSiteByUrlGetter().get(url);

        // if this url don't assign to site
        if (site == null) {
            chain.doFilter(request, response);
            return;
        }

        contextStorage.get().setSiteId(site.getSiteId());
        if (ServiceLocator.getConfigStorage().get().getBillingInfoProperties().isMustBePaidBeforePublishing() &&
                site.getSitePaymentSettings().getSiteStatus() != SiteStatus.ACTIVE) {
            request.getRequestDispatcher(
                    "/site/siteUnderConstruction.action?siteUrl=" +
                            site.getSubDomain() +
                            "&siteShowOption=" + SiteShowOption.getWorkOption()).forward(request, response);
            return;
        }

        String pageUrl = httpRequest.getRequestURI().trim().toLowerCase();
        if (pageUrl != null) {
            if (pageUrl.startsWith("/")) {
                pageUrl = pageUrl.substring(1);
            }
            if (pageUrl.endsWith("/")) {
                pageUrl = pageUrl.substring(0, pageUrl.length() - 1);
            }
            if (pageUrl.isEmpty()) {
                pageUrl = null;
            }
        }

        if (pageUrl == null) {
            final List<MenuItem> items = site.getMenu().getMenuItems();
            if (items.size() > 0) {
                if (new PageManager(persistance.getPage(items.get(0).getPageId())).getWorkPageSettings() != null) {
                    request.getRequestDispatcher(
                            "/site/showPageVersion.action?pageId=" +
                                    items.get(0).getPageId() +
                                    "&siteShowOption=" + SiteShowOption.getWorkOption()).forward(request, response);
                    return;
                } else {
                    request.getRequestDispatcher(
                            "/site/siteUnderConstruction.action?siteUrl=" +
                                    site.getSubDomain() +
                                    "&siteShowOption=" + SiteShowOption.getWorkOption()).forward(request, response);
                    return;
                }
            }
            httpResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        for (final String sitesResourceUrl : fileSystem.getSitesResourcesUrl()) {
            if (pageUrl.toLowerCase().startsWith(sitesResourceUrl.toLowerCase())) {
                chain.doFilter(request, response);
                return;
            }
        }


        final Page page = persistance.getPageByUrlAndAndSiteIgnoreUrlCase(pageUrl, site.getSiteId());
        if (page != null) {
            // "persistance.getPageByUrlAndAndSiteIgnoreUrlCase();" gets Page by url from work or draft pageSettings,
            // so we must check that needed pageSettings (in this case work) has needed url. Tolik
            final PageManager pageManager = new PageManager(page, SiteShowOption.getWorkOption());
            if (pageManager.getUrl().equalsIgnoreCase("/" + pageUrl)) {// Work page exist and has correct url.
                request.getRequestDispatcher(
                        "/site/showPageVersion.action?pageId=" + page.getPageId() +
                                "&siteShowOption=" + SiteShowOption.getWorkOption()).forward(request, response);
                return;
            } else {
                request.getRequestDispatcher(
                        "/site/siteUnderConstruction.action?siteUrl=" +
                                site.getSubDomain() +
                                "&siteShowOption=" + SiteShowOption.getWorkOption()).forward(request, response);
                return;
            }
        }

        httpResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final FileSystem fileSystem = ServiceLocator.getFileSystem();
    private final ContextStorage contextStorage = ServiceLocator.getContextStorage();
    private final ConfigStorage configStorage = ServiceLocator.getConfigStorage();

}
