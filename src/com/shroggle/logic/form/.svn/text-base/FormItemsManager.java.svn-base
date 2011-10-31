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
import com.shroggle.presentation.site.YourFormTableFormItemInfo;

import java.util.*;

/**
 * Author: dmitry.solomadin
 */
public class FormItemsManager {

    public static FormItem getFirstImageField(final List<? extends FormItem> formItems) {
        sortByPosition(formItems);
        for (FormItem formItem : formItems) {
            if (formItem.getFormItemName() == FormItemName.IMAGE_FILE_UPLOAD) {
                return formItem;
            }
        }
        return null;
    }

    public boolean isPageBreakBeforeRequiredFields(final List<? extends FormItem> formItems) {
        sortByPosition(formItems);

        Integer pageBreakIndex = null;
        for (int i = 0; i < formItems.size(); i++) {
            FormItem formItem = formItems.get(i);

            if (formItem.getFormItemName() == FormItemName.PAGE_BREAK) {
                pageBreakIndex = i;
            }

            if (pageBreakIndex != null &&
                    (formItem.getFormItemName().getCheckers().contains(FormItemCheckerType.MANDATORY))
                    && !formItem.getFormItemName().equals(FormItemName.PAYMENT_AREA)) {
                return true;
            }
        }

        return false;
    }

    //Removes non-removable items from list. I.E.: for showing all avalible items in edit page.

    public void removeItemsNotShownInInitTable(List<FormItemName> items) {
        final List<FormItemName> itemsToRemove = new ArrayList<FormItemName>();
        for (FormItemName item : items) {
            if (item.getCheckers().contains(FormItemCheckerType.NOT_DISPLAY_IN_INIT_FIELD_TABLE)) {
                itemsToRemove.add(item);
            }
        }

        for (FormItemName item : itemsToRemove) {
            items.remove(item);
        }
    }

    public static void moveProductRelatedItemsToTheTop(final List<YourFormTableFormItemInfo> items) {
        YourFormTableFormItemInfo priceItem = null;
        YourFormTableFormItemInfo accessGroupsItem = null;
        YourFormTableFormItemInfo billingPeriodItem = null;

        for (YourFormTableFormItemInfo item : items) {
            if (item.getFormItemName().equals(FormItemName.PRICE.toString())) {
                priceItem = item;
            } else if (item.getFormItemName().equals(FormItemName.PRODUCT_ACCESS_GROUPS.toString())) {
                accessGroupsItem = item;
            } else if (item.getFormItemName().equals(FormItemName.SUBSCRIPTION_BILLING_PERIOD.toString())) {
                billingPeriodItem = item;
            }
        }

        if (priceItem != null) {
            items.remove(priceItem);
            items.add(0, priceItem);
        }

        if (accessGroupsItem != null) {
            items.remove(accessGroupsItem);
            items.add(1, accessGroupsItem);
        }

        if (billingPeriodItem != null) {
            items.remove(billingPeriodItem);
            items.add(2, billingPeriodItem);
        }
    }

    public boolean isPaymentBlockOnFirstPage(final List<? extends FormItem> formItems) {
        sortByPosition(formItems);

        for (FormItem formItem : formItems) {
            if (formItem.getFormItemName() == FormItemName.PAGE_BREAK) {
                return false;
            } else if (formItem.getFormItemName() == FormItemName.PAYMENT_AREA) {
                return true;
            }
        }

        return false;
    }

    public static void sortByPosition(final List<? extends FormItem> formItems) {
        Collections.sort(formItems, formItemPositionComparator);
    }

    public void sortByPositionAndManagePriority(final List<? extends FormItem> formItems) {
        Collections.sort(formItems, formItemPositionAndPriorityComparator);
    }

    public static <T extends FormItem> List<T> getSortedFormItemsByPageBreakIndex(final List<T> formItems, final int pageBreakIndex,
                                                                                  final boolean showAllItems) {
        Collections.sort(formItems, formItemPositionComparator);
        if (showAllItems) {
            return formItems;
        }
        final List<T> newFormItems = new ArrayList<T>();
        int currentPageBreakPosition = getCurrentPageBreakPosition(pageBreakIndex, formItems);
        int nextPageBreakPosition = getNextPageBreakPosition(currentPageBreakPosition, formItems);
        for (int i = currentPageBreakPosition + 1; i < nextPageBreakPosition; i++) {
            newFormItems.add(formItems.get(i));
        }
        return newFormItems;
    }

