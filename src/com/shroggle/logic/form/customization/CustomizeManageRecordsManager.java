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
package com.shroggle.logic.form.customization;

import com.shroggle.entity.*;
import com.shroggle.exception.FormNotFoundException;
import com.shroggle.logic.form.FormItemNameManager;
import com.shroggle.logic.form.FormItemsManager;
import com.shroggle.logic.form.FormManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.*;

/**
 * @author Balakirev Anatoliy
 */
public class CustomizeManageRecordsManager {

    public static CustomizeManageRecordsManager getExistingOrConstructNew(final Form form, final Integer userId) {
        if (form == null) {
            throw new IllegalArgumentException("Form should not be null." +
                    " In case form is null this code should not be called");
        }

        CustomizeManageRecords customizeManageRecords = ServiceLocator.getPersistance().getCustomizeManageRecords(form.getId(), userId);
        // Creating data export if it doesn`t exist.
        if (customizeManageRecords == null) {
            customizeManageRecords = new CustomizeManageRecords();
            customizeManageRecords.setFormId(form.getId());
            customizeManageRecords.setUserId(userId);
            final List<FormItem> formItems = new ArrayList<FormItem>(form.getFormItems());
            FormItemsManager.sortByPosition(formItems);
            final FormItem imageFormItem = FormItemsManager.getFirstImageField(formItems);
            if (imageFormItem != null) {
                final CustomizeManageRecordsField imageField = CustomizeManageRecordsFieldManager.constructByFormItem(imageFormItem);
                imageField.setInclude(true);
                customizeManageRecords.addField(imageField);
                boolean secondFieldIncluded = false;
                for (FormItem formItem : formItems) {
                    if (formItem.getFormItemId() != imageFormItem.getFormItemId()) {
                        final CustomizeManageRecordsField field = CustomizeManageRecordsFieldManager.constructByFormItem(formItem);
                        if (!secondFieldIncluded && FormItemNameManager.showFieldOnManageRecords(formItem.getFormItemName())) {
                            field.setInclude(true);
                            secondFieldIncluded = true;
                        }
                        customizeManageRecords.addField(field);
                    }
                }
            } else {
                int numberOfIncludedFields = 0;
                for (FormItem formItem : formItems) {
                    final CustomizeManageRecordsField field = CustomizeManageRecordsFieldManager.constructByFormItem(formItem);
                    if (numberOfIncludedFields < 2 && FormItemNameManager.showFieldOnManageRecords(formItem.getFormItemName())) {
                        field.setInclude(true);
                        numberOfIncludedFields++;
                    }
                    customizeManageRecords.addField(field);
                }
            }
            return new CustomizeManageRecordsManager(customizeManageRecords);
        } else {// If it exists - adding new and removing odd (not existing in form) FormItems
            final CustomizeManageRecordsManager manageRecordsManager = new CustomizeManageRecordsManager(customizeManageRecords);
            manageRecordsManager.normalizeFields();
            return manageRecordsManager;
        }
    }

    public CustomizeManageRecordsManager(CustomizeManageRecords customizeManageRecords) {
        if (customizeManageRecords == null) {
            throw new IllegalArgumentException("Unable to create CustomizeManageRecordsManager without customizeManageRecords.");
        }
        this.form = persistance.getFormById(customizeManageRecords.getFormId());
        if (this.form == null) {
            throw new FormNotFoundException();
        }
        this.customizeManageRecords = customizeManageRecords;
    }

    /**
     * Adding newly created and removing odd (not existing in form) FormItems.
     */
    public void normalizeFields() {
        final FormManager formManager = new FormManager(form);
        for (final CustomizeManageRecordsFieldManager fieldManager : getFields()) {
            if (!formManager.containsFormItem(fieldManager.getFormItemId())) {
                ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
                    @Override
                    public void run() {
                        fieldManager.removeField();
                    }
                });

            }
        }
        for (FormItem formItem : form.getFormItems()) {
            if (!containsFormItem(formItem.getFormItemId())) {
                final CustomizeManageRecordsField field = CustomizeManageRecordsFieldManager.constructByFormItem(formItem);
                field.setInclude(false);
                field.setPosition(customizeManageRecords.getFields().size());
                customizeManageRecords.addField(field);
                persistance.putCustomizeManageRecordsField(field);
            }
        }
    }

    public List<CustomizeManageRecordsFieldManager> getFields() {
        final List<CustomizeManageRecordsFieldManager> fields = new ArrayList<CustomizeManageRecordsFieldManager>();
        for (CustomizeManageRecordsField field : customizeManageRecords.getFields()) {
            fields.add(new CustomizeManageRecordsFieldManager(field));
        }
        Collections.sort(fields, new Comparator<CustomizeManageRecordsFieldManager>() {
            public int compare(CustomizeManageRecordsFieldManager manager1, CustomizeManageRecordsFieldManager manager2) {
                return Integer.valueOf(manager1.getPosition()).compareTo(manager2.getPosition());
            }
        });
        return fields;
    }

    public List<CustomizeManageRecordsFieldManager> getIncludedFields() {
        final List<CustomizeManageRecordsFieldManager> fields = getFields();
        final Iterator<CustomizeManageRecordsFieldManager> iterator = fields.iterator();
        while (iterator.hasNext()) {
            final CustomizeManageRecordsFieldManager manager = iterator.next();
            if (!manager.isInclude()) {
                iterator.remove();
            }
        }
        return fields;
    }

    public String getFirstIncludedFieldItemName() {
        final List<CustomizeManageRecordsFieldManager> fields = getIncludedFields();
        if (!fields.isEmpty()) {
            return fields.get(0).getItemName();
        }
        return null;
    }

    public int getUserId() {
        return customizeManageRecords.getUserId();
    }

    public int getFormId() {
        return customizeManageRecords.getFormId();
    }

    public List<Filter> getFormFilters() {
        return form.getFilters();
    }

    public String getFormName() {
        return form.getName();
    }

    public String getFormDescription() {
        return form.getDescription();
    }

    private boolean containsFormItem(final int formItemId) {
        for (CustomizeManageRecordsField field : customizeManageRecords.getFields()) {
            if (field.getFormItemId() == formItemId) {
                return true;
            }
        }
        return false;
    }

    private final Form form;
    private final CustomizeManageRecords customizeManageRecords;
    private final Persistance persistance = ServiceLocator.getPersistance();
}
