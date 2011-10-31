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
package com.shroggle.logic.blog;

import com.shroggle.entity.BlogPost;
import com.shroggle.entity.User;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;

import java.util.Locale;

/**
 * @author Artem Stasuk
 */
public class BlogPostManager {

    public BlogPostManager(final BlogManager blogManager) {
        this.blogManager = blogManager;
    }

    public BlogPostManager(BlogPost blogPost) {
        this.blogPost = blogPost;
    }

    public String getPostAuthor() {
        String fullName = "";
        if (blogPost != null) {
            final User author = ServiceLocator.getPersistance().getUserById(blogPost.getVisitorId());
            if (author != null) {
                fullName = (StringUtil.getEmptyOrString(author.getLastName()) + " " + StringUtil.getEmptyOrString(author.getFirstName())).trim();
            }
        }
        if (fullName.isEmpty()) {
            return ServiceLocator.getInternationStorage().get("blogPost", Locale.US).get("anonymous");
        }
        return fullName;
    }

    public boolean isAuthor() {
        return author;
    }

    public int getBlogPostId() {
        return blogPost.getBlogPostId();
    }

    public void setAuthor(boolean author) {
        this.author = author;
    }

    public boolean isOwner() {
        return owner;
    }

    public boolean isOwnerOrAuthor() {
        return owner || author;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public boolean isDraft() {
        return draft;
    }

    public void setDraft(boolean draft) {
        this.draft = draft;
    }

    public BlogPost getBlogPost() {
        return blogPost;
    }

    public void setBlogPost(BlogPost blogPost) {
        this.blogPost = blogPost;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getText() {
        return draft ? blogPost.getDraftText() : blogPost.getText();
    }

    public User getAuthor() {
        return ServiceLocator.getPersistance().getUserById(blogPost.getVisitorId());
    }

    public int getCommentsCount() {
        return blogManager.getBlogPostComments(blogPost).size();
    }

    private BlogManager blogManager;
    private boolean author;
    private boolean owner;
    private boolean draft;
    private boolean editable;
    private BlogPost blogPost;

}
