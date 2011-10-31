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

import com.shroggle.PersistanceMock;
import com.shroggle.entity.JournalItem;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.process.ThreadUtil;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Artem Stasuk
 */
public class JournalAsyncClassicTest {

    @Before
    public void before() {
        ServiceLocator.setPersistance(new PersistanceMock());
    }

    @After
    public void after() {
        ServiceLocator.setPersistance(null);
    }

    @Test
    public void add() {
        JournalMock journalMock = new JournalMock();
        Journal journal = new JournalAsyncClassic(journalMock);
        journal.add(Arrays.asList(new JournalItem()));

        Assert.assertNull(journalMock.get());
    }

    @Test
    public void addWithWait() {
        JournalMock journalMock = new JournalMock();
        Journal journal = new JournalAsyncClassic(journalMock);
        JournalItem journalItem = new JournalItem();
        journal.add(Arrays.asList(new JournalItem(), journalItem));
        ThreadUtil.sleep(1000L * 15L);

        Assert.assertEquals(journalItem, journalMock.get().get(1));
    }

    @Test(timeout = 100)
    public void addAndNoWait() {
        JournalSleep journalSleep = new JournalSleep();
        journalSleep.setSleep(10000L);
        Journal journal = new JournalAsyncClassic(journalSleep);
        journal.add(Arrays.asList(new JournalItem()));
    }

    @Test
    public void destroy() {
        JournalMock journalMock = new JournalMock();
        Journal journal = new JournalAsyncClassic(journalMock);
        JournalItem journalItem = new JournalItem();
        journal.add(Arrays.asList(journalItem));
        journal.destroy();

        Assert.assertEquals(journalItem, journalMock.get().get(0));
    }

}
