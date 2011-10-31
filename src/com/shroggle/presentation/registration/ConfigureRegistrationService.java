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
import com.shroggle.exception.RegistrationFormNotFoundException;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.form.FormManager;
import com.shroggle.logic.groups.GroupsTime;
import com.shroggle.logic.groups.GroupsTimeManager;
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
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameters;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class, params = {})
public class ConfigureRegistrationService extends AbstractService implements WithWidgetTitle, ShowForm {

    @SynchronizeByMethodParameters({@SynchronizeByMethodParameter(entityClass = DraftForm.class)})
    @RemoteMethod
    public void show(final Integer widgetId, final Integer registrationFormId) {
        final UserManager userManager = new UsersManager().getLogined();
        final UserRightManager userRightManager = userManager.getRight();

        if (widgetId == null && registrationFormId == null) {
            throw new IllegalArgumentException("Both widgetId and registrationFormId cannot be null. " +
                    "This service is only for configuring existing registration forms.");
        }

        if (widgetId == null) {
            selectedRegistrationForm = persistance.getRegistrationFormById(registrationFormId);

            if (selectedRegistrationForm == null) {
                throw new RegistrationFormNotFoundException("Cannot find registration form by Id=" + registrationFormId);
            }

            site = persistance.getSite(selectedRegistrationForm.getSiteId());
        } else {
            widgetRegistration = userRightManager.getSiteRight().getWidgetItemForEditInPresentationalService(
                    widgetId);
            selectedRegistrationForm = (DraftRegistrationForm) widgetRegistration.getDraftItem();
            site = widgetRegistration.getSite();

            if (selectedRegistrationForm == null) {
                throw new RegistrationFormNotFoundException("Cannot find registration form by");
            }

            widgetTitle = new WidgetTitleGetter(widgetRegistration, "New Account Registration Form");
        }

        final ShowFormService showFormService = new ShowFormService();
        showFormService.fillFormItems(FormType.REGISTRATION, selectedRegistrationForm.getFormId());
        initFormItems = showFormService.getInitFormItems();
        existingFormItems = showFormService.getExistingFormItems();

        if (site != null) {
            groups.addAll(site.getOwnGroups());
        }
        groupsWithTime = GroupsTimeManager.valueOf(selectedRegistrationForm.getGroupsWithTime());

        getContext().getHttpServletRequest().setAttribute("registrationService", this);
        getContext().getHttpServletRequest().setAttribute("showFromRegistration", true);
    }

    public Site getSite() {
        return site;
    }

    public WidgetItem getWidgetRegistration() {
        return widgetRegistration;
    }

    public DraftRegistrationForm getRegistrationForm() {
        return selectedRegistrationForm;
    }

    public WidgetTitle getWidgetTitle() {
        return widgetTitle;
    }

    public DraftForm getForm() {
        return selectedRegistrationForm;
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

    public List<Group> getGroups() {
        return groups;
    }

    public List<GroupsTime> getGroupsWithTime() {
        return groupsWithTime;
    }

    private List<FormItemName> initFormItems = new ArrayList<FormItemName>();
    private List<FormItem> existingFormItems = new ArrayList<FormItem>();
    private WidgetItem widgetRegistration;
    private DraftRegistrationForm selectedRegistrationForm;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final FormManager formManager = new FormManager();
    private Site site;
    private WidgetTitle widgetTitle;
    private final List<Group> groups = new ArrayList<Group>();
    private List<GroupsTime> groupsWithTime;

}
