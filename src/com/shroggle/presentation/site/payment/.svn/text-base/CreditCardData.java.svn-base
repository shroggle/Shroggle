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

import com.shroggle.entity.CreditCard;
import com.shroggle.entity.User;
import com.shroggle.util.StringUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;

import java.util.Locale;

/**
 * @author Balakirev Anatoliy
 */
public class CreditCardData {

    public CreditCardData(final CreditCard creditCard, final User loginedUser) {
        creditCardId = creditCard.getCreditCardId();

        final User user = creditCard.getUser() != null ? creditCard.getUser() : new User();
        final International international = ServiceLocator.getInternationStorage().get("creditCard", Locale.US);

        creditCardOwnerName = StringUtil.getEmptyOrString(user.getFirstName()) + " " +
                StringUtil.getEmptyOrString(user.getLastName()) + "`s " +
                international.get(creditCard.getCreditCardType().toString()) + " card selected";

        creditCardNumber = international.get(creditCard.getCreditCardType().toString()) + ": XXXX XXXX XXXX " +
                creditCard.getCreditCardNumber().substring(creditCard.getCreditCardNumber().length() - 4);
        owner = loginedUser.getUserId() == user.getUserId();
    }

    private final int creditCardId;

    private final String creditCardOwnerName;

    private final String creditCardNumber;

    private final boolean owner;

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public int getCreditCardId() {
        return creditCardId;
    }

    public String getCreditCardOwnerName() {
        return creditCardOwnerName;
    }

    public boolean isOwner() {
        return owner;
    }

}
