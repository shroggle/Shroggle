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

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import com.shroggle.entity.*;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;

import java.util.Date;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class PaymentLoggerTest {

    @Test
    public void testLogPayPalTransaction() {
        final PaymentLogRequest paymentLogRequest = new PaymentLogRequest();
        paymentLogRequest.setMessage("message");

        final PaymentLog paymentLog = paymentLogger.logPayPalTransaction(TransactionStatus.SENT_CONFIRMED, paymentLogRequest);
        Assert.assertEquals(PaymentMethod.PAYPAL, paymentLog.getPaymentMethod());
        Assert.assertEquals(TransactionStatus.SENT_CONFIRMED, paymentLog.getTransactionStatus());
        Assert.assertEquals(paymentLogRequest.getMessage(), paymentLog.getMessage());
    }

    @Test
    public void testLogPayPalTransactionWithUserInRequest() {
        final User user = TestUtil.createUser();
        TestUtil.createUserAndLogin();

        final PaymentLogRequest paymentLogRequest = new PaymentLogRequest();
        paymentLogRequest.setMessage("message");
        paymentLogRequest.setUserId(user.getUserId());

        final PaymentLog paymentLog = paymentLogger.logPayPalTransaction(TransactionStatus.SENT_CONFIRMED, paymentLogRequest);
        Assert.assertEquals(PaymentMethod.PAYPAL, paymentLog.getPaymentMethod());
        Assert.assertEquals(TransactionStatus.SENT_CONFIRMED, paymentLog.getTransactionStatus());
        Assert.assertEquals(paymentLogRequest.getMessage(), paymentLog.getMessage());
        Assert.assertEquals(user.getUserId(), (int) paymentLog.getUserId());
    }

    @Test
    public void testLogJavienTransaction() {
        final PaymentLogRequest paymentLogRequest = new PaymentLogRequest();
        paymentLogRequest.setMessage("message");

        final PaymentLog paymentLog = paymentLogger.logJavienTransaction(TransactionStatus.SENT_CONFIRMED, paymentLogRequest);
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, paymentLog.getPaymentMethod());
        Assert.assertEquals(TransactionStatus.SENT_CONFIRMED, paymentLog.getTransactionStatus());
        Assert.assertEquals(paymentLogRequest.getMessage(), paymentLog.getMessage());
    }

    @Test
    public void testUpdatePaypalTransactionStatus() {
        final PaymentLogRequest paymentLogRequest = new PaymentLogRequest();
        final PaymentLog paymentLog =
                TestUtil.createPaymentLog(PaymentMethod.PAYPAL, TransactionStatus.SENT_UNCONFIRMED, paymentLogRequest);
        paymentLogger.updateTransactionStatus(TransactionStatus.COMPLETED, paymentLog.getLogId());

        Assert.assertEquals(TransactionStatus.COMPLETED, paymentLog.getTransactionStatus());
    }

    @Test
    public void testUpdatePaypalTransactionStatusAndSetProfileId() {
        final PaymentLogRequest paymentLogRequest = new PaymentLogRequest();
        paymentLogRequest.setProfileId("old profile id");
        final PaymentLog paymentLog =
                TestUtil.createPaymentLog(PaymentMethod.PAYPAL, TransactionStatus.SENT_UNCONFIRMED, paymentLogRequest);
        paymentLogger.updatePaypalTransactionStatusAndSetProfileId(TransactionStatus.COMPLETED, "new profile id", paymentLog.getLogId());

        Assert.assertEquals(TransactionStatus.COMPLETED, paymentLog.getTransactionStatus());
        Assert.assertEquals("new profile id", paymentLog.getRecurringPaymentProfileId());
    }

    @Test
    public void testUpdateFailedTransaction() {
        final PaymentLogRequest paymentLogRequest = new PaymentLogRequest();
        paymentLogRequest.setErrorMessage("error message");
        final PaymentLog paymentLog =
                TestUtil.createPaymentLog(PaymentMethod.PAYPAL, TransactionStatus.SENT_UNCONFIRMED, paymentLogRequest);
        paymentLogger.updateFailedTransaction(paymentLog.getLogId(), "new error message");

        Assert.assertEquals(TransactionStatus.FAILED, paymentLog.getTransactionStatus());
        Assert.assertEquals("new error message", paymentLog.getErrorMessage());
    }
    
    @Test
    public void testUpdatePaypalTransactionStatus_withNotFoundLog() {
        // All goes without exceptions.
        paymentLogger.updateTransactionStatus(TransactionStatus.COMPLETED, 0);
    }

    @Test
    public void testUpdatePaypalTransactionStatusAndSetProfileId_withNotFoundLog() {
        // All goes without exceptions.
        paymentLogger.updatePaypalTransactionStatusAndSetProfileId(TransactionStatus.COMPLETED, "new profile id", 0);
    }

    @Test
    public void testUpdateFailedTransaction_withNotFoundLog() {
        // All goes without exceptions.
        paymentLogger.updateFailedTransaction(0, "new error message");
    }

    @Test
    public void log() {
        final PaymentLogRequest paymentLogRequest = new PaymentLogRequest();
        final Site site = TestUtil.createSite();
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), site);
        paymentLogRequest.setMessage("message");
        paymentLogRequest.setChildSiteSettingsId(1);
        paymentLogRequest.setCreditCard(creditCard);
        paymentLogRequest.setErrorMessage("emessage");
        paymentLogRequest.setMonthlySum("35");
        paymentLogRequest.setPaymentPeriod("period");
        paymentLogRequest.setProfileId("profileId");
        paymentLogRequest.setProfileStartDate("start date");
        paymentLogRequest.setSiteId(3);
        paymentLogRequest.setSum("45");

        final PaymentLog paymentLog = paymentLogger.log(PaymentMethod.PAYPAL, TransactionStatus.SENT_CONFIRMED, paymentLogRequest);
        Assert.assertEquals(PaymentMethod.PAYPAL, paymentLog.getPaymentMethod());
        Assert.assertEquals(TransactionStatus.SENT_CONFIRMED, paymentLog.getTransactionStatus());
        Assert.assertEquals(paymentLogRequest.getMessage(), paymentLog.getMessage());
        Assert.assertEquals(paymentLogRequest.getChildSiteSettingsId(), paymentLog.getChildSiteSettingsId());
        Assert.assertEquals(paymentLogRequest.getCreditCard().getCreditCardId(), (int) paymentLog.getCreditCardId());
        Assert.assertEquals(paymentLogRequest.getCreditCard().getCreditCardType(), paymentLog.getCreditCardType());
        Assert.assertEquals(paymentLogRequest.getCreditCard().getCreditCardNumber(), paymentLog.getCreditCardNumber());
        Assert.assertEquals(paymentLogRequest.getCreditCard().getExpirationYear(), paymentLog.getExpirationYear());
        Assert.assertEquals(paymentLogRequest.getCreditCard().getExpirationMonth(), paymentLog.getExpirationMonth());
        Assert.assertEquals(paymentLogRequest.getCreditCard().getSecurityCode(), paymentLog.getSecurityCode());
        Assert.assertEquals(paymentLogRequest.getCreditCard().isUseContactInfo(), paymentLog.isUseContactInfo());
        Assert.assertEquals(paymentLogRequest.getCreditCard().getBillingAddress1(), paymentLog.getBillingAddress1());
        Assert.assertEquals(paymentLogRequest.getCreditCard().getBillingAddress2(), paymentLog.getBillingAddress2());
        Assert.assertEquals(paymentLogRequest.getCreditCard().getCity(), paymentLog.getCity());
        Assert.assertEquals(paymentLogRequest.getCreditCard().getCountry(), paymentLog.getCountry());
        Assert.assertEquals(paymentLogRequest.getCreditCard().getRegion(), paymentLog.getRegion());
        Assert.assertEquals(paymentLogRequest.getCreditCard().getPostalCode(), paymentLog.getPostalCode());
        Assert.assertEquals(paymentLogRequest.getErrorMessage(), paymentLog.getErrorMessage());
        Assert.assertEquals(paymentLogRequest.getMonthlySum(), paymentLog.getMonthlySum());
//        Assert.assertEquals(paymentLogRequest.getPaymentPeriod(), paymentLog.getPaymentPeriod());
        Assert.assertEquals(paymentLogRequest.getProfileId(), paymentLog.getRecurringPaymentProfileId());
//        Assert.assertEquals(paymentLogRequest.getProfileStartDate(), paymentLog.getProfileStartDate());
        Assert.assertEquals(paymentLogRequest.getSiteId(), paymentLog.getSiteId());
        Assert.assertEquals(paymentLogRequest.getSum(), paymentLog.getSum());
    }

    final PaymentLogger paymentLogger = new PaymentLogger();

}
