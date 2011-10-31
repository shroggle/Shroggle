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
package com.shroggle.util.testspeed;

import com.shroggle.util.process.ThreadUtil;

import java.util.List;

/**
 * @author Artem Stasuk
 */
public class CheckPerformanceRun {

    public static void main(String[] args) throws Exception {
        final CheckPerformance speed = new CheckPerformance();
        speed.start(2, 0);

        final Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (speed.isWork()) {
                    final List<CheckPerformanceStatisticsItem> statisticItems = speed.getStatistics().get();

                    if (statisticItems.size() > 0) {
                        final CheckPerformanceStatisticsItem statisticsItem = statisticItems.get(statisticItems.size() - 1);

                        final int count = statisticsItem.getSuccessCount();
                        final long time = statisticsItem.getTime();
                        final long size = statisticsItem.getSize();

                        System.out.println("CheckPerformance result: ");
                        System.out.println();
                        System.out.println("        threads: " + statisticsItem.getThreadCount());
                        System.out.println("     total time: " + time + " msec or " + time / 1000l + " sec");
                        System.out.println("     total size: " + size + " bytes or " + size / 1024l + " kb or " + size / 1024l / 1024l + " mb");
                        System.out.println("    total count: " + count);

                        if (count > 0) {
                            System.out.println("   average time: " + time / count + " msec or " + time / count / 1000l + " sec");
                            System.out.println("   average size: " + size / count + " bytes or " + size / count / 1024l + " kb");
                        }

                        System.out.println("---------------------------------------------------------------");
                        System.out.println();
                        System.out.println();
                    }

                    ThreadUtil.sleep(1000);
                }
            }

        }, "TestSpeedWaiter");

        thread.start();
        thread.join();
    }

}
