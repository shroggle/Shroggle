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
package com.shroggle.presentation.video;

import com.shroggle.entity.Widget;
import com.shroggle.entity.WidgetItem;
import com.shroggle.logic.site.VideoItemManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.site.FunctionalWidgetInfo;
import com.shroggle.presentation.site.GetFunctionalWidgetsService;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.annotations.RemoteMethod;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Artem Stasuk
 */
@RemoteProxy
public class IsWidgetVideoPreparedService {

    @RemoteMethod
    public FunctionalWidgetInfo execute(final Integer widgetId, final int videoItemId) throws IOException, ServletException {
        final UserManager userManager = new UsersManager().getLogined();

        final VideoItemManager videoItemManager = new VideoItemManager(userManager, videoItemId);
        if (videoItemManager.isVideoPrepared()) {
            final Widget widget;
            if (widgetId != null) {
                widget = userManager.getRight().getSiteRight().getWidgetForEditInPresentationalService(widgetId);
            } else {
                widget = null;
            }

            return new GetFunctionalWidgetsService().createFunctionalWidgetInfo(widget, "widget", true);
        }

        return null;
    }

}
