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
package com.shroggle.presentation.resource;

import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.annotations.RemoteMethod;
import com.shroggle.entity.ResourceType;
import com.shroggle.util.filesystem.fileWriter.Status;
import com.shroggle.util.filesystem.fileWriter.ResourceStatus;
import com.shroggle.util.ServiceLocator;

import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class CheckResourceStatusService {

    @RemoteMethod
    public Status execute(final Integer resourceId, final ResourceType resourceType) {
        final ResourceStatus resourceStatus = ServiceLocator.getFilesWriter().getResourceStatus(resourceId, resourceType);
        final Status status;
        if (resourceStatus == null) {
            logger.info("Info about resource (resourceId = " + resourceId + ") not found.");
            status = Status.NOT_SAVED;
        } else {
            status = resourceStatus.getStatus();
        }
        if (status == Status.SAVED) {
            logger.info("Resource (resourceId = " + resourceId + ") saved successfuly.");
        } else if (status == Status.SAVING) {
            logger.info("Resource (resourceId = " + resourceId + ") is saving...");
        } else if (status == Status.NOT_SAVED) {
            logger.severe("Unable to save resource (resourceId = " + resourceId +
                    ") because of: \n" + status.getMessage());
        }
        return status;
    }

    private Logger logger = Logger.getLogger(this.getClass().getName());
}
