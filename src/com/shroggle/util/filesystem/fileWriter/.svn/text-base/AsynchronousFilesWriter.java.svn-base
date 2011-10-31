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

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.entity.Resource;
import com.shroggle.entity.ResourceType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.List;
import java.util.ArrayList;
import java.io.InputStream;
import java.security.PrivilegedAction;

/**
 * @author Balakirev Anatoliy
 */
public class AsynchronousFilesWriter {

    public AsynchronousFilesWriter() {
        threadPool = Executors.newFixedThreadPool(ServiceLocator.getConfigStorage().get().getConcurrentWriteFileThreadCount());
        fileSystem = ServiceLocator.getFileSystem();
        resourceStatusList = new ArrayList<ResourceStatus>();
    }

    public void setResourceStream(final Resource resource, final InputStream data) {
        setResourceStream(resource, data, null);
    }

    public void setResourceStream(final Resource resource, final InputStream data, final Runnable afterSave) {
        final Callable callable = Executors.callable(new PrivilegedAction<Status>() {
            public Status run() {
                final ExecutorService executor = Executors.newSingleThreadExecutor();
                try {
                    fileSystem.setResourceStream(resource, data);
                    if (afterSave != null) {
                        final Future future = executor.submit(afterSave);
                        future.get();
                    }
                } catch (Exception e) {
                    final Status status = Status.NOT_SAVED;
                    status.setMessage(e.getMessage());
                    return status;
                } finally {
                    executor.shutdown();
                }
                return Status.SAVED;
            }
        });
        final Future<Status> future = threadPool.<Status>submit(callable);

        synchronized (resourceStatusList) {
            resourceStatusList.add(new ResourceStatus(resource.getResourceId(), resource.getResourceType(), future));
        }
    }

    public ResourceStatus getResourceStatus(final Integer resourceId, final ResourceType resourceType) {
        if (resourceId == null || resourceType == null || resourceStatusList.isEmpty()) {
            return null;
        }
        for (ResourceStatus resourceStatus : resourceStatusList) {
            if (resourceStatus.getResourceId() == resourceId && resourceStatus.getResourceType() == resourceType) {
                if (resourceStatus.getStatus() == Status.SAVED || resourceStatus.getStatus() == Status.NOT_SAVED) {
                    synchronized (resourceStatusList) {
                        resourceStatusList.remove(resourceStatus);
                    }
                }
                return resourceStatus;
            }
        }
        return null;
    }

    public void destroy() {
        threadPool.shutdown();
    }

    private final ExecutorService threadPool;
    private final FileSystem fileSystem;
    private final List<ResourceStatus> resourceStatusList;
}
