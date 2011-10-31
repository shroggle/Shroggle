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
import com.shroggle.logic.form.FormData;
import com.shroggle.logic.form.FormItemsManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.CollectionUtil;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.presentation.site.render.RenderContext;

import java.util.*;

/**
 * @author dmitry.solomadin
 */
public class FormManager {

    public FormManager() {
        form = null;
    }

    public FormManager(Form form) {
        if (form == null) {
            throw new IllegalArgumentException("Unable to create FormManager with null Form.");
        }
        this.form = form;
    }

    public boolean containsFormItem(final int formItemId) {
        for (FormItem formItem : form.getFormItems()) {
            if (formItem.getFormItemId() == formItemId) {
                return true;
            }
        }
        return false;
    }

    private final Form form;

    public static int getTotalPageBreaks(final Form form) {
        return getTotalPageBreaks(form.getFormItems());
    }

    public static int getTotalPageBreaks(final FormData formData) {
        return getTotalPageBreaks(formData.getFormItems());
    }

    public static DraftFormItem getFormItemByFormItemName(final FormItemName formItemName, final DraftForm form) {
        if (form != null && formItemName != null) {
            for (DraftFormItem item : form.getDraftFormItems()) {
                if (item.getFormItemName() != null && item.getFormItemName() == formItemName) {
                    return item;
                }
            }
        }
        return null;
    }

    public static DraftFormItem getFormItemByPosition(final DraftForm form, final Integer position) {
        if (form != null && position != null) {
            for (DraftFormItem item : form.getDraftFormItems()) {
                if (item.getFormItemName() != null && item.getPosition() == position) {
                    return item;
                }
            }
        }
        return null;
    }

    public static FormItem getFormItemByFormItemType(final FormItemType formItemType, final DraftForm form) {
        if (form != null && formItemType != null) {
            for (FormItem item : form.getDraftFormItems()) {
                if (item.getFormItemName() != null && item.getFormItemName().getType() == formItemType) {
                    return item;
                }
            }
        }
        return null;
    }

    public static List<FormItem> getFormItemListByFormItemName(final FormItemName formItemName, final DraftForm form) {
        List<FormItem> items = new ArrayList<FormItem>();
        if (form != null && formItemName != null) {
            for (FormItem item : form.getDraftFormItems()) {
                if (item.getFormItemName() != null && item.getFormItemName() == formItemName) {
                    items.add(item);
                }
            }
        }
        return items;
    }

    //Used to update form items. Note, that newFormItems contains all items, new one and old. New ones id is null.

    public List<DraftFormItem> createOrSetFormItems(final DraftForm form, final List<DraftFormItem> newFormItems) {
        if (form == null) {
            throw new IllegalArgumentException("form cannot be null.");
        }

        final List<DraftFormItem> existingFormItems = form.getDraftFormItems();
        final List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        final List<DraftFormItem> updatedItems = new ArrayList<DraftFormItem>();
        if (newFormItems != null && !newFormItems.isEmpty()) {
            for (int i = 0; i < newFormItems.size(); i++) {
                DraftFormItem newFormItem = newFormItems.get(i);
                boolean hasItem = false;
                for (DraftFormItem existingFormItem : existingFormItems) {
                    if (existingFormItem.getFormItemId() == newFormItem.getFormItemId()) {
                        existingFormItem.setPosition(i);
                        existingFormItem.setItemName(newFormItem.getItemName());
                        existingFormItem.setRequired(newFormItem.isRequired());
                        existingFormItem.setInstruction(newFormItem.getInstruction());
                        existingFormItem.setLinkedFormItemId(newFormItem.getLinkedFormItemId());
                        existingFormItem.setFormItemDisplayType(newFormItem.getFormItemDisplayType());
                        hasItem = true;
                        formItems.add(existingFormItem);
                        updatedItems.add(existingFormItem);
                        break;
                    }
                }

                if (!hasItem) {
                    newFormItem.setDraftForm(form);
                    newFormItem.setPosition(i);
                    persistance.putFormItem(newFormItem);
                    formItems.add(newFormItem);
                }
            }
        }

        //Removing old form items that are not updated.
        final List<FormItem> itemsToRemove = new ArrayList<FormItem>();
        for (FormItem existingFormItem : existingFormItems) {
            if (!updatedItems.contains(existingFormItem)) {
                itemsToRemove.add(existingFormItem);
            }
        }

        for (FormItem itemToRemove : itemsToRemove) {
            persistance.removeFormItem((DraftFormItem) itemToRemove);
        }

        return formItems;
    }

    public void remove(final Form form) {
        // Removing link on orders form
        if (form.getType() == FormType.ORDER_FORM) {
            final Gallery galleryWithOrderForm = persistance.getGalleryByOrderFormId(form.getId());
            if (galleryWithOrderForm != null) {
                galleryWithOrderForm.getPaypalSettings().setOrdersFormId(null);
            }
        }

        persistance.removeDraftItem((DraftItem) form);
    }

    //Constructs fake form by filled form for edit registered visitor window.

    public static FormData constructFormByFilledForm(final FilledForm filledForm, boolean fromRegistration) {
        final FormData form = new FormData(filledForm);
        if (fromRegistration) {
            for (final FormItem formItem : form.getFormItems()) {
                if (formItem.getFormItemName() == FormItemName.EMAIL) {
                    formItem.setPosition(0);
                }
            }
        }
        insertPaymentFormItem(filledForm.getFormId(), form);
        return form;
    }

    private static void insertPaymentFormItem(final int formOwnerId, final FormData newForm) {
        final List<FormItem> formItems = getFormItemListByFormItemName(FormItemName.PAYMENT_AREA,
                ServiceLocator.getPersistance().getFormById(formOwnerId));
        CollectionUtil.removeNull(formItems);
        if (formItems.size() > 0) {
            newForm.getFormItems().addAll(formItems);
        }
    }

    public static boolean isForceFormShowing(final RenderContext context, final ItemType type) {
        return context != null && context.getParameterMap() != null && type != null &&
                StringUtil.getEmptyOrString(context.getParameterMap().get(type)).contains("pageBreaksToPass");
    }

    public DraftForm createDefaultAccountRegistrationForm(final int siteId) {
        final DraftRegistrationForm registrationForm = new DraftRegistrationForm();
        registrationForm.setName("Default Account Registration Form");
        registrationForm.setSiteId(siteId);

        persistance.putRegistrationForm(registrationForm);

        for (DraftFormItem item : FormItemsManager.getDefaultRegistrationFormItems()) {
            item.setDraftForm(registrationForm);
            persistance.putFormItem(item);
            registrationForm.addFormItem(item);
        }

        return registrationForm;
    }

    //todo Artem, this method invoke very often times, for fix we must use one call on all page or logic operation
    public static International getFormInternational() {
        return ServiceLocator.getInternationStorage().get("formTable", Locale.US);
    }


    private static int getTotalPageBreaks(final List<FormItem> formItems) {
        int totalPageBreaks = 0;
        for (FormItem formItem : formItems) {
            if (formItem.getFormItemName().equals(FormItemName.PAGE_BREAK)) {
                totalPageBreaks++;
            }
        }
        return totalPageBreaks;
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
}
