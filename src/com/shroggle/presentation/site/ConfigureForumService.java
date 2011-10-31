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

package com.shroggle.presentation.site;

import com.shroggle.entity.*;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.widget.WidgetTitle;
import com.shroggle.logic.widget.WidgetTitleGetter;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameters;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class, params = {})
public class ConfigureForumService extends AbstractService implements WithWidgetTitle {

    @SynchronizeByMethodParameters({
            @SynchronizeByMethodParameter(
                    entityClass = DraftForum.class)})
    @RemoteMethod
    public void execute(final Integer widgetId, final Integer forumId) {
        final UserManager userManager = new UsersManager().getLogined();
        final UserRightManager userRightManager = userManager.getRight();

        if (widgetId == null && forumId == null) {
            throw new IllegalArgumentException("Both widgetId and forumId cannot be null. " +
                    "This service is only for configuring existing forums.");
        }

        if (widgetId == null) {
            // edit forum from dashboard or manage items.
            selectedForum = persistance.getDraftItem(forumId);
            site = persistance.getSite(selectedForum.getSiteId());
        } else {
            final WidgetItem widget = userRightManager.getSiteRight().getWidgetItemForEditInPresentationalService(
                    widgetId);
            site = widget.getSite();

            widgetTitle = new WidgetTitleGetter(widget);

            if (widget.getDraftItem() != null) {
                selectedForum = (DraftForum) widget.getDraftItem();
            } else {
                throw new IllegalArgumentException("Seems like widget with Id= " + widgetId + " got no item." +
                        "This service is only for configuring existing forums.");
            }
        }

        getContext().getHttpServletRequest().setAttribute("forumService", this);
    }

    public DraftForum getSelectedForum() {
        return selectedForum;
    }

    public WidgetTitle getWidgetTitle() {
        return widgetTitle;
    }

    public Site getSite() {
        return site;
    }

    private Persistance persistance = ServiceLocator.getPersistance();
    private DraftForum selectedForum;
    private WidgetTitle widgetTitle;
    private Site site;

}
