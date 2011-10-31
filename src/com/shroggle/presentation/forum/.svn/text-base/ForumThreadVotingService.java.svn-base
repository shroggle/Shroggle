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

import com.shroggle.entity.ForumPollVote;
import com.shroggle.entity.ForumThread;
import com.shroggle.entity.User;
import com.shroggle.exception.CannotFindAnswerException;
import com.shroggle.exception.CannotFindThreadException;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;


/**
 * @author Dima
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class, params = {})
public class ForumThreadVotingService extends AbstractService {

    @SynchronizeByMethodParameter(
            entityClass = ForumThread.class,
            method = SynchronizeMethod.WRITE)
    @RemoteMethod
    public String execute(
            final int threadId, final int voteAnswer, final int widgetId,
            final boolean isShowOnUserPages) throws Exception {
        final String respondentId;
        final User user = new UsersManager().getLoginedUser();
        final Integer loginVisitorId = user != null ? user.getUserId() : null;
        if (loginVisitorId != null) {
            respondentId = "" + loginVisitorId;
        } else {
            respondentId = getContext().getHttpServletRequest().getRemoteAddr();
        }

        final ForumThread forumThread = persistance.getForumThreadById(threadId);
        if (forumThread == null) {
            throw new CannotFindThreadException("Cannot find thread with Id=" + threadId);
        }
        if (forumThread.getPollAnswers().size() < voteAnswer || voteAnswer < 0) {
            throw new CannotFindAnswerException("Cannot find answer with number= " + voteAnswer
                    + ". Probably answer number wasn't set properly.");
        }

        //Checking if user already voted in this thread
        if (persistance.getForumThreadVoteByRespondentIdAndThreadId(respondentId, threadId) == null) {
            final ForumPollVote forumPollVote = new ForumPollVote();
            forumPollVote.setRespondentId(respondentId);
            forumPollVote.setAnswerNumber(voteAnswer);
            forumPollVote.setThread(forumThread);

            persistanceTransaction.execute(new Runnable() {

                public void run() {
                    persistance.putVote(forumPollVote);
                    forumThread.addPollVote(forumPollVote);
                }

            });
        }

        return getContext().forwardToString("/forum/showThread.action?" +
                "threadId=" + threadId + "&widgetId=" + widgetId + "&isShowOnUserPages=" + isShowOnUserPages);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();

}
