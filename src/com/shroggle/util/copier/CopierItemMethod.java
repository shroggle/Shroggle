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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Artem Stasuk
 */
public class CopierItemMethod implements CopierItem {

    public CopierItemMethod(
            Method getter, Method setter, Object original,
            Object copy, String name) {
        this.copy = copy;
        this.name = name;
        this.setter = setter;
        try {
            this.originalValue = getter.invoke(original);
        } catch (IllegalAccessException e) {
            throw new CopierException(e);
        } catch (InvocationTargetException e) {
            throw new CopierException(e);
        }
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
    public void setCopyValue(Object value) {
        this.copyValue = value;
        try {
            setter.invoke(copy, value);
            setted = true;
        } catch (IllegalAccessException e) {
            throw new CopierException(e);
        } catch (IllegalArgumentException e) {
            throw new CopierException(e);
        } catch (InvocationTargetException e) {
            throw new CopierException(e);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isSet() {
        return setted;
    }

    @Override
    public String toString() {
        return "CopierItemMethod {name: " + name + ", originalValue: " + originalValue + "}";
    }

    private boolean setted;
    private final Method setter;
    private final Object copy;
    private final Object originalValue;
    private Object copyValue;
    private final String name;

}
