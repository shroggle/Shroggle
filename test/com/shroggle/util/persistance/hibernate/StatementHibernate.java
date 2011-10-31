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

import org.junit.runners.model.Statement;

/**
 * @author Artem Stasuk
 * @see com.shroggle.util.persistance.hibernate.TestRunnerWithHibernateService
 * @see com.shroggle.util.persistance.hibernate.HibernateTestBase
 */
final class StatementHibernate extends Statement {

    @Override
    public void evaluate() throws Throwable {
        final HibernateTestBase testBase = new HibernateTestBase();
        try {
            beforeStatement.evaluate();
            testStatement.evaluate();
            afterStatement.evaluate();
        } finally {
            testBase.destroy();
        }
    }

    public void setAfterStatement(final Statement afterStatement) {
        this.afterStatement = afterStatement;
    }

    public void setTestStatement(final Statement testStatement) {
        this.testStatement = testStatement;
    }

    public void setBeforeStatement(final Statement beforeStatement) {
        this.beforeStatement = beforeStatement;
    }

    private Statement beforeStatement;
    private Statement testStatement;
    private Statement afterStatement;

}