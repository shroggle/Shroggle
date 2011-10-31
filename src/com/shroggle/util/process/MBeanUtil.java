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
package com.shroggle.util.process;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Artem Stasuk
 */
public class MBeanUtil {

    public static long gcTime() {
        try {
            long totalGcTime = 0;
            for (final GarbageCollectorMXBean gc : sun.management.ManagementFactory.getGarbageCollectorMXBeans()) {
                totalGcTime += gc.getCollectionTime();
            }
            return totalGcTime;
        } catch (final Exception exception) {
            logger.log(Level.WARNING, "get gc time", exception);
        }

        return -1;
    }

//    public static long getTomcatGlobalSentBytes() {
//        try {
//            long result = 0;
//
//            for (final ObjectInstance objectInstance : tomcatGlobalRequestInstances) {
//                result += ((Number) mbeanServer.getAttribute(objectInstance.getObjectName(), "bytesSent")).longValue();
//            }
//
//            return result;
//        } catch (final Exception exception) {
//            logger.log(Level.WARNING, "get request sent", exception);
//        }
//
//        return -1;
//    }

//    public static long getTomcatGlobalReceivedBytes() {
//        try {
//            long result = 0;
//
//            for (final ObjectInstance objectInstance : tomcatGlobalRequestInstances) {
//                result += ((Number) mbeanServer.getAttribute(objectInstance.getObjectName(), "bytesReceived")).longValue();
//            }
//
//            return result;
//        } catch (final Exception exception) {
//            logger.log(Level.WARNING, "get request received", exception);
//        }
//
//        return -1;
//    }

    public static double getLoadAverage() {
        OperatingSystemMXBean s = ManagementFactory.getOperatingSystemMXBean();
        try {
            return (Double) s.getClass().getMethod("getSystemLoadAverage").invoke(s);
        } catch (final Exception exception) {
            logger.log(Level.WARNING, "get load average method", exception);
        }
        return -2d;
    }

    private static final Logger logger = Logger.getLogger(MBeanUtil.class.getSimpleName());
//    private static final Set<ObjectInstance> tomcatGlobalRequestInstances;
//    private static final MBeanServer mbeanServer;

//    static {
//        Set<ObjectInstance> tempTomcatGlobalRequestInstances = null;
//        MBeanServer tempMBeanServer = null;
//
//        try {
//            final ArrayList<MBeanServer> mBeanServers = MBeanServerFactory.findMBeanServer(null);
//            if (mBeanServers.size() > 0) {
//                tempMBeanServer = mBeanServers.get(0);
//                tempTomcatGlobalRequestInstances = tempMBeanServer.queryMBeans(
//                        new ObjectName("Catalina:type=GlobalRequestProcessor,name=*"), null);
//            }
//        } catch (final Exception exception) {
//            logger.log(Level.WARNING, "get catalina mBeans", exception);
//        }
//
//        mbeanServer = tempMBeanServer;
//        tomcatGlobalRequestInstances = tempTomcatGlobalRequestInstances;
//    }

}
