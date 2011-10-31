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
package com.shroggle.util.copier;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Artem Stasuk
 */
public class CopierExplorerMethod implements CopierExplorer {

    @Override
    public List<CopierItem> find(Object original, Object copy) {
        final List<CopierItem> copierItems = new ArrayList<CopierItem>();
        final Class originalClass = original.getClass();
        final Class copyClass = copy.getClass();

        for (final Method getter : originalClass.getMethods()) {
            final String getterName = getter.getName();
            Integer getterNameStart = null;
            if (getterName.startsWith("get")) {
                getterNameStart = 3;
            }

            if (getterName.startsWith("is")) {
                getterNameStart = 2;
            }

            if (getterNameStart != null) {
                final String name = getterName.substring(getterNameStart);

                final Method setter;
                try {
                    setter = copyClass.getMethod("set" + name, getter.getReturnType());
                } catch (NoSuchMethodException e) {
                    continue;
                }

                copierItems.add(new CopierItemMethod(getter, setter, original, copy, name));
            }
        }

        return copierItems;
    }

}
