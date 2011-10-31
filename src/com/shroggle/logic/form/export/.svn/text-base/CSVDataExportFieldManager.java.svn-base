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
package com.shroggle.logic.form.export;

import com.shroggle.entity.CSVDataExportField;
import com.shroggle.entity.FormItem;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

/**
 * @author Balakirev Anatoliy
 */
public class CSVDataExportFieldManager {

    public static CSVDataExportField constructByFormItem(final FormItem formItem) {
        if (formItem == null) {
            throw new IllegalArgumentException("Unable to construct by null FormItem.");
        }
        final CSVDataExportField field = new CSVDataExportField();
        field.setCustomizeHeader(formItem.getItemName());
        field.setFormItemId(formItem.getFormItemId());
        field.setInclude(true);
        field.setPosition(formItem.getPosition());
        return field;
    }

    public CSVDataExportFieldManager(CSVDataExportField field) {
        if (field == null) {
            throw new IllegalArgumentException("Unable to create CSVDataExportFieldManager without CSVDataExportField.");
        }
        this.field = field;
    }

    public void removeField() {
        field.getCustomizeDataExport().removeField(field);
        persistance.removeCSVDataExportField(field);
    }

    public int getId() {
        return field.getId();
    }

    public boolean isFirst() {
        return field.getPosition() == 0;
    }

    public boolean isLast() {
        return field.getPosition() == (field.getCustomizeDataExport().getFields().size() - 1);
    }

    public int getPosition() {
        return field.getPosition();
    }

    public int getFormItemId() {
        return field.getFormItemId();
    }

    public String getCustomizeHeader() {
        return field.getCustomizeHeader();
    }

    public String getFormItemName() {
        final FormItem formItem = persistance.getFormItemById(getFormItemId());
        return formItem != null ? formItem.getItemName() : "";
    }

    public boolean isInclude() {
        return field.isInclude();
    }

    private final CSVDataExportField field;
    private final Persistance persistance = ServiceLocator.getPersistance();
}
