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

import com.shroggle.entity.DraftBlog;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.entity.User;
import com.shroggle.logic.blog.BlogManager;
import com.shroggle.logic.blog.BlogPostsManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByClassProperty;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

/**
 * @author Stasuk Artem
 */
@UrlBinding("/blog/showBlogPosts.action")
public class ShowBlogPostsAction extends Action {

    @SynchronizeByClassProperty(
            entityIdFieldPath = "blogId",
            entityClass = DraftBlog.class)
    @DefaultHandler
    public Resolution execute() {
        // Page id can be null if we are viewing this blog from inside app on admin interface.
        if (pageId != null && pageId != 0) {
            showPageVersionUrl = new PageManager(ServiceLocator.getPersistance().getPage(pageId), siteShowOption).getUrl();
        } else {
            showPageVersionUrl = "";
        }

        loginedUser = new UsersManager().getLoginedUser();
        blog = new BlogManager(blogId, siteShowOption);
        blogPosts = new BlogPostsManager(blog, startIndex, exactBlogPostId);
        siteId = blog.getBlog().getSiteId();
        return resolutionCreator.forwardToUrl("/blog/showBlogPosts.jsp");
    }

    public BlogPostsManager getBlogPosts() {
        return blogPosts;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public void setWidgetBlogId(int widgetBlogId) {
        this.widgetBlogId = widgetBlogId;
    }

    public int getBlogId() {
        return blogId;
    }

    public int getWidgetBlogId() {
        return widgetBlogId;
    }

    public BlogManager getBlog() {
        return blog;
    }

    public void setSiteShowOption(SiteShowOption siteShowOption) {
        this.siteShowOption = siteShowOption;
    }

    public SiteShowOption getSiteShowOption() {
        return siteShowOption;
    }

    public int getSiteId() {
        return siteId;
    }

    public Integer getExactBlogPostId() {
        return exactBlogPostId;
    }

    public void setExactBlogPostId(Integer exactBlogPostId) {
        this.exactBlogPostId = exactBlogPostId;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public String getShowPageVersionUrl() {
        return showPageVersionUrl;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public User getLoginedUser() {
        return loginedUser;
    }

    private SiteShowOption siteShowOption;
    private BlogManager blog;
    private int widgetBlogId;
    private int blogId;

    /**
     * Start show blog posts from this number
     *
     * @link http://jira.web-deva.com/browse/SW-2125
     */
    private int startIndex;
    private Integer exactBlogPostId;
    private BlogPostsManager blogPosts;
    private int siteId;
    private String showPageVersionUrl;
    private Integer pageId;
    private User loginedUser;
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();

}