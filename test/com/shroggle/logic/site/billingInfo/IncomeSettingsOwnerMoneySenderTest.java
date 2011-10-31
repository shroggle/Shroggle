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
import com.shroggle.TestUtil;
import com.shroggle.entity.IncomeSettings;
import com.shroggle.entity.PaymentMethod;
import com.shroggle.entity.Site;
import com.shroggle.util.payment.PaymentSystem;
import com.shroggle.util.payment.paypal.PayPal;
import com.shroggle.util.payment.paypal.PayPalMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class IncomeSettingsOwnerMoneySenderTest {

    @Test
    public void testExecute() {
        final Site site1 = TestUtil.createSite();
        final IncomeSettings incomeSettings1 = TestUtil.createIncomeSettings(site1, "firstSiteEmail", 100.0, "Payment details 1");
        site1.setIncomeSettings(incomeSettings1);

        final Site site2 = TestUtil.createSite();
        final IncomeSettings incomeSettings2 = TestUtil.createIncomeSettings(site2, "secondSiteEmail", 5.0, "Payment details 2");
        site2.setIncomeSettings(incomeSettings2);

        final Site site3 = TestUtil.createSite();
        final IncomeSettings incomeSettings3 = TestUtil.createIncomeSettings(site3, "thirdSiteEmail", 0.0, "Payment details 3");
        site3.setIncomeSettings(incomeSettings3);

        TestUtil.createSite();

        Assert.assertEquals(0, paypalMock.getMassPaymentTransactions().size());


        IncomeSettingsOwnerMoneySender.execute();


        Assert.assertEquals(2, paypalMock.getMassPaymentTransactions().size());
        Assert.assertEquals("Mass payment for site with siteId = 1, sum = 100.0. Note:Payment details 1", paypalMock.getMassPaymentTransactions().get(0));
        Assert.assertEquals("Mass payment for site with siteId = 2, sum = 5.0. Note:Payment details 2", paypalMock.getMassPaymentTransactions().get(1));

        Assert.assertEquals(0.0, site1.getIncomeSettings().getSum(), 1);
        Assert.assertNull(site1.getIncomeSettings().getPaymentDetails());

        Assert.assertEquals(0.0, site2.getIncomeSettings().getSum(), 1);
        Assert.assertNull(site2.getIncomeSettings().getPaymentDetails());

        Assert.assertEquals(0.0, site3.getIncomeSettings().getSum(), 1);
        Assert.assertEquals("Payment details 3", site3.getIncomeSettings().getPaymentDetails());
    }

    private final PayPalMock paypalMock = (PayPalMock) ((PayPal) PaymentSystem.newInstance(PaymentMethod.PAYPAL));
}
