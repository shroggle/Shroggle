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

import com.shroggle.entity.ItemSize;
import org.directwebremoting.annotations.DataTransferObject;

/**
 * Author: dmitry.solomadin
 * Date: 02.06.2009
 */
@DataTransferObject
public class CreateItemSizeRequest {

    private ItemSize itemSize = new ItemSize();

    private Integer widgetId;

    private Integer draftItemId;

    private boolean saveItemSizeInCurrentPlace;

    public Integer getDraftItemId() {
        return draftItemId;
    }

    public void setDraftItemId(Integer draftItemId) {
        this.draftItemId = draftItemId;
    }

    public ItemSize getItemSize() {
        return itemSize;
    }

    public void setItemSize(ItemSize itemSize) {
        this.itemSize = itemSize;
    }

    public Integer getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(Integer widgetId) {
        this.widgetId = widgetId;
    }

    public boolean isSaveItemSizeInCurrentPlace() {
        return saveItemSizeInCurrentPlace;
    }

    public void setSaveItemSizeInCurrentPlace(boolean saveItemSizeInCurrentPlace) {
        this.saveItemSizeInCurrentPlace = saveItemSizeInCurrentPlace;
    }
}
