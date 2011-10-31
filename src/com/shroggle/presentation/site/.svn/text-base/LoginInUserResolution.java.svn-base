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
import net.sourceforge.stripes.action.ForwardResolution;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Stasuk Artem
 */
public class LoginInUserResolution extends ForwardResolution {

    public LoginInUserResolution(final Action action) {
        super(ShowLoginAction.class);

        final HttpServletRequest request = action.getContext().getRequest();
        String enterUrl = request.getRequestURI();
        if (request.getQueryString() != null) {
            enterUrl += "?" + request.getQueryString();
        }
        request.setAttribute("enterUrl", enterUrl);
    }

}
