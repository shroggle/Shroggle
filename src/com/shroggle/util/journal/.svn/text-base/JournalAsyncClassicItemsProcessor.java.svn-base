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

import java.util.TimerTask;

/**
 * @author Artem Stasuk
 */
final class JournalAsyncClassicItemsProcessor extends TimerTask {

    public JournalAsyncClassicItemsProcessor(final JournalAsyncClassic journalAsync, final Journal journal) {
        this.journalAsync = journalAsync;
        this.journal = journal;
    }

    public void run() {
        journal.add(journalAsync.pollAll());
    }

    private final JournalAsyncClassic journalAsync;
    private final Journal journal;

}
