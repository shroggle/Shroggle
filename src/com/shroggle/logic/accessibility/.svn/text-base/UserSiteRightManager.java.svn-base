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
package com.shroggle.logic.accessibility;

import com.shroggle.entity.*;
import com.shroggle.exception.*;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

/**
 * @author Artem Stasuk
 * @see UserRightManager
 */
public class UserSiteRightManager {

    public UserSiteRightManager(final UserRightManager userRightManager) {
        this.userRightManager = userRightManager;
    }

    public Widget getWidgetForEditInPresentationalService(final Integer id) {
        try {
            return getWidgetForEdit(id);
        } catch (Exception ex) {
            // If widget not found or logined user has no rights to it - refreshing page (it`s default handler in script
            // for WidgetNotFoundException). So if user not logined - he`ll see login window, if this widget has been
            // removed - refreshed page will not contains it and if user now has no rights to site - he`ll be redirected
            // to dashboard. Tolik.
            throw new WidgetNotFoundException();
        }
    }

    public WidgetItem getWidgetItemForEditInPresentationalService(final Integer id) {
        final Widget widget = getWidgetForEditInPresentationalService(id);
        return (widget.isWidgetItem()) ? (WidgetItem) widget : null;
    }

    public Widget getWidgetForEdit(final Integer id) {
        if (id != null) {
            final Widget widget = persistance.getWidget(id);
            if (widget != null) {
                final Site site = widget.getSite();
                final UserOnSiteRight userOnSiteRight = userRightManager.toSite(site);
                if (userOnSiteRight != null &&
                        (userOnSiteRight.getSiteAccessType() == SiteAccessLevel.ADMINISTRATOR
                                || userOnSiteRight.getSiteAccessType() == SiteAccessLevel.EDITOR)) {
                    return widget;
                }
                throw new WidgetRightsNotFoundException("You have no rights to selected widget (widgetId = " + id + ").");
            }
        }
        throw new WidgetNotFoundException("Can't find widget " + id);
    }

    public WidgetItem getWidgetItemForEdit(final Integer id) {
        final Widget widget = getWidgetForEdit(id);
        return (widget.isWidgetItem()) ? (WidgetItem) widget : null;
    }

    public Widget getWidgetForView(final Integer id) {
        if (id != null) {
            final Widget widget = persistance.getWidget(id);
            if (widget != null) {
                final Site site = widget.getSite();
                final UserOnSiteRight userOnSiteRight = userRightManager.toSite(site);
                if (userOnSiteRight != null) {
                    return widget;
                }
            }
        }
        throw new WidgetNotFoundException("Can't find widget " + id);
    }

    public SiteManager getSiteForView(final Integer siteId) {
        if (siteId != null) {
            final Site site = persistance.getSite(siteId);
            final UserOnSiteRight userOnSiteRight = userRightManager.toSite(site);
            if (userOnSiteRight != null) {
                return new SiteManager(site);
            }
        }

        throw new SiteNotFoundException("Can't find site " + siteId);
    }

    public boolean hasRightToDelete(final Integer siteId) {
        final SiteManager site;
        try {
            site = getSiteForEdit(siteId);
        } catch (SiteNotFoundException ex) {
            return false;
        }

        return hasRightToDelete(site.getSite());
    }

    public boolean hasRightToDelete(final Site site) {
        final UserOnSiteRight userOnSiteRight = userRightManager.toSite(site);

        return userOnSiteRight != null && userOnSiteRight.getSiteAccessType() == SiteAccessLevel.ADMINISTRATOR;
    }

    public SiteManager getSiteForEdit(final Integer siteId) {
        if (siteId != null) {
            final Site site = persistance.getSite(siteId);
            final UserOnSiteRight userOnSiteRight = userRightManager.toSite(site);
            if (userOnSiteRight != null && (userOnSiteRight.getSiteAccessType() == SiteAccessLevel.ADMINISTRATOR
                    || userOnSiteRight.getSiteAccessType() == SiteAccessLevel.EDITOR)) {
                return new SiteManager(site);
            }
        }

        throw new SiteNotFoundException("Can't find site by id = " + siteId);
    }

    public PageManager getPageForEdit(final Integer pageId) {
        if (pageId != null) {
            final Page page = persistance.getPage(pageId);
            if (page != null) {
                final UserOnSiteRight userOnSiteRight = userRightManager.toSite(page.getSite());
                if (userOnSiteRight != null && (userOnSiteRight.getSiteAccessType() == SiteAccessLevel.ADMINISTRATOR
                        || userOnSiteRight.getSiteAccessType() == SiteAccessLevel.EDITOR)) {
                    return new PageManager(page);
                }
            }
        }

        throw new PageNotFoundException("Can't find page " + pageId);
    }

    private final UserRightManager userRightManager;
    private final Persistance persistance = ServiceLocator.getPersistance();

}