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
package com.shroggle.presentation.menu;

import org.directwebremoting.annotations.DataTransferObject;

/**
 * @author Balakirev Anatoliy
 */
@DataTransferObject
public class UpdateMenuItemResponse {

    private String treeHtml;
    
    private String infoAboutSelectedItem = "";

    private Integer selectedMenuItemId;

    public Integer getSelectedMenuItemId() {
        return selectedMenuItemId;
    }

    public void setSelectedMenuItemId(Integer selectedMenuItemId) {
        this.selectedMenuItemId = selectedMenuItemId;
    }

    public String getTreeHtml() {
        return treeHtml;
    }

    public void setTreeHtml(String treeHtml) {
        this.treeHtml = treeHtml;
    }

    public String getInfoAboutSelectedItem() {
        return infoAboutSelectedItem;
    }

    public void setInfoAboutSelectedItem(String infoAboutSelectedItem) {
        this.infoAboutSelectedItem = infoAboutSelectedItem;
    }
}
