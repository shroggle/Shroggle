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

import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.User;
import com.shroggle.logic.paginator.Paginator;
import com.shroggle.logic.statistics.StatisticsSiteSort;
import com.shroggle.logic.statistics.StatisticsSortType;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;

@RemoteProxy
public class TrafficSitesService extends ServiceWithExecutePage {
    private final Persistance persistance = ServiceLocator.getPersistance();

    @RemoteMethod
    public String sort(final StatisticsSortType sortType, boolean desc, final Integer pageNumber) throws IOException, ServletException {
        final User user = new UsersManager().getLoginedUser();
        final Integer loginUserId = user != null ? user.getUserId() : -1;

        final Paginator paginator =
                (new StatisticsSiteSort()).sort(persistance.getSites(loginUserId,
                        SiteAccessLevel.getUserAccessLevels()), sortType, desc, pageNumber);

        getContext().getHttpServletRequest().setAttribute("paginator", paginator);
        getContext().getHttpServletRequest().setAttribute("trafficSitesSortType", sortType);
        getContext().getHttpServletRequest().setAttribute("descending", desc);
        return executePage("/site/traffic/trafficSitesList.jsp");
    }

}
