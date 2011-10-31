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

import junit.framework.Assert;
import org.junit.Test;

/**
 * @author Artem Stasuk
 */
public class TestHelpStorageTest {

    @Test
    public void addEvent() {
        testHelpStorage.addEvent(TestHelpSource.COMMONS_LOG4J, "F");
        testHelpStorage.addEvent(TestHelpSource.JOURNAL, "F1");

        Assert.assertEquals(1, testHelpStorage.getEvents().get(TestHelpSource.COMMONS_LOG4J).get().size());
        Assert.assertEquals(1, testHelpStorage.getEvents().get(TestHelpSource.JOURNAL).get().size());
        Assert.assertEquals(0, testHelpStorage.getEvents().get(TestHelpSource.LOGGER).get().size());
    }

    private final TestHelpStorage testHelpStorage = new TestHelpStorage();

}