    public static <T extends FormItem> List<T> createFormItemsByFilledForm(final FilledForm filledForm, final DraftForm form) {
        final List<T> formItems = new ArrayList<T>();
        if (filledForm.getFilledFormItems() != null) {
            for (final FilledFormItem filledFormItem : filledForm.getFilledFormItems()) {
                if (filledFormItem.getFormItemName() == null) {
                    continue;
                }
                formItems.add((T) FormItemManager.createFormItemByFilledFormItem(filledFormItem, form));
            }
        }
        return formItems;
    }

    public static List<DraftFormItem> getFormItems(final FormType formType) {
        switch (formType) {
            case CONTACT_US: {
                return getDefaultContactUsFormItems();
            }
            case REGISTRATION: {
                return getDefaultRegistrationFormItems();
            }
            case CUSTOM_FORM: {
                return getDefaultFormItems();
            }
            case CHILD_SITE_REGISTRATION: {
                return getDefaultChildSiteRegistrationFormItems();
            }
            default: {
                return getDefaultFormItems();
            }
        }
    }

    protected static List<DraftFormItem> getDefaultFormItems() {
        List<DraftFormItem> defaultFormItems = new ArrayList<DraftFormItem>();
        defaultFormItems.add(FormItemManager.createFormItemByName(FormItemName.FIRST_NAME, 0, false));
        defaultFormItems.add(FormItemManager.createFormItemByName(FormItemName.LAST_NAME, 1, false));
        return defaultFormItems;
    }

    protected static List<DraftFormItem> getDefaultRegistrationFormItems() {
        List<DraftFormItem> defaultFormItems = new ArrayList<DraftFormItem>();
        defaultFormItems.add(FormItemManager.createFormItemByName(FormItemName.FIRST_NAME, 0, false));
        defaultFormItems.add(FormItemManager.createFormItemByName(FormItemName.LAST_NAME, 1, false));
        defaultFormItems.add(FormItemManager.createFormItemByName(FormItemName.REGISTRATION_UNREMOVABLE_SCREEN_NAME, 2, true));
        defaultFormItems.add(FormItemManager.createFormItemByName(FormItemName.REGISTRATION_EMAIL, 3, true));
        defaultFormItems.add(FormItemManager.createFormItemByName(FormItemName.REGISTRATION_PASSWORD, 4, true));
        defaultFormItems.add(FormItemManager.createFormItemByName(FormItemName.REGISTRATION_PASSWORD_RETYPE, 5, true));
        return defaultFormItems;
    }

    protected static List<DraftFormItem> getDefaultChildSiteRegistrationFormItems() {
        List<DraftFormItem> defaultFormItems = new ArrayList<DraftFormItem>();
        defaultFormItems.add(FormItemManager.createFormItemByName(FormItemName.FIRST_NAME, 0, false));
        defaultFormItems.add(FormItemManager.createFormItemByName(FormItemName.LAST_NAME, 1, false));
        defaultFormItems.add(FormItemManager.createFormItemByName(FormItemName.REGISTRATION_EMAIL, 2, true));
        defaultFormItems.add(FormItemManager.createFormItemByName(FormItemName.REGISTRATION_PASSWORD, 3, true));
        defaultFormItems.add(FormItemManager.createFormItemByName(FormItemName.REGISTRATION_PASSWORD_RETYPE, 4, true));
        defaultFormItems.add(FormItemManager.createFormItemByName(FormItemName.YOUR_PAGE_SITE_NAME, 5, true));
        defaultFormItems.add(FormItemManager.createFormItemByName(FormItemName.YOUR_OWN_DOMAIN_NAME, 6, false));
        defaultFormItems.add(FormItemManager.createFormItemByName(FormItemName.PAGE_BREAK, 7, false));
        defaultFormItems.add(FormItemManager.createFormItemByName(FormItemName.PAYMENT_AREA, 8, true));
        return defaultFormItems;
    }

