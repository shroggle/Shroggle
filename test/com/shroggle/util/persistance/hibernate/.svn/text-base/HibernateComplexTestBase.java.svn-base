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
import com.shroggle.util.persistance.PersistanceContext;
import com.shroggle.util.persistance.PersistanceTransaction;
import org.junit.Test;
import org.junit.Ignore;

/**
 * @author Artem Stasuk
 */
public abstract class HibernateComplexTestBase {

    @Ignore
    @Test
    public void execute() {
        final HibernateTestBase testBase = new HibernateTestBase();
        persistance = ServiceLocator.getPersistance();
        persistanceTransaction = ServiceLocator.getPersistanceTransaction();
        try {
            persistance.inContext(new PersistanceContext<Void>() {

                @Override
                public Void execute() {
                    persistanceTransaction.execute(new Runnable() {

                        @Override
                        public void run() {
                            init();
                        }

                    });
                    return null;
                }

            });

            persistance.inContext(new PersistanceContext<Void>() {

                @Override
                public Void execute() {
                    persistanceTransaction.execute(new Runnable() {

                        @Override
                        public void run() {
                            make();
                        }

                    });
                    return null;
                }

            });

            persistance.inContext(new PersistanceContext<Void>() {

                @Override
                public Void execute() {
                    test();
                    return null;
                }

            });
        } finally {
            testBase.destroy();
        }
    }

    public abstract void init();

    public abstract void make();

    public abstract void test();

    protected Persistance persistance;
    protected PersistanceTransaction persistanceTransaction;

}
