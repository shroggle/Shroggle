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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.entity.JournalItem;
import com.shroggle.util.ServiceLocator;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

/**
 * @author Artem Stasuk
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class TestHelpJournalTest {

    @Test
    public void add() {
        JournalItem journalItem = new JournalItem();
        testHelpJournal.add(Arrays.asList(journalItem));

        Assert.assertEquals(1, testHelpStorage.getEvents().get(TestHelpSource.JOURNAL).get().size());
    }

    @Test
    public void destroy() {
        testHelpJournal.destroy();
    }

    private final TestHelpStorage testHelpStorage = ServiceLocator.getTestHelpStorage();
    private final TestHelpJournal testHelpJournal = new TestHelpJournal();

}
