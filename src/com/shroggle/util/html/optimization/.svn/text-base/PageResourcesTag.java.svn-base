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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Stasuk Artem
 */
public final class PageResourcesTag extends TagSupport {

    public void setName(final String name) {
        this.name = name;
    }

    public int doStartTag() throws JspException {
        final PageResourcesContext context = new PageResourcesContext(pageContext);
        final JspWriter jspWriter = pageContext.getOut();
        try {
            jspWriter.print(ServiceLocator.getPageResourcesAccelerator().execute(context, name).getValue());
        } catch (final IOException exception) {
            throw new JspException("Can't print page resources in html!", exception);
        } catch (ServletException exception) {
            throw new JspException("Can't print page resources in html!", exception);
        }
        return SKIP_BODY;
    }

    private String name;

}