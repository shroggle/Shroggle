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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Stasuk Artem
 */
public final class InternationalGetParameterTag extends TagSupport {

    public void setValue(final Object value) {
        this.value = value;
    }

    public final int doStartTag() throws JspException {
        final InternationalGetTag getTag =
                (InternationalGetTag) findAncestorWithClass(this, InternationalGetTag.class);
        if (getTag == null) {
            throw new JspException("International parameter tag must placed in international get tag!");
        }
        getTag.addParameter(value);
        return Tag.SKIP_BODY;
    }

    private Object value;

}