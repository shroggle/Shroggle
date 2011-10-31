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
package com.shroggle.stresstest.application;

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.process.timecounter.TimeCounterCreatorPrefix;
import com.shroggle.util.process.timecounter.simple.TimeCounterCreatorSimple;
import com.shroggle.util.process.timecounter.TimeCounterResult;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Stasuk Artem
 */
public class ApplicationRunner {

    public static void main(final String[] args) throws Exception {
        final List<TimeCounterResult> results = new ArrayList<TimeCounterResult>();

        mainInternal(results, "http://www.shroggle.com");
        mainInternal(results, "http://www.wikalot.com");

        final Logger logger = Logger.getLogger(ApplicationRunner.class.getSimpleName());
        for (final TimeCounterResult result : results) {
            logger.info(result.getName() + ", count: " + result.getExecutedCount() + ", average: "
                    + (result.getExecutedTime() / result.getExecutedCount()) + " msec, total: " + result.getExecutedTime() + " msec");
        }
    }

    private static void mainInternal(List<TimeCounterResult> results, String server) throws Exception {
        ServiceLocator.setTimeCounterCreator(new TimeCounterCreatorPrefix(server, new TimeCounterCreatorSimple()));
        new CommandPressure(server).execute();
        results.addAll(ServiceLocator.getTimeCounterCreator().getResults());
        ServiceLocator.getTimeCounterCreator().clear();
    }

}
