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
package com.shroggle.util.resource.resize;

import com.shroggle.entity.ResourceSize;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.process.timecounter.TimeCounter;
import com.shroggle.util.process.timecounter.TimeCounterCreator;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Artem Stasuk
 */
public class ResourceResizerTimeCounter implements ResourceResizer {

    public ResourceResizerTimeCounter(final ResourceResizer resourceResizer) {
        this.resourceResizer = resourceResizer;
    }

    @Override
    public void execute(
            final InputStream source, final OutputStream destination,
            final String extension, final ResourceSize destinationSize) {
        final TimeCounter timeCounter = timeCounterCreator.create("ResourceResizer: " + resourceResizer.getClass().getSimpleName());
        try {
            resourceResizer.execute(source, destination, extension, destinationSize);
        } finally {
            timeCounter.stop();
        }
    }

    private final ResourceResizer resourceResizer;
    private final TimeCounterCreator timeCounterCreator = ServiceLocator.getTimeCounterCreator();

}
