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
import com.shroggle.logic.paginator.Paginator;
import com.shroggle.logic.site.page.PagesWithoutSystem;
import com.shroggle.logic.statistics.StatisticsPageSort;
import com.shroggle.logic.statistics.StatisticsSortType;
import com.shroggle.logic.statistics.StatisticsTimePeriodType;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class TrafficPagesService extends ServiceWithExecutePage {

    @SynchronizeByMethodParameter(
            entityClass = Site.class)
    @RemoteMethod
    public String sort(final int siteId, final StatisticsSortType sortType, final boolean desc,
                       final StatisticsTimePeriodType timePeriod, final Integer pageNumber) throws IOException, ServletException {
        final UserManager userManager = new UsersManager().getLogined();
        final Site site = userManager.getRight().getSiteRight().getSiteForView(siteId).getSite();

        final List<Page> sitePages = PagesWithoutSystem.get(site.getPages());
        sessionStorage.setStatisticsTimePeriodTypeInServiceForPage(this, timePeriod);


        final Paginator paginator = statisticsPageSort.sort(sitePages, sortType, desc, timePeriod, pageNumber);

        getContext().getHttpServletRequest().setAttribute("paginator", paginator);
        getContext().getHttpServletRequest().setAttribute("trafficPagesSortType", sortType);
        getContext().getHttpServletRequest().setAttribute("descending", desc);
        return executePage("/site/traffic/trafficPagesList.jsp");
    }

    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
    private final StatisticsPageSort statisticsPageSort = new StatisticsPageSort();

}
