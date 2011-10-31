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

import com.shroggle.entity.Country;
import com.shroggle.entity.CreditCard;
import com.shroggle.entity.User;
import com.shroggle.presentation.site.AddEditCreditCardRequest;
import com.shroggle.presentation.site.AddEditCreditCardResponse;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.TimeInterval;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import org.apache.commons.validator.CreditCardValidator;

import java.util.*;

/**
 * @author Balakirev Anatoliy
 */
//todo refactor, add tests. Tolik
public class CreditCardManager {

    public AddEditCreditCardResponse addCreditCardInternal(final AddEditCreditCardRequest request, final User user) throws Exception {
        final List<String> errors = new ArrayList<String>();
         //Checking security code
        if (!isSecurityCodeValid(request.getSecurityCode())) {
            errors.add(ServiceLocator.getInternationStorage().get("addEditCreditCardInfo", Locale.US).get("wrongCode"));
        }
        final Date expirationDate;
        //Checking expiration date
        if (request.getCreditCardExpirationYear() != null && request.getCreditCardExpirationMonth() != null) {
            final Calendar calendar = new GregorianCalendar(request.getCreditCardExpirationYear(), request.getCreditCardExpirationMonth(), 1);
            expirationDate = calendar.getTime();
            if (expirationDate.before(new Date(System.currentTimeMillis() - TimeInterval.ONE_MONTH.getMillis()))) {
                errors.add(ServiceLocator.getInternationStorage().get("addEditCreditCardInfo", Locale.US).get("wrongDate"));
            }
        } else {
            errors.add(ServiceLocator.getInternationStorage().get("addEditCreditCardInfo", Locale.US).get("wrongDate"));
        }

        final CreditCardValidator creditCardValidator = new CreditCardValidator(new CreditCardTypeManager(request.getCreditCardType()).getCreditCardValidatorValue());
        if (!creditCardValidator.isValid(request.getCreditCardNumber())) {
            errors.add(ServiceLocator.getInternationStorage().get("addEditCreditCardInfo", Locale.US).get("enterValidCreditCardNumber"));
        }

        final AddEditCreditCardResponse response = new AddEditCreditCardResponse();
        response.setCardValidationErrors(errors);
        if (errors.size() > 0) {
            return response;
        }

        persistanceTransaction.execute(new Runnable() {

            public void run() {
                CreditCard card;
                if (request.isUpdateCCInfo()) {
                    card = persistance.getCreditCardById(request.getUpdatedCCId());
                } else {
                    card = new CreditCard();
                }
                if (card == null) {
                    card = new CreditCard();
                    request.setUpdateCCInfo(false);
                }
                card.setExpirationYear(request.getCreditCardExpirationYear());
                card.setExpirationMonth(request.getCreditCardExpirationMonth());
                card.setCreditCardNumber(request.getCreditCardNumber());
                card.setCreditCardType(request.getCreditCardType());
                card.setSecurityCode(request.getSecurityCode());
                card.setBillingAddress1(request.getBillingAddress1() != null ? request.getBillingAddress1() : "");
                card.setBillingAddress2(request.getBillingAddress2() != null ? request.getBillingAddress2() : "");
                card.setCity(request.getCity() != null ? request.getCity() : "");
                card.setCountry(request.getCountry() != null ? request.getCountry() : Country.US);
                card.setPostalCode(request.getPostalCode());
                card.setRegion(request.getRegion() != null ? request.getRegion() : "");
                card.setUseContactInfo(request.isUseInfo());
                card.setNotificationMailSent(null);
                if (!request.isUpdateCCInfo()) {
                    card.setUser(user);
                    persistance.putCreditCard(card);
                    user.addCreditCard(card);
                }
                response.setCreditCard(card);
            }
        });
        return response;
    }

    private boolean isSecurityCodeValid(String securityCode) {
        try {
            Integer.parseInt(securityCode);
            if (securityCode.length() < 3 || securityCode.length() > 4) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
}