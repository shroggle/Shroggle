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

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;
import com.shroggle.entity.ChargeType;

/**
 * @author Balakirev Anatoliy
 *         Date: 10.09.2009
 */
@DataTransferObject
public class PaypalRequest {

    @RemoteProperty
    private int ownerId;

    @RemoteProperty
    private ChargeType chargeType;

    @RemoteProperty
    private String redirectToUrl;

    @RemoteProperty
    private int pageBreakIndex;

    @RemoteProperty
    private int filledFormToUpdateId;

    @RemoteProperty
    private boolean paymentRequired;

    @RemoteProperty
    private int widgetId;

    @RemoteProperty
    private int totalPageBreaks;

    @RemoteProperty
    private int childSiteUserId;

    @RemoteProperty
    private int settingsId;

    public int getChildSiteUserId() {
        return childSiteUserId;
    }

    public void setChildSiteUserId(int childSiteUserId) {
        this.childSiteUserId = childSiteUserId;
    }

    public int getSettingsId() {
        return settingsId;
    }

    public void setSettingsId(int settingsId) {
        this.settingsId = settingsId;
    }

    public int getTotalPageBreaks() {
        return totalPageBreaks;
    }

    public void setTotalPageBreaks(int totalPageBreaks) {
        this.totalPageBreaks = totalPageBreaks;
    }

    public int getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(int widgetId) {
        this.widgetId = widgetId;
    }

    public boolean isPaymentRequired() {
        return paymentRequired;
    }

    public void setPaymentRequired(boolean paymentRequired) {
        this.paymentRequired = paymentRequired;
    }

    public int getPageBreakIndex() {
        return pageBreakIndex;
    }

    public void setPageBreakIndex(int pageBreakIndex) {
        this.pageBreakIndex = pageBreakIndex;
    }

    public int getFilledFormToUpdateId() {
        return filledFormToUpdateId;
    }

    public void setFilledFormToUpdateId(int filledFormToUpdateId) {
        this.filledFormToUpdateId = filledFormToUpdateId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public ChargeType getChargeType() {
        return chargeType;
    }

    public void setChargeType(ChargeType chargeType) {
        this.chargeType = chargeType;
    }

    public String getRedirectToUrl() {
        return redirectToUrl;
    }

    public void setRedirectToUrl(String redirectToUrl) {
        this.redirectToUrl = redirectToUrl;
    }
}
