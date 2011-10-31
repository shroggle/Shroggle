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
package com.shroggle.presentation.menu;

import com.shroggle.entity.DraftMenu;
import com.shroggle.entity.Widget;
import com.shroggle.exception.MenuNotFoundException;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.FunctionalWidgetInfo;
import com.shroggle.presentation.site.GetFunctionalWidgetsService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class RemoveSelectedMenuService extends AbstractService {

    @RemoteMethod
    @SynchronizeByMethodParameter(
            entityClass = DraftMenu.class)
    public FunctionalWidgetInfo execute(final int menuId, final int widgetId) throws Exception {
        new UsersManager().getLogined();
        final Persistance persistance = ServiceLocator.getPersistance();

        final DraftMenu menu = persistance.getMenuById(menuId);
        if (menu == null) {
            throw new MenuNotFoundException("Cant find menu by id = " + menuId);
        }

        final Widget widget = persistance.getWidget(widgetId);

        ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
            public void run() {
                persistance.removeDraftItem(menu);
            }
        });

        return new GetFunctionalWidgetsService().createFunctionalWidgetInfo(widget, "widget", true);
    }
}
