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
package com.shroggle.logic.site.billingInfo;

import com.shroggle.exception.BillingInfoPropertiesNotFoundException;
import com.shroggle.util.DateUtil;
import com.shroggle.util.TimeInterval;
import com.shroggle.util.config.BillingInfoProperties;

/**
 * @author Balakirev Anatoliy
 */
public class BillingInfoPropertiesManager {

    public BillingInfoPropertiesManager(final BillingInfoProperties properties) {
        if (properties == null) {
            throw new BillingInfoPropertiesNotFoundException("Can`t create BillingInfoPropertiesManager by null BillingInfoProperties.");
        }
        this.properties = properties;
    }

    /**
     * @param currentTime - Current time in milliseconds
     * @return - Delay in milliseconds.
     *         If executeAtMidnight = true - return time remaining to midnight plus time from "minutesAfterMidnight"
     *         parameter if specified.
     *         If executeAtMidnight = false - return 0
     */
    public long createDelay(final long currentTime) {
        if (properties.isExecuteAtMidnight()) {
            final long millisecondsToMidnight = DateUtil.getMillisToMidnight(currentTime);
            if (properties.getMinutesAfterMidnight() != null && properties.getMinutesAfterMidnight() > 0) {
                return millisecondsToMidnight + DateUtil.minutesToMilliseconds(properties.getMinutesAfterMidnight());
            } else {
                return millisecondsToMidnight;
            }
        } else {
            return 0;
        }
    }

    public long createPeriod() {
        if (properties.isExecuteAtMidnight()) {
            return TimeInterval.ONE_DAY.getMillis();
        } else {
            return DateUtil.minutesToMilliseconds(properties.getExecutionPeriod());
        }
    }


    private final BillingInfoProperties properties;
}
