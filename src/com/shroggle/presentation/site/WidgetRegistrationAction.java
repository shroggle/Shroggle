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
import com.shroggle.logic.form.FormData;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.persistance.Persistance;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import javax.servlet.ServletException;
import java.io.IOException;

@UrlBinding("/site/widgetRegistration.action")
public class WidgetRegistrationAction extends Action {

    public Resolution execute() throws IOException, ServletException {
        Integer loginedVisitorId;
        if (invitedVisitorId != null) {
            loginedVisitorId = invitedVisitorId;
        } else {
            final User user = new UsersManager().getLoginedUser();
            loginedVisitorId = user != null ? user.getUserId() : null;
        }

        if (loginedVisitorId != null) {
            loginedUser = persistance.getUserById(loginedVisitorId);
        }

        final WidgetItem widget = (WidgetItem) persistance.getWidget(widgetId);
        if (formId != null) {
            registrationForm = persistance.getRegistrationFormById(formId);
        } else if (returnToLogin || returnToForum) {
            //If we come here from login visual item then use default registration form.
            //Note that if we come here then user HAD specified default registration form.
            final Site site = widget.getSite();
            registrationForm = persistance.getRegistrationFormById(site.getDefaultFormId());
        } else {
            registrationForm = (DraftRegistrationForm) widget.getDraftItem();
        }

        if (registrationForm == null) {
            return resolutionCreator.forwardToUrl("/site/render/renderWidgetNotConfigure.jsp?widgetType=Registration");
        }

        formData = new FormData(registrationForm);

        this.siteId = registrationForm.getSiteId();
        final Site site = persistance.getSite(registrationForm.getSiteId());
        if (site != null) {
            siteName = StringUtil.isNullOrEmpty(site.getTitle()) ? site.getSubDomain() : site.getTitle();
        }

        final Integer registrationFormIdFilledInThisSession = sessionStorage.getRegistrationFormFilledInThisSession(this);
        if (registrationFormIdFilledInThisSession != null) {
            filledInThisSession = registrationFormIdFilledInThisSession == registrationForm.getFormId();
        }

        if (fillOutFormCompletely) {
            editVisitorDetails = true;
            getContext().getRequest().setAttribute("prefillWithEmail", prefillWithEmail);
        }

        if (getContext().getRequest().getParameter("filledFormToUpdateId") != null) {
            editVisitorDetails = true;
        }

        getContext().getRequest().setAttribute("siteId", registrationForm.getSiteId());
        getContext().getRequest().setAttribute("form", formData);
        getContext().getRequest().setAttribute("forcePrefill", editVisitorDetails || showForInvited);
        getContext().getRequest().setAttribute("loginedUser", loginedUser);
        getContext().getRequest().setAttribute("widgetId", widgetId);

        return resolutionCreator.forwardToUrl("/site/render/widgetRegistration.jsp");
    }

    public boolean isEditVisitorDetails() {
        return editVisitorDetails;
    }

    public void setEditVisitorDetails(boolean editVisitorDetails) {
        this.editVisitorDetails = editVisitorDetails;
    }

    public boolean isFilledInThisSession() {
        return filledInThisSession;
    }

    public int getSiteId() {
        return siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public int getWidgetId() {
        return widgetId;
    }

    public User getLoginedVisitor() {
        return loginedUser;
    }

    public void setWidgetId(int widgetId) {
        this.widgetId = widgetId;
    }

    public boolean isReturnToLogin() {
        return returnToLogin;
    }

    public boolean isReturnToForum() {
        return returnToForum;
    }

    public void setReturnToForum(boolean returnToForum) {
        this.returnToForum = returnToForum;
    }

    public void setReturnToLogin(boolean returnToLogin) {
        this.returnToLogin = returnToLogin;
    }

    public Integer getInvitedVisitorId() {
        return invitedVisitorId;
    }

    public void setInvitedVisitorId(Integer invitedVisitorId) {
        this.invitedVisitorId = invitedVisitorId;
    }

    public void setFormId(Integer formId) {
        this.formId = formId;
    }

    public boolean isShowFromAddRecord() {
        return showFromAddRecord;
    }

    public void setShowFromAddRecord(boolean showFromAddRecord) {
        this.showFromAddRecord = showFromAddRecord;
    }

    public boolean isFillOutFormCompletely() {
        return fillOutFormCompletely;
    }

    public void setFillOutFormCompletely(boolean fillOutFormCompletely) {
        this.fillOutFormCompletely = fillOutFormCompletely;
    }

    public String getPrefillWithEmail() {
        return prefillWithEmail;
    }

    public void setPrefillWithEmail(String prefillWithEmail) {
        this.prefillWithEmail = prefillWithEmail;
    }

    public boolean isShowForInvited() {
        return showForInvited;
    }

    public void setShowForInvited(boolean showForInvited) {
        this.showForInvited = showForInvited;
    }

    public boolean isShowAfterEditingMessage() {
        return showAfterEditingMessage;
    }

    public void setShowAfterEditingMessage(boolean showAfterEditingMessage) {
        this.showAfterEditingMessage = showAfterEditingMessage;
    }

    public FormData getFormData() {
        return formData;
    }

    public DraftRegistrationForm getRegistrationForm() {
        return registrationForm;
    }

    private FormData formData;

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
    private User loginedUser;
    private boolean filledInThisSession;
    private int widgetId;
    private int siteId;
    private String siteName;
    private DraftRegistrationForm registrationForm;

    private Integer invitedVisitorId;
    private Integer formId;
    //True if showing registration from add form record page.
    private boolean showFromAddRecord;
    //True if we came on registration from login block.
    private boolean returnToLogin;
    //True if we came on registration from forum block.
    private boolean returnToForum;
    //True if we are editing existing visitor details. That can be done if visitor will filled up registration form in this session.
    private boolean editVisitorDetails;

    private boolean showAfterEditingMessage;
    //Ture if visitor came from login via "already have an account?"
    private boolean fillOutFormCompletely;
    private String prefillWithEmail;

    //Ture if we are showing form for invited user(i.e. invitedVisitorId != null && formId != null)
    private boolean showForInvited;

}
