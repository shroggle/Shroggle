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

package com.shroggle.presentation.user;

import com.shroggle.presentation.Action;
import com.shroggle.util.ResolutionNotFound;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: Igor Kanshin (igor).
 * </p>
 * Date: 10.10.2009
 */
@UrlBinding("/account/pageResourcesBlockGet.action")
public class FakeAction extends Action{

    @DefaultHandler
    public Resolution show() {
      HttpServletRequest request = getContext().getRequest();
      System.out.println("/account/pageResourcesBlockGet.action referer is " + request.getHeader("referer"));

      return new ResolutionNotFound();
    }

}