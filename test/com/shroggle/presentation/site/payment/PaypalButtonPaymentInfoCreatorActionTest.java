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

import com.shroggle.logic.form.FilledFormManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import com.shroggle.entity.*;
import com.shroggle.TestUtil;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.logic.form.FormItemManager;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.util.ServiceLocator;

import java.util.List;

/**
 * @author dmitry.solomadin
 */
//todo add tests.
@RunWith(value = TestRunnerWithMockServices.class)
public class PaypalButtonPaymentInfoCreatorActionTest {

    @Before
    public void before() {
        action.setContext(new ActionBeanContext());
        action.getContext().setRequest(new MockHttpServletRequest("", ""));
        action.getContext().getRequest().setAttribute("token", "token");
    }

    /*------------------------------------------------------Site------------------------------------------------------*/

    @Test
    public void executeRegularProfileCreation() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        user.setFirstName("ufn");
        user.setLastName("uln");
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final Gallery gallery = TestUtil.createGallery(site);

        // Creating registration form for customers.
        final DraftForm registrationForm = TestUtil.createRegistrationForm(site);
        DraftFormItem emailItem = FormItemManager.createFormItemByName(FormItemName.EMAIL, 0, false);
        ServiceLocator.getPersistance().putFormItem(emailItem);
        registrationForm.addFormItem(emailItem);
        final FilledForm registrationFilledForm = TestUtil.createFilledRegistrationForm(user, site, registrationForm.getId());
        final FilledFormItem emailFilledItem =
                TestUtil.createFilledFormItem(emailItem.getFormItemId(), FormItemName.EMAIL, "email@email.email");
        ServiceLocator.getPersistance().putFilledFormItem(emailFilledItem);
        registrationFilledForm.addFilledFormItem(emailFilledItem);

        // Creating products form for gallery and adding some products into it.
        final DraftForm productsForm = TestUtil.createCustomForm(site);
        final List<FormItem> productsFormItems = TestUtil.createFormItems(FormItemName.PRODUCT_NAME, FormItemName.FIRST_NAME);
        productsForm.setFormItems(productsFormItems);
        final FilledForm product1 = TestUtil.createFilledFormEmpty(productsForm.getId());
        final FilledFormItem product1Name = TestUtil.createFilledFormItem(productsFormItems.get(0).getFormItemId(), FormItemName.PRODUCT_NAME, "product1_name");
        final FilledFormItem product1Price = TestUtil.createFilledFormItem(productsFormItems.get(1).getFormItemId(), FormItemName.FIRST_NAME, "25");
        final FilledFormItem product1Subscription = TestUtil.createFilledFormItem(productsFormItems.get(1).getFormItemId(), FormItemName.SUBSCRIPTION_BILLING_PERIOD, "ONE_MONTH");

        product1.addFilledFormItem(product1Name);
        product1.addFilledFormItem(product1Price);
        product1.addFilledFormItem(product1Subscription);
        gallery.setFormId1(productsForm.getId());

        // Forming gallery paypalSettings.
        final DraftForm orderForm = TestUtil.createOrderFormAndAddToGallerySettings(productsFormItems.get(0).getFormItemId(), registrationForm.getFormId(), gallery);
        gallery.getPaypalSettings().setFormItemIdWithProductName(productsFormItems.get(0).getFormItemId());
        gallery.getPaypalSettings().setFormItemIdWithPrice(productsFormItems.get(1).getFormItemId());

        final PaypalButtonPaymentInfoRequest request = new PaypalButtonPaymentInfoRequest(12.0, product1Name.getItemId(),
                product1Price.getItemId(), product1Subscription.getItemId(), null, gallery.getId(),
                user.getUserId(), product1.getFilledFormId(), registrationForm.getId(), "redirectToUrl", "redirectOnError");
        final int requestId = ServiceLocator.getPaypalPaymentInfoRequestStorage().put(request);

        action.setRequestId(requestId);

        Assert.assertEquals("redirectToUrl", ((ResolutionMock) action.execute()).getRedirectByUrl());
        List<FilledForm> orders = ServiceLocator.getPersistance().getFilledFormsByFormId(orderForm.getId());
        Assert.assertEquals(1, orders.size());
        // Asserting order consistency.
        Assert.assertEquals("product1_name", FilledFormManager.getFilledFormItemsByFormItemName
                (orders.get(0), FormItemName.PRODUCT_NAME).get(0).getValue());
        Assert.assertEquals(product1.getFilledFormId() + ";" + product1Name.getItemId(), FilledFormManager.getFilledFormItemsByFormItemName
                (orders.get(0), FormItemName.LINKED).get(0).getValue());
        Assert.assertEquals(registrationFilledForm.getFilledFormId() + ";" + emailFilledItem.getItemId(), FilledFormManager.getFilledFormItemsByFormItemName
                        (orders.get(0), FormItemName.LINKED).get(1).getValue());
        Assert.assertEquals("25", FilledFormManager.getFilledFormItemsByFormItemName
                (orders.get(0), FormItemName.PAID_AMOUNT).get(0).getValue());
        Assert.assertEquals("ufn uln", FilledFormManager.getFilledFormItemsByFormItemName
                (orders.get(0), FormItemName.CUSTOMER_NAME).get(0).getValue());
        Assert.assertEquals("Paid", FilledFormManager.getFilledFormItemsByFormItemName
                (orders.get(0), FormItemName.ORDER_STATUS).get(0).getValue());
        Assert.assertEquals("", FilledFormManager.getFilledFormItemsByFormItemName
                (orders.get(0), FormItemName.STATUS_NOTES).get(0).getValue());
    }

    private final PaypalButtonPaymentInfoCreatorAction action = new PaypalButtonPaymentInfoCreatorAction();

}
