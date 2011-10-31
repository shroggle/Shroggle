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

import java.util.List;

/**
 * @author Artem Stasuk
 */
public class CopierItemList implements CopierItem {

    public CopierItemList(List copy, Object originalValue) {
        this.originalValue = originalValue;
        this.copy = copy;
    }

    @Override
    public Object getOriginalValue() {
        return originalValue;
    }

    @Override
    public Object getCopyValue() {
        return copyValue;
    }

    @Override
    public void setCopyValue(final Object object) {
        copyValue = object;
        copy.add(object);
        setted = true;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public boolean isSet() {
        return setted;
    }

    private final List copy;
    private final Object originalValue;
    private Object copyValue;
    private boolean setted;

}
