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

import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
public class MenuItemsManager {

    public static InfoAboutMenuItemResponse getInfoAboutMenuItems(final MenuItem menuItem) {
        final InfoAboutMenuItemResponse response = new InfoAboutMenuItemResponse();
        response.setSelectedMenuItem(menuItem);
        final Site site = ServiceLocator.getPersistance().getSite(menuItem.getMenu().getSiteId());
        final DraftMenu defaultMenu = site.getMenu();
        response.setPages(selectDefaultPages(defaultMenu.getMenuItems(), site.getPages()));
        return response;
    }

    public static void restoreDefaultStructure(final DraftMenu menuWithDefaultStructure, final DraftMenu changedMenu) {
        final List<MenuItem> defaultItems = getAllDefaultItemsInOneList(menuWithDefaultStructure.getMenuItems());
        final List<MenuItem> defaultItemsFromChangedMenu = getAllDefaultItemsInOneList(changedMenu.getMenuItems());

        final Persistance persistance = ServiceLocator.getPersistance();
        // Trying to find default item in changed menu. If item not found - restore in by copying default item and move
        // restored item to root level for now
        for (MenuItem defaultMenuItem : defaultItems) {
            MenuItem foundedItem = selectFromChangedItemsItemByPageId(defaultItemsFromChangedMenu, defaultMenuItem.getPageId());
            if (foundedItem == null) {// Restoring it by copying default.
                foundedItem = createDraftCopy(defaultMenuItem);
                foundedItem.setMenu(changedMenu);
                foundedItem.setParent(null);
                persistance.putMenuItem(foundedItem);
            }
        }
        // Making sure that default items was restored correctly.
        final List<MenuItem> retoredDefaultItemsFromChangedMenu = getAllDefaultItemsInOneList(changedMenu.getMenuItems());
        if (retoredDefaultItemsFromChangedMenu.size() != defaultItems.size()) {
            Logger.getLogger(MenuItemsManager.class.getName()).severe("Default items was not restored correctly. " +
                    "Unable to restore default structure for menu with id = " + changedMenu.getId());
            return;
        }
        // Now we have all default items and can move them to appropriate parents.
        for (MenuItem defaultMenuItem : defaultItems) {
            final MenuItem foundedItem = selectFromChangedItemsItemByPageId(retoredDefaultItemsFromChangedMenu, defaultMenuItem.getPageId());
            final MenuItem appropriateParent;
            if (defaultMenuItem.getParent() != null) {
                appropriateParent = selectFromChangedItemsItemByPageId(retoredDefaultItemsFromChangedMenu, defaultMenuItem.getParent().getPageId());
            } else {
                appropriateParent = null;
            }
            foundedItem.setParent(appropriateParent);
        }
        // And now we have all default items connected to their appropriate parents and we can move them to appropriate positions.
        for (MenuItem defaultMenuItem : defaultItems) {
            final MenuItem foundedItem = selectFromChangedItemsItemByPageId(retoredDefaultItemsFromChangedMenu, defaultMenuItem.getPageId());
            foundedItem.moveToPosition(defaultMenuItem.getPosition());
        }
        // We must set menuStructure to default.
        changedMenu.setMenuStructure(MenuStructureType.DEFAULT);
    }

    public static MenuItem getItem(final Menu menu, final Integer pageId) {
        if (menu == null || pageId == null) {
            return null;
        }

        final Persistance persistance = ServiceLocator.getPersistance();
        final List<DraftMenuItem> menuItems = persistance.getMenuItems(pageId);
        for (final MenuItem menuItem : menuItems) {
            if (menuItem.getMenu() == menu) {
                return menuItem;
            }
        }

        return null;
    }

    /**
     * @param pageId - pageId
     *               Find all MenuItem`s with needed pageId, remove them and move their children to its places.
     */
    public static void removeItemsAndMoveChildren(final int pageId) {
        final Persistance persistance = ServiceLocator.getPersistance();
        final List<DraftMenuItem> menuItems = persistance.getMenuItems(pageId);// Execution time for this block ~ 115ms. Tolik (for indie roar site, on my pc)
        for (MenuItem menuItem : menuItems) {// Execution time for this block ~ 195ms. Tolik (for indie roar site, on my pc)
            for (MenuItem child : new ArrayList<MenuItem>(menuItem.getChildren())) {
                child.setParent(menuItem.getParent());
            }
            persistance.removeMenuItem(menuItem);
        }
    }

