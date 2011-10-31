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
package com.shroggle.logic.site.page;

import com.shroggle.entity.Site;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.exception.UserException;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;

/**
 * Author: dmitry.solomadin
 */
public class PageTreeTextCreator {

    public String execute(final int siteId, final SiteShowOption siteShowOption) {
        final Site site;
        try {
            final UserManager userManager = new UsersManager().getLogined();
            site = userManager.getRight().getSiteRight().getSiteForView(siteId).getSite();
        } catch (final SiteNotFoundException exception) {
            return "Could not load the tree.";
        } catch (final UserException exception) {
            return "Could not load the tree.";
        }

        return new DefaultMenuPagesHtmlTextCreator().create(site, siteShowOption);
    }
}
