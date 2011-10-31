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

package com.shroggle.presentation.blog;

import com.shroggle.entity.DraftBlog;
import com.shroggle.entity.Site;
import com.shroggle.entity.WidgetItem;
import com.shroggle.exception.*;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.FunctionalWidgetInfo;
import com.shroggle.presentation.site.GetFunctionalWidgetsService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;

@RemoteProxy
public class EditBlogService extends AbstractService {

    @RemoteMethod
    public FunctionalWidgetInfo execute(final EditBlogRequest request) throws IOException, ServletException {
        final UserManager userManager = new UsersManager().getLogined();

        final WidgetItem widget;
        if (request.getWidgetId() != null) {
            widget = (WidgetItem) userManager.getRight().getSiteRight().getWidgetForEditInPresentationalService(
                    request.getWidgetId());
        } else {
            widget = null;
        }

        if (request.getWritePosts() == null) {
            throw new BlogWritePostsNullException(
                    "Can't edit blog " + request.getBlogId() + " with null write posts!");
        }

        if (request.getBlogName() == null || request.getBlogName().trim().isEmpty()) {
            throw new BlogNameEmptyException("Can't create blog with empty name!");
        }

        persistanceTransaction.execute(new Runnable() {
            public void run() {
                final DraftBlog blog = persistance.getDraftItem(request.getBlogId());
                if (blog == null || blog.getSiteId() <= 0) {
                    throw new BlogNotFoundException("Cannot find blog by Id=" + request.getBlogId());
                }

                final Site site;
                if (widget != null) {
                    site = widget.getSite();
                } else {
                    site = persistance.getSite(blog.getSiteId());
                }

                final DraftBlog tempBlog = persistance.getBlogByNameAndSiteId(request.getBlogName(), site.getSiteId());
                if (tempBlog != null && tempBlog != blog) {
                    throw new BlogNameNotUniqueException("Can't set not unique blog name!");
                }

                blog.setEditBlogPostRight(request.getEditBlogPostRight());
                blog.setEditCommentRight(request.getEditCommentRight());
                blog.setName(request.getBlogName());
                blog.setAddPostRight(request.getWritePosts());
                blog.setAddCommentOnPostRight(request.getWriteComments());
                blog.setAddCommentOnCommentRight(request.getWriteCommentsOnComments());
                blog.setDisplayAuthorEmailAddress(request.isDisplayAuthorEmailAddress());
                blog.setDisplayAuthorScreenName(request.isDisplayAuthorScreenName());
                blog.setDisplayDate(request.isDisplayDate());
                blog.setDisplayBlogName(request.isDisplayBlogName());
                blog.setDisplayNextAndPreviousLinks(request.isDisplayNextAndPreviousLinks());
                blog.setDisplayBackToTopLink(request.isDisplayBackToTopLink());
                blog.setDisplayPosts(request.getDisplayPosts());
                blog.setDisplayPostsFiniteNumber(request.getDisplayPostsFiniteNumber());
                blog.setDisplayPostsWithinDateRange(request.getDisplayPostsWithinDateRange());
                blog.setModified(true);
            }
        });

        return new GetFunctionalWidgetsService().createFunctionalWidgetInfo(widget, "widget", true);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}