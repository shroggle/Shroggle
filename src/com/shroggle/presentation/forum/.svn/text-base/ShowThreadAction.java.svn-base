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

import com.shroggle.entity.ForumThread;
import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.entity.Widget;
import com.shroggle.exception.CannotFindThreadException;
import com.shroggle.logic.forum.ForumRightsManager;
import com.shroggle.logic.site.AccessGroupManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ActionUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.persistance.Persistance;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/forum/showThread.action")
public class ShowThreadAction extends Action {

    private SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
    private Persistance persistance = ServiceLocator.getPersistance();
    //Initialized by true if user already voted for this thread(ForumThreadVotingService)
    public int threadId;

    public boolean alreadyVoted;
    private ForumThread forumThread;
    private boolean isPoll;
    public boolean isShowOnUserPages;

    private boolean createSubForumRight;
    private boolean createThreadRight;
    private boolean createPostRight;
    private boolean createPollRight;
    private boolean voteInPollRight;
    private boolean manageSubFroumsRight;
    private boolean managePostsRight;

    private boolean shouldShowRegisterLinks;

    public int widgetId;

    private Integer activePageVisitorId;
    private Integer loginedVisitorId;

    @DefaultHandler
    public Resolution execute() {
        activePageVisitorId = ActionUtil.getPageVisitorId(getContext().getRequest().getCookies());

        final User user = new UsersManager().getLoginedUser();
        loginedVisitorId = user != null ? user.getUserId() : null;

        forumThread = persistance.getForumThreadById(threadId);

        if (forumThread == null) {
            throw new CannotFindThreadException("Cannot find thread by Id=" + threadId);
        }


        final Widget widget = persistance.getWidget(widgetId);

        final Site site;
        if (widget != null) {
            site = widget.getSite();
        } else {
            site = persistance.getSite(forumThread.getSubForum().getForum().getSiteId());
        }

        if (forumThread == null) {
            throw new CannotFindThreadException("Cannot find thread with Id=" + threadId);
        }

        //Checking if user already voted in this thread
        String respondedntId = String.valueOf(user != null ? user.getUserId() : null);
        if (respondedntId.isEmpty() || respondedntId.equals("null")) {
            respondedntId = getContext().getRequest().getRemoteAddr();
        }
        if (persistance.getForumThreadVoteByRespondentIdAndThreadId(respondedntId, threadId) != null) {
            alreadyVoted = true;
        }

        createSubForumRight = AccessGroupManager.isUserFitsForAccessGroup(forumThread.getSubForum().getForum().getCreateSubForumRight(),
                user, site);
        createThreadRight = AccessGroupManager.isUserFitsForAccessGroup(forumThread.getSubForum().getForum().getCreateThreadRight(),
                user, site);
        createPostRight = AccessGroupManager.isUserFitsForAccessGroup(forumThread.getSubForum().getForum().getCreatePostRight(),
                user, site);
        createPollRight = AccessGroupManager.isUserFitsForAccessGroup(forumThread.getSubForum().getForum().getCreatePollRight(),
                user, site);
        voteInPollRight = AccessGroupManager.isUserFitsForAccessGroup(forumThread.getSubForum().getForum().getVoteInPollRight(),
                user, site);
        manageSubFroumsRight = AccessGroupManager.isUserFitsForAccessGroup(forumThread.getSubForum().getForum().getManageSubForumsRight(),
                user, site);
        managePostsRight = AccessGroupManager.isUserFitsForAccessGroup(forumThread.getSubForum().getForum().getManagePostsRight(),
                user, site);

        isPoll = forumThread.getPollQuestion() != null;

        shouldShowRegisterLinks = ForumRightsManager.isShouldShowRegisterLinks(user, forumThread.getSubForum().getForum());

        return new ForwardResolution("/forum/showThread.jsp");
    }

    public boolean isShouldShowRegisterLinks() {
        return shouldShowRegisterLinks;
    }

    public Integer getLoginedVisitorId() {
        return loginedVisitorId;
    }

    public void setLoginedVisitorId(int loginedVisitorId) {
        this.loginedVisitorId = loginedVisitorId;
    }

    public boolean isManageSubFroumsRight() {
        return manageSubFroumsRight;
    }

    public boolean isManagePostsRight() {
        return managePostsRight;
    }

    public boolean isCreateSubForumRight() {
        return createSubForumRight;
    }

    public boolean isCreateThreadRight() {
        return createThreadRight;
    }

    public boolean isCreatePostRight() {
        return createPostRight;
    }

    public boolean isCreatePollRight() {
        return createPollRight;
    }

    public boolean isVoteInPollRight() {
        return voteInPollRight;
    }

    public boolean isPoll() {
        return isPoll;
    }

    public ForumThread getForumThread() {
        return forumThread;
    }

    public Persistance getPersistance() {
        return persistance;
    }

    public Integer getActivePageVisitorId() {
        return activePageVisitorId;
    }
}
