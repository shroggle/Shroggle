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

import com.shroggle.util.ServiceLocator;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.SiteType;
import com.shroggle.entity.User;
import com.shroggle.presentation.AbstractService;
import com.shroggle.logic.user.UsersManager;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.WebContext;

/**
 * @author Balakirev Anatoliy
 */

@RemoteProxy
public class UpdatePaymentInfoService extends AbstractService {

    @RemoteMethod
    public String execute(final int selectedSiteId) throws Exception {
        final User user = new UsersManager().getLogined().getUser();
        final UpdatePaymentInfoData data = new UpdatePaymentInfoData(ServiceLocator.getPersistance().getSites(user.getUserId(), new SiteAccessLevel[]{SiteAccessLevel.ADMINISTRATOR}, SiteType.COMMON), selectedSiteId, user);
        final WebContext webContext = getContext();
        webContext.getHttpServletRequest().setAttribute("updatePaymentInfoData", data);
        return webContext.forwardToString("/payment/updatePaymentInfoInternal.jsp");
    }

}
