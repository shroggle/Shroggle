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

package com.shroggle.logic.site.payment;

import com.shroggle.entity.PaymentLog;
import com.shroggle.entity.PaymentMethod;
import com.shroggle.entity.User;
import com.shroggle.entity.CreditCard;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.logic.user.UsersManager;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * @author dmitry.solomadin
 */
public class PaymentLogger {

    public PaymentLog logPayPalTransaction(final TransactionStatus transactionStatus, final PaymentLogRequest paymentLogRequest) {
        return log(PaymentMethod.PAYPAL, transactionStatus, paymentLogRequest);
    }

    public PaymentLog logJavienTransaction(final TransactionStatus transactionStatus, final PaymentLogRequest paymentLogRequest) {
        return log(PaymentMethod.AUTHORIZE_NET, transactionStatus, paymentLogRequest);
    }

    public PaymentLog logAuthorizeNetTransaction(final TransactionStatus transactionStatus, final PaymentLogRequest paymentLogRequest) {
        return log(PaymentMethod.AUTHORIZE_NET, transactionStatus, paymentLogRequest);
    }

    public PaymentLog updateTransactionStatus(final TransactionStatus transactionStatus, final int logId) {
        final PaymentLog paymentLog = persistance.getPaymentLogById(logId);

        if (paymentLog == null) {
            logger.log(Level.SEVERE, "Payment Logger Exception! Cannot find payment log by Id=" + logId);
            return null;
        }

        persistanceTransaction.execute(new Runnable() {
            public void run() {
                paymentLog.setTransactionStatus(transactionStatus);
            }
        });
        return paymentLog;
    }

    public PaymentLog updatePaypalTransactionStatusAndSetProfileId(final TransactionStatus transactionStatus, final String profileId, final int logId) {
        final PaymentLog paymentLog = persistance.getPaymentLogById(logId);

        if (paymentLog == null) {
            logger.log(Level.SEVERE, "Payment Logger Exception! Cannot find payment log by Id=" + logId);
            return null;
        }

        persistanceTransaction.execute(new Runnable() {
            public void run() {
                paymentLog.setTransactionStatus(transactionStatus);
                paymentLog.setRecurringPaymentProfileId(profileId);
            }
        });
        return paymentLog;
    }

    public PaymentLog updateFailedTransaction(final int logId, final String errorMessage) {
        final PaymentLog paymentLog = persistance.getPaymentLogById(logId);

        if (paymentLog == null) {
            logger.log(Level.SEVERE, "Payment Logger Exception! Cannot find payment log by Id=" + logId);
            return null;
        }

        persistanceTransaction.execute(new Runnable() {
            public void run() {
                paymentLog.setTransactionStatus(TransactionStatus.FAILED);
                paymentLog.setErrorMessage(errorMessage);
            }
        });
        return paymentLog;
    }

    protected PaymentLog log(final PaymentMethod paymentMethod, final TransactionStatus transactionStatus,
                             final PaymentLogRequest paymentLogRequest) {
        final PaymentLog paymentLog = new PaymentLog();

        persistanceTransaction.execute(new Runnable() {
            public void run() {
                paymentLog.setMessage(StringUtil.getEmptyOrString(paymentLogRequest.getMessage()));
                paymentLog.setSiteId(paymentLogRequest.getSiteId());
                paymentLog.setRecurringPaymentProfileId(paymentLogRequest.getProfileId());
                paymentLog.setChildSiteSettingsId(paymentLogRequest.getChildSiteSettingsId());
                paymentLog.setSum(paymentLogRequest.getSum());
                paymentLog.setMonthlySum(paymentLogRequest.getMonthlySum());
                paymentLog.setPaymentMethod(paymentMethod);
                paymentLog.setTransactionStatus(transactionStatus);
                paymentLog.setFilledFormId(paymentLogRequest.getFilledFormId());
                final User loginedUser = new UsersManager().getLoginedUser();
                paymentLog.setUserId(paymentLogRequest.getUserId() != null ?
                        paymentLogRequest.getUserId() : (loginedUser != null ? loginedUser.getUserId() : null));
                paymentLog.setErrorMessage(paymentLogRequest.getErrorMessage());
                paymentLog.setPaymentReason(paymentLogRequest.getPaymentReason());

                if (paymentLogRequest.getCreditCard() != null){
                    copyCreditCardProperties(paymentLog, paymentLogRequest.getCreditCard());
                }

                persistance.putPaymentLog(paymentLog);
            }
        });

        return paymentLog;
    }

    private void copyCreditCardProperties(final PaymentLog paymentLog, final CreditCard creditCard){
        paymentLog.setCreditCardId(creditCard.getCreditCardId());
        paymentLog.setCreditCardType(creditCard.getCreditCardType());
        paymentLog.setCreditCardNumber(creditCard.getCreditCardNumber());
        paymentLog.setExpirationYear(creditCard.getExpirationYear());
        paymentLog.setExpirationMonth(creditCard.getExpirationMonth());
        paymentLog.setSecurityCode(creditCard.getSecurityCode());
        paymentLog.setUseContactInfo(creditCard.isUseContactInfo());
        paymentLog.setBillingAddress1(creditCard.getBillingAddress1());
        paymentLog.setBillingAddress2(creditCard.getBillingAddress2());
        paymentLog.setCity(creditCard.getCity());
        paymentLog.setCountry(creditCard.getCountry());
        paymentLog.setRegion(creditCard.getRegion());
        paymentLog.setPostalCode(creditCard.getPostalCode());
    }

    private final Logger logger = Logger.getLogger(PaymentLogger.class.getName());
    private Persistance persistance = ServiceLocator.getPersistance();
    private PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
}
