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
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Locale;

/**
 * @author Stasuk Artem
 */
public final class InternationalSetPartTag extends TagSupport {

    public static final String INTERNATIONAL = "INTERNATION";

    public void setPart(final String part) {
        this.part = part;
    }

    public final int doStartTag() throws JspException {
        final InternationalStorage internationalStorage = ServiceLocator.getInternationStorage();
        if (internationalStorage == null) {
            throw new JspException("Can't get international storage service!");
        }
        if (part == null) {
            throw new JspException("Can't work with null part!");
        }
        final International international = internationalStorage.get(part, Locale.US);
        pageContext.setAttribute(INTERNATIONAL, international);
        return Tag.SKIP_BODY;
    }

    private String part;

}