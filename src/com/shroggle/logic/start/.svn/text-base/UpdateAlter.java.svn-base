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

import com.shroggle.exception.AltersFileNotFoundException;
import com.shroggle.exception.RequiredAlterCantBeExecutedException;
import com.shroggle.util.config.Config;
import com.shroggle.util.config.ConfigDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.shroggle.util.ServiceLocator.getConfigStorage;

/**
 * @author Balakirev Anatoliy
 */
public class UpdateAlter implements Update {

    public UpdateAlter(int version, String path) {
        this.version = version;
        this.path = path;
    }

    /**
     * Be careful, some alters use connection variable in this cases all alters must executed through the same connection.
     */
    @Override
    public void execute() {
        final List<String> alterValues = getAlterValues(readAltersFile());
        if (!alterValues.isEmpty()) {
            final Connection connection = createConnection();
            try {
                for (String alterValue : alterValues) {
                    final Alter alter = Alter.newInstance(alterValue);
                    if (alter == null) {
                        continue;
                    }

                    try {
                        executeAlter(alter, connection);
                    } catch (final Exception exception) {
                        if (alter.getType().isIgnoreExecutionFail()) {
                            logger.log(Level.INFO, "Not required alter can`t be executed:\n" + alter.getValue() + "\n", exception);
                        } else {
                            throw new RequiredAlterCantBeExecutedException("\nUnable to execute required alter:\n" + alter.getValue() + "\n", exception);
                        }
                    }
                }
            } finally {
                try {
                    connection.close();
                } catch (final SQLException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    @Override
    public int getVersion() {
        return version;
    }

    private void executeAlter(final Alter alter, final Connection connection) throws SQLException {
        logger.info("Trying to execute " + (alter.getType().isIgnoreExecutionFail() ? "not required" : "required") + " alter:\n" + alter.getValue());
        final PreparedStatement preparedStatement = connection.prepareStatement(alter.getValue());
        preparedStatement.execute();
        final int changedRows = preparedStatement.getUpdateCount();
        logger.info("Alter successfully executed:\n" + alter.getValue() + "\n" + changedRows + " rows affected.\n");
    }


    private Connection createConnection() {
        final Config config = getConfigStorage().get();
        final ConfigDatabase configDatabase = config.getDatabase();
        try {
            Class.forName(configDatabase.getDriver());
        } catch (final ClassNotFoundException exception) {
            throw new RuntimeException(exception);
        }

        try {
            return DriverManager.getConnection(
                    configDatabase.getUrl(), configDatabase.getLogin(), configDatabase.getPassword());
        } catch (final SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    private String readAltersFile() throws AltersFileNotFoundException {
        try {
            final File file = new File(path);
            final FileInputStream fileInputStream;
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[fileInputStream.available()];
            final int read = fileInputStream.read(buffer);
            if (read == buffer.length) {
                return new String(buffer);
            } else {
                throw new IOException("Error while reading file.");
            }
        } catch (Exception exception) {
            throw new AltersFileNotFoundException("Can`t read file with alters.", exception);
        }
    }

    List<String> getAlterValues(final String altersFile) {
        final List<String> normalizedAltersValues = new ArrayList<String>();
        final Iterator<String> iterator = new ArrayList<String>(Arrays.asList(altersFile.split(";"))).iterator();
        while (iterator.hasNext()) {
            String alter = iterator.next();
            while (!containsOddQuotesCount(alter)) {
                alter += iterator.next();
            }
            normalizedAltersValues.add(alter);
        }
        return normalizedAltersValues;
    }

    private boolean containsOddQuotesCount(final String altersValue) {
        final Matcher matcher = Pattern.compile("'").matcher(altersValue);
        int quotesCount = 0;
        while (matcher.find()) {
            quotesCount++;
        }
        return (quotesCount % 2 == 0);
    }

    private final int version;
    private final String path;
    private final Logger logger = Logger.getLogger(this.getClass().getName());
}
