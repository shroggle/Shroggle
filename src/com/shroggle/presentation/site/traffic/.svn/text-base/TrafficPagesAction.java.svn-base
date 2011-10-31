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
import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.exception.UserException;
import com.shroggle.logic.paginator.Paginator;
import com.shroggle.logic.site.page.PagesWithoutSystem;
import com.shroggle.logic.statistics.StatisticsManager;
import com.shroggle.logic.statistics.StatisticsPageSort;
import com.shroggle.logic.statistics.StatisticsSortType;
import com.shroggle.logic.statistics.StatisticsTimePeriodType;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.site.LoginedUserInfo;
import com.shroggle.presentation.account.dashboard.DashboardAction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByClassProperty;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.List;


@UrlBinding("/site/trafficPages.action")
public class TrafficPagesAction extends Action implements LoginedUserInfo {

    @SynchronizeByClassProperty(
            entityClass = Site.class,
            entityIdFieldPath = "siteId")
    @DefaultHandler
    public Resolution show() {
        final UserManager userManager;
        try {
            userManager = new UsersManager().getLogined();
            site = userManager.getRight().getSiteRight().getSiteForView(siteId).getSite();
        } catch (final SiteNotFoundException exception) {
            return resolutionCreator.redirectToAction(DashboardAction.class);
        } catch (final UserException exception) {
            return resolutionCreator.loginInUser(this);
        }

        siteTitle = StringUtil.isNullOrEmpty(site.getTitle()) ? site.getSubDomain() : site.getTitle();

        user = userManager.getUser();
        timePeriod = sessionStorage.getStatisticsTimePeriodTypeInActionForPage(this);
        if (timePeriod == null) {
            timePeriod = StatisticsTimePeriodType.ALL_TIME;
        }

        final List<Page> sitePages = PagesWithoutSystem.get(site.getPages());
        final Paginator paginator =
                statisticsPageSort.sort(sitePages, StatisticsSortType.NAME, false, timePeriod, Paginator.getFirstPageNumber());

        getContext().getRequest().setAttribute("paginator", paginator);
        getContext().getRequest().setAttribute("trafficPagesSortType", StatisticsSortType.NAME);
        getContext().getRequest().setAttribute("descending", false);
        return resolutionCreator.forwardToUrl("/site/traffic/trafficPages.jsp");
    }

    public Site getSite() {
        return site;
    }

    public User getLoginUser() {
        return user;
    }

    public void setSiteId(final int siteId) {
        this.siteId = siteId;
    }

    public StatisticsTimePeriodType getTimePeriod() {
        return timePeriod;
    }

    public String getSiteTitle() {
        return siteTitle;
    }

    private StatisticsTimePeriodType timePeriod;
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
    private StatisticsManager statisticsManager = new StatisticsManager();
    private final StatisticsPageSort statisticsPageSort = new StatisticsPageSort();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private User user;
    private int siteId;
    private Site site;
    private String siteTitle;

}
