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
package com.shroggle.logic.registrationConfirmation;

import com.shroggle.entity.ChildSiteSettings;
import com.shroggle.entity.User;
import com.shroggle.util.TimeInterval;

import java.util.Date;

/**
 * @author Balakirev Anatoliy
 *         Date: 14.08.2009
 */
public class RegistrationConfirmationManager {

    public static boolean isLinkExpired(final User user) {
        return user != null && user.getRegistrationDate() != null && user.getRegistrationDate().before(new Date(System.currentTimeMillis() - TimeInterval.SEVEN_DAYS.getMillis() + 1));
    }

    public static boolean isLinkExpired(final ChildSiteSettings childSiteSettings) {
        return childSiteSettings != null && childSiteSettings.getCreatedDate().before(new Date((System.currentTimeMillis() - TimeInterval.TEN_DAYS.getMillis()) + 1));
    }
}
