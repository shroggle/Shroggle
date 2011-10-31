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

import com.shroggle.entity.*;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.widget.DefaultNameGetterService;
import com.shroggle.logic.widget.WidgetTitle;
import com.shroggle.logic.widget.WidgetTitleGetter;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.presentation.site.WithWidgetTitle;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameters;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class ConfigureBlogService extends AbstractService implements WithWidgetTitle {

    @SynchronizeByMethodParameters({
            @SynchronizeByMethodParameter(
                    entityClass = DraftBlog.class)})
    @RemoteMethod
    public void execute(final Integer widgetId, final Integer blogId) {
        final UserManager userManager = new UsersManager().getLogined();
        final UserRightManager userRightManager = userManager.getRight();

        if (widgetId == null && blogId == null) {
            throw new IllegalArgumentException("Both widgetId and forumId cannot be null. " +
                    "This service is only for configuring existing blogs.");
        }

        if (widgetId == null) {
            selectedBlog = persistance.getDraftItem(blogId);
            site = persistance.getSite(selectedBlog.getSiteId());
        } else {
            final WidgetItem widget = userRightManager.getSiteRight().getWidgetItemForEditInPresentationalService(
                    widgetId);
            site = widget.getSite();

            widgetTitle = new WidgetTitleGetter(widget);

            if (widget.getDraftItem() != null) {
                selectedBlog = (DraftBlog) widget.getDraftItem();
            } else {
                throw new IllegalArgumentException("Seems like widget with Id= " + widgetId + " got no item." +
                        "This service is only for configuring existing blogs.");
            }
        }

        getContext().getHttpServletRequest().setAttribute("blogService", this);
    }

    public DraftBlog getSelectedBlog() {
        return selectedBlog;
    }

    public WidgetTitle getWidgetTitle() {
        return widgetTitle;
    }

    public Site getSite() {
        return site;
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private WidgetTitle widgetTitle;
    private DraftBlog selectedBlog;
    private Site site;

}