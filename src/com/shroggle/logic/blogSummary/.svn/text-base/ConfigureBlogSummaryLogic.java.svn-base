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
package com.shroggle.logic.blogSummary;

import com.shroggle.entity.*;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.ServiceLocator;
import com.shroggle.logic.widget.DefaultNameGetterService;
import com.shroggle.presentation.blogSummary.BlogSummaryData;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

/**
 * @author Balakirev Anatoliy
 */
public class ConfigureBlogSummaryLogic {

    public static List<Integer> createSelectedBlogsIds(List<Integer> selectedCrossWidgetsIds, List<BlogSummaryData> blogSummaryDatas) {
        Set<Integer> selectedBlogsIds = new HashSet<Integer>();
        for (int crossWidgetId : selectedCrossWidgetsIds) {
            for (BlogSummaryData blogSummaryData : blogSummaryDatas) {
                if (blogSummaryData.containsCrossWidgetId(crossWidgetId)) {
                    selectedBlogsIds.add(blogSummaryData.getBlogId());
                    break;
                }
            }
        }
        return new ArrayList<Integer>(selectedBlogsIds);
    }

    public static String createBlogsIdsAsString(List<BlogSummaryData> blogsForBlogSummary) {
        String blogsId = "";
        for (BlogSummaryData userBlog : blogsForBlogSummary) {
            blogsId += userBlog.getBlogId() + ";";
        }
        if (blogsId.endsWith(";")) {
            blogsId = blogsId.substring(0, blogsId.length() - 1);
        }
        return blogsId;
    }
}
