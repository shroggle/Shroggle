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

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;
import com.shroggle.util.config.ConfigStorage;

/**
 * @author Artem Stasuk
 */
class CreateUserFullValidate extends CreateUserValidateNoBotCode {

    public CreateUserFullValidate(final CreateUserValidate validate) {
        this.validate = validate;
    }

    @Override
    public void execute(final CreateUserAction action) {
        super.execute(action);
        validate.execute(action);

        final CreateUserRequest request = action.getRequest();
        if (request.getPass() == null || request.getPass().trim().isEmpty()) {
            action.addValidationError("Please fill the password field", "password");
        } else if (!request.getPass().equals(request.getPasswordConfirm())) {
            action.addValidationError("Please enter the correct confirm password field", "password");
        }

        final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
        final Config config = configStorage.get();
        if (config.getRegistration().isConfirm()) {
            if (request.getLastName() == null || request.getLastName().isEmpty()) {
                action.addValidationError("Please enter your last name", "lastName");
            }
            if (request.getTelephone() == null || request.getTelephone().isEmpty()) {
                action.addValidationError("Please enter your telephone", "telephone");
            }
        }

        if (!request.isAgree()) {
            action.addValidationError("Please agree to the terms and conditions.");
        }
    }

    private final CreateUserValidate validate;

}