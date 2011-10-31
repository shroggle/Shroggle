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
package com.shroggle.logic.forum;

import com.shroggle.presentation.forum.*;
import com.shroggle.exception.UnknownForumDispatchTypeException;
import com.shroggle.exception.MissingDispatchParameterException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

/**
 * @author dmitry.solomadin
 */
public class ForumDispatchHelper {

    public ForumDispatchHelper(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    private ForumDispatchHelper() {
    }

    public String dispatch(final ForumDispatchType dispatch, final Integer widgetId, final Integer forumId,
                           final Integer subForumId, final Integer threadId, final Integer postId,
                           final boolean showOnUserPages, final boolean draftPostEdit) throws IOException, ServletException {
        final ForumDispatchRequest request = new ForumDispatchRequest();
        request.setDispatchForum(dispatch);
        request.setWidgetId(widgetId);
        request.setForumId(forumId);
        request.setSubForumId(subForumId);
        request.setThreadId(threadId);
        request.setPostId(postId);
        request.setShowOnUserPages(showOnUserPages);
        request.setDraftPostEdit(draftPostEdit);
        return dispatch(request);
    }

    public String dispatch(final ForumDispatchRequest request) throws IOException, ServletException {
        if (request.getWidgetId() == null) {
            throw new MissingDispatchParameterException("Cannot find parameter <widgetId> in dispatch request.");
        }

        if (request.isShowOnUserPages() == null){
            throw new MissingDispatchParameterException("Cannot find parameter <showOnUserPages> in dispatch request."); 
        }

        if (request.getDispatchForum() == ForumDispatchType.SHOW_FORUM) {
            final ShowForumService showForumService = new ShowForumService();

            return showForumService.execute(request.getForumId(), request.getWidgetId(),
                    request.isShowOnUserPages());
        } else if (request.getDispatchForum() == ForumDispatchType.SHOW_SUBFORUM) {
            final ShowSubForumService showSubForumService = new ShowSubForumService();

            return showSubForumService.execute(request.getSubForumId(), request.getWidgetId(),
                    request.isShowOnUserPages());
        }  else if (request.getDispatchForum() == ForumDispatchType.SHOW_THREAD) {
            final ShowThreadService showThreadService = new ShowThreadService();

            return showThreadService.execute(request.getThreadId(), request.getWidgetId(),
                    request.isShowOnUserPages());
        } else if (request.getDispatchForum() == ForumDispatchType.SHOW_CREATE_SUBFORUM) {
            final CreateSubForumService createSubForumService = new CreateSubForumService();
            createSubForumService.setHttpServletRequest(httpServletRequest);

            return createSubForumService.showCreateSubForumForm(request.getForumId(), request.getWidgetId(),
                    request.isShowOnUserPages());
        } else if (request.getDispatchForum() == ForumDispatchType.SHOW_RENAME_SUBFORUM) {
            final CreateSubForumService createSubForumService = new CreateSubForumService();
            createSubForumService.setHttpServletRequest(httpServletRequest);

            return createSubForumService.showRenameSubForumForm(request.getSubForumId(), request.getWidgetId(),
                    request.isShowOnUserPages());
        } else if (request.getDispatchForum() == ForumDispatchType.SHOW_CREATE_THREAD) {
            final CreateThreadService createThreadService = new CreateThreadService();
            createThreadService.setRequest(httpServletRequest);

            return createThreadService.showCreateThreadForm(request.getSubForumId(), request.getWidgetId(),
                    request.isShowOnUserPages());
        } else if (request.getDispatchForum() == ForumDispatchType.SHOW_CREATE_POLL) {
            final CreateThreadService createThreadService = new CreateThreadService();
            createThreadService.setRequest(httpServletRequest);

            return createThreadService.showCreatePollForm(request.getSubForumId(), request.getWidgetId(),
                    request.isShowOnUserPages());
        } else if (request.getDispatchForum() == ForumDispatchType.SHOW_RENAME_THREAD) {
            final CreateThreadService createThreadService = new CreateThreadService();
            createThreadService.setRequest(httpServletRequest);

            return createThreadService.showRenameThreadForm(request.getThreadId(), request.getWidgetId(),
                    request.isShowOnUserPages());
        } else if (request.getDispatchForum() == ForumDispatchType.SHOW_RENAME_POLL) {
            final CreateThreadService createThreadService = new CreateThreadService();
            createThreadService.setRequest(httpServletRequest);

            return createThreadService.showRenamePollForm(request.getThreadId(), request.getWidgetId(),
                    request.isShowOnUserPages());
        } else if (request.getDispatchForum() == ForumDispatchType.SHOW_CREATE_POST) {
            final CreatePostService createPostService = new CreatePostService();
            createPostService.setHttpServletRequest(httpServletRequest);

            return createPostService.showCreatePostForm(false, false, request.getThreadId(), 0, false,
                    request.getWidgetId(), request.isShowOnUserPages());
        } else if (request.getDispatchForum() == ForumDispatchType.SHOW_EDIT_POST) {
            final CreatePostService createPostService = new CreatePostService();
            createPostService.setHttpServletRequest(httpServletRequest);

            return createPostService.showCreatePostForm(!request.isDraftPostEdit(), false, request.getThreadId(),
                    request.getPostId(), request.isDraftPostEdit(), request.getWidgetId(), request.isShowOnUserPages());
        } else if (request.getDispatchForum() == ForumDispatchType.SHOW_QUOTE_POST) {
            final CreatePostService createPostService = new CreatePostService();
            createPostService.setHttpServletRequest(httpServletRequest);

            return createPostService.showCreatePostForm(false, true, request.getThreadId(), request.getPostId(),
                    false, request.getWidgetId(), request.isShowOnUserPages());
        } else {
            throw new UnknownForumDispatchTypeException("Unknown forum dispatch type: " + request.getDispatchForum());
        }
    }

    public static String extractForumDispatchParameters(final Map<String, String[]> parameterMap) {
        String forumParameters = "";

        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            if (entry.getKey().equals("dispatchForum")) {
                forumParameters += "&dispatchForum=" + entry.getValue()[0];
            } else if (entry.getKey().equals("subForumId")) {
                forumParameters += "&subForumId=" + entry.getValue()[0];
            } else if (entry.getKey().equals("threadId")) {
                forumParameters += "&threadId=" + entry.getValue()[0];
            } else if (entry.getKey().equals("postId")) {
                forumParameters += "&postId=" + entry.getValue()[0];
            } else if (entry.getKey().equals("draftPostEdit")) {
                forumParameters += "&draftPostEdit=" + entry.getValue()[0];
            } else if (entry.getKey().equals("widgetId")) {
                forumParameters += "&widgetId=" + entry.getValue()[0];
            }
        }

        return forumParameters;
    }

    private HttpServletRequest httpServletRequest;

}
