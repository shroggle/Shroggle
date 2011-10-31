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
package com.shroggle.presentation.gallery;

import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

/**
 * @author Stasuk Artem
 */
@UrlBinding("/getSitePages.action")
public class GetSitePagesAction extends Action {

    @DefaultHandler
    public Resolution execute() throws Exception {
        final UserManager userManager = new UsersManager().getLogined();
        getHttpServletRequest().setAttribute("sitePages", userManager.getRight().getSiteRight().getSiteForView(siteId).getPages());
        return new ForwardResolution("/gallery/sitePagesSelect.jsp");
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    private int siteId;
}
