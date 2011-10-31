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

import org.junit.Test;
import org.junit.runner.RunWith;
import junit.framework.Assert;
import com.shroggle.TestRunnerWithMockServices;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class SitePaymentSettingsTest {
    @Test
    public void create() {
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        Assert.assertEquals(ChargeType.SITE_MONTHLY_FEE, sitePaymentSettings.getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, sitePaymentSettings.getPaymentMethod());
        Assert.assertEquals(SiteStatus.PENDING, sitePaymentSettings.getSiteStatus());
        Assert.assertEquals(0, sitePaymentSettings.getPrice(), 0);
        Assert.assertNull(sitePaymentSettings.getRecurringPaymentId());
        Assert.assertNull(sitePaymentSettings.getRemainingTimeOfUsage());
    }
}
