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
package com.shroggle.util.testhelp;

import com.shroggle.entity.JournalItem;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.journal.Journal;

import java.util.List;

/**
 * @author Artem Stasuk
 */
public class TestHelpJournal implements Journal {

    public void add(final List<JournalItem> journalItems) {
        for (final JournalItem journalItem : journalItems) {
            final TestHelpStorage testHelpStorage = ServiceLocator.getTestHelpStorage();
            if (testHelpStorage != null) {
                final StringBuilder journalItemString = new StringBuilder(1024);
                journalItemString.append(journalItem.getCreated());
                journalItemString.append(" ");
                journalItemString.append(journalItem.getClassName());
                journalItemString.append(" ");
                journalItemString.append(journalItem.getPriority());
                journalItemString.append(" ");
                journalItemString.append(journalItem.getSessionId());
                journalItemString.append(" ");
                journalItemString.append(journalItem.getUserId());
                journalItemString.append(" ");
                journalItemString.append(journalItem.getUserId());
                journalItemString.append(" ");
                journalItemString.append(journalItem.getMessage());
                journalItemString.append("\n");

                testHelpStorage.addEvent(TestHelpSource.JOURNAL, journalItemString.toString());
            }
        }
    }

    public void destroy() {
    }

}