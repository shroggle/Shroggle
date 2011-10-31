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

package com.shroggle.presentation.tellFriend;

import com.shroggle.entity.*;
import com.shroggle.exception.TellFriendNotFoundException;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.site.TellFriendEdit;
import com.shroggle.logic.site.TellFriendManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.widget.WidgetTitle;
import com.shroggle.logic.widget.WidgetTitleGetter;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.presentation.site.WithWidgetTitle;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.List;

/**
 * @author Artem Stasuk, dmitry.solomadin
 */
@RemoteProxy
public class ConfigureTellFriendService extends ServiceWithExecutePage implements WithWidgetTitle {

    @RemoteMethod
    public void execute(final Integer widgetId, final Integer tellFriendId) throws Exception {
        final UserManager userManager = new UsersManager().getLogined();
        final UserRightManager userRightManager = userManager.getRight();

        if (widgetId == null && tellFriendId == null) {
            throw new TellFriendNotFoundException("Both widgetId and tellFriendId cannot be null. " +
                    "This service is only for configuring existing tell friends.");
        }

        if (widgetId == null) {
            tellFriend = persistance.getDraftItem(tellFriendId);

            if (tellFriend == null) {
                throw new TellFriendNotFoundException("Cannot find tell friend by Id=" + tellFriendId);
            }

            site = persistance.getSite(tellFriend.getSiteId());
        } else {
            widget = (WidgetItem) userRightManager.getSiteRight().getWidgetForEditInPresentationalService(
                    widgetId);
            site = widget.getSite();

            widgetTitle = new WidgetTitleGetter(widget);

            if (widget.getDraftItem() != null) {
                tellFriend = (DraftTellFriend) widget.getDraftItem();
            } else {
                throw new TellFriendNotFoundException("Seems like widget with Id= " + widgetId + " got no item.");
            }
        }

        getContext().getHttpServletRequest().setAttribute("tellFriendService", this);
    }

    public WidgetTitle getWidgetTitle() {
        return widgetTitle;
    }

    public DraftTellFriend getTellFriend() {
        return tellFriend;
    }

    public Site getSite() {
        return site;
    }

    public WidgetItem getWidget() {
        return widget;
    }

    private DraftTellFriend tellFriend;
    private WidgetTitle widgetTitle;
    private Site site;
    private WidgetItem widget;
    private final Persistance persistance = ServiceLocator.getPersistance();

}