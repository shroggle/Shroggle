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

/**
 * Author: Artem Stasuk (artem).
 * </p>
 * Date: 08.10.2008
 */
public class MockInternational implements International {

    public String get(final String name, final Object... parameters) {
        this.name = name;
        this.parameters = parameters;
        return result;
    }

    public String getName() {
        return name;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setResult(String result) {
        this.result = result;
    }

    private String name;
    private Object[] parameters;
    private String result;

}
