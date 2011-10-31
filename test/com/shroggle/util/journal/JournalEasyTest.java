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
import com.shroggle.util.context.ContextManual;
import com.shroggle.util.context.ContextStorage;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author Artem Stasuk
 */
public class JournalEasyTest {

    @Before
    public void before() {
        ServiceLocator.setJournal(journalMock);
        ServiceLocator.setContextStorage(new ContextStorage());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createWithNull() {
        new JournalEasy(null);
    }

    @Test
    public void add() {
        journalEasy.add(JournalItemPriority.FATAL, "FFF");

        final List<JournalItem> journalItems = journalMock.get();
        Assert.assertNotNull(journalItems);
        Assert.assertEquals(JournalItemPriority.FATAL, journalItems.get(0).getPriority());
        Assert.assertEquals("JournalEasyTest", journalItems.get(0).getClassName());
        Assert.assertNotNull(journalItems.get(0).getCreated());
        Assert.assertEquals("FFF", journalItems.get(0).getMessage());
    }

    @Test
    public void addWithContext() {
        final Context context = new ContextManual();
        context.setUserId(33);
        ServiceLocator.getContextStorage().set(context);
        journalEasy.add(JournalItemPriority.FATAL, "FFF");

        final List<JournalItem> journalItems = journalMock.get();
        Assert.assertNotNull(journalItems);
        Assert.assertEquals(JournalItemPriority.FATAL, journalItems.get(0).getPriority());
        Assert.assertEquals("JournalEasyTest", journalItems.get(0).getClassName());
        Assert.assertNotNull(journalItems.get(0).getCreated());
        Assert.assertEquals((Integer) 33, journalItems.get(0).getUserId());
        Assert.assertEquals("FFF", journalItems.get(0).getMessage());
    }

    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    @Test
    public void addWithException() {
        journalEasy.add(JournalItemPriority.FATAL, "FFF", new Exception());

        final List<JournalItem> journalItems = journalMock.get();
        Assert.assertNotNull(journalItems);
        Assert.assertEquals(JournalItemPriority.FATAL, journalItems.get(0).getPriority());
        Assert.assertEquals("JournalEasyTest", journalItems.get(0).getClassName());
        Assert.assertNotNull(journalItems.get(0).getCreated());
        Assert.assertNotNull(journalItems.get(0).getMessage());
    }

    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    @Test
    public void addWithoutMessage() {
        journalEasy.add(JournalItemPriority.FATAL, new Exception());

        final List<JournalItem> journalItems = journalMock.get();
        Assert.assertNotNull(journalItems);
        Assert.assertEquals(JournalItemPriority.FATAL, journalItems.get(0).getPriority());
        Assert.assertEquals("JournalEasyTest", journalItems.get(0).getClassName());
        Assert.assertNotNull(journalItems.get(0).getCreated());
        Assert.assertNotNull(journalItems.get(0).getMessage());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void addWithNullPriority() {
        journalEasy.add(null, "FFF");
    }

    private final JournalMock journalMock = new JournalMock();
    private final JournalEasy journalEasy = new JournalEasy(JournalEasyTest.class);

}
