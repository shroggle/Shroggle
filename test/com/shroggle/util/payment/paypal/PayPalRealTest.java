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

import com.shroggle.exception.UnknownRecurringProfileStatusException;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Balakirev Anatoliy
 */
public class PayPalRealTest {
    @Test
    public void testCreateRecurringPaymentsProfile() throws Exception {
        Assert.assertEquals(PayPalRecurringProfileStatus.ACTIVE, PayPalReal.createRecurringPaymentStatus("ActiveProfile"));
        Assert.assertEquals(PayPalRecurringProfileStatus.ACTIVE, PayPalReal.createRecurringPaymentStatus("Active"));
        Assert.assertEquals(PayPalRecurringProfileStatus.CANCELED, PayPalReal.createRecurringPaymentStatus("CancelledProfile"));
        Assert.assertEquals(PayPalRecurringProfileStatus.CANCELED, PayPalReal.createRecurringPaymentStatus("Cancelled"));
        Assert.assertEquals(PayPalRecurringProfileStatus.EXPIRED, PayPalReal.createRecurringPaymentStatus("ExpiredProfile"));
        Assert.assertEquals(PayPalRecurringProfileStatus.EXPIRED, PayPalReal.createRecurringPaymentStatus("Expired"));
        Assert.assertEquals(PayPalRecurringProfileStatus.PENDING, PayPalReal.createRecurringPaymentStatus("PendingProfile"));
        Assert.assertEquals(PayPalRecurringProfileStatus.PENDING, PayPalReal.createRecurringPaymentStatus("Pending"));
        Assert.assertEquals(PayPalRecurringProfileStatus.SUSPENDED, PayPalReal.createRecurringPaymentStatus("SuspendedProfile"));
        Assert.assertEquals(PayPalRecurringProfileStatus.SUSPENDED, PayPalReal.createRecurringPaymentStatus("Suspended"));
    }

    @Test(expected = UnknownRecurringProfileStatusException.class)
    public void testCreateRecurringPaymentsProfile_byWrongArgument() throws Exception {
        Assert.assertEquals(PayPalRecurringProfileStatus.ACTIVE, PayPalReal.createRecurringPaymentStatus("adfasdf"));
    }

    @Test(expected = UnknownRecurringProfileStatusException.class)
    public void testCreateRecurringPaymentsProfile_withoutArgument() throws Exception {
        Assert.assertEquals(PayPalRecurringProfileStatus.ACTIVE, PayPalReal.createRecurringPaymentStatus(null));
    }
}
