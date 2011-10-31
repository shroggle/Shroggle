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
package com.shroggle.stresstest.util.pageresource;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Artem Stasuk
 */
public class PageResourcesThreads {

    public PageResourcesThreads(final String url) {
        this.url = url;
        threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Runnable() {

                @Override
                public void run() {
                    new PageResourcesCaller(url).execute(count, new AtomicInteger());
                }

            });
        }
    }

    public void execute() throws InterruptedException {
        for (final Thread thread : threads) {
            thread.start();
        }

        for (final Thread thread : threads) {
            thread.join();
        }

        System.out.println(count.get() + " = " + url);
    }

    private final Thread[] threads;
    private final String url;
    private final AtomicInteger count = new AtomicInteger();

}
