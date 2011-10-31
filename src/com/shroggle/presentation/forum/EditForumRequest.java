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

import com.shroggle.entity.AccessGroup;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

@DataTransferObject
public class EditForumRequest {

    public AccessGroup getManageSubForumsRight() {
        return manageSubForumsRight;
    }

    public void setManageSubForumsRight(AccessGroup manageSubForumsRight) {
        this.manageSubForumsRight = manageSubForumsRight;
    }

    public AccessGroup getManagePostsRight() {
        return managePostsRight;
    }

    public void setManagePostsRight(AccessGroup managePostsRight) {
        this.managePostsRight = managePostsRight;
    }

    public boolean isAllowSubForums() {
        return allowSubForums;
    }

    public void setAllowSubForums(boolean allowSubForums) {
        this.allowSubForums = allowSubForums;
    }

    public boolean isAllowPolls() {
        return allowPolls;
    }

    public void setAllowPolls(boolean allowPolls) {
        this.allowPolls = allowPolls;
    }

    public int getForumId() {
        return forumId;
    }

    public void setForumId(int forumId) {
        this.forumId = forumId;
    }

    public String getNewForumName() {
        return newForumName;
    }

    public void setNewForumName(String newForumName) {
        this.newForumName = newForumName;
    }

    public AccessGroup getCreateSubForumRight() {
        return createSubForumRight;
    }

    public void setCreateSubForumRight(AccessGroup createSubForumRight) {
        this.createSubForumRight = createSubForumRight;
    }

    public AccessGroup getCreateThreadRight() {
        return createThreadRight;
    }

    public void setCreateThreadRight(AccessGroup createThreadRight) {
        this.createThreadRight = createThreadRight;
    }

    public AccessGroup getCreatePostRight() {
        return createPostRight;
    }

    public void setCreatePostRight(AccessGroup createPostRight) {
        this.createPostRight = createPostRight;
    }

    public AccessGroup getCreatePollRight() {
        return createPollRight;
    }

    public void setCreatePollRight(AccessGroup createPollRight) {
        this.createPollRight = createPollRight;
    }

    public AccessGroup getVoteInPollRight() {
        return voteInPollRight;
    }

    public void setVoteInPollRight(AccessGroup voteInPollRight) {
        this.voteInPollRight = voteInPollRight;
    }

    public Integer getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(Integer widgetId) {
        this.widgetId = widgetId;
    }

    @RemoteProperty
    private int forumId;

    @RemoteProperty
    private String newForumName;

    @RemoteProperty
    private AccessGroup createSubForumRight;

    @RemoteProperty
    private AccessGroup createThreadRight;

    @RemoteProperty
    private AccessGroup createPostRight;

    @RemoteProperty
    private AccessGroup createPollRight;

    @RemoteProperty
    private AccessGroup voteInPollRight;

    @RemoteProperty
    private AccessGroup manageSubForumsRight;

    @RemoteProperty
    private AccessGroup managePostsRight;

    @RemoteProperty
    private boolean allowPolls;

    @RemoteProperty
    private boolean allowSubForums;

    @RemoteProperty
    private Integer widgetId;

}