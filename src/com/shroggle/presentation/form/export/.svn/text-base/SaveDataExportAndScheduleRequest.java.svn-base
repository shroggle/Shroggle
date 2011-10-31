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
package com.shroggle.presentation.form.export;

import com.shroggle.entity.*;
import org.directwebremoting.annotations.DataTransferObject;

import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@DataTransferObject
public class SaveDataExportAndScheduleRequest {

    private Integer formExportTaskId;

    private int formId;

    private Integer filterId;

    private GoogleBaseDataExport googleBaseDataExport;

    private FormExportDataFormat dataFormat;

    private FormExportFrequency frequency;

    private FormExportDestination destination;

    private String name;

    private String ownFtpAddress;

    private String startDate;

    private List<CSVDataExportField> fields;

    private String ftpLogin;

    private String ftpPassword;

    public String getFtpLogin() {
        return ftpLogin;
    }

    public void setFtpLogin(String ftpLogin) {
        this.ftpLogin = ftpLogin;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }

    public FormExportDestination getDestination() {
        return destination;
    }

    public void setDestination(FormExportDestination destination) {
        this.destination = destination;
    }

    public Integer getFormExportTaskId() {
        return formExportTaskId;
    }

    public void setFormExportTaskId(Integer formExportTaskId) {
        this.formExportTaskId = formExportTaskId;
    }

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public Integer getFilterId() {
        return filterId;
    }

    public void setFilterId(Integer filterId) {
        this.filterId = filterId;
    }

    public List<CSVDataExportField> getFields() {
        return fields;
    }

    public void setFields(List<CSVDataExportField> fields) {
        this.fields = fields;
    }

    public GoogleBaseDataExport getGoogleBaseDataExport() {
        return googleBaseDataExport;
    }

    public void setGoogleBaseDataExport(GoogleBaseDataExport googleBaseDataExport) {
        this.googleBaseDataExport = googleBaseDataExport;
    }

    public FormExportDataFormat getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(FormExportDataFormat dataFormat) {
        this.dataFormat = dataFormat;
    }


    public FormExportFrequency getFrequency() {
        return frequency;
    }

    public void setFrequency(FormExportFrequency frequency) {
        this.frequency = frequency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnFtpAddress() {
        return ownFtpAddress;
    }

    public void setOwnFtpAddress(String ownFtpAddress) {
        this.ownFtpAddress = ownFtpAddress;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
