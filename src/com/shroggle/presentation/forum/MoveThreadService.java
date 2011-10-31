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
import com.shroggle.entity.SubForum;
import com.shroggle.exception.CannotFindSubForumException;
import com.shroggle.exception.CannotFindThreadException;
import com.shroggle.exception.ThreadWithoutSubForumException;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import org.directwebremoting.WebContext;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

@RemoteProxy
public class MoveThreadService extends AbstractService {

    private Persistance persistance = ServiceLocator.getPersistance();
    private PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private List<SubForum> subForums;
    private ForumThread thread;

    public int widgetId;
    public boolean isShowOnUserPages;

    @RemoteMethod
    public String show(final int threadId, final int widgetId, final boolean isShowOnUserPages)
            throws ServletException, IOException {
        final WebContext webContext = getContext();

        thread = persistance.getForumThreadById(threadId);

        if (thread == null) {
            throw new CannotFindThreadException("MoveThreadService.show. Cannot find thread with ID=" + threadId);
        }

        subForums = persistance.getSubForumsByForumId(thread.getSubForum().getForum().getId());
        this.widgetId = widgetId;
        this.isShowOnUserPages = isShowOnUserPages;

        webContext.getHttpServletRequest().setAttribute("service", this);

        return webContext.forwardToString("/forum/moveThread.jsp");
    }

    @RemoteMethod
    public String execute(final int threadId, final int subForumId, final int widgetId, final boolean isShowOnUserPages)
            throws ServletException, IOException {
        final WebContext webContext = getContext();

        final ForumThread thread = persistance.getForumThreadById(threadId);

        if (thread == null) {
            throw new CannotFindThreadException("MoveThreadService.execute. Cannot find thread with Id=" + threadId);
        }
        if (thread.getSubForum() == null) {
            throw new ThreadWithoutSubForumException("MoveThreadService.execute. Thread with id=" + threadId + " is without subforum");
        }

        int oldSubForumId = thread.getSubForum().getSubForumId();

        persistanceTransaction.execute(new Runnable() {

            public void run() {
                SubForum prerviousSubForum = thread.getSubForum();
                prerviousSubForum.removeForumThread(thread);

                SubForum newSubForum = persistance.getSubForumById(subForumId);
                if (newSubForum == null) {
                    throw new CannotFindSubForumException("MoveThreadService.execute. Cannot find subforum exception with Id=" + subForumId);
                }
                newSubForum.addForumThread(thread);
            }
        });

        return webContext.forwardToString
                ("/forum/showSubForum.action?" +
                        "subForumId=" + oldSubForumId + "&widgetId=" + widgetId + "&isShowOnUserPages=" + isShowOnUserPages);
    }

    public List<SubForum> getSubForums() {
        return subForums;
    }

    public ForumThread getThread() {
        return thread;
    }
}
