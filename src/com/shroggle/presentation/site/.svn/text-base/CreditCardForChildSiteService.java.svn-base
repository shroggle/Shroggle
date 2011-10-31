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

package com.shroggle.presentation.site;

import com.shroggle.entity.ChildSiteSettings;
import com.shroggle.entity.PaymentSettingsOwnerType;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import com.shroggle.util.international.InternationalStorage;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.logic.site.billingInfo.CreditCardManager;
import com.shroggle.presentation.site.payment.JavienPaymentInfoRequest;
import com.shroggle.presentation.site.payment.JavienPaymentInfoCreator;
import com.shroggle.exception.ChildSiteSettingsNotFoundException;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.Locale;

/**
 * @author Balakirev Anatoliy
 */
//todo add tests(Tolik)
@RemoteProxy
public class CreditCardForChildSiteService {

    @RemoteMethod
    public AddEditCreditCardResponse execute(final AddEditCreditCardRequest request) throws Exception {
        final ChildSiteSettings settings = persistance.getChildSiteSettingsById(request.getChildSiteSettingsId());
        if (settings == null) {
            throw new ChildSiteSettingsNotFoundException("Can`t create credit card for child site without child site settings.");
        }
        AddEditCreditCardResponse response = manager.addCreditCardInternal(request, persistance.getUserById(settings.getUserId()));
        if (response.getCardValidationErrors().size() > 0) {
            return response;
        }

        final JavienPaymentInfoRequest javienPaymentInfoRequest = new JavienPaymentInfoRequest(
                response.getCreditCard().getCreditCardId(), request.getChargeType(), PaymentSettingsOwnerType.CHILD_SITE_SETTINGS, settings.getChildSiteSettingsId(), request.getChildSiteUserId());

        new JavienPaymentInfoCreator().execute(javienPaymentInfoRequest);
        return response;
    }

    private final CreditCardManager manager = new CreditCardManager();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final InternationalStorage internationalStorage = ServiceLocator.getInternationStorage();
    private final International international = internationalStorage.get("childSiteRegistration", Locale.US);

}