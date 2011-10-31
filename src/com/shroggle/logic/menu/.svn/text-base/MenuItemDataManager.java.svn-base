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
import com.shroggle.entity.SiteShowOption;

import java.util.*;

/**
 * @author Balakirev Anatoliy
 */
public class MenuItemDataManager {

    public MenuItemDataManager(final Menu menu, final SiteShowOption siteShowOption, final int currentPageId) {
        this.siteShowOption = siteShowOption;
        this.currentPageId = currentPageId;
        this.rootElementsData = createMenuItemDataForIncludedInMenuItems(menu.getMenuItems());
        this.parentsWithChildrenInColumns = createMenuItemDataBySelectedMenuStyleType(menu.getMenuStyleType(), new ArrayList<MenuItemData>(this.rootElementsData));
    }

    public List<MenuItemDataHolder> getParentsWithChildrenInColumns() {
        return parentsWithChildrenInColumns;
    }

    public List<MenuItemData> getRootElementsData() {
        return rootElementsData;
    }

    /*------------------------------------------------Private methods-------------------------------------------------*/

    private List<MenuItemDataHolder> createMenuItemDataBySelectedMenuStyleType(
            MenuStyleType menuStyleType, final List<MenuItemData> rootElements) {
        switch (menuStyleType) {
            case DROP_DOWN_STYLE_HORIZONTAL:
            case DROP_DOWN_STYLE_VERTICAL:
            case FULL_HEIGHT_STYLE_HORIZONTAL:
            case FULL_HEIGHT_STYLE_VERTICAL: {
                return getRootParentsWithAllTheirChildren(rootElements);
            }
            case TREE_STYLE_HORIZONTAL:
            case TREE_STYLE_VERTICAL: {
                return getAllParentsWithTheirChildren(rootElements);
            }
            case TABBED_STYLE_HORIZONTAL: {
                return createStructureForTabbedMenu(rootElements);
            }
            default: {
                throw new UnsupportedOperationException("Unknown MenuStyleType: " + menuStyleType);
            }
        }
    }

    /*---------------------------------Create MenuItemData For Included In Menu Items---------------------------------*/
    /**
     * This method get List<NewMenuItem> from menu (menu structure) and create structure of MenuItemData like in menu
     * but with small difference: if menuItem not included in menu (includeInMenu == false) we exclude it from new
     * structure and trying to move his children (also only included in menu) on his place.
     *
     * @param items - List<NewMenuItem> all menu items
     * @return - List<MenuItemData> created by included in menu items
     */
    protected/*for tets*/ List<MenuItemData> createMenuItemDataForIncludedInMenuItems(final List<MenuItem> items) {
        final List<MenuItemData> menuItemDatas = createMenuItemDataForIncludedItemsInternal(items, 0);
        addColumnIndexes(menuItemDatas);
        return menuItemDatas;
    }

    private List<MenuItemData> createMenuItemDataForIncludedItemsInternal(final List<MenuItem> items, final int level) {
        final List<MenuItemData> data = new ArrayList<MenuItemData>();
        for (final MenuItem item : items) {
            if (item.isIncludeInMenu()) {
                final MenuItemData info = new MenuItemManager(item, siteShowOption).createMenuItemData(currentPageId, level);
                if (!item.getChildren().isEmpty()) {
                    info.setChildren(createMenuItemDataForIncludedItemsInternal(item.getChildren(), (level + 1)));
                }
                data.add(info);
            } else if (!item.getChildren().isEmpty()) {
                data.addAll(createMenuItemDataForIncludedItemsInternal(item.getChildren(), (level + 1)));
            }
        }
        return data;
    }

    protected/*for tets*/ void addColumnIndexes(List<MenuItemData> menuItemDatas) {
        int columnIndex = 1;
        for (MenuItemData menuItemData : menuItemDatas) {
            menuItemData.setNumber(String.valueOf(columnIndex));
            addColumnIndexesForChildren(menuItemData.getChildren(), columnIndex, 1);
            columnIndex++;
        }
    }

    private int addColumnIndexesForChildren(List<MenuItemData> menuItemDatas, final int columnIndex, int indexInColumn) {
        for (MenuItemData menuItemData : menuItemDatas) {
            menuItemData.setNumber(columnIndex + "_" + indexInColumn);
            indexInColumn++;
            if (!menuItemData.getChildren().isEmpty()) {
                indexInColumn = addColumnIndexesForChildren(menuItemData.getChildren(), columnIndex, indexInColumn);
            }
        }
        return indexInColumn;
    }
    /*---------------------------------Create MenuItemData For Included In Menu Items---------------------------------*/

    /*---------------------------------------Create structure for tabbed menu-----------------------------------------*/

    private List<MenuItemDataHolder> createStructureForTabbedMenu(List<MenuItemData> menuItems) {
        final List<MenuItemDataHolder> dataHolders = new ArrayList<MenuItemDataHolder>();
        for (MenuItemData parent : menuItems) {
            if (hasOnlySecondLevelChildren(parent)) {

                final List<MenuItemData> children = new ArrayList<MenuItemData>();
                if (!parent.getImageUrl().isEmpty()) {
                    final MenuItemData firstDefaultMenuIremData = parent.clone();
                    firstDefaultMenuIremData.setName("");
                    firstDefaultMenuIremData.setDescription("");
                    firstDefaultMenuIremData.setShowImage(true);
                    children.add(firstDefaultMenuIremData);
                }

                children.addAll(getAllChildren(parent));

                final MenuItemDataHolderColumn column = new MenuItemDataHolderColumn(children);
                final MenuItemDataHolder holder = new MenuItemDataHolder(parent, Collections.singletonList(column));

                dataHolders.add(holder);
            } else {
                final List<MenuItemDataHolderColumn> columnsWithChildren = new ArrayList<MenuItemDataHolderColumn>();
                for (MenuItemData secondLevelChild : parent.getChildren()) {
                    secondLevelChild.setShowImage(true);

                    final List<MenuItemData> children = new ArrayList<MenuItemData>();
                    children.add(secondLevelChild);
                    children.addAll(getAllChildren(secondLevelChild));

                    final MenuItemDataHolderColumn column = new MenuItemDataHolderColumn(children);
                    columnsWithChildren.add(column);
                }
                final MenuItemDataHolder holder = new MenuItemDataHolder(parent, columnsWithChildren);
                dataHolders.add(holder);
            }
        }
        addIdentifierToLastRightElements(dataHolders);
        addIdentifierToLastBottomElements(dataHolders);
        return dataHolders;
    }

