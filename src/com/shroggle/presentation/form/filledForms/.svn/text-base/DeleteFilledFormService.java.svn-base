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
import com.shroggle.entity.DraftForm;
import com.shroggle.entity.FormType;
import com.shroggle.entity.User;
import com.shroggle.exception.FilledFormNotFoundException;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.visitor.VisitorManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author dmitry.solomadin, Artem Stasuk
 */
@RemoteProxy
public class DeleteFilledFormService {

    @RemoteMethod
    public void execute(final int filledFormId) {
        usersManager.getLogined();

        final FilledForm filledForm = persistance.getFilledFormById(filledFormId);
        if (filledForm == null) {
            throw new FilledFormNotFoundException("Cannot find filled form by Id=" + filledFormId);
        }

        persistanceTransaction.execute(new Runnable() {

            public void run() {
                if (filledForm.getType() == FormType.REGISTRATION) {
                    final User user = filledForm.getUser();

                    //In case that our form is registration form we need to remove user on site rights and then if there
                    //are no more rights for this vistior — remove user itself.
                    final DraftForm form = persistance.getFormById(filledForm.getFormId());
                    final VisitorManager visitorManager = new VisitorManager(user);
                    visitorManager.removeFilledFormFromVisitorOnSiteRight(form.getSiteId(), filledForm.getFilledFormId());
                    visitorManager.removeVisitor();
                } else {
                    FilledFormManager.remove(filledForm);
                }

                persistance.removeFilledForm(filledForm);
            }

        });
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final UsersManager usersManager = new UsersManager();

}