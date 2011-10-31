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

import net.sourceforge.stripes.mock.MockHttpServletRequest;
import net.sourceforge.stripes.mock.MockHttpServletResponse;
import net.sourceforge.stripes.mock.MockHttpSession;
import net.sourceforge.stripes.mock.MockServletContext;
import org.directwebremoting.Container;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

public class MockWebContext implements WebContext {

    private MockHttpServletResponse httpServletResponse;

    private MockHttpServletRequest httpServletRequest = new MockHttpServletRequest("", "") {
        public HttpSession getSession() {
            return session;
        }
    };

    final HttpSession session = new MockHttpSession(new MockServletContext(""));

    public MockHttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public HttpSession getSession() {
        return session;
    }

    public Collection getScriptSessionsByPage(String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection getAllScriptSessions() {
        return null;
    }

    public ServletConfig getServletConfig() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ServletContext getServletContext() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Container getContainer() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getVersion() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ScriptSession getScriptSession() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getCurrentPage() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public HttpSession getSession(boolean b) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setHttpServletRequest(MockHttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public void setHttpServletResponse(MockHttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    public MockHttpServletResponse getHttpServletResponse() {
        return httpServletResponse;
    }

    public String forwardToString(final String s) throws ServletException, IOException {
        return s;
    }

    public void setCurrentPageInformation(String s, String s1) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void clear() {
    }

}
