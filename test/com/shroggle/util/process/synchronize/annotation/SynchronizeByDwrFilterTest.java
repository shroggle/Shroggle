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
package com.shroggle.util.process.synchronize.annotation;

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.process.synchronize.MockSynchronize;
import junit.framework.Assert;
import org.directwebremoting.AjaxFilter;
import org.directwebremoting.AjaxFilterChain;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Author: Artem Stasuk (artem).
 * </p>
 * Date: 23.10.2008
 */
public class SynchronizeByDwrFilterTest {

    @Test
    public void doFilter() throws Exception {
        final MockSynchronize mockSynchronize = new MockSynchronize();
        ServiceLocator.setSynchronize(mockSynchronize);
        final ObjectSynchronizeByAllEntity object = new ObjectSynchronizeByAllEntity();
        final Method method = object.getClass().getMethod("test");
        final AjaxFilter filter = new SynchronizeForDwrFilter();
        final AtomicBoolean test = new AtomicBoolean();
        filter.doFilter(object, method, new Object[0], new AjaxFilterChain() {

            public Object doFilter(final Object obj, final Method method, final Object[] params) throws Exception {
                test.set(true);
                return null;
            }

        });

        Assert.assertNotNull(mockSynchronize.getRequest());
        Assert.assertTrue(test.get());
    }

    @Test
    public void doFilterWithoutRequest() throws Exception {
        final MockSynchronize mockSynchronize = new MockSynchronize();
        ServiceLocator.setSynchronize(mockSynchronize);
        final ObjectSynchronizeByAllEntity object = new ObjectSynchronizeByAllEntity();
        final Method method = object.getClass().getMethod("testWithout");
        final AjaxFilter filter = new SynchronizeForDwrFilter();
        final AtomicBoolean test = new AtomicBoolean();
        filter.doFilter(object, method, new Object[0], new AjaxFilterChain() {

            public Object doFilter(final Object obj, final Method method, final Object[] params) throws Exception {
                test.set(true);
                return null;
            }

        });

        Assert.assertNull(mockSynchronize.getRequest());
        Assert.assertTrue(test.get());
    }

}
