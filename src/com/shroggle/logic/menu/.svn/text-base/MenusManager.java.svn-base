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
package com.shroggle.logic.menu;

import com.shroggle.entity.DraftMenu;
import com.shroggle.entity.ItemType;
import com.shroggle.entity.MenuItem;
import com.shroggle.logic.SiteItemsManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.PersistanceTransactionContext;

import java.util.List;

/**
 * @author dmitry.solomadin
 */
public class MenusManager {

    public DraftMenu createDefaultMenu(final DraftMenu defaultSiteMenu){
        return ServiceLocator.getPersistanceTransaction().execute(new PersistanceTransactionContext<DraftMenu>() {

            @Override
            public DraftMenu execute() {
                DraftMenu menu = new DraftMenu();
                menu.setSiteId(defaultSiteMenu.getSiteId());
                menu.setName(SiteItemsManager.getNextDefaultName(ItemType.MENU, defaultSiteMenu.getSiteId()));
                MenuItemsManager.copyItemsAndAddThemToMenu(menu, defaultSiteMenu.getMenuItems());
                ServiceLocator.getPersistance().putMenu(menu);
                setIncludedInMenuToAllItems(menu.getMenuItems());
                return menu;
            }

        });
    }

    private void setIncludedInMenuToAllItems(final List<MenuItem> items) {
        for (MenuItem item : items) {
            item.setIncludeInMenu(true);
            if (!item.getChildren().isEmpty()) {
                setIncludedInMenuToAllItems(item.getChildren());
            }
        }
    } 

}
