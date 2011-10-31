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

import com.shroggle.entity.*;
import com.shroggle.exception.PaymentSettingsOwnerNotFoundException;
import com.shroggle.exception.JavienException;
import com.shroggle.logic.payment.PaymentInfoCreatorRequest;
import com.shroggle.logic.payment.PaymentInfoCreator;
import com.shroggle.logic.payment.PaymentInfoCreatorState;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.persistance.PersistanceTransactionContext;

import java.util.Locale;

/**
 * @author Balakirev Anatoliy
 */
public class JavienPaymentInfoCreator {

    public PaymentInfoCreatorState execute(final JavienPaymentInfoRequest request) throws Exception {
        return executeInternal(request);
    }

    private PaymentInfoCreatorState executeInternal(final JavienPaymentInfoRequest request) throws Exception {
        final CreditCard card = persistance.getCreditCardById(request.getCardId());
        final PaymentInfoCreatorRequest paymentInfoCreatorRequest = new PaymentInfoCreatorRequest(request.getPaymentSettingsOwnerId(),
                request.getPaymentSettingsOwnerType(), request.getChargeType(), PaymentMethod.AUTHORIZE_NET, request.getUserId(), null, card);
        try {
            return persistanceTransaction.execute(
                    new PersistanceTransactionContext<PaymentInfoCreatorState>() {
                        public PaymentInfoCreatorState execute() {
                            final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(paymentInfoCreatorRequest);
                            return paymentInfoCreator.create();
                        }
                    });            
        } catch (PaymentSettingsOwnerNotFoundException exception) {
            throw new JavienException(international.get("cantUpdateInfoForNullOwnerOrCard"));
        } catch (Exception exception) {
            throw new JavienException(international.get("problemProcessingCard"));
        }
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final International international = ServiceLocator.getInternationStorage().get("updatePaymentInfo", Locale.US);
}
