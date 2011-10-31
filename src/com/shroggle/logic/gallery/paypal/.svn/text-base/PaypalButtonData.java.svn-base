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
package com.shroggle.logic.gallery.paypal;

import com.shroggle.entity.FilledFormItem;
import com.shroggle.logic.groups.SubscriptionTimeType;
import com.shroggle.logic.site.taxRates.TaxManager;
import com.shroggle.util.DoubleUtil;

/**
 * @author dmitry.solomadin
 */
public class PaypalButtonData {

    public PaypalButtonData(final FilledFormItem productFilledItem, final FilledFormItem priceFilledItem,
                            final FilledFormItem subscriptionFilledItem, final FilledFormItem groupsFilledItem,
                            final FilledFormItem descriptionFilledItem, final FilledFormItem imageFilledItem,
                            final int filledFormId, final int siteId) {
        this.productFilledItem = productFilledItem;
        this.priceFilledItem = priceFilledItem;
        this.subscriptionFilledItem = subscriptionFilledItem;
        this.groupsFilledItem = groupsFilledItem;
        this.descriptionFilledItem = descriptionFilledItem;
        this.imageFilledItem = imageFilledItem;
        this.filledFormId = filledFormId;
        this.siteId = siteId;
    }

    private final FilledFormItem productFilledItem;

    private final FilledFormItem priceFilledItem;

    private final FilledFormItem subscriptionFilledItem;

    private final FilledFormItem groupsFilledItem;

    private final FilledFormItem descriptionFilledItem;

    private final FilledFormItem imageFilledItem;

    private final int siteId;

    private final int filledFormId;

    public int getFilledFormId() {
        return filledFormId;
    }

    public FilledFormItem getDescriptionFilledItem() {
        return descriptionFilledItem;
    }

    public FilledFormItem getImageFilledItem() {
        return imageFilledItem;
    }

    public Integer getProductFilledItemId() {
        return productFilledItem != null ? productFilledItem.getItemId() : null;
    }

    public String getProductFilledItemValue() {
        return productFilledItem != null ? productFilledItem.getValue() : null;
    }

    public Integer getPriceFilledItemId() {
        return priceFilledItem != null ? priceFilledItem.getItemId() : null;
    }

    public double getPrice() {
        return priceFilledItem != null ? DoubleUtil.safeParse(priceFilledItem.getValue()) : 0.0;
    }

    public boolean isPriceCorrect() {
        return priceFilledItem != null && priceFilledItem.getValue() != null &&
                !priceFilledItem.getValue().isEmpty() && DoubleUtil.isParsable(priceFilledItem.getValue())
                && DoubleUtil.safeParse(priceFilledItem.getValue()) != 0.0;
    }

    public double getTaxSum() {
        return getTaxSum(null);
    }

    public double getTaxSum(final Integer quantity) {
        return new TaxManager().calculateTaxForRender(priceFilledItem.getFilledForm(), getPrice(), quantity, siteId).getTax();
    }

    public String getTaxString(final Integer quantity) {
        return new TaxManager().calculateTaxForRender(priceFilledItem.getFilledForm(), getPrice(), quantity, siteId).getTaxString();
    }

    public Integer getSubscriptionFilledItemId() {
        return subscriptionFilledItem != null ? subscriptionFilledItem.getItemId() : null;
    }

    public String getSubscriptionFilledItemValue() {
        return subscriptionFilledItem != null ? subscriptionFilledItem.getValue() : null;
    }

    public Integer getGroupsFilledItemId() {
        return groupsFilledItem != null ? groupsFilledItem.getItemId() : null;
    }

    public boolean isRecurrent() {
        return subscriptionFilledItem != null && !subscriptionFilledItem.getValue().isEmpty() &&
                SubscriptionTimeType.valueOf(subscriptionFilledItem.getValue()) != SubscriptionTimeType.INDEFINITE;
    }

    public int getSiteId() {
        return siteId;
    }
}
