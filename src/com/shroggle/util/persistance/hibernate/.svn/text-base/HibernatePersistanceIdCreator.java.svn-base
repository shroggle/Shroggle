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

import com.shroggle.util.persistance.PersistanceEntities;
import com.shroggle.util.persistance.PersistanceId;
import com.shroggle.util.persistance.PersistanceIdReal;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Artem Stasuk
 */
public class HibernatePersistanceIdCreator {

    public static PersistanceId execute(final EntityManagerFactory factory) {
        final EntityManager entityManager = factory.createEntityManager();
        try {
            return executeInContext(entityManager);
        } finally {
            entityManager.close();
        }
    }

    private static PersistanceId executeInContext(final EntityManager entityManager) {
        final Map<Class, Field> idFieldByEntityClasses = PersistanceEntities.getIdFieldsByClass();
        // get start entity id
        final Map<Field, Integer> maxIdByIdFields = new HashMap<Field, Integer>();
        for (final Class entityClass : idFieldByEntityClasses.keySet()) {
            final Field idField = idFieldByEntityClasses.get(entityClass);
            if (idField == null) continue;
            Integer maxEntityId = getMaxSimpleEntityId(entityManager, entityClass, idField);
            final Integer existMaxEntityId = maxIdByIdFields.get(idField);
            if (existMaxEntityId == null || (maxEntityId != null && existMaxEntityId < maxEntityId)) {
                maxIdByIdFields.put(idField, maxEntityId);
            }
        }
        return new PersistanceIdReal(idFieldByEntityClasses, maxIdByIdFields);
    }

    private static Integer getMaxSimpleEntityId(
            final EntityManager entityManager, final Class entityClass, final Field idField) {
        final Entity entity = (Entity) entityClass.getAnnotation(Entity.class);
        if (entity == null) {
            throw new UnsupportedOperationException(
                    "Can't find max id for entity class " + entityClass + " base "
                            + idField.getDeclaringClass() + " without annotation entity!");
        }

        final Query query = entityManager.createQuery(
                "select max(" + idField.getName() + ") from " + entity.name());
        final Integer maxId = (Integer) query.getSingleResult();
//        if (logger.isLoggable(Level.INFO)) {
//            logger.log(Level.INFO,
//                    "Find for entity " + entityClass + " max id "
//                            + maxId + " base " + idField.getDeclaringClass());
//        }
        return maxId;
    }

    private static final Logger logger = Logger.getLogger(HibernatePersistanceIdCreator.class.getName());

}
