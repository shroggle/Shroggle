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
package com.shroggle.util.payment.paypal;

import java.util.Date;

/**
 * @author Balakirev Anatoliy
 */
public class MockRecurringProfile {

    public MockRecurringProfile(String recurringProfileId, Date profileStartDate) {
        this.recurringProfileId = recurringProfileId;
        this.profileStartDate = profileStartDate;
        this.status = PayPalRecurringProfileStatus.ACTIVE;
    }

    private PayPalRecurringProfileStatus status;

    private final String recurringProfileId;

    private final Date profileStartDate;

    public Date getProfileStartDate() {
        return profileStartDate;
    }

    public PayPalRecurringProfileStatus getStatus() {
        return status;
    }

    public void setStatus(PayPalRecurringProfileStatus status) {
        this.status = status;
    }

    public String getRecurringProfileId() {
        return recurringProfileId;
    }
}