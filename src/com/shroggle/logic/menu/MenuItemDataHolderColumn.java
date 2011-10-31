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

import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
public class MenuItemDataHolderColumn {

    public MenuItemDataHolderColumn(List<MenuItemData> children) {
        this.children = children;
    }

    private final List<MenuItemData> children;

    public List<MenuItemData> getChildren() {
        return children;
    }
}
