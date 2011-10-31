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
package com.mysql.jdbc;

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.process.timecounter.TimeCounter;
import com.shroggle.util.process.timecounter.TimeCounterCreator;
import com.shroggle.util.process.timecounter.TimeCounterCreatorEmpty;

import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Artem Stasuk
 */
public class TimeCounterMySqlConnection extends JDBC4Connection {

    TimeCounterMySqlConnection(
            final String hostToConnectTo, final int portToConnectTo, final Properties info,
            final String databaseToConnectTo, final String url) throws SQLException {
        super(hostToConnectTo, portToConnectTo, info, databaseToConnectTo, url);
    }

    ResultSetInternalMethods execSQL(
            final StatementImpl callingStatement, final String sql, final int maxRows, final Buffer packet,
            final int resultSetType, final int resultSetConcurrency, final boolean streamResults,
            final String catalog, final Field[] fields) throws SQLException {
        final TimeCounter timeCounter = createTimeCounter(createTimeCounterName(callingStatement));
        try {
            return super.execSQL(callingStatement, sql, maxRows, packet, resultSetType, resultSetConcurrency, streamResults, catalog, fields);
        } finally {
            timeCounter.stop();
        }
    }

    ResultSetInternalMethods execSQL(
            final StatementImpl callingStatement, final String sql, final int maxRows, final Buffer packet,
            final int resultSetType, final int resultSetConcurrency, final boolean streamResults,
            final String catalog, final Field[] fields, final boolean isBatch) throws SQLException {
        final TimeCounter timeCounter = createTimeCounter(createTimeCounterName(callingStatement));
        try {
            return super.execSQL(callingStatement, sql, maxRows, packet, resultSetType, resultSetConcurrency, streamResults, catalog, fields, isBatch);
        } finally {
            timeCounter.stop();
        }
    }

    private String createTimeCounterName(final StatementImpl callingStatement) {
        if (callingStatement != null) {
            if (callingStatement instanceof PreparedStatement) {
                final PreparedStatement preparedStatement = (PreparedStatement) callingStatement;
                return preparedStatement.originalSql;
            }
        }
        return null;
    }

    private TimeCounter createTimeCounter(final String sql) {
        if (sql == null) {
            return new TimeCounterCreatorEmpty().create(sql);
        }
        final TimeCounterCreator timeCounterCreator = ServiceLocator.getTimeCounterCreator();
        if (timeCounterCreator == null) {
            return new TimeCounterCreatorEmpty().create("sql://" + sql);
        }
        return timeCounterCreator.create(sql);
    }

}
