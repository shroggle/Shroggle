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

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;
import com.shroggle.util.html.HtmlUtil;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Artem Stasuk
 */
public class PageResourcesAcceleratorInternalLazy implements PageResourcesAccelerator {

    public PageResourcesAcceleratorInternalLazy(final PageResourcesAccelerator accelerator) {
        this.accelerator = accelerator;
    }

    @Override
    public PageResourcesValue execute(final PageResourcesContext context, final String name)
            throws IOException, ServletException {
        final Config config = ServiceLocator.getConfigStorage().get();

        return new PageResourcesValue(
                HtmlUtil.getTag(HtmlUtil.getMimeByName(name), "http://" + config.getApplicationUrl()
                        + "/pageResourcesInternalLazy.action?name="
                        + name + "&version="
                        + ApplicationVersionNormalizer.getNormalizedApplicationVersion()));
    }

    PageResourcesAccelerator getAccelerator() {
        return accelerator;
    }

    private final PageResourcesAccelerator accelerator;

}