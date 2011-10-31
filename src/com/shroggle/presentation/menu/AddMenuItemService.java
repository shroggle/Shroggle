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

import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.annotations.RemoteMethod;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransactionContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.MenuPagesHtmlTextCreator;
import com.shroggle.logic.menu.MenuItemsManager;
import com.shroggle.logic.menu.MenuPageCheckSometimes;

import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class AddMenuItemService extends AbstractService {

    @RemoteMethod
    public UpdateMenuItemResponse execute(final Integer parentMenuItemId, final Integer menuId) throws Exception {
        final UpdateMenuItemResponse response = new UpdateMenuItemResponse();
        final Persistance persistance = ServiceLocator.getPersistance();
        final DraftMenu menu = persistance.getMenuById(menuId);
        if (menu == null) {
            Logger.getLogger(this.getClass().getName()).warning("Can`t find menu by id = " + menuId + "!" +
                    " New MenuItem can`t be created without menu.");
            return response;
        }
        final Site site = persistance.getSite(menu.getSiteId());
        if (site == null || site.getMenu() == null) {
            Logger.getLogger(this.getClass().getName()).warning("Can`t find site by id = " + menu.getSiteId() + "!" +
                    " New MenuItem can`t be created without default site menu.");
            return response;
        }
        if (site.getMenu().getMenuItems().isEmpty()) {
            Logger.getLogger(this.getClass().getName()).warning("Can`t create new NewMenuItem without menu items in " +
                    "default site menu!");
            return response;
        }
        final MenuItem menuItem = ServiceLocator.getPersistanceTransaction().execute(new PersistanceTransactionContext<MenuItem>() {
            public MenuItem execute() {
                final MenuItem menuItem = MenuItemsManager.createDraftCopy(site.getMenu().getMenuItems().get(0));
                // We should set menu structure to CUSTOM here, by setting its defaultPageId to null
                menuItem.setDefaultPageId(null);
                menuItem.setMenu(menu);
                menuItem.setParent(persistance.getDraftMenuItem(parentMenuItemId));
                menuItem.setIncludeInMenu(true);
                menuItem.setName("Default menu item");
                menuItem.setTitle("Default menu item");
                persistance.putMenuItem(menuItem);
                menu.setMenuStructure(MenuStructureType.OWN);
                return menuItem;
            }
        });
        final MenuPagesHtmlTextCreator pagesHtmlTextCreator = new MenuPagesHtmlTextCreator(menu, new MenuPageCheckSometimes(menu), SiteShowOption.getDraftOption());
        response.setTreeHtml(pagesHtmlTextCreator.getHtml());
        if (menuItem != null) {
            final int selectedMenuItemId = menuItem.getId();
            response.setInfoAboutSelectedItem(new GetInfoAboutItemService().execute(selectedMenuItemId));
            response.setSelectedMenuItemId(selectedMenuItemId);
        }
        return response;
    }
}