    /**
     * @param items - List<NewMenuItem> for normalization.
     *              Normalize pozition of element from given list.
     *              For exaple:
     *              If before normalization items contains elements with position: 2, 5, 7 -
     *              after normalization it will be changed to 0, 1, 2 accordingly.
     */
    public static void normalizePositions(final List<? extends MenuItem> items) {
        final List<MenuItem> modifiableItems = new ArrayList<MenuItem>(items);
        for (int i = 0; i < modifiableItems.size(); i++) {
            modifiableItems.get(i).setPosition(i);
        }
    }

    /**
     * @param siteId               - Id of site in which we change menus
     * @param pageIdFromMovedChild - pageId from moved MenuItem with DEFAULT type.
     * @param pageIdFromNewParent  - pageId from needed MenuItem parent with DEFAULT type.
     * @param position             - desired position
     *                             This method get all menus with DEFAULT structure for current site,
     *                             find in them child with type = DEFAULT and pageId = pageIdFromMovedChild
     *                             and move it to needed parent (element from this menu with type = DEFAULT and
     *                             pageId = pageIdFromNewParent or root level) and to needed position.
     */
    public static void moveItemsToNeededPosition(final int siteId, final int pageIdFromMovedChild,
                                                 final Integer pageIdFromNewParent, final int position) {
        final Persistance persistance = ServiceLocator.getPersistance();
        final List<DraftMenu> menus = persistance.getMenusWithDefaultStructureBySiteId(siteId);// Execution time for this block ~ 35ms. Tolik (for indie roar site, on my pc)
        for (DraftMenu menu : menus) {// Execution time for this block ~ 161ms. Tolik (for indie roar site, on my pc)
            final MenuItem child = selectItemWithDefaultTypeAndNeededPageIdFromChildren(menu.getMenuItems(), pageIdFromMovedChild);
            if (child == null) {
                Logger.getLogger(MenuItemsManager.class.getName()).warning("Can`t find MenuItem with default type and " +
                        "pageId = " + pageIdFromMovedChild + " in menu with id = " + menu.getId());
                continue;
            }
            final MenuItem parent = selectItemWithDefaultTypeAndNeededPageIdFromChildren(menu.getMenuItems(), pageIdFromNewParent);
            child.setParent(parent);
            child.moveToPosition(position);
        }
    }

    /**
     * @param siteId        - Id of site in which we change menus
     * @param pageId        - pageId for newly created MenuItem
     * @param includeInMenu - parameter includeInMenu for newly created MenuItem
     *                      This method get all menus for current site, and add into them MenuItem
     *                      with DEFAULT type, selected pageId and includeInMenu.
     */
    public static void addMenuItemsToAllMenusBySiteId(final int siteId, final int pageId, final boolean includeInMenu) {
        final Persistance persistance = ServiceLocator.getPersistance();
        final List<DraftMenu> menus = persistance.getMenusBySiteId(siteId);
        for (DraftMenu menu : menus) {
            final MenuItem menuItem = new DraftMenuItem(pageId, includeInMenu, menu);
            menuItem.setParent(null);
            persistance.putMenuItem(menuItem);
        }
    }

    public static void includeMenuItemsInMenu(final boolean include, final int pageId) {
        final Persistance persistance = ServiceLocator.getPersistance();
        final List<DraftMenuItem> menuItems = persistance.getMenuItems(pageId);
        for (DraftMenuItem menuItem : menuItems) {
            menuItem.setIncludeInMenu(include);
        }
    }

    /**
     * @param items - menu items
     * @return List<Integer> - pageIds from items there includeInMenu == true
     */
    public static List<Integer> getIncludedPageIds(final List<com.shroggle.entity.MenuItem> items) {
        final List<Integer> pageIds = new ArrayList<Integer>();
        for (final com.shroggle.entity.MenuItem item : items) {
            if (item.isIncludeInMenu()) {
                pageIds.add(item.getPageId());
            }
            if (!item.getChildren().isEmpty()) {
                pageIds.addAll(getIncludedPageIds(item.getChildren()));
            }
        }
        return pageIds;
    }

