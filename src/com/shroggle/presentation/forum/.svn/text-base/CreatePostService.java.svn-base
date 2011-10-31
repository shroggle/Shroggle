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

import com.shroggle.entity.ForumPost;
import com.shroggle.entity.ForumThread;
import com.shroggle.entity.PageVisitor;
import com.shroggle.entity.User;
import com.shroggle.exception.CannotCreatePostInClosedThread;
import com.shroggle.exception.CannotFindPostException;
import com.shroggle.exception.CannotFindThreadException;
import com.shroggle.exception.ForumPostWithNullOrEmptyText;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.ActionUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import org.directwebremoting.WebContext;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;


@RemoteProxy
public class CreatePostService extends AbstractService {

    private Persistance persistance = ServiceLocator.getPersistance();
    protected PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private HttpServletRequest httpServletRequest;

    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    private ForumThread forumThread;

    private ForumPost forumPost;

    private boolean edit;
    private boolean quote;
    private boolean draftEdit;

    public int widgetId;
    public boolean isShowOnUserPages;

    //Set postIdToEdit to 0 if creating new post.
    @RemoteMethod
    public String showCreatePostForm(boolean edit, boolean quote, int threadId, int postIdToEdit, boolean draftEdit, final int widgetId, final boolean isShowOnUserPages)
            throws ServletException, IOException {
        forumThread = persistance.getForumThreadById(threadId);

        if (forumThread == null) {
            throw new CannotFindThreadException("Cannot find thread with ID=" + threadId);
        }

        if (edit || quote || draftEdit) {
            forumPost = persistance.getForumPostById(postIdToEdit);

            if (forumPost == null) {
                throw new CannotFindPostException("Cannot find post with ID=" + postIdToEdit);
            }
        }

        if (edit) {
            this.edit = true;
        }
        if (quote) {
            this.quote = true;
        }
        if (draftEdit) {
            this.draftEdit = true;
        }

        this.isShowOnUserPages = isShowOnUserPages;
        this.widgetId = widgetId;

        final WebContext webContext = getContext();
        if (webContext != null) {
            webContext.getHttpServletRequest().setAttribute("service", this);
            return getContext().forwardToString("/forum/showCreatePostForm.jsp");
        } else {
            httpServletRequest.setAttribute("service", this);
            return "/forum/showCreatePostForm.jsp";
        }
    }

    @RemoteMethod
    public String deletePost(final int threadId, int postId, final int widgetId, final boolean isShowOnUserPages)
            throws ServletException, IOException {
        final ForumPost forumPost = persistance.getForumPostById(postId);
        if (forumPost == null) {
            throw new CannotFindPostException("Cannot find post with Id=" + postId);
        }

        if (persistance.getForumThreadById(threadId) == null) {
            throw new CannotFindThreadException("Cannot find thread with Id=" + threadId);
        }

        persistanceTransaction.execute(new Runnable() {
            public void run() {
                persistance.removePost(forumPost);

                forumPost.getThread().removeForumPost(forumPost);
            }
        });

        final WebContext webContext = getContext();
        return webContext.forwardToString
                ("/forum/showThread.action?" +
                        "threadId=" + threadId + "&widgetId=" + widgetId + "&isShowOnUserPages=" + isShowOnUserPages);
    }

