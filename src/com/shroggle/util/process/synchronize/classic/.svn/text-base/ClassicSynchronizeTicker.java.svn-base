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
package com.shroggle.util.process.synchronize.classic;

import com.shroggle.util.process.synchronize.SynchronizePoint;

import java.util.Set;

/**
 * @author Stasuk Artem
 */
final class ClassicSynchronizeTicker {

    /**
     * Creates a new ticker and starts to lock points on it
     *
     * @param heap       - a shared storage of locked points
     * @param usedPoints - set points for a lock
     */
    public ClassicSynchronizeTicker(
            final ClassicSynchronizeHeap heap, final Set<SynchronizePoint> usedPoints) {
        if (heap == null) {
            throw new NullPointerException(
                    "Can't create ticker by null lock points storage!");
        }
        if (usedPoints == null) {
            throw new NullPointerException(
                    "Can't create ticker by null used points!");
        }

        this.heap = heap;
        this.usedPoints = usedPoints;

        // starts a new point, if they were not locked before 
        for (final SynchronizePoint key : this.usedPoints) {
            ClassicSynchronizePoint point = heap.used.get(key);
            if (point == null) {
                point = new ClassicSynchronizePoint();
                heap.used.put(key, point);
            }
            point.start(key.getMethod());
        }
    }

    /**
     * releases all points what locked by this ticker.<br>
     * The second call does nothing.
     */
    public void finish() {
        synchronized (heap) {
            for (final SynchronizePoint usedPoint : usedPoints) {
                final ClassicSynchronizePoint point1 = heap.used.get(usedPoint);
                point1.finish(usedPoint.getMethod());
                if (point1.isEmpty()) {
                    heap.used.remove(usedPoint);
                }
            }
            usedPoints.clear();
            // release other threads
            heap.notifyAll();
        }
    }

    private final Set<SynchronizePoint> usedPoints;
    private final ClassicSynchronizeHeap heap;

}
