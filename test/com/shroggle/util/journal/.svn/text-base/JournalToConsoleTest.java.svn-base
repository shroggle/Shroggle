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
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Artem Stasuk
 */
public class JournalToConsoleTest {

    @Test(expected = UnsupportedOperationException.class)
    public void addNull() {
        journal.add(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void addWithNullPart() {
        JournalItem journalItem = new JournalItem();
        journal.add(Arrays.asList(journalItem));
    }

    @Test
    public void addWithNullPriority() {
        JournalItem journalItem = new JournalItem();
        journalItem.setClassName("F");
        journal.add(Arrays.asList(journalItem));
    }

    @Test
    public void add() {
        JournalItem journalItem = new JournalItem();
        journalItem.setPriority(JournalItemPriority.ERROR);
        journalItem.setClassName("F");
        journal.add(Arrays.asList(journalItem));
    }

    @Test
    public void destroy() {
        journal.destroy();
    }

    private final Journal journal = new JournalToConsole();

}
