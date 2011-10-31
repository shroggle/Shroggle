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

package com.shroggle.util.international.html;

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import com.shroggle.util.international.InternationalStorage;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Stasuk Artem
 */
public final class InternationalGetTag extends BodyTagSupport {

    public void setName(final String name) {
        this.name = name;
    }

    public void setPart(final String part) {
        this.part = part;
    }

    public void addParameter(final Object parameter) {
        parameters.add(parameter);
    }

    public final int doStartTag() {
        parameters.clear();
        return EVAL_BODY_TAG;
    }

    public final int doEndTag() throws JspException {
        // if tag use as <international:get name="ff"/>
        if (getBodyContent() == null) {
            writeValue();
            return SKIP_BODY;
        }
        // if tag use as <international:get name="ff><international:param value="234"/><international:getHtml>
        return EVAL_BODY_TAG;
    }

    public final int doAfterBody() throws JspException {
        writeValue();
        return Tag.SKIP_BODY;
    }

    private void writeValue() throws JspException {
        final International international;

        if (part != null) {
            final InternationalStorage internationalStorage = ServiceLocator.getInternationStorage();
            if (internationalStorage == null) {
                throw new JspException("Can't get international storage service!");
            }
            international = internationalStorage.get(part, Locale.US);
        } else {
            international = (International) pageContext.getAttribute(InternationalSetPartTag.INTERNATIONAL);
        }

        if (international == null) {
            throw new JspException("Can't use get international without set part tag!");
        }

        if (name == null) {
            throw new JspException("Can't use international get without name!");
        }

        final BodyContent bodyContent = getBodyContent();
        final JspWriter jspWriter;
        if (bodyContent == null) {
            jspWriter = pageContext.getOut();
        } else {
            jspWriter = bodyContent.getEnclosingWriter();
        }
        try {
            jspWriter.write(international.get(name, parameters.toArray()));
        } catch (IOException exception) {
            throw new JspException(exception);
        }
    }

    private final List<Object> parameters = new ArrayList<Object>();
    private String name;
    // SW-5192 | SuperWiki - Inconsistency in Settings windows names
    private String part; // optional - will use the one set on the JSP context if not provided


}