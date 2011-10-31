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

/**
 * ServiceLocator.getCheckPerformance().start();
 * <p/>
 * ServiceLocator.getCheckPerformance().stop();
 * <p/>
 * TestSpeedStatus status = ServiceLocator.getCheckPerformance().getStatus();
 * status.xxx;
 * <p/>
 * request count
 * request time
 * <p/>
 * response size
 * response ok
 * response bad
 * response time
 *
 * @author Artem Stasuk
 */
public class CheckPerformanceStatisticsItem {

    public CheckPerformanceStatisticsItem(final int threadCount, final long requestDelay) {
        this.threadCount = threadCount;
        this.requestDelay = requestDelay;
    }

    public long getRequestDelay() {
        return requestDelay;
    }

    public synchronized int getSuccessCount() {
        return successCount;
    }

    public synchronized long getTime() {
        return time;
    }

    public synchronized long getSize() {
        return size;
    }

    public synchronized int getIOErrorCount() {
        return ioErrorCount;
    }

    public synchronized int getServerErrorCount() {
        return serverErrorCount;
    }

    public synchronized int getUnknownErrorCount() {
        return unknownErrorCount;
    }

    public synchronized int getNotFoundCount() {
        return notFoundCount;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public long getStartTime() {
        return startTime;
    }

    synchronized void addSuccess(final long time, final long size) {
        this.time += time;
        this.size += size;
        this.successCount++;
    }

    synchronized void addIOError() {
        this.ioErrorCount++;
    }

    synchronized void addNotFound() {
        this.notFoundCount++;
    }

    synchronized void addServerError() {
        this.serverErrorCount++;
    }

    synchronized void addUnknownError() {
        this.unknownErrorCount++;
    }

    private long size;
    private final int threadCount;
    private int successCount;
    private int ioErrorCount;
    private int notFoundCount;
    private int serverErrorCount;
    private int unknownErrorCount;
    private long time;
    private final long startTime = System.currentTimeMillis();
    private final long requestDelay;

}
