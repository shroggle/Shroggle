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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * @author Balakirev Anatoliy
 */
@DataTransferObject
@Entity(name = "incomeSettings")
public class IncomeSettings {

    public IncomeSettings() {
        sum = 0;
        paypalAddress = "";
        paymentDetails = null;
    }

    @Id
    private int incomeSettingsId;

    @Column(nullable = false, length = 250)
    private String paypalAddress;

    private double sum;

    @Lob
    private String paymentDetails;

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public void addPaymentDetails(String paymentDetails) {
        if (this.paymentDetails == null) {
            this.paymentDetails = paymentDetails;
        } else {
            this.paymentDetails += ";" + paymentDetails;
        }
    }

    public int getIncomeSettingsId() {
        return incomeSettingsId;
    }

    public void setIncomeSettingsId(int incomeSettingsId) {
        this.incomeSettingsId = incomeSettingsId;
    }

    public String getPaypalAddress() {
        return paypalAddress;
    }

    public void setPaypalAddress(String paypalAddress) {
        this.paypalAddress = paypalAddress;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}