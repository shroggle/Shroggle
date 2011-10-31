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
package com.shroggle.logic.creditCard;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.Country;
import com.shroggle.entity.CreditCard;
import com.shroggle.entity.CreditCardType;
import com.shroggle.entity.Site;
import com.shroggle.exception.CreditCardNotFoundException;
import com.shroggle.util.ServiceLocator;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class CreditCardManagerTest {

    @Test
    public void createCreditCard(){
        CreditCard creditCard = new CreditCard();
        Assert.assertEquals(CreditCardType.VISA, creditCard.getCreditCardType());
        Assert.assertEquals("", creditCard.getCreditCardNumber());
        Assert.assertEquals(-1, creditCard.getExpirationYear());
        Assert.assertEquals(-1, creditCard.getExpirationMonth());
        Assert.assertEquals("", creditCard.getSecurityCode());
        Assert.assertEquals(false, creditCard.isUseContactInfo());
        Assert.assertEquals("", creditCard.getBillingAddress1());
        Assert.assertEquals("", creditCard.getBillingAddress2());
        Assert.assertEquals("", creditCard.getCity());
        Assert.assertEquals(Country.US, creditCard.getCountry());
        Assert.assertEquals("", creditCard.getRegion());
        Assert.assertEquals("", creditCard.getPostalCode());
        Assert.assertNull(creditCard.getUser());
        Assert.assertNull(creditCard.getNotificationMailSent());
    }

    @Test(expected = CreditCardNotFoundException.class)
    public void createWithoutCreditCar() {
        new CreditCardManager(null);
    }


    /*---------------------------------------------due to expire in ...-----------------------------------------------*/
    @Test
    public void isCreditCardDueToExpireOrExpired_dueToExpiredInMonth() {
        Calendar currentDateCalendar = new GregorianCalendar();
        currentDateCalendar.set(Calendar.YEAR, 2009);
        currentDateCalendar.set(Calendar.MONTH, Calendar.MAY);
        currentDateCalendar.set(Calendar.DAY_OF_MONTH, 1);
        //notify in 31 days
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setCcExpireNotifyTime(31 * 24 * 60);

        Calendar expirationDateCalendar = new GregorianCalendar();
        expirationDateCalendar.set(Calendar.YEAR, 2009);
        expirationDateCalendar.set(Calendar.MONTH, Calendar.MAY);

        CreditCard creditCard = new CreditCard();
        creditCard.setExpirationYear((short) expirationDateCalendar.get(Calendar.YEAR));
        creditCard.setExpirationMonth((byte) expirationDateCalendar.get(Calendar.MONTH));

        Assert.assertTrue(new CreditCardManager(creditCard).isCreditCardDueToExpireOrExpired(currentDateCalendar.getTime()));
    }


    @Test
    public void isCreditCardDueToExpireOrExpired_dueToExpiredInDay() {
        Calendar currentDateCalendar = new GregorianCalendar();
        currentDateCalendar.set(Calendar.YEAR, 2009);
        currentDateCalendar.set(Calendar.MONTH, Calendar.MAY);
        currentDateCalendar.set(Calendar.DAY_OF_MONTH, 31);
        //notify in 1 day
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setCcExpireNotifyTime(24 * 60);

        Calendar expirationDateCalendar = new GregorianCalendar();
        expirationDateCalendar.set(Calendar.YEAR, 2009);
        expirationDateCalendar.set(Calendar.MONTH, Calendar.MAY);

        CreditCard creditCard = new CreditCard();
        creditCard.setExpirationYear((short) expirationDateCalendar.get(Calendar.YEAR));
        creditCard.setExpirationMonth((byte) expirationDateCalendar.get(Calendar.MONTH));

        Assert.assertTrue(new CreditCardManager(creditCard).isCreditCardDueToExpireOrExpired(currentDateCalendar.getTime()));
    }


    @Test
    public void isCreditCardDueToExpireOrExpired_dueToExpiredInHour() {
        Calendar currentDateCalendar = new GregorianCalendar();
        currentDateCalendar.set(Calendar.YEAR, 2009);
        currentDateCalendar.set(Calendar.MONTH, Calendar.MAY);
        currentDateCalendar.set(Calendar.DAY_OF_MONTH, 31);
        currentDateCalendar.set(Calendar.HOUR_OF_DAY, 23);
        currentDateCalendar.set(Calendar.MINUTE, 0);
        //notify in 1 hour
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setCcExpireNotifyTime(60);

        Calendar expirationDateCalendar = new GregorianCalendar();
        expirationDateCalendar.set(Calendar.YEAR, 2009);
        expirationDateCalendar.set(Calendar.MONTH, Calendar.MAY);

        CreditCard creditCard = new CreditCard();
        creditCard.setExpirationYear((short) expirationDateCalendar.get(Calendar.YEAR));
        creditCard.setExpirationMonth((byte) expirationDateCalendar.get(Calendar.MONTH));

        Assert.assertTrue(new CreditCardManager(creditCard).isCreditCardDueToExpireOrExpired(currentDateCalendar.getTime()));
    }


    @Test
    public void isCreditCardDueToExpireOrExpired_dueToExpiredInMinute() {
        Calendar currentDateCalendar = new GregorianCalendar();
        currentDateCalendar.set(Calendar.YEAR, 2009);
        currentDateCalendar.set(Calendar.MONTH, Calendar.MAY);
        currentDateCalendar.set(Calendar.DAY_OF_MONTH, 31);
        currentDateCalendar.set(Calendar.HOUR_OF_DAY, 23);
        currentDateCalendar.set(Calendar.MINUTE, 59);
        currentDateCalendar.set(Calendar.SECOND, 0);
        //notify in 1 minute
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setCcExpireNotifyTime(1);

        Calendar expirationDateCalendar = new GregorianCalendar();
        expirationDateCalendar.set(Calendar.YEAR, 2009);
        expirationDateCalendar.set(Calendar.MONTH, Calendar.MAY);

        CreditCard creditCard = new CreditCard();
        creditCard.setExpirationYear((short) expirationDateCalendar.get(Calendar.YEAR));
        creditCard.setExpirationMonth((byte) expirationDateCalendar.get(Calendar.MONTH));

        Assert.assertTrue(new CreditCardManager(creditCard).isCreditCardDueToExpireOrExpired(currentDateCalendar.getTime()));
    }

    /*---------------------------------------------due to expire in ...-----------------------------------------------*/

    /*-----------------------------------due to expire in time more than in config------------------------------------*/

    @Test
    public void isCreditCardDueToExpireOrExpired_nonExpiredInMonth() {
        Calendar currentDateCalendar = new GregorianCalendar();
        currentDateCalendar.set(Calendar.YEAR, 2009);
        currentDateCalendar.set(Calendar.MONTH, Calendar.APRIL);
        currentDateCalendar.set(Calendar.DAY_OF_MONTH, 30);
        //notify in 31 days
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setCcExpireNotifyTime(31 * 24 * 60);

        Calendar expirationDateCalendar = new GregorianCalendar();
        expirationDateCalendar.set(Calendar.YEAR, 2009);
        expirationDateCalendar.set(Calendar.MONTH, Calendar.MAY);

        CreditCard creditCard = new CreditCard();
        creditCard.setExpirationYear((short) expirationDateCalendar.get(Calendar.YEAR));
        creditCard.setExpirationMonth((byte) expirationDateCalendar.get(Calendar.MONTH));

        Assert.assertFalse(new CreditCardManager(creditCard).isCreditCardDueToExpireOrExpired(currentDateCalendar.getTime()));
    }


    @Test
    public void isCreditCardDueToExpireOrExpired_nonExpiredInDay() {
        Calendar currentDateCalendar = new GregorianCalendar();
        currentDateCalendar.set(Calendar.YEAR, 2009);
        currentDateCalendar.set(Calendar.MONTH, Calendar.MAY);
        currentDateCalendar.set(Calendar.DAY_OF_MONTH, 30);
        //notify in 1 day
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setCcExpireNotifyTime(24 * 60);

        Calendar expirationDateCalendar = new GregorianCalendar();
        expirationDateCalendar.set(Calendar.YEAR, 2009);
        expirationDateCalendar.set(Calendar.MONTH, Calendar.MAY);

        CreditCard creditCard = new CreditCard();
        creditCard.setExpirationYear((short) expirationDateCalendar.get(Calendar.YEAR));
        creditCard.setExpirationMonth((byte) expirationDateCalendar.get(Calendar.MONTH));

        Assert.assertFalse(new CreditCardManager(creditCard).isCreditCardDueToExpireOrExpired(currentDateCalendar.getTime()));
    }


    @Test
    public void isCreditCardDueToExpireOrExpired_nonExpiredInHour() {
        Calendar currentDateCalendar = new GregorianCalendar();
        currentDateCalendar.set(Calendar.YEAR, 2009);
        currentDateCalendar.set(Calendar.MONTH, Calendar.MAY);
        currentDateCalendar.set(Calendar.DAY_OF_MONTH, 31);
        currentDateCalendar.set(Calendar.HOUR_OF_DAY, 22);
        currentDateCalendar.set(Calendar.MINUTE, 0);
        //notify in 1 hour
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setCcExpireNotifyTime(60);

        Calendar expirationDateCalendar = new GregorianCalendar();
        expirationDateCalendar.set(Calendar.YEAR, 2009);
        expirationDateCalendar.set(Calendar.MONTH, Calendar.MAY);

        CreditCard creditCard = new CreditCard();
        creditCard.setExpirationYear((short) expirationDateCalendar.get(Calendar.YEAR));
        creditCard.setExpirationMonth((byte) expirationDateCalendar.get(Calendar.MONTH));

        Assert.assertFalse(new CreditCardManager(creditCard).isCreditCardDueToExpireOrExpired(currentDateCalendar.getTime()));
    }


    @Test
    public void isCreditCardDueToExpireOrExpired_nonExpiredInMinute() {
        Calendar currentDateCalendar = new GregorianCalendar();
        currentDateCalendar.set(Calendar.YEAR, 2009);
        currentDateCalendar.set(Calendar.MONTH, Calendar.MAY);
        currentDateCalendar.set(Calendar.DAY_OF_MONTH, 31);
        currentDateCalendar.set(Calendar.HOUR_OF_DAY, 23);
        currentDateCalendar.set(Calendar.MINUTE, 58);
        currentDateCalendar.set(Calendar.SECOND, 0);
        //notify in 1 minute
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setCcExpireNotifyTime(1);

        Calendar expirationDateCalendar = new GregorianCalendar();
        expirationDateCalendar.set(Calendar.YEAR, 2009);
        expirationDateCalendar.set(Calendar.MONTH, Calendar.MAY);

        CreditCard creditCard = new CreditCard();
        creditCard.setExpirationYear((short) expirationDateCalendar.get(Calendar.YEAR));
        creditCard.setExpirationMonth((byte) expirationDateCalendar.get(Calendar.MONTH));

        Assert.assertFalse(new CreditCardManager(creditCard).isCreditCardDueToExpireOrExpired(currentDateCalendar.getTime()));
    }
    /*-----------------------------------due to expire in time more than in config------------------------------------*/


    /*---------------------------------------------------expired------------------------------------------------------*/

    @Test
    public void isCreditCardDueToExpireOrExpired_ExpiredYear() {
        Calendar currentDateCalendar = new GregorianCalendar();
        currentDateCalendar.set(Calendar.YEAR, 2009);
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setCcExpireNotifyTime(0);

        Calendar expirationDateCalendar = new GregorianCalendar();
        expirationDateCalendar.set(Calendar.YEAR, 2008);
        expirationDateCalendar.set(Calendar.MONTH, Calendar.MAY);

        CreditCard creditCard = new CreditCard();
        creditCard.setExpirationYear((short) expirationDateCalendar.get(Calendar.YEAR));
        creditCard.setExpirationMonth((byte) expirationDateCalendar.get(Calendar.MONTH));

        Assert.assertTrue(new CreditCardManager(creditCard).isCreditCardDueToExpireOrExpired(currentDateCalendar.getTime()));
    }

    @Test
    public void isCreditCardDueToExpireOrExpired_ExpiredMonth() {
        Calendar currentDateCalendar = new GregorianCalendar();
        currentDateCalendar.set(Calendar.YEAR, 2009);
        currentDateCalendar.set(Calendar.MONTH, Calendar.JULY);
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setCcExpireNotifyTime(0);

        Calendar expirationDateCalendar = new GregorianCalendar();
        expirationDateCalendar.set(Calendar.YEAR, 2009);
        expirationDateCalendar.set(Calendar.MONTH, Calendar.MAY);

        CreditCard creditCard = new CreditCard();
        creditCard.setExpirationYear((short) expirationDateCalendar.get(Calendar.YEAR));
        creditCard.setExpirationMonth((byte) expirationDateCalendar.get(Calendar.MONTH));

        Assert.assertTrue(new CreditCardManager(creditCard).isCreditCardDueToExpireOrExpired(currentDateCalendar.getTime()));
    }


    @Test
    public void isCreditCardDueToExpireOrExpired_ExpiredDay() {
        Calendar currentDateCalendar = new GregorianCalendar();
        currentDateCalendar.set(Calendar.YEAR, 2009);
        currentDateCalendar.set(Calendar.MONTH, Calendar.JUNE);
        currentDateCalendar.set(Calendar.DAY_OF_MONTH, 2);
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setCcExpireNotifyTime(0);

        Calendar expirationDateCalendar = new GregorianCalendar();
        expirationDateCalendar.set(Calendar.YEAR, 2009);
        expirationDateCalendar.set(Calendar.MONTH, Calendar.MAY);

        CreditCard creditCard = new CreditCard();
        creditCard.setExpirationYear((short) expirationDateCalendar.get(Calendar.YEAR));
        creditCard.setExpirationMonth((byte) expirationDateCalendar.get(Calendar.MONTH));

        Assert.assertTrue(new CreditCardManager(creditCard).isCreditCardDueToExpireOrExpired(currentDateCalendar.getTime()));
    }


    @Test
    public void isCreditCardDueToExpireOrExpired_ExpiredHour() {
        Calendar currentDateCalendar = new GregorianCalendar();
        currentDateCalendar.set(Calendar.YEAR, 2009);
        currentDateCalendar.set(Calendar.MONTH, Calendar.JUNE);
        currentDateCalendar.set(Calendar.DAY_OF_MONTH, 1);
        currentDateCalendar.set(Calendar.HOUR_OF_DAY, 1);
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setCcExpireNotifyTime(0);

        Calendar expirationDateCalendar = new GregorianCalendar();
        expirationDateCalendar.set(Calendar.YEAR, 2009);
        expirationDateCalendar.set(Calendar.MONTH, Calendar.MAY);

        CreditCard creditCard = new CreditCard();
        creditCard.setExpirationYear((short) expirationDateCalendar.get(Calendar.YEAR));
        creditCard.setExpirationMonth((byte) expirationDateCalendar.get(Calendar.MONTH));

        Assert.assertTrue(new CreditCardManager(creditCard).isCreditCardDueToExpireOrExpired(currentDateCalendar.getTime()));
    }


    @Test
    public void isCreditCardDueToExpireOrExpired_ExpiredMinute() {
        Calendar currentDateCalendar = new GregorianCalendar();
        currentDateCalendar.set(Calendar.YEAR, 2009);
        currentDateCalendar.set(Calendar.MONTH, Calendar.JUNE);
        currentDateCalendar.set(Calendar.DAY_OF_MONTH, 1);
        currentDateCalendar.set(Calendar.HOUR_OF_DAY, 0);
        currentDateCalendar.set(Calendar.MINUTE, 1);
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setCcExpireNotifyTime(0);

        Calendar expirationDateCalendar = new GregorianCalendar();
        expirationDateCalendar.set(Calendar.YEAR, 2009);
        expirationDateCalendar.set(Calendar.MONTH, Calendar.MAY);

        CreditCard creditCard = new CreditCard();
        creditCard.setExpirationYear((short) expirationDateCalendar.get(Calendar.YEAR));
        creditCard.setExpirationMonth((byte) expirationDateCalendar.get(Calendar.MONTH));

        Assert.assertTrue(new CreditCardManager(creditCard).isCreditCardDueToExpireOrExpired(currentDateCalendar.getTime()));
    }
    /*---------------------------------------------------expired------------------------------------------------------*/


    /*-------------------------------------------createExpirationDatePlusOneMonth-------------------------------------------------*/

    @Test
    public void createExpirationDate() {
        Calendar currentDateCalendar = new GregorianCalendar();
        currentDateCalendar.set(Calendar.YEAR, 2009);
        currentDateCalendar.set(Calendar.MONTH, Calendar.JUNE);
        currentDateCalendar.set(Calendar.DAY_OF_MONTH, 1);
        currentDateCalendar.set(Calendar.HOUR_OF_DAY, 0);
        currentDateCalendar.set(Calendar.MINUTE, 0);


        CreditCard creditCard = new CreditCard();
        creditCard.setExpirationYear((short) currentDateCalendar.get(Calendar.YEAR));
        creditCard.setExpirationMonth((byte) currentDateCalendar.get(Calendar.MONTH));

        final Date expirationDate = new CreditCardManager(creditCard).createExpirationDatePlusOneMonth();
        Calendar newCalendar = new GregorianCalendar();
        newCalendar.setTime(expirationDate);

        Assert.assertEquals(newCalendar.get(Calendar.YEAR), currentDateCalendar.get(Calendar.YEAR));
        Assert.assertNotSame(newCalendar.get(Calendar.MONTH), currentDateCalendar.get(Calendar.MONTH));
        Assert.assertEquals(newCalendar.get(Calendar.DAY_OF_MONTH), currentDateCalendar.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(newCalendar.get(Calendar.HOUR_OF_DAY), currentDateCalendar.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(newCalendar.get(Calendar.MINUTE), currentDateCalendar.get(Calendar.MINUTE));


        newCalendar.set(Calendar.MONTH, newCalendar.get(Calendar.MONTH) - 1);
        Assert.assertEquals(newCalendar.get(Calendar.YEAR), currentDateCalendar.get(Calendar.YEAR));
        Assert.assertEquals(newCalendar.get(Calendar.MONTH), currentDateCalendar.get(Calendar.MONTH));
        Assert.assertEquals(newCalendar.get(Calendar.DAY_OF_MONTH), currentDateCalendar.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(newCalendar.get(Calendar.HOUR_OF_DAY), currentDateCalendar.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(newCalendar.get(Calendar.MINUTE), currentDateCalendar.get(Calendar.MINUTE));
    }
    /*-------------------------------------------createExpirationDatePlusOneMonth-------------------------------------------------*/

    /*-------------------------------------------createRealExpirationDate-------------------------------------------------*/
    @Test
    public void createRealExpirationDate() {
        Calendar currentDateCalendar = new GregorianCalendar();
        currentDateCalendar.set(Calendar.YEAR, 2009);
        currentDateCalendar.set(Calendar.MONTH, Calendar.JUNE);
        currentDateCalendar.set(Calendar.DAY_OF_MONTH, 1);
        currentDateCalendar.set(Calendar.HOUR_OF_DAY, 0);
        currentDateCalendar.set(Calendar.MINUTE, 0);


        CreditCard creditCard = new CreditCard();
        creditCard.setExpirationYear((short) currentDateCalendar.get(Calendar.YEAR));
        creditCard.setExpirationMonth((byte) currentDateCalendar.get(Calendar.MONTH));

        final Date expirationDate = new CreditCardManager(creditCard).createRealExpirationDate();
        Calendar newCalendar = new GregorianCalendar();
        newCalendar.setTime(expirationDate);

        Assert.assertEquals(newCalendar.get(Calendar.YEAR), currentDateCalendar.get(Calendar.YEAR));
        Assert.assertEquals(newCalendar.get(Calendar.MONTH), currentDateCalendar.get(Calendar.MONTH));
        Assert.assertEquals(newCalendar.get(Calendar.DAY_OF_MONTH), currentDateCalendar.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(newCalendar.get(Calendar.HOUR_OF_DAY), currentDateCalendar.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(newCalendar.get(Calendar.MINUTE), currentDateCalendar.get(Calendar.MINUTE));
    }
/*-------------------------------------------createRealExpirationDate-------------------------------------------------*/


    @Test
    public void isNotificationMessageSent_false() {
        CreditCard creditCard = new CreditCard();
        creditCard.setNotificationMailSent(null);
        CreditCardManager manager = new CreditCardManager(creditCard);

        Assert.assertFalse(manager.isNotificationMessageSent());

        manager.setNotificationMessageSent(new Date());

        Assert.assertTrue(manager.isNotificationMessageSent());

        manager.setNotificationMessageSent(null);

        Assert.assertFalse(manager.isNotificationMessageSent());
    }

    @Test
    public void isNotificationMessageSent_true() {
        CreditCard creditCard = new CreditCard();
        creditCard.setNotificationMailSent(new Date());

        Assert.assertTrue(new CreditCardManager(creditCard).isNotificationMessageSent());
    }

    @Test
    public void testName() {
        final Site site = TestUtil.createSite();
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), site);

        final Site site2 = TestUtil.createSite();
        site2.getSitePaymentSettings().setCreditCard(creditCard);


        final Site site3 = TestUtil.createSite();

        final List<Site> sites = new CreditCardManager(creditCard).getSitesConnectedToCreditCard();
        Assert.assertEquals(2, sites.size());
        Assert.assertTrue(sites.contains(site));
        Assert.assertTrue(sites.contains(site2));

    }


    @Test
    public void testGetTestCreditCardNumber() {
        Assert.assertEquals("4444333322221111", CreditCardManager.getTestCreditCardNumber());
    }
}
