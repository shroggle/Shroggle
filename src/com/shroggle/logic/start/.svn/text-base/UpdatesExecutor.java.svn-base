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
package com.shroggle.logic.start;

import com.shroggle.util.config.Config;
import com.shroggle.util.config.ConfigDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.shroggle.util.ServiceLocator.getConfigStorage;

/**
 * @author Balakirev Anatoliy
 */
public abstract class UpdatesExecutor {

    /**
     * Check in database table "versions"
     * If table doesn't exists system don't update and create table and set max found update version.
     * If table exist but doesn't contain any version system set max found update version.
     * If table exist and contain data system update system from old version to max found version.
     */
    abstract void execute();

    void setCurrentDataVersion(final int newDataVersion) {
        final Config config = getConfigStorage().get();
        final ConfigDatabase configDatabase = config.getDatabase();
        try {
            Class.forName(configDatabase.getDriver());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    configDatabase.getUrl(), configDatabase.getLogin(), configDatabase.getPassword());

            final ResultSet existsSet = connection.getMetaData().getTables(null, null, "VERSIONS", null);
            if (!existsSet.next()) {
                connection.prepareStatement("create table VERSIONS (value int not null);").executeUpdate();
            }

            connection.setAutoCommit(false);
            connection.prepareStatement("delete from VERSIONS").execute();
            connection.prepareStatement("insert into VERSIONS values (" + newDataVersion + ")").execute();
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

            throw new RuntimeException(e);
        }
    }

}
