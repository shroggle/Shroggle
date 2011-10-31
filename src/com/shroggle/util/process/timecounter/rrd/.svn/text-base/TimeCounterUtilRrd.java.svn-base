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
package com.shroggle.util.process.timecounter.rrd;

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.process.timecounter.TimeCounterResult;

import java.io.File;

/**
 * @author Artem Stasuk
 */
public class TimeCounterUtilRrd {

    private TimeCounterUtilRrd() {

    }

    public static String getPath(final TimeCounterResult result) {
        return getPath(result.getName());
    }

    public static String getPath(final int id) {
        return ServiceLocator.getConfigStorage().get().getRrdPath() + id + ".rrd";
    }

    public static String getPath(final String name) {
        return getPath(name.hashCode());
    }

    public static String getRrdName(final String name) {
        return StringUtil.cutIfNeed(name, 20, "...");
    }

    public static boolean exist(final TimeCounterResult result) {
        return !notExist(result.getName());
    }

    public static boolean exist(final String name) {
        return !notExist(name);
    }

    public static boolean notExist(final String name) {
        return !new File(getPath(name)).exists();
    }

}
