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

package com.shroggle.presentation.site.payment;

import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.site.LoginedUserInfo;
import com.shroggle.logic.user.UsersManager;
import net.sourceforge.stripes.action.*;

/**
 * @author Balakirev Anatoliy
 */

@UrlBinding("/account/updatePaymentInfo.action")
public class UpdatePaymentInfoAction extends Action implements LoginedUserInfo {

    @DefaultHandler
    public Resolution show() throws Exception {
        user = new UsersManager().getLoginedUser();
        if (user == null) {
            return resolutionCreator.loginInUser(this);
        }
        data = new UpdatePaymentInfoData(persistance.getSites(user.getUserId(), new SiteAccessLevel[]{SiteAccessLevel.ADMINISTRATOR}, SiteType.COMMON), selectedSiteId, user);
        return resolutionCreator.forwardToUrl("/payment/updatePaymentInfo.jsp");
    }

    public User getLoginUser() {
        return user;
    }

    public UpdatePaymentInfoData getData() {
        return data;
    }

    public void setSelectedSiteId(Integer selectedSiteId) {
        this.selectedSiteId = selectedSiteId;
    }

    private UpdatePaymentInfoData data;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private User user;
    private Integer selectedSiteId;
}
