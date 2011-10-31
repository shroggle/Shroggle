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
package com.shroggle.entity;

import com.shroggle.util.TimeInterval;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.EnumConverter;

/**
 * @author Balakirev Anatoliy
 */
@DataTransferObject(converter = EnumConverter.class)
public enum FormExportFrequency {

    DAILY(TimeInterval.ONE_DAY.getMillis()),
    WEEKLY(TimeInterval.ONE_WEEK.getMillis()),
    EVERY_TWO_WEEKS(TimeInterval.TWO_WEEKS.getMillis()),
    MONTHLY(TimeInterval.ONE_MONTH.getMillis()),
    QUARTERLY(TimeInterval.THREE_WEEKS.getMillis()),
    YEARLY(TimeInterval.ONE_YEAR.getMillis());

    FormExportFrequency(final long millis) {
        this.millis = millis;
    }

    public long getMillis() {
        return millis;
    }

    private final long millis;
}
