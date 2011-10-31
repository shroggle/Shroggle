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
import com.shroggle.logic.form.filter.FormFilterManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.form.export.SaveDataExportAndScheduleRequest;
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.ftp.FtpUtils;
import com.shroggle.util.persistance.Persistance;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
public class FormExportTaskManager {

    public FormExportTaskManager(final Integer formExportTaskId) {
        this(ServiceLocator.getPersistance().getFormExportTask(formExportTaskId));
    }

    public FormExportTaskManager(FormExportTask formExportTask) {
        if (formExportTask == null) {
            throw new IllegalArgumentException("Unable to create FormExportTaskManager without FormExportTask.");
        }
        this.formExportTask = formExportTask;
    }

    public static void save(final SaveDataExportAndScheduleRequest request) {
        new UsersManager().getLogined();
        final Persistance persistance = ServiceLocator.getPersistance();
        final Form form = persistance.getFormById(request.getFormId());
        if (form == null) {
            throw new FormNotFoundException("Unable to find form by id = " + request.getFormId() + ".");
        }
        ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
            public void run() {
                FormExportTask formExportTask = persistance.getFormExportTask(request.getFormExportTaskId());
                formExportTask = formExportTask == null ? new FormExportTask() : formExportTask;
                formExportTask.setFormId(request.getFormId());
                formExportTask.setGoogleBaseDataExport(request.getGoogleBaseDataExport());
                formExportTask.setDataFormat(request.getDataFormat());
                formExportTask.setDestination(request.getDestination());
                formExportTask.setFrequency(request.getFrequency());
                formExportTask.setName(request.getName());
                formExportTask.setOwnFtpAddress(request.getOwnFtpAddress());
                formExportTask.setStartDate(DateUtil.getDateByString(request.getStartDate()));
                formExportTask.setFtpLogin(request.getFtpLogin());
                formExportTask.setFtpPassword(request.getFtpPassword());
                formExportTask.setFilterId(request.getFilterId());

                final CSVDataExport csvDataExport = formExportTask.getCsvDataExport() != null ? formExportTask.getCsvDataExport() : new CSVDataExport();
                if (formExportTask.getId() <= 0) {
                    persistance.putFormExportTask(formExportTask);
                    persistance.putCSVDataExport(csvDataExport);
                    formExportTask.setCsvDataExport(csvDataExport);
                }
                // Removing old fields.
                for (CSVDataExportFieldManager manager : new CSVDataExportManager(csvDataExport, form).getFields()) {
                    manager.removeField();
                }
                // Adding new one.
                for (CSVDataExportField field : request.getFields()) {
                    csvDataExport.addField(field);
                    persistance.putCSVDataExportField(field);
                }
            }
        });
    }

    public void delete() {
        ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
            public void run() {
                persistance.removeFormExportTask(formExportTask);
            }
        });
    }

    public void sendDataIfNeeded() {
        if (isDataShouldBeSent()) {
            if (getDataFormat() == FormExportDataFormat.CSV) {
                new FtpUtils(getOwnFtpAddress(), getFtpLogin(), getFtpPassword()).uploadFile(createCSVFile(), createCsvFileName());
            } else {
                getGoogleBaseDataExport().updateOrCreateGoogleBaseItems(getFilteredFilledForms());
            }
            // Adding current date to FormExportTask`s history.
            ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
                public void run() {
                    if (formExportTask.getHistory() != null) {
                        formExportTask.getHistory().add(new Date());
                    } else {
                        final List<Date> history = new ArrayList<Date>();
                        history.add(new Date());
                        formExportTask.setHistory(history);
                    }
                }
            });
        }
    }

    public String createCSVFile() {
        if (getDataFormat() == FormExportDataFormat.CSV) {
            return getCsvDataExport().createCSV(getFilteredFilledForms());
        } else {
            logger.info("Unable to create csv file for form export task configured as 'Google base' task. Check interface.");
            return null;
        }
    }

    public String createCsvFileName() {
        final String formName = getFormName();
        final String filterName = getFilterName();
        final String currentDate = new SimpleDateFormat("MM.dd.yy_hh.mm a", Locale.US).format(new Date());
        return formName + (!filterName.isEmpty() ? ("_" + filterName) : "") + "_" + currentDate + ".csv";
    }

    public String getFormName() {
        return getForm() != null ? getForm().getName() : "";
    }

    public Date getLastSuccessfulExportDate() {
        Date lastDate = null;
        for (Date date : getHistory()) {
            if (lastDate == null || date.after(lastDate)) {
                lastDate = date;
            }
        }
        return lastDate;
    }

    public String getLastSuccessfulExportDateString() {
        final Date lastExportDate = getLastSuccessfulExportDate();
        return lastExportDate != null ? DateUtil.toMonthDayAndYear(lastExportDate) : "";
    }

    public String getCreatedString() {
        return DateUtil.toMonthDayAndYear(getCreated());
    }

    public String getFilterName() {
        final Filter filter = persistance.getFormFilterById(getFilterId());
        return filter != null ? filter.getName() : "";
    }

    public List<CSVDataExportFieldManager> getCSVFields() {
        return getCsvDataExport().getFields();
    }

    public Integer getFormItemIdForTitleId() {
        return getGoogleBaseDataExport().getFormItemIdForTitle();
    }

    public Integer getFormItemIdForDescriptionId() {
        return getGoogleBaseDataExport().getFormItemIdForDescription();
    }

    public Integer getGalleryId() {
        return getGoogleBaseDataExport().getGalleryId();
    }

    public void normalizeCSVFields() {
        getCsvDataExport().normalizeFields();
    }

    public boolean isShowDownloadButton() {
        return getDataFormat() == FormExportDataFormat.CSV;
    }
    /*-----------------------------------------------------Getters----------------------------------------------------*/

    public int getId() {
        return formExportTask.getId();
    }

    public int getFormId() {
        return formExportTask.getFormId();
    }

    public CSVDataExportManager getCsvDataExport() {
        return new CSVDataExportManager(formExportTask.getCsvDataExport(), persistance.getFormById(formExportTask.getFormId()));
    }

    public String getName() {
        return formExportTask.getName();
    }

    public FormExportFrequency getFrequency() {
        return formExportTask.getFrequency();
    }

    public Date getCreated() {
        return formExportTask.getCreated();
    }

    public List<Date> getHistory() {
        return formExportTask.getHistory() != null ? formExportTask.getHistory() : Collections.<Date>emptyList();
    }

    public GoogleBaseDataExportManager getGoogleBaseDataExport() {
        return new GoogleBaseDataExportManager(formExportTask.getGoogleBaseDataExport());
    }

    public Integer getFilterId() {
        return formExportTask.getFilterId();
    }

    public FormExportDataFormat getDataFormat() {
        return formExportTask.getDataFormat();
    }

    public FormExportDestination getDestination() {
        return formExportTask.getDestination();
    }

    public String getGoogleBaseAccountUsername() {
        return getGoogleBaseDataExport().getGoogleBaseAccountUsername();
    }

    public String getGoogleBaseAccountPassword() {
        return getGoogleBaseDataExport().getGoogleBaseAccountPassword();
    }

    public String getFtpLogin() {
        return formExportTask.getFtpLogin();
    }

    public String getFtpPassword() {
        return formExportTask.getFtpPassword();
    }


    public String getOwnFtpAddress() {
        return formExportTask.getOwnFtpAddress();
    }

    public Date getStartDate() {
        return formExportTask.getStartDate();
    }

    public DraftForm getForm() {
        if (form == null) {
            form = persistance.getFormById(formExportTask.getFormId());
        }
        return form;
    }
    /*-----------------------------------------------------Getters----------------------------------------------------*/


    /*-----------------------------------------------------Setters----------------------------------------------------*/

    /*public void setId(int id) {
        formExportTask.setId(id);
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public void setCustomizeDataExport(CSVDataExport customizeDataExport) {
        this.customizeDataExport = customizeDataExport;
    }

    public void setRecipient(FormExportRecipient recipient) {
        this.recipient = recipient;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFrequency(FormExportFrequency frequency) {
        this.frequency = frequency;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setHistory(List<Date> history) {
        this.history = history;
    }

    public void setGoogleBaseDataExport(GoogleBaseDataExport googleBaseDataExport) {
        this.googleBaseDataExport = googleBaseDataExport;
    }

    public void setFilterId(int filterId) {
        this.filterId = filterId;
    }*/
    /*-----------------------------------------------------Setters----------------------------------------------------*/


    private List<FilledForm> getFilteredFilledForms() {
        final DraftFormFilter filter = persistance.getFormFilterById(getFilterId());
        return filter != null ? new FormFilterManager(filter).getFilledForms() : persistance.getFilledFormsByFormId(getForm().getId());
    }

    private boolean isDataShouldBeSent() {
        if (getLastSuccessfulExportDate() == null && getStartDate().before(new Date())) {
            return true;
        }
        final Date followingExecution = new Date(getLastSuccessfulExportDate().getTime() + getFrequency().getMillis());
        // Minimum precision is DAILY so it`s enough to compare only year, month and date, without hours, minutes, seconds, etc.
        return DateUtil.toMonthDayAndYear(followingExecution).equals(DateUtil.toMonthDayAndYear(new Date()));
    }

    private DraftForm form;// Don`t use this field directly! Use getForm() instead.
    private FormExportTask formExportTask;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final Logger logger = Logger.getLogger(this.getClass().getName());
}
