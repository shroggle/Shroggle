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

import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.UserOnSiteRight;
import com.shroggle.util.international.International;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;

import java.util.Locale;

/**
 * @author Artem Stasuk
 */
final class CreateUserSites {

    public void execute(final CreateUserAction action) {
        final CreateUserState state = action.getState();

        final String siteNamesAdmin;
        final String siteNamesEditor;
        if (state.getUserOnSiteRights() != null) {
            final StringBuilder siteNamesAdminBuilder = new StringBuilder("");
            final StringBuilder siteNamesEditorBuilder = new StringBuilder("");

            for (final UserOnSiteRight right : state.getUserOnSiteRights()) {
                final String siteTitle = StringUtil.getEmptyOrString(right.getId().getSite().getTitle()).trim();
                if (right.getSiteAccessType() == SiteAccessLevel.ADMINISTRATOR) {
                    siteNamesAdminBuilder.append(siteTitle);
                    siteNamesAdminBuilder.append(", ");
                } else if (right.getSiteAccessType() == SiteAccessLevel.EDITOR) {
                    siteNamesEditorBuilder.append(siteTitle);
                    siteNamesEditorBuilder.append(", ");
                }
            }
            siteNamesAdmin = normalizeNames(siteNamesAdminBuilder.toString());
            siteNamesEditor = normalizeNames(siteNamesEditorBuilder.toString());
        } else {
            siteNamesAdmin = "";
            siteNamesEditor = "";
        }

        final boolean hasAdminAcces = !StringUtil.isNullOrEmpty(siteNamesAdmin);
        final boolean hasEditorAcces = !StringUtil.isNullOrEmpty(siteNamesEditor);

        final International international = ServiceLocator.getInternationStorage().get("createUser", Locale.US);

        final String accessToSitesMessage;
        if (hasAdminAcces && hasEditorAcces) {
            accessToSitesMessage = international.get("invitationTextForAdminAndEditor", siteNamesAdmin, siteNamesEditor);
        } else if (hasAdminAcces) {
            accessToSitesMessage = international.get("invitationTextForAdmin", siteNamesAdmin);
        } else if (hasEditorAcces) {
            accessToSitesMessage = international.get("invitationTextForEditor", siteNamesEditor);
        } else {// Has no access // todo. Maybe we have to show message if this state is possible? Check it. Tolik.
            accessToSitesMessage = "";
        }
        action.getRequest().setHasAccessToSitesMessage(accessToSitesMessage);
    }

    private String normalizeNames(final String siteNames) {
        if (StringUtil.isNullOrEmpty(siteNames)) {
            return "";
        }
        String newSiteNames = siteNames.trim();
        return newSiteNames.replaceAll(",\\s*$", "");
    }

}
