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

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.html.HttpUtil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Artem Stasuk
 */
class CheckPerformanceThread extends Thread {

    public CheckPerformanceThread(final CheckPerformanceStatistics statistics, final int threadCount, final long requestDelay) {
        super("CheckPerformanceThread");
        setDaemon(true);

        this.statistics = statistics;
        this.threadCount = threadCount;
        this.requestDelay = requestDelay;
    }

    @Override
    public void run() {
        logger.info("start thread count: " + threadCount + ", request delay: " + requestDelay);

        final List<String> urls = readUrls();

        logger.info("read " + urls.size() + " urls");

        int threadCount = this.threadCount;

        while (true) {
            logger.info("start on " + threadCount + " threads");

            final ThreadGroup threadGroup = new ThreadGroup("TestSpeedCallerGroup {threadCount: " + threadCount + "}");
            threadGroup.setDaemon(true);

            final CheckPerformanceStatisticsItem statisticsItem = new CheckPerformanceStatisticsItem(threadCount, requestDelay);
            statistics.add(statisticsItem);

            final Thread[] threads = new Thread[threadCount];

            for (int i = 0; i < threads.length; i++) {
                threads[i] = new Thread(threadGroup, new Runnable() {

                    @Override
                    public void run() {
                        executeLoop(urls, statisticsItem);
                    }

                }, "TestSpeedCaller {index: " + i + "}");

                threads[i].setDaemon(true);
                threads[i].start();

                try {
                    Thread.sleep(requestDelay);
                } catch (final InterruptedException exception) {
                    threadGroup.interrupt();
                    logger.info("finish on " + threadCount + " threads, by interrupt!");
                    return;
                }
            }

            for (final Thread thread : threads) {
                try {
                    thread.join();
                } catch (final InterruptedException exception) {
                    threadGroup.interrupt();
                    logger.info("finish on " + threadCount + " threads, by interrupt!");
                    return;
                }
            }

            final int allBadErrors = statisticsItem.getIOErrorCount()
                    + statisticsItem.getServerErrorCount() + statisticsItem.getUnknownErrorCount();
            if (allBadErrors > statisticsItem.getSuccessCount()) {
                break;
            }

            threadCount = threadCount * 2;
        }

        logger.info("finish on " + threadCount + " threads");
    }

    private List<String> readUrls() {
        final String applicationUrl = ServiceLocator.getConfigStorage().get().getApplicationUrl();

        final String mapUrl = "http://" + applicationUrl + "/map.action";
        final String mapHtml;
        try {
            mapHtml = HttpUtil.read(mapUrl);
        } catch (IOException e) {
            throw new RuntimeException(mapUrl, e);
        }

        final List<String> urls = new ArrayList<String>();
        final Pattern pattern = Pattern.compile("<a href=\"([a-zA-Z0-9:/_=?;%.]+)\"");
        final Matcher matcher = pattern.matcher(mapHtml);
        while (matcher.find()) {
            urls.add(matcher.group(1));
        }
        return urls;
    }

    private void executeLoop(final List<String> urls, final CheckPerformanceStatisticsItem statisticsItem) {
        for (final String url : urls) {
            if (isInterrupted()) {
                logger.info(Thread.currentThread().getName() + " finish by interrupt on url: " + url);
                return;
            }

            executeCall(url, statisticsItem);

            try {
                sleep(requestDelay);
            } catch (final InterruptedException exception) {
                return;
            }
        }
    }

    private void executeCall(final String url, final CheckPerformanceStatisticsItem statisticsItem) {
        final long start = System.currentTimeMillis();

        final HttpURLConnection connection;

        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
        } catch (final IOException exception) {
            statisticsItem.addIOError();
            logger.log(Level.SEVERE, Thread.currentThread().getName() + " can't connect to url: " + url, exception);
            return;
        }

        connection.setConnectTimeout(40000);
        connection.setReadTimeout(60000);

        int responseCode;
        try {
            responseCode = connection.getResponseCode();
        } catch (final IOException exception) {
            statisticsItem.addIOError();
            connection.disconnect();
            logger.log(Level.SEVERE, Thread.currentThread().getName() + " can't get response code for url: " + url, exception);
            return;
        }

        if (responseCode == 404) {
            statisticsItem.addNotFound();
            connection.disconnect();
        } else if (responseCode == 500) {
            statisticsItem.addServerError();
            connection.disconnect();
        } else if (responseCode == 200) {
            final int total;
            try {
                total = HttpUtil.read(connection).length();
                connection.disconnect();
            } catch (final IOException exception) {
                connection.disconnect();
                statisticsItem.addIOError();
                logger.log(Level.SEVERE, Thread.currentThread().getName() + " can't read response for url: " + url, exception);
                return;
            }

            final long delta = System.currentTimeMillis() - start;
            statisticsItem.addSuccess(delta, total);
        } else {
            connection.disconnect();
            statisticsItem.addUnknownError();
            logger.log(Level.SEVERE, Thread.currentThread().getName() + " unknown response code for url: " + url);
        }
    }

    private final long requestDelay;
    private final int threadCount;
    private final CheckPerformanceStatistics statistics;
    private final static Logger logger = Logger.getLogger(CheckPerformanceThread.class.getSimpleName());

}
