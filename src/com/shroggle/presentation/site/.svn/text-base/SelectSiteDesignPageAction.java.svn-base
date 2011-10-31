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

import com.shroggle.entity.User;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.exception.UserException;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.util.ServiceLocator;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;


@UrlBinding("/selectSiteDesignPage.action")
public class SelectSiteDesignPageAction extends Action implements LoginedUserInfo {

    @DefaultHandler
    public Resolution show() {
        try {
            final UserManager userManager = new UsersManager().getLogined();
            loginedUser = userManager.getUser();
            userManager.getRight().getSiteRight().getSiteForEdit(siteId).getSite();
        } catch (final UserException exception) {
            return resolutionCreator.loginInUser(this);
        } catch (final SiteNotFoundException exception) {
            return resolutionCreator.loginInUser(this);
        }

        return resolutionCreator.forwardToUrl("/WEB-INF/pages/selectSiteDesignPage.jsp");
    }

    public User getLoginUser() {
        return loginedUser;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public boolean isCreateChildSite() {
        return createChildSite;
    }

    public void setCreateChildSite(boolean createChildSite) {
        this.createChildSite = createChildSite;
    }

    private int siteId;
    private boolean createChildSite;
    private User loginedUser;
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();

}