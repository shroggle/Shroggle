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
package com.shroggle.presentation.site;

import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.Widget;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.widget.WidgetTitle;
import com.shroggle.logic.widget.WidgetTitleGetter;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.List;

/**
 * @author dmitry.solomadin
 */
@UrlBinding("/site/showAddWidget.action")
public class ShowAddWidgetAction extends Action implements WithWidgetTitle {

    @DefaultHandler
    public Resolution execute() {
        final UserManager userManager = new UsersManager().getLogined();

        if (showFromManageItemsPage) {
            availableSites = persistance.getSites(userManager.getUserId(), SiteAccessLevel.getUserAccessLevels());

            if (availableSites.isEmpty()) {
                throw new SiteNotFoundException();
            }

            selectedSiteId = availableSites.get(0).getSiteId();
        } else {
            final Widget widget = userManager.getRight().getSiteRight().getWidgetForEditInPresentationalService(widgetId);
            widgetTitle = new WidgetTitleGetter(widget);

            selectedSiteId = widget.getSiteId();
        }

        return resolutionCreator.forwardToUrl("/site/addWidget.jsp");
    }

    public WidgetTitle getWidgetTitle() {
        return widgetTitle;
    }

    public List<Site> getAvailableSites() {
        return availableSites;
    }

    public Integer getSelectedSiteId() {
        return selectedSiteId;
    }

    public void setWidgetId(Integer widgetId) {
        this.widgetId = widgetId;
    }

    public void setShowFromManageItemsPage(boolean showFromManageItemsPage) {
        this.showFromManageItemsPage = showFromManageItemsPage;
    }

    private Integer widgetId;
    private boolean showFromManageItemsPage;

    private WidgetTitle widgetTitle;
    private List<Site> availableSites;
    private Integer selectedSiteId;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();

}
