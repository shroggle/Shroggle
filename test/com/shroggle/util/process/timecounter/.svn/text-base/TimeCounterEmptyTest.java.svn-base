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
package com.shroggle.util.process.timecounter;

import org.junit.Test;

/**
 * Author: Artem Stasuk (artem).
 * </p>
 * Date: 24.10.2008
 */
public class TimeCounterEmptyTest {

    @Test
    public void create() {
        new TimeCounterEmpty();
    }

    @Test
    public void stop() {
        new TimeCounterEmpty().stop();
    }

    @Test
    public void stopTwice() {
        final TimeCounter timeCounter = new TimeCounterEmpty();
        timeCounter.stop();
        timeCounter.stop();
    }

}
