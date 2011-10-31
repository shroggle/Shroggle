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

import com.shroggle.entity.Page;
import com.shroggle.entity.User;
import com.shroggle.exception.PageNotFoundException;
import com.shroggle.logic.paginator.Paginator;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.statistics.StatisticsSortType;
import com.shroggle.logic.statistics.StatisticsTimePeriodType;
import com.shroggle.logic.statistics.StatisticsVisitorSort;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.site.LoginedUserInfo;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.html.HtmlUtil;
import com.shroggle.util.persistance.Persistance;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

/**
 * @author dmitry.solomadin
 */
@UrlBinding("/site/trafficVisitors.action")
public class TrafficVisitorsAction extends Action implements LoginedUserInfo {

    @DefaultHandler
    public Resolution show() {
        user = new UsersManager().getLoginedUser();
        if (user == null) {
            return resolutionCreator.loginInUser(this);
        }

        Page page = persistance.getPage(pageId);
        if (page == null) {
            throw new PageNotFoundException("Cannot find page by id=" + pageId);
        }

        timePeriod = sessionStorage.getStatisticsTimePeriodTypeInActionForVisitor(this);
        if (timePeriod == null) {
            timePeriod = StatisticsTimePeriodType.ALL_TIME;
        }

        siteId = page.getSite().getSiteId();
        siteTitle = HtmlUtil.limitName(StringUtil.isNullOrEmpty(page.getSite().getTitle()) ?
                page.getSite().getSubDomain() : page.getSite().getTitle(), 40);
        pageTitle = HtmlUtil.limitName(new PageManager(page).getName(), 40);// todo. Add siteShowOptions if needed. Tolik

        final Paginator paginator = (new StatisticsVisitorSort()).sort(page,
                StatisticsSortType.VISITOR_ID, false, timePeriod, Paginator.getFirstPageNumber());

        getContext().getRequest().setAttribute("paginator", paginator);
        getContext().getRequest().setAttribute("trafficVisitorsSortType", StatisticsSortType.VISITOR_ID);
        getContext().getRequest().setAttribute("descending", false);
        getContext().getRequest().setAttribute("trafficVisitorsSiteId", siteId);
        getContext().getRequest().setAttribute("trafficVisitorsPageId", pageId);
        return resolutionCreator.forwardToUrl("/site/traffic/trafficVisitors.jsp");
    }

    public StatisticsTimePeriodType getTimePeriod() {
        return timePeriod;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public User getLoginUser() {
        return user;
    }

    public String getSiteTitle() {
        return siteTitle;
    }

    public void setSiteTitle(String siteTitle) {
        this.siteTitle = siteTitle;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private StatisticsTimePeriodType timePeriod;

    private User user;
    private int siteId;
    private String siteTitle;
    private String pageTitle;

    public int pageId;
}
