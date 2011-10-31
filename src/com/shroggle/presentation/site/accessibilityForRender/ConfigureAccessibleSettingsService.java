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
package com.shroggle.presentation.site.accessibilityForRender;

import com.shroggle.entity.AccessibleElementType;
import com.shroggle.entity.AccessibleForRender;
import com.shroggle.entity.Site;
import com.shroggle.entity.Widget;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.pageversion.PageTitle;
import com.shroggle.logic.site.page.pageversion.PageTitleGetter;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.widget.WidgetTitle;
import com.shroggle.logic.widget.WidgetTitleGetter;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.presentation.site.WithPageVersionTitle;
import com.shroggle.presentation.site.WithWidgetTitle;
import com.shroggle.util.ServiceLocator;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class ConfigureAccessibleSettingsService extends ServiceWithExecutePage implements WithPageVersionTitle, WithWidgetTitle {

    @RemoteMethod
    public String execute(final int accessibleId, final AccessibleElementType accessibleType,
                          final boolean showAsSeparate) throws Exception {
        new UsersManager().getLogined();

        accessibleForRender = ServiceLocator.getPersistance().getAccessibleElement(accessibleId, accessibleType);
        this.showAsSeparate = showAsSeparate;

        switch (accessibleType) {
            case SITE: {
                siteTitle = ((Site) accessibleForRender).getTitle();
                break;
            }
            case PAGE: {
                pageTitle = new PageTitleGetter(((PageManager) accessibleForRender));
                showForPage = true;
                break;
            }
            case WIDGET: {
                final WidgetManager widgetManager = (WidgetManager) accessibleForRender;
                savedInCurrentPlace = widgetManager.isAccessibleSettingsSavedInCurrentPlace();
                widgetTitle = new WidgetTitleGetter(widgetManager.getWidget());
                widget = widgetManager.getWidget();
                break;
            }
            case ITEM: {
                savedInCurrentPlace = true;
                break;
            }
        }
        getContext().getHttpServletRequest().setAttribute("accessibleService", this);

        if (showAsSeparate) {
            return getContext().forwardToString("/site/accessibilityForRender/elementAccessibility.jsp");
        } else {
            return "";
        }
    }

    public String getSiteTitle() {
        return siteTitle;
    }

    public PageTitle getPageTitle() {
        return pageTitle;
    }

    public WidgetTitle getWidgetTitle() {
        return widgetTitle;
    }

    public Widget getWidget() {
        return widget;
    }

    public AccessibleForRender getAccessibleForRender() {
        return accessibleForRender;
    }

    public int getSiteId() {
        return accessibleForRender.getSiteId();
    }

    public boolean isSavedInCurrentPlace() {
        return savedInCurrentPlace;
    }

    public boolean isShowForPage() {
        return showForPage;
    }

    public boolean isShowAsSeparate() {
        return showAsSeparate;
    }

    private boolean showAsSeparate;
    private boolean showForPage;
    private boolean savedInCurrentPlace;
    private String siteTitle;
    private PageTitle pageTitle;
    private WidgetTitle widgetTitle;
    private Widget widget;
    private AccessibleForRender accessibleForRender;

}