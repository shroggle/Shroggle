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
import com.shroggle.exception.BlogSummaryNotFoundException;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.blogSummary.ConfigureBlogSummaryLogic;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.widget.WidgetTitle;
import com.shroggle.logic.widget.WidgetTitleGetter;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.WithWidgetTitle;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Balakirev Anatoliy, dmitry.solomadin
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class, params = {})
public class ConfigureBlogSummaryService extends AbstractService implements WithWidgetTitle {

    @SynchronizeByMethodParameter(
            entityClass = Widget.class)
    @RemoteMethod
    public void execute(final Integer widgetId, Integer blogSummaryId) {
        final UserManager userManager = new UsersManager().getLogined();
        final UserRightManager userRightManager = userManager.getRight();

        if (widgetId == null && blogSummaryId == null) {
            throw new BlogSummaryNotFoundException("Both widgetId and blogSummaryId cannot be null. " +
                    "This service is only for configuring existing blog summaries.");
        }

        if (widgetId == null) {
            // edit forum from dashboard or manage items.
            blogSummary = persistance.getDraftItem(blogSummaryId);

            if (blogSummary == null) {
                throw new BlogSummaryNotFoundException("Cannot find blog summary by Id=" + blogSummaryId);
            }

            site = persistance.getSite(blogSummary.getSiteId());
        } else {
            widget = (WidgetItem) userRightManager.getSiteRight().getWidgetForEditInPresentationalService(widgetId);
            site = widget.getSite();

            widgetTitle = new WidgetTitleGetter(widget);

            if (widget.getDraftItem() != null) {
                blogSummary = (DraftBlogSummary) widget.getDraftItem();
            } else {
                throw new BlogSummaryNotFoundException("Seems like widget with Id = " + widgetId + " has not got item." +
                        "This service is only for configuring existing blog summaries.");
            }
        }

        BlogSummaryDataCreator creator = new BlogSummaryDataCreator(userManager.getUserId(), SiteShowOption.getDraftOption());
        blogsFromAllSites = creator.getForAllSites();
        blogsFromCurrentSite = creator.getForSite(site.getSiteId());

        selectedBlogIds = ConfigureBlogSummaryLogic.createSelectedBlogsIds(blogSummary.getIncludedCrossWidgetId(), blogsFromAllSites);
        blogsIdsFromCurrentSite = ConfigureBlogSummaryLogic.createBlogsIdsAsString(blogsFromCurrentSite);
        blogsIdsFromCurrentAccount = ConfigureBlogSummaryLogic.createBlogsIdsAsString(blogsFromAllSites);

        getContext().getHttpServletRequest().setAttribute("blogSummaryService", this);
    }


    public WidgetItem getWidget() {
        return widget;
    }

    public WidgetTitle getWidgetTitle() {
        return widgetTitle;
    }

    public List<BlogSummaryData> getBlogsFromAllSites() {
        return blogsFromAllSites;
    }

    public List<BlogSummaryData> getBlogsFromCurrentSite() {
        return blogsFromCurrentSite;
    }

    public DraftBlogSummary getBlogSummary() {
        return blogSummary;
    }

    public List<Integer> getSelectedBlogIds() {
        return selectedBlogIds;
    }

    public String getBlogsIdsFromCurrentSite() {
        return blogsIdsFromCurrentSite;
    }

    public String getBlogsIdsFromCurrentAccount() {
        return blogsIdsFromCurrentAccount;
    }

    public Site getSite() {
        return site;
    }

    private String blogsIdsFromCurrentSite;
    private String blogsIdsFromCurrentAccount;
    private List<Integer> selectedBlogIds = new ArrayList<Integer>();
    private DraftBlogSummary blogSummary = null;
    private List<BlogSummaryData> blogsFromAllSites; //from all available sites
    private List<BlogSummaryData> blogsFromCurrentSite;
    private WidgetItem widget;
    private WidgetTitle widgetTitle;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private Site site;

}


