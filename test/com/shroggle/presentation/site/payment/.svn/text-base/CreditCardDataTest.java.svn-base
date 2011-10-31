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
import com.shroggle.entity.CreditCard;
import com.shroggle.entity.User;
import com.shroggle.entity.CreditCardType;
import com.shroggle.TestRunnerWithMockServices;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class CreditCardDataTest {

    @Test
    public void create_forOwner(){
        User user = new User();
        user.setFirstName("Anatoliy");
        user.setLastName("Balakirev");
        CreditCard creditCard = new CreditCard();
        creditCard.setCreditCardType(CreditCardType.AMERICAN_EXPRESS);
        creditCard.setCreditCardNumber("4444333322221111");
        creditCard.setUser(user);

        CreditCardData creditCardData = new CreditCardData(creditCard, user);
        Assert.assertEquals(true, creditCardData.isOwner());
        Assert.assertEquals(creditCard.getCreditCardId(), creditCardData.getCreditCardId());
        Assert.assertEquals("Anatoliy Balakirev`s American Express  card selected", creditCardData.getCreditCardOwnerName());
        Assert.assertEquals("American Express : XXXX XXXX XXXX 1111", creditCardData.getCreditCardNumber());
    }


    @Test
    public void create_forNotOwner(){
        User user = new User();
        user.setUserId(10);
        user.setFirstName("Anatoliy");
        user.setLastName("Balakirev");
        CreditCard creditCard = new CreditCard();
        creditCard.setCreditCardType(CreditCardType.AMERICAN_EXPRESS);
        creditCard.setCreditCardNumber("4444333322221111");
        creditCard.setUser(user);

        CreditCardData creditCardData = new CreditCardData(creditCard, new User());
        Assert.assertEquals(false, creditCardData.isOwner());
        Assert.assertEquals(creditCard.getCreditCardId(), creditCardData.getCreditCardId());
        Assert.assertEquals("Anatoliy Balakirev`s American Express  card selected", creditCardData.getCreditCardOwnerName());
        Assert.assertEquals("American Express : XXXX XXXX XXXX 1111", creditCardData.getCreditCardNumber());
    }
    
}
