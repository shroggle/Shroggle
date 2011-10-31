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

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Artem Stasuk
 */
public class PageResourcesAcceleratorCache implements PageResourcesAccelerator {

    public PageResourcesAcceleratorCache(final PageResourcesAccelerator accelerator) {
        this.accelerator = accelerator;
    }

    @Override
    public PageResourcesValue execute(final PageResourcesContext context, final String name)
            throws IOException, ServletException {
        PageResourcesValue value = values.get(name.hashCode());

        if (value == null) {
            value = accelerator.execute(context, name);
            values.putIfAbsent(name.hashCode(), value);
        }

        return value;

    }

    private final PageResourcesAccelerator accelerator;
    private final ConcurrentMap<Integer, PageResourcesValue> values =
            new ConcurrentHashMap<Integer, PageResourcesValue>();

}