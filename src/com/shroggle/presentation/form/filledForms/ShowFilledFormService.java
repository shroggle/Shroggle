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

import com.shroggle.entity.FilledForm;
import com.shroggle.exception.FilledFormNotFoundException;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Dmitry Solomadin, Artem Stasuk
 */
@RemoteProxy
public class ShowFilledFormService extends ServiceWithExecutePage {

    @RemoteMethod
    public String execute(final int filledFormId) throws IOException, ServletException {
        usersManager.getLogined();

        filledForm = persistance.getFilledFormById(filledFormId);
        if (filledForm == null) {
            throw new FilledFormNotFoundException("Cannot find filled form by Id=" + filledFormId);
        }

        return executePage("/site/formRecordData.jsp");
    }

    public FilledForm getFilledForm() {
        return filledForm;
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final UsersManager usersManager = new UsersManager();
    private FilledForm filledForm;

}