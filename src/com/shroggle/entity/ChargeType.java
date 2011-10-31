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

package com.shroggle.entity;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.EnumConverter;

/**
 * @author Balakirev Anatoliy
 */
@DataTransferObject(converter = EnumConverter.class)
public enum ChargeType {

    SITE_ANNUAL_FEE(null, PaymentPeriod.YEAR),
    SITE_MONTHLY_FEE(null, PaymentPeriod.MONTH),
    SITE_ONE_TIME_FEE(null, PaymentPeriod.MONTH),
    SITE_250MB_MONTHLY_FEE(250, PaymentPeriod.MONTH),
    SITE_500MB_MONTHLY_FEE(500, PaymentPeriod.MONTH),
    SITE_1GB_MONTHLY_FEE(1024, PaymentPeriod.MONTH),
    SITE_3GB_MONTHLY_FEE(3072, PaymentPeriod.MONTH);

    ChargeType(Integer spaceAcmount, PaymentPeriod paymentPeriod) {
        this.spaceAmount = spaceAcmount;
        this.paymentPeriod = paymentPeriod;
    }

    public int getSpaceAmount() {
        return spaceAmount;
    }

    public PaymentPeriod getPaymentPeriod() {
        return paymentPeriod;
    }

    private final Integer spaceAmount;
    private final PaymentPeriod paymentPeriod;

}