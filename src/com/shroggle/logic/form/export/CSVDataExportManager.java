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

import com.shroggle.entity.*;
import com.shroggle.exception.FormNotFoundException;
import com.shroggle.logic.form.FilledFormItemManager;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.form.FormManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.*;

/**
 * @author Balakirev Anatoliy
 */
public class CSVDataExportManager {

    public CSVDataExportManager(CSVDataExport csvDataExport, final Form form) {
        if (csvDataExport == null) {
            throw new IllegalArgumentException("Unable to create CSVDataExportManager without csvDataExport.");
        }
        if (form == null) {
            throw new FormNotFoundException();
        }
        this.form = form;
        this.csvDataExport = csvDataExport;
    }

    public String createCSV(final List<FilledForm> filledForms) {
        if (filledForms == null || filledForms.isEmpty()) {
            return "";
        }
        final StringBuilder csv = new StringBuilder("");
        final List<CSVDataExportFieldManager> includedFields = getIncludedFields();
        for (CSVDataExportFieldManager fieldManager : includedFields) {
            csv.append(fieldManager.getCustomizeHeader());
            csv.append(PATH_SEPARATOR);
        }
        removeLastSymbol(csv);
        csv.append("\n");
        for (FilledForm filledForm : filledForms) {
            for (CSVDataExportFieldManager fieldManager : includedFields) {
                final FilledFormItem filledFormItem = FilledFormManager.getFilledFormItemByFormItemId(filledForm, fieldManager.getFormItemId());
                if (filledFormItem != null) {
                    final String value = new FilledFormItemManager(filledFormItem).getStringValueForDataExport();
                    csv.append(value);
                } else {
                    csv.append("null");
                }
                csv.append(PATH_SEPARATOR);
            }
            removeLastSymbol(csv);
            csv.append("\n");
        }
        return csv.toString();
    }

    /**
     * Adding new and removing odd (not existing in form) FormItems.
     */
    public void normalizeFields() {
        final FormManager formManager = new FormManager(form);
        for (final CSVDataExportFieldManager fieldManager : getFields()) {
            if (!formManager.containsFormItem(fieldManager.getFormItemId())) {
                ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
                    public void run() {
                        fieldManager.removeField();
                    }
                });

            }
        }
        for (FormItem formItem : form.getFormItems()) {
            if (!containsFormItem(formItem.getFormItemId())) {
                final CSVDataExportField field = CSVDataExportFieldManager.constructByFormItem(formItem);
                field.setInclude(false);
                field.setPosition(csvDataExport.getFields().size());
                csvDataExport.addField(field);
                persistance.putCSVDataExportField(field);
            }
        }
    }

    public List<CSVDataExportFieldManager> getFields() {
        final List<CSVDataExportFieldManager> fields = new ArrayList<CSVDataExportFieldManager>();
        for (CSVDataExportField field : csvDataExport.getFields()) {
            fields.add(new CSVDataExportFieldManager(field));
        }
        Collections.sort(fields, new Comparator<CSVDataExportFieldManager>() {
            public int compare(CSVDataExportFieldManager manager1, CSVDataExportFieldManager manager2) {
                return Integer.valueOf(manager1.getPosition()).compareTo(manager2.getPosition());
            }
        });
        return fields;
    }

    public List<CSVDataExportFieldManager> getIncludedFields() {
        final List<CSVDataExportFieldManager> fields = getFields();
        final Iterator<CSVDataExportFieldManager> iterator = fields.iterator();
        while (iterator.hasNext()) {
            final CSVDataExportFieldManager manager = iterator.next();
            if (!manager.isInclude()) {
                iterator.remove();
            }
        }
        return fields;
    }


    public String getPathSeparator() {
        return PATH_SEPARATOR;
    }

    public int getId() {
        return csvDataExport.getId();
    }

    /*------------------------------------------------Private Methods-------------------------------------------------*/

    private void removeLastSymbol(final StringBuilder stringBuilder) {
        if (stringBuilder != null && stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
    }

    private boolean containsFormItem(final int formItemId) {
        for (CSVDataExportField field : csvDataExport.getFields()) {
            if (field.getFormItemId() == formItemId) {
                return true;
            }
        }
        return false;
    }
    /*------------------------------------------------Private Methods-------------------------------------------------*/

    private final Form form;
    private final CSVDataExport csvDataExport;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final String PATH_SEPARATOR = ServiceLocator.getConfigStorage().get().getCsvPathSeparator();
}
