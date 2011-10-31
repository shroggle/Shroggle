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

import com.shroggle.util.process.ThreadUtil;
import com.shroggle.util.process.timecounter.simple.TimeCounterCreatorSimple;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Artem Stasuk
 */
public class TimeCounterResultComparatorByAverageTest {

    @Test
    public void compare() {
        TimeCounterCreator creator = new TimeCounterCreatorSimple();
        TimeCounter time0 = creator.create("g");
        ThreadUtil.sleep(1);
        time0.stop();
        TimeCounter time1 = creator.create("f");
        ThreadUtil.sleep(2);
        time1.stop();
        List<TimeCounterResult> results = new ArrayList<TimeCounterResult>(creator.getResults());
        Collections.sort(results, TimeCounterResultComparatorByAverage.instance);

        Assert.assertEquals("g", results.get(0).getName());
        Assert.assertEquals("f", results.get(1).getName());
    }

}
