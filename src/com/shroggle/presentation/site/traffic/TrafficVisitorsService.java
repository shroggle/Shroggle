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
import com.shroggle.exception.PageNotFoundException;
import com.shroggle.logic.paginator.Paginator;
import com.shroggle.logic.statistics.StatisticsSortType;
import com.shroggle.logic.statistics.StatisticsTimePeriodType;
import com.shroggle.logic.statistics.StatisticsVisitorSort;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.persistance.Persistance;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * author: dmitry.solomadin
 * date: 06.11.2008
 */
@RemoteProxy
public class TrafficVisitorsService extends ServiceWithExecutePage {
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
    private final Persistance persistance = ServiceLocator.getPersistance();

    @RemoteMethod
    public String sort(int pageId, StatisticsSortType sortType, boolean desc, StatisticsTimePeriodType timePeriod, final Integer pageNumber) throws IOException, ServletException {
        new UsersManager().getLogined();

        sessionStorage.setStatisticsTimePeriodTypeInServiceForVisitor(this, timePeriod);

        final Page page = persistance.getPage(pageId);
        if (page == null) {
            throw new PageNotFoundException("Cannot find page by Id=" + pageId);
        }

        final Paginator paginator = (new StatisticsVisitorSort()).sort(page, sortType, desc, timePeriod, pageNumber);

        getContext().getHttpServletRequest().setAttribute("paginator", paginator);
        getContext().getHttpServletRequest().setAttribute("trafficVisitorsSortType", sortType);
        getContext().getHttpServletRequest().setAttribute("descending", desc);
        getContext().getHttpServletRequest().setAttribute("trafficVisitorsSiteId", page.getSite().getSiteId());
        getContext().getHttpServletRequest().setAttribute("trafficVisitorsPageId", pageId);
        return executePage("/site/traffic/trafficVisitorsList.jsp");
    }

}
