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

import com.shroggle.entity.Site;
import com.shroggle.entity.User;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Stasuk Artem
 */
public class SynchronizeRequestEntity implements SynchronizeRequest {

    public SynchronizeRequestEntity(
            final Class entityClass, final SynchronizeMethod method,
            final Object entityId, final int parentDeep) {
        if (entityClass == null) {
            throw new NullPointerException(
                    "Can't create synchronize request by null region!");
        }
        if (method == null) {
            throw new NullPointerException(
                    "Can't create synchronize request by null method! " +
                            "Use SynchronizeMethod enum for this parameter!");
        }
        if (parentDeep < 0) {
            throw new UnsupportedOperationException(
                    "Can't create synchronize request by negative deep parent!");
        }
        this.entityClass = entityClass;
        this.parentDeep = parentDeep;
        this.method = method;
        this.entityId = entityId;
    }

    public SynchronizeRequestEntity(
            final Class entityClass, final SynchronizeMethod method,
            final Object entityId) {
        this(entityClass, method, entityId, 0);
    }

    public SynchronizeMethod getMethod() {
        return method;
    }

    public Class getEntityClass() {
        return entityClass;
    }

    public Object getEntityId() {
        return entityId;
    }

    public Set<SynchronizePoint> getPoints() {
        final Set<SynchronizePoint> points = new HashSet<SynchronizePoint>();
        if (entityId != null) {
            int tempParentDeep = parentDeep;
            SynchronizeEntityParentResult result = new SynchronizeEntityParentResult(entityClass, entityId);
            while (result != null) {
                if (tempParentDeep <= 0) {
                    // if this not first entity always need only read
                    SynchronizeMethod tempMethod = SynchronizeMethod.READ;
                    // if this first entity in synchronize list it use real method access
                    if (tempParentDeep == 0) {
                        tempMethod = method;
                    }
                    points.add(new SynchronizePointEntity(result.getEntityClass(), result.getEntityId(), tempMethod));
                    if (result.getEntityClass() == Site.class && tempMethod == SynchronizeMethod.WRITE) {
                        points.add(new SynchronizePointAllEntity(Site.class));
                    } else if (result.getEntityClass() == User.class && tempMethod == SynchronizeMethod.WRITE) {
                        points.add(new SynchronizePointAllEntity(User.class));
                    } else if (result.getEntityClass() == User.class && tempMethod == SynchronizeMethod.WRITE) {
                        points.add(new SynchronizePointAllEntity(User.class));
                    }
                }
                tempParentDeep--;
                result = SynchronizeEntityParent.next(result);
            }
        }
        return points;
    }

    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SynchronizeRequestEntity.class.getSimpleName());
        stringBuilder.append(" {class: ");
        stringBuilder.append(entityClass);
        stringBuilder.append(", method: ");
        stringBuilder.append(method);
        stringBuilder.append(", id: ");
        stringBuilder.append(entityId);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private final int parentDeep;
    private final SynchronizeMethod method;
    private final Class entityClass;
    private final Object entityId;

}
