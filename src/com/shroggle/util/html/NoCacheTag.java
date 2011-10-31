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

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * @author Stasuk Artem
 */
public final class NoCacheTag extends TagSupport {

    public final int doStartTag() throws JspException {
        final JspWriter jspWriter = pageContext.getOut();
        final HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        try {
            HtmlUtil.writeHeaderInfo(jspWriter, response);
        } catch (final IOException exception) {
            throw new JspException(exception);
        }
        return Tag.SKIP_BODY;
    }

}