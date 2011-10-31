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

package com.shroggle.util;

import javax.el.ELContext;
import javax.servlet.*;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.el.ExpressionEvaluator;
import javax.servlet.jsp.el.VariableResolver;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Stasuk Artem
 */
public class PageContextMock extends PageContext {

    public PageContextMock(final JspWriter jspWriter) {
        this.jspWriter = jspWriter;
    }

    public PageContextMock() {
        this(null);
    }

    public void initialize(Servlet servlet, ServletRequest servletRequest, ServletResponse servletResponse, String s, boolean b, int i, boolean b1) throws IOException, IllegalStateException, IllegalArgumentException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void release() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public HttpSession getSession() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object getPage() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ServletRequest getRequest() {
        return request;
    }

    public void setResponse(final ServletResponse response) {
        this.response = response;
    }

    public ServletResponse getResponse() {
        return response;
    }

    public void setRequest(final ServletRequest request) {
        this.request = request;
    }

    public Exception getException() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ServletConfig getServletConfig() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ServletContext getServletContext() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void forward(String s) throws ServletException, IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void include(String s) throws ServletException, IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void include(String s, boolean b) throws ServletException, IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void handlePageException(Exception e) throws ServletException, IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void handlePageException(Throwable throwable) throws ServletException, IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setAttribute(String s, Object o) {
        attributes.put(s, o);
    }

    public void setAttribute(String s, Object o, int i) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object getAttribute(String s) {
        return attributes.get(s);
    }

    public Object getAttribute(String s, int i) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object findAttribute(String s) {
        return attributes.get(s);
    }

    public void removeAttribute(String s) {
        attributes.remove(s);
    }

    public void removeAttribute(String s, int i) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getAttributesScope(String s) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Enumeration getAttributeNamesInScope(int i) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public JspWriter getOut() {
        return jspWriter;
    }

    public ExpressionEvaluator getExpressionEvaluator() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public VariableResolver getVariableResolver() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ELContext getELContext() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private final Map<String, Object> attributes = new HashMap<String, Object>();
    private JspWriter jspWriter;
    private ServletResponse response;
    private ServletRequest request;

}
