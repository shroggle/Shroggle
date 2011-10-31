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
package com.shroggle.util.persistance;

import com.shroggle.PersistanceMock;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.TimerTaskMock;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Test;

/**
 * @author Artem Stasuk
 */
public class PersistanceTimerTaskWithContextTest {

    @After
    public void after() {
        ServiceLocator.setPersistance(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void runWithoutPersistance() {
        ServiceLocator.setPersistance(null);
        new PersistanceTimerTaskWithContext(new TimerTaskMock()).run();
    }

    @Test
    public void run() {
        ServiceLocator.setPersistance(new PersistanceMock());
        final TimerTaskMock timerTaskMock = new TimerTaskMock();
        new PersistanceTimerTaskWithContext(timerTaskMock).run();

        Assert.assertTrue(timerTaskMock.isAlredyRun());
    }

}
