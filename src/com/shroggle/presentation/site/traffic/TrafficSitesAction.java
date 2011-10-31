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
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.site.LoginedUserInfo;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/site/trafficSites.action")
public class TrafficSitesAction extends Action implements LoginedUserInfo {

    @DefaultHandler
    public Resolution show() {
        user = new UsersManager().getLoginedUser();
        if (user == null) {
            return resolutionCreator.loginInUser(this);
        }
        final StatisticsSiteSort statisticsSiteSort = new StatisticsSiteSort();
        final Paginator paginator = statisticsSiteSort.sort(persistance.getSites(user.getUserId(),
                SiteAccessLevel.getUserAccessLevels()), StatisticsSortType.NAME, false, Paginator.getFirstPageNumber());

        getContext().getRequest().setAttribute("paginator", paginator);
        getContext().getRequest().setAttribute("trafficSitesSortType", StatisticsSortType.NAME);
        getContext().getRequest().setAttribute("descending", false);
        return resolutionCreator.forwardToUrl("/site/traffic/trafficSites.jsp");
    }

    public User getLoginUser() {
        return user;
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private User user;
}
