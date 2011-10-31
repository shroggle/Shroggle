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

package com.shroggle.presentation.site.render;

import com.shroggle.entity.User;
import com.shroggle.entity.FilledForm;
import com.shroggle.logic.form.FormData;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dmitry.solomadin
 */
public class RenderWidgetForm {

    public static void addFormParameters(final HttpServletRequest request, final int widgetId, final int siteId,
                                      final User loginedUser, final FormData formData, final boolean forcePrefill,
                                      final FilledForm prefilledForm){
        request.setAttribute("widgetId", widgetId);
        request.setAttribute("loginedUser", loginedUser);
        request.setAttribute("siteId", siteId);
        request.setAttribute("form", formData);
        request.setAttribute("forcePrefill", forcePrefill);
        request.setAttribute("prefilledForm", prefilledForm);
    }

    public static void setShowFromEditRecord(final HttpServletRequest request, final boolean showFormEditRecord){
        request.setAttribute("showFromEditRecord", showFormEditRecord);
    }

}
