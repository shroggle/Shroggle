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

import com.shroggle.exception.UserNotFoundException;
import com.shroggle.logic.user.UserManager;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

@RemoteProxy
public class ResentAnActivationEmailService {

    @RemoteMethod
    public void execute(final String email) {
        try {
            new UserManager(email).sendActivationOrInvitationEmails();
        } catch (final UserNotFoundException exception) {
            // All rights.
        }
    }

}