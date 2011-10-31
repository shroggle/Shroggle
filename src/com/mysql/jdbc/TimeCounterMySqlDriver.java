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

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author Artem Stasuk
 */
public class TimeCounterMySqlDriver extends Driver {

    static {
        try {
            final Enumeration<java.sql.Driver> enu = DriverManager.getDrivers();
            while (enu.hasMoreElements()) {
                final java.sql.Driver driver = enu.nextElement();
                boolean isMysqlDriver = false;
                try {
                    isMysqlDriver = driver.acceptsURL("jdbc:mysql://fakehost:3306/fakedb");
                }
                catch (final SQLException exception) {
                    // Theoretically JDBC driver can use this fake url to establish connect,
                    // but in fact most of them (all used for now) just checks URL pattern.
                    System.out.println("WARN: << TimeCounterMySqlDriver:static-initialization >>\n" +
                            " We have JDBC driver that really tries to connect with fake url" +
                            " [" + driver.getClass().getName() + "]." +
                            " Please investigate - it shouldn't be a MySQL driver.");
                }
                if (isMysqlDriver) {
                    //KS-5697: We should remove only MySQL drivers
                    DriverManager.deregisterDriver(driver);
                }
            }
            DriverManager.registerDriver(new TimeCounterMySqlDriver());
        }
        catch (final SQLException exception) {
            throw new RuntimeException("Can't register driver!", exception);
        }
    }

    public TimeCounterMySqlDriver() throws SQLException {
        super();
    }

    public java.sql.Connection connect(final String url, final Properties info) throws SQLException {
        final Properties urlAndInfoProperties = parseURL(url, info);
        if (urlAndInfoProperties == null) return null;
        try {
            return new TimeCounterMySqlConnection(
                    host(urlAndInfoProperties), port(urlAndInfoProperties),
                    urlAndInfoProperties, database(urlAndInfoProperties), url);
        } catch (final SQLException exception) {
            throw exception;
        } catch (final Exception exception) {
            throw new SQLException(
                    Messages.getString("NonRegisteringDriver.17") //$NON-NLS-1$
                            + exception.toString() + Messages.getString("NonRegisteringDriver.18"), //$NON-NLS-1$
                    SQLError.SQL_STATE_UNABLE_TO_CONNECT_TO_DATASOURCE);
        }

    }

}
