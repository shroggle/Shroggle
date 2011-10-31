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
package com.shroggle.presentation.taxRates;

import com.shroggle.entity.DraftTaxRatesUS;
import com.shroggle.entity.States_US;
import com.shroggle.exception.TaxRatesNameNotUnique;
import com.shroggle.exception.TaxRatesNotFoundException;
import com.shroggle.logic.site.taxRates.TaxRateUSManager;
import com.shroggle.logic.site.taxRates.TaxRatesUSManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameterProperty;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class SaveTaxRatesService {

    @SynchronizeByMethodParameterProperty(
            entityIdPropertyPath = "itemId",
            entityClass = DraftTaxRatesUS.class,
            method = SynchronizeMethod.WRITE)
    @RemoteMethod
    public void execute(final SaveTaxRatesRequest request) {
        new UsersManager().getLogined();
        final DraftTaxRatesUS draftTaxRatesUS = persistance.getDraftItem(request.getItemId());
        if (draftTaxRatesUS == null) {
            throw new TaxRatesNotFoundException();
        }
        final DraftTaxRatesUS duplicateTaxRates = persistance.getTaxRatesByNameAndSiteId(request.getName(), draftTaxRatesUS.getSiteId());
        if (duplicateTaxRates != null && duplicateTaxRates.getId() != request.getItemId()) {
            throw new TaxRatesNameNotUnique();
        }

        persistanceTransaction.execute(new Runnable() {
            @Override
            public void run() {
                final TaxRatesUSManager manager = new TaxRatesUSManager(draftTaxRatesUS);
                manager.setName(request.getName());
                for (TaxRateUSManager fieldManager : manager.getTaxRates()) {
                    fieldManager.setIncluded(false);
                }
                for (States_US state : request.getStatesTaxes().keySet()) {
                     manager.updateTaxByState(state, request.getStatesTaxes().get(state), true);
                }
            }
        });
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
}
