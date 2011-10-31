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

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import com.shroggle.entity.User;
import com.shroggle.entity.CreditCard;
import com.shroggle.entity.Site;
import com.shroggle.TestUtil;
import com.shroggle.TestRunnerWithMockServices;

import java.util.Date;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class CreditCardDataManagerTest {

    @Test
    public void testCreateCreditCardData() {
        User user = TestUtil.createUser();
        Site site = TestUtil.createSite();
        CreditCard creditCard1 = TestUtil.createCreditCard(new Date(), user);
        creditCard1.setCreditCardNumber("1234567891234567");
        CreditCard creditCard2 = TestUtil.createCreditCard(new Date(), user);
        creditCard2.setCreditCardNumber("9876543219876543");
        CreditCard creditCard3 = TestUtil.createCreditCard(new Date(), site);
        creditCard3.setCreditCardNumber("4111111111111111");

        final List<CreditCardData> creditCardData = CreditCardDataManager.createCreditCardData(user, site);
        Assert.assertEquals(3, creditCardData.size());
        Assert.assertEquals(creditCard1.getCreditCardId(), creditCardData.get(0).getCreditCardId());
        Assert.assertEquals(creditCard2.getCreditCardId(), creditCardData.get(1).getCreditCardId());
        Assert.assertEquals(creditCard3.getCreditCardId(), creditCardData.get(2).getCreditCardId());

        Assert.assertEquals(true, creditCardData.get(0).isOwner());
        Assert.assertEquals(true, creditCardData.get(1).isOwner());
        Assert.assertEquals(false, creditCardData.get(2).isOwner());
    }

    @Test
    public void testCreateCreditCardData_forOwnerWithActiveSite() {
        User user = TestUtil.createUser();
        Site site = TestUtil.createSite();
        CreditCard creditCard1 = TestUtil.createCreditCard(new Date(), user);
        creditCard1.setCreditCardNumber("1234567891234567");
        site.getSitePaymentSettings().setCreditCard(creditCard1);

        final List<CreditCardData> creditCardData = CreditCardDataManager.createCreditCardData(user, site);
        Assert.assertEquals(1, creditCardData.size());
        Assert.assertEquals(creditCard1.getCreditCardId(), creditCardData.get(0).getCreditCardId());

        Assert.assertEquals(true, creditCardData.get(0).isOwner());
    }

    @Test
    public void testCreateCreditCardData_withoutSite() {
        User user = TestUtil.createUser();
        Site site = TestUtil.createSite();
        CreditCard creditCard1 = TestUtil.createCreditCard(new Date(), user);
        creditCard1.setCreditCardNumber("1234567891234567");
        CreditCard creditCard2 = TestUtil.createCreditCard(new Date(), user);
        creditCard2.setCreditCardNumber("9876543219876543");
        CreditCard creditCard3 = TestUtil.createCreditCard(new Date(), site);
        creditCard3.setCreditCardNumber("4111111111111111");

        final List<CreditCardData> creditCardData = CreditCardDataManager.createCreditCardData(user, null);
        Assert.assertEquals(2, creditCardData.size());
        Assert.assertEquals(creditCard1.getCreditCardId(), creditCardData.get(0).getCreditCardId());
        Assert.assertEquals(creditCard2.getCreditCardId(), creditCardData.get(1).getCreditCardId());

        Assert.assertEquals(true, creditCardData.get(0).isOwner());
        Assert.assertEquals(true, creditCardData.get(1).isOwner());
    }
}
