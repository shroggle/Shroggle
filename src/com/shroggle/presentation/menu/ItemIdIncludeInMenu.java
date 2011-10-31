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

import org.directwebremoting.annotations.DataTransferObject;

/**
 * @author Balakirev Anatoliy
 */
@DataTransferObject
public class ItemIdIncludeInMenu {

    public ItemIdIncludeInMenu() {
    }

    public ItemIdIncludeInMenu(int menuItemId, boolean includeInMenu) {
        this.menuItemId = menuItemId;
        this.includeInMenu = includeInMenu;
    }

    private int menuItemId;

    private boolean includeInMenu;

    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public boolean isIncludeInMenu() {
        return includeInMenu;
    }

    public void setIncludeInMenu(boolean includeInMenu) {
        this.includeInMenu = includeInMenu;
    }
}
