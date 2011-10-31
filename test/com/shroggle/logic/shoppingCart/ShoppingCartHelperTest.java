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
package com.shroggle.logic.shoppingCart;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.presentation.site.render.shoppingCart.FilledFormGalleryIdsPair;
import com.shroggle.entity.*;

import java.util.List;
import java.util.ArrayList;

import junit.framework.Assert;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ShoppingCartHelperTest {

    @Test
    public void testSplitItems() {
        final User user = TestUtil.createUserAndLogin();
        user.setFirstName("ufn");
        user.setLastName("uln");
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final Gallery gallery = TestUtil.createGallery(site);
        final Gallery gallery1 = TestUtil.createGallery(site);

        final Page page = TestUtil.createPage(site);

        final WidgetItem galleryWidget = TestUtil.createWidgetItem();
        galleryWidget.setDraftItem((DraftItem) gallery);
        page.getPageSettings().addWidget(galleryWidget);

        final WidgetItem galleryWidget1 = TestUtil.createWidgetItem();
        galleryWidget1.setDraftItem((DraftItem) gallery1);
        page.getPageSettings().addWidget(galleryWidget1);

        // Creating products form for gallery and adding some products into it.
        final DraftForm productsForm = TestUtil.createCustomForm(site);
        final List<FormItem> productsFormItems = TestUtil.createFormItems(FormItemName.PRODUCT_NAME, FormItemName.FIRST_NAME, FormItemName.SUBSCRIPTION_BILLING_PERIOD);
        productsForm.setFormItems(productsFormItems);

        gallery.setFormId1(productsForm.getId());
        gallery.getPaypalSettings().setFormItemIdWithProductName(productsFormItems.get(0).getFormItemId());

        gallery1.setFormId1(productsForm.getId());
        gallery1.getPaypalSettings().setFormItemIdWithProductName(productsFormItems.get(0).getFormItemId());

        final FilledForm product1 = TestUtil.createFilledFormEmpty(productsForm.getId());
        final FilledFormItem product1Name = TestUtil.createFilledFormItem(productsFormItems.get(0).getFormItemId(), FormItemName.PRODUCT_NAME, "product1_name");
        final FilledFormItem product1Price = TestUtil.createFilledFormItem(productsFormItems.get(1).getFormItemId(), FormItemName.FIRST_NAME, "25");
        final FilledFormItem product1Subscription = TestUtil.createFilledFormItem(productsFormItems.get(2).getFormItemId(), FormItemName.SUBSCRIPTION_BILLING_PERIOD, "INDEFINITE");
        product1.addFilledFormItem(product1Name);
        product1.addFilledFormItem(product1Price);
        product1.addFilledFormItem(product1Subscription);

        final FilledForm product2 = TestUtil.createFilledFormEmpty(productsForm.getId());
        final FilledFormItem product2Name = TestUtil.createFilledFormItem(productsFormItems.get(0).getFormItemId(), FormItemName.PRODUCT_NAME, "product1_name");
        final FilledFormItem product2Price = TestUtil.createFilledFormItem(productsFormItems.get(1).getFormItemId(), FormItemName.FIRST_NAME, "25");
        final FilledFormItem product2Subscription = TestUtil.createFilledFormItem(productsFormItems.get(2).getFormItemId(), FormItemName.SUBSCRIPTION_BILLING_PERIOD, "ONE_DAY");
        product2.addFilledFormItem(product2Name);
        product2.addFilledFormItem(product2Price);
        product2.addFilledFormItem(product2Subscription);

        final FilledForm product3 = TestUtil.createFilledFormEmpty(productsForm.getId());
        final FilledFormItem product3Name = TestUtil.createFilledFormItem(productsFormItems.get(0).getFormItemId(), FormItemName.PRODUCT_NAME, "product1_name");
        final FilledFormItem product3Price = TestUtil.createFilledFormItem(productsFormItems.get(1).getFormItemId(), FormItemName.FIRST_NAME, "25");
        final FilledFormItem product3Subscription = TestUtil.createFilledFormItem(productsFormItems.get(2).getFormItemId(), FormItemName.SUBSCRIPTION_BILLING_PERIOD, "INDEFINITE");
        product3.addFilledFormItem(product3Name);
        product3.addFilledFormItem(product3Price);
        product3.addFilledFormItem(product3Subscription);

        final FilledForm product4 = TestUtil.createFilledFormEmpty(productsForm.getId());
        final FilledFormItem product4Name = TestUtil.createFilledFormItem(productsFormItems.get(0).getFormItemId(), FormItemName.PRODUCT_NAME, "product1_name");
        final FilledFormItem product4Price = TestUtil.createFilledFormItem(productsFormItems.get(1).getFormItemId(), FormItemName.FIRST_NAME, "25");
        final FilledFormItem product4Subscription = TestUtil.createFilledFormItem(productsFormItems.get(2).getFormItemId(), FormItemName.SUBSCRIPTION_BILLING_PERIOD, "INDEFINITE");
        product4.addFilledFormItem(product4Name);
        product4.addFilledFormItem(product4Price);
        product4.addFilledFormItem(product4Subscription);

        List<ShoppingCartGroupData> shoppingCartGroupDataList = ShoppingCartHelper.splitItems(new ArrayList<FilledFormGalleryIdsPair>() {{
            final FilledFormGalleryIdsPair filledFormGalleryIdsPair1 = new FilledFormGalleryIdsPair();
            filledFormGalleryIdsPair1.setGalleryId(gallery.getId());
            filledFormGalleryIdsPair1.setWidgetId(galleryWidget.getWidgetId());
            filledFormGalleryIdsPair1.setFilledFormId(product1.getFilledFormId());

            add(filledFormGalleryIdsPair1);

            final FilledFormGalleryIdsPair filledFormGalleryIdsPair2 = new FilledFormGalleryIdsPair();
            filledFormGalleryIdsPair2.setGalleryId(gallery.getId());
            filledFormGalleryIdsPair2.setWidgetId(galleryWidget.getWidgetId());
            filledFormGalleryIdsPair2.setFilledFormId(product2.getFilledFormId());

            add(filledFormGalleryIdsPair2);

            final FilledFormGalleryIdsPair filledFormGalleryIdsPair3 = new FilledFormGalleryIdsPair();
            filledFormGalleryIdsPair3.setGalleryId(gallery.getId());
            filledFormGalleryIdsPair3.setWidgetId(galleryWidget.getWidgetId());
            filledFormGalleryIdsPair3.setFilledFormId(product3.getFilledFormId());

            add(filledFormGalleryIdsPair3);

            final FilledFormGalleryIdsPair filledFormGalleryIdsPair4 = new FilledFormGalleryIdsPair();
            filledFormGalleryIdsPair4.setGalleryId(gallery1.getId());
            filledFormGalleryIdsPair4.setWidgetId(galleryWidget1.getWidgetId());
            filledFormGalleryIdsPair4.setFilledFormId(product4.getFilledFormId());

            add(filledFormGalleryIdsPair4);
        }}, SiteShowOption.ON_USER_PAGES, null);
        Assert.assertEquals(4, shoppingCartGroupDataList.size());

        Assert.assertEquals(1, shoppingCartGroupDataList.get(0).getShoppingCartItemDataList().size());
        Assert.assertEquals(ShoppingCartGroupType.NORMAL, shoppingCartGroupDataList.get(0).getShoppingCartGroupType());

        Assert.assertEquals(1, shoppingCartGroupDataList.get(1).getShoppingCartItemDataList().size());
        Assert.assertEquals(ShoppingCartGroupType.RECURRENT, shoppingCartGroupDataList.get(1).getShoppingCartGroupType());

        Assert.assertEquals(1, shoppingCartGroupDataList.get(2).getShoppingCartItemDataList().size());
        Assert.assertEquals(ShoppingCartGroupType.NORMAL, shoppingCartGroupDataList.get(2).getShoppingCartGroupType());

        Assert.assertEquals(1, shoppingCartGroupDataList.get(3).getShoppingCartItemDataList().size());
        Assert.assertEquals(ShoppingCartGroupType.NORMAL, shoppingCartGroupDataList.get(3).getShoppingCartGroupType());

        shoppingCartGroupDataList = ShoppingCartHelper.splitItems(new ArrayList<FilledFormGalleryIdsPair>() {{
            final FilledFormGalleryIdsPair filledFormGalleryIdsPair1 = new FilledFormGalleryIdsPair();
            filledFormGalleryIdsPair1.setGalleryId(gallery.getId());
            filledFormGalleryIdsPair1.setWidgetId(galleryWidget.getWidgetId());
            filledFormGalleryIdsPair1.setFilledFormId(product1.getFilledFormId());

            add(filledFormGalleryIdsPair1);

            final FilledFormGalleryIdsPair filledFormGalleryIdsPair3 = new FilledFormGalleryIdsPair();
            filledFormGalleryIdsPair3.setGalleryId(gallery.getId());
            filledFormGalleryIdsPair3.setWidgetId(galleryWidget.getWidgetId());
            filledFormGalleryIdsPair3.setFilledFormId(product3.getFilledFormId());

            add(filledFormGalleryIdsPair3);

            final FilledFormGalleryIdsPair filledFormGalleryIdsPair2 = new FilledFormGalleryIdsPair();
            filledFormGalleryIdsPair2.setGalleryId(gallery.getId());
            filledFormGalleryIdsPair2.setWidgetId(galleryWidget.getWidgetId());
            filledFormGalleryIdsPair2.setFilledFormId(product2.getFilledFormId());

            add(filledFormGalleryIdsPair2);
        }}, SiteShowOption.ON_USER_PAGES, null);
        Assert.assertEquals(2, shoppingCartGroupDataList.size());

        Assert.assertEquals(2, shoppingCartGroupDataList.get(0).getShoppingCartItemDataList().size());
        Assert.assertEquals(ShoppingCartGroupType.NORMAL, shoppingCartGroupDataList.get(0).getShoppingCartGroupType());

        Assert.assertEquals(1, shoppingCartGroupDataList.get(1).getShoppingCartItemDataList().size());
        Assert.assertEquals(ShoppingCartGroupType.RECURRENT, shoppingCartGroupDataList.get(1).getShoppingCartGroupType());
    }

}
