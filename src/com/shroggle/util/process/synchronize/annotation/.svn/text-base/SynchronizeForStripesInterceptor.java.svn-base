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
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Artem Stasuk 
 */
@Intercepts({LifecycleStage.EventHandling})
public class SynchronizeForStripesInterceptor implements Interceptor {

    public Resolution intercept(final ExecutionContext context) throws Exception {
        final SynchronizeRequest synchronizeRequest = synchronizeByCache.getRequest(
                context.getHandler(), context.getActionBean());
        if (logger.isLoggable(Level.ALL)) {
            logger.info(
                    context.getActionBeanContext().getRequest().getRequestURL()
                            + " request: " + String.valueOf(synchronizeRequest));
        }
        if (synchronizeRequest == null) {
            return context.proceed();
        }
        return ServiceLocator.getSynchronize().execute(
                synchronizeRequest, new SynchronizeContext<Resolution>() {

            public Resolution execute() {
                try {
                    return context.proceed();
                } catch (Exception exception) {
                    throw new RuntimeException(exception);
                }
            }

        });
    }

    private final SynchronizeByCache synchronizeByCache = new SynchronizeByCache();
    private final static Logger logger = Logger.getLogger(SynchronizeForStripesInterceptor.class.getName());

}
