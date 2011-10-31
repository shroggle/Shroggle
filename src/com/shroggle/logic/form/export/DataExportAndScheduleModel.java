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
import com.shroggle.logic.SiteItemsManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.html.HtmlUtil;
import com.shroggle.util.persistance.Persistance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
public class DataExportAndScheduleModel {

    public DataExportAndScheduleModel(int formId) {
        this(formId, null);
    }

    public DataExportAndScheduleModel(final int formId, final Integer formExportTaskId) {
        new UsersManager().getLogined();
        this.formId = formId;
        final Form form = getForm();
        if (form == null) {
            throw new FormNotFoundException("Unable to find form by id = " + formId + ".");
        }
        if (formExportTaskId == null) {
            formExportTaskManager = constructFormExportTaskManager();
        } else {
            formExportTaskManager = new FormExportTaskManager(persistance.getFormExportTask(formExportTaskId));
            formExportTaskManager.normalizeCSVFields();
        }
    }

    public int getFormId() {
        return formId;
    }

    public int getFormExportTaskId() {
        return formExportTaskManager.getId();
    }

    public Integer getSiteId() {
        final DraftForm draftForm = getForm();
        return draftForm != null ? draftForm.getSiteId() : null;
    }

    public String getFormName() {
        final DraftForm draftForm = getForm();
        return draftForm != null ? draftForm.getName() : "";
    }

    public String getLimitedFormDescription() {
        return HtmlUtil.limitName(HtmlUtil.removeParagraphs(getFormDescription()));
    }

    public String getFormDescription() {
        final DraftForm draftForm = getForm();
        return draftForm != null ? draftForm.getDescription() : "";
    }

    public List<Filter> getFormFilters() {
        final DraftForm draftForm = getForm();
        return draftForm != null ? draftForm.getFilters() : Collections.<Filter>emptyList();
    }

    public Integer getFormExportTaskFilterId() {
        return formExportTaskManager.getFilterId();
    }

    public int getFormExportTaskGalleryId() {
        return formExportTaskManager.getGalleryId() != null ? formExportTaskManager.getGalleryId() : -1;
    }

    public int getFormItemIdForTitleId() {
        return formExportTaskManager.getFormItemIdForTitleId() != null ? formExportTaskManager.getFormItemIdForTitleId() : -1;
    }

    public int getFormItemIdForDescriptionId() {
        return formExportTaskManager.getFormItemIdForDescriptionId() != null ? formExportTaskManager.getFormItemIdForDescriptionId() : -1;
    }

    public String getFormExportTaskName() {
        return formExportTaskManager.getName();
    }

    public FormExportDataFormat getFormExportTaskDataFormat() {
        return formExportTaskManager.getDataFormat();
    }

    public FormExportDestination getFormExportTaskDestination() {
        return formExportTaskManager.getDestination();
    }

    public List<CSVDataExportFieldManager> getCSVFields() {
        return formExportTaskManager.getCSVFields();
    }

    public List<DraftGallery> getAvailableGalleries() {
        if (availableGalleries == null) {
            availableGalleries = persistance.getGalleriesByFormId(formId);
        }
        return availableGalleries;
    }

    public List<FormItem> getAvailableTextFormItems() {
        if (textFormItems == null) {
            final DraftForm draftForm = getForm();
            if (draftForm == null) {
                textFormItems = Collections.emptyList();
            } else {
                textFormItems = new ArrayList<FormItem>();
                for (FormItem formItem : draftForm.getFormItems()) {
                    if (formItem.getFormItemName().isText()) {
                        textFormItems.add(formItem);
                    }
                }
            }
        }
        return textFormItems;
    }

    public boolean isAvailableGalleriesEmpty() {
        return getAvailableGalleries().isEmpty();
    }

    public boolean isAvailableTextFormItemsEmpty() {
        return getAvailableTextFormItems().isEmpty();
    }

    public String getGoogleBaseAccountUsername() {
        return StringUtil.isNullOrEmpty(formExportTaskManager.getGoogleBaseAccountUsername()) ? "" : formExportTaskManager.getGoogleBaseAccountUsername();
    }

    public String getGoogleBaseAccountPassword() {
        return StringUtil.isNullOrEmpty(formExportTaskManager.getGoogleBaseAccountPassword()) ? "" : formExportTaskManager.getGoogleBaseAccountPassword();
    }

    public String getFtpLogin() {
        return StringUtil.isNullOrEmpty(formExportTaskManager.getFtpLogin()) ? "" : formExportTaskManager.getFtpLogin();
    }

    public String getFtpPassword() {
        return StringUtil.isNullOrEmpty(formExportTaskManager.getFtpPassword()) ? "" : formExportTaskManager.getFtpPassword();
    }

    public String getOwnFtpAddress() {
        return StringUtil.isNullOrEmpty(formExportTaskManager.getOwnFtpAddress()) ? "" : formExportTaskManager.getOwnFtpAddress();
    }

    public FormExportFrequency getFrequency() {
        return formExportTaskManager.getFrequency();
    }

    public String getStartDateString() {
        final Date startDate = formExportTaskManager.getStartDate() != null ? formExportTaskManager.getStartDate() : new Date();
        return DateUtil.toMonthDayAndYear(startDate);
    }

    public boolean isDestinationFromOurListOfOptions() {
        return !isDestinationOwnFtp();
    }

    public boolean isDestinationOwnFtp() {
        return getFormExportTaskDestination() == FormExportDestination.FTP;
    }

    /*------------------------------------------------Private Methods-------------------------------------------------*/

    private DraftForm getForm() {
        if (form == null) {
            form = persistance.getFormById(formId);
        }
        return form;
    }

    private FormExportTaskManager constructFormExportTaskManager() {
        final FormExportTask formExportTask = new FormExportTask();
        formExportTask.setFormId(formId);
        formExportTask.setName(SiteItemsManager.getNextDefaultNameForFormExportTask(getSiteId()));

        final CSVDataExport csvDataExport = new CSVDataExport();
        for (FormItem formItem : form.getFormItems()) {
            csvDataExport.addField(CSVDataExportFieldManager.constructByFormItem(formItem));
        }
        formExportTask.setCsvDataExport(csvDataExport);

        return new FormExportTaskManager(formExportTask);
    }
    /*------------------------------------------------Private Methods-------------------------------------------------*/

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final FormExportTaskManager formExportTaskManager;
    private List<DraftGallery> availableGalleries;
    private List<FormItem> textFormItems;
    private final int formId;
    private DraftForm form;
}
