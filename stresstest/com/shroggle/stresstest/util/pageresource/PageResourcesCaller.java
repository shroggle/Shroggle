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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Artem Stasuk
 */
public class PageResourcesCaller {

    public static void main(String[] args) throws IOException, InterruptedException {
//        new PageResourcesThreads("http://localhost:8080/pageResourcesBlockGet.action?resourceId=54215945&applicationVersion=null").execute();
//        new PageResourcesThreads("http://localhost:8080/pageResourcesBlockGet.action?resourceId=54215945&applicationVersion=null").execute();
//        new PageResourcesThreads("http://localhost:8080/test.js").execute();
//        new PageResourcesThreads("http://localhost/test.js").execute();
        new PageResourcesThreads("http://localhost/resources/54215945.js?applicationVersion=null").execute();
        new PageResourcesThreads("http://localhost/resources/54215945.js?applicationVersion=null").execute();
    }

    PageResourcesCaller(String url) {
        this.url = url;
    }

    public void execute(final AtomicInteger count, final AtomicInteger result) {
        try {
            final long start = System.currentTimeMillis();
            // 3 minutes
            while (System.currentTimeMillis() - start < 1l * 60l * 1000l) {
                URLConnection urlConnection = new URL(url).openConnection();
                urlConnection.setDefaultUseCaches(false);
                urlConnection.setUseCaches(false);
                urlConnection.setDoOutput(false);
                urlConnection.connect();

                if (!"HTTP/1.1 200 OK".equals(urlConnection.getHeaderField(null))) {
                    throw new IOException("Incorrect return status " + urlConnection.getHeaderField(null));
                }

                InputStream inputStream = urlConnection.getInputStream();
                byte[] buffer = new byte[2048];
                while (inputStream.read(buffer) > 0) {
                    for (byte bufferItem : buffer) {
                        result.set(bufferItem);
                    }
                }
                inputStream.close();
                count.incrementAndGet();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final String url;

}
