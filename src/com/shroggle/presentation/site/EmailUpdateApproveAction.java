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

import com.shroggle.entity.EmailUpdateRequest;
import com.shroggle.entity.User;
import com.shroggle.exception.CannotFindEmailUpdateRequestException;
import com.shroggle.exception.UpdateRequestNotApplicableForThisUserException;
import com.shroggle.exception.UserNotFoundException;
import com.shroggle.presentation.Action;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByClassProperty;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/account/emailUpdateApprove.action")
public class EmailUpdateApproveAction extends Action {

    @SynchronizeByClassProperty(
            entityClass = User.class,
            method = SynchronizeMethod.WRITE,
            entityIdFieldPath = "updateId")
    @DefaultHandler
    public Resolution show() {
        final EmailUpdateRequest emailUpdateRequest = persistance.getEmailUpdateRequestById(updateId);
        if (emailUpdateRequest == null) {
            throw new CannotFindEmailUpdateRequestException("Cannot find email update request by id = " + updateId);
        }

        final User user = persistance.getUserById(emailUpdateRequest.getUserId());
        if (user == null) {
            throw new UserNotFoundException("Cannot find user by id=" + emailUpdateRequest.getUserId());
        }

        if (emailUpdateRequest.getUserId() != user.getUserId()) {
            throw new UpdateRequestNotApplicableForThisUserException();
        }

        persistanceTransaction.execute(new Runnable() {

            public void run() {
                user.setEmail(emailUpdateRequest.getNewEmail());
            }

        });
        return new ForwardResolution("/account/emailUpdateApprove.jsp");
    }

    public String updateId;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}
