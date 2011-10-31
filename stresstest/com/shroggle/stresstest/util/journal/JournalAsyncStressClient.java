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
package com.shroggle.stresstest.util.journal;

import com.shroggle.entity.JournalItem;
import com.shroggle.util.journal.Journal;

import java.util.Arrays;

/**
 * @author Artem Stasuk
 */
final class JournalAsyncStressClient {

    public JournalAsyncStressClient(final Journal journal, final int count) {
        this.thread = new Thread(new Runnable() {

            public void run() {
                for (int i = 0; i < count; i++) {
                    JournalItem journalItem = new JournalItem();
                    journalItem.setMessage(String.valueOf(counter++));
                    final long start = System.currentTimeMillis();
                    journal.add(Arrays.asList(journalItem));
                    time += System.currentTimeMillis() - start;
                }
            }

            private int counter;

        });
    }

    public Thread getThread() {
        return thread;
    }

    public long getTime() {
        return time;
    }

    private final Thread thread;
    private volatile long time;

}
