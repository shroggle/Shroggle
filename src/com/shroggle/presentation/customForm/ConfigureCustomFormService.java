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

package com.shroggle.presentation.customForm;

import com.shroggle.entity.*;
import com.shroggle.exception.CustomFormNotFoundException;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.form.FormManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.widget.WidgetTitle;
import com.shroggle.logic.widget.WidgetTitleGetter;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.form.ShowForm;
import com.shroggle.presentation.form.ShowFormService;
import com.shroggle.presentation.site.WithWidgetTitle;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.WebContext;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class ConfigureCustomFormService extends AbstractService implements WithWidgetTitle, ShowForm {

    @SynchronizeByMethodParameter(
            entityClass = DraftForm.class)
    @RemoteMethod
    public void show(final Integer widgetId, final Integer customFormId) {
        final UserManager userManager = new UsersManager().getLogined();
        final UserRightManager userRightManager = userManager.getRight();

        if (widgetId == null && customFormId == null) {
            throw new IllegalArgumentException("Both widgetId and customFormId cannot be null. " +
                    "This service is only for configuring existing custom forms.");
        }

        if (widgetId == null) {
            selectedCustomForm = persistance.getDraftItem(customFormId);

            if (selectedCustomForm == null) {
                throw new CustomFormNotFoundException("Cannot find custom form by Id=" + customFormId);
            }

            site = persistance.getSite(selectedCustomForm.getSiteId());
        } else {
            widgetCustomForm = userRightManager.getSiteRight().getWidgetItemForEditInPresentationalService(
                    widgetId);
            site = widgetCustomForm.getSite();
            selectedCustomForm = (DraftCustomForm) widgetCustomForm.getDraftItem();

            if (selectedCustomForm == null) {
                throw new CustomFormNotFoundException("It appears that widget by Id=" + widgetId + " has no item.");
            }

            widgetTitle = new WidgetTitleGetter(widgetCustomForm, "New Forms & Registrations");
        }

        final ShowFormService showFormService = new ShowFormService();
        showFormService.fillFormItems(FormType.CUSTOM_FORM, selectedCustomForm.getId());
        initFormItems = showFormService.getInitFormItems();
        existingFormItems = showFormService.getExistingFormItems();

        getContext().getHttpServletRequest().setAttribute("customFormService", this);
    }

    public WidgetItem getWidgetCustomForm() {
        return widgetCustomForm;
    }

    public DraftCustomForm getSelectedCustomForm() {
        return selectedCustomForm;
    }

    public Site getSite() {
        return site;
    }

    public WidgetTitle getWidgetTitle() {
        return widgetTitle;
    }

    public DraftForm getForm() {
        return selectedCustomForm;
    }

    public FormManager getFormManager() {
        return formManager;
    }

    public List<FormItem> getExistingFormItems() {
        return existingFormItems;
    }

    public List<FormItemName> getInitFormItems() {
        return initFormItems;
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final FormManager formManager = new FormManager();
    private List<FormItemName> initFormItems = new ArrayList<FormItemName>();
    private List<FormItem> existingFormItems = new ArrayList<FormItem>();
    private WidgetTitle widgetTitle;
    private Site site;
    private WidgetItem widgetCustomForm;
    private DraftCustomForm selectedCustomForm;

}
