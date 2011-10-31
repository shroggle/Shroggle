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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Artem Stasuk
 */
public final class TestHelpStrings {

    public TestHelpStrings(final int size) {
        this.strings = new String[size];
    }

    public void add(final String string) {
        synchronized (strings) {
            position++;
            if (position == strings.length) {
                firstLoop = false;
                position = 0;
            }
            strings[position] = string;
        }
    }

    public List<String> get() {
        synchronized (strings) {
            final List<String> tempStrings = new ArrayList<String>();
            if (!firstLoop) {
                for (int i = position + 1; i < strings.length; i++) {
                    tempStrings.add(strings[i]);
                }
            }
            for (int i = 0; i <= position; i++) {
                tempStrings.add(strings[i]);
            }
            return tempStrings;
        }
    }

    private volatile boolean firstLoop = true;
    private volatile int position = -1;
    private final String[] strings;

}
