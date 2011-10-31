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

import java.util.List;
import java.util.ArrayList;

/**
 * @author Stasuk Artem
 */
@DataTransferObject
public class MoveWidgetRequest {

    @RemoteProperty
    private int widgetId;

    @RemoteProperty
    private int toWidgetId;

    @RemoteProperty
    private int toWidgetPosition;

    @RemoteProperty
    private List<CreateItemSizeRequest> widgetsToChangeSize = new ArrayList<CreateItemSizeRequest>();

    public List<CreateItemSizeRequest> getWidgetsToChangeSize() {
        return widgetsToChangeSize;
    }

    public void setWidgetsToChangeSize(List<CreateItemSizeRequest> widgetsToChangeSize) {
        this.widgetsToChangeSize = widgetsToChangeSize;
    }

    public int getToWidgetPosition() {
        return toWidgetPosition;
    }

    public void setToWidgetPosition(int toWidgetPosition) {
        this.toWidgetPosition = toWidgetPosition;
    }

    public int getToWidgetId() {
        return toWidgetId;
    }

    public void setToWidgetId(int toWidgetId) {
        this.toWidgetId = toWidgetId;
    }

    public int getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(int widgetId) {
        this.widgetId = widgetId;
    }

}