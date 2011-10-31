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
import com.shroggle.util.persistance.PersistanceTransactionContext;
import org.junit.runners.model.Statement;

/**
 * @author Artem Stasuk
 * @see com.shroggle.util.persistance.hibernate.TestRunnerWithHibernateService
 */
final class StatementHibernateTransaction extends Statement {

    public StatementHibernateTransaction(final Statement statement) {
        this.statement = statement;
    }

    @Override
    public void evaluate() throws Throwable {
        ServiceLocator.getPersistanceTransaction().execute(new PersistanceTransactionContext<Void>() {

            public Void execute() {
                try {
                    statement.evaluate();
                } catch (Throwable throwable) {
                    if (throwable instanceof RuntimeException) {
                        throw (RuntimeException) throwable;
                    }
                    throw new RuntimeException(throwable);
                }
                return null;
            }

        });
    }

    private final Statement statement;

}