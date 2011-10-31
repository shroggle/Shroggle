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

import com.shroggle.entity.*;
import com.shroggle.logic.blogSummary.ShowBlogSummaryLogic;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
public class BlogSummaryDataForPreviewCreator {

    public static List<BlogSummaryDataForPreview> create(final BlogSummary blogSummary,
                                                         final PageManager pageManager, final SiteShowOption siteShowOption) {
        // Creating blog summary data for preview
        List<BlogSummaryDataForPreview> blogSummaryPreviewData =
                BlogSummaryDataForPreviewCreator.createByCrossWidgetsIds(blogSummary.getIncludedCrossWidgetId(),
                        pageManager, siteShowOption);
        // Removing odd posts
        ShowBlogSummaryLogic.selectBlogPostsAndRemoveSuperfluous(blogSummary.getPostDisplayCriteria(),
                blogSummary.getIncludedPostNumber(), blogSummaryPreviewData);
        // Sorting posts
        ShowBlogSummaryLogic.sortBlogPosts(blogSummary.getPostSortCriteria(), blogSummaryPreviewData);
        return blogSummaryPreviewData;
    }


    static List<BlogSummaryDataForPreview> createByCrossWidgetsIds(List<Integer> crossWidgetsId,
                                                                   final PageManager pageManager, final SiteShowOption siteShowOption) {
        final List<BlogSummaryDataForPreview> blogsWithWidgets = new ArrayList<BlogSummaryDataForPreview>();
        if (crossWidgetsId != null) {
            final Persistance persistance = ServiceLocator.getPersistance();
            for (Widget widget : persistance.getWidgetsByCrossWidgetsId(crossWidgetsId, siteShowOption)) {
                final BlogSummaryDataForPreview dataForPreview = createBlogSummaryDataByWidget(widget);
                if (dataForPreview != null) {
                    BlogSummaryLinksCreator.execute(dataForPreview, pageManager, siteShowOption);
                    blogsWithWidgets.add(dataForPreview);
                }
            }
        }
        return blogsWithWidgets;
    }

    private static BlogSummaryDataForPreview createBlogSummaryDataByWidget(final Widget widget) {
        if (widget != null && widget.isWidgetItem()) {
            final DraftBlog blog = (DraftBlog) ((WidgetItem) widget).getDraftItem();
            if (blog != null) {
                final BlogSummaryDataForPreview blogSummaryData = new BlogSummaryDataForPreview(blog.getId(), blog.getName(), ((WidgetItem) widget));
                final List<BlogPost> blogPostList = new ArrayList<BlogPost>();
                for (final BlogPost blogPost : ServiceLocator.getPersistance().getBlogPostsByBlog(blog.getId())) {
                    if (blogPost != null && blogPost.getText() != null) {
                        blogPostList.add(blogPost);
                    }
                }
                blogSummaryData.setBlogPosts(blogPostList);
                return blogSummaryData;
            }
        }
        return null;
    }
}
