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
package com.shroggle.util.reflection;

import java.util.List;

/**
 * @author Artem Stasuk
 */
public class ClassesSynchronize implements Classes {

    public ClassesSynchronize(final Classes classes) {
        this.classes = classes;
    }

    @Override
    public synchronized List<Class> get(final ClassesFilter filter) {
        return classes.get(filter);
    }

    private final Classes classes;

}
