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

import com.shroggle.presentation.Action;
import com.shroggle.presentation.site.render.RenderContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.jsp.PageContext;

/**
 * @author Artem Stasuk
 */
public class PageResourcesContext {

    public PageResourcesContext(final Action action) {
        this.request = action.getContext().getRequest();
        this.response = action.getContext().getResponse();
        this.servletContext = action.getContext().getServletContext();
    }

    public PageResourcesContext(final PageContext pageContext) {
        this.request = (HttpServletRequest) pageContext.getRequest();
        this.response = (HttpServletResponse) pageContext.getResponse();
        this.servletContext = pageContext.getServletContext();
    }

    public PageResourcesContext(final RenderContext renderContext) {
        this.request = renderContext.getRequest();
        this.response = renderContext.getResponse();
        this.servletContext = renderContext.getServletContext();
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final ServletContext servletContext;

}
