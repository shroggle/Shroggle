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

import net.sourceforge.stripes.util.ResolverUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Artem Stasuk
 * @see com.shroggle.util.reflection.ClassesSimple
 * @see ClassesOneJar
 */
public class ClassesStripes implements Classes {

    public ClassesStripes(final String packageName) {
        this.packageName = packageName;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public List<Class> get(final ClassesFilter filter) {
        final ResolverUtil resolverUtil = new ResolverUtil();
        resolverUtil.find(new ResolverUtil.Test() {

            @Override
            public boolean matches(final Class<?> type) {
                return filter.accept(type);
            }

        }, packageName);
        return new ArrayList<Class>(resolverUtil.getClasses());
    }

    private final String packageName;

}
