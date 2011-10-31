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
import com.shroggle.exception.*;
import com.shroggle.logic.payment.PaymentInfoCreatorState;
import com.shroggle.logic.payment.PaymentSettingsOwnerManager;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.presentation.site.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import com.shroggle.util.international.InternationalStorage;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.*;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import com.shroggle.logic.site.billingInfo.CreditCardManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.user.UserManager;
import org.directwebremoting.WebContext;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */

@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class AddEditCreditCardService extends ServiceWithExecutePage implements UserCreditCardsInfo {

    @RemoteMethod
    public String showCreditCardWindow(final int cardId) throws IOException, ServletException {
        final UserManager userManager = new UsersManager().getLogined();
        user = userManager.getUser();
        if (cardId != -1) {
            card = persistance.getCreditCardById(cardId);
        }
        return executePage("/payment/addEditCreditCardInfoWindow.jsp");
    }

    @RemoteMethod
    public AddEditCreditCardResponse addCreditCard(final AddEditCreditCardRequest request) throws Exception {
        final UserManager userManager = new UsersManager().getLogined();
        user = userManager.getUser();
        if (user == null) {
            return new AddEditCreditCardResponse();
        }
        AddEditCreditCardResponse response = manager.addCreditCardInternal(request, user);
        if (response.getCardValidationErrors().size() > 0) {
            return response;
        }

        creditCards = user.getCreditCards();
        final WebContext webContext = getContext();
        webContext.getHttpServletRequest().setAttribute("service", this);
        response.setInnerHTML(webContext.forwardToString("/account/creditCardListTable.jsp"));
        International international = ServiceLocator.getInternationStorage().get("creditCard", Locale.US);
        response.setCreditCardNumber(international.get(response.getCreditCard().getCreditCardType().toString()) + ": XXXX XXXX XXXX " + response.getCreditCard().getCreditCardNumber().substring(response.getCreditCard().getCreditCardNumber().length() - 4));
        response.setCreditCardId(String.valueOf(response.getCreditCard().getCreditCardId()));
        return response;
    }

    @SynchronizeByMethodParameter(
            entityClass = CreditCard.class,
            method = SynchronizeMethod.WRITE)
    @RemoteMethod
    public String removeCreditCard(final int creditCardId) throws Exception {
        new UsersManager().getLogined();
        final CreditCard card = persistance.getCreditCardById(creditCardId);
        user = card.getUser();
        persistanceTransaction.execute(new Runnable() {

            public void run() {
                persistance.removeCreditCard(card);
            }

        });
        creditCards = user.getCreditCards();

        return executePage("/account/creditCardListTable.jsp");
    }

    @RemoteMethod
    public String updateSitePaymentInfo(final UpdateSitePaymentInfoRequest request) throws Exception {
        final UserManager userManager = new UsersManager().getLogined();
        final JavienPaymentInfoRequest javienPaymentInfoRequest = new JavienPaymentInfoRequest(request.getCardId(),
                request.getChargeType(), PaymentSettingsOwnerType.SITE, request.getSiteId(), userManager.getUser().getUserId());

        final PaymentInfoCreatorState state = new JavienPaymentInfoCreator().execute(javienPaymentInfoRequest);

        final International international = internationalStorage.get("updatePaymentInfo", Locale.US);
        if (state == PaymentInfoCreatorState.ACTIVETED_ACTIVE || state == PaymentInfoCreatorState.ACTIVETED_PENDING) {
            final WebContext context = getContext();
            context.getHttpServletRequest().setAttribute("paymentAmount", String.valueOf(state.getPrice()));
            context.getHttpServletRequest().setAttribute("siteId", request.getSiteId());
            return context.forwardToString("/payment/paymentCompleted.jsp");
        } else {
            return international.get("creditInfoCardUpdated");
        }
    }

    @RemoteMethod
    public String deactivateSite(final Integer siteId) throws Exception {
        new UsersManager().getLogined();
        final Site site = persistance.getSite(siteId);
        if (site == null) {
            throw new SiteNotFoundException("Can't find site by id = " + siteId);
        }
        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        manager.suspendActivity();
        return "Off Line";
    }

    @RemoteMethod
    public String reactivateSite(final Integer siteId) throws Exception {
        new UsersManager().getLogined();
        final Site site = persistance.getSite(siteId);
        if (site == null) {
            throw new SiteNotFoundException("Can't find site by id = " + siteId);
        }
        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        manager.reactivate();
        final International international = internationalStorage.get("updatePaymentInfo", Locale.US);
        return international.get("siteReactivated");
    }

    public List<CreditCard> getCreditCards() {
        return creditCards;
    }

    public International getInternatinal() {
        return intenational;
    }

    public CreditCard getCard() {
        return card;
    }

    public void setCard(CreditCard card) {
        this.card = card;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private CreditCardManager manager = new CreditCardManager();
    private final InternationalStorage internationalStorage = ServiceLocator.getInternationStorage();
    private final International intenational = internationalStorage.get("creditCardList", Locale.US);
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private List<CreditCard> creditCards = null;
    private CreditCard card;
    private User user;

}