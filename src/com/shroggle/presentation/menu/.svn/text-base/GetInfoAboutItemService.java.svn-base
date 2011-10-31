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

import com.shroggle.entity.MenuItem;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.annotations.RemoteMethod;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.logic.menu.InfoAboutMenuItemResponse;
import com.shroggle.logic.menu.MenuItemsManager;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
//todo. Add tests. Tolik
@RemoteProxy
public class GetInfoAboutItemService extends AbstractService {

    @RemoteMethod
    public String execute(final Integer menuItemId) throws IOException, ServletException {
        final MenuItem menuItem = persistance.getDraftMenuItem(menuItemId);
        if (menuItem == null) {
            Logger.getLogger(this.getClass().getName()).warning("Can`t find menuItem by id = " + menuItemId);
            return "";
        }
        final InfoAboutMenuItemResponse infoAboutMenuItem = MenuItemsManager.getInfoAboutMenuItems(menuItem);
        getContext().getHttpServletRequest().setAttribute("menuItem", infoAboutMenuItem.getSelectedMenuItem());
        getContext().getHttpServletRequest().setAttribute("pages", infoAboutMenuItem.getPages());

        return getContext().forwardToString("/menu/menuItemDetails.jsp");
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
}
