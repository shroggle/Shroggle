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

import com.shroggle.util.ServiceLocator;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

/**
 * Author: Artem Stasuk (artem).
 * </p>
 * Date: 06.09.2008
 */
@UrlBinding(value = "/applicationVersion.action")
public class ApplicationVersionAction extends Action {

    public Resolution execute() {
        applicationVersion = ServiceLocator.getFileSystem().getApplicationVersion();
        return new ForwardResolution("/applicationVersion.jsp");
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    private String applicationVersion;
}
