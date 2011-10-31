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

import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.site.forms.FormPageAdditionalParameters;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.exception.InvalidItemTypeForPageBreakException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.exception.FormNotFoundException;

import javax.servlet.ServletException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
public class RenderFormPageBreak {

    public String execute(final int widgetId, final int formId, final int pageBreaksToPass,
                          final int filledFormId, final List<FormPageAdditionalParameters> additionalParameters,
                          RenderContext context) throws IOException, ServletException {
        final Widget widget = persistance.getWidget(widgetId);

        if (widget == null) {
            throw new WidgetNotFoundException("Cannot find widget by Id=" + widgetId);
        }

        final Form form = persistance.getFormById(formId);

        if (form == null) {
            throw new FormNotFoundException("Cannot find form by Id=" + formId);
        }

        final Map<ItemType, String> parameterMap = new HashMap<ItemType, String>();
        String parameterString =
                "&pageBreaksToPass=" + pageBreaksToPass + "&filledFormToUpdateId=" + filledFormId;
        for (FormPageAdditionalParameters parameter : additionalParameters) {
            if (!StringUtil.isNullOrEmpty(parameter.getParameterName()) && !parameter.getParameterName().equals("null") &&
                    !StringUtil.isNullOrEmpty(parameter.getParameterValue()) && !parameter.getParameterValue().equals("null")) {
                parameterString += "&" + parameter.getParameterName() + "=" + parameter.getParameterValue();
            }
        }

        parameterMap.put(form.getItemType(), parameterString);
        context.setParameterMap(parameterMap);

        return new RenderWidgets(new PageManager(widget.getPage()), SiteShowOption.ON_USER_PAGES)
                .executeWidgetWithoutItsSize(widget, context, false);
    }

    public String executeForAddRecord(final ItemType type, final int pageBreaksToPass, final int filledFormId,
                                      final int formId, final List<FormPageAdditionalParameters> additionalParameters)
            throws IOException, ServletException {
        String responseString;
        switch (type) {
            case CUSTOM_FORM: {
                responseString = CUSTOM_FORM_ACTION_URL + "?formId=" + formId
                        + "&showFromAddRecord=true"
                        + "&pageBreaksToPass=" + pageBreaksToPass
                        + "&filledFormToUpdateId=" + filledFormId;
                break;
            }
            case REGISTRATION: {
                responseString = REGISTRATION_ACTION_URL + "?formId=" + formId
                        + "&showFromAddRecord=true"
                        + "&pageBreaksToPass=" + pageBreaksToPass
                        + "&filledFormToUpdateId=" + filledFormId;
                break;
            }
            case CONTACT_US: {
                responseString = CONTACT_US_ACTION_URL + "?formId=" + formId
                        + "&showFromAddRecord=true"
                        + "&pageBreaksToPass=" + pageBreaksToPass
                        + "&filledFormToUpdateId=" + filledFormId;
                break;
            }
            case CHILD_SITE_REGISTRATION: {
                responseString = CHILD_SITE_REGISTRATION_ACTION_URL + "?formId=" + formId
                        + "&showFromAddRecord=true"
                        + "&pageBreaksToPass=" + pageBreaksToPass
                        + "&filledFormToUpdateId=" + filledFormId;
                break;
            }
            default: {
                throw new InvalidItemTypeForPageBreakException();
            }
        }

        for (FormPageAdditionalParameters parameter : additionalParameters) {
            if (!StringUtil.isNullOrEmpty(parameter.getParameterName()) && !parameter.getParameterName().equals("null") &&
                    !StringUtil.isNullOrEmpty(parameter.getParameterValue()) && !parameter.getParameterValue().equals("null")) {
                responseString += "&" + parameter.getParameterName() + "=" + parameter.getParameterValue();
            }
        }

        return responseString;
    }

    public String reset(final int widgetId, final int formId, final RenderContext context) throws IOException, ServletException {
        final Widget widget = persistance.getWidget(widgetId);

        if (widget == null) {
            throw new WidgetNotFoundException("Cannot find widget by Id=" + widgetId);
        }

        final Form form = persistance.getFormById(formId);

        final Map<ItemType, String> parameterMap = new HashMap<ItemType, String>();
        parameterMap.put(form.getItemType(), "&cameFromReset=true");
        context.setParameterMap(parameterMap);

        return new RenderWidgets(new PageManager(widget.getPage()), SiteShowOption.ON_USER_PAGES).executeWidgetWithoutItsSize(widget, context, false);
    }

    public String resetForAddRecord(final ItemType type, final int formId) {
        switch (type) {
            case CUSTOM_FORM: {
                return CUSTOM_FORM_ACTION_URL + "?formId=" + formId + "&showFromAddRecord=true" + "&cameFromReset=true";
            }
            case REGISTRATION: {
                return REGISTRATION_ACTION_URL + "?formId=" + formId + "&showFromAddRecord=true" + "&cameFromReset=true";
            }
            case CONTACT_US: {
                return CONTACT_US_ACTION_URL + "?formId=" + formId + "&showFromAddRecord=true" + "&cameFromReset=true";
            }
            case CHILD_SITE_REGISTRATION: {
                return CHILD_SITE_REGISTRATION_ACTION_URL + "?formId=" + formId + "&showFromAddRecord=true" + "&cameFromReset=true";
            }
            default: {
                throw new InvalidItemTypeForPageBreakException();
            }
        }
    }

    private Persistance persistance = ServiceLocator.getPersistance();
    private final String CHILD_SITE_REGISTRATION_ACTION_URL = "/site/showChildSiteRegistration.action";
    private final String REGISTRATION_ACTION_URL = "/site/showRegistrationForm.action";
    private final String CUSTOM_FORM_ACTION_URL = "/site/showCustomForm.action";
    private final String CONTACT_US_ACTION_URL = "/site/showContactUs.action";


}
