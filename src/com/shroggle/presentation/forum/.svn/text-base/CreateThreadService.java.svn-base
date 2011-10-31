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
import com.shroggle.exception.CannotFindThreadException;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.ActionUtil;
import com.shroggle.presentation.site.render.ShowPageVersionUrlGetter;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import org.directwebremoting.WebContext;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RemoteProxy
public class CreateThreadService extends AbstractService {

    @RemoteMethod
    public String showCreateThreadForm(int subForumId, int widgetId, boolean isShowOnUserPages)
            throws ServletException, IOException {
        this.subForum = persistance.getSubForumById(subForumId);
        this.forumId = subForum.getForum().getId();
        this.widgetId = widgetId;
        this.isShowOnUserPages = isShowOnUserPages;
        this.showPageVersionUrl = ShowPageVersionUrlGetter.get(httpServletRequest);

        final WebContext webContext = getContext();
        if (webContext != null) {
            webContext.getHttpServletRequest().setAttribute("service", this);
            return getContext().forwardToString("/forum/showCreateThreadForm.jsp");
        } else {
            httpServletRequest.setAttribute("service", this);
            return "/forum/showCreateThreadForm.jsp";
        }
    }

    @RemoteMethod
    public String showCreatePollForm(int subForumId, int widgetId, boolean isShowOnUserPages)
            throws ServletException, IOException {
        this.subForum = persistance.getSubForumById(subForumId);
        this.forumId = subForum.getForum().getId();
        this.widgetId = widgetId;
        this.isShowOnUserPages = isShowOnUserPages;

        final WebContext webContext = getContext();
        if (webContext != null) {
            webContext.getHttpServletRequest().setAttribute("service", this);
            return webContext.forwardToString
                    ("/forum/showCreateThreadForm.jsp?isPoll=" + true);
        } else {
            httpServletRequest.setAttribute("service", this);
            return "/forum/showCreateThreadForm.jsp?isPoll=" + true;
        }
    }

    @RemoteMethod
    public String showRenameThreadForm(int threadId, int widgetId, boolean isShowOnUserPages)
            throws ServletException, IOException {
        this.renamedThread = persistance.getForumThreadById(threadId);
        this.subForum = renamedThread.getSubForum();
        this.forumId = subForum.getForum().getId();
        this.widgetId = widgetId;
        this.isShowOnUserPages = isShowOnUserPages;

        final WebContext webContext = getContext();
        if (webContext != null) {
            webContext.getHttpServletRequest().setAttribute("service", this);
            return getContext().forwardToString("/forum/showCreateThreadForm.jsp");
        } else {
            httpServletRequest.setAttribute("service", this);
            return "/forum/showCreateThreadForm.jsp";
        }
    }

    @RemoteMethod
    public String showRenamePollForm(int threadId, int widgetId, boolean isShowOnUserPages)
            throws ServletException, IOException {
        this.renamedThread = persistance.getForumThreadById(threadId);
        this.subForum = renamedThread.getSubForum();
        this.forumId = subForum.getForum().getId();
        this.widgetId = widgetId;
        this.isShowOnUserPages = isShowOnUserPages;

        final WebContext webContext = getContext();
        if (webContext != null) {
            webContext.getHttpServletRequest().setAttribute("service", this);
            return webContext.forwardToString
                    ("/forum/showCreateThreadForm.jsp?isPoll=" + true);
        } else {
            httpServletRequest.setAttribute("service", this);
            return "/forum/showCreateThreadForm.jsp?isPoll=" + true;
        }
    }


    @RemoteMethod
    public String rename(final RenameThreadRequest renameThreadRequest, final int widgetId, final boolean isShowOnUserPages)
            throws ServletException, IOException {
        final WebContext webContext = getContext();

        if ((renameThreadRequest.getThreadName() == null) || (renameThreadRequest.getThreadName().isEmpty())) {
            return "EmptyThreadNameException";
        }

        final ForumThread thread = persistance.getForumThreadById(renameThreadRequest.getThreadId());

        if (thread == null) {
            throw new CannotFindThreadException("Cannot find thread with ID=" + renameThreadRequest.getThreadId());
        }

        final SubForum subForum = thread.getSubForum();

        if (subForum == null) {
            throw new CannotFindSubForumException("Cannot find subForum in thread with Id=" + renameThreadRequest.getThreadId());
        }

        persistanceTransaction.execute(new Runnable() {
            public void run() {
                thread.setThreadName(renameThreadRequest.getThreadName());
                thread.setThreadDescription(renameThreadRequest.getThreadDescription());

                if (renameThreadRequest.isPoll()) {
                    thread.setPollQuestion(renameThreadRequest.getPollQuestion());
                    thread.setPollAnswers(renameThreadRequest.getPollAnswers());
                }
            }
        });

        return webContext.forwardToString
                ("/forum/showSubForum.action?" +
                        "subForumId=" + subForum.getSubForumId() + "&widgetId=" + widgetId + "&isShowOnUserPages=" + isShowOnUserPages);
    }

    @RemoteMethod
    public String deleteThread(int threadId, int widgetId, boolean isShowOnUserPages)
            throws ServletException, IOException {
        final WebContext webContext = getContext();
        final ForumThread thread = persistance.getForumThreadById(threadId);

        if (thread == null) {
            throw new CannotFindThreadException("Cannot find thread with Id =" + threadId);
        }

        final SubForum subForum = thread.getSubForum();

        if (subForum == null) {
            throw new CannotFindSubForumException("Cannot find subforum in thread with id" + threadId);
        }

        final ForumThread forumThread = persistance.getForumThreadById(threadId);

        persistanceTransaction.execute(new Runnable() {

            public void run() {
                persistance.removeThread(forumThread);

                SubForum subForum = forumThread.getSubForum();
                subForum.removeForumThread(forumThread);
            }

        });

        return webContext.forwardToString
                ("/forum/showSubForum.action?" +
                        "subForumId=" + subForum.getSubForumId() + "&widgetId=" + widgetId + "&isShowOnUserPages=" + isShowOnUserPages);
    }

