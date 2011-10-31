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

import com.shroggle.entity.BlogPost;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.entity.Widget;
import com.shroggle.logic.site.page.PageManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Balakirev Anatoliy
 */
public class BlogSummaryLinksCreator {

    public static void execute(final BlogSummaryDataForPreview blogSummaryData, final PageManager currentPage, final SiteShowOption siteShowOption) {
        final PageManager widgetPage = new PageManager(blogSummaryData.getWidget().getPage(), siteShowOption);
        final Map<Integer, String> postsUrl = new HashMap<Integer, String>();
        if (currentPage.getPageId() == widgetPage.getPageId()) {
            final Widget widgetWithBlog;
            //if (currentPage.getPageId() == widgetPage.getPageId()) { WTF? How can it be, according to the previous check? Tolik.
            widgetWithBlog = blogSummaryData.getWidget();
            /*} else {
                widgetWithBlog = getWidgetForPageVersionByCrossWidgetId(currentPage, blogSummaryData.getWidget().getCrossWidgetId());
            }*/
            if (widgetWithBlog != null) {
                for (BlogPost blogPost : blogSummaryData.getBlogPosts()) {
                    postsUrl.put(blogPost.getBlogPostId(), "javascript:showBlogPosts(" + widgetWithBlog.getWidgetId() + ", " + blogSummaryData.getBlogId() + ", null, " + blogPost.getBlogPostId() + ");");
                }
            } else {
                for (BlogPost blogPost : blogSummaryData.getBlogPosts()) {
                    postsUrl.put(blogPost.getBlogPostId(), "#");
                }
            }
        } else {
            for (BlogPost blogPost : blogSummaryData.getBlogPosts()) {
                StringBuilder postUrl = new StringBuilder();
                postUrl.append(widgetPage.getUrl());
                if (!postUrl.toString().contains("?")) {
                    postUrl.append("?");
                } else {
                    postUrl.append("&");
                }
                postUrl.append("selectedWidgetId=");
                postUrl.append(blogSummaryData.getWidget().getWidgetId());
                postUrl.append("&blogPostId=");
                postUrl.append(blogPost.getBlogPostId());
                postUrl.append("#");
                postUrl.append("widget");
                postUrl.append(blogSummaryData.getWidget().getWidgetId());
                postUrl.append("blogPost");
                postUrl.append(blogPost.getBlogPostId());
                postsUrl.put(blogPost.getBlogPostId(), postUrl.toString());
            }
        }
        blogSummaryData.setPostUrl(postsUrl);
    }

   /* private static Widget getWidgetForPageVersionByCrossWidgetId(final PageManager pageManager, final Integer crossWidgetId) {
        if (pageManager != null && crossWidgetId != null) {
            for (Widget widget : pageManager.getWidgetsWithItemSize()) {
                if (widget.getCrossWidgetId() == crossWidgetId) {
                    return widget;
                }
            }
        }
        return null;
    }*/
}