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
package com.shroggle.logic.site.item;

import com.shroggle.entity.ItemType;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;

import java.util.Locale;

/**
 * @author Balakirev Anatoliy
 */
public class ItemTypeManager {

    public ItemTypeManager(ItemType itemType) {
        if (itemType == null) {
            throw new IllegalArgumentException("Unable to create ItemTypeManager without ItemType.");
        }
        this.itemType = itemType;
    }

    public String getName() {
        return international.get(itemType.toString());
    }

    public String getItemName() { // I`ve added this method, because there is it`s usage in some jstl. Check all places with jstl, before deletion of this method. Tolik
        return getName();
    }

    private final ItemType itemType;
    /**
     * Some pepole may ask me why i use this slow code? Because i added special cached version InternationalStorage
     * and now it work very fast, for example for 10000 getting it excuted in 17 msec, opposit 7000 msec on
     * storage without cache =)
     * @see com.shroggle.util.international.cache.InternationalStorageCache
     * @see com.shroggle.util.international.parameters.InternationalStorageParameters
     * @see com.shroggle.util.international.property.InternationalStoragePropertyBundle 
     */
    private final International international = ServiceLocator.getInternationStorage().get("itemType", Locale.US);

}

