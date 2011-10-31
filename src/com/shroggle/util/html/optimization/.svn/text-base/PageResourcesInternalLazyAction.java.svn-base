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

package com.shroggle.util.html.optimization;

import com.shroggle.presentation.Action;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.html.HtmlUtil;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Stasuk Artem
 */
@UrlBinding("/pageResourcesInternalLazy.action")
public final class PageResourcesInternalLazyAction extends Action {

    public Resolution execute() throws IOException, ServletException {
        final PageResourcesContext context = new PageResourcesContext(this);
        final PageResourcesAcceleratorInternalLazy internalLazy
                = (PageResourcesAcceleratorInternalLazy) ServiceLocator.getRootPageResourcesAccelerator();
        final PageResourcesValue value = internalLazy.getAccelerator().execute(context, name);
        return new StreamingResolution(HtmlUtil.getMimeByName(name), value.getValue());
    }

    public void setName(final String name) {
        this.name = name;
    }

    private String name;

}