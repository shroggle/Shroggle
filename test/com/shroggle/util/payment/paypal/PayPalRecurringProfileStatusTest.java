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
package com.shroggle.util.payment.paypal;

import org.junit.Test;
import junit.framework.Assert;

/**
 * @author Balakirev Anatoliy
 */
public class PayPalRecurringProfileStatusTest {

    @Test
    public void test(){
        Assert.assertEquals("Reactivate", PayPalRecurringProfileStatus.ACTIVE.getActionForPaypalExcecution());
        Assert.assertEquals("Cancel", PayPalRecurringProfileStatus.CANCELED.getActionForPaypalExcecution());
        Assert.assertEquals("", PayPalRecurringProfileStatus.EXPIRED.getActionForPaypalExcecution());
        Assert.assertEquals("", PayPalRecurringProfileStatus.PENDING.getActionForPaypalExcecution());
        Assert.assertEquals("Suspend", PayPalRecurringProfileStatus.SUSPENDED.getActionForPaypalExcecution());
    }
}
