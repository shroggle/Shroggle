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

import com.shroggle.entity.*;
import com.shroggle.exception.AdminLoginNotFoundException;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.widget.WidgetTitle;
import com.shroggle.logic.widget.WidgetTitleGetter;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author Balakirev Anatoliy, dmitry.solomadin
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class ConfigureAdminLoginService extends ServiceWithExecutePage implements WithWidgetTitle {

    @SynchronizeByMethodParameter(
            entityClass = Widget.class)
    @RemoteMethod
    public void execute(final Integer widgetId, final Integer adminLoginId) throws Exception {
        final UserManager userManager = new UsersManager().getLogined();
        final UserRightManager userRightManager = userManager.getRight();

        if (widgetId == null && adminLoginId == null) {
            throw new AdminLoginNotFoundException("Both widgetId and adminLoginId cannot be null. " +
                    "This service is only for configuring existing admin logins.");
        }

        if (widgetId == null) {
            adminLogin = persistance.getDraftItem(adminLoginId);

            if (adminLogin == null) {
                throw new AdminLoginNotFoundException("Cannot find admin login by Id=" + adminLoginId);
            }

            site = persistance.getSite(adminLogin.getSiteId());
        } else {
            widget = (WidgetItem) userRightManager.getSiteRight().getWidgetForEditInPresentationalService(
                    widgetId);
            site = widget.getSite();

            widgetTitle = new WidgetTitleGetter(widget);

            if (widget.getDraftItem() != null) {
                adminLogin = (DraftAdminLogin) widget.getDraftItem();
            } else {
                throw new AdminLoginNotFoundException("Seems like widget with Id= " + widgetId + " got no item.");
            }
        }

        getContext().getHttpServletRequest().setAttribute("adminLoginService", this);
    }

    public WidgetTitle getWidgetTitle() {
        return widgetTitle;
    }

    public WidgetItem getWidget() {
        return widget;
    }

    public DraftAdminLogin getAdminLogin() {
        return adminLogin;
    }

    public Site getSite() {
        return site;
    }

    private WidgetTitle widgetTitle;
    private DraftAdminLogin adminLogin;
    private Site site;
    private WidgetItem widget;
    private Persistance persistance = ServiceLocator.getPersistance();

}