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
package com.shroggle.logic.groups;

import com.shroggle.entity.PaymentPeriod;
import com.shroggle.util.TimeInterval;

/**
 * @author dmitry.solomadin
 */
public enum SubscriptionTimeType {
    // Please, preserve order.
    INDEFINITE("one-time payment", null, 0),
    ONE_DAY("1 day", PaymentPeriod.DAY, 1),
    TWO_DAYS("2 days", PaymentPeriod.DAY, 2),
    THREE_DAYS("3 days", PaymentPeriod.DAY, 3),
    FIVE_DAYS("5 days", PaymentPeriod.DAY, 5),
    ONE_WEEK("one week", PaymentPeriod.WEEK, 1),
    TEN_DAYS("10 days", PaymentPeriod.DAY, 10),
    TWO_WEEKS("two weeks", PaymentPeriod.WEEK, 2),
    THREE_WEEKS("three weeks", PaymentPeriod.WEEK, 3),
    FOUR_WEEKS("four weeks", PaymentPeriod.WEEK, 4),
    ONE_MONTH("one month", PaymentPeriod.MONTH, 1),
    TWO_MONTHS("two months", PaymentPeriod.MONTH, 2),
    THREE_MONTHS("three months", PaymentPeriod.MONTH, 3),
    SIX_MONTHS("six months", PaymentPeriod.MONTH, 6),
    ONE_YEAR("one year", PaymentPeriod.YEAR, 1);

    SubscriptionTimeType(final String text, final PaymentPeriod period, final int frequency) {
        this.text = text;
        this.period = period;
        this.frequency = frequency;
    }

    public String getText() {
        return text;
    }

    public int getFrequency() {
        return frequency;
    }

    public PaymentPeriod getPeriod() {
        return period;
    }

    public TimeInterval convertToTimeInterval() {
        switch (this) {
            case INDEFINITE: {
                return TimeInterval.ONE_HUNDRED_YEARS;
            }
            case ONE_DAY: {
                return TimeInterval.ONE_DAY;
            }
            case TWO_DAYS: {
                return TimeInterval.TWO_DAYS;
            }
            case THREE_DAYS: {
                return TimeInterval.THREE_DAYS;
            }
            case FIVE_DAYS: {
                return TimeInterval.FIVE_DAYS;
            }
            case TEN_DAYS: {
                return TimeInterval.TEN_DAYS;
            }
            case TWO_WEEKS: {
                return TimeInterval.TWO_WEEKS;
            }
            case THREE_WEEKS: {
                return TimeInterval.THREE_WEEKS;
            }
            case FOUR_WEEKS: {
                return TimeInterval.FOUR_WEEKS;
            }
            case ONE_MONTH: {
                return TimeInterval.ONE_MONTH;
            }
            case TWO_MONTHS: {
                return TimeInterval.TWO_MONTHS;
            }
            case THREE_MONTHS: {
                return TimeInterval.THREE_MONTHS;
            }
            case SIX_MONTHS: {
                return TimeInterval.SIX_MONTHS;
            }
            case ONE_YEAR: {
                return TimeInterval.ONE_YEAR;
            }
            default: {
                throw new IllegalArgumentException("Cannot convert " + this + " to TimeInterval.");
            }
        }
    }

    final String text;
    final int frequency;
    final PaymentPeriod period;

}
