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

import com.shroggle.command.forum.CreateSubForumCommand;
import com.shroggle.entity.DraftForum;
import com.shroggle.entity.SubForum;
import com.shroggle.exception.ForumNotFoundException;
import com.shroggle.exception.CannotFindSubForumException;
import com.shroggle.exception.SubForumWithNullOrEmptyDescriptionException;
import com.shroggle.exception.SubForumWithNullOrEmptyNameException;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.render.ShowPageVersionUrlGetter;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import org.directwebremoting.WebContext;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RemoteProxy
public class CreateSubForumService extends AbstractService {

    private CreateSubForumCommand createSubForumCommand = new CreateSubForumCommand();
    private Persistance persistance = ServiceLocator.getPersistance();
    private PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private HttpServletRequest httpServletRequest;
    private String showPageVersionUrl;

    public SubForum renameSubForum;
    public int forumId;
    public int widgetId;
    public boolean isShowOnUserPages;


    @RemoteMethod
    public String showCreateSubForumForm(int forumId, int widgetId, boolean isShowOnUserPages)
            throws ServletException, IOException {

        this.forumId = forumId;
        this.widgetId = widgetId;
        this.isShowOnUserPages = isShowOnUserPages;
        this.showPageVersionUrl = ShowPageVersionUrlGetter.get(httpServletRequest);

        final WebContext webContext = getContext();
        if (webContext != null) {
            webContext.getHttpServletRequest().setAttribute("service", this);
            return getContext().forwardToString("/forum/showCreateSubForumForm.jsp");
        } else {
            httpServletRequest.setAttribute("service", this);
            return "/forum/showCreateSubForumForm.jsp";
        }
    }

    @RemoteMethod
    public String showRenameSubForumForm(final int subForumId, final int widgetId, boolean isShowOnUserPages)
            throws ServletException, IOException {
        renameSubForum = persistance.getSubForumById(subForumId);

        if (renameSubForum == null) {
            throw new CannotFindSubForumException("Cannot find subForum by Id=" + subForumId);
        }

        this.forumId = renameSubForum.getForum().getId();
        this.widgetId = widgetId;
        this.isShowOnUserPages = isShowOnUserPages;

        final WebContext webContext = getContext();
        if (webContext != null) {
            webContext.getHttpServletRequest().setAttribute("service", this);
            return getContext().forwardToString("/forum/showCreateSubForumForm.jsp");
        } else {
            httpServletRequest.setAttribute("service", this);
            return "/forum/showCreateSubForumForm.jsp";
        }
    }

    @RemoteMethod
    public String rename(final RenameSubForumRequest renameSubForumRequest, int widgetId, boolean isShowOnUserPages) throws ServletException, IOException {
        if ((renameSubForumRequest.getSubForumName() == null) || (renameSubForumRequest.getSubForumName().isEmpty())) {
            return "EmptySubForumNameException";
        }

        if ((renameSubForumRequest.getSubForumDescription() == null) || (renameSubForumRequest.getSubForumDescription().isEmpty())) {
            return "EmptySubForumDescriptionException";
        }

        final SubForum renameSubForum = persistance.getSubForumById(renameSubForumRequest.getSubForumId());

        if (renameSubForum == null) {
            throw new CannotFindSubForumException("Cannot find subForum by Id=" + renameSubForumRequest.getSubForumId());
        }

        persistanceTransaction.execute(new Runnable() {
            public void run() {
                renameSubForum.setSubForumDescription(renameSubForumRequest.getSubForumDescription());
                renameSubForum.setSubForumName(renameSubForumRequest.getSubForumName());
            }
        });

        return getContext().forwardToString
                ("/forum/widgetForum.action?forumId=" + renameSubForum.getForum().getId() + "&widgetId=" + widgetId + "&isShowOnUserPages=" + isShowOnUserPages);
    }

    @RemoteMethod
    public String execute(CreateSubForumRequest createSubForumRequest, int forumId, int widgetId, boolean isShowOnUserPages) throws ServletException, IOException {
        createSubForumCommand.setSubForumName(createSubForumRequest.getSubForumName());
        createSubForumCommand.setSubForumDescription(createSubForumRequest.getSubForumDescription());
        createSubForumCommand.setForumId(forumId);
        try {
            createSubForumCommand.execute();
        } catch (SubForumWithNullOrEmptyNameException ex) {
            return "SubForumWithNullOrEmptyNameException";
        } catch (SubForumWithNullOrEmptyDescriptionException ex) {
            return "SubForumWithNullOrEmptyDescriptionException";
        }

        return getContext().forwardToString
                ("/forum/widgetForum.action?forumId=" + forumId + "&widgetId=" + widgetId + "&isShowOnUserPages=" + isShowOnUserPages);
    }

    @RemoteMethod
    public String deleteSubForum(int subForumId, int widgetId, boolean isShowOnUserPages) throws ServletException, IOException {
        final SubForum subForum = persistance.getSubForumById(subForumId);

        if (subForum == null) {
            throw new CannotFindSubForumException("Cannot find subforum with Id=" + subForumId);
        }

        final DraftForum forum = subForum.getForum();

        if (forum == null) {
            throw new ForumNotFoundException("Cannot find forum in subForum with Id =" + subForumId);
        }

        final int forumId = forum.getId();

        persistanceTransaction.execute(new Runnable() {
            public void run() {
                persistance.removeSubForum(subForum);
                forum.removeSubForum(subForum);
            }
        });

        return getContext().forwardToString
                ("/forum/widgetForum.action?forumId=" + forumId + "&widgetId=" + widgetId + "&isShowOnUserPages=" + isShowOnUserPages);
    }

    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public String getShowPageVersionUrl() {
        return showPageVersionUrl;
    }

}
