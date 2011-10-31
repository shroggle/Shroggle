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
import org.directwebremoting.annotations.RemoteProperty;

/**
 * @author Stasuk Artem
 */
@DataTransferObject
public class EditBlogPostRequest {

    public String getBlogPostText() {
        return blogPostText;
    }

    public void setBlogPostText(String blogPostText) {
        this.blogPostText = blogPostText;
    }

    public int getBlogPostId() {
        return blogPostId;
    }

    public void setBlogPostId(int blogPostId) {
        this.blogPostId = blogPostId;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public boolean isAsDraft() {
        return asDraft;
    }

    public void setAsDraft(boolean asDraft) {
        this.asDraft = asDraft;
    }

    public String getCreationDateString() {
        return creationDateString;
    }

    public void setCreationDateString(String creationDateString) {
        this.creationDateString = creationDateString;
    }

    @RemoteProperty
    private String blogPostText;

    @RemoteProperty
    private int blogPostId;

    @RemoteProperty
    private String postTitle;

    @RemoteProperty
    private boolean asDraft;

    @RemoteProperty
    private String creationDateString;

}