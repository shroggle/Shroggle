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
package com.shroggle.presentation.form.filledForms;

import com.shroggle.entity.DraftForm;
import com.shroggle.entity.FilledForm;
import com.shroggle.entity.User;
import com.shroggle.exception.FilledFormNotFoundException;
import com.shroggle.logic.form.FormData;
import com.shroggle.logic.form.FormManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Random;

/**
 * @author dmitry.solomadin, Artem Stasuk
 */
@RemoteProxy
public class ShowEditFilledFormService extends ServiceWithExecutePage {

    @RemoteMethod
    public String execute(final int filledFormId, final Integer linkedFilledFormId) throws IOException, ServletException {
        usersManager.getLogined();

        filledForm = persistance.getFilledFormById(filledFormId);
        if (filledForm == null) {
            throw new FilledFormNotFoundException("Cannot find filled form by Id=" + filledFormId);
        }

        final FormData form = FormManager.constructFormByFilledForm(filledForm, false);
        final DraftForm trueForm = persistance.getFormById(filledForm.getFormId());

        this.linkedFilledFormId = linkedFilledFormId;
        uniqueWidgetId = -(new Random().nextInt());

        getContext().getHttpServletRequest().setAttribute("settingsId", filledForm.getChildSiteSettingsId());
        getContext().getHttpServletRequest().setAttribute("childSiteUserId", filledForm.getUser() != null ? filledForm.getUser().getUserId() : null);
        getContext().getHttpServletRequest().setAttribute("form", form);
        getContext().getHttpServletRequest().setAttribute("widgetId", uniqueWidgetId);
        getContext().getHttpServletRequest().setAttribute("loginedVisitor", new User());
        getContext().getHttpServletRequest().setAttribute("siteId", trueForm == null || trueForm.getSiteId() <= 0 ? 0 : trueForm.getSiteId());
        getContext().getHttpServletRequest().setAttribute("prefilledForm", filledForm);
        getContext().getHttpServletRequest().setAttribute("showFromEditRecord", true);
        return executePage("/site/render/editFormRecord.jsp");
    }

    public FilledForm getFilledForm() {
        return filledForm;
    }

    public int getUniqueWidgetId() {
        return uniqueWidgetId;
    }

    public Integer getLinkedFilledFormId() {
        return linkedFilledFormId;
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final UsersManager usersManager = new UsersManager();
    private FilledForm filledForm;
    private int uniqueWidgetId;
    private Integer linkedFilledFormId;

}