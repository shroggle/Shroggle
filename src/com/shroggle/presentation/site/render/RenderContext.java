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
package com.shroggle.presentation.site.render;

import com.shroggle.entity.ItemType;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Artem Stasuk
 */
public class RenderContext {

    public RenderContext(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final ServletContext servletContext,
            final Map<ItemType, String> parameterMap,
            final boolean showFromSiteEditPage) {
        this.request = request;
        this.response = response;
        this.servletContext = servletContext;
        this.parameterMap = parameterMap;
        this.showFromSiteEditPage = showFromSiteEditPage;
    }

    public Integer getIntParameterByName(final String name) {
        String parameter = this.getRequest().getParameter(name);
        if (parameter != null) {
            return Integer.parseInt(parameter);
        }
        return null;
    }

     public String getParameterByName(final String name) {
        return this.getRequest().getParameter(name);
    }

    public Object getAttribute(final String name) {
        return this.getRequest().getAttribute(name);
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

    public Map<ItemType, String> getParameterMap() {
        return parameterMap;
    }

    public boolean isShowFromSiteEditPage() {
        return showFromSiteEditPage;
    }

    public void setParameterMap(Map<ItemType, String> parameterMap) {
        this.parameterMap = parameterMap;
    }

    private Map<ItemType, String> parameterMap;
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final ServletContext servletContext;
    private final boolean showFromSiteEditPage;
}
