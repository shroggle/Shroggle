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
package com.shroggle.util.html;

import com.shroggle.util.ServiceLocator;
import org.directwebremoting.Container;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

/**
 * @author Artem Stasuk
 */
public class WebContextManual implements WebContext {

    public WebContextManual(
            final HttpServletRequest request, final HttpServletResponse response,
            final ServletContext servletContext) {
        this.request = request;
        this.servletContext = servletContext;
        this.response = response;
    }

    public ScriptSession getScriptSession() {
        throw new UnsupportedOperationException();
    }

    public String getCurrentPage() {
        throw new UnsupportedOperationException();
    }

    public HttpSession getSession() {
        return request.getSession();
    }

    public HttpSession getSession(boolean create) {
        return request.getSession(true);
    }

    public HttpServletRequest getHttpServletRequest() {
        return request;
    }

    public HttpServletResponse getHttpServletResponse() {
        return response;
    }

    public String forwardToString(final String url) throws ServletException, IOException {
        return htmlGetter.get(url, getHttpServletRequest(), getHttpServletResponse(), getServletContext());
    }

    public void setCurrentPageInformation(String page, String scriptSessionId) {
        throw new UnsupportedOperationException();
    }

    public Collection getScriptSessionsByPage(String url) {
        throw new UnsupportedOperationException();
    }

    public Collection getAllScriptSessions() {
        throw new UnsupportedOperationException();
    }

    public ServletConfig getServletConfig() {
        throw new UnsupportedOperationException();
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public Container getContainer() {
        throw new UnsupportedOperationException();
    }

    public String getVersion() {
        throw new UnsupportedOperationException();
    }

    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final ServletContext servletContext;
    private final HtmlGetter htmlGetter = ServiceLocator.getHtmlGetter();

}
