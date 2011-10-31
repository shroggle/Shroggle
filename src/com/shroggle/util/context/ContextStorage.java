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
package com.shroggle.util.context;

/**
 * @author Artem Stasuk
 */
public class ContextStorage {

    public Context get() {
        return contexts.get();
    }

    public void set(final Context context) {
        if (context == null) {
            throw new UnsupportedOperationException(
                    "Can't set null context!");
        }
        contexts.set(context);
    }

    public void remove() {
        contexts.remove();
    }

    private final ThreadLocal<Context> contexts = new ThreadLocal<Context>();

}
