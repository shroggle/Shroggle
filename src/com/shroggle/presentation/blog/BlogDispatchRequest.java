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
package com.shroggle.presentation.blog;

import org.directwebremoting.annotations.DataTransferObject;
import com.shroggle.logic.blog.BlogDispatchType;
import com.shroggle.entity.SiteShowOption;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject
public class BlogDispatchRequest {

    private int widgetId;

    private BlogDispatchType dispatchBlog;

    private int blogId;

    private SiteShowOption siteShowOption;

    private int startIndex;

    private Integer exactBlogPostId;

    public Integer getExactBlogPostId() {
        return exactBlogPostId;
    }

    public void setExactBlogPostId(Integer exactBlogPostId) {
        this.exactBlogPostId = exactBlogPostId;
    }

    public int getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(int widgetId) {
        this.widgetId = widgetId;
    }

    public BlogDispatchType getDispatchBlog() {
        return dispatchBlog;
    }

    public void setDispatchBlog(BlogDispatchType dispatchBlog) {
        this.dispatchBlog = dispatchBlog;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public SiteShowOption getSiteShowOption() {
        return siteShowOption;
    }

    public void setSiteShowOption(SiteShowOption siteShowOption) {
        this.siteShowOption = siteShowOption;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }
}
