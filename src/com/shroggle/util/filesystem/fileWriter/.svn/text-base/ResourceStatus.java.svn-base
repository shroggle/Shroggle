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
package com.shroggle.util.filesystem.fileWriter;

import com.shroggle.entity.ResourceType;
import com.shroggle.exception.ResourceStatusUndefinedException;

import java.util.concurrent.Future;

/**
 * @author Balakirev Anatoliy
 */
public class ResourceStatus {

    public ResourceStatus(int resourceId, ResourceType resourceType, Future<Status> future) {
        this.resourceId = resourceId;
        this.resourceType = resourceType;
        this.future = future;
    }

    public Status getStatus() {
        if (future.isDone()) {
            try {
                return future.get();
            } catch (Exception e) {
                throw new ResourceStatusUndefinedException("Can`t get resource status!");
            }
        } else {
            return Status.SAVING;
        }
    }

    public int getResourceId() {
        return resourceId;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    private final int resourceId;

    private final ResourceType resourceType;

    private final Future<Status> future;   

}
