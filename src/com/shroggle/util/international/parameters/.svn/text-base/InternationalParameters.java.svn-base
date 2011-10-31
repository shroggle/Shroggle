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

package com.shroggle.util.international.parameters;

import com.shroggle.util.international.International;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Stasuk Artem
 */
class InternationalParameters implements International {

    public InternationalParameters(final International international) {
        this.international = international;
    }


    public String get(final String name, final Object... parameters) {
        String value = international.get(name, parameters);
        if (parameters.length > 0) {
            final MessageFormat messageFormat = new MessageFormat(value, Locale.US);
            value = messageFormat.format(parameters);
        }
        return value;
    }

    private final International international;

}