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

import com.shroggle.util.persistance.PersistanceIdMock;
import junit.framework.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;

/**
 * Author: Artem Stasuk (artem).
 * </p>
 * Date: 06.10.2008
 */
public class HibernateEntityManagerTest {

    @Test(expected = UnsupportedOperationException.class)
    public void createWithNullPersistanceId() {
        new HibernateEntityManager(new EntityManagerMock(), null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createWithNullEntityManager() {
        PersistanceIdMock persistanceIdMock = new PersistanceIdMock();
        new HibernateEntityManager(null, persistanceIdMock);
    }

    @Test
    public void persist() {
        PersistanceIdMock persistanceIdMock = new PersistanceIdMock();
        EntityManager entityManager = new HibernateEntityManager(
                new EntityManagerMock(), persistanceIdMock);
        final Object entity = new Object();
        entityManager.persist(entity);

        Assert.assertEquals(entity, persistanceIdMock.getSetted());
    }

}
