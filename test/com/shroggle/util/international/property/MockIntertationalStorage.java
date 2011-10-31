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
import com.shroggle.util.international.InternationalStorage;

import java.util.Locale;

/**
 * Author: Artem Stasuk (artem).
 * </p>
 * Date: 08.10.2008
 */
public class MockIntertationalStorage implements InternationalStorage {

    public International get(final String part, final Locale locale) {
        return mockInternational;
    }

    private final MockInternational mockInternational = new MockInternational();

}
