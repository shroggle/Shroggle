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
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.Context;
import com.shroggle.util.context.ContextStorage;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Date;

/**
 * @author Artem Stasuk
 */
public class JournalEasy {

    public JournalEasy(final Class journalClass) {
        if (journalClass == null) {
            throw new UnsupportedOperationException("Can't create with null class!");
        }
        this.journalClassName = journalClass.getSimpleName();
    }

    public void add(final JournalItemPriority priority, final String message) {
        if (priority == null) {
            throw new UnsupportedOperationException("Can't add with null priority!");
        }
        add(priority, message, null);
    }

    public void add(final JournalItemPriority priority, final Throwable throwable) {
        add(priority, null, throwable);
    }

    public void add(final JournalItemPriority priority, final String message, final Throwable throwable) {
        final JournalItem journalItem = new JournalItem();
        journalItem.setCreated(new Date());
        journalItem.setMessage(message);
        if (throwable != null) {
            final StringWriter messageStringWriter = new StringWriter(1024 * 1024);
            final PrintWriter messagePrintWriter = new PrintWriter(messageStringWriter);
            if (journalItem.getMessage() != null) {
                messagePrintWriter.println(journalItem.getMessage());
            }
            throwable.printStackTrace(messagePrintWriter);
            journalItem.setMessage(messageStringWriter.toString());
        }

        journalItem.setClassName(journalClassName);

        final ContextStorage contextStorage = ServiceLocator.getContextStorage();
        if (contextStorage == null) return; 

        final Context context = contextStorage.get();
        if (context != null) {
            journalItem.setVisitorId(context.getUserId());
            journalItem.setUserId(context.getUserId());
            journalItem.setSessionId(context.getSessionId());
        }
        journalItem.setPriority(priority);

        final Journal journal = ServiceLocator.getJournal();
        if (journal == null) return;

        journal.add(Arrays.asList(journalItem));
    }

    private final String journalClassName;

}
