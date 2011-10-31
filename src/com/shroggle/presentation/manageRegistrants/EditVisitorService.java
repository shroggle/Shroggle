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
package com.shroggle.presentation.manageRegistrants;

import com.shroggle.entity.*;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.exception.UserNotFoundException;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.form.FormManager;
import com.shroggle.logic.form.FormData;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.presentation.site.render.RenderWidgetForm;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class EditVisitorService extends ServiceWithExecutePage {

    private final Persistance persistance = ServiceLocator.getPersistance();

    private User visitorToEdit;

    @RemoteMethod
    public String show(final int visitorId, final int siteId) throws IOException, ServletException {
        new UsersManager().getLogined();

        visitorToEdit = persistance.getUserById(visitorId);

        if (visitorToEdit == null) {
            throw new UserNotFoundException("Cannot find user to edit by Id=" + visitorId);
        }

        final Site site = persistance.getSite(siteId);

        if (site == null) {
            throw new SiteNotFoundException("Cannot find site by Id=" + siteId);
        }

        final FilledForm filledRegistrationForm = FilledFormManager.getFirstRegistrationFilledFormForSite(visitorToEdit, site);
        final FormData formData = FormManager.constructFormByFilledForm(filledRegistrationForm, true);

        RenderWidgetForm.addFormParameters(getContext().getHttpServletRequest(), 0, siteId, visitorToEdit, formData, true, filledRegistrationForm);
        RenderWidgetForm.setShowFromEditRecord(getContext().getHttpServletRequest(), true);
        return executePage("/account/manageRegistrants/editRegisteredVisitor.jsp");
    }

    public User getVisitorToEdit() {
        return visitorToEdit;
    }
}
