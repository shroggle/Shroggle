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

package com.shroggle.presentation.site.traffic;

import com.shroggle.util.MapUtil;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.statistics.StatisticsManager;
import com.shroggle.logic.statistics.StatisticsTimePeriodType;
import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.exception.PageNotFoundException;
import com.shroggle.exception.PageVisitorNotFoundException;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class ShowAllRefUrlSearchTermsService extends ServiceWithExecutePage {

    @RemoteMethod
    public String executeRefUrlsForSite(final int siteId) throws IOException, ServletException {
        new UsersManager().getLogined();

        final Site site = persistance.getSite(siteId);

        if (site == null) {
            throw new SiteNotFoundException("Cannot find site by Id=" + siteId);
        }

        final Map<String, Integer> refUrlsForSite = MapUtil.sortByValue(
                statisticsManager.getRefUrlsForSite(site, StatisticsTimePeriodType.ALL_TIME), true);

        getContext().getHttpServletRequest().setAttribute("allRefUrlsSearchTerms", refUrlsForSite);
        return executePage("/site/traffic/allRefUrlsSearchTerms.jsp");
    }

    @RemoteMethod
    public String executeRefUrlsForPage(final int pageId) throws IOException, ServletException {
        new UsersManager().getLogined();

        final Page page = persistance.getPage(pageId);

        if (page == null) {
            throw new PageNotFoundException("Cannot find page by Id=" + pageId);
        }

        final Map<String, Integer> refURLsForPage = MapUtil.sortByValue(
                statisticsManager.getRefURLsForPage(page, StatisticsTimePeriodType.ALL_TIME), true);

        getContext().getHttpServletRequest().setAttribute("allRefUrlsSearchTerms", refURLsForPage);
        return executePage("/site/traffic/allRefUrlsSearchTerms.jsp");
    }

    @RemoteMethod
    public String executeSearchTermsForPage(final int pageId) throws IOException, ServletException {
        new UsersManager().getLogined();

        final Page page = persistance.getPage(pageId);

        if (page == null) {
            throw new PageNotFoundException("Cannot find page by Id=" + pageId);
        }

        final Map<String, Integer> refSearchTermsForPage = statisticsManager.getRefSearchTermsForPage(page, StatisticsTimePeriodType.ALL_TIME);

        getContext().getHttpServletRequest().setAttribute("allRefUrlsSearchTerms", refSearchTermsForPage);
        return executePage("/site/traffic/allRefUrlsSearchTerms.jsp");
    }

    @RemoteMethod
    public String executeSearchTermsForVisitor(final int pageVisitorId, final int pageId) throws IOException, ServletException {
        new UsersManager().getLogined();

        final PageVisitor pageVisitor = persistance.getPageVisitorById(pageVisitorId);

        if (pageVisitor == null) {
            throw new PageVisitorNotFoundException("Cannot find page visitor by Id=" + pageVisitorId);
        }

        final Map<String, Integer> refSearchTermsForPageVisitor = new HashMap<String, Integer>();
        final List<VisitReferrer> visitReferrers = statisticsManager.getMergedVisitForPageVisitor
                (pageVisitor.getVisits(), pageVisitor.getPageVisitorId(), pageId).getReferrerSearchTerms();

        for (VisitReferrer visitReferrer : visitReferrers){
            refSearchTermsForPageVisitor.put(visitReferrer.getTermOrUrl(), visitReferrer.getVisitCount());
        }

        getContext().getHttpServletRequest().setAttribute("allRefUrlsSearchTerms", refSearchTermsForPageVisitor);
        return executePage("/site/traffic/allRefUrlsSearchTerms.jsp");
    }

    @RemoteMethod
    public String executeRefUrlsForVisitor(final int pageVisitorId, final int pageId) throws IOException, ServletException {
        new UsersManager().getLogined();

        final PageVisitor pageVisitor = persistance.getPageVisitorById(pageVisitorId);

        if (pageVisitor == null) {
            throw new PageVisitorNotFoundException("Cannot find page visitor by Id=" + pageVisitorId);
        }

        final Map<String, Integer> refURLsForPageVisitor = new HashMap<String, Integer>();
        final List<VisitReferrer> visitReferrers = statisticsManager.getMergedVisitForPageVisitor
                (pageVisitor.getVisits(), pageVisitor.getPageVisitorId(), pageId).getReferrerURLs();

        for (VisitReferrer visitReferrer : visitReferrers){
            refURLsForPageVisitor.put(visitReferrer.getTermOrUrl(), visitReferrer.getVisitCount());
        }

        getContext().getHttpServletRequest().setAttribute("allRefUrlsSearchTerms", refURLsForPageVisitor);
        return executePage("/site/traffic/allRefUrlsSearchTerms.jsp");
    }

    private final StatisticsManager statisticsManager = new StatisticsManager();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
