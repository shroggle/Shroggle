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
package com.shroggle.presentation.site;


import com.shroggle.logic.site.item.ItemManager;

import java.util.Comparator;

/**
 * @author Stasuk Artem
 */
public class SiteItemByNameComparator implements Comparator<ItemManager> {

    public final static Comparator<ItemManager> instance = new SiteItemByNameComparator();

    private SiteItemByNameComparator() {

    }

    @Override
    public int compare(ItemManager o1, ItemManager o2) {
        return (o1.getOwnerSiteName() + o1.getName()).compareToIgnoreCase(o2.getOwnerSiteName() + o2.getName());
    }

}
