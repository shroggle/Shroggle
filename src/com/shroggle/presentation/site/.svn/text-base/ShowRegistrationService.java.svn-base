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

import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.annotations.RemoteMethod;

import javax.servlet.ServletException;
import java.io.IOException;

import com.shroggle.presentation.AbstractService;

/**
 * author: dmitry.solomadin
 * date: 15.01.2009
 */
@RemoteProxy
public class ShowRegistrationService extends AbstractService {

    @RemoteMethod
    public String execute(final int widgetId) throws ServletException, IOException {
        return getContext().forwardToString(
                "/site/widgetRegistration.action?widgetId=" + widgetId);
    }

    @RemoteMethod
    public String executeWithEditVisitorDetails(final int widgetId) throws ServletException, IOException {
        return getContext().forwardToString(
                "/site/widgetRegistration.action?editVisitorDetails=true&widgetId=" + widgetId);
    }

    @RemoteMethod
    public String executeWithReturnToLogin(final int widgetId) throws ServletException, IOException {
        return getContext().forwardToString(
                "/site/widgetRegistration.action?returnToLogin=true&widgetId=" + widgetId);
    }

    @RemoteMethod
    public String executeWithReturnToLoginAndFormId(final int widgetId, final int formId) throws ServletException, IOException {
        return getContext().forwardToString(
                "/site/widgetRegistration.action?returnToLogin=true&widgetId=" + widgetId + "&formId=" + formId);
    }

    @RemoteMethod
    public String executeWithReturnToForum(final int widgetId) throws ServletException, IOException {
        return getContext().forwardToString(
                "/site/widgetRegistration.action?returnToForum=true&widgetId=" + widgetId);
    }

}
