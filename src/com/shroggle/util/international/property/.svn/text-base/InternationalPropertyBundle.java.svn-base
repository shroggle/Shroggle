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

package com.shroggle.util.international.property;

import com.shroggle.util.international.International;

import java.util.ResourceBundle;

/**
 * @author Stasuk Artem
 */
public class InternationalPropertyBundle implements International {

    public InternationalPropertyBundle(final ResourceBundle resourceBundle) {
        if (resourceBundle == null) {
            throw new UnsupportedOperationException(
                    "Can't create property international by null resource bundle!");
        }
        this.resourceBundle = resourceBundle;
    }

    /**
     * @param name       -name
     * @param parameters - parameters
     * @return - text
     * @see com.shroggle.util.international.parameters.InternationalStorageParameters
     */
    public String get(final String name, final Object... parameters) {
        return resourceBundle.getString(name);
    }

    private final ResourceBundle resourceBundle;

}
