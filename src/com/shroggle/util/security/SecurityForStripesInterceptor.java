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
package com.shroggle.util.security;

import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.util.ServiceLocator;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

/**
 * @author Artem Stasuk
 */
@Intercepts({LifecycleStage.EventHandling})
public class SecurityForStripesInterceptor implements Interceptor {

    public Resolution intercept(final ExecutionContext context) throws Exception {
        final SecurityCheck check = creator.execute(
                context.getActionBean().getClass(), context.getHandler());
        if (check.execute(context.getActionBean())) {
            return context.proceed();
        }
        // This session don't have access to this action!
        return resolutionCreator.loginInUser((Action) context.getActionBean());
    }

    private final SecurityCheckCreator creator = new SecurityCheckCreator();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();

}