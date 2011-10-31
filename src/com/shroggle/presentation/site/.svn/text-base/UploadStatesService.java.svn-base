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

import com.shroggle.presentation.AbstractService;
import com.shroggle.entity.Country;
import com.shroggle.logic.countries.CountryManager;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.WebContext;


@RemoteProxy
public class UploadStatesService extends AbstractService {

    @RemoteMethod
    public String getStates(final Country country, final int widgetId) throws Exception {
        final WebContext webContext = getContext();
        webContext.getHttpServletRequest().setAttribute("widgetId", widgetId);
        webContext.getHttpServletRequest().setAttribute("states", new CountryManager(country).getStatesByCountry());
        webContext.getHttpServletRequest().setAttribute("state", "");
        return webContext.forwardToString("/account/states.jsp");
    }
}
