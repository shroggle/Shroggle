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

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Stasuk Artem
 */
@RunWith(TestRunnerWithHibernateService.class)
public abstract class HibernatePersistanceTestBase {

    @Before
    public void before() {
        persistance = ServiceLocator.getPersistance();
        persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    }

    protected Persistance persistance;
    protected PersistanceTransaction persistanceTransaction;

}