    private void addIdentifierToLastRightElements(final List<MenuItemDataHolder> dataHolders) {
        for (MenuItemDataHolder holder : dataHolders) {
            if (!holder.getColumnsWithChildren().isEmpty()) {
                final MenuItemDataHolderColumn lastRightColumn = holder.getColumnsWithChildren().get(holder.getColumnsWithChildren().size() - 1);
                for (MenuItemData menuItemData : lastRightColumn.getChildren()) {
                    menuItemData.setLastRight(true);
                }
            }
        }
    }

    private void addIdentifierToLastBottomElements(final List<MenuItemDataHolder> dataHolders) {
        for (MenuItemDataHolder holder : dataHolders) {
            for (MenuItemDataHolderColumn column : holder.getColumnsWithChildren()) {
                if (!column.getChildren().isEmpty()) {
                    final MenuItemData lastBottomElement = column.getChildren().get(column.getChildren().size() - 1);
                    lastBottomElement.setLastBottom(true);
                }
            }
        }
    }

    private boolean hasOnlySecondLevelChildren(final MenuItemData parent) {
        for (MenuItemData secondLevelChild : parent.getChildren()) {
            if (!secondLevelChild.getChildren().isEmpty()) {
                return false;
            }
        }
        return true;
    }
    /*---------------------------------------Create structure for tabbed menu-----------------------------------------*/


    /*-----------------------------------Get Root Parents With All Their Children-------------------------------------*/

    protected/*for tets*/ List<MenuItemDataHolder> getRootParentsWithAllTheirChildren(List<MenuItemData> menuItems) {
        // Root elements with all their children moved on second level
        final List<MenuItemDataHolder> dataHolders = new ArrayList<MenuItemDataHolder>();
        for (MenuItemData menuItemData : menuItems) {
            final MenuItemDataHolderColumn column = new MenuItemDataHolderColumn(getAllChildren(menuItemData));
            final MenuItemDataHolder holder = new MenuItemDataHolder(menuItemData, Collections.singletonList(column));
            dataHolders.add(holder);
        }
        return dataHolders;
    }

    private List<MenuItemData> getAllChildren(MenuItemData menuItemData) {
        final List<MenuItemData> children = new ArrayList<MenuItemData>();
        for (MenuItemData child : menuItemData.getChildren()) {
            children.add(child);
            if (!child.getChildren().isEmpty()) {
                children.addAll(getAllChildren(child));
            }
        }
        return children;
    }
    /*-----------------------------------Get Root Parents With All Their Children-------------------------------------*/


    /*--------------------------------------Get All Parents With Their Children---------------------------------------*/

    protected/*for tets*/ List<MenuItemDataHolder> getAllParentsWithTheirChildren(List<MenuItemData> menuItems) {
        final Map<MenuItemData, List<MenuItemData>> map = new HashMap<MenuItemData, List<MenuItemData>>();
        getAllParentsWithTheirChildrenInternal(menuItems, map);

        final List<MenuItemDataHolder> dataHolders = new ArrayList<MenuItemDataHolder>();
        for (MenuItemData parent : map.keySet()) {
            final MenuItemDataHolderColumn column = new MenuItemDataHolderColumn(map.get(parent));
            final MenuItemDataHolder holder = new MenuItemDataHolder(parent, Collections.singletonList(column));
            dataHolders.add(holder);
        }
        return dataHolders;
    }

    private void getAllParentsWithTheirChildrenInternal(List<MenuItemData> menuItemDatas, Map<MenuItemData, List<MenuItemData>> map) {
        for (MenuItemData menuItemData : menuItemDatas) {
            if (!menuItemData.getChildren().isEmpty()) {
                map.put(menuItemData, menuItemData.getChildren());
            }
            getAllParentsWithTheirChildrenInternal(menuItemData.getChildren(), map);
        }
    }
    /*--------------------------------------Get All Parents With Their Children---------------------------------------*/


    /*--------------------------------Here is protected constructors for tests only-----------------------------------*/

    protected/*for tets*/ MenuItemDataManager() {
        siteShowOption = null;
        currentPageId = 0;
        rootElementsData = null;
//        childrenWithTheirParents = null;
        parentsWithChildrenInColumns = null;
    }

    protected/*for tets*/ MenuItemDataManager(SiteShowOption siteShowOption, int currentPageId) {
        this.siteShowOption = siteShowOption;
        this.currentPageId = currentPageId;
        rootElementsData = null;
//        childrenWithTheirParents = null;
        parentsWithChildrenInColumns = null;
    }
    /*--------------------------------Here is protected constructors for tests only-----------------------------------*/

    private final SiteShowOption siteShowOption;
    private final int currentPageId;
    private final List<MenuItemData> rootElementsData;
    //private final Map<MenuItemData, List<MenuItemData>> childrenWithTheirParents;

    private List<MenuItemDataHolder> parentsWithChildrenInColumns;
}
