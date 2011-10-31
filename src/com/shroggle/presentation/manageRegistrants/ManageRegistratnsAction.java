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
package com.shroggle.presentation.manageRegistrants;

import com.shroggle.entity.User;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.exception.UserException;
import com.shroggle.logic.manageRegistrants.ManageRegistrantsProvider;
import com.shroggle.logic.manageRegistrants.ManageRegistrantsSortType;
import com.shroggle.logic.paginator.Paginator;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.site.LoginedUserInfo;
import com.shroggle.util.ServiceLocator;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

/**
 * @author dmitry.solomadin
 */
@UrlBinding("/account/manageRegistrants.action")
public class ManageRegistratnsAction extends Action implements LoginedUserInfo {

    @DefaultHandler
    public Resolution show() {
        final UserManager userManager;
        final SiteManager siteManager;
        try {
            userManager = new UsersManager().getLogined();
            siteManager = userManager.getRight().getSiteRight().getSiteForEdit(siteId);
        } catch (final UserException exception) {
            return resolutionCreator.loginInUser(this);
        } catch (final SiteNotFoundException exception) {
            return resolutionCreator.loginInUser(this);
        }
        siteName = siteManager.getName();
        user = userManager.getUser();

        final Paginator paginator = new ManageRegistrantsProvider().executeWithBlankRequest(siteId);

        getContext().getRequest().setAttribute("paginator", paginator);
        getContext().getRequest().setAttribute("manageRegistrantsSiteId", siteId);
        getContext().getRequest().setAttribute("manageRegistrantsSiteName", siteManager.getName());
        getContext().getRequest().setAttribute("manageRegistrantsSortType", ManageRegistrantsSortType.FIRST_NAME);
        getContext().getRequest().setAttribute("manageRegistrantsDesc", false);
        getContext().getRequest().setAttribute("availableGroups", siteManager.getSite().getOwnGroups());

        return resolutionCreator.forwardToUrl("/account/manageRegistrants/manageRegistrantsForAction.jsp");
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public User getLoginUser() {
        return user;
    }

    public String getSiteName() {
        return siteName;
    }

    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private int siteId;
    private String siteName;
    private User user;

}
