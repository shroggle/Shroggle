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
import com.shroggle.logic.forum.ForumDispatchHelper;
import com.shroggle.logic.forum.ForumDispatchType;
import com.shroggle.logic.forum.ForumRightsManager;
import com.shroggle.logic.site.AccessGroupManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.*;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.*;

@UrlBinding("/forum/widgetForum.action")
public class WidgetForumAction extends Action {

    @DefaultHandler
    public Resolution execute() throws Exception {
        if (dispatchForum != null) {
            return executeDispatch(showPageVersionUrl);
        }
        loginedUser = new UsersManager().getLoginedUser();

        widget = persistance.getWidget(widgetId);

        // Widget can be null if we are viewing this forum from inside app on admin interface.
        if (widget != null){
            showPageVersionUrl = new PageManager(widget.getPage(), siteShowOption).getUrl();
        } else {
            showPageVersionUrl = "";
        }

        if (siteShowOption == SiteShowOption.OUTSIDE_APP) {
            forum = (Forum) persistance.getWorkItem(forumId);
        } else {
            forum = (Forum) persistance.getDraftItem(forumId);
        }

        final Site site;
        if (widget != null) {
            site = widget.getSite();
        } else {
            site = persistance.getSite(forum.getSiteId());
        }

        lastPosts = new HashMap<SubForum, ForumPost>();
        for (SubForum subForum : persistance.getSubForumsByForumId(forum.getId())) {
            lastPosts.put(subForum, persistance.getLastSubForumPost(subForum.getSubForumId()));
        }

        if (!forum.isAllowSubForums()) {
            //If user set "allow subforums" attribute to false then and this forum has no subforums then create default one
            if (persistance.getSubForumsByForumId(forum.getId()).size() == 0) {
                final SubForum subForum = new SubForum();
                subForum.setForum(persistance.<DraftForum>getDraftItem(forum.getId()));
                subForum.setSubForumName(forum.getName());
                subForum.setDateCreated(new Date());
                subForum.setSubForumDescription("Default subforum created by system");

                final SynchronizeRequest synchronizeRequest = new SynchronizeRequestEntity(
                        User.class, SynchronizeMethod.WRITE, forum);
                synchronize.execute(synchronizeRequest, new SynchronizeContext<Void>() {

                    public Void execute() {
                        persistanceTransaction.execute(new Runnable() {
                            public void run() {
                                persistance.putSubForum(subForum);
//                                forum.addSubForum(subForum);
                            }
                        });
                        return null;
                    }
                });
            }

            //By default, we will redirect user to first subforum.
            return new ForwardResolution("/forum/showSubForum.action?" +
                    "subForumId=" + persistance.getSubForumsByForumId(forum.getId()).get(0).getSubForumId() +
                    "&widgetId=" + widgetId);
        }

        createSubForumRight = AccessGroupManager.isUserFitsForAccessGroup(forum.getCreateSubForumRight(), loginedUser, site);
        createThreadRight = AccessGroupManager.isUserFitsForAccessGroup(forum.getCreateThreadRight(), loginedUser, site);
        createPostRight = AccessGroupManager.isUserFitsForAccessGroup(forum.getCreatePostRight(), loginedUser, site);
        createPollRight = AccessGroupManager.isUserFitsForAccessGroup(forum.getCreatePollRight(), loginedUser, site);
        voteInPollRight = AccessGroupManager.isUserFitsForAccessGroup(forum.getVoteInPollRight(), loginedUser, site);
        manageSubFroumsRight = AccessGroupManager.isUserFitsForAccessGroup(forum.getManageSubForumsRight(), loginedUser, site);
        managePostsRight = AccessGroupManager.isUserFitsForAccessGroup(forum.getManagePostsRight(), loginedUser, site);

        shouldShowRegisterLinks = ForumRightsManager.isShouldShowRegisterLinks(loginedUser, forum);

        return new ForwardResolution("/forum/widgetForum.jsp");
    }

    public Resolution executeDispatch(final String showPageVersionUrl) throws Exception {
        getContext().getRequest().setAttribute("showPageVersionUrl", showPageVersionUrl);
        final ForumDispatchHelper dispatchHelper = new ForumDispatchHelper(getContext().getRequest());
        return new ForwardResolution(dispatchHelper.dispatch(dispatchForum, widgetId, forumId, subForumId,
                threadId, postId, isShowOnUserPages, draftPostEdit));
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

    public Map<SubForum, ForumPost> getLastPosts() {
        return lastPosts;
    }

    public boolean isCreatePollRight() {
        return createPollRight;
    }

    public void setCreatePollRight(boolean createPollRight) {
        this.createPollRight = createPollRight;
    }

    public boolean isVoteInPollRight() {
        return voteInPollRight;
    }

    public void setVoteInPollRight(boolean voteInPollRight) {
        this.voteInPollRight = voteInPollRight;
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(DraftForum forum) {
        this.forum = forum;
    }

    public boolean isCreateSubForumRight() {
        return createSubForumRight;
    }

    public void setCreateSubForumRight(boolean createSubForumRight) {
        this.createSubForumRight = createSubForumRight;
    }

    public boolean isCreateThreadRight() {
        return createThreadRight;
    }

    public void setCreateThreadRight(boolean createThreadRight) {
        this.createThreadRight = createThreadRight;
    }

    public boolean isCreatePostRight() {
        return createPostRight;
    }

    public void setCreatePostRight(boolean createPostRight) {
        this.createPostRight = createPostRight;
    }

    public boolean isManageSubFroumsRight() {
        return manageSubFroumsRight;
    }

    public void setManageSubFroumsRight(boolean manageSubFroumsRight) {
        this.manageSubFroumsRight = manageSubFroumsRight;
    }

    public boolean isManagePostsRight() {
        return managePostsRight;
    }

    public void setManagePostsRight(boolean managePostsRight) {
        this.managePostsRight = managePostsRight;
    }

    public Widget getWidget() {
        return widget;
    }

    public User getLoginedUser() {
        return loginedUser;
    }

    public boolean isShouldShowRegisterLinks() {
        return shouldShowRegisterLinks;
    }

    public String getShowPageVersionUrl() {
        return showPageVersionUrl;
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
    private final Synchronize synchronize = ServiceLocator.getSynchronize();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private Map<SubForum, ForumPost> lastPosts;
    private Forum forum;

    public int forumId;
    public int widgetId;
    private Widget widget;
    public ForumDispatchType dispatchForum;
    public int subForumId;
    public int threadId;
    public int postId;
    public boolean draftPostEdit;
    public boolean isShowOnUserPages;
    public SiteShowOption siteShowOption;
    public int pageId;

    private boolean createSubForumRight;
    private boolean createThreadRight;
    private boolean createPostRight;
    private boolean createPollRight;
    private boolean voteInPollRight;
    private boolean manageSubFroumsRight;
    private boolean managePostsRight;
    private String showPageVersionUrl;
    private boolean shouldShowRegisterLinks;

    private User loginedUser;

}
