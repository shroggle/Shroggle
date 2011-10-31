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

import com.shroggle.entity.PaymentMethod;
import com.shroggle.entity.PaymentReason;
import com.shroggle.entity.Site;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.payment.PaymentSystem;
import com.shroggle.util.payment.paypal.PayPal;
import com.shroggle.util.payment.paypal.PayPalPaymentRequest;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
public class IncomeSettingsOwnerMoneySender {

    public static void execute() {
        for (final Site site : ServiceLocator.getPersistance().getSitesWithNotEmptyIncomeSettings()) {
            if (site.getIncomeSettings() != null && site.getIncomeSettings().getSum() > 0) {
                final PayPalPaymentRequest request = new PayPalPaymentRequest();
                request.setEmail(site.getIncomeSettings().getPaypalAddress());
                request.setSum(site.getIncomeSettings().getSum());
                request.setNote(StringUtil.getEmptyOrString(site.getIncomeSettings().getPaymentDetails()));
                request.setSiteId(site.getId());
                try {
                    // Here should be default PayPal because we must send money from our system to child site`s owner,
                    // which decided to use our payment system instead of their own one.
                    ((PayPal) PaymentSystem.newInstance(PaymentMethod.PAYPAL)).massPayment(Arrays.asList(request), PaymentReason.AUTO_PAYMENT);
                    new SiteManager(site).clearIncomeSettings();
                } catch (Exception exception) {
                    Logger.getLogger(IncomeSettingsOwnerMoneySender.class.getName()).log(Level.SEVERE, "Can`t send money for parent site.", exception);
                }
            }
        }
    }
}
