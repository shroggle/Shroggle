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
package com.shroggle.logic.stripes;

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.html.stripes.CustomMultipartWrapperFactory;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
public class MaxPostSizeCreator {

    public static long createMaxPostSizeInBytes() {
        String maxPostSize = ServiceLocator.getConfigStorage().get().getMaxPostSize();
        if (maxPostSize != null) {
            Pattern pattern = Pattern.compile("^(\\d+)(?:[.,][\\d]+)?([kmg]?)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(maxPostSize);
            if (!matcher.find()) {
                logger.log(Level.SEVERE, "Did not understand value of configuration parameter " + maxPostSize,
                        " You supplied: " + maxPostSize + ". Valid values are any string of numbers " +
                                "optionally followed by (case insensitive) [k|kb|m|mb|g|gb]. " +
                                "Default value of " + DEFAULT_MAX_POST_SIZE + " bytes will be used instead.");
            } else {
                String digits = matcher.group(1);
                String suffix = matcher.group(2).toLowerCase();
                long number = Long.parseLong(digits);

                if ("k".equalsIgnoreCase(suffix)) {
                    number = number * 1024;
                } else if ("m".equalsIgnoreCase(suffix)) {
                    number = number * 1024 * 1024;
                } else if ("g".equalsIgnoreCase(suffix)) {
                    number = number * 1024 * 1024 * 1024;
                }
                return number;
            }
        }
        return DEFAULT_MAX_POST_SIZE;
    }

    public static String createMaxPostSizeString() {
        return createMaxPostSizeInBytes() + " B";
    }

    public static long getDefaultMaxPostSize() {
        return DEFAULT_MAX_POST_SIZE;
    }

    private static final long DEFAULT_MAX_POST_SIZE = 10 * 1024 * 1024; // Defaults to 10MB
    private static final Logger logger = Logger.getLogger(CustomMultipartWrapperFactory.class.getName());
}
