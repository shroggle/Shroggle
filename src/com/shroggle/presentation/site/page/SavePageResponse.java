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
package com.shroggle.presentation.site.page;

import org.directwebremoting.annotations.DataTransferObject;

/**
 * Author: dmitry.solomadin
 */
@DataTransferObject
public class SavePageResponse {

    private String treeHtml;

    private String pageSelectHtml;

    private int pageId;

    private Integer createdWidgetId;

    public Integer getCreatedWidgetId() {
        return createdWidgetId;
    }

    public void setCreatedWidgetId(Integer createdWidgetId) {
        this.createdWidgetId = createdWidgetId;
    }

    public String getTreeHtml() {
        return treeHtml;
    }

    public void setTreeHtml(String treeHtml) {
        this.treeHtml = treeHtml;
    }

    public String getPageSelectHtml() {
        return pageSelectHtml;
    }

    public void setPageSelectHtml(String pageSelectHtml) {
        this.pageSelectHtml = pageSelectHtml;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }
}
