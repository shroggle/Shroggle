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
import com.shroggle.presentation.video.ActionWithLoginUser;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.List;

@UrlBinding("/map.action")
public class MapAction extends ActionWithLoginUser {

    @Override
    protected Resolution getResolution() {
        siteTitlePageNames = persistance.getWorkSiteTitlePageNames(null);
        return ServiceLocator.getResolutionCreator().forwardToUrl("/map.jsp");
    }

    public List<SiteTitlePageName> getSiteTitlePageNames() {
        return siteTitlePageNames;
    }

    private List<SiteTitlePageName> siteTitlePageNames;
    private final Persistance persistance = ServiceLocator.getPersistance();

}