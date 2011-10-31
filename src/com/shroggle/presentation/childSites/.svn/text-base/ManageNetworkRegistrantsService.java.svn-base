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

package com.shroggle.presentation.childSites;

import com.shroggle.entity.*;
import com.shroggle.exception.FilledFormNotFoundException;
import com.shroggle.exception.UserNotFoundException;
import com.shroggle.logic.childSites.childSiteRegistration.ChildSiteRegistrationManager;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.form.FormData;
import com.shroggle.logic.form.FormManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.render.RenderWidgetForm;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameters;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @author Balakirev Anatoliy
 */

@RemoteProxy
public class ManageNetworkRegistrantsService extends AbstractService {

    @RemoteMethod
    @SynchronizeByMethodParameters({
            @SynchronizeByMethodParameter(
                    entityClass = User.class),
            @SynchronizeByMethodParameter(
                    entityClass = FilledForm.class)})
    public String execute(final int visitorId, final int filledFormId, final int parentSiteId,
                          final boolean showVisitor) throws Exception {
        this.showVisitor = showVisitor;

        new UsersManager().getLogined();
        user = persistance.getUserById(visitorId);
        if (user == null) {
            throw new UserNotFoundException("Cannot find user by Id = " + visitorId);
        }
        filledForm = persistance.getFilledFormById(filledFormId);
        if (filledForm == null) {
            throw new FilledFormNotFoundException("Cannot find filled form by Id = " + filledFormId);
        }
        this.parentSiteId = parentSiteId;
        formData = FormManager.constructFormByFilledForm(filledForm, true);
        if (filledForm.getChildSiteSettingsId() != null) {
            childSiteSettings = persistance.getChildSiteSettingsById(filledForm.getChildSiteSettingsId());
            blueprints = new ArrayList<Site>();
            if (childSiteSettings != null && childSiteSettings.getChildSiteRegistration() != null) {
                final ChildSiteRegistrationManager manager = new ChildSiteRegistrationManager(childSiteSettings.getChildSiteRegistration());
                blueprints.addAll(manager.getUsedBlueprints());
            }
        }

        uniqueWidgetId = -(new Random().nextInt());

        getContext().getHttpServletRequest().setAttribute("settingsId", filledForm.getChildSiteSettingsId());
        getContext().getHttpServletRequest().setAttribute("childSiteUserId", filledForm.getUser() != null ? filledForm.getUser().getUserId() : null);
        getContext().getHttpServletRequest().setAttribute("service", this);
        RenderWidgetForm.addFormParameters(getContext().getHttpServletRequest(), uniqueWidgetId, parentSiteId, user, formData,
                true, filledForm);
        RenderWidgetForm.setShowFromEditRecord(getContext().getHttpServletRequest(), true);
        return getContext().forwardToString("/account/editChildSiteRegistrationWindow.jsp");
    }

    @RemoteMethod
    @SynchronizeByMethodParameters({
            @SynchronizeByMethodParameter(
                    entityClass = User.class),
            @SynchronizeByMethodParameter(
                    entityClass = FilledForm.class)})
    public String updateNetworkVisitorForm(final int visitorId, final int filledFormId, final List<FilledFormItem>
            filledFormItems, final int parentSiteId) throws IOException, ServletException {
        new UsersManager().getLogined();

        user = persistance.getUserById(visitorId);
        if (user == null) {
            throw new UserNotFoundException("Cannot find user by Id = " + visitorId);
        }
        filledForm = persistance.getFilledFormById(filledFormId);
        if (filledForm == null) {
            throw new FilledFormNotFoundException("Cannot find filled form by Id = " + filledFormId);
        }
        this.parentSiteId = parentSiteId;
        ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
            public void run() {
                FilledFormManager.updateFilledFormItems(filledFormItems, filledForm);
            }
        });
        filledForms = FilledFormManager.getFilledFormsByNetworkSiteId(parentSiteId);
        getContext().getHttpServletRequest().setAttribute("siteName", ServiceLocator.getPersistance().getSite(parentSiteId).getTitle());
        getContext().getHttpServletRequest().setAttribute("parentSiteId", parentSiteId);
        getContext().getHttpServletRequest().setAttribute("filledForms", filledForms);
        return getContext().forwardToString("/account/networkRegistrantsTable.jsp");
    }


    public User getVisitor() {
        return user;
    }

    public FormManager getFormManager() {
        return formManager;
    }

    public List<FilledForm> getFilledForms() {
        return filledForms;
    }

    public int getParentSiteId() {
        return parentSiteId;
    }

    public ChildSiteSettings getChildSiteSettings() {
        return childSiteSettings;
    }

    public List<Site> getBlueprints() {
        return blueprints;
    }

    public boolean isShowVisitor() {
        return showVisitor;
    }

    public FormData getFormData() {
        return formData;
    }

    public FilledForm getFilledForm() {
        return filledForm;
    }

    public int getUniqueWidgetId() {
        return uniqueWidgetId;
    }

    private FormData formData;
    private User user;
    private boolean showVisitor;
    private int parentSiteId;
    private int uniqueWidgetId;
    private FilledForm filledForm;
    private List<FilledForm> filledForms;
    private ChildSiteSettings childSiteSettings;
    private List<Site> blueprints;
    private FormManager formManager = new FormManager();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
}