    @RemoteMethod
    public String closeThread(final int subForumId, final int threadId, final int widgetId, final boolean isShowOnUserPages)
            throws ServletException, IOException {
        final WebContext webContext = getContext();

        final ForumThread forumThread = persistance.getForumThreadById(threadId);

        if (forumThread == null) {
            throw new CannotFindThreadException("Cannot find thread with Id=" + threadId);
        }

        persistanceTransaction.execute(new Runnable() {
            public void run() {
                forumThread.setClosed(true);
            }
        });

        return webContext.forwardToString
                ("/forum/showSubForum.action?" +
                        "subForumId=" + subForumId + "&widgetId=" + widgetId + "&isShowOnUserPages=" + isShowOnUserPages);
    }

    @RemoteMethod
    public String openThread(final int subForumId, final int threadId, final int widgetId, final boolean isShowOnUserPages)
            throws ServletException, IOException {
        final ForumThread forumThread = persistance.getForumThreadById(threadId);
        if (forumThread == null) {
            throw new CannotFindThreadException("Cannot find thread with Id " + threadId);
        }

        persistanceTransaction.execute(new Runnable() {

            public void run() {
                forumThread.setClosed(false);
            }

        });

        return getContext().forwardToString(
                "/forum/showSubForum.action?subForumId=" + subForumId + "&widgetId="
                        + widgetId + "&isShowOnUserPages=" + isShowOnUserPages);
    }

    @RemoteMethod
    public String execute(
            final CreateThreadRequest request, final int subForumId,
            final int widgetId, final boolean isShowOnUserPages) throws ServletException, IOException {
        final Integer pageVisitorId = ActionUtil.getPageVisitorId(getContext().getHttpServletRequest().getCookies());

        if (request.getThreadName() == null || request.getThreadName().trim().isEmpty()) {
            return "ThreadWithNullOrEmptyName";
        }

        if (request.getForumPostText() == null || request.getForumPostText().trim().isEmpty()) {
            return "ForumPostWithNullOrEmptyText";
        }

        if (request.isPoll()) {
            if (request.getPollAnswers() == null || request.getPollAnswers().size() <= 1) {
                return "PollWithLessThanTwoAnswersException";
            }

            if (request.getPollQuestion() == null || request.getPollQuestion().isEmpty()) {
                return "PollWithoutQuestionException";
            }
        }

        //Creating new thread in transaction
        persistanceTransaction.execute(new Runnable() {

            public void run() {
                final ForumThread thread = new ForumThread();
                thread.setThreadName(request.getThreadName());
                thread.setThreadDescription(request.getThreadDescription());

                final User user = new UsersManager().getLoginedUser();

                thread.setAuthor(user);
                thread.setSubForum(persistance.getSubForumById(subForumId));
                thread.setUpdateDate(new Date());

                if (request.isPoll()) {
                    thread.setPollQuestion(request.getPollQuestion());
                    thread.setPollAnswers(request.getPollAnswers());
                }

                persistance.putForumThread(thread);

                newThreadId = thread.getThreadId();
            }

        });

        //Creating new post in just created thread
        persistanceTransaction.execute(new Runnable() {

            public void run() {
                final ForumPost post = new ForumPost();
                post.setText(request.getForumPostText());
                post.setThread(persistance.getForumThreadById(newThreadId));

                final User user = new UsersManager().getLoginedUser();

                PageVisitor pageVisitor = null;
                if (pageVisitorId != null) {
                    pageVisitor = persistance.getPageVisitorById(pageVisitorId);
                }

                post.setAuthor(user);
                post.setPageVisitor(pageVisitor);

                persistance.putForumPost(post);

                ForumThread thread = persistance.getForumThreadById(newThreadId);
                thread.addForumPost(post);
                thread.setUpdateDate(new Date());
            }

        });

        return getContext().forwardToString(
                "/forum/showThread.action?threadId=" + newThreadId
                        + "&widgetId=" + widgetId + "&isShowOnUserPages=" + isShowOnUserPages);
    }

    @RemoteMethod
    public boolean checkIfAnswerIsVoted(final int answerNumber) throws ServletException, IOException {
        final List<ForumPollVote> pollVotes = renamedThread.getPollVotes();

        for (final ForumPollVote forumPollVote : pollVotes) {
            if (forumPollVote.getAnswerNumber().equals(answerNumber)) {
                return true;
            }
        }
        return false;
    }

    public void setRequest(final HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public int getNewThreadId() {
        return newThreadId;
    }

    public SubForum getSubForum() {
        return subForum;
    }

    public void setSubForum(final SubForum subForum) {
        this.subForum = subForum;
    }

    public int getForumId() {
        return forumId;
    }

    public void setForumId(final int forumId) {
        this.forumId = forumId;
    }

    public String getShowPageVersionUrl() {
        return showPageVersionUrl;
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private HttpServletRequest httpServletRequest;
    private SubForum subForum;
    private int forumId;
    public int widgetId;
    public boolean isShowOnUserPages;
    public ForumThread renamedThread;
    private int newThreadId;
    private String showPageVersionUrl;

}
