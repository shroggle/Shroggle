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

package com.shroggle.presentation.advancedSearch;

import com.shroggle.entity.Widget;
import com.shroggle.entity.WidgetItem;
import com.shroggle.logic.advancedSearch.AdvancedSearchCreator;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.FunctionalWidgetInfo;
import com.shroggle.presentation.site.GetFunctionalWidgetsService;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameterProperty;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class SaveAdvancedSearchService extends AbstractService {

    @SynchronizeByMethodParameterProperty(
            entityClass = Widget.class,
            method = SynchronizeMethod.WRITE,
            entityIdPropertyPath = "widgetId")
    @RemoteMethod
    public FunctionalWidgetInfo execute(final SaveAdvancedSearchRequest request) throws IOException, ServletException {
        final UserManager userManager = new UsersManager().getLogined();

        new AdvancedSearchCreator(userManager).save(request);

        final WidgetItem widgetItem;
        if (request.getWidgetId() != null) {
            final UserRightManager userRightManager = userManager.getRight();
            widgetItem = userRightManager.getSiteRight().getWidgetItemForEditInPresentationalService(
                    request.getWidgetId());
        } else {
            widgetItem = null;
        }

        return new GetFunctionalWidgetsService().createFunctionalWidgetInfo(widgetItem, "widget", true);
    }

}
