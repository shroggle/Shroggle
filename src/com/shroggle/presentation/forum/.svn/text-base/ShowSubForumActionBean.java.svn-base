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

import com.shroggle.entity.*;
import com.shroggle.exception.CannotFindSubForumException;
import com.shroggle.logic.forum.ForumRightsManager;
import com.shroggle.logic.site.AccessGroupManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.site.render.ShowPageVersionUrlGetter;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.*;

@UrlBinding("/forum/showSubForum.action")
public class ShowSubForumActionBean extends Action {

    private final Persistance persistance = ServiceLocator.getPersistance();
    private Map<ForumThread, ForumPost> lastForumThreadsPost;

    public int subForumId;
    public int widgetId;
    public boolean isShowOnUserPages;

    private String showPageVersionUrl;

    private SubForum subForum;

    private boolean createSubForumRight;
    private boolean createThreadRight;
    private boolean createPostRight;
    private boolean createPollRight;
    private boolean voteInPollRight;
    private boolean manageSubFroumsRight;
    private boolean managePostsRight;

    private boolean shouldShowRegisterLinks;
    private Integer loginedVisitorId;

    @DefaultHandler
    public Resolution execute() {
        showPageVersionUrl = ShowPageVersionUrlGetter.get(getContext().getRequest());

        User user = new UsersManager().getLoginedUser();
        loginedVisitorId = user != null ? user.getUserId() : null;
        subForum = persistance.getSubForumById(subForumId);

        if (subForum == null) {
            throw new CannotFindSubForumException("Cannot find subforum by Id = " + subForumId);
        }

        Site site = persistance.getSite(subForum.getForum().getSiteId());
        lastForumThreadsPost = new HashMap<ForumThread, ForumPost>();
        for (ForumThread forumThread : subForum.getForumThreads()) {
            lastForumThreadsPost.put(forumThread, persistance.getLastThreadPost(forumThread.getThreadId()));
        }

        createSubForumRight = AccessGroupManager.isUserFitsForAccessGroup(subForum.getForum().getCreateSubForumRight(), user, site);
        createThreadRight = AccessGroupManager.isUserFitsForAccessGroup(subForum.getForum().getCreateThreadRight(), user, site);
        createPostRight = AccessGroupManager.isUserFitsForAccessGroup(subForum.getForum().getCreatePostRight(), user, site);
        createPollRight = AccessGroupManager.isUserFitsForAccessGroup(subForum.getForum().getCreatePollRight(), user, site);
        voteInPollRight = AccessGroupManager.isUserFitsForAccessGroup(subForum.getForum().getVoteInPollRight(), user, site);
        manageSubFroumsRight = AccessGroupManager.isUserFitsForAccessGroup(subForum.getForum().getManageSubForumsRight(), user, site);
        managePostsRight = AccessGroupManager.isUserFitsForAccessGroup(subForum.getForum().getManagePostsRight(), user, site);

        shouldShowRegisterLinks = ForumRightsManager.isShouldShowRegisterLinks(user, subForum.getForum());

        return new ForwardResolution("/forum/showSubForum.jsp");
    }

    public List<ForumThread> getForumThreads() {
        List<ForumThread> returnList = subForum.getForumThreads();
        Collections.sort(returnList, new ThreadComparator());
        return returnList;
    }

    public List<ForumPost> getForumPosts(ForumThread thread) {
        List<ForumPost> forumPosts = new ArrayList<ForumPost>();
        for (ForumPost forumPost : thread.getForumPosts()) {
            if (forumPost.getDraftText() == null) {
                forumPosts.add(forumPost);
            } else if (forumPost.getText() != null) {
                forumPosts.add(forumPost);
            }
        }
        return forumPosts;
    }

    private class ThreadComparator implements Comparator<ForumThread> {

        public int compare(ForumThread t1, ForumThread t2) {
            return t1.getUpdateDate().compareTo(t2.getUpdateDate());
        }

    }

    public String getShowPageVersionUrl() {
        return showPageVersionUrl;
    }

    public SubForum getSubForum() {
        return subForum;
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

    public Map<ForumThread, ForumPost> getLastForumThreadsPost() {
        return lastForumThreadsPost;
    }

    public boolean isShouldShowRegisterLinks() {
        return shouldShowRegisterLinks;
    }

    public Integer getLoginedVisitorId() {
        return loginedVisitorId;
    }
}
