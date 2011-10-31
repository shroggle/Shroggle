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
package com.shroggle.util.copier.stack;

import java.util.Map;

/**
 * @author Artem Stasuk
 */
public class CopierStackExecutorByClass implements CopierStackExecutor {

    public CopierStackExecutorByClass(final Map<Class, CopierStackExecutor> executors) {
        this.executors = executors;
    }

    @Override
    public void copy(final CopierStack stack, final Object object) {
        if (object != null) {
            final CopierStackExecutor executor = executors.get(object.getClass());
            if (executor != null) {
                executor.copy(stack, object);
            }
        }
    }

    private final Map<Class, CopierStackExecutor> executors;

}