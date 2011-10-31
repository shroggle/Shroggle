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

import com.shroggle.entity.DraftForum;
import com.shroggle.entity.Site;
import com.shroggle.entity.SubForum;
import com.shroggle.entity.WidgetItem;
import com.shroggle.exception.ForumNameNotUniqueException;
import com.shroggle.exception.ForumNotFoundException;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.FunctionalWidgetInfo;
import com.shroggle.presentation.site.GetFunctionalWidgetsService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameterProperty;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.Date;

@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class, params = {})
public class EditForumService extends AbstractService {

    @SynchronizeByMethodParameterProperty(
            method = SynchronizeMethod.WRITE,
            entityIdPropertyPath = "forumId",
            entityClass = DraftForum.class)
    @RemoteMethod
    public FunctionalWidgetInfo execute(final EditForumRequest request) throws Exception {
        final UserManager userManager = new UsersManager().getLogined();

        final WidgetItem widget;
        if (request.getWidgetId() != null) {
            widget = (WidgetItem) userManager.getRight().getSiteRight().getWidgetForEditInPresentationalService(
                    request.getWidgetId());
        } else {
            widget = null;
        }

        checkRequest(request);

        persistanceTransaction.execute(new Runnable() {
            public void run() {
                final DraftForum forum = persistance.getDraftItem(request.getForumId());
                if (forum == null || forum.getSiteId() <= 0) {
                    throw new ForumNotFoundException("Cannot find forum by Id=" + request.getForumId());
                }

                final Site site;
                if (widget != null) {
                    site = widget.getSite();
                } else {
                    site = persistance.getSite(forum.getSiteId());
                }

                final DraftForum duplicateForum = persistance.getForumByNameAndSiteId(request.getNewForumName(), site.getSiteId());
                if (duplicateForum != null && duplicateForum != forum) {
                    throw new ForumNameNotUniqueException("Forum name " + request.getNewForumName() + " not unique!");
                }

                forum.setName(request.getNewForumName());
                forum.setCreateSubForumRight(request.getCreateSubForumRight());
                forum.setCreateThreadRight(request.getCreateThreadRight());
                forum.setCreatePostRight(request.getCreatePostRight());
                forum.setCreatePollRight(request.getCreatePollRight());
                forum.setVoteInPollRight(request.getVoteInPollRight());
                forum.setManageSubForumsRight(request.getManageSubForumsRight());
                forum.setManagePostsRight(request.getManagePostsRight());
                forum.setAllowPolls(request.isAllowPolls());
                forum.setAllowSubForums(request.isAllowSubForums());

                if (!forum.isAllowSubForums() && persistance.getSubForumsByForumId(forum.getId()).isEmpty()) {
                    final SubForum subForum = new SubForum();
                    subForum.setSubForumName(forum.getName());
                    subForum.setDateCreated(new Date());
                    subForum.setSubForumDescription("Default subforum created by system");
                    forum.addSubForum(subForum);
                    persistance.putSubForum(subForum);
                }
            }
        });

        return new GetFunctionalWidgetsService().createFunctionalWidgetInfo(widget, "widget", true);
    }

    private void checkRequest(final EditForumRequest request) {
        if (request.getCreatePollRight() == null) {
            throw new IllegalArgumentException("Create Poll Right cannot be null.");
        }

        if (request.getCreatePostRight() == null) {
            throw new IllegalArgumentException("Create Post Right cannot be null.");
        }

        if (request.getCreateSubForumRight() == null) {
            throw new IllegalArgumentException("Create Sub Forum Right cannot be null.");
        }

        if (request.getCreateThreadRight() == null) {
            throw new IllegalArgumentException("Create Thread Right cannot be null.");
        }

        if (request.getManagePostsRight() == null) {
            throw new IllegalArgumentException("Manage Posts Right cannot be null.");
        }

        if (request.getManageSubForumsRight() == null) {
            throw new IllegalArgumentException("Manage Sub Forum Right cannot be null.");
        }

        if (request.getVoteInPollRight() == null) {
            throw new IllegalArgumentException("Vote In Poll Right cannot be null.");
        }
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}