    @RemoteMethod
    public String execute(final String postText, final int threadId, final int widgetId, final boolean isShowOnUserPages) throws ServletException, IOException {
        final Integer pageVisitorId = ActionUtil.getPageVisitorId(getContext().getHttpServletRequest().getCookies());

        if (postText == null || postText.trim().isEmpty()) {
            throw new ForumPostWithNullOrEmptyText();
        }

        //Creating new post in transaction and updating thread that new post created
        persistanceTransaction.execute(new Runnable() {
            public void run() {
                ForumPost post = new ForumPost();

                post.setText(postText);
                ForumThread forumThread = persistance.getForumThreadById(threadId);
                if (forumThread == null) {
                    throw new CannotFindThreadException("Cannot find thread with Id=" + threadId);
                }
                if (forumThread.isClosed()) {
                    throw new CannotCreatePostInClosedThread();
                }

                post.setThread(forumThread);

                User user = new UsersManager().getLoginedUser();

                PageVisitor pageVisitor = null;
                if (pageVisitorId != null) {
                    pageVisitor = persistance.getPageVisitorById(pageVisitorId);
                }

                post.setAuthor(user);
                post.setPageVisitor(pageVisitor);

                persistance.putForumPost(post);

                forumThread.addForumPost(post);
                forumThread.setUpdateDate(new Date());
            }
        });

        final WebContext webContext = getContext();
        return webContext.forwardToString("/forum/showThread.action?" +
                "threadId=" + threadId + "&widgetId=" + widgetId + "&isShowOnUserPages=" + isShowOnUserPages);
    }

    @RemoteMethod
    public String saveEditedPost(final String postText, final int postId, final int widgetId, final boolean isShowOnUserPages) throws ServletException, IOException {
        if (postText == null || postText.isEmpty()) {
            throw new ForumPostWithNullOrEmptyText();
        }

        final ForumPost post = persistance.getForumPostById(postId);

        if (post == null) {
            throw new CannotFindPostException("Cannot find post with Id=" + postId);
        }

        final ForumThread forumThread = post.getThread();

        if (forumThread == null) {
            throw new CannotFindThreadException("Cannot find thread");
        }
        if (forumThread.isClosed()) {
            throw new CannotCreatePostInClosedThread();
        }

        persistanceTransaction.execute(new Runnable() {
            public void run() {
                post.setText(postText);
            }
        });

        final WebContext webContext = getContext();
        return webContext.forwardToString("/forum/showThread.action?" +
                "threadId=" + forumThread.getThreadId() + "&widgetId=" + widgetId + "&isShowOnUserPages=" + isShowOnUserPages);
    }

    @RemoteMethod
    public String previewNewPost(final String postText, final int threadId, final int widgetId, final boolean isShowOnUserPages) throws ServletException, IOException {
        final Integer pageVisitorId = ActionUtil.getPageVisitorId(getContext().getHttpServletRequest().getCookies());

        if (postText == null || postText.trim().isEmpty()) {
            throw new ForumPostWithNullOrEmptyText();
        }

        final ForumThread forumThread = persistance.getForumThreadById(threadId);
        if (forumThread == null) {
            throw new CannotFindThreadException("Cannot find thread with Id=" + threadId);
        }
        if (forumThread.isClosed()) {
            throw new CannotCreatePostInClosedThread();
        }

        persistanceTransaction.execute(new Runnable() {
            public void run() {
                ForumPost post = new ForumPost();

                post.setDraftText(postText);

                post.setThread(forumThread);

                final User user = new UsersManager().getLoginedUser();

                PageVisitor pageVisitor = null;
                if (pageVisitorId != null) {
                    pageVisitor = persistance.getPageVisitorById(pageVisitorId);
                }

                post.setAuthor(user);
                post.setPageVisitor(pageVisitor);

                persistance.putForumPost(post);
                forumThread.addForumPost(post);
                forumThread.setUpdateDate(new Date());
            }
        });

        final WebContext webContext = getContext();
        return webContext.forwardToString("/forum/showThread.action?" +
                "threadId=" + forumThread.getThreadId() + "&widgetId=" + widgetId + "&isShowOnUserPages=" + isShowOnUserPages);
    }

