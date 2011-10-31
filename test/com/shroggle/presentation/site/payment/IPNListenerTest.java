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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.FilledFormItemNotFoundException;
import com.shroggle.exception.GalleryNotFoundException;
import com.shroggle.exception.PaypalButtonCustomParameterMalformedException;
import com.shroggle.exception.UserNotFoundException;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.form.FormItemManager;
import com.shroggle.logic.gallery.paypal.PaypalButtonIPNRequest;
import com.shroggle.logic.site.payment.PaymentLogRequest;
import com.shroggle.logic.site.payment.TransactionStatus;
import com.shroggle.logic.user.UsersGroupManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.mail.Mail;
import com.shroggle.util.mail.MockMailSender;
import com.shroggle.util.persistance.Persistance;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

/**
 * @author dmitry.solomadin
 */
@RunWith(TestRunnerWithMockServices.class)
public class IPNListenerTest {

    @Before
    public void before() {
        listener.setContext(new ActionBeanContext());
    }

    @Test
    public void handleRecurringProfileNotification() {
        // Preparing logs.
        final PaymentLogRequest paymentLogRequest = new PaymentLogRequest();
        final PaymentLog paymentLog =
                TestUtil.createPaymentLog(PaymentMethod.PAYPAL, TransactionStatus.SENT_CONFIRMED, paymentLogRequest);

        prepareRecurringProfileNotification("" + paymentLog.getLogId());

        listener.handleNotificationInternal("VERIFIED");

        Assert.assertEquals(TransactionStatus.COMPLETED, paymentLog.getTransactionStatus());
    }

    @Test
    public void handleMassPaymentNotification_withExistingLogs() {
        // Preparing logs.
        final PaymentLogRequest paymentLogRequest1 = new PaymentLogRequest();
        final PaymentLog paymentLog1 =
                TestUtil.createPaymentLog(PaymentMethod.PAYPAL, TransactionStatus.SENT_CONFIRMED, paymentLogRequest1);
        final PaymentLogRequest paymentLogRequest2 = new PaymentLogRequest();
        final PaymentLog paymentLog2 =
                TestUtil.createPaymentLog(PaymentMethod.PAYPAL, TransactionStatus.SENT_CONFIRMED, paymentLogRequest2);

        prepareMassPaymentNotification("" + paymentLog1.getLogId(), "" + paymentLog2.getLogId());

        listener.handleNotificationInternal("VERIFIED");

        Assert.assertEquals(TransactionStatus.COMPLETED, paymentLog1.getTransactionStatus());
        Assert.assertEquals(TransactionStatus.COMPLETED, paymentLog2.getTransactionStatus());
        Assert.assertNotNull(paymentLog1.getTransactionId());
        Assert.assertNotNull(paymentLog2.getTransactionId());
        Assert.assertEquals("25.99", paymentLog1.getSum());
        Assert.assertEquals("9.99", paymentLog2.getSum());
    }

    @Test
    public void handleMassPaymentNotification_withNotExistingFirstLog() {
        // Preparing logs.
        final PaymentLogRequest paymentLogRequest2 = new PaymentLogRequest();
        final PaymentLog paymentLog2 =
                TestUtil.createPaymentLog(PaymentMethod.PAYPAL, TransactionStatus.SENT_CONFIRMED, paymentLogRequest2);

        prepareMassPaymentNotification("12345", "" + paymentLog2.getLogId());

        listener.handleNotificationInternal("VERIFIED");

        Assert.assertEquals(TransactionStatus.COMPLETED, paymentLog2.getTransactionStatus());
        Assert.assertNotNull(paymentLog2.getTransactionId());
        Assert.assertEquals("9.99", paymentLog2.getSum());
    }

