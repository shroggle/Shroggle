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

import org.directwebremoting.annotations.RemoteProperty;

import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.Column;

/**
 * @author dmitry.solomadin
 */
@Embeddable
public class ForumSettings {

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

    @RemoteProperty
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AccessGroup createSubForumRight = AccessGroup.OWNER;

    @RemoteProperty
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AccessGroup createThreadRight = AccessGroup.VISITORS;

    @RemoteProperty
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AccessGroup createPostRight = AccessGroup.VISITORS;

    @RemoteProperty
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AccessGroup createPollRight = AccessGroup.OWNER;

    @RemoteProperty
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AccessGroup voteInPollRight = AccessGroup.VISITORS;

    @RemoteProperty
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AccessGroup manageSubForumsRight = AccessGroup.OWNER;
    
    @RemoteProperty
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AccessGroup managePostsRight = AccessGroup.OWNER;

    @RemoteProperty
    private boolean allowPolls;

    @RemoteProperty
    private boolean allowSubForums;

}
