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

import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.util.ServiceLocator;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

/**
 * @author Stasuk Artem
 */
@UrlBinding(value = "/account/loginInUserBlank.action")
public class ShowLoginAction extends Action {

    public Resolution execute() {
        enterUrl = (String) getContext().getRequest().getAttribute("enterUrl");
        return resolutionCreator.forwardToUrl("/account/loginInAccountBlank.jsp");
    }

    public String getEnterUrl() {
        return enterUrl;
    }

    private String enterUrl;
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();

}
