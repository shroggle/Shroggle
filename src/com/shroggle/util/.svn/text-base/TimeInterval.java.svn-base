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

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.EnumConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Artem Stasuk
 */
@DataTransferObject(converter = EnumConverter.class)
public enum TimeInterval {

    ONE_DAY(24 * 60 * 60 * 1000L),
    TWO_DAYS(2 * 24 * 60 * 60 * 1000L),
    THREE_DAYS(3 * 24 * 60 * 60 * 1000L),
    FIVE_DAYS(5 * 24 * 60 * 60 * 1000L),
    ONE_WEEK(7 * 24 * 60 * 60 * 1000L),
    TEN_DAYS(10 * 24 * 60 * 60 * 1000L),
    TWO_WEEKS(2 * 7 * 24 * 60 * 60 * 1000L),
    TWENTY_DAYS(20 * 24 * 60 * 60 * 1000L),
    THREE_WEEKS(3 * 7 * 24 * 60 * 60 * 1000L),
    FOUR_WEEKS(4 * 7 * 24 * 60 * 60 * 1000L),
    ONE_MONTH(30 * 24 * 60 * 60 * 1000L),
    TWO_MONTHS(2 * 30 * 24 * 60 * 60 * 1000L),
    THREE_MONTHS(3 * 30 * 24 * 60 * 60 * 1000L),
    SIX_MONTHS(6 * 30 * 24 * 60 * 60 * 1000L),
    ONE_YEAR(365 * 24 * 60 * 60 * 1000L),

    SEVEN_DAYS(7 * 24 * 60 * 60 * 1000L) {
        public boolean isForGroups() {
            return false;
        }
    },
    ONE_HUNDRED_YEARS(100L * 365L * 24L * 60L * 60L * 1000L) {
        public boolean isForGroups() {
            return false;
        }
    },
    FIVE_HOURS(5L * 60L * 60L * 1000L) {
        public boolean isForGroups() {
            return false;
        }
    };

    TimeInterval(final long millis) {
        this.millis = millis;
    }

    public long getMillis() {
        return millis;
    }

    public boolean isForGroups() {
        return true;
    }

    public static TimeInterval[] getLimitedTimeForGroups() {
        List<TimeInterval> limitedTime = new ArrayList<TimeInterval>();
        for (TimeInterval limitedTimeValue : TimeInterval.values()) {
            if (limitedTimeValue.isForGroups()) {
                limitedTime.add(limitedTimeValue);
            }
        }
        return limitedTime.toArray(new TimeInterval[limitedTime.size()]);
    }

    private final long millis;
}
