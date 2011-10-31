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
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.forum.ForumDispatchHelper;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.site.ShowWidgetPreviewResolution;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByClassProperty;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@UrlBinding("/forum/showForum.action")
public class ShowForumAction extends Action {

    @SynchronizeByClassProperty(
            entityClass = DraftForum.class,
            entityIdFieldPath = "forumId")
    @DefaultHandler
    public Resolution execute() throws IOException, ServletException {
        final UserManager userManager = new UsersManager().getLogined();

        final DraftForum forum;
        if (forumId != null) {
            forum = userManager.getSiteItemForViewById(forumId, ItemType.FORUM);
        } else if (widgetId != null) {
            final Item draftItem = new WidgetManager(widgetId).getItemManager().getDraftItem();
            if (draftItem != null && draftItem.getItemType() == ItemType.FORUM) {
                forum = (DraftForum) draftItem;
            } else {
                return resolutionCreator.loginInUser(this);
            }
        } else {
            return resolutionCreator.loginInUser(this);
        }

        Map<ItemType, String> parameterMap = new HashMap<ItemType, String>();

        final String forumParameters =
                ForumDispatchHelper.extractForumDispatchParameters(getContext().getRequest().getParameterMap());
        parameterMap.put(ItemType.FORUM, forumParameters);

        final WidgetItem widgetForum = new WidgetItem();
        widgetForum.setDraftItem(forum);

        return new ShowWidgetPreviewResolution(
                widgetForum, getContext().getServletContext(), parameterMap);
    }

    public void setForumId(final Integer forumId) {
        this.forumId = forumId;
    }

    public void setWidgetId(Integer widgetId) {
        this.widgetId = widgetId;
    }

    private Integer widgetId;
    private Integer forumId;
    private ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();

}