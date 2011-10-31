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
package com.shroggle.presentation.site.render.childSiteRegistration;

import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.render.RenderContext;
import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.annotations.RemoteMethod;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class RenderChildSiteRegistrationService extends AbstractService {

    @RemoteMethod
    public String showForm(final int widgetId) throws IOException, ServletException {
        final Widget widget = ServiceLocator.getPersistance().getWidget(widgetId);
        return RenderChildSiteRegistration.executeForNotLogined(widget, createRenderContext(false));
    }

    @RemoteMethod
    public String editFilledForm(final int widgetId, final int filledFormId) throws IOException, ServletException {
        final Widget widget = persistance.getWidget(widgetId);

        final FilledForm filledForm = persistance.getFilledFormById(filledFormId);
        getContext().getHttpServletRequest().setAttribute("editDetails", true);
        getContext().getHttpServletRequest().setAttribute("service", this);
        getContext().getHttpServletRequest().setAttribute("filledFormId", filledFormId);
        getContext().getHttpServletRequest().setAttribute("settingsId", filledForm.getChildSiteSettingsId());
        getContext().getHttpServletRequest().setAttribute("childSiteUserId", filledForm.getUser().getUserId());

        final Map<ItemType, String> parameterMap = new HashMap<ItemType, String>();
        parameterMap.put(ItemType.CHILD_SITE_REGISTRATION, "&filledFormToUpdateId=" + filledForm.getFilledFormId());
        final RenderContext context = createRenderContext(false, parameterMap);

        return RenderChildSiteRegistration.executeForNotLogined(widget, context);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
}
