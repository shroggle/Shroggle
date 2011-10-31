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

import java.net.HttpURLConnection;

/**
 * @author Stasuk Artem
 */
final class Context {

    public void store(final HttpURLConnection connection) {
        final String newCookies = connection.getHeaderField("Set-Cookie");
        if (newCookies != null) {
            cookies = newCookies;
        }
    }

    public String getJsessionId() {
        if (cookies != null) {
            final String jsessionIdPattern = "JSESSIONID=";
            final int jsessionIdIndex = cookies.indexOf(jsessionIdPattern);
            if (jsessionIdIndex > -1) {
                int jsessionIdEndIndex = cookies.indexOf(";", jsessionIdIndex + 1);
                if (jsessionIdEndIndex < 0) {
                    jsessionIdEndIndex = cookies.length();
                }

                return cookies.substring(jsessionIdIndex + jsessionIdPattern.length(), jsessionIdEndIndex);
            }
        }
        return null;
    }

    public void restore(final HttpURLConnection connection) {
        connection.addRequestProperty("User-Agent", "\tMozilla/5.0 (Windows; U; Windows NT 6.1; ru; rv:1.9.1.3) Gecko/20090824 Firefox/3.5.3");
        if (cookies != null) {
            connection.addRequestProperty("Cookie", cookies);
        }
    }

    private String cookies;

}
