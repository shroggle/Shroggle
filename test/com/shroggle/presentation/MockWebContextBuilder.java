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
import org.directwebremoting.Container;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MockWebContextBuilder implements WebContextFactory.WebContextBuilder {
    private MockWebContext mockWebContext = new MockWebContext();

    public void set(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, ServletConfig servletConfig, ServletContext servletContext, Container container) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public WebContext get() {
        return mockWebContext;
    }

    public void unset() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setHttpServletRequest(MockHttpServletRequest httpServletRequest) {
        mockWebContext.setHttpServletRequest(httpServletRequest);
    }

    public void setHttpServletResponse(MockHttpServletResponse httpServletResponse) {
        mockWebContext.setHttpServletResponse(httpServletResponse);
    }
}
