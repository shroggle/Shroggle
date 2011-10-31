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

/**
 * @author Balakirev Anatoliy
 */
public class CreditCardTypeManager {

    public CreditCardTypeManager(CreditCardType creditCardType) {
        if (creditCardType == null) {
            throw new IllegalArgumentException("Unable to create CreditCardTypeManager without CreditCardType.");
        }
        this.creditCardType = creditCardType;
    }

    public int getCreditCardValidatorValue() {
        switch (creditCardType) {
            case AMERICAN_EXPRESS: {
                return CreditCardValidator.AMEX;
            }
            case DISCOVER: {
                return CreditCardValidator.DISCOVER;
            }
            case MASTER_CARD: {
                return CreditCardValidator.MASTERCARD;
            }
            case VISA: {
                return CreditCardValidator.VISA;
            }
            default: {
                throw new IllegalArgumentException("Unknown CreditCardType = " + creditCardType);
            }
        }
    }

    private final CreditCardType creditCardType;
}
