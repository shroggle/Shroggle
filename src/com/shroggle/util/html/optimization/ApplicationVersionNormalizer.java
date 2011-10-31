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
package com.shroggle.util.html.optimization;

import com.shroggle.util.ServiceLocator;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * @author Balakirev Anatoliy
 */
public class ApplicationVersionNormalizer {

    public static String getNormalizedApplicationVersion() {
        final String appVersion = ServiceLocator.getFileSystem().getApplicationVersion();
        Pattern pattern = Pattern.compile("v(\\d\\.\\d*)");
        if (appVersion != null && !appVersion.isEmpty()) {
            Matcher matcher = pattern.matcher(appVersion);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return "notSpecified";
    }
}
