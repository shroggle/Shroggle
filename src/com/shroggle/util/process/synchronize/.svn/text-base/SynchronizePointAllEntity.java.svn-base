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
package com.shroggle.util.process.synchronize;

/**
 * Author: Artem Stasuk (artem).
 * </p>
 * Date: 17.10.2008
 */
public class SynchronizePointAllEntity implements SynchronizePoint {

    public SynchronizePointAllEntity(final Class entityClass) {
        this.entityClass = entityClass;
    }

    public SynchronizeMethod getMethod() {
        return SynchronizeMethod.WRITE;
    }

    @Override
    public int hashCode() {
        return entityClass.hashCode();
    }

    @Override
    public boolean equals(final Object object) {
        if (object == null) return false;
        if (object.getClass() != SynchronizePointAllEntity.class) return false;
        final SynchronizePointAllEntity pointAllEntity = (SynchronizePointAllEntity) object;
        return pointAllEntity.entityClass == entityClass;
    }

    private final Class entityClass;

}