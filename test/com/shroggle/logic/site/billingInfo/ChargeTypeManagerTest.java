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

import com.shroggle.util.TimeInterval;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import com.shroggle.entity.*;
import com.shroggle.logic.payment.PaymentSettingsOwnerManager;
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.TestUtil;
import com.shroggle.TestRunnerWithMockServices;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.Map;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ChargeTypeManagerTest {
    @Test
    public void testGetPaymentIntervalMillis_SITE_MONTHLY_FEE() {
        final SitePaymentSettings paymentSettings = new SitePaymentSettings();
        paymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        ChargeTypeManager manager = new ChargeTypeManager(paymentSettings.getChargeType());
        Assert.assertEquals(DateUtil.minutesToMilliseconds(MONTH), manager.getPaymentIntervalMillis());
    }

    @Test
    public void testGetPaymentIntervalMillis_SITE_3GB_MONTHLY_FEE() {
        final SitePaymentSettings paymentSettings = new SitePaymentSettings();
        paymentSettings.setChargeType(ChargeType.SITE_3GB_MONTHLY_FEE);
        ChargeTypeManager manager = new ChargeTypeManager(paymentSettings.getChargeType());
        Assert.assertEquals(DateUtil.minutesToMilliseconds(MONTH), manager.getPaymentIntervalMillis());
    }

    @Test
    public void testGetPaymentIntervalMillis_SITE_1GB_MONTHLY_FEE() {
        final SitePaymentSettings paymentSettings = new SitePaymentSettings();
        paymentSettings.setChargeType(ChargeType.SITE_1GB_MONTHLY_FEE);
        ChargeTypeManager manager = new ChargeTypeManager(paymentSettings.getChargeType());
        Assert.assertEquals(DateUtil.minutesToMilliseconds(MONTH), manager.getPaymentIntervalMillis());
    }

    @Test
    public void testGetPaymentIntervalMillis_SITE_500MB_MONTHLY_FEE() {
        final SitePaymentSettings paymentSettings = new SitePaymentSettings();
        paymentSettings.setChargeType(ChargeType.SITE_500MB_MONTHLY_FEE);
        ChargeTypeManager manager = new ChargeTypeManager(paymentSettings.getChargeType());
        Assert.assertEquals(DateUtil.minutesToMilliseconds(MONTH), manager.getPaymentIntervalMillis());
    }

    @Test
    public void testGetPaymentIntervalMillis_SITE_250MB_MONTHLY_FEE() {
        final SitePaymentSettings paymentSettings = new SitePaymentSettings();
        paymentSettings.setChargeType(ChargeType.SITE_250MB_MONTHLY_FEE);
        ChargeTypeManager manager = new ChargeTypeManager(paymentSettings.getChargeType());
        Assert.assertEquals(DateUtil.minutesToMilliseconds(MONTH), manager.getPaymentIntervalMillis());
    }

    @Test
    public void testGetPaymentIntervalMillis_SITE_ANNUAL_FEE() {
        final SitePaymentSettings paymentSettings = new SitePaymentSettings();
        paymentSettings.setChargeType(ChargeType.SITE_ANNUAL_FEE);
        ChargeTypeManager manager = new ChargeTypeManager(paymentSettings.getChargeType());
        Assert.assertEquals(DateUtil.minutesToMilliseconds(YEAR), manager.getPaymentIntervalMillis());
    }

    @Test(expected = IllegalStateException.class)
    public void testGetPaymentIntervalMillis_SITE_ONE_TIME_FEE() {
        final SitePaymentSettings paymentSettings = new SitePaymentSettings();
        paymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);
        ChargeTypeManager manager = new ChargeTypeManager(paymentSettings.getChargeType());
        manager.getPaymentIntervalMillis();
    }

    /*----------------------------------Create New Expiration Date For Active Owner-----------------------------------*/

    @Test
    public void testCreateNewExpirationDateForActiveOwner_siteWithOldExpirationDate() {
        final Calendar currentExpirationDate = new GregorianCalendar(2010, 3, 1, 0, 0);
        final Site site = TestUtil.createSite();
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setExpirationDate(currentExpirationDate.getTime());
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        site.setSitePaymentSettings(sitePaymentSettings);


        final Calendar newExpirationDate = new GregorianCalendar();
        ChargeTypeManager manager = new ChargeTypeManager(site.getSitePaymentSettings().getChargeType());
        newExpirationDate.setTime(manager.createNewExpirationDateForActiveOwner(site.getSitePaymentSettings().getExpirationDate()));


        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date(currentExpirationDate.getTimeInMillis() + DateUtil.minutesToMilliseconds(MONTH)));

        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), newExpirationDate.get(Calendar.YEAR));
        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), newExpirationDate.get(Calendar.MONTH));
        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), newExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreateNewExpirationDateForActiveOwner_siteWithoutOldExpirationDate() {
        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setExpirationDate(null);
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        site.setSitePaymentSettings(sitePaymentSettings);


        final Calendar newExpirationDate = new GregorianCalendar();
        ChargeTypeManager manager = new ChargeTypeManager(site.getSitePaymentSettings().getChargeType());
        newExpirationDate.setTime(manager.createNewExpirationDateForActiveOwner(site.getSitePaymentSettings().getExpirationDate()));


        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date(System.currentTimeMillis() + DateUtil.minutesToMilliseconds(MONTH)));

        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), newExpirationDate.get(Calendar.YEAR));
        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), newExpirationDate.get(Calendar.MONTH));
        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), newExpirationDate.get(Calendar.DAY_OF_MONTH));
    }
    /*----------------------------------Create New Expiration Date For Active Owner-----------------------------------*/

    /*----------------------------------Create New Expiration Date For Pending Owner----------------------------------*/

    @Test
    public void testCreateNewExpirationDateForPendingOwner_siteWithoutOldExpDate() {
        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setExpirationDate(null);
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        site.setSitePaymentSettings(sitePaymentSettings);


        final Calendar newExpirationDate = new GregorianCalendar();
        ChargeTypeManager manager = new ChargeTypeManager(site.getSitePaymentSettings().getChargeType());
        newExpirationDate.setTime(manager.createNewExpirationDateForPendingOwner(new PaymentSettingsOwnerManager(site).getChildSiteSettings()));


        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date(System.currentTimeMillis() + DateUtil.minutesToMilliseconds(MONTH)));

        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), newExpirationDate.get(Calendar.YEAR));
        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), newExpirationDate.get(Calendar.MONTH));
        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), newExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreateNewExpirationDateForPendingOwner_childSiteSettingsWithoutOldExpDate() {
        ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), new Site());
        childSiteSettings.getSitePaymentSettings().setExpirationDate(null);
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        childSiteSettings.setSitePaymentSettings(sitePaymentSettings);


        final Calendar newExpirationDate = new GregorianCalendar();
        ChargeTypeManager manager = new ChargeTypeManager(childSiteSettings.getSitePaymentSettings().getChargeType());
        newExpirationDate.setTime(manager.createNewExpirationDateForPendingOwner(childSiteSettings));
        //new PaymentSettingsOwnerManager(site).getChildSiteSettings()

        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date(System.currentTimeMillis() + DateUtil.minutesToMilliseconds(MONTH)));

        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), newExpirationDate.get(Calendar.YEAR));
        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), newExpirationDate.get(Calendar.MONTH));
        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), newExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreateNewExpirationDateForPendingOwner_childSiteSettingsWithOldExpDate() {
        ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), new Site());
        childSiteSettings.getSitePaymentSettings().setExpirationDate(new Date(System.currentTimeMillis() + 100000000000000000L));
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        childSiteSettings.setSitePaymentSettings(sitePaymentSettings);


        final Calendar newExpirationDate = new GregorianCalendar();
        ChargeTypeManager manager = new ChargeTypeManager(childSiteSettings.getSitePaymentSettings().getChargeType());
        newExpirationDate.setTime(manager.createNewExpirationDateForPendingOwner(childSiteSettings));


        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date(System.currentTimeMillis() + DateUtil.minutesToMilliseconds(MONTH)));

        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), newExpirationDate.get(Calendar.YEAR));
        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), newExpirationDate.get(Calendar.MONTH));
        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), newExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreateNewExpirationDateForPendingOwner_siteWithOldExpDate() {
        final Calendar currentExpirationDate = new GregorianCalendar(2010, 3, 1, 0, 0);
        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setExpirationDate(currentExpirationDate.getTime());
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        site.setSitePaymentSettings(sitePaymentSettings);


        final Calendar newExpirationDate = new GregorianCalendar();
        ChargeTypeManager manager = new ChargeTypeManager(site.getSitePaymentSettings().getChargeType());
        newExpirationDate.setTime(manager.createNewExpirationDateForPendingOwner(new PaymentSettingsOwnerManager(site).getChildSiteSettings()));


        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date(System.currentTimeMillis() + DateUtil.minutesToMilliseconds(MONTH)));

        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), newExpirationDate.get(Calendar.YEAR));
        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), newExpirationDate.get(Calendar.MONTH));
        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), newExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreateNewExpirationDateForPendingOwner_childSiteWithoutOldExpDate() {
        final Site site = TestUtil.createChildSite();
        site.getSitePaymentSettings().setExpirationDate(null);
        site.getSitePaymentSettings().setExpirationDate(null);
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        site.setSitePaymentSettings(sitePaymentSettings);


        final Calendar newExpirationDate = new GregorianCalendar();
        ChargeTypeManager manager = new ChargeTypeManager(site.getSitePaymentSettings().getChargeType());
        newExpirationDate.setTime(manager.createNewExpirationDateForPendingOwner(new PaymentSettingsOwnerManager(site).getChildSiteSettings()));


        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date(System.currentTimeMillis() + DateUtil.minutesToMilliseconds(MONTH)));

        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), newExpirationDate.get(Calendar.YEAR));
        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), newExpirationDate.get(Calendar.MONTH));
        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), newExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreateNewExpirationDateForPendingOwner_childSiteWithOldExpDate() {
        final Calendar currentExpirationDate = new GregorianCalendar(2010, 3, 1, 0, 0);
        final Site site = TestUtil.createChildSite();
        site.getSitePaymentSettings().setExpirationDate(currentExpirationDate.getTime());
        site.getSitePaymentSettings().setExpirationDate(null);
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        site.setSitePaymentSettings(sitePaymentSettings);


        final Calendar newExpirationDate = new GregorianCalendar();
        ChargeTypeManager manager = new ChargeTypeManager(site.getSitePaymentSettings().getChargeType());
        newExpirationDate.setTime(manager.createNewExpirationDateForPendingOwner(new PaymentSettingsOwnerManager(site).getChildSiteSettings()));


        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date(System.currentTimeMillis() + DateUtil.minutesToMilliseconds(MONTH)));

        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), newExpirationDate.get(Calendar.YEAR));
        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), newExpirationDate.get(Calendar.MONTH));
        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), newExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreateNewExpirationDateForPendingOwner_childSite_SITE_ONE_TIME_FEE_withEndDate() {
        final Site site = TestUtil.createChildSite();
        site.getSitePaymentSettings().setExpirationDate(null);
        site.getSitePaymentSettings().setExpirationDate(null);
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);
        site.setSitePaymentSettings(sitePaymentSettings);
        site.getChildSiteSettings().setEndDate(new Date(System.currentTimeMillis() + 100000000000L));


        final Calendar newExpirationDate = new GregorianCalendar();
        ChargeTypeManager manager = new ChargeTypeManager(site.getSitePaymentSettings().getChargeType());
        newExpirationDate.setTime(manager.createNewExpirationDateForPendingOwner(new PaymentSettingsOwnerManager(site).getChildSiteSettings()));


        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date(site.getChildSiteSettings().getEndDate().getTime() + TimeInterval.ONE_DAY.getMillis()));

        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), newExpirationDate.get(Calendar.YEAR));
        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), newExpirationDate.get(Calendar.MONTH));
        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), newExpirationDate.get(Calendar.DAY_OF_MONTH));
    }


    @Test
    public void testCreateNewExpirationDateForPendingOwner_childSiteSettings_SITE_ONE_TIME_FEE_withEndDate() {
        ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), new Site());
        childSiteSettings.getSitePaymentSettings().setExpirationDate(null);
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);
        childSiteSettings.setSitePaymentSettings(sitePaymentSettings);
        childSiteSettings.setEndDate(new Date(System.currentTimeMillis() + 100000000000L));


        final Calendar newExpirationDate = new GregorianCalendar();
        ChargeTypeManager manager = new ChargeTypeManager(childSiteSettings.getSitePaymentSettings().getChargeType());
        newExpirationDate.setTime(manager.createNewExpirationDateForPendingOwner(childSiteSettings));


        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date(childSiteSettings.getEndDate().getTime() + TimeInterval.ONE_DAY.getMillis()));

        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), newExpirationDate.get(Calendar.YEAR));
        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), newExpirationDate.get(Calendar.MONTH));
        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), newExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreateNewExpirationDateForPendingOwner_childSite_SITE_ONE_TIME_FEE_withoutEndDate() {
        final Site site = TestUtil.createChildSite();
        site.getSitePaymentSettings().setExpirationDate(null);
        site.getSitePaymentSettings().setExpirationDate(null);
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);
        site.setSitePaymentSettings(sitePaymentSettings);
        site.getChildSiteSettings().setEndDate(null);


        final Calendar newExpirationDate = new GregorianCalendar();
        ChargeTypeManager manager = new ChargeTypeManager(site.getSitePaymentSettings().getChargeType());
        newExpirationDate.setTime(manager.createNewExpirationDateForPendingOwner(new PaymentSettingsOwnerManager(site).getChildSiteSettings()));


        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date(System.currentTimeMillis() + TimeInterval.ONE_HUNDRED_YEARS.getMillis()));

        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), newExpirationDate.get(Calendar.YEAR));
        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), newExpirationDate.get(Calendar.MONTH));
    }


    @Test
    public void testCreateNewExpirationDateForPendingOwner_childSiteSettings_SITE_ONE_TIME_FEE_withoutEndDate() {
        ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), new Site());
        childSiteSettings.getSitePaymentSettings().setExpirationDate(null);
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);
        childSiteSettings.setSitePaymentSettings(sitePaymentSettings);
        childSiteSettings.setEndDate(null);


        final Calendar newExpirationDate = new GregorianCalendar();
        ChargeTypeManager manager = new ChargeTypeManager(childSiteSettings.getSitePaymentSettings().getChargeType());
        newExpirationDate.setTime(manager.createNewExpirationDateForPendingOwner(childSiteSettings));


        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date(System.currentTimeMillis() + TimeInterval.ONE_HUNDRED_YEARS.getMillis()));

        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), newExpirationDate.get(Calendar.YEAR));
        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), newExpirationDate.get(Calendar.MONTH));
    }

    /*----------------------------------Create New Expiration Date For Pending Owner----------------------------------*/


    /*---------------------------------Create New Expiration Date For Suspended Owner---------------------------------*/

    @Test
    public void testCreateNewExpirationDateForSuspendedOwner_site() {
        final Site site = TestUtil.createChildSite();
        site.getSitePaymentSettings().setExpirationDate(null);
        site.getSitePaymentSettings().setExpirationDate(null);
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.SUSPENDED);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        sitePaymentSettings.setRemainingTimeOfUsage(1000000000000L);
        site.setSitePaymentSettings(sitePaymentSettings);


        final Calendar newExpirationDate = new GregorianCalendar();
        ChargeTypeManager manager = new ChargeTypeManager(site.getSitePaymentSettings().getChargeType());
        newExpirationDate.setTime(manager.createNewExpirationDateForSuspendedOwner(site.getSitePaymentSettings().getRemainingTimeOfUsage()));


        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date(System.currentTimeMillis() + sitePaymentSettings.getRemainingTimeOfUsage().longValue()));

        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), newExpirationDate.get(Calendar.YEAR));
        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), newExpirationDate.get(Calendar.MONTH));
    }

    @Test
    public void testCreateNewExpirationDateForSuspendedOwner_childSiteSettings() {
        ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), new Site());
        childSiteSettings.getSitePaymentSettings().setExpirationDate(null);
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.SUSPENDED);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        sitePaymentSettings.setRemainingTimeOfUsage(1000000000000L);
        childSiteSettings.setSitePaymentSettings(sitePaymentSettings);


        final Calendar newExpirationDate = new GregorianCalendar();
        ChargeTypeManager manager = new ChargeTypeManager(childSiteSettings.getSitePaymentSettings().getChargeType());
        newExpirationDate.setTime(manager.createNewExpirationDateForSuspendedOwner(childSiteSettings.getSitePaymentSettings().getRemainingTimeOfUsage()));


        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date(System.currentTimeMillis() + sitePaymentSettings.getRemainingTimeOfUsage().longValue()));

        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), newExpirationDate.get(Calendar.YEAR));
        junit.framework.Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), newExpirationDate.get(Calendar.MONTH));
    }

    @Test(expected = IllegalStateException.class)
    public void testCreateNewExpirationDateForSuspendedOwner_siteWithoutRemainingTimeOfUsage() {
        final Site site = TestUtil.createChildSite();
        site.getSitePaymentSettings().setExpirationDate(null);
        site.getSitePaymentSettings().setExpirationDate(null);
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.SUSPENDED);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        sitePaymentSettings.setRemainingTimeOfUsage(null);
        site.setSitePaymentSettings(sitePaymentSettings);


        ChargeTypeManager manager = new ChargeTypeManager(site.getSitePaymentSettings().getChargeType());
        manager.createNewExpirationDateForSuspendedOwner(site.getSitePaymentSettings().getRemainingTimeOfUsage());
    }

    @Test(expected = IllegalStateException.class)
    public void testCreateNewExpirationDateForSuspendedOwner_childSiteSettingsWithoutRemainingTimeOfUsage() {
        ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), new Site());
        childSiteSettings.getSitePaymentSettings().setExpirationDate(null);
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.SUSPENDED);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        sitePaymentSettings.setRemainingTimeOfUsage(null);
        childSiteSettings.setSitePaymentSettings(sitePaymentSettings);


        ChargeTypeManager manager = new ChargeTypeManager(childSiteSettings.getSitePaymentSettings().getChargeType());
        manager.createNewExpirationDateForSuspendedOwner(null);
    }

    /*---------------------------------Create New Expiration Date For Suspended Owner---------------------------------*/

    
    @Test
    public void testGetPaymentSumByChargeType() {
        final Map<ChargeType, Double> paymentPrices = ServiceLocator.getConfigStorage().get().getPaymentPrices();
        Assert.assertEquals(paymentPrices.get(ChargeType.SITE_ANNUAL_FEE), new ChargeTypeManager(ChargeType.SITE_ANNUAL_FEE).getPrice(), 1);
        Assert.assertEquals(paymentPrices.get(ChargeType.SITE_MONTHLY_FEE), new ChargeTypeManager(ChargeType.SITE_MONTHLY_FEE).getPrice(), 1);

        Assert.assertEquals(paymentPrices.get(ChargeType.SITE_250MB_MONTHLY_FEE), new ChargeTypeManager(ChargeType.SITE_250MB_MONTHLY_FEE).getPrice(), 1);
        Assert.assertEquals(paymentPrices.get(ChargeType.SITE_500MB_MONTHLY_FEE), new ChargeTypeManager(ChargeType.SITE_500MB_MONTHLY_FEE).getPrice(), 1);
        Assert.assertEquals(paymentPrices.get(ChargeType.SITE_1GB_MONTHLY_FEE), new ChargeTypeManager(ChargeType.SITE_1GB_MONTHLY_FEE).getPrice(), 1);
        Assert.assertEquals(paymentPrices.get(ChargeType.SITE_3GB_MONTHLY_FEE), new ChargeTypeManager(ChargeType.SITE_3GB_MONTHLY_FEE).getPrice(), 1);
        Assert.assertEquals(paymentPrices.get(ChargeType.SITE_ONE_TIME_FEE), new ChargeTypeManager(ChargeType.SITE_ONE_TIME_FEE).getPrice(), 1);
    }

    private final int YEAR = 365 * 24 * 60;
    private final int MONTH = 30 * 24 * 60;
}
