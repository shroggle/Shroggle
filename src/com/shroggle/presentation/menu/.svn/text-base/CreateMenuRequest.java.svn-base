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

import com.shroggle.entity.MenuStructureType;
import com.shroggle.entity.MenuStyleType;
import org.directwebremoting.annotations.DataTransferObject;

import java.util.List;

@DataTransferObject
public class CreateMenuRequest {

    private Integer widgetId;

    private String name;

    private int menuId;

    private MenuStyleType menuStyleType;

    private boolean includePageTitle;

    private MenuStructureType menuStructure;

    private List<ItemIdIncludeInMenu> includeInMenus;

    public List<ItemIdIncludeInMenu> getIncludeInMenus() {
        return includeInMenus;
    }

    public void setIncludeInMenus(List<ItemIdIncludeInMenu> includeInMenus) {
        this.includeInMenus = includeInMenus;
    }

    public MenuStructureType getMenuStructure() {
        return menuStructure;
    }

    public void setMenuStructure(MenuStructureType menuStructure) {
        this.menuStructure = menuStructure;
    }

    public boolean isIncludePageTitle() {
        return includePageTitle;
    }

    public void setIncludePageTitle(boolean includePageTitle) {
        this.includePageTitle = includePageTitle;
    }

    public MenuStyleType getMenuStyleType() {
        return menuStyleType;
    }

    public void setMenuStyleType(MenuStyleType menuStyleType) {
        this.menuStyleType = menuStyleType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public void setWidgetId(Integer widgetId) {
        this.widgetId = widgetId;
    }

    public Integer getWidgetId() {
        return widgetId;
    }
}