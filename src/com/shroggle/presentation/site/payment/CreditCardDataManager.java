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

import com.shroggle.entity.User;
import com.shroggle.entity.Site;
import com.shroggle.entity.CreditCard;

import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

/**
 * @author Balakirev Anatoliy
 */
public class CreditCardDataManager {

    public static List<CreditCardData> createCreditCardData(final User loginedUser, final Site site) {
        if (loginedUser == null) {
            return Collections.emptyList();
        }
        final List<CreditCardData> creditCardDataList = new ArrayList<CreditCardData>();
        for (CreditCard creditCard : loginedUser.getCreditCards()) {
            creditCardDataList.add(new CreditCardData(creditCard, loginedUser));
        }
        if (site != null && site.getSitePaymentSettings().getCreditCard() != null && !containsCreditCardData(creditCardDataList, site.getSitePaymentSettings().getCreditCard().getCreditCardId())) {
            creditCardDataList.add(new CreditCardData(site.getSitePaymentSettings().getCreditCard(), loginedUser));
        }
        return creditCardDataList;
    }

    private static boolean containsCreditCardData(final List<CreditCardData> creditCardDataList, final int creditCardId) {
        for (CreditCardData creditCardData : creditCardDataList) {
            if (creditCardData.getCreditCardId() == creditCardId) {
                return true;
            }
        }
        return false;
    }
}
