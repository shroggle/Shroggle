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

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.shroggle.entity.Site;
import com.shroggle.entity.PaymentReason;
import com.shroggle.TestUtil;
import com.shroggle.TestRunnerWithMockServices;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class PayPalEmptyTest {

    @Test
    public void getEnv() {
        Assert.assertEquals("", payPal.getPaypalLink());
    }

    @Test
    public void setCustomerBillingAgreement() {
        Assert.assertNull(payPal.setCustomerBillingAgreement(null, null, null, PaymentReason.SHROGGLE_MONTHLY_PAYMENT,
                null, null, null));
    }

    @Test
    public void createRecurringPaymentsProfile() {
        final Site site = TestUtil.createSite();
        Assert.assertNotNull(payPal.createRecurringPaymentsProfile(null, null, null, 0, site,
                PaymentReason.SHROGGLE_MONTHLY_PAYMENT, null));
    }

    private final PayPal payPal = new PayPalMock();

}
