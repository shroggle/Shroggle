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
package com.shroggle.logic.purchaseHistory;

import com.shroggle.entity.FilledForm;
import com.shroggle.entity.FilledFormItem;
import com.shroggle.entity.FormItemName;
import com.shroggle.logic.form.FilledFormItemManager;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.form.LinkedFormManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
public class PurchaseHistoryHelper {

    public List<Purchase> getPurchases(final Integer userId, final int siteId) {
        final List<Purchase> purchases = new ArrayList<Purchase>();

        if (userId == null) {
            return purchases;
        }

        for (FilledForm filledForm : persistance.getUserPurchasesOnSite(userId, siteId)) {
            final Purchase purchase = new Purchase();
            purchase.setProductName(FilledFormItemManager.getValue(FilledFormManager.getFilledFormItemByFormItemName(filledForm.getFilledFormItems(), FormItemName.PRODUCT_NAME)));
            purchase.setProductPrice(FilledFormItemManager.getValue(FilledFormManager.getFilledFormItemByFormItemName(filledForm.getFilledFormItems(), FormItemName.PAID_AMOUNT)) + "$");
            purchase.setPurchaseDate(new FilledFormItemManager(FilledFormManager.getFilledFormItemByFormItemName(filledForm.getFilledFormItems(), FormItemName.PURCHASE_DATE_AND_TIME)).getFormattedValue(null));
            purchase.setProductOrderStatus(FilledFormItemManager.getValue(FilledFormManager.getFilledFormItemByFormItemName(filledForm.getFilledFormItems(), FormItemName.ORDER_STATUS)));

            final FilledFormItem linkedProductItem =
                    FilledFormManager.getFilledFormItemByFormItemName(filledForm.getFilledFormItems(), FormItemName.LINKED);
            final FilledForm linkedProductForm = LinkedFormManager.getLinkedFilledFormByLinkedFilledFormItem(linkedProductItem);
            purchase.setProductImageItem(FilledFormManager.getFilledFormItemByFormItemName(linkedProductForm.getFilledFormItems(), FormItemName.IMAGE_FILE_UPLOAD));

            purchases.add(purchase);
        }

        return purchases;
    }

    private Persistance persistance = ServiceLocator.getPersistance();

}
