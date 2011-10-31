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

import com.shroggle.entity.*;
import com.shroggle.logic.form.FormManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.childSites.childSiteRegistration.ChildSiteRegistrationManager;
import com.shroggle.logic.form.FormData;
import com.shroggle.presentation.site.render.RenderContext;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Balakirev Anatoliy
 */
public class RenderChildSiteRegistration {

    public static String execute(final Widget widget, final RenderContext context) throws IOException, ServletException {
        final boolean showFromAddRecord = context.getRequest().getParameter("showFromAddRecord") != null
                && Boolean.parseBoolean(context.getRequest().getParameter("showFromAddRecord"));
        if (ChildSiteRegistrationManager.showFormsForEdit(widget, FormManager.isForceFormShowing(context, ItemType.CHILD_SITE_REGISTRATION))
                && !showFromAddRecord) {
            return executeForLogined(widget, context);
        } else {
            return executeForNotLogined(widget, context);
        }
    }

    private static String executeForLogined(final Widget widget, final RenderContext context)
            throws IOException, ServletException {
        final User user = new UsersManager().getLoginedUser();
        final Persistance persistance = ServiceLocator.getPersistance();
        final DraftChildSiteRegistration form = (DraftChildSiteRegistration)((WidgetItem) widget).getDraftItem();//persistance.getChildSiteRegistrationById(((WidgetItem) widget).getDraftItem());
        if (form == null) {
            return widgetNotConfigured(context);
        }
        final List<FilledForm> filledForms = new ArrayList<FilledForm>();
        for (FilledForm filledForm : persistance.getFilledFormsByFormAndUserId(form.getFormId(), user.getUserId())) {
            //If childSiteSettingsId == null - form opted out from network. We are showing this filledForm with "not in network" message.
            //If childSiteSettingsId not null and there is no such childSiteSettings in db - site was deleted. We aren`t showing this filledForm
            if (filledForm.getChildSiteSettingsId() == null) {
                filledForms.add(filledForm);
            } else if (persistance.getChildSiteSettingsById(filledForm.getChildSiteSettingsId()) != null) {
                filledForms.add(filledForm);
            }
        }
        context.getRequest().setAttribute("widgetId", widget.getWidgetId());
        context.getRequest().setAttribute("form", new FormData(form));
        context.getRequest().setAttribute("filledForms", filledForms);

        return ServiceLocator.getHtmlGetter().get(
                "/site/render/renderWidgetChildSiteRegistrationForLoginedUser.jsp?" +
                        StringUtil.getEmptyOrString(context.getParameterMap().get(ItemType.CHILD_SITE_REGISTRATION)),
                context.getRequest(), context.getResponse(), context.getServletContext());
    }

    public static String executeForNotLogined(final Widget widget, final RenderContext context)
            throws IOException, ServletException {
        final Persistance persistance = ServiceLocator.getPersistance();
        final DraftChildSiteRegistration form = (DraftChildSiteRegistration)((WidgetItem) widget).getDraftItem();//persistance.getChildSiteRegistrationById(((WidgetItem) widget).getDraftItem());
        if (form == null) {
            return widgetNotConfigured(context);
        }
        context.getRequest().setAttribute("widgetId", widget.getWidgetId());
        context.getRequest().setAttribute("form", new FormData(form));
        context.getRequest().setAttribute("termsAndConditions", form.getTermsAndConditions());
        context.getRequest().setAttribute("siteId", form.getSiteId());
        context.getRequest().setAttribute("loginedUser", new UsersManager().getLoginedUser());

        return ServiceLocator.getHtmlGetter().get(
                "/site/render/renderWidgetChildSiteRegistration.jsp?" +
                        StringUtil.getEmptyOrString(context.getParameterMap().get(ItemType.CHILD_SITE_REGISTRATION)),
                context.getRequest(), context.getResponse(), context.getServletContext());
    }

    private static String widgetNotConfigured(final RenderContext context) throws IOException, ServletException {
        return ServiceLocator.getHtmlGetter().get(
                "/site/render/renderWidgetNotConfigure.jsp?widgetType=Child Site Registration",
                context.getRequest(), context.getResponse(), context.getServletContext());
    }

}
