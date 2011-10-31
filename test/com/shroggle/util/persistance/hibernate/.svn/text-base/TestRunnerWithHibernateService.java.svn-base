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

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

/**
 * Attention! Base class use override methods in next order. methodInvoker -> withBefores -> withAfters
 *
 * @author Artem Stasuk
 */
public class TestRunnerWithHibernateService extends BlockJUnit4ClassRunner {

    public TestRunnerWithHibernateService(final Class testClass) throws InitializationError {
        super(testClass);
    }

    /**
     * Add session and transaction for execute in theirs before methods
     *
     * @param method - method
     * @param target - target
     * @return - result
     */
    @Override
    protected Statement withBefores(
            final FrameworkMethod method, final Object target, Statement statement) {
        statement = super.withBefores(method, target, statement);
        statement = new StatementHibernateTransaction(statement);
        statement = new StatementHibernateSession(statement);
        statementHibernate.setBeforeStatement(statement);

        return StatementHibernateEmpty.instance;
    }

    /**
     * Add session and transaction for execute in theirs test method.
     *
     * @param method - method
     * @param test   - test
     * @return - result
     */
    @Override
    protected Statement methodInvoker(final FrameworkMethod method, final Object test) {
        statementHibernate = new StatementHibernate();
        Statement statement = super.methodInvoker(method, test);
        statement = new StatementHibernateTransaction(statement);
        statement = new StatementHibernateSession(statement);
        statementHibernate.setTestStatement(statement);

        return StatementHibernateEmpty.instance;
    }

    @Override
    protected Statement withAfters(
            final FrameworkMethod method, final Object target, Statement statement) {
        statement = super.withAfters(method, target, statement);
        statementHibernate.setAfterStatement(statement);
        return statementHibernate;
    }

    private StatementHibernate statementHibernate;

}