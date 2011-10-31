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
public class SaveTextRequest {

    @RemoteProperty
    private Integer widgetId;

    @RemoteProperty
    private int textItemId;

    @RemoteProperty
    private String widgetText;

    @RemoteProperty
    private Boolean saveDraftText;

    @RemoteProperty
    private String name;

    public int getTextItemId() {
        return textItemId;
    }

    public void setTextItemId(int textItemId) {
        this.textItemId = textItemId;
    }

    public void setSaveDraftText(Boolean saveDraftText) {
        this.saveDraftText = saveDraftText;
    }

    public Boolean getSaveDraftText() {
        return saveDraftText;
    }

    public String getWidgetText() {
        return widgetText;
    }

    public void setWidgetText(String widgetText) {
        this.widgetText = widgetText;
    }

    public Integer getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(Integer widgetId) {
        this.widgetId = widgetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}