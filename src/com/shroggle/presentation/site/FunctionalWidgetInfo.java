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

import com.shroggle.entity.MenuStyleType;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.entity.Widget;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

import java.util.ArrayList;
import java.util.List;

@DataTransferObject
public class FunctionalWidgetInfo {

    @RemoteProperty
    private int widgetId;

    @RemoteProperty
    private int widgetPosition;

    @RemoteProperty
    private String widgetInfo;

    @RemoteProperty
    private String widgetContent;

    @RemoteProperty
    private String widgetStyle;

    @RemoteProperty
    private WidgetInfo widget;

    @RemoteProperty
    private MenuStyleType menuType;

    @RemoteProperty
    private List<FunctionalWidgetInfo> childInfos = new ArrayList<FunctionalWidgetInfo>();

    public String getWidgetStyle() {
        return widgetStyle;
    }

    public void setWidgetStyle(String widgetStyle) {
        this.widgetStyle = widgetStyle;
    }

    public MenuStyleType getMenuType() {
        return menuType;
    }

    public void setMenuType(MenuStyleType menuType) {
        this.menuType = menuType;
    }

    public void addChildInfo(FunctionalWidgetInfo functionalWidgetInfo) {
        childInfos.add(functionalWidgetInfo);
    }

    public List<FunctionalWidgetInfo> getChildInfos() {
        return childInfos;
    }

    public void setChildInfos(List<FunctionalWidgetInfo> childInfos) {
        this.childInfos = childInfos;
    }

    public int getWidgetPosition() {
        return widgetPosition;
    }

    public void setWidgetPosition(int widgetPosition) {
        this.widgetPosition = widgetPosition;
    }

    public WidgetInfo getWidget() {
        return widget;
    }

    public void setWidget(Widget widget, SiteShowOption siteShowOption) {
        this.widget = new WidgetInfo(widget, siteShowOption);
    }

    public String getWidgetContent() {
        return widgetContent;
    }

    public void setWidgetContent(String widgetContent) {
        this.widgetContent = widgetContent;
    }

    public Integer getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(Integer widgetId) {
        this.widgetId = widgetId;
    }

    public String getWidgetInfo() {
        return widgetInfo;
    }

    public void setWidgetInfo(String widgetInfo) {
        this.widgetInfo = widgetInfo;
    }
}
