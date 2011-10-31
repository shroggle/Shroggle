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
import com.shroggle.entity.SiteShowOption;
import com.shroggle.presentation.AbstractService;
import com.shroggle.entity.Site;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.ServiceLocator;
import com.shroggle.logic.site.page.MenuPagesHtmlTextCreator;
import com.shroggle.logic.menu.MenuPageCheckSometimes;
import com.shroggle.logic.menu.MenuItemsManager;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.annotations.RemoteMethod;

import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class RestoreDefaultStructureService extends AbstractService {

    @RemoteMethod
    public UpdateMenuItemResponse execute(final Integer menuId) throws Exception {
        final UpdateMenuItemResponse response = new UpdateMenuItemResponse();
        final DraftMenu menu = persistance.getMenuById(menuId);
        if (menu == null) {
            logger.warning("Can`t create default structure for menu with id = " + menuId + ". Menu not found.");
            return response;
        }
        final Site site = persistance.getSite(menu.getSiteId());
        if (site == null) {
            logger.warning("Can`t create default structure for menu with id = " + menuId + ". Site not found. Site id " +
                    "in this menu = " + menu.getSiteId());
            return response;
        }
        final DraftMenu defaultMenu = site.getMenu();
        if (defaultMenu == null) {
            logger.warning("Can`t create default structure for menu with id = " + menuId + ". Default menu not found" +
                    " for site with id = " + menu.getSiteId());
            return response;
        }
        ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
            public void run() {
                MenuItemsManager.restoreDefaultStructure(defaultMenu, menu);
            }
        });
        final MenuPagesHtmlTextCreator pagesHtmlTextCreator = new MenuPagesHtmlTextCreator(menu, new MenuPageCheckSometimes(menu), SiteShowOption.getDraftOption());
        response.setTreeHtml(pagesHtmlTextCreator.getHtml());
        if (!menu.getMenuItems().isEmpty()) {
            final int selectedMenuItemId = menu.getMenuItems().get(0).getId();
            response.setInfoAboutSelectedItem(new GetInfoAboutItemService().execute(selectedMenuItemId));
            response.setSelectedMenuItemId(selectedMenuItemId);
        }
        return response;
    }


    private final Persistance persistance = ServiceLocator.getPersistance();
    private final Logger logger = Logger.getLogger(this.getClass().getName());
}
