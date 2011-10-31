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

import java.util.HashSet;
import java.util.Set;

/**
 * Author: Artem Stasuk (artem).
 * </p>
 * Date: 17.10.2008
 */
public class SynchronizeRequestAllEntity implements SynchronizeRequest {

    public SynchronizeRequestAllEntity(final Class entityClass) {
        if (entityClass == null) {
            throw new UnsupportedOperationException("Can't create request by all entity with null class!");
        }
        this.entityClass = entityClass;
    }

    public Set<SynchronizePoint> getPoints() {
        final Set<SynchronizePoint> points = new HashSet<SynchronizePoint>();
        points.add(new SynchronizePointAllEntity(entityClass));
        return points;
    }

    private final Class entityClass;

}
