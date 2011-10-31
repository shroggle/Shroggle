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

import com.shroggle.entity.CreditCardType;
import org.apache.commons.validator.CreditCardValidator;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Balakirev Anatoliy
 */
public class CreditCardTypeManagerTest {

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithoutCreditCardType() throws Exception {
        new CreditCardTypeManager(null);
    }

    @Test
    public void testGetCreditCardValidatorValue() throws Exception {
        Assert.assertEquals(CreditCardValidator.AMEX, new CreditCardTypeManager(CreditCardType.AMERICAN_EXPRESS).getCreditCardValidatorValue());
        Assert.assertEquals(CreditCardValidator.DISCOVER, new CreditCardTypeManager(CreditCardType.DISCOVER).getCreditCardValidatorValue());
        Assert.assertEquals(CreditCardValidator.MASTERCARD, new CreditCardTypeManager(CreditCardType.MASTER_CARD).getCreditCardValidatorValue());
        Assert.assertEquals(CreditCardValidator.VISA, new CreditCardTypeManager(CreditCardType.VISA).getCreditCardValidatorValue());
    }
}
