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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Artem Stasuk
 */
public class PageResourcesAcceleratorInternal implements PageResourcesAccelerator {

    public PageResourcesAcceleratorInternal(final PageResourcesAccelerator accelerator) {
        this.accelerator = accelerator;
    }

    @Override
    public PageResourcesValue execute(final PageResourcesContext context, final String name)
            throws IOException, ServletException {
        final PageResourcesValue value = accelerator.execute(context, name);
        final Config config = ServiceLocator.getConfigStorage().get();

        final PageResourcesInternalResult result = new PageResourcesInternalResult(name, value);
        results.put(name.hashCode(), result);

        return new PageResourcesValue(
                HtmlUtil.getTag(HtmlUtil.getMimeByName(name), "http://" + config.getApplicationUrl()
                        + "/pageResourcesInternal.action?name="
                        + name + "&version="
                        + ApplicationVersionNormalizer.getNormalizedApplicationVersion()));
    }

    public PageResourcesInternalResult getResult(final String name) {
        return results.get(name.hashCode());
    }

    private final PageResourcesAccelerator accelerator;
    private final ConcurrentMap<Integer, PageResourcesInternalResult> results =
            new ConcurrentHashMap<Integer, PageResourcesInternalResult>();

}