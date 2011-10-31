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
@Entity(name = "menus")
public class DraftMenu extends DraftItem implements Menu {

    public DraftMenu() {
        menuStyleType = MenuStyleType.DROP_DOWN_STYLE_HORIZONTAL;
        menuStructure = MenuStructureType.DEFAULT;
        menuItems = new ArrayList<DraftMenuItem>();
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
    private List<DraftMenuItem> menuItems;

    public boolean isDefaultSiteMenu() {
        return defaultSiteMenu;
    }

    public void setDefaultSiteMenu(boolean defaultSiteMenu) {
        this.defaultSiteMenu = defaultSiteMenu;
    }

    public List<MenuItem> getMenuItems() {
        Collections.sort(menuItems, new Comparator<DraftMenuItem>() {
            public int compare(DraftMenuItem o1, DraftMenuItem o2) {
                return Integer.valueOf(o1.getPosition()).compareTo(o2.getPosition());
            }
        });
        return Collections.unmodifiableList(new ArrayList<MenuItem>(menuItems));
    }

    @Deprecated
    public void setMenuItems(List<DraftMenuItem> menuItems) {
        this.menuItems = menuItems;
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

    public ItemType getItemType() {
        return ItemType.MENU;
    }

    public void addChild(MenuItem child) {
        if (child instanceof DraftMenuItem) {
            MenuItemsManager.normalizePositions(getMenuItems());
            child.setPosition(getMenuItems().size());
            menuItems.add((DraftMenuItem) child);
        } else {
            logger.warning("Unable to add not DraftMenuItem to DraftMenuItem`s children.");
        }
    }

    public void removeChild(MenuItem menuItem) {
        if (menuItem instanceof DraftMenuItem) {
            menuItems.remove((DraftMenuItem) menuItem);
            MenuItemsManager.normalizePositions(getMenuItems());
        } else {
            logger.warning("Unable to remove not DraftMenuItem from DraftMenuItem`s children.");
        }
    }

    @Transient
    protected final Logger logger = Logger.getLogger(this.getClass().getName());
}