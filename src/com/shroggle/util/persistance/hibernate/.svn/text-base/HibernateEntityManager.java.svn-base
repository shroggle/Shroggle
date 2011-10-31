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
package com.shroggle.util.persistance.hibernate;

import com.shroggle.entity.Widget;
import com.shroggle.util.persistance.PersistanceId;

import javax.persistence.*;

/**
 * @author Artem Stasuk
 */
public class HibernateEntityManager implements EntityManager {

    public HibernateEntityManager(
            final EntityManager entityManager, final PersistanceId persistanceId) {
        if (persistanceId == null) {
            throw new UnsupportedOperationException(
                    "Can't create entity manager without persistance id!");
        }
        if (entityManager == null) {
            throw new UnsupportedOperationException(
                    "Can't create entity manager without real entity manager!");
        }
        this.persistanceId = persistanceId;
        this.entityManager = entityManager;
    }

    public void persist(final Object entity) {
        if (entity instanceof Widget) {

        } else {
            persistanceId.set(entity);
        }
        entityManager.persist(entity);
    }

    public <T> T merge(T t) {
        return entityManager.merge(t);
    }

    public void remove(Object o) {
        entityManager.remove(o);
    }

    public <T> T find(Class<T> tClass, Object o) {
        return entityManager.find(tClass, o);
    }

    public <T> T getReference(Class<T> tClass, Object o) {
        return entityManager.getReference(tClass, o);
    }

    public void flush() {
        entityManager.flush();
    }

    public void setFlushMode(FlushModeType flushModeType) {
        entityManager.setFlushMode(flushModeType);
    }

    public FlushModeType getFlushMode() {
        return entityManager.getFlushMode();
    }

    public void lock(Object o, LockModeType lockModeType) {
        entityManager.lock(o, lockModeType);
    }

    public void refresh(Object o) {
        entityManager.refresh(o);
    }

    public void clear() {
        entityManager.clear();
    }

    public boolean contains(Object o) {
        return entityManager.contains(o);
    }

    public Query createQuery(String s) {
        return entityManager.createQuery(s);
    }

    public Query createNamedQuery(String s) {
        return entityManager.createNamedQuery(s);
    }

    public Query createNativeQuery(String s) {
        return entityManager.createNativeQuery(s);
    }

    public Query createNativeQuery(String s, Class aClass) {
        return entityManager.createNativeQuery(s, aClass);
    }

    public Query createNativeQuery(String s, String s1) {
        return entityManager.createNativeQuery(s, s1);
    }

    public void joinTransaction() {
        entityManager.joinTransaction();
    }

    public Object getDelegate() {
        return entityManager.getDelegate();
    }

    public void close() {
        entityManager.close();
    }

    public boolean isOpen() {
        return entityManager.isOpen();
    }

    public EntityTransaction getTransaction() {
        return entityManager.getTransaction();
    }

    private final PersistanceId persistanceId;
    private final EntityManager entityManager;

}
