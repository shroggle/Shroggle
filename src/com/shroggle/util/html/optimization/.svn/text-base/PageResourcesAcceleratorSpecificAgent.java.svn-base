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

import javax.servlet.ServletException;
import java.util.regex.Pattern;
import java.io.IOException;

/**
 * @author Artem Stasuk
 */
public class PageResourcesAcceleratorSpecificAgent implements PageResourcesAccelerator {

    public PageResourcesAcceleratorSpecificAgent(
            final PageResourcesAccelerator yes, final PageResourcesAccelerator no) {
        this.yes = yes;
        this.no = no;
    }

    @Override
    public PageResourcesValue execute(final PageResourcesContext context, final String name)
            throws IOException, ServletException {
        if (isAllow(context)) {
            return yes.execute(context, name);
        }
        return no.execute(context, name);
    }

    private boolean isAllow(final PageResourcesContext context) {
        final String agent = context.getRequest().getHeader("user-agent");
        final Config config = ServiceLocator.getConfigStorage().get();
        return agent == null || !Pattern.compile(config.getDisableResourceMergeAgent()).matcher(agent).find();
    }

    private final PageResourcesAccelerator yes;
    private final PageResourcesAccelerator no;

}
