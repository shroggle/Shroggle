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
package com.shroggle.logic.payment;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class PaymentInfoCreatorIncomeSettingsTest {
    /*-----------------------------------------ChildSiteSettings with Javien------------------------------------------*/

    @Test
    public void testCreate_forActiveChildSiteSettingsWithJavien() {
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site parentSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        childSiteSettings.setPrice250mb(100);
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2010, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        childSiteSettings.setSitePaymentSettings(sitePaymentSettings);

        childSiteSettings.getChildSiteRegistration().setUseOwnAuthorize(true);
        childSiteSettings.getChildSiteRegistration().setUseOwnPaypal(true);
        parentSite.setIncomeSettings(new IncomeSettings());

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(childSiteSettings.getChildSiteSettingsId(), PaymentSettingsOwnerType.CHILD_SITE_SETTINGS,
                ChargeType.SITE_250MB_MONTHLY_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();

        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_ACTIVE, state);
        Assert.assertEquals(childSiteSettings.getPrice250mb(), state.getPrice());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings());
        Assert.assertEquals(childSiteSettings.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSiteSettings.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_250MB_MONTHLY_FEE, childSiteSettings.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, childSiteSettings.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(creditCard, childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, childSiteSettings.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(childSiteSettings.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertTrue(parentSite.getIncomeSettings().getSum() == 0);

        final Calendar expectedExpirationDate = new GregorianCalendar(2010, 2, 1, 0, 0);
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(childSiteSettings.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
//        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreate_forPendingChildSiteSettingsWithJavien() {
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site parentSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        childSiteSettings.setPrice250mb(100);
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2110, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        childSiteSettings.setSitePaymentSettings(sitePaymentSettings);

        childSiteSettings.getChildSiteRegistration().setUseOwnAuthorize(true);
        childSiteSettings.getChildSiteRegistration().setUseOwnPaypal(true);
        parentSite.setIncomeSettings(new IncomeSettings());

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(childSiteSettings.getChildSiteSettingsId(), PaymentSettingsOwnerType.CHILD_SITE_SETTINGS,
                ChargeType.SITE_250MB_MONTHLY_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);


        final PaymentInfoCreatorState state = paymentInfoCreator.create();


        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_PENDING, state);
        Assert.assertEquals(childSiteSettings.getPrice250mb(), state.getPrice());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings());
        Assert.assertEquals(childSiteSettings.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSiteSettings.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_250MB_MONTHLY_FEE, childSiteSettings.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, childSiteSettings.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(creditCard, childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, childSiteSettings.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(childSiteSettings.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertTrue(parentSite.getIncomeSettings().getSum() == 0);

        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date());
        expectedExpirationDate.set(Calendar.DAY_OF_MONTH, (expectedExpirationDate.get(Calendar.DAY_OF_MONTH) + 30));
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(childSiteSettings.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreate_forActiveChildSiteSettingsWithJavien_withTestCreditCard() {
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());
        creditCard.setCreditCardNumber("4444333322221111");

        final Site parentSite = TestUtil.createSite();
        parentSite.setIncomeSettings(TestUtil.createIncomeSettings(parentSite, "", 0));
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        childSiteSettings.setPrice250mb(100);
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2010, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        childSiteSettings.setSitePaymentSettings(sitePaymentSettings);

        childSiteSettings.getChildSiteRegistration().setUseOwnAuthorize(true);
        childSiteSettings.getChildSiteRegistration().setUseOwnPaypal(true);
        parentSite.setIncomeSettings(new IncomeSettings());

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(childSiteSettings.getChildSiteSettingsId(), PaymentSettingsOwnerType.CHILD_SITE_SETTINGS,
                ChargeType.SITE_250MB_MONTHLY_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();

        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_ACTIVE, state);
        Assert.assertEquals(childSiteSettings.getPrice250mb(), state.getPrice());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings());
        Assert.assertEquals(childSiteSettings.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSiteSettings.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_250MB_MONTHLY_FEE, childSiteSettings.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, childSiteSettings.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(creditCard, childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, childSiteSettings.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(childSiteSettings.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertTrue("We don`t add idfference to parent sites income setting when using test credit card",
                parentSite.getIncomeSettings().getSum() == 0);

        final Calendar expectedExpirationDate = new GregorianCalendar(2010, 2, 1, 0, 0);
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(childSiteSettings.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
//        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreate_forPendingChildSiteSettingsWithJavien_withTestCreditCard() {
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());
        creditCard.setCreditCardNumber("4444333322221111");

        final Site parentSite = TestUtil.createSite();
        parentSite.setIncomeSettings(TestUtil.createIncomeSettings(parentSite, "", 0));
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        childSiteSettings.setPrice250mb(100);
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2110, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        childSiteSettings.setSitePaymentSettings(sitePaymentSettings);

        childSiteSettings.getChildSiteRegistration().setUseOwnAuthorize(true);
        childSiteSettings.getChildSiteRegistration().setUseOwnPaypal(true);
        parentSite.setIncomeSettings(new IncomeSettings());

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(childSiteSettings.getChildSiteSettingsId(), PaymentSettingsOwnerType.CHILD_SITE_SETTINGS,
                ChargeType.SITE_250MB_MONTHLY_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();
        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_PENDING, state);
        Assert.assertEquals(childSiteSettings.getPrice250mb(), state.getPrice());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings());
        Assert.assertEquals(childSiteSettings.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSiteSettings.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_250MB_MONTHLY_FEE, childSiteSettings.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, childSiteSettings.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(creditCard, childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, childSiteSettings.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(childSiteSettings.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertTrue("We don`t add idfference to parent sites income setting when using test credit card",
                parentSite.getIncomeSettings().getSum() == 0);

        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date());
        expectedExpirationDate.set(Calendar.DAY_OF_MONTH, (expectedExpirationDate.get(Calendar.DAY_OF_MONTH) + 30));
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(childSiteSettings.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreate_forSuspendedChildSiteSettingsWithJavien() {
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site parentSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        childSiteSettings.setPrice250mb(100);
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.SUSPENDED);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should not be changed for this state
        sitePaymentSettings.setRemainingTimeOfUsage((10 * 24 * 60 * 60 * 1000L));// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2110, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        childSiteSettings.setSitePaymentSettings(sitePaymentSettings);

        childSiteSettings.getChildSiteRegistration().setUseOwnAuthorize(true);
        childSiteSettings.getChildSiteRegistration().setUseOwnPaypal(true);
        parentSite.setIncomeSettings(new IncomeSettings());

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(childSiteSettings.getChildSiteSettingsId(), PaymentSettingsOwnerType.CHILD_SITE_SETTINGS,
                ChargeType.SITE_250MB_MONTHLY_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();
        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_SUSPENDED, state);
        Assert.assertEquals(childSiteSettings.getPrice250mb(), state.getPrice());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings());
        Assert.assertEquals(childSiteSettings.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSiteSettings.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_250MB_MONTHLY_FEE, childSiteSettings.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, childSiteSettings.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, childSiteSettings.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(childSiteSettings.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertTrue(parentSite.getIncomeSettings().getSum() == 0);

        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date());
        expectedExpirationDate.set(Calendar.DAY_OF_MONTH, (expectedExpirationDate.get(Calendar.DAY_OF_MONTH) + 10));
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(childSiteSettings.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }
    /*-----------------------------------------ChildSiteSettings with Javien------------------------------------------*/

    /*-----------------------------------------ChildSiteSettings with AuthorizeNet------------------------------------------*/

    @Test
    public void testCreate_forActiveChildSiteSettingsWithAuthorizeNet() {

        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site parentSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        childSiteSettings.setPrice250mb(100);
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2010, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        childSiteSettings.setSitePaymentSettings(sitePaymentSettings);

        childSiteSettings.getChildSiteRegistration().setUseOwnAuthorize(true);
        childSiteSettings.getChildSiteRegistration().setUseOwnPaypal(true);
        parentSite.setIncomeSettings(new IncomeSettings());

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(childSiteSettings.getChildSiteSettingsId(), PaymentSettingsOwnerType.CHILD_SITE_SETTINGS,
                ChargeType.SITE_250MB_MONTHLY_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();

        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_ACTIVE, state);
        Assert.assertEquals(childSiteSettings.getPrice250mb(), state.getPrice());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings());
        Assert.assertEquals(childSiteSettings.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSiteSettings.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_250MB_MONTHLY_FEE, childSiteSettings.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, childSiteSettings.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(creditCard, childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, childSiteSettings.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(childSiteSettings.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertTrue(parentSite.getIncomeSettings().getSum() == 0);

        final Calendar expectedExpirationDate = new GregorianCalendar(2010, 2, 1, 0, 0);
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(childSiteSettings.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
//        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreate_forPendingChildSiteSettingsWithAuthorizeNet() {

        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site parentSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        childSiteSettings.setPrice250mb(100);
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2110, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        childSiteSettings.setSitePaymentSettings(sitePaymentSettings);

        childSiteSettings.getChildSiteRegistration().setUseOwnAuthorize(true);
        childSiteSettings.getChildSiteRegistration().setUseOwnPaypal(true);
        parentSite.setIncomeSettings(new IncomeSettings());

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(childSiteSettings.getChildSiteSettingsId(), PaymentSettingsOwnerType.CHILD_SITE_SETTINGS,
                ChargeType.SITE_250MB_MONTHLY_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);


        final PaymentInfoCreatorState state = paymentInfoCreator.create();


        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_PENDING, state);
        Assert.assertEquals(childSiteSettings.getPrice250mb(), state.getPrice());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings());
        Assert.assertEquals(childSiteSettings.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSiteSettings.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_250MB_MONTHLY_FEE, childSiteSettings.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, childSiteSettings.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(creditCard, childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, childSiteSettings.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(childSiteSettings.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertTrue(parentSite.getIncomeSettings().getSum() == 0);

        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date());
        expectedExpirationDate.set(Calendar.DAY_OF_MONTH, (expectedExpirationDate.get(Calendar.DAY_OF_MONTH) + 30));
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(childSiteSettings.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreate_forActiveChildSiteSettingsWithAuthorizeNet_withTestCreditCard() {

        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());
        creditCard.setCreditCardNumber("4444333322221111");

        final Site parentSite = TestUtil.createSite();
        parentSite.setIncomeSettings(TestUtil.createIncomeSettings(parentSite, "", 0));
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        childSiteSettings.setPrice250mb(100);
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2010, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        childSiteSettings.setSitePaymentSettings(sitePaymentSettings);

        childSiteSettings.getChildSiteRegistration().setUseOwnAuthorize(true);
        childSiteSettings.getChildSiteRegistration().setUseOwnPaypal(true);
        parentSite.setIncomeSettings(new IncomeSettings());

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(childSiteSettings.getChildSiteSettingsId(), PaymentSettingsOwnerType.CHILD_SITE_SETTINGS,
                ChargeType.SITE_250MB_MONTHLY_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();

        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_ACTIVE, state);
        Assert.assertEquals(childSiteSettings.getPrice250mb(), state.getPrice());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings());
        Assert.assertEquals(childSiteSettings.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSiteSettings.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_250MB_MONTHLY_FEE, childSiteSettings.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, childSiteSettings.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(creditCard, childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, childSiteSettings.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(childSiteSettings.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertTrue("We don`t add idfference to parent sites income setting when using test credit card",
                parentSite.getIncomeSettings().getSum() == 0);

        final Calendar expectedExpirationDate = new GregorianCalendar(2010, 2, 1, 0, 0);
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(childSiteSettings.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
//        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreate_forPendingChildSiteSettingsWithAuthorizeNet_withTestCreditCard() {

        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());
        creditCard.setCreditCardNumber("4444333322221111");

        final Site parentSite = TestUtil.createSite();
        parentSite.setIncomeSettings(TestUtil.createIncomeSettings(parentSite, "", 0));
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        childSiteSettings.setPrice250mb(100);
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2110, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        childSiteSettings.setSitePaymentSettings(sitePaymentSettings);

        childSiteSettings.getChildSiteRegistration().setUseOwnAuthorize(true);
        childSiteSettings.getChildSiteRegistration().setUseOwnPaypal(true);
        parentSite.setIncomeSettings(new IncomeSettings());


        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(childSiteSettings.getChildSiteSettingsId(), PaymentSettingsOwnerType.CHILD_SITE_SETTINGS,
                ChargeType.SITE_250MB_MONTHLY_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();
        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_PENDING, state);
        Assert.assertEquals(childSiteSettings.getPrice250mb(), state.getPrice());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings());
        Assert.assertEquals(childSiteSettings.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSiteSettings.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_250MB_MONTHLY_FEE, childSiteSettings.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, childSiteSettings.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(creditCard, childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, childSiteSettings.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(childSiteSettings.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertTrue("We don`t add idfference to parent sites income setting when using test credit card",
                parentSite.getIncomeSettings().getSum() == 0);

        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date());
        expectedExpirationDate.set(Calendar.DAY_OF_MONTH, (expectedExpirationDate.get(Calendar.DAY_OF_MONTH) + 30));
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(childSiteSettings.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreate_forSuspendedChildSiteSettingsWithAuthorizeNet() {

        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site parentSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        childSiteSettings.setPrice250mb(100);
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.SUSPENDED);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should not be changed for this state
        sitePaymentSettings.setRemainingTimeOfUsage((10 * 24 * 60 * 60 * 1000L));// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2110, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        childSiteSettings.setSitePaymentSettings(sitePaymentSettings);

        childSiteSettings.getChildSiteRegistration().setUseOwnAuthorize(true);
        childSiteSettings.getChildSiteRegistration().setUseOwnPaypal(true);
        parentSite.setIncomeSettings(new IncomeSettings());


        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(childSiteSettings.getChildSiteSettingsId(), PaymentSettingsOwnerType.CHILD_SITE_SETTINGS,
                ChargeType.SITE_250MB_MONTHLY_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();
        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_SUSPENDED, state);
        Assert.assertEquals(childSiteSettings.getPrice250mb(), state.getPrice());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings());
        Assert.assertEquals(childSiteSettings.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSiteSettings.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_250MB_MONTHLY_FEE, childSiteSettings.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, childSiteSettings.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, childSiteSettings.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(childSiteSettings.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertTrue(parentSite.getIncomeSettings().getSum() == 0);

        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date());
        expectedExpirationDate.set(Calendar.DAY_OF_MONTH, (expectedExpirationDate.get(Calendar.DAY_OF_MONTH) + 10));
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(childSiteSettings.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }
    /*-----------------------------------------ChildSiteSettings with AuthorizeNet------------------------------------------*/

    /*-----------------------------------------ChildSiteSettings with Paypal------------------------------------------*/

    @Test
    public void testCreate_forActiveChildSiteSettingsWithPaypal() {
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site parentSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        childSiteSettings.setPrice250mb(100);
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.AUTHORIZE_NET);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2010, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        childSiteSettings.setSitePaymentSettings(sitePaymentSettings);

        childSiteSettings.getChildSiteRegistration().setUseOwnAuthorize(true);
        childSiteSettings.getChildSiteRegistration().setUseOwnPaypal(true);
        parentSite.setIncomeSettings(new IncomeSettings());

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(childSiteSettings.getChildSiteSettingsId(), PaymentSettingsOwnerType.CHILD_SITE_SETTINGS,
                ChargeType.SITE_250MB_MONTHLY_FEE, PaymentMethod.PAYPAL, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();
        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_ACTIVE, state);
        Assert.assertEquals(childSiteSettings.getPrice250mb(), state.getPrice());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings());
        Assert.assertEquals(childSiteSettings.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSiteSettings.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_250MB_MONTHLY_FEE, childSiteSettings.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.PAYPAL, childSiteSettings.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, childSiteSettings.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(childSiteSettings.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings().getRecurringPaymentId());
//        Assert.assertEquals(PayPalRecurringProfileStatus.ACTIVE, ((PayPal) PaymentSystem.newInstance(PaymentMethod.PAYPAL)).getProfileStatus(childSiteSettings.getSitePaymentSettings().getRecurringPaymentId()));
        Assert.assertTrue(parentSite.getIncomeSettings().getSum() == 0);

        final Calendar expectedExpirationDate = new GregorianCalendar(2010, 2, 1, 0, 0);
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(childSiteSettings.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
//        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreate_forPendingChildSiteSettingsWithPaypal() {
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site parentSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        childSiteSettings.setPrice250mb(100);
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.AUTHORIZE_NET);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2110, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        childSiteSettings.setSitePaymentSettings(sitePaymentSettings);

        childSiteSettings.getChildSiteRegistration().setUseOwnAuthorize(true);
        childSiteSettings.getChildSiteRegistration().setUseOwnPaypal(true);
        parentSite.setIncomeSettings(new IncomeSettings());

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(childSiteSettings.getChildSiteSettingsId(), PaymentSettingsOwnerType.CHILD_SITE_SETTINGS,
                ChargeType.SITE_250MB_MONTHLY_FEE, PaymentMethod.PAYPAL, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();
        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_PENDING, state);
        Assert.assertEquals(childSiteSettings.getPrice250mb(), state.getPrice());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings());
        Assert.assertEquals(childSiteSettings.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSiteSettings.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_250MB_MONTHLY_FEE, childSiteSettings.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.PAYPAL, childSiteSettings.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, childSiteSettings.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(childSiteSettings.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings().getRecurringPaymentId());
//        Assert.assertEquals(PayPalRecurringProfileStatus.ACTIVE, ((PayPal) PaymentSystem.newInstance(PaymentMethod.PAYPAL)).getProfileStatus(childSiteSettings.getSitePaymentSettings().getRecurringPaymentId()));
        Assert.assertTrue(parentSite.getIncomeSettings().getSum() == 0);

        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date());
        expectedExpirationDate.set(Calendar.DAY_OF_MONTH, (expectedExpirationDate.get(Calendar.DAY_OF_MONTH) + 30));
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(childSiteSettings.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }   
    /*-----------------------------------------ChildSiteSettings with Paypal------------------------------------------*/


    /*------------------------------------Site with ChildSiteSettings with Javien-------------------------------------*/

    @Test
    public void testCreate_forActiveSiteWithChildSiteSettingsWithJavien() {
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site parentSite = TestUtil.createSite();
        final Site childSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, childSite);
        childSiteSettings.setPrice250mb(100);
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2010, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        childSite.setSitePaymentSettings(sitePaymentSettings);

        childSiteSettings.getChildSiteRegistration().setUseOwnAuthorize(true);
        childSiteSettings.getChildSiteRegistration().setUseOwnPaypal(true);
        parentSite.setIncomeSettings(new IncomeSettings());

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(childSite.getSiteId(), PaymentSettingsOwnerType.SITE,
                ChargeType.SITE_250MB_MONTHLY_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();

        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_ACTIVE, state);
        Assert.assertEquals(childSiteSettings.getPrice250mb(), state.getPrice());
        Assert.assertNotNull(childSite.getSitePaymentSettings());
        Assert.assertEquals(childSite.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSite.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_250MB_MONTHLY_FEE, childSite.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, childSite.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNotNull(childSite.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(creditCard, childSite.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, childSite.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(childSite.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(childSite.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertTrue(parentSite.getIncomeSettings().getSum() == 0);

        final Calendar expectedExpirationDate = new GregorianCalendar(2010, 2, 1, 0, 0);
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(childSite.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
    }
}
