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

import com.shroggle.entity.*;
import com.shroggle.exception.*;
import com.shroggle.logic.form.*;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.form.FormManager;
import com.shroggle.logic.gallery.GalleryManager;
import com.shroggle.logic.site.taxRates.TaxManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersGroupManager;
import com.shroggle.logic.groups.SubscriptionTimeType;
import com.shroggle.util.DoubleUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.persistance.PersistanceTransactionContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author dmitry.solomadin
 */
public class PaypalButtonHelper {

    // Parameter should come with such structure - userId;groupsFilledItemId

    public static ParsedCustomPaypalButtonRecurringPaymentParameter parseCustomPaypalButtonRecurringPaymentParameter(final String customParameter) {
        final ParsedCustomPaypalButtonRecurringPaymentParameter parsedCustomPaypalButtonRecurringPaymentParameter =
                new ParsedCustomPaypalButtonRecurringPaymentParameter();
        if (customParameter == null || customParameter.isEmpty()) {
            throw new PaypalButtonCustomParameterMalformedException();
        }

        final String[] parameters = customParameter.split(";");

        if (parameters.length != 2) {
            throw new PaypalButtonCustomParameterMalformedException();
        }

        parsedCustomPaypalButtonRecurringPaymentParameter.setUserId(parseParameter(parameters[0]));
        parsedCustomPaypalButtonRecurringPaymentParameter.setGroupsFilledItemId(parseParameter(parameters[1]));

        return parsedCustomPaypalButtonRecurringPaymentParameter;
    }

    public static List<PaypalButtonIPNRequest> parsePaypalButtonCustomParameter(final String paypalButtonCustomParameter) {
        final List<PaypalButtonIPNRequest> returnList = new ArrayList<PaypalButtonIPNRequest>();

        try {
            if (paypalButtonCustomParameter.indexOf(";") != -1) {
                String[] splittedPaypalButtonCustomParameter = paypalButtonCustomParameter.split(";");
                for (String requestId : splittedPaypalButtonCustomParameter) {
                    returnList.add(ServiceLocator.getPaypalButtonIPNRequestStorage().getRequest(Integer.parseInt(requestId)));
                }
            } else {
                returnList.add(ServiceLocator.getPaypalButtonIPNRequestStorage().getRequest(Integer.parseInt(paypalButtonCustomParameter)));
            }
        } catch (NumberFormatException ex) {
            throw new PaypalButtonCustomParameterMalformedException();
        }

        return returnList;
    }

    private static Integer parseParameter(final String parameter) {
        if (parameter == null || parameter.equals("null")) {
            return null;
        }

        try {
            return Integer.valueOf(parameter);
        } catch (NumberFormatException ex) {
            throw new PaypalButtonCustomParameterMalformedException(ex);
        }
    }

    public static class ParsedCustomPaypalButtonRecurringPaymentParameter {

        private Integer userId;

        private Integer groupsFilledItemId;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getGroupsFilledItemId() {
            return groupsFilledItemId;
        }

        public void setGroupsFilledItemId(Integer groupsFilledItemId) {
            this.groupsFilledItemId = groupsFilledItemId;
        }
    }

    // Return's created order record id.

    public FilledForm createOrderRecord(int galleryId, int userId, int productFilledItemId, int priceFilledItemId,
                                        int registrationFormId) {
        final DraftGallery gallery = persistance.getGalleryById(galleryId);
        if (gallery == null) {
            throw new GalleryNotFoundException("Cannot find gallery by Id=" + galleryId + ". Seems like" +
                    " notification that came to IPN is malformed.");
        }

        final PaypalSettingsForGallery paypalSettings = gallery.getPaypalSettings();
        final DraftForm orderForm = persistance.getDraftItem(paypalSettings.getOrdersFormId());

        if (orderForm == null) {
            throw new FormNotFoundException("Order form is null in gallery paypal settings. But notification has came.");
        }

        final FilledFormItem productNameFilledItem = persistance.getFilledFormItemById(productFilledItemId);
        if (productNameFilledItem == null) {
            throw new FilledFormItemNotFoundException("Cannot find product filled item by Id=" +
                    productFilledItemId + ". Seems like notification that came to IPN is malformed.");
        }

        final FilledFormItem priceFilledItem = persistance.getFilledFormItemById(priceFilledItemId);
        if (priceFilledItem == null) {
            throw new FilledFormItemNotFoundException("Cannot find price filled item by Id=" +
                    priceFilledItemId + ". Seems like notification that came to IPN is malformed.");
        }

        final User user = persistance.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("Cannot find user by Id=" + userId + ". Seems like" +
                    " notification that came to IPN is malformed.");
        }

        final FilledForm filledRegistrationForm =
                persistance.getFilledRegistrationFormByUserAndFormId(user, registrationFormId);