    @Test
    public void handleCartNotification() {
        final User user = TestUtil.createUserAndLogin();
        user.setFirstName("ufn");
        user.setLastName("uln");
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final Gallery gallery = TestUtil.createGallery(site);

        final Group group = TestUtil.createGroup("group", site);

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
        final List<FormItem> productsFormItems = TestUtil.createFormItems(FormItemName.PRODUCT_NAME, FormItemName.FIRST_NAME, FormItemName.PRODUCT_ACCESS_GROUPS);
        productsForm.setFormItems(productsFormItems);
        final FilledForm product1 = TestUtil.createFilledFormEmpty(productsForm.getId());
        final FilledFormItem product1Name = TestUtil.createFilledFormItem(productsFormItems.get(0).getFormItemId(), FormItemName.PRODUCT_NAME, "product1_name");
        final FilledFormItem product1Price = TestUtil.createFilledFormItem(productsFormItems.get(1).getFormItemId(), FormItemName.FIRST_NAME, "25");
        final FilledFormItem product1Groups = TestUtil.createFilledFormItem(productsFormItems.get(1).getFormItemId(),
                FormItemName.PRODUCT_ACCESS_GROUPS, group.getGroupId() + FilledFormItem.VALUE_DELIMETER + "ONE_DAY");
        product1.addFilledFormItem(product1Name);
        product1.addFilledFormItem(product1Price);
        product1.addFilledFormItem(product1Groups);
        gallery.setFormId1(productsForm.getId());

        // Forming gallery paypalSettings.
        final DraftForm orderForm = TestUtil.createOrderFormAndAddToGallerySettings(productsFormItems.get(0).getFormItemId(), registrationForm.getId(), gallery);
        gallery.getPaypalSettings().setFormItemIdWithProductName(productsFormItems.get(0).getFormItemId());
        gallery.getPaypalSettings().setFormItemIdWithPrice(productsFormItems.get(1).getFormItemId());

        prepareCartNotification(new ArrayList<Integer>() {{
            add(prepareRequest(gallery.getId(), user.getUserId(), product1Name.getItemId(), product1Price.getItemId(), product1Groups.getItemId(), registrationForm.getId(), site.getSiteId()));
        }});

        // Check that all goes fine. No exceptions.
        listener.handleNotificationInternal("VERIFIED");
        // Asserting that we got a new order in orderForm.
        List<FilledForm> orders = persistance.getFilledFormsByFormId(orderForm.getId());
        Assert.assertEquals(1, orders.size());
        // Asserting order consistency.
        Assert.assertEquals("product1_name", FilledFormManager.getFilledFormItemsByFormItemName
                (orders.get(0), FormItemName.PRODUCT_NAME).get(0).getValue());
        //product link
        Assert.assertEquals(product1.getFilledFormId() + ";" + product1Name.getItemId(), FilledFormManager.getFilledFormItemsByFormItemName
                (orders.get(0), FormItemName.LINKED).get(0).getValue());
        //user link
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

        Assert.assertEquals(1, new UsersGroupManager(user).getAccessibleGroupsId().size());
        Assert.assertEquals(group.getGroupId(), (int) new UsersGroupManager(user).getAccessibleGroupsId().get(0));

        // Asserting that user expiration date in group is current date + 1 Day.
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, 1);
        Date datePlusOneDay = c.getTime();
        Date groupExpirationDate = new UsersGroupManager(user).getExpirationDateForGroup(group.getGroupId());
        long timeDifference = datePlusOneDay.getTime() - groupExpirationDate.getTime();
        Assert.assertTrue(timeDifference < 1000 * 60);
    }

    @Test
    public void handleCartNotification_MultipleItems() {
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
        product1.addFilledFormItem(product1Name);
        product1.addFilledFormItem(product1Price);
        final FilledForm product2 = TestUtil.createFilledFormEmpty(productsForm.getId());
        final FilledFormItem product2Name = TestUtil.createFilledFormItem(productsFormItems.get(0).getFormItemId(), FormItemName.PRODUCT_NAME, "product2_name");
        final FilledFormItem product2Price = TestUtil.createFilledFormItem(productsFormItems.get(1).getFormItemId(), FormItemName.FIRST_NAME, "10");
        product2.addFilledFormItem(product2Name);
        product2.addFilledFormItem(product2Price);

        gallery.setFormId1(productsForm.getId());

        // Forming gallery paypalSettings.
        final DraftForm orderForm = TestUtil.createOrderFormAndAddToGallerySettings(productsFormItems.get(0).getFormItemId(), registrationForm.getId(), gallery);
        gallery.getPaypalSettings().setFormItemIdWithProductName(productsFormItems.get(0).getFormItemId());
        gallery.getPaypalSettings().setFormItemIdWithPrice(productsFormItems.get(1).getFormItemId());

        prepareCartNotification(new ArrayList<Integer>() {{
            add(prepareRequest(gallery.getId(), user.getUserId(), product1Name.getItemId(), product1Price.getItemId(), 0, registrationForm.getId(), site.getSiteId()));
            add(prepareRequest(gallery.getId(), user.getUserId(), product2Name.getItemId(), product2Price.getItemId(), 0, registrationForm.getId(), site.getSiteId()));
        }});

        // Check that all goes fine. No exceptions.
        listener.handleNotificationInternal("VERIFIED");
        // Asserting that we got a new order in orderForm.
        List<FilledForm> orders = persistance.getFilledFormsByFormId(orderForm.getId());
        Assert.assertEquals(2, orders.size());
        // Asserting first order consistency.
        Assert.assertEquals("product1_name", FilledFormManager.getFilledFormItemsByFormItemName
                (orders.get(0), FormItemName.PRODUCT_NAME).get(0).getValue());
        //product link
        Assert.assertEquals(product1.getFilledFormId() + ";" + product1Name.getItemId(), FilledFormManager.getFilledFormItemsByFormItemName
                (orders.get(0), FormItemName.LINKED).get(0).getValue());
        //user link
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
        // Asserting second order consistency.
        Assert.assertEquals("product2_name", FilledFormManager.getFilledFormItemsByFormItemName
                (orders.get(1), FormItemName.PRODUCT_NAME).get(0).getValue());
        Assert.assertEquals(product2.getFilledFormId() + ";" + product2Name.getItemId(), FilledFormManager.getFilledFormItemsByFormItemName
                (orders.get(1), FormItemName.LINKED).get(0).getValue());
    }

    @Test
    public void handleCartNotification_WithoutFieldInRegistrationForm() {
        final User user = TestUtil.createUserAndLogin();
        user.setFirstName("ufn");
        user.setLastName("uln");
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final Gallery gallery = TestUtil.createGallery(site);

        // Creating registration form for customers.
        final DraftForm registrationForm = TestUtil.createRegistrationForm(site);
        final FilledForm registrationFilledForm = TestUtil.createFilledRegistrationForm(user, site, registrationForm.getId());

        // Creating products form for gallery and adding some products into it.
        final DraftForm productsForm = TestUtil.createCustomForm(site);
        final List<FormItem> productsFormItems = TestUtil.createFormItems(FormItemName.PRODUCT_NAME, FormItemName.FIRST_NAME);
        productsForm.setFormItems(productsFormItems);
        final FilledForm product1 = TestUtil.createFilledFormEmpty(productsForm.getId());
        final FilledFormItem product1Name = TestUtil.createFilledFormItem(productsFormItems.get(0).getFormItemId(), FormItemName.PRODUCT_NAME, "product1_name");
        final FilledFormItem product1Price = TestUtil.createFilledFormItem(productsFormItems.get(1).getFormItemId(), FormItemName.FIRST_NAME, "25");
        product1.addFilledFormItem(product1Name);
        product1.addFilledFormItem(product1Price);
        gallery.setFormId1(productsForm.getId());

        // Forming gallery paypalSettings.
        final DraftForm orderForm = TestUtil.createOrderFormAndAddToGallerySettings(productsFormItems.get(0).getFormItemId(), registrationForm.getId(), gallery);
        gallery.getPaypalSettings().setFormItemIdWithProductName(productsFormItems.get(0).getFormItemId());
        gallery.getPaypalSettings().setFormItemIdWithPrice(productsFormItems.get(1).getFormItemId());

        prepareCartNotification(new ArrayList<Integer>() {{
            add(prepareRequest(gallery.getId(), user.getUserId(), product1Name.getItemId(), product1Price.getItemId(), 0, registrationForm.getId(), site.getSiteId()));
        }});

        // Check that all goes fine. No exceptions.
        listener.handleNotificationInternal("VERIFIED");
        // Asserting that we got a new order in orderForm.
        List<FilledForm> orders = persistance.getFilledFormsByFormId(orderForm.getId());
        Assert.assertEquals(registrationFilledForm.getFilledFormId() + ";0", FilledFormManager.getFilledFormItemsByFormItemName
                (orders.get(0), FormItemName.LINKED).get(1).getValue());
    }

    @Test(expected = UserNotFoundException.class)
    public void handleCartNotification_withoutUser() {
        final User user = TestUtil.createUserAndLogin();
        user.setFirstName("ufn");
        user.setLastName("uln");
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final Gallery gallery = TestUtil.createGallery(site);

        // Creating registration form for customers.
        final Form registrationForm = TestUtil.createRegistrationForm(site);
        TestUtil.createFilledRegistrationForm(user, site, registrationForm.getId());

        // Creating products form for gallery and adding some products into it.
        final DraftForm productsForm = TestUtil.createCustomForm(site);
        final List<FormItem> productsFormItems = TestUtil.createFormItems(FormItemName.PRODUCT_NAME, FormItemName.FIRST_NAME);
        productsForm.setFormItems(productsFormItems);
        final FilledForm product1 = TestUtil.createFilledFormEmpty(productsForm.getId());
        final FilledFormItem product1Name = TestUtil.createFilledFormItem(productsFormItems.get(0).getFormItemId(), FormItemName.PRODUCT_NAME, "product1_name");
        final FilledFormItem product1Price = TestUtil.createFilledFormItem(productsFormItems.get(1).getFormItemId(), FormItemName.FIRST_NAME, "25");
        product1.addFilledFormItem(product1Name);
        product1.addFilledFormItem(product1Price);
        gallery.setFormId1(productsForm.getId());

        // Forming gallery paypalSettings.
        TestUtil.createOrderFormAndAddToGallerySettings(productsFormItems.get(0).getFormItemId(), registrationForm.getId(), gallery);
        gallery.getPaypalSettings().setFormItemIdWithProductName(productsFormItems.get(0).getFormItemId());
        gallery.getPaypalSettings().setFormItemIdWithPrice(productsFormItems.get(1).getFormItemId());

        prepareCartNotification(new ArrayList<Integer>() {{
            add(prepareRequest(gallery.getId(), 0, product1Name.getItemId(), product1Price.getItemId(), 0, registrationForm.getId(), site.getSiteId()));
        }});

        listener.handleNotificationInternal("VERIFIED");
    }

    @Test(expected = GalleryNotFoundException.class)
    public void handleCartNotification_withoutGallery() {
        final User user = TestUtil.createUserAndLogin();
        user.setFirstName("ufn");
        user.setLastName("uln");
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final Gallery gallery = TestUtil.createGallery(site);

        // Creating registration form for customers.
        final Form registrationForm = TestUtil.createRegistrationForm(site);
        TestUtil.createFilledRegistrationForm(user, site, registrationForm.getId());

        // Creating products form for gallery and adding some products into it.
        final DraftForm productsForm = TestUtil.createCustomForm(site);
        final List<FormItem> productsFormItems = TestUtil.createFormItems(FormItemName.PRODUCT_NAME, FormItemName.FIRST_NAME);
        productsForm.setFormItems(productsFormItems);
        final FilledForm product1 = TestUtil.createFilledFormEmpty(productsForm.getId());
        final FilledFormItem product1Name = TestUtil.createFilledFormItem(productsFormItems.get(0).getFormItemId(), FormItemName.PRODUCT_NAME, "product1_name");
        final FilledFormItem product1Price = TestUtil.createFilledFormItem(productsFormItems.get(1).getFormItemId(), FormItemName.FIRST_NAME, "25");
        product1.addFilledFormItem(product1Name);
        product1.addFilledFormItem(product1Price);
        gallery.setFormId1(productsForm.getId());

        // Forming gallery paypalSettings.
        TestUtil.createOrderFormAndAddToGallerySettings(productsFormItems.get(0).getFormItemId(), registrationForm.getId(), gallery);
        gallery.getPaypalSettings().setFormItemIdWithProductName(productsFormItems.get(0).getFormItemId());
        gallery.getPaypalSettings().setFormItemIdWithPrice(productsFormItems.get(1).getFormItemId());

        prepareCartNotification(new ArrayList<Integer>() {{
            add(prepareRequest(0, user.getUserId(), product1Name.getItemId(), product1Price.getItemId(), 0, registrationForm.getId(), site.getSiteId()));
        }});

        listener.handleNotificationInternal("VERIFIED");
    }

    @Test(expected = FilledFormItemNotFoundException.class)
    public void handleCartNotification_withoutProductItem() {
        final User user = TestUtil.createUserAndLogin();
        user.setFirstName("ufn");
        user.setLastName("uln");
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final Gallery gallery = TestUtil.createGallery(site);

        // Creating registration form for customers.
        final Form registrationForm = TestUtil.createRegistrationForm(site);
        TestUtil.createFilledRegistrationForm(user, site, registrationForm.getId());

        // Creating products form for gallery and adding some products into it.
        final DraftForm productsForm = TestUtil.createCustomForm(site);
        final List<FormItem> productsFormItems = TestUtil.createFormItems(FormItemName.PRODUCT_NAME, FormItemName.FIRST_NAME);
        productsForm.setFormItems(productsFormItems);
        final FilledForm product1 = TestUtil.createFilledFormEmpty(productsForm.getId());
        final FilledFormItem product1Name = TestUtil.createFilledFormItem(productsFormItems.get(0).getFormItemId(), FormItemName.PRODUCT_NAME, "product1_name");
        final FilledFormItem product1Price = TestUtil.createFilledFormItem(productsFormItems.get(1).getFormItemId(), FormItemName.FIRST_NAME, "25");
        product1.addFilledFormItem(product1Name);
        product1.addFilledFormItem(product1Price);
        gallery.setFormId1(productsForm.getId());

        // Forming gallery paypalSettings.
        TestUtil.createOrderFormAndAddToGallerySettings(productsFormItems.get(0).getFormItemId(), registrationForm.getId(), gallery);
        gallery.getPaypalSettings().setFormItemIdWithProductName(productsFormItems.get(0).getFormItemId());
        gallery.getPaypalSettings().setFormItemIdWithPrice(productsFormItems.get(1).getFormItemId());

        prepareCartNotification(new ArrayList<Integer>() {{
            add(prepareRequest(gallery.getId(), user.getUserId(), 0, product1Price.getItemId(), 0, registrationForm.getId(), site.getSiteId()));
        }});

        listener.handleNotificationInternal("VERIFIED");
    }

    @Test(expected = FilledFormItemNotFoundException.class)
    public void handleCartNotification_withoutPriceItem() {
        final User user = TestUtil.createUserAndLogin();
        user.setFirstName("ufn");
        user.setLastName("uln");
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final Gallery gallery = TestUtil.createGallery(site);

        // Creating registration form for customers.
        final Form registrationForm = TestUtil.createRegistrationForm(site);
        TestUtil.createFilledRegistrationForm(user, site, registrationForm.getId());

        // Creating products form for gallery and adding some products into it.
        final DraftForm productsForm = TestUtil.createCustomForm(site);
        final List<FormItem> productsFormItems = TestUtil.createFormItems(FormItemName.PRODUCT_NAME, FormItemName.FIRST_NAME);
        productsForm.setFormItems(productsFormItems);
        final FilledForm product1 = TestUtil.createFilledFormEmpty(productsForm.getId());
        final FilledFormItem product1Name = TestUtil.createFilledFormItem(productsFormItems.get(0).getFormItemId(), FormItemName.PRODUCT_NAME, "product1_name");
        final FilledFormItem product1Price = TestUtil.createFilledFormItem(productsFormItems.get(1).getFormItemId(), FormItemName.FIRST_NAME, "25");
        product1.addFilledFormItem(product1Name);
        product1.addFilledFormItem(product1Price);
        gallery.setFormId1(productsForm.getId());

        // Forming gallery paypalSettings.
        TestUtil.createOrderFormAndAddToGallerySettings(productsFormItems.get(0).getFormItemId(), registrationForm.getId(), gallery);
        gallery.getPaypalSettings().setFormItemIdWithProductName(productsFormItems.get(0).getFormItemId());
        gallery.getPaypalSettings().setFormItemIdWithPrice(productsFormItems.get(1).getFormItemId());

        prepareCartNotification(new ArrayList<Integer>() {{
            add(prepareRequest(gallery.getId(), user.getUserId(), product1Name.getItemId(), 0, 0, registrationForm.getId(), site.getSiteId()));
        }});

        listener.handleNotificationInternal("VERIFIED");
    }

    @Test(expected = PaypalButtonCustomParameterMalformedException.class)
    public void handleCartNotification_withMalformedCustomParameter() {
        prepareMalformedCartNotification();

        listener.handleNotificationInternal("VERIFIED");
    }

    @Test
    public void handleMassPaymentNotification_withoutExistingLogs_VERIFIED() {
        prepareMassPaymentNotification();

        // Check that all goes fine. No exceptions.
        listener.handleNotificationInternal("VERIFIED");
    }

    @Test
    public void handleMassPaymentNotification_withoutExistingLogs_INVALID() {
        prepareMassPaymentNotification();

        // Check that all goes fine. No exceptions.
        listener.handleNotificationInternal("INVALID");
    }

    @Test
    public void handleMassPaymentNotification_withoutExistingLogs_UNKNOWN() {
        prepareMassPaymentNotification();

        // Check that all goes fine. No exceptions.
        listener.handleNotificationInternal("UNKNOWN");
    }

    @Test
    public void handleUnknownNotification() {
        prepareUnknownNotification();

        // Check that all goes fine. No exceptions.
        listener.handleNotificationInternal("VERIFIED");
    }

    @Test
    public void testSendPurchaseEmails() throws Exception {
        final User user = TestUtil.createUserAndLogin("email");
        final User siteOwner = TestUtil.createUser("ownersEmail");
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(siteOwner, site);

        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm(site.getSiteId());

        final FilledForm filledForm = TestUtil.createFilledForm(registrationForm, user);
        final FilledFormItem itemFirstName = TestUtil.createFilledFormItem(FormItemName.FIRST_NAME);
        itemFirstName.setItemName("First Name");
        itemFirstName.setValue("Anatoliy");
        final FilledFormItem itemLastName = TestUtil.createFilledFormItem(FormItemName.LAST_NAME);
        itemLastName.setItemName("Last Name");
        itemLastName.setValue("Balakirev");
        filledForm.addFilledFormItem(itemFirstName);
        filledForm.addFilledFormItem(itemLastName);

        final DraftGallery gallery = TestUtil.createGallery(site);


        final FilledFormItem purchasedItem = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        purchasedItem.setItemName("Image");
        purchasedItem.setValue("Image");


        final List<PaypalButtonIPNRequest> ipnRequests = new ArrayList<PaypalButtonIPNRequest>();
        final PaypalButtonIPNRequest request = new PaypalButtonIPNRequest();
        request.setUserId(user.getUserId());
        request.setRegistrationFormId(registrationForm.getFormId());
        request.setSiteId(site.getSiteId());
        request.setProductNameFilledItemId(purchasedItem.getItemId());
        request.setGalleryId(gallery.getFormId());
        request.setPriceWithTax(10, 1);
        ipnRequests.add(request);


        new IPNListener().sendPurchaseEmails(ipnRequests);

        final MockMailSender mailSender = (MockMailSender)ServiceLocator.getMailSender();
        final List<Mail> mails = mailSender.getMails();
        Assert.assertEquals(2, mails.size());
        Assert.assertEquals("A 11$ purchase was made from your " + gallery.getName() + " store", mails.get(0).getSubject());
        Assert.assertEquals("ownersEmail", mails.get(0).getTo());
        Assert.assertEquals("A purchase was made from your Gallery1 store\n" +
                "Purchase details:\n" +
                "\n" +
                "Customer registration form:\n" +
                "Email Address: User Email Address\n" +
                "First Name: User First Name\n" +
                "Last Name: User Last Name\n" +
                "Screen name / Nickname: User Screen Name\n" +
                "Telephone number: User Telephone Number\n" +
                "Title: some text\n" +
                "First Name: Anatoliy\n" +
                "Last Name: Balakirev\n" +
                "\n" +
                "\n" +
                "Item Purchased: Image\n" +
                "Quantity: 1\n" +
                "Price: 11\n" +
                "\n" +
                "\n" +
                "Total: 11$", mails.get(0).getText());

        Assert.assertEquals("Thank you for your purchase", mails.get(1).getSubject());
        Assert.assertEquals("email", mails.get(1).getTo());
        Assert.assertEquals("title has received your order.\n" +
                "Please find your receipt below:\n" +
                "\n" +
                "Customer registration form:\n" +
                "Email Address: User Email Address\n" +
                "First Name: User First Name\n" +
                "Last Name: User Last Name\n" +
                "Screen name / Nickname: User Screen Name\n" +
                "Telephone number: User Telephone Number\n" +
                "Title: some text\n" +
                "First Name: Anatoliy\n" +
                "Last Name: Balakirev\n" +
                "\n" +
                "\n" +
                "Item Purchased: Image\n" +
                "Quantity: 1\n" +
                "Price: 11\n" +
                "\n" +
                "\n" +
                "Total: 11$", mails.get(1).getText());
    }

    @Test
    public void testSendPurchaseEmails_withTwoEqualItems() throws Exception {
        final User user = TestUtil.createUserAndLogin("email");
        final User siteOwner = TestUtil.createUser("ownersEmail");
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(siteOwner, site);

        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm(site.getSiteId());

        final FilledForm filledForm = TestUtil.createFilledForm(registrationForm, user);
        final FilledFormItem itemFirstName = TestUtil.createFilledFormItem(FormItemName.FIRST_NAME);
        itemFirstName.setItemName("First Name");
        itemFirstName.setValue("Anatoliy");
        final FilledFormItem itemLastName = TestUtil.createFilledFormItem(FormItemName.LAST_NAME);
        itemLastName.setItemName("Last Name");
        itemLastName.setValue("Balakirev");
        filledForm.addFilledFormItem(itemFirstName);
        filledForm.addFilledFormItem(itemLastName);

        final DraftGallery gallery = TestUtil.createGallery(site);


        final FilledFormItem purchasedItem = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        purchasedItem.setItemName("Image");
        purchasedItem.setValue("Image");


        final List<PaypalButtonIPNRequest> ipnRequests = new ArrayList<PaypalButtonIPNRequest>();
        final PaypalButtonIPNRequest request = new PaypalButtonIPNRequest();
        request.setUserId(user.getUserId());
        request.setRegistrationFormId(registrationForm.getFormId());
        request.setSiteId(site.getSiteId());
        request.setProductNameFilledItemId(purchasedItem.getItemId());
        request.setGalleryId(gallery.getFormId());
        request.setPriceWithTax(10, 1);
        ipnRequests.add(request);

        final PaypalButtonIPNRequest request2 = new PaypalButtonIPNRequest();
        request2.setUserId(user.getUserId());
        request2.setRegistrationFormId(registrationForm.getFormId());
        request2.setSiteId(site.getSiteId());
        request2.setProductNameFilledItemId(purchasedItem.getItemId());
        request2.setGalleryId(gallery.getFormId());
        request2.setPriceWithTax(10, 1);
        ipnRequests.add(request2);

        new IPNListener().sendPurchaseEmails(ipnRequests);

        final MockMailSender mailSender = (MockMailSender)ServiceLocator.getMailSender();
        final List<Mail> mails = mailSender.getMails();
        Assert.assertEquals(2, mails.size());
        Assert.assertEquals("A 22$ purchase was made from your " + gallery.getName() + " store", mails.get(0).getSubject());
        Assert.assertEquals("ownersEmail", mails.get(0).getTo());
        Assert.assertEquals("A purchase was made from your Gallery1 store\n" +
                "Purchase details:\n" +
                "\n" +
                "Customer registration form:\n" +
                "Email Address: User Email Address\n" +
                "First Name: User First Name\n" +
                "Last Name: User Last Name\n" +
                "Screen name / Nickname: User Screen Name\n" +
                "Telephone number: User Telephone Number\n" +
                "Title: some text\n" +
                "First Name: Anatoliy\n" +
                "Last Name: Balakirev\n" +
                "\n" +
                "\n" +
                "Item Purchased: Image\n" +
                "Quantity: 2\n" +
                "Price: 11\n" +
                "\n" +
                "\n" +
                "Total: 22$", mails.get(0).getText());

        Assert.assertEquals("Thank you for your purchase", mails.get(1).getSubject());
        Assert.assertEquals("email", mails.get(1).getTo());
        Assert.assertEquals("title has received your order.\n" +
                "Please find your receipt below:\n" +
                "\n" +
                "Customer registration form:\n" +
                "Email Address: User Email Address\n" +
                "First Name: User First Name\n" +
                "Last Name: User Last Name\n" +
                "Screen name / Nickname: User Screen Name\n" +
                "Telephone number: User Telephone Number\n" +
                "Title: some text\n" +
                "First Name: Anatoliy\n" +
                "Last Name: Balakirev\n" +
                "\n" +
                "\n" +
                "Item Purchased: Image\n" +
                "Quantity: 2\n" +
                "Price: 11\n" +
                "\n" +
                "\n" +
                "Total: 22$", mails.get(1).getText());
    }

    @Test
    public void testSendPurchaseEmails_withTwoNotEqualItems() throws Exception {
        final User user = TestUtil.createUserAndLogin("email");
        final User siteOwner = TestUtil.createUser("ownersEmail");
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(siteOwner, site);

        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm(site.getSiteId());

        final FilledForm filledForm = TestUtil.createFilledForm(registrationForm, user);
        final FilledFormItem itemFirstName = TestUtil.createFilledFormItem(FormItemName.FIRST_NAME);
        itemFirstName.setItemName("First Name");
        itemFirstName.setValue("Anatoliy");
        final FilledFormItem itemLastName = TestUtil.createFilledFormItem(FormItemName.LAST_NAME);
        itemLastName.setItemName("Last Name");
        itemLastName.setValue("Balakirev");
        filledForm.addFilledFormItem(itemFirstName);
        filledForm.addFilledFormItem(itemLastName);

        final DraftGallery gallery = TestUtil.createGallery(site);


        final FilledFormItem purchasedItem = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        purchasedItem.setItemName("Image");
        purchasedItem.setValue("Image");

        final FilledFormItem purchasedItem2 = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        purchasedItem2.setItemName("Video");
        purchasedItem2.setValue("Video");


        final List<PaypalButtonIPNRequest> ipnRequests = new ArrayList<PaypalButtonIPNRequest>();
        final PaypalButtonIPNRequest request = new PaypalButtonIPNRequest();
        request.setUserId(user.getUserId());
        request.setRegistrationFormId(registrationForm.getFormId());
        request.setSiteId(site.getSiteId());
        request.setProductNameFilledItemId(purchasedItem.getItemId());
        request.setGalleryId(gallery.getFormId());
        request.setPriceWithTax(10, 1);
        ipnRequests.add(request);

        final PaypalButtonIPNRequest request2 = new PaypalButtonIPNRequest();
        request2.setUserId(user.getUserId());
        request2.setRegistrationFormId(registrationForm.getFormId());
        request2.setSiteId(site.getSiteId());
        request2.setProductNameFilledItemId(purchasedItem2.getItemId());
        request2.setGalleryId(gallery.getFormId());
        request2.setPriceWithTax(100, 20);
        ipnRequests.add(request2);

        new IPNListener().sendPurchaseEmails(ipnRequests);

        final MockMailSender mailSender = (MockMailSender)ServiceLocator.getMailSender();
        final List<Mail> mails = mailSender.getMails();
        Assert.assertEquals(2, mails.size());
        Assert.assertEquals("A 131$ purchase was made from your " + gallery.getName() + " store", mails.get(0).getSubject());
        Assert.assertEquals("ownersEmail", mails.get(0).getTo());
        Assert.assertEquals("A purchase was made from your Gallery1 store\n" +
                "Purchase details:\n" +
                "\n" +
                "Customer registration form:\n" +
                "Email Address: User Email Address\n" +
                "First Name: User First Name\n" +
                "Last Name: User Last Name\n" +
                "Screen name / Nickname: User Screen Name\n" +
                "Telephone number: User Telephone Number\n" +
                "Title: some text\n" +
                "First Name: Anatoliy\n" +
                "Last Name: Balakirev\n" +
                "\n" +
                "\n" +
                "Item Purchased: Image\n" +
                "Quantity: 1\n" +
                "Price: 11\n" +
                "Item Purchased: Video\n" +
                "Quantity: 1\n" +
                "Price: 120\n" +
                "\n" +
                "\n" +
                "Total: 131$", mails.get(0).getText());

        Assert.assertEquals("Thank you for your purchase", mails.get(1).getSubject());
        Assert.assertEquals("email", mails.get(1).getTo());
        Assert.assertEquals("title has received your order.\n" +
                "Please find your receipt below:\n" +
                "\n" +
                "Customer registration form:\n" +
                "Email Address: User Email Address\n" +
                "First Name: User First Name\n" +
                "Last Name: User Last Name\n" +
                "Screen name / Nickname: User Screen Name\n" +
                "Telephone number: User Telephone Number\n" +
                "Title: some text\n" +
                "First Name: Anatoliy\n" +
                "Last Name: Balakirev\n" +
                "\n" +
                "\n" +
                "Item Purchased: Image\n" +
                "Quantity: 1\n" +
                "Price: 11\n" +
                "Item Purchased: Video\n" +
                "Quantity: 1\n" +
                "Price: 120\n" +
                "\n" +
                "\n" +
                "Total: 131$", mails.get(1).getText());
    }

    private void prepareMassPaymentNotification(String... logIds) {
        final MockHttpServletRequest request = new MockHttpServletRequest("", "");
        listener.getContext().setRequest(request);

        final String notification = "payer_id=HZU8ZEBYHLEZQ& \n" +
                "payment_date=09%3A01%3A16+Dec+21%2C+2006+PST& \n" +
                "payment_gross_1=25.99& \n" +
                "payment_gross_2=9.99& \n" +
                "payment_status=Completed& \n" +
                "receiver_email_1=fred@unknowncompany.com& \n" +
                "receiver_email_2=john@fictionalcompany.com& \n" +
                "charset=windows-1252& \n" +
                "mc_currency_1=USD& \n" +
                "masspay_txn_id_1=5W531651W5136225N& \n" +
                "mc_currency_2=USD& \n" +
                "masspay_txn_id_2=8P09425963946233T& \n" +
                "first_name=Robert& \n" +
                "unique_id_1=" + (logIds.length > 0 && logIds[0] != null ? logIds[0] : "12345") + "& \n" +
                "notify_version=2.1& \n" +
                "unique_id_2=" + (logIds.length > 1 && logIds[1] != null ? logIds[1] : "45678") + "& \n" +
                "payer_status=verified& \n" +
                "verify_sign=AB5URHwIzIbcANTQUdSveiWRw8-WACTmrKK-dops2Tb6KKAQnpUJyF.l \n" +
                "&payer_email=robert@hisowncompany.com& \n" +
                "payer_business_name=His+Own+Company& \n" +
                "last_name=Moore& \n" +
                "status_1=Completed& \n" +
                "status_2=Unclaimed& \n" +
                "txn_type=masspay& \n" +
                "mc_gross_1=25.99& \n" +
                "mc_gross_2=9.99& \n" +
                "payment_fee_1=0.52& \n" +
                "residence_country=US& \n" +
                "payment_fee_2=0.20& \n" +
                "test_ipn=1& \n" +
                "mc_fee_1=0.52& \n" +
                "mc_fee_2=0.20";

        final Map<String, String[]> parameterMap = request.getParameterMap();

        StringTokenizer stringTokenizer = new StringTokenizer(notification, "&");
        String nameValuePair = stringTokenizer.nextToken();
        while (stringTokenizer.hasMoreTokens()) {
            String name = nameValuePair.substring(0, nameValuePair.indexOf("=")).trim();
            String value = nameValuePair.substring(nameValuePair.indexOf("=") + 1, nameValuePair.length()).trim();

            String[] values = new String[1];
            values[0] = value;
            parameterMap.put(name, values);
            nameValuePair = stringTokenizer.nextToken();
        }
    }

    private void prepareCartNotification(final List<Integer> requestIds) {
        final MockHttpServletRequest request = new MockHttpServletRequest("", "");
        listener.getContext().setRequest(request);

        String requestString = "";
        for (int i = 0; i < requestIds.size(); i++) {
            requestString = requestString + (i == 0 ? "" : ";") + requestIds.get(i);
        }

        final String notification = "txn_type=cart& \n" +
                "custom=" + requestString + "& \n";

        final Map<String, String[]> parameterMap = request.getParameterMap();

        StringTokenizer stringTokenizer = new StringTokenizer(notification, "&");
        String nameValuePair = stringTokenizer.nextToken();
        while (stringTokenizer.hasMoreTokens()) {
            String name = nameValuePair.substring(0, nameValuePair.indexOf("=")).trim();
            String value = nameValuePair.substring(nameValuePair.indexOf("=") + 1, nameValuePair.length()).trim();

            String[] values = new String[1];
            values[0] = value;
            parameterMap.put(name, values);
            nameValuePair = stringTokenizer.nextToken();
        }
    }

    private int prepareRequest(final int galleryId, final int userId, final int productFilledItemId,
                               final int priceFilledItemId, final int groupsFilledItemId, final int registrationFormId, final int siteId) {
        final PaypalButtonIPNRequest ipnRequest = new PaypalButtonIPNRequest();
        ipnRequest.setGalleryId(galleryId);
        ipnRequest.setUserId(userId);
        ipnRequest.setProductNameFilledItemId(productFilledItemId);
        ipnRequest.setPriceFilledItemId(priceFilledItemId);
        ipnRequest.setGroupsFilledItemId(groupsFilledItemId);
        ipnRequest.setRegistrationFormId(registrationFormId);
        ipnRequest.setSiteId(siteId);

        return ServiceLocator.getPaypalButtonIPNRequestStorage().put(ipnRequest);
    }

    private void prepareMalformedCartNotification() {
        final MockHttpServletRequest request = new MockHttpServletRequest("", "");
        listener.getContext().setRequest(request);

        final String notification = "txn_type=cart& \n" +
                "custom=asd & \n";

        final Map<String, String[]> parameterMap = request.getParameterMap();

        StringTokenizer stringTokenizer = new StringTokenizer(notification, "&");
        String nameValuePair = stringTokenizer.nextToken();
        while (stringTokenizer.hasMoreTokens()) {
            String name = nameValuePair.substring(0, nameValuePair.indexOf("=")).trim();
            String value = nameValuePair.substring(nameValuePair.indexOf("=") + 1, nameValuePair.length()).trim();

            String[] values = new String[1];
            values[0] = value;
            parameterMap.put(name, values);
            nameValuePair = stringTokenizer.nextToken();
        }
    }

    private void prepareRecurringProfileNotification(final String logId) {
        final MockHttpServletRequest request = new MockHttpServletRequest("", "");
        listener.getContext().setRequest(request);

        final String notification = "payment_cycle=Monthly& \n" +
                "txn_type=recurring_payment_profile_created& \n" +
                "last_name=Bobb& \n" +
                "next_payment_date=03:00:00 Jul 08, 2009 PDT& \n" +
                "residence_country=US& \n" +
                "initial_payment_amount=0.00& \n" +
                "rp_invoice_id=" + (logId != null ? logId : "12345") + "& \n" +
                "currency_code=USD& \n" +
                "time_created=12:59:41 May 27, 2009 PDT& \n" +
                "verify_sign=A8SKEyFJtpw0I2pUVRQ9wRhpVLh9AhYFEKfRC.wxwT.BmLB-Zx5phkSj& \n" +
                "period_type=Regular& \n" +
                "payer_status=unverified& \n" +
                "tax=0.00& \n" +
                "first_name=Joe\n" +
                "receiver_email=payments@usbswiper.com& \n" +
                "payer_id=PM3ZXCZXCWL4& \n" +
                "product_type=1& \n" +
                "shipping=0.00& \n" +
                "amount_per_cycle=4.95& \n" +
                "profile_status=Active& \n" +
                "charset=windows-1252& \n" +
                "notify_version=2.8& \n" +
                "amount=4.95& \n" +
                "outstanding_balance=0.00& \n" +
                "recurring_payment_id=I-18AASDASDS0P& \n" +
                "product_name=USBSwiper Monthly Subscription& ";

        final Map<String, String[]> parameterMap = request.getParameterMap();

        StringTokenizer stringTokenizer = new StringTokenizer(notification, "&");
        String nameValuePair = stringTokenizer.nextToken();
        while (stringTokenizer.hasMoreTokens()) {
            String name = nameValuePair.substring(0, nameValuePair.indexOf("=")).trim();
            String value = nameValuePair.substring(nameValuePair.indexOf("=") + 1, nameValuePair.length()).trim();

            String[] values = new String[1];
            values[0] = value;
            parameterMap.put(name, values);
            nameValuePair = stringTokenizer.nextToken();
        }
    }

    private void prepareUnknownNotification() {
        final MockHttpServletRequest request = new MockHttpServletRequest("", "");
        listener.getContext().setRequest(request);

        final String notification = "asd=asd& \n" +
                "txn_tyasdadpe=asdad& \n" +
                "asdad=asdsad& \n";

        final Map<String, String[]> parameterMap = request.getParameterMap();

        StringTokenizer stringTokenizer = new StringTokenizer(notification, "&");
        String nameValuePair = stringTokenizer.nextToken();
        while (stringTokenizer.hasMoreTokens()) {
            String name = nameValuePair.substring(0, nameValuePair.indexOf("=")).trim();
            String value = nameValuePair.substring(nameValuePair.indexOf("=") + 1, nameValuePair.length()).trim();

            String[] values = new String[1];
            values[0] = value;
            parameterMap.put(name, values);
            nameValuePair = stringTokenizer.nextToken();
        }
    }

    private IPNListener listener = new IPNListener();
    private Persistance persistance = ServiceLocator.getPersistance();

}
