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

import com.shroggle.entity.Widget;
import com.shroggle.entity.WidgetItem;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.site.TellFriendEdit;
import com.shroggle.logic.site.TellFriendsManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.FunctionalWidgetInfo;
import com.shroggle.presentation.site.GetFunctionalWidgetsService;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author Artem Stasuk
 */
@RemoteProxy
public class SaveTellFriendService extends AbstractService {

    @RemoteMethod
    public FunctionalWidgetInfo execute(final TellFriendEdit edit) throws Exception {
        final UserManager userManager = new UsersManager().getLogined();

        new TellFriendsManager(userManager).save(edit);

        Widget widget;
        if (edit.getWidgetId() != null) {
            widget = userManager.getRight().getSiteRight().getWidgetForEditInPresentationalService(
                    edit.getWidgetId());
        } else {
            widget = null;
        }

        return new GetFunctionalWidgetsService().createFunctionalWidgetInfo(widget, "widget", true);
    }

}