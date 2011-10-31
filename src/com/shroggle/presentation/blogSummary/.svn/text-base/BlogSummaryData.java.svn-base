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

package com.shroggle.presentation.blogSummary;

import com.shroggle.entity.WidgetItem;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.util.StringUtil;
import com.shroggle.util.copier.CopierUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
public class BlogSummaryData {

    public BlogSummaryData(final BlogSummaryData blogSummaryData) {
        CopierUtil.copyProperties(blogSummaryData, this, "WidgetItems");
        this.widgetItems = new ArrayList<BlogSummaryDataWidget>(blogSummaryData.getWidgetItems());
    }

    public BlogSummaryData() {
    }

    private int blogId;

    private String blogName;

    private List<BlogSummaryDataWidget> widgetItems;


    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public boolean containsCrossWidgetId(final int crossWidgetId) {
        for (BlogSummaryDataWidget widget : widgetItems) {
            if (widget.getCrossWidgetId() == crossWidgetId) {
                return true;
            }
        }
        return false;
    }

    public List<BlogSummaryDataWidget> getWidgetItems() {
        return widgetItems;
    }

    public void setWidgetItems(List<WidgetItem> widgetItems) {
        this.widgetItems = new ArrayList<BlogSummaryDataWidget>();
        for (WidgetItem widgetItem : widgetItems) {
            this.widgetItems.add(new BlogSummaryDataWidget(widgetItem));
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof BlogSummaryData) {
            final BlogSummaryData blogSummaryData = (BlogSummaryData) obj;
            return blogSummaryData.getBlogId() == getBlogId() &&
                    StringUtil.getEmptyOrString(blogSummaryData.getBlogName()).equals(StringUtil.getEmptyOrString(getBlogName())) &&
                    blogSummaryData.getWidgetItems().equals(getWidgetItems());
        } else {
            return false;
        }
    }

    public static class BlogSummaryDataWidget {

        public BlogSummaryDataWidget(final WidgetItem widgetItem) {
            this.widgetManager = new WidgetManager(widgetItem);
        }

        private final WidgetManager widgetManager;

        public int getSiteId() {
            return widgetManager.getSiteId();
        }

        public int getCrossWidgetId() {
            return widgetManager.getCrossWidgetId();
        }

        public String getLocation() {
            return widgetManager.getLocation();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof BlogSummaryDataWidget) {
                final BlogSummaryDataWidget blogSummaryDataWidget = (BlogSummaryDataWidget) obj;
                return blogSummaryDataWidget.getCrossWidgetId() == getCrossWidgetId() &&
                        blogSummaryDataWidget.getSiteId() == getSiteId() &&
                        StringUtil.getEmptyOrString(blogSummaryDataWidget.getLocation()).equals(StringUtil.getEmptyOrString(getLocation()));
            } else {
                return false;
            }
        }
    }
}