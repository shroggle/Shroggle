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

package com.shroggle.util.payment.javien;

import com.shroggle.entity.*;
import com.shroggle.exception.JavienException;
import com.shroggle.util.payment.javien.Javien;

public class JavienMock extends Javien {

    void purchaseProduct(final CreditCard card, final String productName, final PaymentSettingsOwner owner,
                         final double price, final PaymentReason paymentReason, final Integer userId) {
        if (wrongCreditCard) {
            throw new JavienException("Wrong Credit Card.");
        }
    }

    String getExistingProductNameByPriceOrCreateNew(final double sum) {
        return "";
    }

    public boolean isWrongCreditCard() {
        return wrongCreditCard;
    }

    public void setWrongCreditCard(boolean wrongCreditCard) {
        this.wrongCreditCard = wrongCreditCard;
    }


    private boolean wrongCreditCard = false;
}