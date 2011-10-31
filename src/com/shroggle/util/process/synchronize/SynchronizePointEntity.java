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
 * @author Stasuk Artem
 */
public class SynchronizePointEntity implements SynchronizePoint {

    public SynchronizePointEntity(
            final Class entityClass, final Object entityId, final SynchronizeMethod method) {
        this.entityClass = entityClass;
        this.method = method;
        this.entityId = entityId;
    }

    @Override
    public int hashCode() {
        if (entityId == null) {
            return entityClass.hashCode();
        }
        return entityId.hashCode() << 32 + entityClass.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object.getClass() != SynchronizePointEntity.class) {
            return false;
        }
        final SynchronizePointEntity point = (SynchronizePointEntity) object;
        if (point.entityClass.equals(entityClass)) {
            if (entityId == null) {
                return point.entityId == null;
            }
            return (entityId.equals(point.entityId));
        }
        return false;
    }

    public SynchronizeMethod getMethod() {
        return method;
    }

    private final Class entityClass;
    private final Object entityId;
    private final SynchronizeMethod method;

}