    @RemoteMethod
    public String previewEditedPost(final String postText, final int postId, final int widgetId, final boolean isShowOnUserPages) throws ServletException, IOException {
        if (postText == null || postText.isEmpty()) {
            throw new ForumPostWithNullOrEmptyText();
        }

        final ForumPost post = persistance.getForumPostById(postId);

        if (post == null) {
            throw new CannotFindPostException("Cannot find post with Id=" + postId);
        }

        final ForumThread forumThread = post.getThread();

        if (forumThread == null) {
            throw new CannotFindThreadException("Cannot find thread");
        }
        if (forumThread.isClosed()) {
            throw new CannotCreatePostInClosedThread();
        }

        persistanceTransaction.execute(new Runnable() {
            public void run() {
                post.setDraftText(postText);
            }

        });

        final WebContext webContext = getContext();
        return webContext.forwardToString("/forum/showThread.action?" +
                "threadId=" + forumThread.getThreadId() + "&widgetId=" + widgetId + "&isShowOnUserPages=" + isShowOnUserPages);
    }

    @RemoteMethod
    public String submitDraftEditedPost(final String postText, final int postId, final int widgetId, final boolean isShowOnUserPages) throws ServletException, IOException {
        if (postText == null || postText.isEmpty()) {
            throw new ForumPostWithNullOrEmptyText();
        }

        final ForumPost post = persistance.getForumPostById(postId);

        if (post == null) {
            throw new CannotFindPostException("Cannot find post with Id=" + postId);
        }

        final ForumThread forumThread = post.getThread();

        if (forumThread == null) {
            throw new CannotFindThreadException("Cannot find thread");
        }
        if (forumThread.isClosed()) {
            throw new CannotCreatePostInClosedThread();
        }

        persistanceTransaction.execute(new Runnable() {
            public void run() {
                post.setText(postText);
                post.setDraftText(null);
            }

        });

        final WebContext webContext = getContext();
        return webContext.forwardToString("/forum/showThread.action?" +
                "threadId=" + forumThread.getThreadId() + "&widgetId=" + widgetId + "&isShowOnUserPages=" + isShowOnUserPages);
    }

    @RemoteMethod
    public String submitDraftPost(final int postId, final int widgetId, final boolean isShowOnUserPages) throws ServletException, IOException {
        final ForumPost post = persistance.getForumPostById(postId);

        if (post == null) {
            throw new CannotFindPostException("Cannot find post with Id=" + postId);
        }

        final ForumThread forumThread = post.getThread();

        if (forumThread == null) {
            throw new CannotFindThreadException("Cannot find thread");
        }
        if (forumThread.isClosed()) {
            throw new CannotCreatePostInClosedThread();
        }

        persistanceTransaction.execute(new Runnable() {
            public void run() {
                post.setText(post.getDraftText());
                post.setDraftText(null);
            }
        });

        final WebContext webContext = getContext();
        return webContext.forwardToString("/forum/showThread.action?" +
                "threadId=" + forumThread.getThreadId() + "&widgetId=" + widgetId + "&isShowOnUserPages=" + isShowOnUserPages);
    }

    @RemoteMethod
    public String discardDraftPost(final int postId, final int widgetId, final boolean isShowOnUserPages) throws ServletException, IOException {
        final ForumPost post = persistance.getForumPostById(postId);

        if (post == null) {
            throw new CannotFindPostException("Cannot find post with Id=" + postId);
        }

        final ForumThread forumThread = post.getThread();

        if (forumThread == null) {
            throw new CannotFindThreadException("Cannot find thread");
        }
        if (forumThread.isClosed()) {
            throw new CannotCreatePostInClosedThread();
        }

        persistanceTransaction.execute(new Runnable() {
            public void run() {
                if (post.getText() != null) {
                    post.setDraftText(null);
                } else {
                    persistance.removePost(post);
                }
            }
        });

        final WebContext webContext = getContext();
        return webContext.forwardToString("/forum/showThread.action?" +
                "threadId=" + forumThread.getThreadId() + "&widgetId=" + widgetId + "&isShowOnUserPages=" + isShowOnUserPages);
    }

    public ForumThread getForumThread() {
        return forumThread;
    }

    public ForumPost getForumPost() {
        return forumPost;
    }

    public boolean isEdit() {
        return edit;
    }

    public boolean isQuote() {
        return quote;
    }

    public boolean isDraftEdit() {
        return draftEdit;
    }
}
