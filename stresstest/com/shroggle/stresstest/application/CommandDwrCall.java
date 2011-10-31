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
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

/**
 * @author Stasuk Artem
 */
class CommandDwrCall implements Command {

    public CommandDwrCall(
            final String url, final String service,
            final String method, final Context context,
            final String... parameters) {
        this.url = url;
        this.parameters = parameters;
        this.service = service;
        this.method = method;
        this.context = context;
    }

    @Override
    public void execute() throws Exception {
        final TimeCounterCreator timeCounterCreator = ServiceLocator.getTimeCounterCreator();

        final TimeCounter timeCounter = timeCounterCreator.create("total");

        final TimeCounter connectTimeCounter = timeCounterCreator.create("connect");
        final HttpURLConnection connection = (HttpURLConnection) new URL(
                url + "/dwr/call/plaincall/" + service + "." + method + ".dwr").openConnection();
        connectTimeCounter.stop();

        final TimeCounter requestTimeCounter = timeCounterCreator.create("request");
        connection.setConnectTimeout(20000);
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.addRequestProperty("Content-Type", "text/plain; charset=UTF-8");
        connection.setUseCaches(false);
        context.restore(connection);

        try {
            PrintWriter request = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), "utf8"));
            request.print("\ncallCount=1");
            request.print("\nc0-id=0");
            request.print("\nc0-scriptName=" + service);
            request.print("\nc0-methodName=" + method);

            for (final String parameter : parameters) {
                request.print("\n" + parameter);
            }

            request.print("\nhttpSessionId=" + context.getJsessionId());
            request.print("\nscriptSessionId=068B907669491C800EC0D28F3174D14E741");
            request.print("\nbatchId=0");
            request.close();
            requestTimeCounter.stop();

            final TimeCounter responseTimeCounter = timeCounterCreator.create("response");
            if (connection.getResponseCode() != 200) {
                logger.info(url + " error dwr call, code " + connection.getResponseCode());
                throw new IOException(url + " incorrect reponse code " + connection.getResponseCode());
            }

            final String content = HttpUtil.read(connection);
            responseTimeCounter.stop();

            if (content.contains("_remoteHandleBatchException")) {
                logger.info(url + " error dwr call " + content);
                throw new IOException(url + " incorrect reponse " + content);
            }

            logger.info(url + " success dwr call, size " + content.length() + " b");
        } finally {
            connection.disconnect();
        }

        timeCounter.stop();
    }

    private final String url;
    private final String service;
    private final String method;
    private final Context context;
    private final String[] parameters;
    private final static Logger logger = Logger.getLogger("CommandDwrCall");

}
