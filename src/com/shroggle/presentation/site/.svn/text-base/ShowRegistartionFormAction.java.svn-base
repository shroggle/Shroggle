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
import com.shroggle.logic.accessibility.Right;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.StartAction;
import com.shroggle.util.MD5;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByClassProperty;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: dmitry.solomadin
 * Date: 16.02.2009
 */
@UrlBinding("/site/showRegistrationForm.action")
public class ShowRegistartionFormAction extends Action {

    @SynchronizeByClassProperty(
            entityClass = DraftForm.class,
            entityIdFieldPath = "formId")
    @DefaultHandler
    public Resolution execute() throws IOException, ServletException {
        final DraftRegistrationForm registrationForm = persistance.getRegistrationFormById(formId);

        Map<ItemType, String> parameterMap = new HashMap<ItemType, String>();
        if (showForInvited) {
            //todo add forwarding to page with message about wrong link. For every exception.
            if (registrationForm == null) {
                return new ForwardResolution(StartAction.class);
            }

            final Site site = persistance.getSite(registrationForm.getSiteId());
            final User user = persistance.getUserById(visitorId);
            if (site == null || user == null) {
                return new ForwardResolution(StartAction.class);
            }

            final UserOnSiteRight visitorOnSiteRight = persistance.getUserOnSiteRightById(new UserOnSiteRightId(user, site));

            if (visitorOnSiteRight == null || !visitorOnSiteRight.getVisitorStatus().equals(VisitorStatus.PENDING)) {
                return new ForwardResolution(StartAction.class);
            }

            if (StringUtil.isNullOrEmpty(confirmCode) || !confirmCode.equals(MD5.crypt(user.getEmail() + user.getUserId()))) {
                return new ForwardResolution(StartAction.class);
            }

            String registrationParameters = "&invitedVisitorId=" + visitorId + "&formId=" + formId;
            parameterMap.put(ItemType.REGISTRATION, registrationParameters);
        } else {
            final User user = new UsersManager().getLoginedUser();
            final Integer loginUserId = user != null ? user.getUserId() : null;

            // We should leave this check otherwise registration will just won't work.
            if (!Right.isAuthorizedUser(registrationForm, loginUserId)) {
                return resolutionCreator.loginInUser(ShowRegistartionFormAction.this);
            }

            if (showFromAddRecord) {
                String registrationParameters = "&showFromAddRecord=" + true;
                parameterMap.put(ItemType.REGISTRATION, registrationParameters);
            }
        }

        final WidgetItem widgetRegistration = new WidgetItem();
        widgetRegistration.setDraftItem(registrationForm);

        final User user = new UsersManager().getLoginedUser();
        return resolutionCreator.showWidgetPreview(
                widgetRegistration, getContext().getServletContext(), (user != null ? user.getUserId() : null), parameterMap);
    }

    public void setFormId(final int formId) {
        this.formId = formId;
    }

    public void setConfirmCode(String confirmCode) {
        this.confirmCode = confirmCode;
    }

    public void setVisitorId(int visitorId) {
        this.visitorId = visitorId;
    }

    public void setShowForInvited(boolean showForInvited) {
        this.showForInvited = showForInvited;
    }

    public void setShowFromAddRecord(boolean showFromAddRecord) {
        this.showFromAddRecord = showFromAddRecord;
    }

    private int formId;
    private String confirmCode;
    private int visitorId;
    private boolean showForInvited;
    private boolean showFromAddRecord;
    private ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
}
