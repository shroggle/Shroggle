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

package com.shroggle.presentation.form;

import com.shroggle.entity.*;
import com.shroggle.logic.form.FormItemManager;
import com.shroggle.logic.form.FormItemsManager;
import com.shroggle.logic.form.FormManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.YourFormTableFormItemInfo;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class ShowFormService extends AbstractService implements ShowForm {

    public void fillFormItems(final FormType formType, final Integer formId) {
        new UsersManager().getLogined();

        final List<FormItemName> allItems = Arrays.asList(FormItemName.values());
        for (FormItemName formItem : allItems) {
            if (formItem.getFormItemFilters().contains(FormItemFilter.BASIC)
                    || formItem.getFormItemFilters().contains(FormItemFilter.ALL_FILTERS)) {
                this.initFormItems.add(formItem);
            }
        }

        if (formId != null) {
            final List<FormItem> draftFormItems = new ArrayList<FormItem>(persistance.getFormById(formId).getDraftFormItems());
            new FormItemsManager().sortByPosition(draftFormItems);
            this.existingFormItems = draftFormItems;
        } else {
            //Getting form item's by default
            this.existingFormItems = new ArrayList<FormItem>(FormItemsManager.getFormItems(formType));
        }
    }

    @RemoteMethod
    public List<YourFormTableFormItemInfo> updateByFilter(final FormItemFilter formItemFilter) {
        final List<FormItemName> allItems = new ArrayList<FormItemName>();
        final List<YourFormTableFormItemInfo> filteredItems = new ArrayList<YourFormTableFormItemInfo>();
        final International international = FormManager.getFormInternational();
        allItems.addAll(Arrays.asList(FormItemName.values()));
        new FormItemsManager().removeItemsNotShownInInitTable(allItems);

        for (FormItemName item : allItems) {
            if (item.getFormItemFilters().contains(formItemFilter) || formItemFilter.equals(FormItemFilter.NO_FILTER)
                    || item.getFormItemFilters().contains(FormItemFilter.ALL_FILTERS)) {
                final YourFormTableFormItemInfo yourFormTableFormItemInfo = new YourFormTableFormItemInfo();
                yourFormTableFormItemInfo.setFormItemName(item.toString());
                yourFormTableFormItemInfo.setFieldName(international.get(item.toString() + "_FN"));
                yourFormTableFormItemInfo.setItemFieldType(FormItemManager.getItemFieldType(item, international));
                yourFormTableFormItemInfo.setItemDescription(FormItemManager.getItemDesc(item, international));
                yourFormTableFormItemInfo.setItemDefaultInstruction(FormItemManager.getItemDefaultInstruction(item, international));
                filteredItems.add(yourFormTableFormItemInfo);
            }
        }

        if (formItemFilter == FormItemFilter.PRODUCTS){
            FormItemsManager.moveProductRelatedItemsToTheTop(filteredItems);
        }

        return filteredItems;
    }

    @RemoteMethod
    public List<DraftFormItem> getFormItems(final int formId, final FormType formType) {
        new UsersManager().getLogined();

        final DraftForm form = persistance.getFormById(formId);
        if (form != null && form.getFormItems() != null) {
            new FormItemsManager().sortByPosition(form.getDraftFormItems());
            return form.getDraftFormItems();
        }
        return FormItemsManager.getFormItems(formType);
    }

    // todo blind next four method in one get by form type.
    @RemoteMethod
    public List<DraftFormItem> getDefaultFormItems() {
        return FormItemsManager.getFormItems(FormType.CUSTOM_FORM);
    }

    @RemoteMethod
    public List<DraftFormItem> getDefaultRegistrationFormItems() {
        return FormItemsManager.getFormItems(FormType.REGISTRATION);
    }

    @RemoteMethod
    public List<DraftFormItem> getDefaultChildSiteRegistrationFormItems() {
        return FormItemsManager.getFormItems(FormType.CHILD_SITE_REGISTRATION);
    }

    @RemoteMethod
    public List<DraftFormItem> getDefaultContactUsFormItems() {
        return FormItemsManager.getFormItems(FormType.CONTACT_US);
    }

    public DraftForm getForm() {
        return null;
    }

    public FormManager getFormManager() {
        return formManager;
    }

    public List<FormItem> getExistingFormItems() {
        return existingFormItems;
    }

    public List<FormItemName> getInitFormItems() {
        return initFormItems;
    }

    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
    private List<FormItemName> initFormItems = new ArrayList<FormItemName>();
    private List<FormItem> existingFormItems = new ArrayList<FormItem>();
    private final FormManager formManager = new FormManager();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
