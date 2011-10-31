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

import com.shroggle.entity.Resource;
import com.shroggle.entity.ResourceCustom;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.resource.resize.ResourceResizeException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Artem Stasuk
 */
public class ResourceProducerSimple implements ResourceProducer {

    @Override
    public InputStream execute(final Resource resource) {
        final Resource resourceWithoutSize = ResourceCustom.copyWithoutSize(resource);
        final InputStream parentData = ServiceLocator.getFileSystem().getResourceStream(resourceWithoutSize);
        final ByteArrayOutputStream output = new ByteArrayOutputStream(1024);

        try {
            ServiceLocator.getResourceResizer().execute(
                    parentData, output, resource.getExtension(), resource.getResourceSize());
        } catch (final ResourceResizeException exception) {
            throw new ResourceResizeException("Can't resize resource with id: " + resource.getResourceId()
                    + ", name: " + resource.getName() + ", site id: " + resource.getSiteId(), exception);
        }

        try {
            parentData.close();
            output.flush();
            output.close();
        } catch (final Exception exception) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Can`t close resource stream.");
        }

        return new ByteArrayInputStream(output.toByteArray());
    }

}
