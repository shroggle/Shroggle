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
package com.shroggle.presentation.site;

import com.shroggle.entity.Page;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.mock.MockHttpServletRequest;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
public class RenderedPageHtmlProviderReal implements RenderedPageHtmlProvider {

    public String provide(final Page page) throws IOException, ServletException {
        final ShowPageVersionAction showPageVersionAction = new ShowPageVersionAction();
        final ActionBeanContext context = new ActionBeanContext();

        // todo we need to replace real request with something.
        context.setRequest(ServiceLocator.getWebContextGetter().get().getHttpServletRequest());
        context.setResponse(ServiceLocator.getWebContextGetter().get().getHttpServletResponse());
        context.setServletContext(ServiceLocator.getWebContextGetter().get().getServletContext());
        showPageVersionAction.setContext(context);
        showPageVersionAction.setPageId(page.getPageId());
        showPageVersionAction.setSiteShowOption(SiteShowOption.ON_USER_PAGES);
        return showPageVersionAction.renderPageHtml(new PageManager(page));
    }

}
