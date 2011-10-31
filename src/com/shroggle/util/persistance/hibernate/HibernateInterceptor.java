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

import com.shroggle.util.persistance.PersistanceListener;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Artem Stasuk
 */
public class HibernateInterceptor extends EmptyInterceptor {

    public void addUpdateListener(final PersistanceListener listener) {
        if (listener == null) {
            throw new UnsupportedOperationException("Can't add null listener!");
        }
        updateListeners.add(listener);
    }

    public void addRemoveListener(final PersistanceListener listener) {
        if (listener == null) {
            throw new UnsupportedOperationException("Can't add null listener!");
        }
        removeListeners.add(listener);
    }

    @Override
    public boolean onSave(
            final Object entity, final Serializable id,
            final Object[] state, final String[] propertyNames, final Type[] types) {
        if (logger.isLoggable(Level.INFO)) {
            //logger.log(Level.INFO, "Start notify about create entity " + entity.getClass() + " id " + id);
        }
        return super.onSave(entity, id, state, propertyNames, types);
    }

    @Override
    public boolean onFlushDirty(
            final Object entity, final Serializable id, final Object[] currentState,
            final Object[] previousState, final String[] propertyNames, final Type[] types) {
        if (logger.isLoggable(Level.INFO)) {
            //logger.log(Level.INFO, "Start notify about update entity " + entity.getClass() + " id " + id);
        }
        notify(entity.getClass(), id, updateListeners);
        return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }

    @Override
    public void onDelete(
            final Object entity, final Serializable id,
            final Object[] state, final String[] propertyNames, final Type[] types) {
        if (logger.isLoggable(Level.INFO)) {
            //logger.log(Level.INFO, "Start notify about delete entity " + entity.getClass() + " id " + id);
        }
        notify(entity.getClass(), id, removeListeners);
        super.onDelete(entity, id, state, propertyNames, types);
    }

    private static void notify(
            final Class entityClass, final Object entityId,
            final List<PersistanceListener> listeners) {
        for (final PersistanceListener listener : listeners) {
            listener.execute(entityClass, entityId);
        }
    }

    private final List<PersistanceListener> updateListeners = new ArrayList<PersistanceListener>();
    private final List<PersistanceListener> removeListeners = new ArrayList<PersistanceListener>();
    private final static Logger logger = Logger.getLogger(
            HibernateInterceptor.class.getName());

}
