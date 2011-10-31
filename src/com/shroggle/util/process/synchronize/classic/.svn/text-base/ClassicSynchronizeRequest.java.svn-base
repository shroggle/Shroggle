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
import com.shroggle.util.process.synchronize.SynchronizeRequest;

import java.util.Set;

/**
 * @author Stasuk Artem
 */
class ClassicSynchronizeRequest {

    public ClassicSynchronizeRequest(
            final SynchronizeRequest request, final ClassicSynchronizeHeap heap) {
        if (request == null) {
            throw new NullPointerException("Can't create classic request by null request!");
        }
        if (heap == null) {
            throw new NullPointerException("Can't create classic request by null heap!");
        }
        this.requiredPoints = request.getPoints();
        this.heap = heap;
    }

    public ClassicSynchronizeTicker getTicker() {
        synchronized (heap) {
//            log.info("Start get ticker for thread " + Thread.currentThread());
            // map of required for this synchronize request points
            // go by all required points and check is allow
            for (final SynchronizePoint requirePoint : requiredPoints) {
                boolean isNotAllow;
                do {
                    isNotAllow = false;
                    final ClassicSynchronizePoint usedPoint = heap.used.get(requirePoint);
                    if (usedPoint != null && !usedPoint.isAllow(requirePoint.getMethod())) {
                        try {
                            isNotAllow = true;
                            heap.wait();
                        } catch (InterruptedException exception) {
                            Thread.currentThread().interrupt();
                        }
                    }
                } while (isNotAllow);
            }
            // create and start ticker
            return new ClassicSynchronizeTicker(heap, requiredPoints);
        }
    }

    private final ClassicSynchronizeHeap heap;
    private final Set<SynchronizePoint> requiredPoints;

}