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
package com.shroggle.logic.accessibility;

import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.User;
import com.shroggle.presentation.account.accessPermissions.InviteUserHelper;
import com.shroggle.presentation.site.SiteAccessLevelHolderRequest;
import com.shroggle.util.ServiceLocator;

import java.util.Arrays;

/**
 * @author Balakirev Anatoliy
 */
public class UserOnSiteRightsCreator {

    public static void createRightsForAppAdmins(final Site site) {
        // Adding userOnSiteRights to this site for all admins.
        for (String email : ServiceLocator.getConfigStorage().get().getAdminEmails()) {
            final User user = ServiceLocator.getPersistance().getUserByEmail(email);
            if (user != null) {
                InviteUserHelper.createRightsForExistingUser(
                        Arrays.asList(new SiteAccessLevelHolderRequest(site.getSiteId(), SiteAccessLevel.ADMINISTRATOR)),
                        user,
                        true
                );
            }
        }
    }

}
