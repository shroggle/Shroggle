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
package com.shroggle.util.http;

import com.shroggle.exception.HttpConnectionException;
import com.shroggle.util.StringUtil;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
public class HttpConnection {

    public HttpConnection(final String host, final Map<String, String> parameters) throws HttpConnectionException {
        this(host, parameters, null);
    }

    public HttpConnection(final String host, final String body) throws HttpConnectionException {
        this(host, null, body);
    }

    public HttpConnection(final String host, final Map<String, String> parameters, final String body) throws HttpConnectionException {
        try {
            connection = new URL(host).openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            // Default Content-Type. This line is not necessarily required but fixes a bug with some servers.
            setContentType("application/x-www-form-urlencoded");
        } catch (Exception e) {
            throw new HttpConnectionException("Unable to open connection.", e);
        }
        this.body = (PostParametersConverter.convert(parameters) + StringUtil.getEmptyOrString(body));
    }

    public HttpResponse submitData() throws HttpConnectionException {
        submitDataInternal();
        return getResponse();
    }

    public HttpConnection setContentType(final String contentType) {
        connection.setRequestProperty("Content-Type", contentType);
        return this;
    }

    public HttpConnection addHeader(final String name, final String value) {
        connection.setRequestProperty(name, value);
        return this;
    }

    public HttpConnection setMethod(final HttpMethod method) {
        try {
            ((HttpURLConnection) connection).setRequestMethod(method.toString());
        } catch (Exception e) {
            logger.log(Level.WARNING, ("Unable to set request method to: " + method), e);
        }
        return this;
    }


    /*-------------------------------------------------Private methods------------------------------------------------*/

    private void submitDataInternal() throws HttpConnectionException {
        DataOutputStream outputStream = null;
        try {
            outputStream = new DataOutputStream(connection.getOutputStream());
            if (body != null) {
                outputStream.write(body.getBytes());
            }
        } catch (Exception e) {
            throw new HttpConnectionException("Unable to write to connection`s outputStream.", e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Unable to close output stream.", e);
                }
            }
        }
    }

    private HttpResponse getResponse() throws HttpConnectionException {
        final List<String> responseBody = new ArrayList<String>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                responseBody.add(line);
            }
            final HttpURLConnection httpURLConnection = ((HttpURLConnection) connection);
            return new HttpResponse(httpURLConnection.getResponseCode(), httpURLConnection.getResponseMessage(), responseBody);
        } catch (Exception e) {
            throw new HttpConnectionException("Unable to process response.", e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Unable to close input stream.", e);
                }
            }
        }
    }
    /*-------------------------------------------------Private methods------------------------------------------------*/

    private final String body;
    private final URLConnection connection;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public enum HttpMethod {
        GET, POST, HEAD, OPTIONS, PUT, DELETE, TRACE
    }

    public static class HttpResponse {

        public HttpResponse(int responseCode, String responseMessage, List<String> responseBody) {
            this.responseCode = responseCode;
            this.responseMessage = responseMessage;
            this.responseBody = responseBody;
        }

        private final int responseCode;

        private final String responseMessage;

        private final List<String> responseBody;

        public int getResponseCode() {
            return responseCode;
        }

        public String getResponseMessage() {
            return responseMessage;
        }

        public List<String> getResponseBody() {
            return responseBody;
        }

        public String getResponseBodyAsString() {
            final StringBuilder stringBuilder = new StringBuilder();
            for (String string : responseBody) {
                stringBuilder.append(string);
            }
            return stringBuilder.toString();
        }
    }
}