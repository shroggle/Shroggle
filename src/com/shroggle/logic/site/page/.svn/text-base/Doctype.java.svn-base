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
package com.shroggle.logic.site.page;

/**
 * @author Balakirev Anatoliy
 */
public class Doctype {

    public static String addDoctypeIfNeeded(final String oldHtml) {
        if (oldHtml != null && !oldHtml.startsWith(Doctype.TRANSITIONAL)) {
            return (Doctype.TRANSITIONAL + "\n" + oldHtml);
        }
        return oldHtml;
    }

    public static final String TRANSITIONAL = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">";
}
