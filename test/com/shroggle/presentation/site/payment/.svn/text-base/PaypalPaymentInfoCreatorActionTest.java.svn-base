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
import com.shroggle.logic.site.SiteManager;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.payment.paypal.PayPalWithExceptionsInMethods;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class PaypalPaymentInfoCreatorActionTest {

    @Before
    public void before() {
        action.setContext(new ActionBeanContext());
        action.getContext().setRequest(new MockHttpServletRequest("", ""));
        action.getContext().getRequest().setAttribute("token", "token");
    }

    /*------------------------------------------------------Site------------------------------------------------------*/

    @Test
    public void executeRegularProfileCreation() throws Exception {
        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final PaypalPaymentInfoRequest request = new PaypalPaymentInfoRequest(site.getId(), PaymentSettingsOwnerType.SITE,
                ChargeType.SITE_MONTHLY_FEE, "url", "error_url", PaymentReason.SHROGGLE_MONTHLY_PAYMENT, null);
        final int requestId = ServiceLocator.getPaypalPaymentInfoRequestStorage().put(request);

        action.setRequestId(requestId);

        Assert.assertEquals("url", ((ResolutionMock) action.execute()).getForwardToUrl());

        Assert.assertNotNull(site.getSitePaymentSettings());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());
        Assert.assertEquals(PaymentMethod.PAYPAL, site.getSitePaymentSettings().getPaymentMethod());
        Assert.assertEquals(ChargeType.SITE_MONTHLY_FEE, site.getSitePaymentSettings().getChargeType());
    }

    @Test
    public void executeRegularProfileCreationForChildSite() throws Exception {
        Site parentSite = TestUtil.createSite();
        Site childSite = TestUtil.createSite();
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration(parentSite);
        ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(childSiteRegistration, childSite);
        childSiteSettings.setPrice250mb(100.0);
        childSiteSettings.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        childSite.setChildSiteSettings(childSiteSettings);
        TestUtil.createUserAndUserOnSiteRightAndLogin(childSite, SiteAccessLevel.ADMINISTRATOR);

        final PaypalPaymentInfoRequest request = new PaypalPaymentInfoRequest(childSite.getId(), PaymentSettingsOwnerType.SITE,
                ChargeType.SITE_250MB_MONTHLY_FEE, "url", "error_url", PaymentReason.SHROGGLE_MONTHLY_PAYMENT, null);
        final int requestId = ServiceLocator.getPaypalPaymentInfoRequestStorage().put(request);

        action.setRequestId(requestId);

        Assert.assertEquals("url", ((ResolutionMock) action.execute()).getForwardToUrl());

        Assert.assertNotNull(childSite.getSitePaymentSettings());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(childSite).getSiteStatus());
        Assert.assertEquals(PaymentMethod.PAYPAL, childSite.getSitePaymentSettings().getPaymentMethod());
        Assert.assertEquals(ChargeType.SITE_250MB_MONTHLY_FEE, childSite.getSitePaymentSettings().getChargeType());
        Assert.assertNotNull(childSite.getIncomeSettings());
    }

    @Test
    public void executeRegularProfileCreation_withExceptionInPaypal() throws Exception {
        ServiceLocator.setPayPal(new PayPalWithExceptionsInMethods());
        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final PaypalPaymentInfoRequest request = new PaypalPaymentInfoRequest(site.getId(), PaymentSettingsOwnerType.SITE,
                ChargeType.SITE_MONTHLY_FEE, "url", "error_url", PaymentReason.SHROGGLE_MONTHLY_PAYMENT, null);
        final int requestId = ServiceLocator.getPaypalPaymentInfoRequestStorage().put(request);

        action.setRequestId(requestId);

        Assert.assertEquals("error_url", ((ResolutionMock) action.execute()).getRedirectByUrl());
    }
    
    @Test
    public void executeWithoutRequest() throws Exception {
        Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.PENDING);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Assert.assertEquals("/paymentException.jsp", ((ResolutionMock) action.execute()).getRedirectByUrl());
    }

    @Test
    public void executeWithoutSiteId() throws Exception {
        Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final PaypalPaymentInfoRequest request = new PaypalPaymentInfoRequest(0, PaymentSettingsOwnerType.SITE,
                ChargeType.SITE_MONTHLY_FEE, "url", "error_url", PaymentReason.SHROGGLE_MONTHLY_PAYMENT, null);
        final int requestId = ServiceLocator.getPaypalPaymentInfoRequestStorage().put(request);

        action.setRequestId(requestId);

        Assert.assertEquals("/paymentException.jsp", ((ResolutionMock) action.execute()).getRedirectByUrl());
    }

    private final PaypalPaymentInfoCreatorAction action = new PaypalPaymentInfoCreatorAction();

}
