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
package com.shroggle.logic.system;

import java.util.LinkedList;

/**
 * @author Artem Stasuk
 */
public class CreateSitesStatus {

    public synchronized void push(final String value) {
        values.add(value);
    }

    public synchronized void change(final String value) {
        values.set(values.size() - 1, value);
    }

    public synchronized void pop() {
        values.removeLast();
    }

    public synchronized String get() {
        StringBuilder result = new StringBuilder();
        for (final String value : values) {
            result.append(value).append("<br>");
        }
        return result.toString();
    }

    private final LinkedList<String> values = new LinkedList<String>();

}
