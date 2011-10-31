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
package com.shroggle.logic.site.taxRates;

import com.shroggle.entity.*;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.util.DoubleUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

/**
 * @author dmitry.solomadin
 */
public class TaxManager {

    // Returns NO_TAXES_APPLIED if siteId is null or
    //                             there is no tax item in filled form or
    //                             all tax rates are disabled
    //                             there is no state field in users registration form.
    // Returns BEFORE_STATE_TAX if there is no logined user.
    // Returns NORMAL and tax value if all is ok.

    public CalculateTaxResponse calculateTaxForRender(final FilledForm filledForm, final double initialPrice, final Integer quantity, final Integer siteId) {
        if (siteId == null || filledForm == null) {
            return new CalculateTaxResponse();
        }
        //Check if we got tax rates filled item in form.
        final FilledFormItem taxFilledItem = FilledFormManager.getFilledFormItemByFormItemName(
                filledForm.getFilledFormItems(), FormItemName.PRODUCT_TAX_RATE);
        DraftTaxRatesUS taxRates = persistance.getDraftItem(taxFilledItem != null ? taxFilledItem.getIntValue() : null);
        if (taxRates == null) {
            return new CalculateTaxResponse();
        }

        final UsersManager usersManager = new UsersManager();
        if (usersManager.getLoginedUser() == null) {
            return new CalculateTaxResponse();
        }

        // Get user state from user filled registration forms.
        final State state = usersManager.getLogined().getStateEnteredForSite(siteId);
        if (state == null) {
            return new CalculateTaxResponse();
        }

        final TaxRateUSManager taxRate = new TaxRatesUSManager(taxRates).getIncludedTaxRate(state);
        if (taxRate == null) {
            return new CalculateTaxResponse();
        }

        double tax = DoubleUtil.round(initialPrice / 100 * taxRate.getTaxRate(), 2);
        return new CalculateTaxResponse("(plus tax: $<span class='itemTax'>" + (tax * (quantity != null ? quantity : 1)) + "</span>)", tax);
    }

    public static class CalculateTaxResponse {

        private CalculateTaxResponse() {
            this("");
        }

        private CalculateTaxResponse(final String taxString) {
            this(taxString, 0.0);
        }

        private CalculateTaxResponse(final String taxString, final double tax) {
            this.taxString = taxString;
            this.tax = tax;
        }

        public String getTaxString() {
            return taxString;
        }

        public double getTax() {
            return tax;
        }

        // Contains response string that is rendered in E-commerce store and shopping cart. Tax here is powered by qty.
        private final String taxString;

        // Contains pure tax NOT powered by qty.
        private final double tax;

    }

    private final Persistance persistance = ServiceLocator.getPersistance();

}
