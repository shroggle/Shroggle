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

package com.shroggle.presentation.blogSummary;

import com.shroggle.entity.*;
import com.shroggle.exception.BlogSummaryNameNotUnique;
import com.shroggle.exception.BlogSummaryNotFoundException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.FunctionalWidgetInfo;
import com.shroggle.presentation.site.GetFunctionalWidgetsService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Locale;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class CreateBlogSummaryService extends AbstractService {

    @RemoteMethod
    public FunctionalWidgetInfo execute(final CreateBlogSummaryRequest request) throws IOException, ServletException {
        final UserManager userManager = new UsersManager().getLogined();

        final WidgetItem widget;
        if (request.getWidgetId() != null) {
            widget = (WidgetItem) userManager.getRight().getSiteRight().getWidgetForEditInPresentationalService(
                    request.getWidgetId());
        } else {
            widget = null;
        }

        persistanceTransaction.execute(new Runnable() {
            public void run() {
                final DraftBlogSummary blogSummary = persistance.getDraftItem(request.getSelectedBlogSummaryId());
                if (blogSummary == null || blogSummary.getSiteId() <= 0) {
                    throw new BlogSummaryNotFoundException("Cannot find blog summary by Id=" + request.getSelectedBlogSummaryId());
                }

                final Site site;
                if (widget != null) {
                    site = widget.getSite();
                } else {
                    site = persistance.getSite(blogSummary.getSiteId());
                }

                final DraftBlogSummary duplicateBlogSummary = persistance.getBlogSummaryByNameAndSiteId(request.getBlogSummaryName(), site.getSiteId());
                if (duplicateBlogSummary != null && duplicateBlogSummary.getId() != request.getSelectedBlogSummaryId()) {
                    throw new BlogSummaryNameNotUnique(blogSummaryBundle.get("blogSummaryNameNotUnique"));
                }

                blogSummary.setName(request.getBlogSummaryName());
                blogSummary.setBlogSummaryHeader(request.getBlogSummaryHeader());
                blogSummary.setDisplayBlogSummaryHeader(request.isDisplayBlogSummaryHeader());
                blogSummary.setIncludedCrossWidgetId(request.getIncludedCrossWidgetId());
                blogSummary.setIncludedPostNumber(request.getIncludedPostNumber());
                blogSummary.setNumberOfWordsToDisplay(request.getNumberOfWordsToDisplay());
                //display criteria
                blogSummary.setPostDisplayCriteria(request.getPostDisplayCriteria());
                //sort criteria
                blogSummary.setPostSortCriteria(request.getPostSortCriteria());
                blogSummary.setShowPostName(request.isShowPostName());
                blogSummary.setShowPostContents(request.isShowPostContents());
                blogSummary.setShowPostAuthor(request.isShowPostAuthor());
                blogSummary.setShowPostDate(request.isShowPostDate());
                blogSummary.setShowBlogName(request.isShowBlogName());
                blogSummary.setCurrentSiteBlogs(request.isCurrentSiteBlogs());
                blogSummary.setAllSiteBlogs(request.isAllSiteBlogs());
            }
        });

        return new GetFunctionalWidgetsService().createFunctionalWidgetInfo(widget, "widget", true);
    }

    private Persistance persistance = ServiceLocator.getPersistance();
    private PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private International blogSummaryBundle = ServiceLocator.getInternationStorage().get("configureBlogSummary", Locale.US);

}