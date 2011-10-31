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
package com.shroggle.stresstest.application;

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.html.HttpUtil;
import com.shroggle.util.process.timecounter.TimeCounter;
import com.shroggle.util.process.timecounter.TimeCounterCreator;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Stasuk Artem
 */
class CommandHttpGet implements Command {

    public CommandHttpGet(final String url, final Context context) {
        this.context = context;
        this.url = url;
        this.headers = Collections.emptyMap();
    }

    public CommandHttpGet(
            final String url, final Context context,
            final Map<String, String> headers) {
        this.context = context;
        this.headers = headers;
        this.url = url;
    }

    @Override
    public void execute() throws Exception {
        final TimeCounterCreator timeCounterCreator = ServiceLocator.getTimeCounterCreator();

        final TimeCounter timeCounter = timeCounterCreator.create("total");

        final TimeCounter connectTimeCounter = timeCounterCreator.create("connect");
        final HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
        } catch (final IOException exception) {
            timeCounterCreator.create("skipped").stop();
            logger.info(url + " skipped by " + exception.getMessage());
            return;
        } finally {
            connectTimeCounter.stop();
        }

        connection.setConnectTimeout(20000);

        final TimeCounter requestTimeCounter = timeCounterCreator.create("request");
        try {
            for (final String header : headers.keySet()) {
                connection.addRequestProperty(header, headers.get(header));
            }
            context.restore(connection);
        } finally {
            requestTimeCounter.stop();
        }

        final TimeCounter responseTimeCounter = timeCounterCreator.create("response");
        try {
            final int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new IOException("Incorrect response code: " + responseCode);
            }

            context.store(connection);
            response = HttpUtil.read(connection);
            final int total = response.length();
            logger.info(url + " success get, size " + (total / 1024) + " kb");
        } catch (final IOException exception) {
            timeCounterCreator.create("skipped").stop();
            logger.info(url + " skipped by " + exception.getMessage());
        } finally {
            connection.disconnect();
            responseTimeCounter.stop();
            timeCounter.stop();
        }

    }

    public String getResponse() {
        return response;
    }

    private final String url;
    private String response;
    private final Context context;
    private final Map<String, String> headers;
    private final static Logger logger = Logger.getLogger("CommandHttpGet");

}
