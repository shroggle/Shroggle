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
package com.shroggle.presentation.forum;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;
import com.shroggle.logic.forum.ForumDispatchType;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject
public class ForumDispatchRequest {

    @RemoteProperty
    private ForumDispatchType dispatchForum;

    @RemoteProperty
    private Integer widgetId;

    @RemoteProperty
    private Integer forumId;

    @RemoteProperty
    private Integer subForumId;

    @RemoteProperty
    private Integer threadId;

    @RemoteProperty
    private Integer postId;

    @RemoteProperty
    private Boolean showOnUserPages;

    @RemoteProperty
    private boolean draftPostEdit;

    public boolean isDraftPostEdit() {
        return draftPostEdit;
    }

    public void setDraftPostEdit(boolean draftPostEdit) {
        this.draftPostEdit = draftPostEdit;
    }

    public Boolean isShowOnUserPages() {
        return showOnUserPages;
    }

    public void setShowOnUserPages(Boolean showOnUserPages) {
        this.showOnUserPages = showOnUserPages;
    }

    public Integer getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(Integer widgetId) {
        this.widgetId = widgetId;
    }

    public Integer getForumId() {
        return forumId;
    }

    public void setForumId(Integer forumId) {
        this.forumId = forumId;
    }

    public Integer getSubForumId() {
        return subForumId;
    }

    public void setSubForumId(Integer subForumId) {
        this.subForumId = subForumId;
    }

    public Integer getThreadId() {
        return threadId;
    }

    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public ForumDispatchType getDispatchForum() {
        return dispatchForum;
    }

    public void setDispatchForum(ForumDispatchType dispatchForum) {
        this.dispatchForum = dispatchForum;
    }
}
