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

import com.shroggle.entity.Menu;
import com.shroggle.entity.MenuItem;

import java.util.List;

/**
 * @author Artem Stasuk
 */
public class MenuPageCheckSometimes implements MenuPageCheck {

    public MenuPageCheckSometimes(final Menu menu) {
        includedItemsIds = MenuItemsManager.getIncludedMenuItemIds(menu.getMenuItems());
    }

    public boolean isIncluded(final MenuItem menuItem) {
        return includedItemsIds.contains(menuItem.getId());
    }

    private final List<Integer> includedItemsIds;

}
