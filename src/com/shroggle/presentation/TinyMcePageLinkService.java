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
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.List;

/**
 * @author Artem Stasuk
 */
@RemoteProxy
public class TinyMcePageLinkService {

    @RemoteMethod
    public List<SiteTitlePageName> execute(final Integer siteId) {
        final Persistance persistance = ServiceLocator.getPersistance();
        final UserManager userManager = new UsersManager().getLogined();
        if (siteId != null) {
            return persistance.getSiteTitlePageNamesBySiteId(siteId);
        } else {
            return persistance.getSiteTitlePageNamesByUserId(userManager.getUserId());
        }
    }

}
