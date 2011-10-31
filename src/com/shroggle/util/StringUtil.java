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
package com.shroggle.util;

import java.util.Arrays;
import java.util.List;

/**
 * @author Artem Stasuk
 */
public class StringUtil {

    public static int getInt(final String string, final int defaultValue) {
        if (string != null) {
            try {
                return Integer.parseInt(string);
            } catch (final NumberFormatException exception) {
                // none
            }
        }
        return defaultValue;
    }

    public static boolean isNullOrEmpty(final String string) {
        return string == null || string.trim().isEmpty();
    }

    public static String getEmptyOrString(final Object string) {
        return string == null ? "" : string.toString();
    }

    public static String getNotEmptyOrNull(final String string) {
        return isNullOrEmpty(string) ? null : string;
    }

    public static void addMany(final List<String> strings, final String... addStrings) {
        strings.addAll(Arrays.asList(addStrings));
    }

    public static boolean equalsString(final String string1, final String string2) {
        return getEmptyOrString(string1).equals(string2);
    }

    //----Use these methods for db persisting. For html output use HtmlUtil.limitName or util.js limitName function.----    

    public static String cutIfNeed(final String string, final int limit) {
        if (string != null && string.length() > limit) {
            return string.substring(0, limit);
        }
        return string;
    }

    public static String cutIfNeed(final String string, final int limit, final String suffix) {
        if (string != null && string.length() > limit) {
            return string.substring(0, limit - suffix.length()) + suffix;
        }
        return string;
    }

    public static String trimCutIfNeedAndLower(String string, final int limit) {
        string = trimCutIfNeed(string, limit);
        if (string != null) {
            string = string.toLowerCase();
        }
        return string;
    }

    public static String trimCutIfNeed(String string, final int limit) {
        if (string != null) {
            string = string.trim();
            if (string.isEmpty()) {
                string = null;
            } else {
                string = cutIfNeed(string, limit);
            }
        }
        return string;
    }

    public static String find(final String string, final String startPattern, final String endPattern) {
        final int start = string.indexOf(startPattern);
        if (start > -1) {
            final int end = string.indexOf(endPattern, start + startPattern.length());
            if (end > -1) {
                return string.substring(start + startPattern.length(), end);
            }
        }
        return null;
    }

    public static String toLowerCase(final String string) {
        return string == null ? null : string.toLowerCase();
    }

}
