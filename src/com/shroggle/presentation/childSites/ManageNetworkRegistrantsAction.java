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

package com.shroggle.presentation.childSites;

import com.shroggle.entity.FilledForm;
import com.shroggle.entity.User;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.site.LoginedUserInfo;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByLoginUser;
import com.shroggle.util.security.SecurityUser;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.List;

/**
 * @author Balakirev Anatoliy
 */

@UrlBinding("/account/manageNetworkRegistrantsAction.action")
public class ManageNetworkRegistrantsAction extends Action implements LoginedUserInfo {

    @SecurityUser
    @SynchronizeByLoginUser
    @DefaultHandler
    public Resolution show() {
        user = new UsersManager().getLoginedUser();
        filledForms = FilledFormManager.getFilledFormsByNetworkSiteId(parentSiteId);
        getContext().getRequest().setAttribute("siteName", ServiceLocator.getPersistance().getSite(parentSiteId).getTitle());
        getContext().getRequest().setAttribute("parentSiteId", parentSiteId);
        getContext().getRequest().setAttribute("filledForms", filledForms);
        return ServiceLocator.getResolutionCreator().forwardToUrl("/account/manageNetworkRegistrants.jsp");
    }

    public int getParentSiteId() {
        return parentSiteId;
    }

    public void setParentSiteId(int parentSiteId) {
        this.parentSiteId = parentSiteId;
    }

    public User getLoginUser() {
        return user;
    }

    public List<FilledForm> getFilledForms() {
        return filledForms;
    }

    private User user;
    private int parentSiteId;
    private List<FilledForm> filledForms;
}