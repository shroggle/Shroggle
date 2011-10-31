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

import com.shroggle.exception.IncorrectEmailException;
import com.shroggle.exception.NotUniqueUserEmailException;
import com.shroggle.exception.NullOrEmptyEmailException;
import com.shroggle.logic.user.UserEmailChecker;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;

import java.util.Locale;

/**
 * @author Artem Stasuk
 */
class CreateUserValidateEmail implements CreateUserValidate {

    public void execute(final CreateUserAction action) {
        final International international =
                ServiceLocator.getInternationStorage().get("createUser", Locale.US);
        try {
            new UserEmailChecker().execute(action.getRequest().getEmail(), null);
        } catch (final NullOrEmptyEmailException exception) {
            action.addValidationError(international.get("emailEmptyError"), "email");
        } catch (final IncorrectEmailException exception) {
            action.addValidationError(international.get("emailNotCorrect"), "email");
        } catch (final NotUniqueUserEmailException exception) {
            action.addValidationError(international.get("emailNotAvalible"), "email");
        }
    }

}
