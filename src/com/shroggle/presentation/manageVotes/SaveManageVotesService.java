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
package com.shroggle.presentation.manageVotes;

import com.shroggle.entity.SiteShowOption;
import com.shroggle.entity.WidgetItem;
import com.shroggle.logic.manageVotes.ManageVotesCreator;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.FunctionalWidgetInfo;
import com.shroggle.presentation.site.GetFunctionalWidgetsService;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class SaveManageVotesService extends AbstractService {

    @RemoteMethod
    public FunctionalWidgetInfo save(final SaveManageVotesRequest request) throws IOException, ServletException {
        final UserManager userManager = new UsersManager().getLogined();

        new ManageVotesCreator(userManager).save(request, SiteShowOption.getDraftOption());

        final WidgetItem widget;
        if (request.getWidgetId() != null) {
            widget = (WidgetItem) userManager.getRight().getSiteRight().getWidgetForEditInPresentationalService(
                    request.getWidgetId());
        } else {
            widget = null;
        }

        return new GetFunctionalWidgetsService().createFunctionalWidgetInfo(widget, "widget", true);
    }
}
