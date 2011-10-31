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
package com.shroggle.logic.site;

import com.shroggle.util.url.UrlValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * @author Balakirev Anatoliy
 */
public class NotUsersSiteUrlsNormalizer {

    public NotUsersSiteUrlsNormalizer(HttpServletRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public String getNormalizedUrl() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(httpRequest.getScheme());
        String lowerServerName = httpRequest.getServerName().toLowerCase();
        if (lowerServerName.startsWith("www") || UrlValidator.isIpAddress(lowerServerName)) {
            stringBuilder.append("://");
        } else {
            stringBuilder.append("://www.");
        }
        stringBuilder.append(httpRequest.getServerName());
        if (httpRequest.getServerPort() != 80) {
            stringBuilder.append(":");
            stringBuilder.append(httpRequest.getServerPort());
        }
        stringBuilder.append(httpRequest.getRequestURI());

        final String querry = httpRequest.getQueryString();
        if (querry != null && !querry.isEmpty()) {
            stringBuilder.append("?");
            stringBuilder.append(querry);
        }
        return stringBuilder.toString();
    }

    private final HttpServletRequest httpRequest;
}
