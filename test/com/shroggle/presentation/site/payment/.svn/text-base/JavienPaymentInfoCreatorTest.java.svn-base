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
package com.shroggle.presentation.site.payment;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.JavienException;
import com.shroggle.logic.payment.PaymentInfoCreatorState;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.payment.javien.JavienWithExceptionsInMethods;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class JavienPaymentInfoCreatorTest {
    final JavienPaymentInfoCreator creator = new JavienPaymentInfoCreator();


    @Test
    public void executeRegularProfileCreation_forActive() throws Exception {
        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        final User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), user);

        final JavienPaymentInfoRequest request = new JavienPaymentInfoRequest(creditCard.getCreditCardId(),
                ChargeType.SITE_MONTHLY_FEE, PaymentSettingsOwnerType.SITE, site.getSiteId(), null);

        final PaymentInfoCreatorState response = creator.execute(request);
        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_ACTIVE, response);
        Assert.assertNotNull(site.getSitePaymentSettings());
        Assert.assertEquals(SiteStatus.ACTIVE, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, site.getSitePaymentSettings().getPaymentMethod());
        Assert.assertEquals(ChargeType.SITE_MONTHLY_FEE, site.getSitePaymentSettings().getChargeType());
    }

    @Test
    public void executeRegularProfileCreation_forPending() throws Exception {
        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.PENDING);
        final User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), user);

        final JavienPaymentInfoRequest request = new JavienPaymentInfoRequest(creditCard.getCreditCardId(),
                ChargeType.SITE_MONTHLY_FEE, PaymentSettingsOwnerType.SITE, site.getSiteId(), null);

        final PaymentInfoCreatorState response = creator.execute(request);
        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_PENDING, response);
        Assert.assertNotNull(site.getSitePaymentSettings());
        Assert.assertEquals(SiteStatus.ACTIVE, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, site.getSitePaymentSettings().getPaymentMethod());
        Assert.assertEquals(ChargeType.SITE_MONTHLY_FEE, site.getSitePaymentSettings().getChargeType());
    }

    @Test
    public void executeRegularProfileCreation_forSuspended() throws Exception {
        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.SUSPENDED);
        site.getSitePaymentSettings().setRemainingTimeOfUsage(1000000000L);
        final User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), user);

        final JavienPaymentInfoRequest request = new JavienPaymentInfoRequest(creditCard.getCreditCardId(),
                ChargeType.SITE_MONTHLY_FEE, PaymentSettingsOwnerType.SITE, site.getSiteId(), null);

        final PaymentInfoCreatorState response = creator.execute(request);
        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_SUSPENDED, response);
        Assert.assertNotNull(site.getSitePaymentSettings());
        Assert.assertEquals(SiteStatus.ACTIVE, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, site.getSitePaymentSettings().getPaymentMethod());
        Assert.assertEquals(ChargeType.SITE_MONTHLY_FEE, site.getSitePaymentSettings().getChargeType());
    }

    @Test(expected = JavienException.class)
    public void executeWithoutCreditCard() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final JavienPaymentInfoRequest request = new JavienPaymentInfoRequest(0,
                ChargeType.SITE_MONTHLY_FEE, PaymentSettingsOwnerType.SITE, site.getSiteId(), null);

        creator.execute(request);
    }

    @Test(expected = JavienException.class)
    public void executeWithoutSite() throws Exception {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), user);

        final JavienPaymentInfoRequest request = new JavienPaymentInfoRequest(creditCard.getCreditCardId(),
                ChargeType.SITE_MONTHLY_FEE, PaymentSettingsOwnerType.SITE, -1, null);

        creator.execute(request);
    }

    @Ignore("This test is ignored, because we don`t use Javien any more. We use Authorize.net instead.")
    @Test(expected = JavienException.class)
    public void executeRegularProfileCreation_withExceptionInJavien() throws Exception {
        ServiceLocator.setJavien(new JavienWithExceptionsInMethods());
        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        final User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), user);

        final JavienPaymentInfoRequest request = new JavienPaymentInfoRequest(creditCard.getCreditCardId(),
                ChargeType.SITE_MONTHLY_FEE, PaymentSettingsOwnerType.SITE, site.getSiteId(), null);

        creator.execute(request);
    }
}