    /**
     * @param items - menu items
     * @return List<Integer> - menuItemIds from items there includeInMenu == true
     */
    public static List<Integer> getIncludedMenuItemIds(final List<com.shroggle.entity.MenuItem> items) {
        final List<Integer> itemIds = new ArrayList<Integer>();
        for (final com.shroggle.entity.MenuItem item : items) {
            if (item.isIncludeInMenu()) {
                itemIds.add(item.getId());
            }
            if (!item.getChildren().isEmpty()) {
                itemIds.addAll(getIncludedMenuItemIds(item.getChildren()));
            }
        }
        return itemIds;
    }

    /**
     * @param menu     - new menu without children
     * @param oldItems - copied children
     *                 This method make copy of oldItems, put it in database and add this copied items to menu structure.
     */
    public static void copyItemsAndAddThemToMenu(final Menu menu, final List<MenuItem> oldItems) {
        copyItemsAndAddThemToMenu(menu, oldItems, null);
    }


    public static WorkMenuItem createWorkCopy(final MenuItem menuItem) {
        return copyMenuItem(menuItem, new WorkMenuItem());
    }

    public static DraftMenuItem createDraftCopy(final MenuItem menuItem) {
        return copyMenuItem(menuItem, new DraftMenuItem());
    }

    /*------------------------------------------------Private methods-------------------------------------------------*/

    private static <T extends MenuItem> T copyMenuItem(final MenuItem original, final T copy) {
        copy.setCustomUrl(original.getCustomUrl());
        copy.setName(original.getName());
        copy.setTitle(original.getTitle());
        copy.setPageId(original.getPageId());
        copy.setUrlType(original.getUrlType());
        copy.setImageId(original.getImageId());
        copy.setIncludeInMenu(original.isIncludeInMenu());
        copy.setPosition(original.getPosition());
        copy.setDefaultPageId(original.getDefaultPageId());
        return copy;
    }

    protected static MenuItem selectItemWithDefaultTypeAndNeededPageIdFromChildren(final List<MenuItem> items, final Integer pageId) {
        if (pageId == null) {
            return null;
        }
        for (final MenuItem item : items) {
            if (item.getDefaultPageId() != null && item.getDefaultPageId() == pageId.intValue()) {
                return item;
            }
            final MenuItem neededItem = selectItemWithDefaultTypeAndNeededPageIdFromChildren(item.getChildren(), pageId);
            if (neededItem != null) {
                return neededItem;
            }
        }
        return null;
    }

    private static void copyItemsAndAddThemToMenu(final Menu menu, final List<MenuItem> oldItems, final MenuItem parent) {
        final Persistance persistance = ServiceLocator.getPersistance();
        for (final MenuItem item : oldItems) {
            final MenuItem newItem = (menu instanceof WorkMenu) ? createWorkCopy(item) : createDraftCopy(item);
            newItem.setPageId(item.getPageId());
            newItem.setMenu(menu);
            newItem.setParent(parent);
            persistance.putMenuItem(newItem);
            copyItemsAndAddThemToMenu(menu, item.getChildren(), newItem);
        }
    }

    private static List<MenuItem> getAllDefaultItemsInOneList(final List<MenuItem> oldItems) {
        final List<MenuItem> newItems = new ArrayList<MenuItem>();
        for (MenuItem child : oldItems) {
            if (child.getDefaultPageId() != null) {
                newItems.add(child);
            }
            if (!child.getChildren().isEmpty()) {
                newItems.addAll(getAllDefaultItemsInOneList(child.getChildren()));
            }
        }
        return newItems;
    }

    private static MenuItem selectFromChangedItemsItemByPageId(final List<MenuItem> items, final int pageId) {
        for (MenuItem item : items) {
            if (item.getPageId() != null && item.getPageId().intValue() == pageId) {
                return item;
            }
        }
        return null;
    }

    // todo. This method is very slow. Tolik.

    private static List<Page> selectDefaultPages(List<com.shroggle.entity.MenuItem> items, final List<Page> pages) {
        final List<Page> includedPages = new ArrayList<Page>();
        for (com.shroggle.entity.MenuItem item : items) {
            includedPages.add(selectPageById(pages, item.getPageId()));
            if (!item.getChildren().isEmpty()) {
                includedPages.addAll(selectDefaultPages(item.getChildren(), pages));
            }
        }
        return includedPages;
    }

    private static Page selectPageById(final List<Page> pages, final Integer pageId) {
        if (pageId == null) {
            return null;
        }
        for (Page page : pages) {
            if (page.getPageId() == pageId.intValue()) {
                return page;
            }
        }
        return null;
    }
    /*------------------------------------------------Private methods-------------------------------------------------*/
}
