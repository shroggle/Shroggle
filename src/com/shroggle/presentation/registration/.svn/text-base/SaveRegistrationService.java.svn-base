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

package com.shroggle.presentation.registration;

import com.shroggle.entity.*;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.registration.RegistrationFormCreator;
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
public class SaveRegistrationService extends AbstractService {

    @SynchronizeByMethodParameterProperty(
            entityClass = Widget.class,
            method = SynchronizeMethod.WRITE,
            entityIdPropertyPath = "widgetId")
    @RemoteMethod
    public FunctionalWidgetInfo execute(final SaveRegistrationRequest request) throws ServletException, IOException {
        final UserManager userManager = new UsersManager().getLogined();

        new RegistrationFormCreator(userManager).save(request);

        final WidgetItem widgetRegistration;
        if (request.getWidgetId() != null) {
            final UserRightManager userRightManager = userManager.getRight();
            widgetRegistration = userRightManager.getSiteRight().getWidgetItemForEditInPresentationalService(
                    request.getWidgetId());
        } else {
            widgetRegistration = null;
        }

        return new GetFunctionalWidgetsService().createFunctionalWidgetInfo(widgetRegistration, "widget", true);
    }

}
