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

import com.shroggle.util.journal.Journal;
import com.shroggle.util.journal.JournalAsyncClassic;
import com.shroggle.util.journal.JournalMock;

/**
 * Please run with -Xmx512M options.
 *
 * @author Artem Stasuk
 */
public class JournalAsyncStressTest {

    public static void main(String[] args) throws InterruptedException {
        final JournalMock journalMock = new JournalMock();
        final Journal journal = new JournalAsyncClassic(journalMock);
        final JournalAsyncStressClient[] clients = new JournalAsyncStressClient[countClient];
        for (int i = 0; i < clients.length; i++) {
            clients[i] = new JournalAsyncStressClient(journal, countStep);
        }
        for (final JournalAsyncStressClient client : clients) {
            client.getThread().start();
        }
        long totalTime = 0;
        for (final JournalAsyncStressClient client : clients) {
            client.getThread().join();
            totalTime += client.getTime();
        }

        System.out.println("Execute 1000 clients per 1000 add, total time: " + totalTime + " msec");
        System.out.println("on one operation need " + (float) totalTime / (countClient * countStep) + " msec");
    }

    private final static int countClient = 1000;
    private final static int countStep = 1000;

}
