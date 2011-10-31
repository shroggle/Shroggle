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
package com.shroggle.logic.creditCard;

import com.shroggle.entity.CreditCard;
import com.shroggle.entity.Site;
import com.shroggle.exception.CreditCardNotFoundException;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.process.synchronize.SynchronizeRequest;
import com.shroggle.util.process.synchronize.SynchronizeRequestEntity;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.SynchronizeContext;
import com.shroggle.util.config.Config;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
public class CreditCardManager {

    public CreditCardManager(CreditCard creditCard) {
        if (creditCard == null) {
            throw new CreditCardNotFoundException("Cant create CreditCardManager by null CreditCard.");
        }
        this.creditCard = creditCard;
    }

    public List<Site> getSitesConnectedToCreditCard() {
        return ServiceLocator.getPersistance().getSitesConnectedToCreditCard(creditCard.getCreditCardId());
    }


    public boolean isCreditCardDueToExpireOrExpired(final Date currentDate) {
        Config config = ServiceLocator.getConfigStorage().get();
        final int notifyAboutCCExpireTime = config.getBillingInfoProperties().getCcExpireNotifyTime();
        Calendar currentDateCalendar = new GregorianCalendar();
        currentDateCalendar.setTime(currentDate);
        currentDateCalendar.set(Calendar.MINUTE, currentDateCalendar.get(Calendar.MINUTE) + notifyAboutCCExpireTime);

        Calendar expirationDateCalendar = new GregorianCalendar();
        expirationDateCalendar.setTime(createExpirationDatePlusOneMonth());

        final Date currentTime = currentDateCalendar.getTime();
        final Date expirationTime = expirationDateCalendar.getTime();
        return expirationTime.before(currentTime) || expirationTime.equals(currentTime);
    }


    public Date createExpirationDatePlusOneMonth() {
        final int year = creditCard.getExpirationYear();
        final int month = creditCard.getExpirationMonth();
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month + 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public Date createRealExpirationDate() {
        final int year = creditCard.getExpirationYear();
        final int month = creditCard.getExpirationMonth();
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    public boolean isNotificationMessageSent() {
        return creditCard.getNotificationMailSent() != null;
    }

    public void setNotificationMessageSent(final Date messageSentDate) {
        if (creditCard != null) {
            try {
                final SynchronizeRequest synchronizeRequest = new SynchronizeRequestEntity(
                        CreditCard.class, SynchronizeMethod.WRITE, creditCard.getCreditCardId());
                ServiceLocator.getSynchronize().execute(synchronizeRequest, new SynchronizeContext<Void>() {
                    public Void execute() {
                        ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
                            public void run() {
                                creditCard.setNotificationMailSent(messageSentDate);
                            }
                        });
                        return null;
                    }
                });
            } catch (Exception exception) {
                Logger.getLogger(CreditCardManager.class.getName()).log(Level.SEVERE, "Can't update notificationMailSent field! CreditCard id = " +
                        creditCard.getCreditCardId(), exception);
            }
        }
    }

    private final CreditCard creditCard;

    public static String getTestCreditCardNumber() {
        return TEST_CREDIT_CARD_NUMBER;
    }

    private final static String TEST_CREDIT_CARD_NUMBER = "4444333322221111";
}
