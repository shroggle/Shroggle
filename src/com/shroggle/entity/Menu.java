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

import java.util.List;

/**
 * @author Artem Stasuk
 */
public interface Menu extends Item {

    void addChild(MenuItem child);

    public void removeChild(MenuItem menuItem);

    public List<MenuItem> getMenuItems();

    public MenuStructureType getMenuStructure();

    public void setMenuStructure(MenuStructureType menuStructure);

    public boolean isIncludePageTitle();

    public void setIncludePageTitle(boolean includePageTitle);

    public MenuStyleType getMenuStyleType();

    public void setMenuStyleType(MenuStyleType menuStyleType);

    public ItemType getItemType();

}
