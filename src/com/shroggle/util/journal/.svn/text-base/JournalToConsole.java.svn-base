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
package com.shroggle.util.journal;

import com.shroggle.entity.JournalItem;
import com.shroggle.entity.JournalItemPriority;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Artem Stasuk
 */
public class JournalToConsole implements Journal {

    public void add(final List<JournalItem> journalItems) {
        if (journalItems == null) {
            throw new UnsupportedOperationException(
                    "Can't add null journal item!");
        }
        for (final JournalItem journalItem : journalItems) {
            if (journalItem.getClassName() == null) {
                throw new UnsupportedOperationException(
                        "Can't add journal item with null class name!");
            }
            final Logger logger = Logger.getLogger(journalItem.getClassName());
            logger.log(priorityToLevel(journalItem.getPriority()), journalItem.getMessage());
        }
    }

    public void destroy() {
    }

    private static Level priorityToLevel(final JournalItemPriority priority) {
        if (priority == null) return Level.ALL;
        switch (priority) {
            case WARNING:
                return Level.WARNING;
            case ERROR:
                return Level.SEVERE;
            case FATAL:
                return Level.SEVERE;
            case INFO:
                return Level.INFO;
            case NONE:
                return Level.OFF;
            default:
                return Level.ALL;
        }
    }

}
