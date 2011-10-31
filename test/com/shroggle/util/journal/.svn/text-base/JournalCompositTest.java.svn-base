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
import junit.framework.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Artem Stasuk
 */
public class JournalCompositTest {

    @Test
    public void add() {
        final JournalItem journalItem = new JournalItem();
        journal.add(Arrays.asList(journalItem));

        Assert.assertEquals(journalItem, journalMock1.get().get(0));
        Assert.assertEquals(journalItem, journalMock2.get().get(0));
    }

    @Test
    public void destroy() {
        journal.destroy();

        Assert.assertTrue(journalMock1.isDestroy());
        Assert.assertTrue(journalMock2.isDestroy());
    }

    private final JournalMock journalMock1 = new JournalMock();
    private final JournalMock journalMock2 = new JournalMock();
    private final Journal journal = new JournalComposit(Arrays.asList((Journal) journalMock1, journalMock2));

}
