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

import com.shroggle.entity.*;
import com.shroggle.exception.ContactUsNotFoundException;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.form.FormManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.widget.WidgetTitle;
import com.shroggle.logic.widget.WidgetTitleGetter;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.presentation.form.ShowForm;
import com.shroggle.presentation.form.ShowFormService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.WebContext;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.*;

/**
 * @author Balakirev Anatoliy, dmitry.solomadin
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class, params = {})
public class ConfigureContactUsService extends AbstractService implements WithWidgetTitle, ShowForm {

    @RemoteMethod
    public void execute(final Integer widgetId, final Integer contactUsId) {
        final UserManager userManager = new UsersManager().getLogined();
        final UserRightManager userRightManager = userManager.getRight();

        loginedUserEmail = userManager.getUser().getEmail();

        if (widgetId == null && contactUsId == null) {
            throw new IllegalArgumentException("Both widgetId and contactUsId cannot be null. " +
                    "This service is only for configuring existing contact uses.");
        }

        if (widgetId == null) {
            contactUs = persistance.getDraftItem(contactUsId);

            if (contactUs == null) {
                throw new ContactUsNotFoundException("Cannot find contact us by Id=" + contactUsId);
            }

            site = persistance.getSite(contactUs.getSiteId());
        } else {
            widget = userRightManager.getSiteRight().getWidgetItemForEditInPresentationalService(
                    widgetId);
            site = widget.getSite();
            contactUs = (DraftContactUs) widget.getDraftItem();

            if (contactUs == null) {
                throw new ContactUsNotFoundException("It appears that widget by Id=" + widgetId + " has no item.");
            }

            widgetTitle = new WidgetTitleGetter(widget, "New Contact Us");
        }

        final ShowFormService showFormService = new ShowFormService();
        showFormService.fillFormItems(FormType.CONTACT_US, contactUs.getFormId());
        initFormItems = showFormService.getInitFormItems();
        existingFormItems = showFormService.getExistingFormItems();

        header = contactUs.getDescription();

        getContext().getHttpServletRequest().setAttribute("contactUsService", this);
    }

    public WidgetTitle getWidgetTitle() {
        return widgetTitle;
    }

    public String getHeader() {
        return header;
    }

    public DraftContactUs getContactUs() {
        return contactUs;
    }

    public International getInternational() {
        return international;
    }

    public String getLoginedUserEmail() {
        return loginedUserEmail;
    }

    public DraftForm getForm() {
        return contactUs;
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

    public WidgetItem getWidget() {
        return widget;
    }

    public Site getSite() {
        return site;
    }

    private List<FormItemName> initFormItems = new ArrayList<FormItemName>();
    private List<FormItem> existingFormItems = new ArrayList<FormItem>();
    private DraftContactUs contactUs;
    private String header;
    private WidgetItem widget;
    private WidgetTitle widgetTitle;
    private Site site;
    private String loginedUserEmail;
    private final FormManager formManager = new FormManager();
    private final International international = ServiceLocator.getInternationStorage().get("configureContactUs", Locale.US);
    private final Persistance persistance = ServiceLocator.getPersistance();

}