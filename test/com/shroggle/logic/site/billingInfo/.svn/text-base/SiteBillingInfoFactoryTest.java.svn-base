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

import org.junit.Test;
import org.junit.runner.RunWith;
import com.shroggle.util.config.BillingInfoProperties;
import com.shroggle.TestRunnerWithMockServices;
import junit.framework.Assert;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class SiteBillingInfoFactoryTest {

    @Test
    public void testCreateInstance_withBillingInfoType() {
        BillingInfoProperties properties = new BillingInfoProperties();

        SitesBillingInfoChecker checker = SiteBillingInfoFactory.createInstance(properties, BillingInfoType.FULL_CHECKER);
        Assert.assertTrue(checker instanceof SitesBillingInfoFullChecker);

        checker = SiteBillingInfoFactory.createInstance(properties, BillingInfoType.LIGHT_CHECKER);
        Assert.assertTrue(checker instanceof SitesBillingInfoLightChecker);

        checker = SiteBillingInfoFactory.createInstance(properties, BillingInfoType.EMAIL_NOTIFICATIONS);
        Assert.assertTrue(checker instanceof CreditCardNotificationMailSender);
    }


    @Test
    public void testCreateInstance() {
        BillingInfoProperties properties = new BillingInfoProperties();

        properties.setCheckSitesBillingInfo(true);
        SitesBillingInfoChecker checker = SiteBillingInfoFactory.createInstance(properties);
        Assert.assertTrue(checker instanceof SitesBillingInfoFullChecker);

        properties.setCheckSitesBillingInfo(false);
        checker = SiteBillingInfoFactory.createInstance(properties);
        Assert.assertTrue(checker instanceof SitesBillingInfoLightChecker);
    }
    
}
