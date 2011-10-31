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

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This journal fasted journal based on volatile.
 *
 * @author Artem Stasuk
 */
public class JournalAsyncClassic implements Journal {

    public JournalAsyncClassic(final Journal journal) {
        itemsProcessorTimer = new Timer("journalAsync", true);
        itemsProcessor = new JournalAsyncClassicItemsProcessor(this, journal);
        itemsProcessorTimer.schedule(
                itemsProcessor, itemsProcessorStartPeriod, itemsProcessorStartPeriod);
    }

    public void add(final List<JournalItem> journalItems) {
        synchronized (notProcessItems) {
            notProcessItems.addAll(journalItems);
        }
    }

    public void destroy() {
        itemsProcessorTimer.cancel();
        // Need for process all stay items
        itemsProcessor.run();
    }

    List<JournalItem> pollAll() {
        synchronized (notProcessItems) {
            final List<JournalItem> tempJournalItems = new ArrayList<JournalItem>();
            tempJournalItems.addAll(notProcessItems);
            notProcessItems.clear();
            return tempJournalItems;
        }
    }

    private final static long itemsProcessorStartPeriod = 1000L * 10L;
    private final List<JournalItem> notProcessItems = new ArrayList<JournalItem>();
    private final Timer itemsProcessorTimer;
    private final TimerTask itemsProcessor;

}