    protected static List<DraftFormItem> getDefaultContactUsFormItems() {
        List<DraftFormItem> defaultFormItems = new ArrayList<DraftFormItem>();
        defaultFormItems.add(FormItemManager.createFormItemByName(FormItemName.FIRST_NAME, 0, false));
        defaultFormItems.add(FormItemManager.createFormItemByName(FormItemName.LAST_NAME, 1, false));
        defaultFormItems.add(FormItemManager.createFormItemByName(FormItemName.EMAIL, 2, false));
        defaultFormItems.add(FormItemManager.createFormItemByName(FormItemName.CONTACT_US_MESSAGE, 4, true));
        defaultFormItems.add(FormItemManager.createFormItemByName(FormItemName.CONTACT_US_REGISTER_FOR_NEWSLETTER, 5, false));
        return defaultFormItems;
    }

    public static List<FormItemName> getRestrictedFormItemsForGalleryDataAndNavigation() {
        final List<FormItemName> returnList = new ArrayList<FormItemName>();
        returnList.addAll(FormTypeGetter.getByType(FormItemType.SPECIAL));
        returnList.add(FormItemName.PRODUCT_ACCESS_GROUPS);
        returnList.add(FormItemName.REGISTRATION_PASSWORD);
        returnList.add(FormItemName.REGISTRATION_PASSWORD_RETYPE);

        return returnList;
    }

    public static List<FormItemName> getRestrictedFormItemsForGalleryNavigation() {
        final List<FormItemName> returnList = new ArrayList<FormItemName>();
        returnList.add(FormItemName.YOUTUBE_VIDEO);
        returnList.add(FormItemName.EMBEDDED_HTML_OBJECT);
        returnList.add(FormItemName.REGISTRATION_PASSWORD);
        returnList.add(FormItemName.REGISTRATION_PASSWORD_RETYPE);

        return returnList;
    }

    public static List<FormItem> getCorrectFormItemsForAdvancedSearch(final List<? extends FormItem> formItems) {
        final List<FormItem> returnList = new ArrayList<FormItem>();
        for (FormItem formItem : formItems) {
            if (FormItemManager.isCorrectFormItemForAdvancedSearch(formItem)) {
                returnList.add(formItem);
            }
        }

        return returnList;
    }

    /*-------------------------------------------------private methods------------------------------------------------*/
    private static final Comparator<FormItem> formItemPositionComparator = new Comparator<FormItem>() {
        public int compare(final FormItem fi1, final FormItem fi2) {
            return Integer.valueOf(fi1.getPosition()).compareTo(fi2.getPosition());
        }
    };

    private static final Comparator<FormItem> formItemPositionAndPriorityComparator = new Comparator<FormItem>() {
        public int compare(final FormItem fi1, final FormItem fi2) {
            //Move linked items to the end of the list.
            if (fi1.getFormItemName() == FormItemName.LINKED) {
                return 1;
            } else if (fi2.getFormItemName() == FormItemName.LINKED) {
                return -1;
            }

            return ((Integer) fi1.getPosition()).compareTo(fi2.getPosition());
        }
    };

    private static <T extends FormItem> int getNextPageBreakPosition(final int currentPageBreakPosition, final List<T> formItems) {
        for (int i = currentPageBreakPosition + 1; i < formItems.size(); i++) {
            if (formItems.get(i).getFormItemName() == FormItemName.PAGE_BREAK) {
                return formItems.get(i).getPosition();
            }
        }
        return formItems.size();
    }

    private static <T extends FormItem> int getCurrentPageBreakPosition(final int currentPageBreakIndex, final List<T> formItems) {
        if (currentPageBreakIndex == 0) {
            return -1;
        }
        int pageBreakIndex = 0;
        for (FormItem formItem : formItems) {
            if (formItem.getFormItemName() == FormItemName.PAGE_BREAK && ++pageBreakIndex == currentPageBreakIndex) {
                return formItem.getPosition();
            }
        }
        return formItems.size();
    }
}
