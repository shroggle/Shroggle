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
package com.shroggle.presentation.form.customization;

import com.shroggle.entity.Form;
import com.shroggle.exception.FormNotFoundException;
import com.shroggle.logic.form.customization.CustomizeManageRecordsManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class ShowCustomizeManageRecordsWindowService extends AbstractService {

    @RemoteMethod
    public String execute(final Integer formId) throws Exception {
        final UserManager userManager = new UsersManager().getLogined();
        final Form form = persistance.getFormById(formId);
        if (form == null) {
            throw new FormNotFoundException("Unable to find form by id = " + formId + ".");
        }
        customizeManageRecordsManager = CustomizeManageRecordsManager.getExistingOrConstructNew(form, userManager.getUserId());

        getContext().getHttpServletRequest().setAttribute("service", this);
        return getContext().forwardToString("/site/form/customization/customizeManageRecords.jsp");
    }

    public CustomizeManageRecordsManager getCustomizeManageRecordsManager() {
        return customizeManageRecordsManager;
    }

    private CustomizeManageRecordsManager customizeManageRecordsManager;
    private final Persistance persistance = ServiceLocator.getPersistance();
}
