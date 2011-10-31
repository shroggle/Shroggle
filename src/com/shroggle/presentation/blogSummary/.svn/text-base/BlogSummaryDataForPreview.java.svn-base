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
import com.shroggle.entity.WidgetItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Balakirev Anatoliy
 */
public class BlogSummaryDataForPreview {

    public BlogSummaryDataForPreview(int blogId, String blogName, WidgetItem widget) {
        this.blogId = blogId;
        this.blogName = blogName;
        this.widget = widget;
        this.id = (System.currentTimeMillis() / blogId);
    }

    private final long id;

    private final int blogId;

    private final String blogName;

    private final WidgetItem widget;

    private List<BlogPost> blogPosts;

    private Map<Integer, String> postUrl = new HashMap<Integer, String>();

    public long getId() {
        return id;
    }

    public int getBlogId() {
        return blogId;
    }

    public String getBlogName() {
        return blogName;
    }

    public Map<Integer, String> getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(Map<Integer, String> postUrl) {
        this.postUrl = postUrl;
    }

    public WidgetItem getWidget() {
        return widget;
    }

    public List<BlogPost> getBlogPosts() {
        return blogPosts;
    }

    public void setBlogPosts(List<BlogPost> blogPosts) {
        this.blogPosts = blogPosts;
    }
}
