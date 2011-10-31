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
package com.shroggle.util.process.timecounter.simple;

import org.junit.Test;

/**
 * @author Artem Stasuk
 */
public class TimeCounterSimpleTest {

    @Test
    public void create() {
        new TimeCounterSimple(new TimeCounterResultSimple("f"));
    }

    @Test
    public void stop() {
        TimeCounterSimple simple = new TimeCounterSimple(new TimeCounterResultSimple("f"));
        simple.stop();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void stopTwice() {
        TimeCounterSimple simple = new TimeCounterSimple(new TimeCounterResultSimple("f"));
        simple.stop();
        simple.stop();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createWithNull() {
        new TimeCounterSimple(null);
    }

}
