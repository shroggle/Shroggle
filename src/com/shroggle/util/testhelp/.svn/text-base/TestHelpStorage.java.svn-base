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
package com.shroggle.util.testhelp;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Artem Stasuk
 */
public class TestHelpStorage {

    public TestHelpStorage() {
        final Map<TestHelpSource, TestHelpStrings> tempStringBySources =
                new HashMap<TestHelpSource, TestHelpStrings>();
        for (final TestHelpSource source : TestHelpSource.values()) {
            tempStringBySources.put(source, new TestHelpStrings(50));
        }
        stringBySources = Collections.unmodifiableMap(tempStringBySources);
    }

    public void addEvent(final TestHelpSource source, final String string) {
        stringBySources.get(source).add(string);
    }

    public Map<TestHelpSource, TestHelpStrings> getEvents() {
        return stringBySources;
    }

    private final Map<TestHelpSource, TestHelpStrings> stringBySources;

}
