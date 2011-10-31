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
package com.shroggle.presentation;

import com.shroggle.entity.SiteTitlePageName;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.List;

/**
 * @author Artem Stasuk
 */
@UrlBinding("/tinymce/plugins/pagelink/dialog.action")
public class TinyMcePageLinkAction extends Action {

    @DefaultHandler
    public Resolution execute() {
        final UserManager userManager = new UsersManager().getLogined();
        if (siteId != null) {
            siteTitlePageNames = persistance.getSiteTitlePageNamesBySiteId(siteId);
        } else {
            siteTitlePageNames = persistance.getSiteTitlePageNamesByUserId(userManager.getUserId());
        }

        return resolutionCreator.forwardToUrl("/tinymce/plugins/pagelink/dialog.jsp");
    }

    public List<SiteTitlePageName> getSiteTitlePageNames() {
        return siteTitlePageNames;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    private Integer siteId;
    private List<SiteTitlePageName> siteTitlePageNames;
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
