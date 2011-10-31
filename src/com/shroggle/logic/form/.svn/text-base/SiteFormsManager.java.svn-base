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
package com.shroggle.logic.form;

import com.shroggle.entity.*;
import com.shroggle.logic.SiteItemsManager;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.List;

/**
 * @author Artem Stasuk
 */
public class SiteFormsManager {

    public SiteFormsManager(final SiteManager site) {
        this.siteId = site.getId();
    }

    public FormLogic createCustomForm(final List<? extends FormItem> items, final String customNamePattern,
                                      final boolean dontUseFirstNumber) {
        return createCustomForm(FormType.CUSTOM_FORM, items, customNamePattern, dontUseFirstNumber);
    }

    public FormLogic createCustomForm(final FormType formType, final List<? extends FormItem> items, final String customNamePattern,
                                      final boolean dontUseFirstNumber) {
        final DraftCustomForm form = new DraftCustomForm();
        form.setSiteId(siteId);
        form.setType(formType);
        form.setName(SiteItemsManager.getNextDefaultName(ItemType.CUSTOM_FORM, siteId, customNamePattern, dontUseFirstNumber));
        persistance.putCustomForm(form);
        for (final FormItem item : items) {
            form.addFormItem((DraftFormItem)item);
            persistance.putFormItem((DraftFormItem)item);
        }
        return new FormLogic(form);
    }

    private int siteId;
    private final Persistance persistance = ServiceLocator.getPersistance();



}
