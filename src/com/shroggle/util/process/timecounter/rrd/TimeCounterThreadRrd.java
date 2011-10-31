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
package com.shroggle.util.process.timecounter.rrd;

import com.shroggle.util.process.MBeanUtil;
import com.shroggle.util.process.ThreadUtil;
import com.shroggle.util.process.timecounter.TimeCounterCreator;
import com.shroggle.util.process.timecounter.TimeCounterException;
import com.shroggle.util.process.timecounter.TimeCounterResult;
import org.rrd4j.ConsolFun;
import org.rrd4j.DsType;
import org.rrd4j.core.RrdDb;
import org.rrd4j.core.RrdDef;
import org.rrd4j.core.Sample;
import org.rrd4j.core.Util;

import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Artem Stasuk
 */
public class TimeCounterThreadRrd extends Thread {

    public TimeCounterThreadRrd(final TimeCounterCreator creator) {
        super(TimeCounterThreadRrd.class.getSimpleName());
        this.creator = creator;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            try {
                updateRrds();
            } catch (final Throwable throwable) {
                logger.log(Level.SEVERE, "Error update rrd counters, but thread still work!", throwable);
            }

            final long delta = PERIOD - System.currentTimeMillis() % PERIOD;
            ThreadUtil.sleep(delta);
        }
    }

    public void updateRrds() {
        final Collection<TimeCounterResult> results = creator.getResults();

//        final long start = System.currentTimeMillis();

        long totalHttpTime = 0;
        int totalHttpCount = 0;
        long totalFileSystemTime = 0;
        int totalFileSystemCount = 0;
        long totalPersistanceTime = 0;
        int totalPersistanceCount = 0;

        for (final TimeCounterResult result : results) {
            if (result.getName().startsWith("http://")) {
                totalHttpTime += result.getExecutedTime();
                totalHttpCount += result.getExecutedCount();
            }

            if (result.getName().startsWith("fileSystem://")) {
                totalFileSystemTime += result.getExecutedTime();
                totalFileSystemCount += result.getExecutedCount();
            }

            if (result.getName().startsWith("persistance://") && !result.getName().startsWith("persistance://inContext")) {
                totalPersistanceTime += result.getExecutedTime();
                totalPersistanceCount += result.getExecutedCount();
            }
        }

        createOrUpdateRrdCounter("total://httpTime", totalHttpTime);
        createOrUpdateRrdCounter("total://httpCount", totalHttpCount);
        createOrUpdateRrdCounter("total://fileSystemTime", totalFileSystemTime);
        createOrUpdateRrdCounter("total://fileSystemCount", totalFileSystemCount);
        createOrUpdateRrdCounter("total://persistanceTime", totalPersistanceTime);
        createOrUpdateRrdCounter("total://persistanceCount", totalPersistanceCount);
//        System.out.println(totalPersistanceTime + "=" + totalPersistanceCount);

        createOrUpdateRrdCounter("total://gcTime", MBeanUtil.gcTime());
        final double loadAverage = MBeanUtil.getLoadAverage();
        createOrUpdateRrdGauge("total://loadAverage", loadAverage);
        final long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        createOrUpdateRrdGauge("total://memoryBytes", usedMemory);

//        System.out.println("UpdateRRD: " + (System.currentTimeMillis() - start) + " msec");
    }

    private void createOrUpdateRrd(final DsType dsType, final String name) {
        if (TimeCounterUtilRrd.notExist(name)) {
            try {
                createRrd(dsType, name);
            } catch (final IOException exception) {
                throw new TimeCounterException(exception);
            }
        }
    }

    private void createOrUpdateRrdGauge(final String name, final double value) {
        createOrUpdateRrd(DsType.GAUGE, name);
//
//        // Look on special operation
//        Double oldValue = oldValues.put(name, value);
//        if (oldValue == null) oldValue = 0d;
//        final double deltaValue = value - oldValue;

        updateRrd(name, value);
    }

    private void createOrUpdateRrdAbsolute(final String name, final double value) {
        createOrUpdateRrd(DsType.ABSOLUTE, name);
        updateRrd(name, value);
    }

    private void createOrUpdateRrdCounter(final String name, final double value) {
        createOrUpdateRrd(DsType.COUNTER, name);
        updateRrd(name, value);
    }

    private void updateRrd(final String name, final double value) {
        try {
            final RrdDb rrdDb = new RrdDb(TimeCounterUtilRrd.getPath(name));
            final Sample sample = rrdDb.createSample();
            sample.setAndUpdate("N:" + value);
            rrdDb.close();
        } catch (final IOException exception) {
            throw new TimeCounterException(exception);
        }
    }

    private void createRrd(final DsType dsType, final String name) throws IOException {
        final RrdDef rrdDef = new RrdDef(TimeCounterUtilRrd.getPath(name), STEP);
        rrdDef.setStartTime(Util.getTime() - 1);
        rrdDef.addDatasource(TimeCounterUtilRrd.getRrdName(name), dsType, HEARTBEAT, Double.NaN, Double.NaN);
        rrdDef.addArchive(ConsolFun.AVERAGE, 0.5, 1, 600);
        rrdDef.addArchive(ConsolFun.AVERAGE, 0.5, 6, 700);
        rrdDef.addArchive(ConsolFun.AVERAGE, 0.5, 24, 775);
        rrdDef.addArchive(ConsolFun.AVERAGE, 0.5, 288, 797);
        rrdDef.addArchive(ConsolFun.MAX, 0.5, 1, 600);
        rrdDef.addArchive(ConsolFun.MAX, 0.5, 6, 700);
        rrdDef.addArchive(ConsolFun.MAX, 0.5, 24, 775);
        rrdDef.addArchive(ConsolFun.MAX, 0.5, 288, 797);

        final RrdDb rrdDb = new RrdDb(rrdDef);
        rrdDb.close();
    }

    private final TimeCounterCreator creator;

    private final static long PERIOD = 60000l;
    private final static long STEP = PERIOD / 1000l;
    private final static long HEARTBEAT = STEP * 2l;
    private final static Logger logger = Logger.getLogger(TimeCounterThreadRrd.class.getSimpleName());

}
