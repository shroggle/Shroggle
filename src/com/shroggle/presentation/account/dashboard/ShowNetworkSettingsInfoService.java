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
package com.shroggle.presentation.account.dashboard;

import com.shroggle.entity.ChildSiteSettings;
import com.shroggle.exception.ChildSiteSettingsNotFoundException;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author Balakirev Anatoliy
 *         Date: 07.08.2009
 */
@RemoteProxy
public class ShowNetworkSettingsInfoService extends AbstractService {
    @RemoteMethod
    public String execute(final int childSiteSettingsId) throws Exception {
        new UsersManager().getLogined();

        ChildSiteSettings settings = persistance.getChildSiteSettingsById(childSiteSettingsId);
        if(settings == null){
            throw new ChildSiteSettingsNotFoundException("Can`t find child site settings by id = " + childSiteSettingsId);
        }

        getContext().getHttpServletRequest().setAttribute("settings", settings);
        return getContext().forwardToString("/account/dashboard/networkSettingsInfo.jsp");
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
}
