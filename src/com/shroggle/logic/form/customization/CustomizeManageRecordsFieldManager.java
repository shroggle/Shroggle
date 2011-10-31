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

import com.shroggle.entity.CustomizeManageRecordsField;
import com.shroggle.entity.FormItem;
import com.shroggle.entity.FormItemName;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

/**
 * @author Balakirev Anatoliy
 */
public class CustomizeManageRecordsFieldManager {

    public static CustomizeManageRecordsField constructByFormItem(final FormItem formItem) {
        if (formItem == null) {
            throw new IllegalArgumentException("Unable to construct by null FormItem.");
        }
        final CustomizeManageRecordsField field = new CustomizeManageRecordsField();
        field.setFormItemId(formItem.getFormItemId());
        field.setInclude(false);
        field.setPosition(formItem.getPosition());
        return field;
    }

    public CustomizeManageRecordsFieldManager(CustomizeManageRecordsField field) {
        if (field == null) {
            throw new IllegalArgumentException("Unable to create CustomizeManageRecordsFieldManager without CustomizeManageRecordsField.");
        }
        this.field = field;
    }

    public void removeField() {
        field.getCustomizeManageRecords().removeField(field);
        persistance.removeCustomizeManageRecordsField(field);
    }

    public int getId() {
        return field.getId();
    }

    public boolean isFirst() {
        return field.getPosition() == 0;
    }

    public boolean isLast() {
        return field.getPosition() == (field.getCustomizeManageRecords().getFields().size() - 1);
    }

    public int getPosition() {
        return field.getPosition();
    }

    public int getFormItemId() {
        return field.getFormItemId();
    }

    public String getItemName() {
        final FormItem formItem = persistance.getFormItemById(getFormItemId());
        return formItem != null ? formItem.getItemName() : "";
    }

    public FormItemName getFormItemName() {
        final FormItem formItem = persistance.getFormItemById(getFormItemId());
        return formItem != null ? formItem.getFormItemName() : null;
    }

    public boolean isInclude() {
        return field.isInclude();
    }

    public static int getMaxFieldsQuantity() {
        return MAX_FIELDS_QUANTITY;
    }

    private final CustomizeManageRecordsField field;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final static int MAX_FIELDS_QUANTITY = 5;
}
