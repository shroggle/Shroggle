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

import com.shroggle.util.IOUtil;
import com.shroggle.util.config.Config;
import com.shroggle.util.config.ConfigDatabase;

import java.io.File;
import java.sql.*;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.shroggle.util.ServiceLocator.getConfigStorage;

/**
 * @author Balakirev Anatoliy
 */
public class Updates {
    /**
     * Return updates executor.
     *
     * @param configFile - Config
     * @return UpdatesExecutor
     */
    static UpdatesExecutor getExecutor(final String configFile) {
        final int currentVersion = getCurrentDataVersion();
        if (currentVersion > 0) {
            return new UpdatesExecutorReal(new Updates(currentVersion, configFile));
        } else {
            return new UpdatesExecutorMock(new Updates(currentVersion, configFile));
        }
    }

    boolean hasNext() {
        return !unexecutedUpdates.isEmpty();
    }

    Update next() {
        if (!hasNext()) {
            throw new UnsupportedOperationException("Unable to get next update! Use hasNext method to check next update availability.");
        }
        final int version = unexecutedUpdates.firstKey();
        final String path = unexecutedUpdates.remove(version);
        if (path.endsWith("jar")) {
            return new UpdateJar(version, (BASE_DIR + File.separator + path), this.configFile);
        } else {
            return new UpdateAlter(version, (BASE_DIR + File.separator + path));
        }
    }

    private final String configFile;
    private final SortedMap<Integer, String> unexecutedUpdates;
    private static final String BASE_DIR = (IOUtil.baseDir() + "/WEB-INF/updates");

    /*----------------------------------------Private methods and constructors----------------------------------------*/

    private Updates(final int currentVersion, final String configFile) {
        this.unexecutedUpdates = getUnexecutedUpdates(currentVersion);
        this.configFile = configFile;
    }

    private static SortedMap<Integer, String> getUnexecutedUpdates(final int currentDataVersion) {
        final SortedMap<Integer, String> unexecutedUpdates = new TreeMap<Integer, String>();
        final String[] updates = new File(BASE_DIR).list();
        if (updates != null) {
            for (final String update : updates) {
                final int version = Integer.parseInt(update.replace("shroggle", "").replace(".jar", "").replace(".sql", ""));
                if (version > currentDataVersion) {
                    unexecutedUpdates.put(version, update);
                }
            }
        }
        return unexecutedUpdates;
    }

    private static int getCurrentDataVersion() {
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
            if (existsSet.next()) {
                final PreparedStatement statement = connection.prepareStatement("select max(value) from VERSIONS");
                final ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                return resultSet.getInt(1);
            }

            return 0;
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
    /*----------------------------------------Private methods and constructors----------------------------------------*/
}
