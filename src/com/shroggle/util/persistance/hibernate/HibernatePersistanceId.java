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

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.TableGenerator;

import java.io.Serializable;

import com.shroggle.entity.Widget;

/**
 * @author Artem Stasuk
 */
public class HibernatePersistanceId extends TableGenerator {

    @Override
    public synchronized Serializable generate(
            final SessionImplementor session, final Object object)
            throws HibernateException {
        if (object instanceof Widget) {
            final Widget widget = (Widget) object;
            if (widget.getCrossWidgetId() < 1) {
                widget.setCrossWidgetId((Integer) HibernateManager.getPersistanceId().get(object));
            }
        }
        return (Serializable) HibernateManager.getPersistanceId().get(object);
    }

}
