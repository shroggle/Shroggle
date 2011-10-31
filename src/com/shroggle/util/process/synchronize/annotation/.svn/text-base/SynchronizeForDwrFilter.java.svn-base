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
import com.shroggle.util.process.synchronize.SynchronizeContext;
import com.shroggle.util.process.synchronize.SynchronizeRequest;
import org.directwebremoting.AjaxFilter;
import org.directwebremoting.AjaxFilterChain;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Artem Stasuk
 */
public class SynchronizeForDwrFilter implements AjaxFilter {

    public Object doFilter(
            final Object object, final Method method,
            final Object[] parameters, final AjaxFilterChain chain)
            throws Exception {
        final SynchronizeRequest synchronizeRequest =
                synchronizeByCache.getRequest(method, object, parameters);
        if (logger.isLoggable(Level.ALL)) {
            logger.info(object.getClass().getName() + " request: " + synchronizeRequest);
        }
        if (synchronizeRequest == null) {
            return chain.doFilter(object, method, parameters);
        }
        return ServiceLocator.getSynchronize().execute(
                synchronizeRequest, new SynchronizeContext<Object>() {

            public Object execute() throws Exception {
                return chain.doFilter(object, method, parameters);
            }

        });
    }

    private final SynchronizeByCache synchronizeByCache = new SynchronizeByCache();
    private final static Logger logger = Logger.getLogger(SynchronizeForStripesInterceptor.class.getName());

}
