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

/**
 * @author Artem Stasuk
 */
public class ItemNamingSameAsInOriginal implements ItemNaming {

    @Override
    public String execute(final ItemType type, final int siteId, final String customNamePattern,            final boolean dontUseFirstNumber) {
        return customNamePattern;
    }

}
