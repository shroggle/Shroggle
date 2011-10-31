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

package com.shroggle.entity;

import com.shroggle.logic.menu.MenuItemsManager;
import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.annotations.CollectionOfElements;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

@DataTransferObject
@Entity(name = "workMenus")
public class WorkMenu extends WorkItem implements Menu {

    public WorkMenu() {
        menuStyleType = MenuStyleType.DROP_DOWN_STYLE_HORIZONTAL;
        menuStructure = MenuStructureType.DEFAULT;
        menuItems = new ArrayList<WorkMenuItem>();
        setId(-1);
    }

    private boolean includePageTitle;

    private boolean defaultSiteMenu;

    @Enumerated(value = EnumType.STRING)
    private MenuStyleType menuStyleType;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 10, nullable = false)
    private MenuStructureType menuStructure;

    @CollectionOfElements
    private List<WorkMenuItem> menuItems;

    public List<MenuItem> getMenuItems() {
        Collections.sort(menuItems, new Comparator<WorkMenuItem>() {
            public int compare(WorkMenuItem o1, WorkMenuItem o2) {
                return Integer.valueOf(o1.getPosition()).compareTo(o2.getPosition());
            }
        });
        return Collections.unmodifiableList(new ArrayList<MenuItem>(menuItems));
    }

    @Deprecated
    public void setMenuItems(List<WorkMenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public void addChild(MenuItem child) {
        if (child instanceof WorkMenuItem) {
            MenuItemsManager.normalizePositions(getMenuItems());
            child.setPosition(getMenuItems().size());
            menuItems.add((WorkMenuItem) child);
        } else {
            logger.warning("Unable to add not WorkMenuItem to WorkMenuItem`s children.");
        }
    }

    public void removeChild(MenuItem menuItem) {
        if (menuItem instanceof WorkMenuItem) {
            menuItems.remove((WorkMenuItem) menuItem);
            MenuItemsManager.normalizePositions(getMenuItems());
        } else {
            logger.warning("Unable to remove not WorkMenuItem from WorkMenuItem`s children.");
        }
    }

    public boolean isDefaultSiteMenu() {
        return defaultSiteMenu;
    }

    public void setDefaultSiteMenu(boolean defaultSiteMenu) {
        this.defaultSiteMenu = defaultSiteMenu;
    }

    @Override
    public MenuStructureType getMenuStructure() {
        return menuStructure;
    }

    public void setMenuStructure(MenuStructureType menuStructure) {
        this.menuStructure = menuStructure;
    }

    @Override
    public boolean isIncludePageTitle() {
        return includePageTitle;
    }

    public void setIncludePageTitle(boolean includePageTitle) {
        this.includePageTitle = includePageTitle;
    }

    @Override
    public MenuStyleType getMenuStyleType() {
        return menuStyleType;
    }

    public void setMenuStyleType(MenuStyleType menuStyleType) {
        this.menuStyleType = menuStyleType;
    }

    public ItemType getItemType() {
        return ItemType.MENU;
    }

    @Transient
    protected final Logger logger = Logger.getLogger(this.getClass().getName());
}