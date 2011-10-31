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
package com.shroggle.util.cache;

/**
 * @author Artem Stasuk
 */
final class CacheByEntitysId {

    public CacheByEntitysId(Class entityClass, Object entityId) {
        this.entityClass = entityClass;
        this.entityId = entityId;
    }

    public Class getEntityClass() {
        return entityClass;
    }

    public Object getEntityId() {
        return entityId;
    }

    @Override
    public int hashCode() {
        return entityClass.hashCode() ^ entityId.hashCode();
    }

    @Override
    public boolean equals(final Object object) {
        if (object == null) return false;
        if (object.getClass() != CacheByEntitysId.class) return false;
        final CacheByEntitysId id = (CacheByEntitysId) object;
        return id.entityClass.equals(entityClass) && id.entityId.equals(entityId);
    }

    private final Object entityId;
    private final Class entityClass;

}
