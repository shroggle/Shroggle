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

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

/**
 * @author Stasuk Artem
 */
@DataTransferObject
public class PageWithPosition {

    @RemoteProperty
    private int pageId;

    @RemoteProperty
    private int widgetPosition;

    public int getWidgetPosition() {
        return widgetPosition;
    }

    public void setWidgetPosition(int widgetPosition) {
        this.widgetPosition = widgetPosition;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public String toString() {
        return "widgetId [pageId: " + pageId + " widgetPosition: " + widgetPosition + "]";
    }

}