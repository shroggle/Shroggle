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

package com.shroggle.entity;

import org.directwebremoting.annotations.DataTransferObject;

import javax.persistence.Entity;
import javax.persistence.Embedded;

@DataTransferObject
@Entity(name = "workForums")
public class WorkForum extends WorkItem implements Forum {

    public ItemType getItemType() {
        return ItemType.FORUM;
    }

    public boolean isAllowSubForums() {
        return forumSettings.isAllowSubForums();
    }

    public void setAllowSubForums(boolean allowSubForums) {
        forumSettings.setAllowSubForums(allowSubForums);
    }

    public boolean isAllowPolls() {
        return forumSettings.isAllowPolls();
    }

    public void setAllowPolls(boolean allowPolls) {
        forumSettings.setAllowPolls(allowPolls);
    }

    public AccessGroup getCreateSubForumRight() {
        return forumSettings.getCreateSubForumRight();
    }

    public void setCreateSubForumRight(AccessGroup createSubForumRight) {
        forumSettings.setCreateSubForumRight(createSubForumRight);
    }

    public AccessGroup getCreateThreadRight() {
        return forumSettings.getCreateThreadRight();
    }

    public void setCreateThreadRight(AccessGroup createThreadRight) {
        forumSettings.setCreateThreadRight(createThreadRight);
    }

    public AccessGroup getCreatePostRight() {
        return forumSettings.getCreatePostRight();
    }

    public void setCreatePostRight(AccessGroup createPostRight) {
        forumSettings.setCreatePostRight(createPostRight);
    }

    public AccessGroup getCreatePollRight() {
        return forumSettings.getCreatePollRight();
    }

    public void setCreatePollRight(AccessGroup createPollRight) {
        forumSettings.setCreatePollRight(createPollRight);
    }

    public AccessGroup getVoteInPollRight() {
        return forumSettings.getVoteInPollRight();
    }

    public void setVoteInPollRight(AccessGroup voteInPollRight) {
        forumSettings.setVoteInPollRight(voteInPollRight);
    }

    public AccessGroup getManageSubForumsRight() {
        return forumSettings.getManageSubForumsRight();
    }

    public void setManageSubForumsRight(AccessGroup manageSubForumsRight) {
        forumSettings.setManageSubForumsRight(manageSubForumsRight);
    }

    public AccessGroup getManagePostsRight() {
        return forumSettings.getManagePostsRight();
    }

    public void setManagePostsRight(AccessGroup managePostsRight) {
        forumSettings.setManagePostsRight(managePostsRight);
    }

    @Embedded
    private ForumSettings forumSettings = new ForumSettings();

}