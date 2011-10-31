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
package com.shroggle.util.payment.authorize;

import com.shroggle.entity.CreditCard;
import com.shroggle.entity.PaymentReason;
import com.shroggle.entity.PaymentSettingsOwner;
import com.shroggle.exception.AuthorizeNetException;

/**
 * @author Balakirev Anatoliy
 */
public class AuthorizeNetWithExceptionsInMethods extends AuthorizeNet {
    void purchase(final CreditCard card, final PaymentSettingsOwner owner, final double price,
                  final PaymentReason paymentReason, final Integer userId) {
        throw new AuthorizeNetException();
    }
}