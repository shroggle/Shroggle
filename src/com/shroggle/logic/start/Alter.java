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

import com.shroggle.exception.UnknownAlterTypeException;

import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * @author Balakirev Anatoliy
 */
public class Alter {

    public static Alter newInstance(String value) {
        final String normalizedValue = removeCommentsAndNewLines(value);
        if (normalizedValue.isEmpty()) {
            return null;
        }
        final Alter alter = new Alter();
        alter.setValue(normalizedValue);
        alter.setType(createAlterType(normalizedValue));
        return alter.setShowLogs(true);
    }

    private String value;

    private AlterType type;

    private boolean showLogs;

    public Alter setShowLogs(boolean showLogs) {
        this.showLogs = showLogs;
        return this;
    }

    public boolean isShowLogs() {
        return showLogs;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public AlterType getType() {
        return type;
    }

    public void setType(AlterType type) {
        this.type = type;
    }

    public void logBefore() {
        AlterLogger.newInstance(this).logBefore();
    }

    public void logAfter(final int changedRowsCount) {
        AlterLogger.newInstance(this).logAfter(changedRowsCount);
    }

    private Alter() {
    }

    private static String removeCommentsAndNewLines(String value) {
        value = value.replace("\r", "\n").trim();
        final String[] values = value.split("\n");
        String newValue = "";
        for (String tempValue : values) {
            if (!tempValue.startsWith("--")) {
                newValue += tempValue + " ";
            }
        }
        newValue = newValue.trim();
        if (newValue.isEmpty()) {
            return newValue;
        }
        newValue = newValue + (newValue.endsWith(";") ? "" : ";");
        return newValue;
    }

    private static AlterType createAlterType(String value) {
        value = value.toLowerCase();
        if (Pattern.compile("(?:^|\\s++)add\\s+?").matcher(value).find()) {
            return AlterType.ADD;
        }
        if (Pattern.compile("(?:^|\\s++)change\\s+?").matcher(value).find()) {
            return AlterType.CHANGE;
        }
        if (Pattern.compile("(?:^|\\s++)delete\\s+?").matcher(value).find()) {
            return AlterType.DELETE;
        }
        if (Pattern.compile("(?:^|\\s++)drop\\s+?").matcher(value).find()) {
            return AlterType.DROP;
        }
        if (Pattern.compile("(?:^|\\s++)insert\\s+?").matcher(value).find()) {
            return AlterType.INSERT;
        }
        if (Pattern.compile("(?:^|\\s++)update\\s+?").matcher(value).find()) {
            return AlterType.UPDATE;
        }
        if (Pattern.compile("(?:^|\\s++)modify\\s+?").matcher(value).find()) {
            return AlterType.MODIFY;
        }
        if (Pattern.compile("(?:^|\\s++)create\\s+?").matcher(value).find()) {
            return AlterType.CREATE;
        }
        if (Pattern.compile("(?:^|\\s++)set\\s+?").matcher(value).find()) {
            return AlterType.SET;
        }
        if (Pattern.compile("(?:^|\\s++)start\\s+?").matcher(value).find()) {
            return AlterType.START;
        }
        if (Pattern.compile("(?:^|\\s++)select\\s+?").matcher(value).find()) {
            return AlterType.SELECT;
        }
        if (Pattern.compile("(?:^|\\s++)commit\\s*?").matcher(value).find()) {
            return AlterType.COMMIT;
        }
        if (Pattern.compile("(?:^|\\s++)rename\\s*?").matcher(value).find()) {
            return AlterType.RENAME;
        }

        throw new UnknownAlterTypeException("Can`t create alter type for alter: " + value);
    }

    private static abstract class AlterLogger {

        public abstract void logBefore();

        public abstract void logAfter(final int changedRowsCount);

        public static AlterLogger newInstance(final Alter alter) {
            if (alter.isShowLogs()) {
                return new AlterLoggerReal(alter);
            } else {
                return new AlterLoggerEmpty();
            }
        }

        private static class AlterLoggerReal extends AlterLogger {

            private AlterLoggerReal(final Alter alter) {
                this.alter = alter;
            }

            public void logBefore() {
                logger.info("Trying to execute " + (alter.getType().isIgnoreExecutionFail() ? "not " : "") + "required alter:\n" + alter.getValue());
            }

            public void logAfter(final int changedRowsCount) {
                logger.info("Alter successfully executed:\n" + alter.getValue() + "\n" + changedRowsCount + " rows affected.\n");
            }

            final Alter alter;
            final Logger logger = Logger.getLogger(this.getClass().getName());
        }

        private static class AlterLoggerEmpty extends AlterLogger {
            public void logBefore() {
            }

            public void logAfter(final int changedRowsCount) {
            }
        }
    }
}