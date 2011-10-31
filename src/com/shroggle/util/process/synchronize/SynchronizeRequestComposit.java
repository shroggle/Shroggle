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
package com.shroggle.util.process.synchronize;

import java.util.*;

/**
 * @author Artem Stasuk
 */
public final class SynchronizeRequestComposit implements SynchronizeRequest {

    public SynchronizeRequestComposit(final SynchronizeRequest... synchronizeRequests) {
        if (synchronizeRequests == null || synchronizeRequests.length == 0) {
            throw new UnsupportedOperationException(
                    "Can't create composit request with null requests!");
        }
        for (final SynchronizeRequest synchronizeRequest : synchronizeRequests) {
            if (synchronizeRequest == null) {
                throw new UnsupportedOperationException(
                        "Can't create composit request with null requests item!");
            }
        }
        this.synchronizeRequests = Collections.unmodifiableList(Arrays.asList(synchronizeRequests));
    }

    public Set<SynchronizePoint> getPoints() {
        final Set<SynchronizePoint> synchronizePoints = new HashSet<SynchronizePoint>();
        for (final SynchronizeRequest synchronizeRequest : synchronizeRequests) {
            synchronizePoints.addAll(synchronizeRequest.getPoints());
        }
        return synchronizePoints;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SynchronizeRequestComposit.class.getName());
        stringBuilder.append(" [");
        for (final SynchronizeRequest synchronizeRequest : synchronizeRequests) {
            stringBuilder.append(synchronizeRequest.toString());
            stringBuilder.append(", ");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private final List<SynchronizeRequest> synchronizeRequests;

}
