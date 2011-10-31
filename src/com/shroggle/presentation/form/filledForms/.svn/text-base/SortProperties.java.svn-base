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
package com.shroggle.presentation.form.filledForms;

import com.shroggle.presentation.site.ManageFormRecordSortType;
import org.directwebremoting.annotations.DataTransferObject;

/**
 * @author Balakirev Anatoliy
 */
@DataTransferObject
public class SortProperties {

    private String itemName;

    private boolean descending;

    private ManageFormRecordSortType sortFieldType;

    public SortProperties(String itemName, boolean descending, ManageFormRecordSortType sortFieldType) {
        this.itemName = itemName;
        this.descending = descending;
        this.sortFieldType = sortFieldType;
    }

    public SortProperties() {
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isDescending() {
        return descending;
    }

    public void setDescending(boolean descending) {
        this.descending = descending;
    }

    public ManageFormRecordSortType getSortFieldType() {
        return sortFieldType;
    }

    public void setSortFieldType(ManageFormRecordSortType sortFieldType) {
        this.sortFieldType = sortFieldType;
    }
}
