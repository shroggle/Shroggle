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

import com.shroggle.exception.AuthorizeNetException;

import java.net.URLEncoder;
import java.util.Map;

/**
 * @author Balakirev Anatoliy
 */
public class PostParametersConverter {

    public static String convert(final Map<String, String> parameters) {
        if (parameters == null || parameters.isEmpty()) {
            return "";
        }
        try {
            final StringBuilder post_string = new StringBuilder();
            for (final String key : parameters.keySet()) {
                String encodedKey = URLEncoder.encode(key, "UTF-8");
                String value = URLEncoder.encode(parameters.get(key), "UTF-8");
                post_string.append(encodedKey);
                post_string.append("=");
                post_string.append(value);
                post_string.append("&");
            }
            return post_string.length() == 0 ? "" : post_string.deleteCharAt(post_string.length() - 1).toString();
        } catch (Exception e) {
            throw new AuthorizeNetException("Unable to convert post values to string.", e);
        }
    }

}
