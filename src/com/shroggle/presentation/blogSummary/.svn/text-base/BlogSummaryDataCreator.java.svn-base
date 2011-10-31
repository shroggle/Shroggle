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
import com.shroggle.logic.widget.WidgetSort;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.*;

/**
 * @author Balakirev Anatoliy
 */
public class BlogSummaryDataCreator {

    private final List<BlogSummaryData> blogsFromAllSites;
    private final Map<Integer, List<BlogSummaryData>> blogsForSite = new HashMap<Integer, List<BlogSummaryData>>();

    public BlogSummaryDataCreator(final Integer userId, SiteShowOption siteShowOption) {
        final Persistance persistance = ServiceLocator.getPersistance();
        final List<Site> sites = persistance.getSites(userId, SiteAccessLevel.getUserAccessLevels());
        final List<Integer> siteIds = new ArrayList<Integer>();
        for (Site site : sites) {
            siteIds.add(site.getId());
        }
        //siteIds.retainAll(Arrays.asList(806));
        final Map<DraftItem, List<WidgetItem>> draftBlogs = persistance.getItems(siteIds, ItemType.BLOG, siteShowOption);
        blogsFromAllSites = createBlogSummaryData(draftBlogs);
    }

    public List<BlogSummaryData> getForAllSites() {
        Collections.sort(blogsFromAllSites, sortByName);
        return blogsFromAllSites;
    }

    public List<BlogSummaryData> getForSite(final Integer selectedSiteId) {
        if (selectedSiteId == null) {
            return Collections.emptyList();
        }
        List<BlogSummaryData> blogWithWidgets = blogsForSite.get(selectedSiteId);
        if (blogWithWidgets == null) {
            blogWithWidgets = getForSiteInternal(selectedSiteId);
            blogsForSite.put(selectedSiteId, blogWithWidgets);
        }
        Collections.sort(blogWithWidgets, sortByName);
        return blogWithWidgets;
    }


    //--------------------------------------------------private methods-------------------------------------------------

    private List<BlogSummaryData> getForSiteInternal(final int selectedSiteId) {
        List<BlogSummaryData> blogWithWidgets = new ArrayList<BlogSummaryData>();
        for (BlogSummaryData blogWithWidget : blogsFromAllSites) {
            if (isInsertedOnSite(blogWithWidget, selectedSiteId)) {
                // Copying blog summary data
                final BlogSummaryData copy = new BlogSummaryData(blogWithWidget);
                blogWithWidgets.add(copy);
                // Removing widgets not from current sites from copied BlogSummaryData. 
                final Iterator<BlogSummaryData.BlogSummaryDataWidget> iterator = copy.getWidgetItems().iterator();
                while (iterator.hasNext()) {
                    if (iterator.next().getSiteId() != selectedSiteId) {
                        iterator.remove();
                    }
                }
            }
        }
        return blogWithWidgets;
    }

    private boolean isInsertedOnSite(final BlogSummaryData blogWithWidgets, final int siteId) {
        for (BlogSummaryData.BlogSummaryDataWidget blogSummaryDataWidget : blogWithWidgets.getWidgetItems()) {
            if (blogSummaryDataWidget.getSiteId() == siteId) {
                return true;
            }
        }
        return false;
    }

    private List<BlogSummaryData> createBlogSummaryData(Map<? extends DraftItem, List<WidgetItem>> draftBlogs) {
        final List<BlogSummaryData> blogsWithWidgets = new ArrayList<BlogSummaryData>();
        for (final DraftItem draftBlog : draftBlogs.keySet()) {
            final List<WidgetItem> widgetItems = draftBlogs.get(draftBlog);
            if (widgetItems.isEmpty()) {
                continue;
            }
            final BlogSummaryData blogSummaryData = new BlogSummaryData();
            blogSummaryData.setBlogId(draftBlog.getId());
            blogSummaryData.setBlogName(draftBlog.getName());

            Collections.sort(widgetItems, WidgetSort.widgetPositionComparator);
            blogSummaryData.setWidgetItems(widgetItems);

            blogsWithWidgets.add(blogSummaryData);
        }
        return blogsWithWidgets;
    }

    private Comparator<BlogSummaryData> sortByName = new Comparator<BlogSummaryData>() {
        public int compare(BlogSummaryData o1, BlogSummaryData o2) {
            return o1.getBlogName().compareTo(o2.getBlogName());
        }
    };

}