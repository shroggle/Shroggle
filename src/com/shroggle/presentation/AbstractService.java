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

import com.shroggle.entity.ItemType;
import com.shroggle.presentation.site.render.RenderContext;
import com.shroggle.util.ServiceLocator;
import org.directwebremoting.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Stasuk Artem
 */
public abstract class AbstractService {

    public final void setContext(final WebContext context) {
        this.context = context;
    }

    public final WebContext getContext() {
        if (context != null) {
            return context;
        }
        return ServiceLocator.getWebContextGetter().get();
    }

    public final HttpServletRequest getRequest() {
        return getContext().getHttpServletRequest();
    }

    public final String forwardToString(final String destination) throws ServletException, IOException {
        return getContext().forwardToString(destination);
    }

    public final RenderContext createRenderContext(final boolean showFromSiteEditPage) {
        return new RenderContext(
                getContext().getHttpServletRequest(),
                getContext().getHttpServletResponse(),
                getContext().getServletContext(),
                new HashMap<ItemType, String>(),
                showFromSiteEditPage
        );
    }

    public final RenderContext createRenderContext(final boolean showFromSiteEditPage, final Map<ItemType, String> parameterMap) {
        return new RenderContext(
                getContext().getHttpServletRequest(),
                getContext().getHttpServletResponse(),
                getContext().getServletContext(),
                parameterMap,
                showFromSiteEditPage
        );
    }


    public HttpSession getSession() {
        return getContext().getSession();
    }

    private WebContext context;

}
