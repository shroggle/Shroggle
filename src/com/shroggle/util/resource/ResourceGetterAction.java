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

package com.shroggle.util.resource;

import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.process.synchronize.*;
import com.shroggle.util.resource.provider.ResourceGetterType;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

/**
 * ResourceProvider produce resource.
 *
 * @author Stasuk Artem
 * @see com.shroggle.util.resource.ResourceProducer
 * @see com.shroggle.util.resource.resize.ResourceResizer
 * @see ResourceGetterUrl
 */
@UrlBinding("/resourceGetter.action")
public class ResourceGetterAction extends Action {

    public Resolution execute() throws Exception {
        if (resourceGetterType == null) {
            return resolutionCreator.notFound();
        }

        final SynchronizeRequest synchronizeRequest = new SynchronizeRequestEntity(
                resourceGetterType.getResourceClass(), SynchronizeMethod.READ, resourceId);
        return synchronize.execute(synchronizeRequest, new SynchronizeContext<Resolution>() {

            public Resolution execute() throws Exception {
                final ResourceRequest request = new ResourceRequest(
                        resourceId, resourceSizeId, resourceSizeAdditionId, 0, 0);

                final ResourceResponse response = resourceGetterType.getGetter().execute(request);

                if (response.getData() != null) {
                    if (resourceDownload) {
                        return resolutionCreator.resourceDownload(
                                response.getData(), response.getResource().getName());
                    }
                    return resolutionCreator.resourceGetter(
                            response.getResource().getExtension(), response.getData(),
                            StringUtil.getEmptyOrString(response.getResource().getName()));
                }

                return resolutionCreator.notFound();
            }

        });
    }

    public void setResourceGetterType(final ResourceGetterType resourceGetterType) {
        this.resourceGetterType = resourceGetterType;
    }

    public void setResourceId(final int resourceId) {
        this.resourceId = resourceId;
    }

    public void setResourceDownload(final boolean resourceDownload) {
        this.resourceDownload = resourceDownload;
    }

    public void setResourceSizeId(final int resourceSizeId) {
        this.resourceSizeId = resourceSizeId;
    }

    public void setResourceSizeAdditionId(final int resourceSizeAdditionId) {
        this.resourceSizeAdditionId = resourceSizeAdditionId;
    }

    private int resourceId;
    private int resourceSizeId;
    private int resourceSizeAdditionId;
    private boolean resourceDownload;
    private ResourceGetterType resourceGetterType;
    private final Synchronize synchronize = ServiceLocator.getSynchronize();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();

}
