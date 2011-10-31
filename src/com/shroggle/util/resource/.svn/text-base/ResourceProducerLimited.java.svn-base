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
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;

import java.io.InputStream;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * @author Artem Stasuk
 */
public class ResourceProducerLimited implements ResourceProducer {

    public ResourceProducerLimited(final ResourceProducer producer) {
        this.producer = producer;
    }

    @Override
    public InputStream execute(final Resource resource) {
        final Config config = ServiceLocator.getConfigStorage().get();
        final int concurrentThreadMax = config.getConcurrentResizerThreadCount();

        synchronized (this) {
            while (concurrentThreadCount >= concurrentThreadMax) {
                try {
                    log.log(Level.INFO, "Producer is busy, it's has "
                            + concurrentThreadCount + " thread, start wait.");
                    this.wait();
                } catch (final InterruptedException exception) {
                    Thread.currentThread().interrupt();
                }
            }
            concurrentThreadCount++;
        }
        try {
            return producer.execute(resource);
        } finally {
            synchronized (this) {
                concurrentThreadCount--;
                this.notify();
            }
        }
    }

    private final ResourceProducer producer;
    private int concurrentThreadCount = 0;
    private final Logger log = Logger.getLogger(ResourceProducerLimited.class.getSimpleName());

}
