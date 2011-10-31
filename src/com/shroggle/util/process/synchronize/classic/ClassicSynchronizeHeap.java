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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Stasuk Artem
 */
class ClassicSynchronizeHeap {

    /**
     * @param requiredPoints  is's a sorted set of required keys.
     * @param requiredMethod it's method that needs synchronization
     * @return the object is everything is fine, otherwise it returns false.
     * возвращает объект запрос на вход в синхронизацию в противном случаи ложь
     */
//    public ClassicSynchronizeTicker getTicker(
//            final Set<SynchronizePointEntity> requiredPoints, SynchronizeMethod requiredMethod) {
//        final Map<SynchronizePointEntity, ClassicSynchronizePoint> tickerPoints =
//                new HashMap<SynchronizePointEntity, ClassicSynchronizePoint>();
//        boolean firstRequiredKey = true;
//        for (final SynchronizePointEntity requiredPoint : requiredPoints) {
//            if (firstRequiredKey) {
//                firstRequiredKey = false;
//            } else {
//                requiredMethod = SynchronizeMethod.READ;
//            }
//            final ClassicSynchronizePoint usedPoint = used.get(requiredPoint);
//            if (usedPoint != null) {
//                if (usedPoint.getTicker(requiredMethod)) {
//                    tickerPoints.put(requiredPoint, usedPoint);
//                } else {
//                    return null;
//                }
//            } else {
//                tickerPoints.put(requiredPoint, null);
//            }
//        }
//        return new ClassicSynchronizeTicker(requiredMethod, tickerPoints);
//    }

//    public void start(final ClassicSynchronizeTicker ticker) {
//        for (SynchronizePoint point : ticker.getUsedPoints().keySet()) {
//            ClassicSynchronizePoint point = ticker.getUsedPoints().get(key);
//            if (point == null) {
//                point = new ClassicSynchronizePoint();
//                ticker.getUsedPoints().put(key, point);
//                used.put(key, point);
//            }
//            point.start(ticker.getMethod());
//        }
//    }

//    public void finish(final ClassicSynchronizeTicker ticker) {
//        final Iterator<SynchronizePointEntity> keyIterator = ticker.getUsedPoints().keySet().iterator();
//        while (keyIterator.hasNext()) {
//            final SynchronizePoint point = keyIterator.next();
//            final ClassicSynchronizePoint point = ticker.getUsedPoints().get(key);
//            point.finish(ticker.getMethod());
//            if (point.isEmpty()) {
//                keyIterator.remove();
//                used.remove(key);
//            }
//        }
//    }

    final Map<SynchronizePoint, ClassicSynchronizePoint> used =
            new HashMap<SynchronizePoint, ClassicSynchronizePoint>();

}
