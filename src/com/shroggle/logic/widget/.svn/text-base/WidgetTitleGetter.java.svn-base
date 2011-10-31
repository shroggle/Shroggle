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
package com.shroggle.logic.widget;

import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.pageversion.PageTitle;
import com.shroggle.logic.site.page.pageversion.PageTitleGetter;
import com.shroggle.util.html.HtmlUtil;

public class WidgetTitleGetter implements WidgetTitle {

    public WidgetTitleGetter(final Widget widget) {
        this(widget, null);
    }

    public WidgetTitleGetter(final Widget widget, final String defaultWidgetTitle) {
        pageTitle = new PageTitleGetter(new PageManager(widget.getPage()));
        String tempWidgetTitle = null;
        if (widget.isWidgetItem()) {
            final WidgetItem widgetItem = (WidgetItem) widget;
            final DraftItem draftItem = widgetItem.getDraftItem();
            if (draftItem != null) {
                tempWidgetTitle = HtmlUtil.limitName(draftItem.getName());

                if (tempWidgetTitle == null || tempWidgetTitle.isEmpty()){
                    if (draftItem.getItemType() == ItemType.IMAGE){
                        tempWidgetTitle = "Image";
                    } else if (draftItem.getItemType() == ItemType.VIDEO){
                        tempWidgetTitle = "Video";
                    } else if (draftItem.getItemType() == ItemType.TEXT){
                        tempWidgetTitle = "Text";
                    } else if (draftItem.getItemType() == ItemType.GALLERY_DATA){
                        tempWidgetTitle = "Gallery Data";
                    } else {
                        tempWidgetTitle = defaultWidgetTitle != null ? defaultWidgetTitle : "item undefined";
                    }
                }
            } else {
                tempWidgetTitle = defaultWidgetTitle != null ? defaultWidgetTitle : "item undefined";
            }
        } else if (widget.isWidgetComposit()) {
            tempWidgetTitle = "Media block";
        } else if (widget.getItemType().equals(ItemType.LOGIN)) {
            tempWidgetTitle = "Login with Registration";
        }
        if (tempWidgetTitle == null) {
            tempWidgetTitle = defaultWidgetTitle;
        }
        this.widgetTitle = tempWidgetTitle;
    }

    public String getWidgetTitle() {
        return widgetTitle;
    }

    public String getPageVersionTitle() {
        return pageTitle.getPageVersionTitle();
    }

    public String getSiteTitle() {
        return pageTitle.getSiteTitle();
    }

    private final PageTitle pageTitle;
    private final String widgetTitle;

}
