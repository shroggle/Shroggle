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
package com.shroggle.presentation.site.requestContent;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;
import com.shroggle.entity.ItemType;

/**
 * @author Artem Stasuk
 */
@DataTransferObject
public final class RequestContentItem {

    public RequestContentItem(
            final String name, final ItemType type, final int itemId) {
        if (type == null) {
            throw new UnsupportedOperationException("Can't create without type!");
        }

        if (name == null) {
            throw new UnsupportedOperationException("Can't create without name!");
        }

        this.name = name;
        this.type = type;
        this.itemId = itemId;
    }

    @RemoteProperty
    public String getName() {
        return name;
    }

    @RemoteProperty
    public ItemType getType() {
        return type;
    }

    @RemoteProperty
    public int getItemId() {
        return itemId;
    }

    private final String name;
    private final int itemId;
    private final ItemType type;

}
