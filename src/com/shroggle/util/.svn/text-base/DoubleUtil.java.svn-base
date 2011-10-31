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

import java.math.BigDecimal;
import java.util.Formatter;

/**
 * @author dmitry.solomadin
 */
public class DoubleUtil {

    public static double safeParse(final String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException ex) {
            return 0.0;
        }
    }

    public static boolean isParsable(final String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static Double getDoubleOrNull(final String s) {
        try {
            return Double.valueOf(s);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public static double round(final double d, final int precision) {
        BigDecimal bd = new BigDecimal(d);
        bd = bd.setScale(precision, BigDecimal.ROUND_HALF_EVEN);
        return bd.doubleValue();
    }

    public static String roundWithPrecision(final double d, final int precision) {
        BigDecimal bd = new BigDecimal(d);
        bd = bd.setScale(precision, BigDecimal.ROUND_HALF_EVEN);
        return new Formatter().format("%0$." + precision + "f", bd.doubleValue()).toString();
    }

    public static String withPrecision(final double d, final int precision) {
        return new Formatter().format("%0$." + precision + "f", d).toString();
    }


}
