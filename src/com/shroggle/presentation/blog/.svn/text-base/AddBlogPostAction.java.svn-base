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

import com.shroggle.entity.*;
import com.shroggle.logic.blog.BlogRight;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/blog/addBlogPost.action")
public class AddBlogPostAction extends Action {

    @DefaultHandler
    public Resolution execute() {
        if (StringUtil.isNullOrEmpty(text)) {
            return new ForwardResolution("/blog/addBlogPost.jsp");
        }

        final DraftBlog blog = persistance.getDraftItem(blogId);
        if (blog != null) {

            final User loginUser = new UsersManager().getLoginedUser();
            if (BlogRight.allowAddBlogPost(loginUser, blog)) {

                final BlogPost blogPost = new BlogPost();
                blogPost.setPostTitle(title);
                blogPost.setVisitorId(loginUser != null ? loginUser.getUserId() : null);
                blogPost.setPostRead(0);
                persistanceTransaction.execute(new Runnable() {

                    public void run() {
                        if (asDraft) {
                            blogPost.setDraftText(text);
                        } else {
                            blogPost.setText(text);
                        }
                        blog.addBlogPost(blogPost);
                        persistance.putBlogPost(blogPost);

                        if (contentId != null) {
                            final Content content = persistance.getContentById(contentId);
                            if (content != null) {
                                persistance.removeContent(content);
                            }
                        }
                    }

                });
            }
        }

        final ForwardResolution resolution = new ForwardResolution(ShowBlogPostsAction.class);
        resolution.addParameter("blogId", blogId);
        resolution.addParameter("widgetBlogId", widgetBlogId);
        resolution.addParameter("siteShowOption", siteShowOption);
        return resolution;
    }

    public void setWidgetBlogId(int widgetBlogId) {
        this.widgetBlogId = widgetBlogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAsDraft(boolean asDraft) {
        this.asDraft = asDraft;
    }

    public void setContentId(ContentId contentId) {
        this.contentId = contentId;
    }

    public ContentId getContentId() {
        return contentId;
    }

    public void setSiteShowOption(SiteShowOption siteShowOption) {
        this.siteShowOption = siteShowOption;
    }

    private int blogId;
    private int widgetBlogId;
    private String text;
    private ContentId contentId = new ContentId();
    private String title;
    private SiteShowOption siteShowOption;
    private boolean asDraft;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}