        if (filledRegistrationForm == null) {
            throw new FilledFormNotFoundException("Attempt to create an record in orders form when user haven't" +
                    " right filled registration form in rights.");
        }

        return persistanceTransaction.execute(new PersistanceTransactionContext<FilledForm>() {
            public FilledForm execute() {

                final FormItem productNameItem = FormManager.getFormItemByFormItemName(FormItemName.PRODUCT_NAME, orderForm);
                final FormItem productNameLinkItem = FormManager.getFormItemListByFormItemName(FormItemName.LINKED, orderForm).get(0);
                final FormItem customerNameItem = FormManager.getFormItemByFormItemName(FormItemName.CUSTOMER_NAME, orderForm);
                final FormItem customerNameLinkItem = FormManager.getFormItemListByFormItemName(FormItemName.LINKED, orderForm).get(1);
                final FormItem purchaseDateAndTimeItem = FormManager.getFormItemByFormItemName(FormItemName.PURCHASE_DATE_AND_TIME, orderForm);
                final FormItem paidAmountItem = FormManager.getFormItemByFormItemName(FormItemName.PAID_AMOUNT, orderForm);
                final FormItem taxAmountItem = FormManager.getFormItemByFormItemName(FormItemName.TAX_AMOUNT, orderForm);
                final FormItem orderStatusItem = FormManager.getFormItemByFormItemName(FormItemName.ORDER_STATUS, orderForm);
                final FormItem statusNotesItem = FormManager.getFormItemByFormItemName(FormItemName.STATUS_NOTES, orderForm);

                final FilledForm filledForm = FilledFormManager.saveFilledForm(new ArrayList<FilledFormItem>(), orderForm);
                FilledFormManager.saveFilledFormItem(productNameItem.getItemName(), productNameItem.getFormItemName(),
                        productNameItem.getFormItemId(), filledForm, productNameFilledItem.getValue());
                FilledFormManager.saveFilledFormItem(productNameLinkItem.getItemName(), productNameLinkItem.getFormItemName(),
                        productNameLinkItem.getFormItemId(), filledForm,
                        productNameFilledItem.getFilledForm().getFilledFormId() + ";" + productNameFilledItem.getItemId());
                FilledFormManager.saveFilledFormItem(customerNameItem.getItemName(), customerNameItem.getFormItemName(),
                        customerNameItem.getFormItemId(), filledForm, new UserManager(user).getFirstLastNamePair());
                final FilledFormItem linkedUserFormFilledItem =
                        LinkedFormManager.getLinkedFilledItemByFormItemIdAndFilledFormId(
                                customerNameLinkItem.getLinkedFormItemId(), filledRegistrationForm.getFilledFormId());
                FilledFormManager.saveFilledFormItem(customerNameLinkItem.getItemName(), customerNameLinkItem.getFormItemName(),
                        customerNameLinkItem.getFormItemId(), filledForm,
                        filledRegistrationForm.getFilledFormId() + ";" + (linkedUserFormFilledItem != null ? linkedUserFormFilledItem.getItemId() : "0"));
                String[] dateParts = FilledFormManager.filledFormDateFormat.format(new Date()).split(";");
                FilledFormManager.saveFilledFormItem(purchaseDateAndTimeItem.getItemName(), purchaseDateAndTimeItem.getFormItemName(),
                        purchaseDateAndTimeItem.getFormItemId(), filledForm, dateParts[0], dateParts[1], dateParts[2],
                        dateParts[3], dateParts[4]);
                FilledFormManager.saveFilledFormItem(paidAmountItem.getItemName(), paidAmountItem.getFormItemName(),
                        paidAmountItem.getFormItemId(), filledForm, priceFilledItem.getValue());
                if (taxAmountItem != null) {
                    // May be null for old order forms.
                    FilledFormManager.saveFilledFormItem(taxAmountItem.getItemName(), taxAmountItem.getFormItemName(),
                            taxAmountItem.getFormItemId(), filledForm, "" + new TaxManager().calculateTaxForRender(priceFilledItem.getFilledForm(),
                                    DoubleUtil.safeParse(priceFilledItem.getValue()), null,
                                    persistance.getFormById(filledRegistrationForm.getFormId()).getSiteId()).getTax());
                }
                FilledFormManager.saveFilledFormItem(orderStatusItem.getItemName(), orderStatusItem.getFormItemName(),
                        orderStatusItem.getFormItemId(), filledForm, "Paid");
                FilledFormManager.saveFilledFormItem(statusNotesItem.getItemName(), statusNotesItem.getFormItemName(),
                        statusNotesItem.getFormItemId(), filledForm, "");

                user.addFilledForm(filledForm);

                return filledForm;
            }
        });
    }

    // Return's groupId-period pairs to log them.

    public String addUserToGroups(final Integer userId, final Integer groupsFilledFormItemId) {
        String userWasAddedToGroups = "";
        final User user = persistance.getUserById(userId);
        if (user == null || groupsFilledFormItemId == null) {
            return userWasAddedToGroups;
        }

        final FilledFormItem subscriptionFilledItem =
                ServiceLocator.getPersistance().getFilledFormItemById(groupsFilledFormItemId);

        if (subscriptionFilledItem == null) {
            return userWasAddedToGroups;
        }

        for (int i = 0; i < subscriptionFilledItem.getValues().size(); i++) {
            String groupId = subscriptionFilledItem.getValues().get(i);
            if (subscriptionFilledItem.getValues().size() > i + 1) {
                String subscriptionPeriod = subscriptionFilledItem.getValues().get(i + 1);

                Group group;
                try {
                    group = persistance.getGroup(Integer.parseInt(groupId));
                } catch (NumberFormatException ex) {
                    continue;
                }

                if (group != null) {
                    new UsersGroupManager(user).addAccessToGroup(group,
                            SubscriptionTimeType.valueOf(subscriptionPeriod).convertToTimeInterval());

                    userWasAddedToGroups = userWasAddedToGroups + "GROUP_ID: " + group.getGroupId() +
                            " PERIOD: " + subscriptionPeriod + ".";
                } else {
                    Logger.getLogger(this.getClass().getName()).warning("Unable to find group with id = " + subscriptionFilledItem.getValues().get(0));
                }
            }
        }

        return userWasAddedToGroups;
    }

    public static PaypalButtonData fillData(final Gallery gallery, final int filledFormId, final int siteId) {
        final DraftForm productsForm = new GalleryManager(gallery).getFormLogic().getForm();
        final FormItem subscriptionItem =
                FormManager.getFormItemByFormItemName(FormItemName.SUBSCRIPTION_BILLING_PERIOD, productsForm);
        final FormItem groupsItem =
                FormManager.getFormItemByFormItemName(FormItemName.PRODUCT_ACCESS_GROUPS, productsForm);

        final FilledFormItem productFilledItem = LinkedFormManager.getLinkedFilledItemByFormItemIdAndFilledFormId(
                gallery.getPaypalSettings().getFormItemIdWithProductName(), filledFormId);
        final FilledFormItem priceFilledItem = LinkedFormManager.getLinkedFilledItemByFormItemIdAndFilledFormId(
                gallery.getPaypalSettings().getFormItemIdWithPrice(), filledFormId);
        final FilledFormItem descriptionFilledItem = LinkedFormManager.getLinkedFilledItemByFormItemIdAndFilledFormId(
                gallery.getPaypalSettings().getFormItemIdWithProductDescription(), filledFormId);
        final FilledFormItem imageFilledItem = LinkedFormManager.getLinkedFilledItemByFormItemIdAndFilledFormId(
                gallery.getPaypalSettings().getFormItemIdWithProductImage(), filledFormId);

        FilledFormItem subscriptionFilledItem = null;
        if (subscriptionItem != null) {
            subscriptionFilledItem = LinkedFormManager.getLinkedFilledItemByFormItemIdAndFilledFormId(
                    subscriptionItem.getFormItemId(), filledFormId);
        }
        FilledFormItem groupsFilledItem = null;
        if (groupsItem != null) {
            groupsFilledItem = LinkedFormManager.getLinkedFilledItemByFormItemIdAndFilledFormId(
                    groupsItem.getFormItemId(), filledFormId);
        }

        return new PaypalButtonData(productFilledItem, priceFilledItem, subscriptionFilledItem, groupsFilledItem,
                descriptionFilledItem, imageFilledItem, filledFormId, siteId);
    }

    public static int putIPNRequest(final int galleryId, final int userId, final int registrationFormId, final int siteId,
                                    final PaypalButtonData paypalButtonData) {

        final PaypalButtonIPNRequest ipnRequest = new PaypalButtonIPNRequest();
        ipnRequest.setGalleryId(galleryId);
        ipnRequest.setUserId(userId);
        ipnRequest.setProductNameFilledItemId(paypalButtonData.getProductFilledItemId());
        ipnRequest.setPriceFilledItemId(paypalButtonData.getPriceFilledItemId());
        ipnRequest.setGroupsFilledItemId(paypalButtonData.getGroupsFilledItemId());
        ipnRequest.setRegistrationFormId(registrationFormId);
        ipnRequest.setSiteId(siteId);
        ipnRequest.setPriceWithTax(paypalButtonData.getPrice(), paypalButtonData.getTaxSum());

        return ServiceLocator.getPaypalButtonIPNRequestStorage().put(ipnRequest);
    }

    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Persistance persistance = ServiceLocator.getPersistance();


}
