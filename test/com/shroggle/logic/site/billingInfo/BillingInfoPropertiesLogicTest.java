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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.exception.BillingInfoPropertiesNotFoundException;
import com.shroggle.util.config.BillingInfoProperties;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Balakirev Anatoliy
 *         Date: 17.07.2009
 *         Time: 18:28:38
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class BillingInfoPropertiesLogicTest extends TestCase {


    @Test(expected = BillingInfoPropertiesNotFoundException.class)
    public void create_withNullProrperties() {
        new BillingInfoPropertiesManager(null);
    }

    @Test
    public void cerateDelay_withExecuteAtMidnightTrue() {
        final BillingInfoProperties properties = new BillingInfoProperties();
        properties.setExecuteAtMidnight(true);

        Calendar currentDateCalendar = new GregorianCalendar();
        currentDateCalendar.set(Calendar.YEAR, 2009);
        currentDateCalendar.set(Calendar.MONTH, Calendar.MAY);
        currentDateCalendar.set(Calendar.DAY_OF_MONTH, 1);
        currentDateCalendar.set(Calendar.HOUR_OF_DAY, 0);
        currentDateCalendar.set(Calendar.MINUTE, 0);
        currentDateCalendar.set(Calendar.SECOND, 0);
        currentDateCalendar.set(Calendar.MILLISECOND, 0);

        BillingInfoPropertiesManager propertiesManager = new BillingInfoPropertiesManager(properties);
        final long delay = propertiesManager.createDelay(currentDateCalendar.getTimeInMillis());
        Assert.assertEquals(24 * 60 * 60 * 1000L, delay);
    }

    @Test
    public void cerateDelay_withExecuteAtMidnightTrue_withMinutesAfterMidnight() {
        final int FOUR_HORS_IN_MINUTES = 4 * 60;
        final BillingInfoProperties properties = new BillingInfoProperties();
        properties.setExecuteAtMidnight(true);
        properties.setMinutesAfterMidnight(FOUR_HORS_IN_MINUTES);

        Calendar currentDateCalendar = new GregorianCalendar();
        currentDateCalendar.set(Calendar.YEAR, 2009);
        currentDateCalendar.set(Calendar.MONTH, Calendar.MAY);
        currentDateCalendar.set(Calendar.DAY_OF_MONTH, 1);
        currentDateCalendar.set(Calendar.HOUR_OF_DAY, 0);
        currentDateCalendar.set(Calendar.MINUTE, 0);
        currentDateCalendar.set(Calendar.SECOND, 0);
        currentDateCalendar.set(Calendar.MILLISECOND, 0);

        BillingInfoPropertiesManager propertiesManager = new BillingInfoPropertiesManager(properties);
        final long delay = propertiesManager.createDelay(currentDateCalendar.getTimeInMillis());
        Assert.assertEquals(((24 * 60) + FOUR_HORS_IN_MINUTES) * 60 * 1000L, delay);
    }

    @Test
    public void cerateDelay_withExecuteAtMidnightTrue_withNegativeMinutesAfterMidnight() {
        final int FOUR_HORS_IN_MINUTES = -(4 * 60);
        final BillingInfoProperties properties = new BillingInfoProperties();
        properties.setExecuteAtMidnight(true);
        properties.setMinutesAfterMidnight(FOUR_HORS_IN_MINUTES);

        Calendar currentDateCalendar = new GregorianCalendar();
        currentDateCalendar.set(Calendar.YEAR, 2009);
        currentDateCalendar.set(Calendar.MONTH, Calendar.MAY);
        currentDateCalendar.set(Calendar.DAY_OF_MONTH, 1);
        currentDateCalendar.set(Calendar.HOUR_OF_DAY, 0);
        currentDateCalendar.set(Calendar.MINUTE, 0);
        currentDateCalendar.set(Calendar.SECOND, 0);
        currentDateCalendar.set(Calendar.MILLISECOND, 0);

        BillingInfoPropertiesManager propertiesManager = new BillingInfoPropertiesManager(properties);
        final long delay = propertiesManager.createDelay(currentDateCalendar.getTimeInMillis());
        Assert.assertEquals(24 * 60 * 60 * 1000L, delay);
    }

    @Test
    public void cerateDelay_withExecuteAtMidnightFalse() {
        final BillingInfoProperties properties = new BillingInfoProperties();
        properties.setExecuteAtMidnight(false);


        BillingInfoPropertiesManager propertiesManager = new BillingInfoPropertiesManager(properties);
        final long delay = propertiesManager.createDelay(System.currentTimeMillis());
        Assert.assertEquals(0, delay);
    }


    @Test
    public void ceratePeriod_withExecuteAtMidnightTrue() {
        final BillingInfoProperties properties = new BillingInfoProperties();
        properties.setExecuteAtMidnight(true);
        properties.setExecutionPeriod(30);

        BillingInfoPropertiesManager propertiesManager = new BillingInfoPropertiesManager(properties);
        final long period = propertiesManager.createPeriod();
        Assert.assertEquals(24 * 60 * 60 * 1000L, period);
    }


    @Test
    public void ceratePeriod_withExecuteAtMidnightFalse() {
        final BillingInfoProperties properties = new BillingInfoProperties();
        properties.setExecuteAtMidnight(false);
        properties.setExecutionPeriod(30);

        BillingInfoPropertiesManager propertiesManager = new BillingInfoPropertiesManager(properties);
        final long period = propertiesManager.createPeriod();
        Assert.assertEquals(30 * 60 * 1000L, period);
    }

}
