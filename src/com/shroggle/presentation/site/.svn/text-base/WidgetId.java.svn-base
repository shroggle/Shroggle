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
public class WidgetId extends PageWithPosition {

    @RemoteProperty
    private int widgetSequince;

    public int getWidgetSequince() {
        return widgetSequince;
    }

    public void setWidgetSequince(int widgetSequince) {
        this.widgetSequince = widgetSequince;
    }

    public String toString() {
        return "widgetId [pageId: " + getPageId() +
                " widgetPosition: " + getWidgetPosition() +
                " widgetSequince: " + widgetSequince + "]";
    }

}