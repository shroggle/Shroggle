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
package com.shroggle.logic.site.page;

/**
* @author Artem Stasuk
*/
public class SavePageResponse {

    private int pageId;

    private Integer createWidgetId;

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public Integer getCreateWidgetId() {
        return createWidgetId;
    }

    public void setCreateWidgetId(Integer createWidgetId) {
        this.createWidgetId = createWidgetId;
    }

}
