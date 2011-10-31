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

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.controller.DispatcherServlet;
import net.sourceforge.stripes.controller.StripesFilter;
import net.sourceforge.stripes.mock.MockRoundtrip;
import net.sourceforge.stripes.mock.MockServletContext;
import org.junit.Assert;

import java.util.*;

/**
 * @author Stasuk Artem
 */
public class ActionTestUtil {

    public static void assertValidationErrors(final Action action, final String... needErrors) {
        final ActionBeanContext context = action.getContext();
        if (context == null) {
            throw new UnsupportedOperationException("Set action bean context for this assert!");
        }

        final Set<String> tempNeedErrors = new HashSet<String>(Arrays.asList(needErrors));
        Assert.assertEquals(context.getValidationErrors().keySet(), tempNeedErrors);
    }

    public static MockRoundtrip createMockRoundtrip(final Class<? extends ActionBean> actionBeanClass) {
        if (actionBeanClass == null) {
            throw new IllegalArgumentException("Can't create roundtrip by null action bean class!");
        }
        return new MockRoundtrip(createServletContext(), actionBeanClass);
    }

    public static MockServletContext createServletContext() {
        final MockServletContext servletContext = new MockServletContext("test");
        final Map<String, String> filterParams = new HashMap<String, String>();
        filterParams.put("ActionResolver.Packages", "com.shroggle");
        servletContext.addFilter(StripesFilter.class, "StripesFilter", filterParams);
        servletContext.setServlet(DispatcherServlet.class, "StripesDispatcher", null);
        return servletContext;
    }

}
