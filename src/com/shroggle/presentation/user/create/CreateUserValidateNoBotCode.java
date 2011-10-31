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
package com.shroggle.presentation.user.create;

import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.ServiceLocator;

/**
 * @author Artem Stasuk
 */
class CreateUserValidateNoBotCode implements CreateUserValidate {

    public void execute(final CreateUserAction action) {
        final CreateUserRequest request = action.getRequest();
        final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
        final String noBotCode = sessionStorage.getNoBotCode(action, "createUser");
        sessionStorage.setNoBotCode(action, "createUser", null);

        if (noBotCode == null || request.getNoBotCodeConfirm() == null) {
            action.addValidationError("Please complete the text verification field.", "noBotCodeConfirm");
        } else if (!noBotCode.equals(request.getNoBotCodeConfirm())) {
            action.addValidationError("Please enter the correct verification code.", "noBotCodeConfirm");
        }
    }

}
